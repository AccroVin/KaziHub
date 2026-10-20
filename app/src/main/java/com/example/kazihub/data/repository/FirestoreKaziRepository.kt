package com.example.kazihub.data.repository

import android.content.Context
import com.example.R
import com.example.kazihub.data.OperationType
import com.example.kazihub.data.handleFirestoreError
import com.example.kazihub.data.model.JobApplicationFirestore
import com.example.kazihub.data.model.JobVacancyFirestore
import com.example.kazihub.data.model.UserProfileFirestore
import com.example.kazihub.util.TanzaniaData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirestoreKaziRepository(private val db: FirebaseFirestore) {

    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private val auth = FirebaseAuth.getInstance()

    private fun requireUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User must be signed in with Google before accessing Firestore.")
    }

    // 1. Observe User Profile
    fun observeUserProfile(userId: String): Flow<UserProfileFirestore?> {
        val path = "users/$userId"
        return db.collection("users").document(userId)
            .snapshots()
            .map { snapshot ->
                if (snapshot.exists()) {
                    snapshot.toObject(UserProfileFirestore::class.java)
                } else {
                    null
                }
            }
            .catch { e ->
                if (e is Exception) handleFirestoreError(e, OperationType.GET, path)
                throw e
            }
            .flowOn(Dispatchers.IO)
    }

    // 2. Save / Update User Profile
    suspend fun saveUserProfile(profile: UserProfileFirestore): Result<Unit> = withContext(Dispatchers.IO) {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid)
        try {
            val existing = docRef.get().await()
            if (existing.exists()) {
                val existingProfile = existing.toObject(UserProfileFirestore::class.java)
                val updatePayload = profile.copy(
                    userId = uid,
                    createdAt = existingProfile?.createdAt,
                    updatedAt = null
                )
                docRef.set(updatePayload).await()
            } else {
                val newProfile = profile.copy(
                    userId = uid,
                    createdAt = null,
                    updatedAt = null
                )
                docRef.set(newProfile).await()
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            Result.failure(e)
        }
    }

    // 3. Observe All Jobs (Public Marketplace)
    fun observeAllJobs(): Flow<List<JobVacancyFirestore>> {
        val path = "jobs"
        return db.collection("jobs")
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(JobVacancyFirestore::class.java)
            }
            .catch { e ->
                if (e is Exception) handleFirestoreError(e, OperationType.LIST, path)
                throw e
            }
            .flowOn(Dispatchers.IO)
    }

    // 4. Post New Job
    suspend fun postJob(job: JobVacancyFirestore): Result<String> = withContext(Dispatchers.IO) {
        val uid = requireUserId()
        val collectionRef = db.collection("jobs")
        try {
            val docRef = collectionRef.document()
            val payload = job.copy(
                userId = uid,
                createdAt = null,
                updatedAt = null
            )
            docRef.set(payload).await()
            Result.success(docRef.id)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, collectionRef.path)
            Result.failure(e)
        }
    }

    // 5. Delete Job
    suspend fun deleteJob(jobId: String): Result<Unit> = withContext(Dispatchers.IO) {
        val uid = requireUserId()
        val docRef = db.collection("jobs").document(jobId)
        try {
            val existing = docRef.get().await()
            if (existing.exists() && existing.getString("userId") == uid) {
                docRef.delete().await()
                Result.success(Unit)
            } else {
                Result.failure(IllegalStateException("Unauthorized or job not found"))
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docRef.path)
            Result.failure(e)
        }
    }

    // 6. Observe Applications for Authenticated User
    fun observeUserApplications(userId: String): Flow<List<JobApplicationFirestore>> {
        val path = "applications"
        return db.collection("applications")
            .whereEqualTo("applicantId", userId)
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(JobApplicationFirestore::class.java)
            }
            .catch { e ->
                if (e is Exception) handleFirestoreError(e, OperationType.LIST, path)
                throw e
            }
            .flowOn(Dispatchers.IO)
    }

    // 7. Apply For Job
    suspend fun submitApplication(application: JobApplicationFirestore): Result<String> = withContext(Dispatchers.IO) {
        val uid = requireUserId()
        val collectionRef = db.collection("applications")
        try {
            val docRef = collectionRef.document()
            val payload = application.copy(
                applicantId = uid,
                createdAt = null
            )
            docRef.set(payload).await()
            Result.success(docRef.id)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, collectionRef.path)
            Result.failure(e)
        }
    }

    // 8. Seed Initial Jobs if Empty
    suspend fun seedInitialJobsIfEmpty() = withContext(Dispatchers.IO) {
        val uid = auth.currentUser?.uid ?: return@withContext
        try {
            val snapshot = db.collection("jobs").limit(1).get().await()
            if (snapshot.isEmpty) {
                val initialList = TanzaniaData.getInitialJobListings()
                for (item in initialList) {
                    val docRef = db.collection("jobs").document()
                    val firestoreJob = JobVacancyFirestore(
                        userId = uid,
                        title = item.title,
                        employerName = item.employerName,
                        category = item.category,
                        requiredSkills = item.requiredSkills,
                        region = item.region,
                        district = item.district,
                        salaryTsh = item.salaryTsh,
                        salaryPeriod = item.salaryPeriod,
                        jobType = item.jobType,
                        description = item.description,
                        contactPhone = item.contactPhone,
                        contactWhatsapp = item.contactWhatsapp,
                        isUrgent = item.isUrgent,
                        isFeatured = item.isFeatured,
                        postedDate = item.postedDate,
                        createdAt = null,
                        updatedAt = null
                    )
                    docRef.set(firestoreJob).await()
                }
            }
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.LIST, "jobs")
        }
    }
}
