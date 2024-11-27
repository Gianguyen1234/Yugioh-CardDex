package com.example.ygocarddex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ygocarddex.navigation.NavGraph
import com.example.ygocarddex.screens.CardListScreen
import com.example.ygocarddex.ui.theme.YGOCardDexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YGOCardDexTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    YugiohApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun YugiohApp(modifier: Modifier = Modifier) {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            NavGraph()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YGOCardDexTheme {
        YugiohApp()
    }
}