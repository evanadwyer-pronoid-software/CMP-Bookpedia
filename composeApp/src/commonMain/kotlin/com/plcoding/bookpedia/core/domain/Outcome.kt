package com.plcoding.bookpedia.core.domain

sealed interface Outcome<out D, out E: Error> {
    data class Success<out D>(val data: D): Outcome<D, Nothing>
    data class Error<out E: com.plcoding.bookpedia.core.domain.Error>(val error: E):
        Outcome<Nothing, E>
}

inline fun <T, E: Error, R> Outcome<T, E>.map(map: (T) -> R): Outcome<R, E> {
    return when(this) {
        is Outcome.Error -> Outcome.Error(error)
        is Outcome.Success -> Outcome.Success(map(data))
    }
}

fun <T, E: Error> Outcome<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

inline fun <T, E: Error> Outcome<T, E>.onSuccess(action: (T) -> Unit): Outcome<T, E> {
    return when(this) {
        is Outcome.Error -> this
        is Outcome.Success -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: Error> Outcome<T, E>.onError(action: (E) -> Unit): Outcome<T, E> {
    return when(this) {
        is Outcome.Error -> {
            action(error)
            this
        }
        is Outcome.Success -> this
    }
}

typealias EmptyResult<E> = Outcome<Unit, E>