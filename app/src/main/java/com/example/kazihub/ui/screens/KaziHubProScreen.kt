package com.example.kazihub.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kazihub.data.model.SubscriptionRecord
import com.example.kazihub.data.model.WorkerProfile
import com.example.kazihub.ui.PaymentUiState
import com.example.ui.theme.KaziGoldAccent
import com.example.ui.theme.KaziGoldDark
import com.example.ui.theme.KaziGoldLight
import com.example.ui.theme.KaziGreenLight
import com.example.ui.theme.KaziGreenPrimary
import com.example.kazihub.util.AppLanguage
import com.example.kazihub.util.LanguageStrings
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun KaziHubProScreen(
    workerProfile: WorkerProfile?,
    activeSubscription: SubscriptionRecord?,
    paymentState: PaymentUiState,
    language: AppLanguage,
    onStartPayment: (planType: String, priceTsh: Int, carrier: String, phone: String) -> Unit,
    onDismissPayment: () -> Unit
) {
    var selectedPlanType by remember { mutableStateOf("MONTHLY") }
    var showPaymentModal by remember { mutableStateOf(false) }
    var selectedCarrier by remember { mutableStateOf("M-Pesa") }
    var inputPhone by remember { mutableStateOf("+255 754 123 456") }

    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("kazihub_pro_screen")
    ) {
        // Hero Pro Card with Gold Gradient
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF0F2B1D),
                                Color(0xFF005A36),
                                Color(0xFF008751)
                            )
                        )
                    )
                    .padding(22.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Surface(
                        shape = CircleShape,
                        color = KaziGoldAccent,
                        modifier = Modifier.size(54.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Pro Icon",
                                tint = Color.Black,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "KaziHub PRO",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = KaziGoldAccent
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = LanguageStrings.get("pro_subtitle", language),
                        fontSize = 13.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )

                    // If already active, show Active Badge
                    if (activeSubscription != null && activeSubscription.isActive) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = KaziGoldLight
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Verified, contentDescription = "Active", tint = Color(0xFFB45309), modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${if (language == AppLanguage.SWAHILI) "Kifurushi Kinafanya Kazi:" else "Active Plan:"} ${activeSubscription.planType}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFB45309)
                                    )
                                }
                                Text(
                                    text = "${LanguageStrings.get("valid_until", language)} ${dateFormat.format(Date(activeSubscription.expiresAt))}",
                                    fontSize = 11.sp,
                                    color = Color(0xFF78350F)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Subscription Plans (Weekly & Monthly in TSh)
        Text(
            text = if (language == AppLanguage.SWAHILI) "Chagua Kifurushi Chako cha Kazi" else "Choose Your Pro Plan",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Monthly Card (Recommended / Popular)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { selectedPlanType = "MONTHLY" }
                .testTag("plan_monthly_card"),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (selectedPlanType == "MONTHLY") KaziGreenLight else MaterialTheme.colorScheme.surface
            ),
            border = if (selectedPlanType == "MONTHLY") BorderStroke(2.dp, KaziGreenPrimary) else BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = if (selectedPlanType == "MONTHLY") KaziGreenPrimary else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.size(22.dp)
                        ) {
                            if (selectedPlanType == "MONTHLY") {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.Check, contentDescription = "Selected", tint = Color.White, modifier = Modifier.size(14.dp))
                                }
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = LanguageStrings.get("monthly_plan", language),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = KaziGoldAccent
                    ) {
                        Text(
                            text = LanguageStrings.get("popular", language),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "TSh 10,000",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = KaziGreenPrimary
                    )
                    Text(
                        text = if (language == AppLanguage.SWAHILI) " / mwezi mzima (Siku 30)" else " / full month (30 days)",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 2.dp, start = 4.dp)
                    )
                }

                Text(
                    text = if (language == AppLanguage.SWAHILI) "Okoa 25% kulinganisha na kulipia kila wiki" else "Save 25% compared to weekly pass",
                    fontSize = 11.sp,
                    color = Color(0xFF16A34A),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Weekly Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { selectedPlanType = "WEEKLY" }
                .testTag("plan_weekly_card"),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (selectedPlanType == "WEEKLY") KaziGreenLight else MaterialTheme.colorScheme.surface
            ),
            border = if (selectedPlanType == "WEEKLY") BorderStroke(2.dp, KaziGreenPrimary) else BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = if (selectedPlanType == "WEEKLY") KaziGreenPrimary else MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.size(22.dp)
                    ) {
                        if (selectedPlanType == "WEEKLY") {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Check, contentDescription = "Selected", tint = Color.White, modifier = Modifier.size(14.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = LanguageStrings.get("weekly_plan", language),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "TSh 3,000",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = KaziGreenPrimary
                    )
                    Text(
                        text = if (language == AppLanguage.SWAHILI) " / wiki 1 (Siku 7)" else " / 1 week (7 days)",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 2.dp, start = 4.dp)
                    )
                }

                Text(
                    text = if (language == AppLanguage.SWAHILI) "Kifurushi cha majaribio cha bei nafuu" else "Budget-friendly starter pass",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Subscribe Action Button
        Button(
            onClick = { showPaymentModal = true },
            colors = ButtonDefaults.buttonColors(containerColor = KaziGreenPrimary),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("subscribe_mobile_money_button")
        ) {
            Icon(Icons.Default.PhoneAndroid, contentDescription = "Pay")
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${LanguageStrings.get("choose_plan", language)} (${if (selectedPlanType == "WEEKLY") "TSh 3,000" else "TSh 10,000"})",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Perks Checklist
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = if (language == AppLanguage.SWAHILI) "Faida Zote za KaziHub Pro:" else "All KaziHub Pro Benefits:",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(14.dp))

                ProPerkRow(
                    icon = Icons.Default.Verified,
                    tint = KaziGoldAccent,
                    title = if (language == AppLanguage.SWAHILI) "Beji ya Uhakika (Gold Verified Badge)" else "Verified Gold Checkmark Badge",
                    desc = if (language == AppLanguage.SWAHILI) "Waajiri wanawaamini zaidi mafundi na wataalamu wenye beji hii." else "Build instant trust with employers and homeowners."
                )

                Spacer(modifier = Modifier.height(12.dp))

                ProPerkRow(
                    icon = Icons.Default.TrendingUp,
                    tint = KaziGreenPrimary,
                    title = if (language == AppLanguage.SWAHILI) "Onekana Wa Kwanza Kwenye Utafutaji (3x Views)" else "Top Placement in Searches (3x Views)",
                    desc = if (language == AppLanguage.SWAHILI) "Wasifu wako unawekwa juu kabisa mwajiri anapotafuta ujuzi wako." else "Ranked #1 when employers look for your registered skills."
                )

                Spacer(modifier = Modifier.height(12.dp))

                ProPerkRow(
                    icon = Icons.Default.Bolt,
                    tint = Color(0xFF25D366),
                    title = if (language == AppLanguage.SWAHILI) "Wasiliana Moja kwa Moja WhatsApp & Simu" else "Direct WhatsApp & Phone Contact",
                    desc = if (language == AppLanguage.SWAHILI) "Pata namba za waajiri na uwasiliane nao bila vikwazo vyovyote." else "Direct employer phone and WhatsApp numbers without barriers."
                )

                Spacer(modifier = Modifier.height(12.dp))

                ProPerkRow(
                    icon = Icons.Default.NotificationsActive,
                    tint = Color(0xFF0288D1),
                    title = if (language == AppLanguage.SWAHILI) "Taarifa za Haraka Kazi Mpya Zinapowekwa" else "Priority Instant Job Alerts",
                    desc = if (language == AppLanguage.SWAHILI) "Pata ujumbe simuni mara tu kazi ya ujuzi wako inapotangazwa." else "Get notified the second a vacancy matching your skill goes live."
                )
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }

    // Mobile Money Payment Modal
    if (showPaymentModal) {
        val price = if (selectedPlanType == "WEEKLY") 3000 else 10000

        AlertDialog(
            onDismissRequest = { showPaymentModal = false },
            title = {
                Text(
                    text = if (language == AppLanguage.SWAHILI) "Lipia KaziHub Pro kwa Simu" else "Pay via Mobile Money (TSh)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            },
            text = {
                Column {
                    Text(
                        text = "${LanguageStrings.get("salary_label", language)} TSh $price (${if (selectedPlanType == "WEEKLY") "Wiki 1" else "Mwezi 1"})",
                        fontWeight = FontWeight.Bold,
                        color = KaziGreenPrimary,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = LanguageStrings.get("select_network", language),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Tanzanian Carrier Selection
                    val carriers = listOf(
                        Triple("M-Pesa", Color(0xFFE60000), "Vodacom"),
                        Triple("Tigo Pesa", Color(0xFF00377B), "Tigo"),
                        Triple("Airtel Money", Color(0xFFED1C24), "Airtel"),
                        Triple("HaloPesa", Color(0xFFFF6600), "Halotel")
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        carriers.forEach { (name, color, operator) ->
                            val isSel = selectedCarrier == name
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSel) color.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant,
                                border = if (isSel) BorderStroke(2.dp, color) else null,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedCarrier = name }
                                    .testTag("carrier_${name.take(3)}")
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .background(color, CircleShape)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = name,
                                        fontSize = 10.sp,
                                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = inputPhone,
                        onValueChange = { inputPhone = it },
                        label = { Text(LanguageStrings.get("phone_number", language)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("payment_phone_input"),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = LanguageStrings.get("enter_pin_prompt", language),
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(8.dp),
                            lineHeight = 15.sp
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showPaymentModal = false
                        onStartPayment(selectedPlanType, price, selectedCarrier, inputPhone)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = KaziGreenPrimary),
                    modifier = Modifier.testTag("confirm_pay_mobile_money_button")
                ) {
                    Text(LanguageStrings.get("confirm_pay", language), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showPaymentModal = false }) {
                    Text(if (language == AppLanguage.SWAHILI) "Funga" else "Close")
                }
            }
        )
    }

    // Payment Processing USSD Simulation Dialog
    when (val state = paymentState) {
        is PaymentUiState.ProcessingUssd -> {
            AlertDialog(
                onDismissRequest = {},
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(
                            color = KaziGreenPrimary,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 3.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (language == AppLanguage.SWAHILI) "Inatuma Ombi la Simu..." else "Sending USSD Prompt...",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                },
                text = {
                    Column {
                        Text(
                            text = if (language == AppLanguage.SWAHILI)
                                "Ombi la malipo kupitia mtandao wa ${state.carrier} linatumwa kwenye simu yako. Tafadhali thibitisha kwa kuweka PIN yako kwenye simu."
                            else
                                "Payment request via ${state.carrier} is being pushed to your phone. Please confirm by typing your mobile money PIN.",
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                },
                confirmButton = {}
            )
        }
        is PaymentUiState.Success -> {
            AlertDialog(
                onDismissRequest = onDismissPayment,
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            tint = KaziGreenPrimary,
                            modifier = Modifier.size(26.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = LanguageStrings.get("payment_success", language),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = KaziGreenPrimary
                        )
                    }
                },
                text = {
                    Column {
                        Text(
                            text = "${LanguageStrings.get("receipt_id", language)} ${state.record.transactionRef}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${LanguageStrings.get("salary_label", language)} TSh ${state.record.priceTsh} (${state.record.carrier})",
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${LanguageStrings.get("valid_until", language)} ${dateFormat.format(Date(state.record.expiresAt))}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = KaziGoldLight
                        ) {
                            Text(
                                text = if (language == AppLanguage.SWAHILI)
                                    "Beji yako ya Verified Pro imeamilishwa na profile yako imepewa kipaumbele namba 1!"
                                else
                                    "Your Verified Pro badge is active and your profile is now ranked #1 in searches!",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF78350F),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = onDismissPayment,
                        colors = ButtonDefaults.buttonColors(containerColor = KaziGreenPrimary)
                    ) {
                        Text(if (language == AppLanguage.SWAHILI) "Sawa, Endelea" else "Great, Continue")
                    }
                }
            )
        }
        else -> Unit
    }
}

@Composable
fun ProPerkRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    title: String,
    desc: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            shape = CircleShape,
            color = tint.copy(alpha = 0.15f),
            modifier = Modifier.size(34.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = title, tint = tint, modifier = Modifier.size(18.dp))
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = desc,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp
            )
        }
    }
}
