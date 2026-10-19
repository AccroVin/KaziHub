package com.example.kazihub.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.kazihub.data.model.JobListing
import com.example.kazihub.data.model.WorkerProfile
import com.example.kazihub.ui.components.SkillChip
import com.example.kazihub.ui.components.launchWhatsApp
import com.example.ui.theme.KaziGoldAccent
import com.example.ui.theme.KaziGoldLight
import com.example.ui.theme.KaziGreenLight
import com.example.ui.theme.KaziGreenPrimary
import com.example.kazihub.util.AppLanguage
import com.example.kazihub.util.LanguageStrings
import com.example.kazihub.util.TanzaniaData
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobFeedScreen(
    jobs: List<JobListing>,
    workerProfile: WorkerProfile?,
    language: AppLanguage,
    searchQuery: String,
    selectedCategory: String,
    selectedRegion: String,
    matchMySkillsOnly: Boolean,
    onSearchChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onRegionChange: (String) -> Unit,
    onToggleMatchSkills: () -> Unit,
    onSelectJob: (JobListing) -> Unit,
    onToggleSave: (JobListing) -> Unit,
    onOpenPro: () -> Unit
) {
    val context = LocalContext.current
    var regionDropdownExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("jobs_feed_list")
    ) {
        // Hero Visual Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .testTag("hero_banner_card"),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.kazihub_hero),
                        contentDescription = "KaziHub Banner",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    // Dark gradient scrim
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color(0xCC003D20)
                                    )
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Surface(
                            color = KaziGoldAccent,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = if (language == AppLanguage.SWAHILI) "Ajira & Ujuzi Tanzania" else "Tanzania Skills & Jobs",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (language == AppLanguage.SWAHILI)
                                "Unganishwa na Kazi za Ujuzi Wako"
                            else
                                "Get Hired for Skills You Have Mastered",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = if (language == AppLanguage.SWAHILI)
                                "Mafundi, Wataalamu & Vibaruani nchi nzima"
                            else
                                "Artisans, Technicians & Pros nationwide",
                            color = Color(0xFFE2E8F0),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Search Bar & Region Picker
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("job_search_input"),
                    placeholder = {
                        Text(
                            LanguageStrings.get("search_placeholder", language),
                            fontSize = 13.sp
                        )
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = KaziGreenPrimary)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchChange("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear search")
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = KaziGreenPrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Region selector button & Skill Match switch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box {
                        OutlinedButton(
                            onClick = { regionDropdownExpanded = true },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("region_filter_button")
                        ) {
                            Icon(Icons.Default.LocationOn, contentDescription = "Region", modifier = Modifier.size(16.dp), tint = KaziGreenPrimary)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = selectedRegion,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        DropdownMenu(
                            expanded = regionDropdownExpanded,
                            onDismissRequest = { regionDropdownExpanded = false }
                        ) {
                            TanzaniaData.REGIONS.forEach { reg ->
                                DropdownMenuItem(
                                    text = { Text(reg, fontSize = 13.sp) },
                                    onClick = {
                                        onRegionChange(reg)
                                        regionDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Count of available vacancies
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = "${jobs.size} ${if (language == AppLanguage.SWAHILI) "Kazi" else "Jobs"}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = KaziGreenPrimary,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Skills Matching Toggle Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .testTag("skill_match_toggle_card"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (matchMySkillsOnly) KaziGreenLight else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = if (matchMySkillsOnly) KaziGreenPrimary else MaterialTheme.colorScheme.outline
                        ) {
                            Icon(
                                Icons.Default.Verified,
                                contentDescription = "Matched Skills",
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(6.dp)
                                    .size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = LanguageStrings.get("match_my_skills", language),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (matchMySkillsOnly) KaziGreenPrimary else MaterialTheme.colorScheme.onSurface
                            )
                            if (workerProfile != null) {
                                Text(
                                    text = "${workerProfile.skills.size} ${if (language == AppLanguage.SWAHILI) "ujuzi wako unatumika" else "registered skills matched"}",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                    Switch(
                        checked = matchMySkillsOnly,
                        onCheckedChange = { onToggleMatchSkills() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = KaziGreenPrimary
                        ),
                        modifier = Modifier.testTag("skill_match_switch")
                    )
                }
            }
        }

        // Categories Horizontal Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TanzaniaData.CATEGORIES.forEach { category ->
                    val isSelected = selectedCategory == category
                    FilterChip(
                        selected = isSelected,
                        onClick = { onCategoryChange(category) },
                        label = {
                            Text(
                                text = category.split("(")[0].trim(),
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = KaziGreenPrimary,
                            selectedLabelColor = Color.White,
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            }
        }

        // Empty State or Job Cards
        if (jobs.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.WorkOutline,
                            contentDescription = "Empty",
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.outline
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = LanguageStrings.get("empty_jobs", language),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                onSearchChange("")
                                onCategoryChange("Zote (All)")
                                onRegionChange("Zote (All Tanzania)")
                                if (matchMySkillsOnly) onToggleMatchSkills()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = KaziGreenPrimary)
                        ) {
                            Text(if (language == AppLanguage.SWAHILI) "Onyesha Kazi Zote" else "Show All Jobs")
                        }
                    }
                }
            }
        } else {
            items(jobs, key = { it.id }) { job ->
                JobCardItem(
                    job = job,
                    userSkills = workerProfile?.skills ?: emptyList(),
                    language = language,
                    onSelect = { onSelectJob(job) },
                    onToggleSave = { onToggleSave(job) },
                    onWhatsApp = {
                        val message = if (language == AppLanguage.SWAHILI)
                            "Habari ${job.employerName}, nimeona tangazo lako la '${job.title}' kwenye KaziHub App. Nina ujuzi unaohitajika, ningependa kuwasilisha maombi yangu."
                        else
                            "Hello ${job.employerName}, I saw your vacancy for '${job.title}' on KaziHub App. I have the required skills and would like to apply."
                        launchWhatsApp(context, job.contactWhatsapp, message)
                    }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobCardItem(
    job: JobListing,
    userSkills: List<String>,
    language: AppLanguage,
    onSelect: () -> Unit,
    onToggleSave: () -> Unit,
    onWhatsApp: () -> Unit
) {
    val formatter = remember { NumberFormat.getNumberInstance(Locale.US) }
    val userSkillsLower = remember(userSkills) { userSkills.map { it.lowercase().trim() } }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onSelect() }
            .testTag("job_card_${job.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Badges row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (job.isUrgent) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFEF4444)
                        ) {
                            Text(
                                text = LanguageStrings.get("urgent", language),
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    if (job.isFeatured) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = KaziGoldAccent
                        ) {
                            Text(
                                text = LanguageStrings.get("featured", language),
                                color = Color.Black,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = job.jobType.split("/")[0].trim(),
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                IconButton(
                    onClick = onToggleSave,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (job.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Save",
                        tint = if (job.isSaved) KaziGreenPrimary else MaterialTheme.colorScheme.outline
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = job.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Employer & Location
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = job.employerName,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = KaziGreenPrimary
                )
                Text(
                    text = " • ",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.outline
                )
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = "Location",
                    modifier = Modifier.size(14.dp),
                    tint = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "${job.district}, ${job.region}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Required Skills
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                job.requiredSkills.forEach { skill ->
                    val isMatch = userSkillsLower.any {
                        skill.lowercase().contains(it) || it.contains(skill.lowercase())
                    }
                    SkillChip(
                        skill = skill,
                        isHighlighted = isMatch
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Salary & Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = LanguageStrings.get("salary_label", language),
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = "TSh ${formatter.format(job.salaryTsh)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KaziGreenPrimary
                    )
                    Text(
                        text = job.salaryPeriod,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = onWhatsApp,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .height(36.dp)
                            .testTag("whatsapp_btn_${job.id}")
                    ) {
                        Text(
                            text = "WhatsApp",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF25D366)
                        )
                    }

                    Button(
                        onClick = onSelect,
                        colors = ButtonDefaults.buttonColors(containerColor = KaziGreenPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .height(36.dp)
                            .testTag("view_job_btn_${job.id}")
                    ) {
                        Text(
                            text = if (language == AppLanguage.SWAHILI) "Maelezo" else "Details",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
