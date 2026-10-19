package com.example.kazihub.data.repository

import com.example.kazihub.data.local.KaziHubDatabase
import com.example.kazihub.data.model.JobApplication
import com.example.kazihub.data.model.JobListing
import com.example.kazihub.data.model.SubscriptionRecord
import com.example.kazihub.data.model.WorkerProfile
import com.example.kazihub.util.TanzaniaData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class KaziHubRepository(private val database: KaziHubDatabase) {
    val workerProfile: Flow<WorkerProfile?> = database.workerProfileDao().getProfile()
    val allJobs: Flow<List<JobListing>> = database.jobListingDao().getAllJobs()
    val applications: Flow<List<JobApplication>> = database.jobApplicationDao().getAllApplications()
    val activeSubscription: Flow<SubscriptionRecord?> = database.subscriptionDao().getActiveSubscription()

    suspend fun initializeDatabaseIfEmpty() = withContext(Dispatchers.IO) {
        val jobsCount = database.jobListingDao().getJobsCount()
        if (jobsCount == 0) {
            database.jobListingDao().insertJobs(TanzaniaData.getInitialJobListings())
        }

        val profile = database.workerProfileDao().getProfileDirect()
        if (profile == null) {
            database.workerProfileDao().insertOrUpdateProfile(WorkerProfile())
        }
    }

    suspend fun updateProfile(profile: WorkerProfile) = withContext(Dispatchers.IO) {
        database.workerProfileDao().insertOrUpdateProfile(profile)
    }

    suspend fun postJob(job: JobListing): Long = withContext(Dispatchers.IO) {
        database.jobListingDao().insertJob(job)
    }

    suspend fun toggleSaveJob(jobId: Long, isSaved: Boolean) = withContext(Dispatchers.IO) {
        database.jobListingDao().toggleSaveJob(jobId, isSaved)
    }

    suspend fun applyForJob(jobId: Long, jobTitle: String, employerName: String, proposal: String): Long = withContext(Dispatchers.IO) {
        val app = JobApplication(
            jobId = jobId,
            jobTitle = jobTitle,
            employerName = employerName,
            proposalNote = proposal
        )
        database.jobApplicationDao().insertApplication(app)
    }

    suspend fun activateSubscription(
        planType: String,
        priceTsh: Int,
        carrier: String,
        phoneNumber: String
    ): SubscriptionRecord = withContext(Dispatchers.IO) {
        val durationMillis = if (planType.equals("WEEKLY", ignoreCase = true)) {
            7L * 24 * 60 * 60 * 1000
        } else {
            30L * 24 * 60 * 60 * 1000
        }
        val now = System.currentTimeMillis()
        val expiry = now + durationMillis
        val txRef = "TZ-${carrier.take(2).uppercase()}-${(100000..999999).random()}"

        val record = SubscriptionRecord(
            planType = planType,
            priceTsh = priceTsh,
            carrier = carrier,
            phoneNumber = phoneNumber,
            transactionRef = txRef,
            activatedAt = now,
            expiresAt = expiry,
            isActive = true
        )
        database.subscriptionDao().insertSubscription(record)
        database.workerProfileDao().updateProStatus(true, expiry)
        record
    }
}
