package com.superx.heroes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.superx.heroes.feature.core.ui.theme.SuperxTheme
import com.superx.heroes.navigation.AppNavigation
import com.superx.heroes.util.Constants.backPressInterval
import com.superx.heroes.util.Constants.backPressedTime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    @Inject
    lateinit var analytics: FirebaseAnalytics

    @Inject
    lateinit var onActivityResultFlow: MutableSharedFlow<Pair<Int, Intent?>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupBackPressHandler()

        setContent {
            SuperxTheme {
                AppNavigation()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lifecycleScope.launch {
            onActivityResultFlow.emit(Pair(requestCode, data))
        }
    }

    private fun setupBackPressHandler() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressedTime + backPressInterval > System.currentTimeMillis()) {
                    // Exit the app
                    finishAffinity()
                    exitProcess(0)
                } else {
                    Toast.makeText(applicationContext, getString(R.string.press_back_again_to_exit), Toast.LENGTH_SHORT).show()
                    backPressedTime = System.currentTimeMillis()
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
}