package com.example.holamundo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                    InteractiveHelloScreen()
                }
            }
        }
    }
}

@Composable
fun InteractiveHelloScreen() {
    // Estados para almacenar el texto ingresado y el saludo final
    var nameInput by remember { mutableStateOf("") }
    var greetingMessage by remember { mutableStateOf("¡Hola, mundo!") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Etiqueta de texto (TextView equivalente)
        Text(
            text = greetingMessage,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de entrada de texto (EditText / TextField equivalente)
        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Escribe tu nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de acción
        Button(
            onClick = {
                if (nameInput.isNotBlank()) {
                    greetingMessage = "¡Hola, $nameInput!"
                } else {
                    greetingMessage = "¡Hola, mundo!"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Saludar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InteractiveHelloPreview() {
    HolaMundoTheme {
        InteractiveHelloScreen()
    }
}