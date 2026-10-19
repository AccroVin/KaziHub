package com.example.kazihub.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kazihub.data.local.KaziHubDatabase
import com.example.kazihub.data.model.JobApplication
import com.example.kazihub.data.model.JobListing
import com.example.kazihub.data.model.SubscriptionRecord
import com.example.kazihub.data.model.WorkerProfile
import com.example.kazihub.data.repository.KaziHubRepository
import com.example.kazihub.util.AppLanguage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class Screen {
    JOBS,
    PROFILE,
    POST_JOB,
    PRO,
    JOB_DETAIL
}

sealed class PaymentUiState {
    object Idle : PaymentUiState()
    data class ProcessingUssd(val carrier: String, val secondsRemaining: Int) : PaymentUiState()
    data class Success(val record: SubscriptionRecord) : PaymentUiState()
    data class Error(val message: String) : PaymentUiState()
}

data class FilterCriteria(
    val query: String,
    val category: String,
    val region: String,
    val matchSkills: Boolean
)

class KaziHubViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: KaziHubRepository

    val workerProfile: StateFlow<WorkerProfile?>
    private val _allJobs = MutableStateFlow<List<JobListing>>(emptyList())
    val activeSubscription: StateFlow<SubscriptionRecord?>
    val applications: StateFlow<List<JobApplication>>

    private val _currentScreen = MutableStateFlow(Screen.JOBS)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _screenBackStack = mutableListOf<Screen>()

    private val _selectedJob = MutableStateFlow<JobListing?>(null)
    val selectedJob: StateFlow<JobListing?> = _selectedJob.asStateFlow()

    private val _language = MutableStateFlow(AppLanguage.SWAHILI)
    val language: StateFlow<AppLanguage> = _language.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Zote (All)")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _selectedRegion = MutableStateFlow("Zote (All Tanzania)")
    val selectedRegion: StateFlow<String> = _selectedRegion.asStateFlow()

    private val _matchMySkillsOnly = MutableStateFlow(false)
    val matchMySkillsOnly: StateFlow<Boolean> = _matchMySkillsOnly.asStateFlow()

    private val _paymentState = MutableStateFlow<PaymentUiState>(PaymentUiState.Idle)
    val paymentState: StateFlow<PaymentUiState> = _paymentState.asStateFlow()

    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    init {
        val db = KaziHubDatabase.getDatabase(application)
        repository = KaziHubRepository(db)

        workerProfile = repository.workerProfile.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            null
        )

        activeSubscription = repository.activeSubscription.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            null
        )

        applications = repository.applications.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )

        viewModelScope.launch {
            repository.initializeDatabaseIfEmpty()
            repository.allJobs.collect {
                _allJobs.value = it
            }
        }
    }

    private val filterCriteria = combine(
        _searchQuery,
        _selectedCategory,
        _selectedRegion,
        _matchMySkillsOnly
    ) { q, cat, reg, match ->
        FilterCriteria(q, cat, reg, match)
    }

    val filteredJobs: StateFlow<List<JobListing>> = combine(
        _allJobs,
        filterCriteria,
        workerProfile
    ) { jobs, criteria, profile ->
        jobs.filter { job ->
            val matchesQuery = criteria.query.isBlank() ||
                job.title.contains(criteria.query, ignoreCase = true) ||
                job.description.contains(criteria.query, ignoreCase = true) ||
                job.requiredSkills.any { it.contains(criteria.query, ignoreCase = true) } ||
                job.district.contains(criteria.query, ignoreCase = true) ||
                job.region.contains(criteria.query, ignoreCase = true)

            val matchesCategory = criteria.category.startsWith("Zote") ||
                job.category.equals(criteria.category, ignoreCase = true) ||
                criteria.category.contains(job.category, ignoreCase = true)

            val matchesRegion = criteria.region.startsWith("Zote") ||
                job.region.equals(criteria.region, ignoreCase = true)

            val matchesSkills = if (!criteria.matchSkills || profile == null) {
                true
            } else {
                val profileSkillsLower = profile.skills.map { it.lowercase().trim() }
                job.requiredSkills.any { req ->
                    profileSkillsLower.any { userSkill ->
                        req.lowercase().contains(userSkill) || userSkill.contains(req.lowercase())
                    }
                }
            }

            matchesQuery && matchesCategory && matchesRegion && matchesSkills
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun navigateTo(screen: Screen) {
        if (_currentScreen.value != screen) {
            _screenBackStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun handleBack(): Boolean {
        if (_screenBackStack.isNotEmpty()) {
            _currentScreen.value = _screenBackStack.removeAt(_screenBackStack.size - 1)
            return true
        }
        return false
    }

    fun viewJobDetails(job: JobListing) {
        _selectedJob.value = job
        navigateTo(Screen.JOB_DETAIL)
    }

    fun toggleLanguage() {
        _language.value = if (_language.value == AppLanguage.SWAHILI) AppLanguage.ENGLISH else AppLanguage.SWAHILI
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun selectRegion(region: String) {
        _selectedRegion.value = region
    }

    fun toggleMatchMySkills() {
        _matchMySkillsOnly.value = !_matchMySkillsOnly.value
    }

    fun toggleSaveJob(job: JobListing) {
        viewModelScope.launch {
            repository.toggleSaveJob(job.id, !job.isSaved)
        }
    }

    fun updateProfile(profile: WorkerProfile) {
        viewModelScope.launch {
            repository.updateProfile(profile)
            _userMessage.value = if (_language.value == AppLanguage.SWAHILI)
                "Wasifu wako umehifadhiwa kikamilifu!" else "Profile saved successfully!"
        }
    }

    fun addSkill(newSkill: String) {
        val current = workerProfile.value ?: return
        if (newSkill.isNotBlank() && !current.skills.contains(newSkill.trim())) {
            val updated = current.copy(skills = current.skills + newSkill.trim())
            updateProfile(updated)
        }
    }

    fun removeSkill(skill: String) {
        val current = workerProfile.value ?: return
        val updated = current.copy(skills = current.skills.filter { it != skill })
        updateProfile(updated)
    }

    fun postNewJob(
        title: String,
        employer: String,
        category: String,
        skills: List<String>,
        region: String,
        district: String,
        salaryTsh: Int,
        period: String,
        jobType: String,
        description: String,
        phone: String,
        whatsapp: String
    ) {
        viewModelScope.launch {
            val newJob = JobListing(
                title = title,
                employerName = employer,
                category = category,
                requiredSkills = skills,
                region = region,
                district = district,
                salaryTsh = salaryTsh,
                salaryPeriod = period,
                jobType = jobType,
                description = description,
                contactPhone = phone,
                contactWhatsapp = whatsapp,
                isUrgent = false,
                isFeatured = true,
                postedDate = "Hivi punde"
            )
            repository.postJob(newJob)
            _userMessage.value = if (_language.value == AppLanguage.SWAHILI)
                "Nafasi ya kazi imechapishwa kikamilifu!" else "Job vacancy published successfully!"
            _currentScreen.value = Screen.JOBS
        }
    }

    fun applyForJob(job: JobListing, coverNote: String) {
        viewModelScope.launch {
            val profile = workerProfile.value
            repository.applyForJob(
                jobId = job.id,
                jobTitle = job.title,
                employerName = job.employerName,
                proposal = coverNote
            )
            _userMessage.value = if (_language.value == AppLanguage.SWAHILI)
                "Maombi yako yametumwa kwa ${job.employerName}!" else "Application sent to ${job.employerName}!"
        }
    }

    fun simulateMobileMoneySubscription(
        planType: String,
        priceTsh: Int,
        carrier: String,
        phoneNumber: String
    ) {
        viewModelScope.launch {
            _paymentState.value = PaymentUiState.ProcessingUssd(carrier, 3)
            delay(1200)
            _paymentState.value = PaymentUiState.ProcessingUssd(carrier, 2)
            delay(1200)
            _paymentState.value = PaymentUiState.ProcessingUssd(carrier, 1)
            delay(1000)

            val record = repository.activateSubscription(planType, priceTsh, carrier, phoneNumber)
            _paymentState.value = PaymentUiState.Success(record)
            _userMessage.value = if (_language.value == AppLanguage.SWAHILI)
                "Hongera! KaziHub Pro imewashwa kwa namba $phoneNumber"
            else
                "Congratulations! KaziHub Pro activated for $phoneNumber"
        }
    }

    fun dismissPaymentState() {
        _paymentState.value = PaymentUiState.Idle
    }

    fun clearUserMessage() {
        _userMessage.value = null
    }
}
