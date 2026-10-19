package com.example.kazihub.ui.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kazihub.ui.components.SkillChip
import com.example.ui.theme.KaziGreenLight
import com.example.ui.theme.KaziGreenPrimary
import com.example.kazihub.util.AppLanguage
import com.example.kazihub.util.LanguageStrings
import com.example.kazihub.util.TanzaniaData

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PostJobScreen(
    language: AppLanguage,
    onPostJob: (
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
    ) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var employer by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(TanzaniaData.CATEGORIES[1]) }
    var skillInput by remember { mutableStateOf("") }
    var skillsList by remember { mutableStateOf(listOf("Uzoefu wa Kazi", "Nidhamu")) }
    var region by remember { mutableStateOf("Dar es Salaam") }
    var district by remember { mutableStateOf("Kinondoni") }
    var salaryTsh by remember { mutableStateOf("500000") }
    var period by remember { mutableStateOf("Kwa Mwezi") }
    var jobType by remember { mutableStateOf("Kazi ya Kudumu / Full-time") }
    var description by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("+255 ") }
    var whatsapp by remember { mutableStateOf("+255 ") }

    var categoryDropdownExpanded by remember { mutableStateOf(false) }
    var regionDropdownExpanded by remember { mutableStateOf(false) }
    var jobTypeDropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("post_job_screen")
    ) {
        // Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = KaziGreenLight)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.PostAdd,
                    contentDescription = "Post Job",
                    tint = KaziGreenPrimary,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = LanguageStrings.get("post_job_title", language),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = KaziGreenPrimary
                    )
                    Text(
                        text = LanguageStrings.get("post_job_subtitle", language),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Job Title
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(LanguageStrings.get("job_title_input", language)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("post_title_input"),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Employer / Business
                OutlinedTextField(
                    value = employer,
                    onValueChange = { employer = it },
                    label = { Text(LanguageStrings.get("company_input", language)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("post_employer_input"),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Category selector
                Box {
                    OutlinedButton(
                        onClick = { categoryDropdownExpanded = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("${LanguageStrings.get("category_select", language)}: $category", fontSize = 13.sp)
                    }
                    DropdownMenu(
                        expanded = categoryDropdownExpanded,
                        onDismissRequest = { categoryDropdownExpanded = false }
                    ) {
                        TanzaniaData.CATEGORIES.filter { !it.startsWith("Zote") }.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat, fontSize = 13.sp) },
                                onClick = {
                                    category = cat
                                    categoryDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Required Skills Tag Input
                Text(
                    text = LanguageStrings.get("required_skills", language),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    skillsList.forEach { skill ->
                        SkillChip(
                            skill = skill,
                            isHighlighted = true,
                            onDelete = { skillsList = skillsList.filter { it != skill } }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = skillInput,
                        onValueChange = { skillInput = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text(if (language == AppLanguage.SWAHILI) "Ujuzi unaohitajika..." else "Required skill...") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (skillInput.isNotBlank() && !skillsList.contains(skillInput.trim())) {
                                skillsList = skillsList + skillInput.trim()
                                skillInput = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = KaziGreenPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
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
                        label = { Text(if (language == AppLanguage.SWAHILI) "Wilaya" else "District") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Salary & Period
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = salaryTsh,
                        onValueChange = { salaryTsh = it },
                        label = { Text(LanguageStrings.get("salary_input", language)) },
                        modifier = Modifier
                            .weight(1.3f)
                            .testTag("post_salary_input"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = period,
                        onValueChange = { period = it },
                        label = { Text(if (language == AppLanguage.SWAHILI) "Mzunguko" else "Period") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Description
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(if (language == AppLanguage.SWAHILI) "Maelezo ya Kazi & Majukumu" else "Description & Duties") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .testTag("post_description_input"),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Contact Phone & WhatsApp
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text(if (language == AppLanguage.SWAHILI) "Simu ya Kupiga" else "Phone Call") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("post_phone_input"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = whatsapp,
                        onValueChange = { whatsapp = it },
                        label = { Text("WhatsApp") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("post_whatsapp_input"),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Publish Button
                Button(
                    onClick = {
                        val parsedSalary = salaryTsh.toIntOrNull() ?: 500000
                        val finalTitle = title.ifBlank { "Fundi / Mtaalamu wa Kazi" }
                        val finalEmployer = employer.ifBlank { "Mwajiri Binafsi" }
                        val finalDesc = description.ifBlank { "Nafasi ya kazi kwa mafundi au wataalamu wenye uzoefu." }
                        val finalPhone = phone.ifBlank { "+255714000000" }
                        val finalWhatsapp = whatsapp.ifBlank { "+255714000000" }

                        onPostJob(
                            finalTitle,
                            finalEmployer,
                            category,
                            skillsList,
                            region,
                            district,
                            parsedSalary,
                            period,
                            jobType,
                            finalDesc,
                            finalPhone,
                            finalWhatsapp
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = KaziGreenPrimary),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("publish_job_button")
                ) {
                    Icon(Icons.Default.PostAdd, contentDescription = "Publish")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = LanguageStrings.get("publish_button", language),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}
