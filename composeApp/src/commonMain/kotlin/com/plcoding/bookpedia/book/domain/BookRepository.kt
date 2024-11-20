package com.plcoding.bookpedia.book.domain

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyOutcome
import com.plcoding.bookpedia.core.domain.Outcome
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(query: String): Outcome<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): Outcome<String?, DataError>

    fun getFavoriteBooks(): Flow<List<Book>>
    fun isBookFavorite(id: String): Flow<Boolean>
    suspend fun markAsFavorite(book: Book): EmptyOutcome<DataError.Local>
    suspend fun deleteFromFavorites(id: String)
}