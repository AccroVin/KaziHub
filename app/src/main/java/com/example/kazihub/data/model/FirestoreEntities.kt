package com.example.kazihub.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class UserProfileFirestore(
    @DocumentId val id: String = "",
    val userId: String = "",
    val fullName: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val region: String = "",
    val district: String = "",
    val primaryTrade: String = "",
    val skills: List<String> = emptyList(),
    val yearsExperience: Int = 0,
    val expectedRateTsh: Int = 0,
    val ratePeriod: String = "Kwa Siku",
    val bio: String = "",
    @field:PropertyName("isVerifiedPro") @get:PropertyName("isVerifiedPro") val isVerifiedPro: Boolean = false,
    val rating: Double = 5.0,
    val completedJobs: Int = 0,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class JobVacancyFirestore(
    @DocumentId val id: String = "",
    val userId: String = "",
    val title: String = "",
    val employerName: String = "",
    val category: String = "",
    val requiredSkills: List<String> = emptyList(),
    val region: String = "",
    val district: String = "",
    val salaryTsh: Int = 0,
    val salaryPeriod: String = "Kwa Mwezi",
    val jobType: String = "Kazi ya Kudumu / Full-time",
    val description: String = "",
    val contactPhone: String = "",
    val contactWhatsapp: String = "",
    @field:PropertyName("isUrgent") @get:PropertyName("isUrgent") val isUrgent: Boolean = false,
    @field:PropertyName("isFeatured") @get:PropertyName("isFeatured") val isFeatured: Boolean = false,
    val postedDate: String = "Leo",
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class JobApplicationFirestore(
    @DocumentId val id: String = "",
    val applicantId: String = "",
    val jobId: String = "",
    val jobTitle: String = "",
    val employerName: String = "",
    val applicantName: String = "",
    val applicantPhone: String = "",
    val proposalNote: String = "",
    val status: String = "Imetumwa (Sent)",
    @ServerTimestamp val createdAt: Timestamp? = null
)
