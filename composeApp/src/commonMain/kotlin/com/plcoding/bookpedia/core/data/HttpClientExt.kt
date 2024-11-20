package com.plcoding.bookpedia.core.data

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Outcome
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse,
): Outcome<T, DataError.Remote> {
    val response = try {
        execute()
    } catch (e: SocketTimeoutException) {
        return Outcome.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (e: UnresolvedAddressException) {
        return Outcome.Error(DataError.Remote.NO_INTERNET)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Outcome.Error(DataError.Remote.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse,
): Outcome<T, DataError.Remote> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                Outcome.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Outcome.Error(DataError.Remote.SERIALIZATION)
            }
        }

        408 -> Outcome.Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> Outcome.Error(DataError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> Outcome.Error(DataError.Remote.SERVER)
        else -> Outcome.Error(DataError.Remote.UNKNOWN)
    }
}