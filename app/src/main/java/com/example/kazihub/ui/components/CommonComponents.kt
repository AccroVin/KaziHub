package com.example.kazihub.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kazihub.ui.Screen
import com.example.ui.theme.KaziBlueAccent
import com.example.ui.theme.KaziGoldAccent
import com.example.ui.theme.KaziGoldLight
import com.example.ui.theme.KaziGreenLight
import com.example.ui.theme.KaziGreenPrimary
import com.example.kazihub.util.AppLanguage
import com.example.kazihub.util.LanguageStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KaziHubTopBar(
    language: AppLanguage,
    isProUser: Boolean,
    onToggleLanguage: () -> Unit,
    onOpenPro: () -> Unit
) {
    Surface(
        shadowElevation = 3.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column {
            // Tanzanian national accent band
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            ) {
                Box(modifier = Modifier.weight(1f).height(4.dp).background(KaziGreenPrimary))
                Box(modifier = Modifier.weight(0.3f).height(4.dp).background(KaziGoldAccent))
                Box(modifier = Modifier.weight(0.8f).height(4.dp).background(Color(0xFF0F172A)))
                Box(modifier = Modifier.weight(0.3f).height(4.dp).background(KaziGoldAccent))
                Box(modifier = Modifier.weight(1f).height(4.dp).background(KaziBlueAccent))
            }

            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Kazi",
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp,
                            color = KaziGreenPrimary
                        )
                        Text(
                            text = "Hub",
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp,
                            color = KaziGoldAccent
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "TZ",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = KaziGreenPrimary,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                },
                navigationIcon = {
                    FilterChip(
                        selected = false,
                        onClick = onToggleLanguage,
                        label = {
                            Text(
                                text = if (language == AppLanguage.SWAHILI) "EN" else "SW",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Language,
                                contentDescription = "Language",
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .testTag("language_toggle_button"),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                },
                actions = {
                    if (isProUser) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = KaziGoldAccent,
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .clickable { onOpenPro() }
                                .testTag("pro_badge_header")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = "Pro",
                                    tint = Color.Black,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "PRO",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 11.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    } else {
                        Button(
                            onClick = onOpenPro,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = KaziGoldAccent,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .height(32.dp)
                                .testTag("upgrade_pro_button")
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Pro",
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Pro",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    }
}

@Composable
fun KaziHubBottomNavigation(
    currentScreen: Screen,
    language: AppLanguage,
    onNavigate: (Screen) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    ) {
        val jobsLabel = LanguageStrings.get("tab_jobs", language)
        val profileLabel = LanguageStrings.get("tab_profile", language)
        val postLabel = LanguageStrings.get("tab_post", language)
        val proLabel = LanguageStrings.get("tab_pro", language)

        NavigationBarItem(
            selected = currentScreen == Screen.JOBS || currentScreen == Screen.JOB_DETAIL,
            onClick = { onNavigate(Screen.JOBS) },
            icon = { Icon(Icons.Default.Work, contentDescription = jobsLabel) },
            label = { Text(jobsLabel, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = KaziGreenPrimary,
                selectedTextColor = KaziGreenPrimary,
                indicatorColor = KaziGreenLight
            ),
            modifier = Modifier.testTag("nav_jobs_tab")
        )

        NavigationBarItem(
            selected = currentScreen == Screen.PROFILE,
            onClick = { onNavigate(Screen.PROFILE) },
            icon = { Icon(Icons.Default.Person, contentDescription = profileLabel) },
            label = { Text(profileLabel, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = KaziGreenPrimary,
                selectedTextColor = KaziGreenPrimary,
                indicatorColor = KaziGreenLight
            ),
            modifier = Modifier.testTag("nav_profile_tab")
        )

        NavigationBarItem(
            selected = currentScreen == Screen.POST_JOB,
            onClick = { onNavigate(Screen.POST_JOB) },
            icon = { Icon(Icons.Default.PostAdd, contentDescription = postLabel) },
            label = { Text(postLabel, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = KaziGreenPrimary,
                selectedTextColor = KaziGreenPrimary,
                indicatorColor = KaziGreenLight
            ),
            modifier = Modifier.testTag("nav_post_job_tab")
        )

        NavigationBarItem(
            selected = currentScreen == Screen.PRO,
            onClick = { onNavigate(Screen.PRO) },
            icon = {
                BadgedBox(
                    badge = {
                        Badge(containerColor = KaziGoldAccent) {
                            Text("TSh", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        }
                    }
                ) {
                    Icon(Icons.Default.Star, contentDescription = proLabel)
                }
            },
            label = { Text(proLabel, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = KaziGoldAccent,
                selectedTextColor = KaziGoldAccent,
                indicatorColor = KaziGoldLight
            ),
            modifier = Modifier.testTag("nav_pro_tab")
        )
    }
}

@Composable
fun SkillChip(
    skill: String,
    isHighlighted: Boolean = false,
    onDelete: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isHighlighted) KaziGreenLight else MaterialTheme.colorScheme.surfaceVariant,
        border = if (isHighlighted) androidx.compose.foundation.BorderStroke(1.dp, KaziGreenPrimary) else null,
        modifier = Modifier
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .testTag("skill_chip_${skill.replace(" ", "_")}")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Text(
                text = skill,
                fontSize = 12.sp,
                fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Medium,
                color = if (isHighlighted) KaziGreenPrimary else MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (onDelete != null) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Remove skill",
                    modifier = Modifier
                        .size(14.dp)
                        .clickable { onDelete() },
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

fun launchWhatsApp(context: Context, phoneNumber: String, message: String) {
    try {
        val cleanPhone = phoneNumber.replace("+", "").replace(" ", "").replace("-", "")
        val url = "https://api.whatsapp.com/send?phone=$cleanPhone&text=${Uri.encode(message)}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "WhatsApp haipatikani kwenye kifaa hiki / WhatsApp not available", Toast.LENGTH_SHORT).show()
    }
}

fun launchPhoneCall(context: Context, phoneNumber: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Imeshindikana kufungua kipiga simu", Toast.LENGTH_SHORT).show()
    }
}
