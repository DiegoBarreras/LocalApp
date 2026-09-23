package com.example.holamundo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.holamundo.ui.theme.HolaMundoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HolaMundoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen() {
    var input by remember { mutableStateOf("0") }
    var firstNumber by remember { mutableStateOf<Double?>(null) }
    var operation by remember { mutableStateOf<String?>(null) }
    var resetInputOnNextType by remember { mutableStateOf(false) }

    val buttons = listOf(
        "C", "⌫", "/", "*",
        "7", "8", "9", "-",
        "4", "5", "6", "+",
        "1", "2", "3", "=",
        "0", "."
    )

    fun onButtonClick(btn: String) {
        when (btn) {
            "C" -> {
                input = "0"
                firstNumber = null
                operation = null
                resetInputOnNextType = false
            }
            "⌫" -> {
                if (input.length > 1 && input != "Error: Div/0") {
                    input = input.dropLast(1)
                } else {
                    input = "0"
                }
            }
            "+", "-", "*", "/" -> {
                val currentVal = input.toDoubleOrNull()
                if (currentVal != null) {
                    firstNumber = currentVal
                    operation = btn
                    resetInputOnNextType = true
                }
            }
            "=" -> {
                val secondNumber = input.toDoubleOrNull()
                if (firstNumber != null && secondNumber != null && operation != null) {
                    val result = when (operation) {
                        "+" -> firstNumber!! + secondNumber
                        "-" -> firstNumber!! - secondNumber
                        "*" -> firstNumber!! * secondNumber
                        "/" -> {
                            if (secondNumber == 0.0) Double.NaN else firstNumber!! / secondNumber
                        }
                        else -> secondNumber
                    }

                    input = if (result.isNaN()) {
                        "Error: Div/0"
                    } else if (result == result.toInt().toDouble()) {
                        result.toInt().toString()
                    } else {
                        result.toString()
                    }
                    firstNumber = null
                    operation = null
                    resetInputOnNextType = true
                }
            }
            "." -> {
                if (resetInputOnNextType) {
                    input = "0."
                    resetInputOnNextType = false
                } else if (!input.contains(".")) {
                    input += "."
                }
            }
            else -> { // Números del 0 al 9
                if (input == "0" || input == "Error: Div/0" || resetInputOnNextType) {
                    input = btn
                    resetInputOnNextType = false
                } else {
                    input += btn
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        // Pantalla de resultados / visor
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Text(
                text = input,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.End,
                maxLines = 1
            )
        }

        // Rejilla de botones (GridLayout equivalente)
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(buttons) { btn ->
                Button(
                    onClick = { onButtonClick(btn) },
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when (btn) {
                            "C", "⌫" -> MaterialTheme.colorScheme.errorContainer
                            "/", "*", "-", "+", "=" -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.secondaryContainer
                        },
                        contentColor = when (btn) {
                            "C", "⌫" -> MaterialTheme.colorScheme.onErrorContainer
                            "/", "*", "-", "+", "=" -> MaterialTheme.colorScheme.onPrimary
                            else -> MaterialTheme.colorScheme.onSecondaryContainer
                        }
                    )
                ) {
                    Text(text = btn, fontSize = 24.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    HolaMundoTheme {
        CalculatorScreen()
    }
}