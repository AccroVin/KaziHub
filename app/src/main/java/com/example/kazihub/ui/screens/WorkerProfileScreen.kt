package com.example.kazihub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kazihub.data.model.WorkerProfile
import com.example.kazihub.ui.components.SkillChip
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
fun WorkerProfileScreen(
    profile: WorkerProfile?,
    language: AppLanguage,
    onSaveProfile: (WorkerProfile) -> Unit,
    onAddSkill: (String) -> Unit,
    onRemoveSkill: (String) -> Unit,
    onOpenPro: () -> Unit
) {
    val formatter = remember { NumberFormat.getNumberInstance(Locale.US) }
    val currentProfile = profile ?: WorkerProfile()

    var fullName by remember(currentProfile) { mutableStateOf(currentProfile.fullName) }
    var phoneNumber by remember(currentProfile) { mutableStateOf(currentProfile.phoneNumber) }
    var region by remember(currentProfile) { mutableStateOf(currentProfile.region) }
    var district by remember(currentProfile) { mutableStateOf(currentProfile.district) }
    var primaryTrade by remember(currentProfile) { mutableStateOf(currentProfile.primaryTrade) }
    var expectedRateTsh by remember(currentProfile) { mutableStateOf(currentProfile.expectedRateTsh.toString()) }
    var ratePeriod by remember(currentProfile) { mutableStateOf(currentProfile.ratePeriod) }
    var yearsExp by remember(currentProfile) { mutableFloatStateOf(currentProfile.yearsExperience.toFloat()) }
    var bio by remember(currentProfile) { mutableStateOf(currentProfile.bio) }

    var newSkillInput by remember { mutableStateOf("") }
    var regionDropdownExpanded by remember { mutableStateOf(false) }
    var tradeDropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("worker_profile_screen")
    ) {
        // User Profile Header Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    Surface(
                        modifier = Modifier.size(80.dp),
                        shape = CircleShape,
                        color = KaziGreenLight
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Avatar",
                                modifier = Modifier.size(46.dp),
                                tint = KaziGreenPrimary
                            )
                        }
                    }
                    if (currentProfile.isVerifiedPro) {
                        Surface(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(26.dp),
                            shape = CircleShape,
                            color = KaziGoldAccent
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.Verified,
                                    contentDescription = "Verified Pro",
                                    tint = Color.Black,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = fullName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = primaryTrade,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = KaziGreenPrimary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = "Rating", tint = KaziGoldAccent, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text("${currentProfile.rating} / 5.0", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Text("•", color = MaterialTheme.colorScheme.outline)

                    Text(
                        text = "${currentProfile.completedJobs} ${if (language == AppLanguage.SWAHILI) "Kazi Zilizokamilika" else "Jobs Completed"}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (!currentProfile.isVerifiedPro) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = KaziGoldLight,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOpenPro() }
                            .testTag("profile_upgrade_pro_banner")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = "Pro", tint = Color(0xFFB45309), modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (language == AppLanguage.SWAHILI) "Pata Beji ya PRO (TSh 3,000)" else "Get Verified PRO Badge",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFB45309)
                                )
                            }
                            Text(
                                text = if (language == AppLanguage.SWAHILI) "Jiunge >" else "Join >",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB45309)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Skills Management Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = LanguageStrings.get("my_skills_title", language),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (language == AppLanguage.SWAHILI)
                        "Orodhesha ujuzi wote uliosomea au kuufanyia kazi ili mfumo ukuunganishe na kazi zinazokufaa:"
                    else
                        "List all skills you have mastered to help employers find and match with you:",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Current skills list
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    currentProfile.skills.forEach { skill ->
                        SkillChip(
                            skill = skill,
                            isHighlighted = true,
                            onDelete = { onRemoveSkill(skill) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Add Custom Skill input
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newSkillInput,
                        onValueChange = { newSkillInput = it },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("add_custom_skill_input"),
                        placeholder = {
                            Text(
                                if (language == AppLanguage.SWAHILI) "Andika ujuzi mpya (mf. CCTV)..." else "Enter new skill...",
                                fontSize = 12.sp
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (newSkillInput.isNotBlank()) {
                                onAddSkill(newSkillInput.trim())
                                newSkillInput = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = KaziGreenPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .testTag("add_skill_btn")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Suggested Skills for Selected Trade
                val suggestions = TanzaniaData.SKILL_CATALOG[primaryTrade] ?: emptyList()
                val unselectedSuggestions = suggestions.filter { !currentProfile.skills.contains(it) }
                if (unselectedSuggestions.isNotEmpty()) {
                    Text(
                        text = if (language == AppLanguage.SWAHILI) "Ujuzi Unaoendana na Fani Yako (Bofya kuongeza):" else "Suggested for your trade (Click to add):",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        unselectedSuggestions.take(4).forEach { sug ->
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier
                                    .clickable { onAddSkill(sug) }
                                    .testTag("suggested_skill_${sug.take(5)}")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(12.dp), tint = KaziGreenPrimary)
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(sug, fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Personal Details & Rates Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = if (language == AppLanguage.SWAHILI) "Taarifa Zako na Viwango vya Kazi" else "Details & Expected Rates",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Full Name
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text(if (language == AppLanguage.SWAHILI) "Jina Kamili" else "Full Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("profile_name_input"),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Phone
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text(if (language == AppLanguage.SWAHILI) "Namba ya Simu / WhatsApp" else "Phone / WhatsApp Number") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("profile_phone_input"),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Trade Dropdown
                Box {
                    OutlinedButton(
                        onClick = { tradeDropdownExpanded = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "${if (language == AppLanguage.SWAHILI) "Fani Kuu:" else "Trade:"} $primaryTrade",
                            fontSize = 13.sp
                        )
                    }
                    DropdownMenu(
                        expanded = tradeDropdownExpanded,
                        onDismissRequest = { tradeDropdownExpanded = false }
                    ) {
                        TanzaniaData.CATEGORIES.filter { !it.startsWith("Zote") }.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat, fontSize = 13.sp) },
                                onClick = {
                                    primaryTrade = cat
                                    tradeDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Region & District
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedButton(
                            onClick = { regionDropdownExpanded = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(region, fontSize = 12.sp)
                        }
                        DropdownMenu(
                            expanded = regionDropdownExpanded,
                            onDismissRequest = { regionDropdownExpanded = false }
                        ) {
                            TanzaniaData.REGIONS.filter { !it.startsWith("Zote") }.forEach { reg ->
                                DropdownMenuItem(
                                    text = { Text(reg, fontSize = 12.sp) },
                                    onClick = {
                                        region = reg
                                        regionDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = district,
                        onValueChange = { district = it },
                        label = { Text(if (language == AppLanguage.SWAHILI) "Wilaya / Mtaa" else "District") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Experience Slider
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(LanguageStrings.get("years_exp", language), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        Text("${yearsExp.toInt()} ${if (language == AppLanguage.SWAHILI) "Miaka" else "Years"}", fontWeight = FontWeight.Bold, color = KaziGreenPrimary)
                    }
                    Slider(
                        value = yearsExp,
                        onValueChange = { yearsExp = it },
                        valueRange = 0f..25f,
                        steps = 24,
                        colors = SliderDefaults.colors(
                            thumbColor = KaziGreenPrimary,
                            activeTrackColor = KaziGreenPrimary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Expected Rate in TSh
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = expectedRateTsh,
                        onValueChange = { expectedRateTsh = it },
                        label = { Text(if (language == AppLanguage.SWAHILI) "Kiwango cha Malipo (TSh)" else "Rate (TSh)") },
                        modifier = Modifier
                            .weight(1.3f)
                            .testTag("profile_rate_input"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = ratePeriod,
                        onValueChange = { ratePeriod = it },
                        label = { Text(if (language == AppLanguage.SWAHILI) "Muda" else "Frequency") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Bio
                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    label = { Text(if (language == AppLanguage.SWAHILI) "Maelezo ya Kazi Zako (Bio)" else "Work Bio / Summary") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .testTag("profile_bio_input"),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Save Profile Button
                Button(
                    onClick = {
                        val parsedRate = expectedRateTsh.toIntOrNull() ?: currentProfile.expectedRateTsh
                        val updated = currentProfile.copy(
                            fullName = fullName,
                            phoneNumber = phoneNumber,
                            region = region,
                            district = district,
                            primaryTrade = primaryTrade,
                            yearsExperience = yearsExp.toInt(),
                            expectedRateTsh = parsedRate,
                            ratePeriod = ratePeriod,
                            bio = bio
                        )
                        onSaveProfile(updated)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = KaziGreenPrimary),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("save_profile_button")
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Save")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = LanguageStrings.get("save_profile", language),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}
