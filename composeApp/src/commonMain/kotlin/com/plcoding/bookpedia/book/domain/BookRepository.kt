package com.plcoding.bookpedia.book.domain

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Outcome

interface BookRepository {
    suspend fun searchBooks(query: String): Outcome<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): Outcome<String?, DataError>
}