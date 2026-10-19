package com.example.kazihub.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.kazihub.ui.components.KaziHubBottomNavigation
import com.example.kazihub.ui.components.KaziHubTopBar
import com.example.kazihub.ui.screens.JobDetailScreen
import com.example.kazihub.ui.screens.JobFeedScreen
import com.example.kazihub.ui.screens.KaziHubProScreen
import com.example.kazihub.ui.screens.PostJobScreen
import com.example.kazihub.ui.screens.WorkerProfileScreen

@Composable
fun KaziHubApp(viewModel: KaziHubViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val language by viewModel.language.collectAsState()
    val workerProfile by viewModel.workerProfile.collectAsState()
    val filteredJobs by viewModel.filteredJobs.collectAsState()
    val selectedJob by viewModel.selectedJob.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val selectedRegion by viewModel.selectedRegion.collectAsState()
    val matchMySkillsOnly by viewModel.matchMySkillsOnly.collectAsState()
    val activeSubscription by viewModel.activeSubscription.collectAsState()
    val paymentState by viewModel.paymentState.collectAsState()
    val userMessage by viewModel.userMessage.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(userMessage) {
        userMessage?.let {
            snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
            viewModel.clearUserMessage()
        }
    }

    BackHandler(enabled = currentScreen != Screen.JOBS) {
        viewModel.handleBack()
    }

    Scaffold(
        topBar = {
            if (currentScreen != Screen.JOB_DETAIL) {
                KaziHubTopBar(
                    language = language,
                    isProUser = workerProfile?.isVerifiedPro == true || activeSubscription?.isActive == true,
                    onToggleLanguage = { viewModel.toggleLanguage() },
                    onOpenPro = { viewModel.navigateTo(Screen.PRO) }
                )
            }
        },
        bottomBar = {
            if (currentScreen != Screen.JOB_DETAIL) {
                KaziHubBottomNavigation(
                    currentScreen = currentScreen,
                    language = language,
                    onNavigate = { screen -> viewModel.navigateTo(screen) }
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "screen_transition"
            ) { screen ->
                when (screen) {
                    Screen.JOBS -> {
                        JobFeedScreen(
                            jobs = filteredJobs,
                            workerProfile = workerProfile,
                            language = language,
                            searchQuery = searchQuery,
                            selectedCategory = selectedCategory,
                            selectedRegion = selectedRegion,
                            matchMySkillsOnly = matchMySkillsOnly,
                            onSearchChange = { viewModel.setSearchQuery(it) },
                            onCategoryChange = { viewModel.selectCategory(it) },
                            onRegionChange = { viewModel.selectRegion(it) },
                            onToggleMatchSkills = { viewModel.toggleMatchMySkills() },
                            onSelectJob = { viewModel.viewJobDetails(it) },
                            onToggleSave = { viewModel.toggleSaveJob(it) },
                            onOpenPro = { viewModel.navigateTo(Screen.PRO) }
                        )
                    }

                    Screen.PROFILE -> {
                        WorkerProfileScreen(
                            profile = workerProfile,
                            language = language,
                            onSaveProfile = { viewModel.updateProfile(it) },
                            onAddSkill = { viewModel.addSkill(it) },
                            onRemoveSkill = { viewModel.removeSkill(it) },
                            onOpenPro = { viewModel.navigateTo(Screen.PRO) }
                        )
                    }

                    Screen.POST_JOB -> {
                        PostJobScreen(
                            language = language,
                            onPostJob = { title, employer, category, skills, region, district, salary, period, type, desc, phone, whatsapp ->
                                viewModel.postNewJob(
                                    title, employer, category, skills, region, district,
                                    salary, period, type, desc, phone, whatsapp
                                )
                            }
                        )
                    }

                    Screen.PRO -> {
                        KaziHubProScreen(
                            workerProfile = workerProfile,
                            activeSubscription = activeSubscription,
                            paymentState = paymentState,
                            language = language,
                            onStartPayment = { plan, price, carrier, phone ->
                                viewModel.simulateMobileMoneySubscription(plan, price, carrier, phone)
                            },
                            onDismissPayment = { viewModel.dismissPaymentState() }
                        )
                    }

                    Screen.JOB_DETAIL -> {
                        selectedJob?.let { job ->
                            JobDetailScreen(
                                job = job,
                                workerProfile = workerProfile,
                                language = language,
                                onBack = { viewModel.handleBack() },
                                onApply = { note ->
                                    viewModel.applyForJob(job, note)
                                }
                            )
                        } ?: run {
                            viewModel.navigateTo(Screen.JOBS)
                        }
                    }
                }
            }
        }
    }
}
