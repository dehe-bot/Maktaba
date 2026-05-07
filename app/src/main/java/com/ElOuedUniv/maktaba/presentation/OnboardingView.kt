package com.ElOuedUniv.maktaba.presentation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import com.ElOuedUniv.maktaba.data.local.PreferenceManager

@Composable
fun OnboardingView(
    navController: NavController,
    preferenceManager: PreferenceManager
) {
    val coroutineScope = rememberCoroutineScope()

    // تصميم الشاشة هنا (Column, Text, Image...)

    Button(
        onClick = {
            coroutineScope.launch {
                // حفظ الحالة في الهاتف
                preferenceManager.saveOnboardingState(true)

                // الانتقال لقائمة الكتب وحذف شاشة الترحيب من "الذاكرة"
                navController.navigate("book_list") {
                    popUpTo("onboarding") { inclusive = true }
                }
            }
        }
    ) {
        Text("ابدأ الآن")
    }
}