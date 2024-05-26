package com.superx.heroes.feature.auth.presentation.components

import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superx.heroes.R
import com.superx.heroes.feature.core.ui.theme.ubuntuFontFamily
import com.superx.heroes.util.isValidEmail
import com.superx.heroes.util.isValidPassword


@Composable
fun LoginComponent(
    signInWithEmailAndPassword: (email: String, password: String) -> Unit,
    signInWithGoogle: () -> Unit,
    signInWithFacebook: () -> Unit,
    signUp: () -> Unit,
) {
    Column {
        Spacer(modifier = Modifier.weight(0.8f))

        LoginForm(
            onLoginClicked = signInWithEmailAndPassword,
            onSignUpClicked = signUp
        )

        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.secondary,
            text = "OR CONNECT WITH",
            fontFamily = ubuntuFontFamily
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SignInButton(
                resource = R.drawable.ic_google_logo,
            ) {
                signInWithGoogle()
            }

            SignInButton(
                resource = R.drawable.ic_facebook_logo,
            ) {
                signInWithFacebook()
            }
        }
    }
}


@Composable
fun LoginForm(
    onLoginClicked: (email: String, password: String) -> Unit,
    onSignUpClicked: () -> Unit,
) {
    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var outlinedEmailText by remember { mutableStateOf("Email") }
    var outlinedPasswordText by remember { mutableStateOf("Password") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            maxLines = 1,
            value = email,
            onValueChange = {
                email = it
                emailError = false
                outlinedEmailText = "Email"
            },
            isError = emailError,
            label = { Text(outlinedEmailText) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            maxLines = 1,
            value = password,
            onValueChange = {
                password = it
                passwordError = false
                outlinedPasswordText = "Password"
            },
            label = { Text(outlinedPasswordText) },
            isError = passwordError,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                emailError = !isValidEmail(email.text)
                passwordError = !isValidPassword(password.text)

                if (emailError){
                    outlinedEmailText = "Email is not valid"
                }

                if (passwordError){
                    outlinedPasswordText = "Password must contain 6 or more characters"
                }

                if (!emailError && !passwordError) {
                    onLoginClicked(email.text, password.text)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Log In", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .clickable {
                    onSignUpClicked()
                },
            text = "Sign Up",
            textDecoration = TextDecoration.Underline,
            fontFamily = ubuntuFontFamily,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}