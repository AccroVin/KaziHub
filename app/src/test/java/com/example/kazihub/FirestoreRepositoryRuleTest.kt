package com.example.kazihub

import com.example.base.FirestoreEmulatorTestBase
import com.example.kazihub.data.model.JobApplicationFirestore
import com.example.kazihub.data.model.JobVacancyFirestore
import com.example.kazihub.data.model.UserProfileFirestore
import com.example.kazihub.data.repository.FirestoreKaziRepository
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class FirestoreRepositoryRuleTest : FirestoreEmulatorTestBase() {

    @Test
    fun createAndReadProfile_authenticatedUser_succeeds() = runBlocking {
        val uid = signInTestUser("user_${System.nanoTime()}@kazihub.tz")
        val repository = FirestoreKaziRepository(firestore)

        val profile = UserProfileFirestore(
            userId = uid,
            fullName = "Alice Bakari",
            phoneNumber = "+255714112233",
            primaryTrade = "Fundi Umeme",
            region = "Dar es Salaam",
            district = "Kinondoni",
            skills = listOf("Wiring ya Nyumba", "Solar Power"),
            yearsExperience = 4,
            expectedRateTsh = 40000
        )

        val saveResult = withTimeout(DEFAULT_TIMEOUT_MS) { repository.saveUserProfile(profile) }
        if (saveResult.isFailure) throw saveResult.exceptionOrNull()!!
        assertTrue(saveResult.isSuccess)

        val readProfile = withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.observeUserProfile(uid).first { it != null }
        }
        assertNotNull(readProfile)
        assertEquals("Alice Bakari", readProfile?.fullName)
        assertEquals("Fundi Umeme", readProfile?.primaryTrade)
    }

    @Test
    fun postJob_authenticatedUser_succeeds() = runBlocking {
        val uid = signInTestUser("employer_${System.nanoTime()}@kazihub.tz")
        val repository = FirestoreKaziRepository(firestore)

        val job = JobVacancyFirestore(
            userId = uid,
            title = "Fundi Bomba wa Haraka",
            employerName = "Serengeti Lodge",
            category = "Fundi Bomba",
            region = "Arusha",
            district = "Arusha City",
            salaryTsh = 50000,
            contactPhone = "+255788112233"
        )

        val postResult = withTimeout(DEFAULT_TIMEOUT_MS) { repository.postJob(job) }
        if (postResult.isFailure) throw postResult.exceptionOrNull()!!
        assertTrue(postResult.isSuccess)
        val jobId = postResult.getOrThrow()
        assertTrue(jobId.isNotEmpty())

        val jobsList = withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.observeAllJobs().first { list -> list.any { it.title == "Fundi Bomba wa Haraka" } }
        }
        assertTrue(jobsList.any { it.title == "Fundi Bomba wa Haraka" })
    }

    @Test
    fun submitApplication_authenticatedUser_succeeds() = runBlocking {
        val uniqueBob = "bob_${System.nanoTime()}@kazihub.tz"
        val uid = signInTestUser(uniqueBob)
        val repository = FirestoreKaziRepository(firestore)

        val application = JobApplicationFirestore(
            applicantId = uid,
            jobId = "test_job_123",
            jobTitle = "Fundi Bomba",
            employerName = "Serengeti Lodge",
            applicantName = "Bob Mwita",
            applicantPhone = "+255755112233",
            proposalNote = "Niko tayari kuanza kazi mara moja"
        )

        val appResult = withTimeout(DEFAULT_TIMEOUT_MS) { repository.submitApplication(application) }
        assertTrue(appResult.isSuccess)

        val myApps = withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.observeUserApplications(uid).first { it.isNotEmpty() }
        }
        assertTrue(myApps.any { it.applicantName == "Bob Mwita" })
    }

    @Test
    fun observeUserApplications_unauthenticatedUser_failsWithPermissionDenied() = runBlocking {
        auth.signOut()
        val repository = FirestoreKaziRepository(firestore)

        try {
            withTimeout(DEFAULT_TIMEOUT_MS) {
                repository.observeUserApplications("unauth_user").first()
            }
            fail("Expected PERMISSION_DENIED for unauthenticated call")
        } catch (e: Throwable) {
            var cause: Throwable? = e
            var firestoreEx: FirebaseFirestoreException? = null
            while (cause != null) {
                if (cause is FirebaseFirestoreException) {
                    firestoreEx = cause
                    break
                }
                cause = cause.cause
            }
            assertNotNull("Expected FirebaseFirestoreException in cause chain: $e", firestoreEx)
            assertEquals(FirebaseFirestoreException.Code.PERMISSION_DENIED, firestoreEx?.code)
        }
    }

    private companion object {
        const val ALICE_EMAIL = "alice@kazihub.tz"
        const val BOB_EMAIL = "bob@kazihub.tz"
        const val DEFAULT_TIMEOUT_MS = 8000L
    }
}
