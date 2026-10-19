package com.example.kazihub.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kazihub.data.model.Converters
import com.example.kazihub.data.model.JobApplication
import com.example.kazihub.data.model.JobListing
import com.example.kazihub.data.model.SubscriptionRecord
import com.example.kazihub.data.model.WorkerProfile

@Database(
    entities = [
        WorkerProfile::class,
        JobListing::class,
        JobApplication::class,
        SubscriptionRecord::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class KaziHubDatabase : RoomDatabase() {
    abstract fun workerProfileDao(): WorkerProfileDao
    abstract fun jobListingDao(): JobListingDao
    abstract fun jobApplicationDao(): JobApplicationDao
    abstract fun subscriptionDao(): SubscriptionDao

    companion object {
        @Volatile
        private var INSTANCE: KaziHubDatabase? = null

        fun getDatabase(context: Context): KaziHubDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KaziHubDatabase::class.java,
                    "kazihub_tanzania.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
