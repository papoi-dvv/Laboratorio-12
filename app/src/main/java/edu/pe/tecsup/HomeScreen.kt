package edu.pe.tecsup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(onLogout: () -> Unit)
{
    val auth = FirebaseAuth.getInstance()
    val email = auth.currentUser?.email ?: "Usuario"

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Bienvenido/a ", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            Text("Sesión iniciada como: $email")
            Spacer(Modifier.height(8.dp))
            Button(onClick = {
                auth.signOut()
                onLogout()
            },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar Sesión")
            }
        }
    }
}