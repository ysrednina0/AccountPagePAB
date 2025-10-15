package com.example.createaccount

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.createaccount.ui.theme.CreateAccountTheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CreateAccountTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CreateAccountPage()
                }
            }
        }
    }
}

@Composable
fun CreateAccountPage() {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val genders = listOf("Male", "Female")
    var selectedGender by remember { mutableStateOf(genders.first()) }
    var agreed by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val isDarkMode = isSystemInDarkTheme()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = if (isDarkMode) {

                        listOf(
                            Color(0xFF0A1A2F),
                            MaterialTheme.colorScheme.background
                        )
                    } else {

                        listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                            MaterialTheme.colorScheme.background
                        )
                    }
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Create Account",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 40.dp, bottom = 24.dp)
        )

        // First & Last Name
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Gender selection
        Text(
            text = "Gender",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            genders.forEach { option ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedGender == option,
                        onClick = { selectedGender = option }
                    )
                    Text(option, modifier = Modifier.padding(end = 12.dp),  color = MaterialTheme.colorScheme.onBackground)
                }
            }
        }

        // Checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Checkbox(checked = agreed, onCheckedChange = { agreed = it })
            Text(
                text = "I agree to the Terms and Conditions",
                modifier = Modifier.padding(start = 4.dp),
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        // Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    firstName = ""
                    lastName = ""
                    username = ""
                    password = ""
                    selectedGender = genders.first()
                    agreed = false
                },
                modifier = Modifier
                    .width(140.dp)
                    .height(44.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Clear")
            }

            Button(
                onClick = {
                    if (agreed) {
                        Toast.makeText(
                            context,
                            "Account Created:\nName: $firstName $lastName\nUsername: $username\nGender: $selectedGender",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Please agree to Terms and Conditions",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .width(140.dp)
                    .height(44.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Submit")
            }
        }
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CreateAccountLightPreview() {
    CreateAccountTheme {
        CreateAccountPage()
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CreateAccountDarkPreview() {
    CreateAccountTheme {
        CreateAccountPage()
    }
}
