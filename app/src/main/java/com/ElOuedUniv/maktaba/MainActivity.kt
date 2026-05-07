package com.ElOuedUniv.maktaba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ElOuedUniv.maktaba.data.repository.OnboardingRepository
import com.ElOuedUniv.maktaba.presentation.navigation.NavGraph
import com.ElOuedUniv.maktaba.presentation.navigation.Screen
import com.ElOuedUniv.maktaba.presentation.theme.MaktabaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var onboardingRepository: OnboardingRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaktabaTheme {
                val hasCompleted by onboardingRepository.hasCompletedOnboarding
                    .collectAsState(initial = false)

                val startDestination = if (hasCompleted) {
                    Screen.BookList.route
                } else {
                    Screen.Onboarding.route
                }

                NavGraph(startDestination = startDestination)
            }
        }
    }
}