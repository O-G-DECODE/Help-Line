package com.example.helplineapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.helplineapp.ui.components.StylishButton
import com.example.helplineapp.ui.components.StylishTextField

@Composable
fun RegisterScreen(
    onRegisterSuccess: (Long) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var photoUploaded by remember { mutableStateOf(false) }
    var idCardUploaded by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Create Account", 
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Join our mutual help community",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(32.dp))

        StylishTextField(
            value = name,
            onValueChange = { name = it },
            label = "Full Name"
        )
        Spacer(modifier = Modifier.height(16.dp))

        StylishTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email Address",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(16.dp))

        StylishTextField(
            value = phone,
            onValueChange = { phone = it },
            label = "Phone Number",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        Spacer(modifier = Modifier.height(16.dp))

        StylishTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Upload Section
        Text(
            text = "Verification Documents",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Photo Upload Button Mockup
        UploadButton(
            label = "Upload Profile Photo",
            isUploaded = photoUploaded,
            onClick = { photoUploaded = !photoUploaded }
        )
        
        Spacer(modifier = Modifier.height(12.dp))

        // ID Card Upload Button Mockup
        UploadButton(
            label = "Upload Government ID",
            isUploaded = idCardUploaded,
            onClick = { idCardUploaded = !idCardUploaded }
        )

        Spacer(modifier = Modifier.height(40.dp))

        StylishButton(
            text = "Register",
            onClick = { 
                // Placeholder for actual registration logic
                if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && phone.isNotBlank()) {
                    onRegisterSuccess(1L) 
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = onNavigateToLogin) {
            Text(
                "Already have an account? Login",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun UploadButton(label: String, isUploaded: Boolean, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (isUploaded) Color(0xFF2D6A4F) else MaterialTheme.colorScheme.primary
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = if (isUploaded) "$label (Uploaded)" else label)
            Icon(
                imageVector = if (isUploaded) Icons.Default.CheckCircle else Icons.Default.Send,
                contentDescription = null
            )
        }
    }
}

