package com.plcoding.bookpedia.book.data.repository

import com.plcoding.bookpedia.book.data.mappers.toBook
import com.plcoding.bookpedia.book.data.networking.RemoteBookDataSource
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Outcome
import com.plcoding.bookpedia.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
) : BookRepository {

    override suspend fun searchBooks(query: String): Outcome<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map {
                it.results.map { bookDto ->
                    bookDto.toBook()
                }
            }
    }
}