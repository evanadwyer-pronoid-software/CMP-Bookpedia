package com.plcoding.bookpedia.book.data.networking

import com.plcoding.bookpedia.book.data.dto.BookWorkDto
import com.plcoding.bookpedia.book.data.dto.SearchResponseDto
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Outcome

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null,
    ): Outcome<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(
        bookWorkId: String
    ): Outcome<BookWorkDto, DataError.Remote>
}