package com.example.kazihub.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "worker_profiles")
data class WorkerProfile(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 1L,
    val fullName: String = "Juma Bakari",
    val phoneNumber: String = "+255 754 123 456",
    val email: String = "juma.bakari@kazihub.tz",
    val region: String = "Dar es Salaam",
    val district: String = "Kinondoni",
    val primaryTrade: String = "Fundi Umeme (Electrician)",
    val skills: List<String> = listOf("Wiring ya Nyumba", "Solar Installation", "CCTV & Security", "Generators"),
    val yearsExperience: Int = 4,
    val expectedRateTsh: Int = 45000,
    val ratePeriod: String = "Kwa Siku", // Kwa Siku, Kwa Mwezi, Kwa Mradi
    val bio: String = "Fundi mwenye uzoefu wa miaka 4 katika wiring za kisasa za nyumba, ufungaji wa mifumo ya sola na ukaguzi wa usalama wa umeme jijini Dar es Salaam.",
    val isVerifiedPro: Boolean = false,
    val proExpiryDate: Long = 0L,
    val rating: Float = 4.9f,
    val completedJobs: Int = 18
)

@Entity(tableName = "job_listings")
data class JobListing(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val employerName: String,
    val category: String,
    val requiredSkills: List<String>,
    val region: String,
    val district: String,
    val salaryTsh: Int,
    val salaryPeriod: String, // Kwa Siku, Kwa Mwezi, Kwa Mradi
    val jobType: String, // Kibarua / Gig, Kandarasi / Contract, Kazi ya Kudumu / Full-time
    val description: String,
    val contactPhone: String,
    val contactWhatsapp: String,
    val isUrgent: Boolean = false,
    val isFeatured: Boolean = false,
    val postedDate: String = "Leo",
    val isSaved: Boolean = false
)

@Entity(tableName = "job_applications")
data class JobApplication(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val jobId: Long,
    val jobTitle: String,
    val employerName: String,
    val appliedAt: Long = System.currentTimeMillis(),
    val status: String = "Imetumwa (Sent)",
    val proposalNote: String = ""
)

@Entity(tableName = "subscriptions")
data class SubscriptionRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val planType: String, // "WEEKLY" or "MONTHLY"
    val priceTsh: Int,
    val carrier: String, // M-Pesa, Tigo Pesa, Airtel Money, HaloPesa
    val phoneNumber: String,
    val transactionRef: String,
    val activatedAt: Long = System.currentTimeMillis(),
    val expiresAt: Long = System.currentTimeMillis() + (7L * 24 * 60 * 60 * 1000),
    val isActive: Boolean = true
)

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return value?.joinToString(":::") ?: ""
    }

    @TypeConverter
    fun toStringList(value: String?): List<String> {
        if (value.isNullOrBlank()) return emptyList()
        return value.split(":::").filter { it.isNotBlank() }
    }
}
