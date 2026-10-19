package com.example.kazihub.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kazihub.data.model.JobApplication
import com.example.kazihub.data.model.JobListing
import com.example.kazihub.data.model.SubscriptionRecord
import com.example.kazihub.data.model.WorkerProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkerProfileDao {
    @Query("SELECT * FROM worker_profiles WHERE id = 1 LIMIT 1")
    fun getProfile(): Flow<WorkerProfile?>

    @Query("SELECT * FROM worker_profiles WHERE id = 1 LIMIT 1")
    suspend fun getProfileDirect(): WorkerProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: WorkerProfile)

    @Query("UPDATE worker_profiles SET isVerifiedPro = :isPro, proExpiryDate = :expiry WHERE id = 1")
    suspend fun updateProStatus(isPro: Boolean, expiry: Long)
}

@Dao
interface JobListingDao {
    @Query("SELECT * FROM job_listings ORDER BY isFeatured DESC, isUrgent DESC, id DESC")
    fun getAllJobs(): Flow<List<JobListing>>

    @Query("SELECT * FROM job_listings WHERE id = :jobId LIMIT 1")
    suspend fun getJobById(jobId: Long): JobListing?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobs(jobs: List<JobListing>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: JobListing): Long

    @Query("UPDATE job_listings SET isSaved = :isSaved WHERE id = :jobId")
    suspend fun toggleSaveJob(jobId: Long, isSaved: Boolean)

    @Query("SELECT COUNT(*) FROM job_listings")
    suspend fun getJobsCount(): Int
}

@Dao
interface JobApplicationDao {
    @Query("SELECT * FROM job_applications ORDER BY appliedAt DESC")
    fun getAllApplications(): Flow<List<JobApplication>>

    @Query("SELECT * FROM job_applications WHERE jobId = :jobId LIMIT 1")
    suspend fun getApplicationForJob(jobId: Long): JobApplication?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApplication(application: JobApplication): Long
}

@Dao
interface SubscriptionDao {
    @Query("SELECT * FROM subscriptions WHERE isActive = 1 ORDER BY expiresAt DESC LIMIT 1")
    fun getActiveSubscription(): Flow<SubscriptionRecord?>

    @Query("SELECT * FROM subscriptions ORDER BY activatedAt DESC")
    fun getAllSubscriptions(): Flow<List<SubscriptionRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscription(sub: SubscriptionRecord): Long
}
