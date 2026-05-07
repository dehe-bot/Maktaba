package com.ElOuedUniv.maktaba.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ElOuedUniv.maktaba.presentation.book.BookListView
import com.ElOuedUniv.maktaba.presentation.book.BookViewModel
import com.ElOuedUniv.maktaba.presentation.book.add.AddBookView
import com.ElOuedUniv.maktaba.presentation.book.detail.BookDetailView
import com.ElOuedUniv.maktaba.presentation.category.CategoryListView
import com.ElOuedUniv.maktaba.presentation.onboarding.OnboardingView

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Onboarding.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingView(
                onNavigateToLibrary = {
                    navController.navigate(Screen.BookList.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.BookList.route) {
            // Shared ViewModel so AddBook can trigger refresh on BookList
            val bookViewModel: BookViewModel = hiltViewModel()
            BookListView(
                onCategoriesClick = { navController.navigate(Screen.CategoryList.route) },
                onAddBookClick = { navController.navigate(Screen.AddBook.route) },
                onBookClick = { isbn ->
                    navController.navigate(Screen.BookDetail.createRoute(isbn))
                },
                viewModel = bookViewModel
            )
        }

        composable(
            route = Screen.BookDetail.route,
            arguments = listOf(
                navArgument("isbn") { type = NavType.StringType }
            )
        ) {
            BookDetailView(onBackClick = { navController.popBackStack() })
        }

        composable(Screen.CategoryList.route) {
            CategoryListView(onBackClick = { navController.popBackStack() })
        }

        composable(Screen.AddBook.route) {
            AddBookView(
                onBackClick = {
                    navController.popBackStack()
                    // Refresh book list after returning
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("refresh", true)
                }
            )
        }
    }
}