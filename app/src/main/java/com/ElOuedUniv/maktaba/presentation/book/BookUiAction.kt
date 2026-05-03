package com.ElOuedUniv.maktaba.presentation.book

import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.presentation.book.detail.BookDetailUiAction

/**
 * UI Actions representing user interactions on the Book screen.
 * TODO: Student must implement and use these actions in the ViewModel.
 */
sealed interface BookUiAction {
    object RefreshBooks : BookUiAction
    object OnAddBookClick : BookUiAction
    object OnDismissAddBook : BookUiAction

    data class OnAddBookConfirm(val title: String, val isbn: String, val nbPages: Int) : BookUiAction


    }
