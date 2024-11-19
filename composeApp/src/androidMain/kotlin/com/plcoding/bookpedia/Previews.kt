package com.plcoding.bookpedia

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreen
import com.plcoding.bookpedia.book.presentation.book_list.BookListState

private val previewBooks = (1..100).map {
    Book(
        id = "$it",
        title = "Book $it",
        imageUrl = "https://test.com",
        authors = listOf("PL"),
        description = "Description $it",
        languages = listOf(),
        firstPublishedYear = null,
        averageRating = 4.67854,
        ratingCount = 5,
        numPages = it,
        numEditions = 3

    )
}

@Preview
@Composable
private fun BookListScreenPreview() {
    BookListScreen(
        state = BookListState(
            searchResults = previewBooks
        ),
        onAction = {}
    )
}