package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kazihub.ui.KaziHubApp
import com.example.kazihub.ui.KaziHubViewModel
import com.example.ui.theme.KaziHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KaziHubTheme {
                val viewModel: KaziHubViewModel = viewModel()
                KaziHubApp(viewModel = viewModel)
            }
        }
    }
}
