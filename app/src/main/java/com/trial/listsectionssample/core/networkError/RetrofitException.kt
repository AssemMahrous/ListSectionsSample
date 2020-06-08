package com.trial.listsectionssample.core.networkError

import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class RetrofitException(
    private val _message: String?,
    private val _response: Response<*>?,
    private val _kind: Kind,
    private val _exception: Throwable?
) : RuntimeException(_message, _exception) {

    companion object {
        fun httpError(url: String, response: Response<*>, retrofit: Retrofit): RetrofitException {
            val message = response.code().toString() + " " + response.message()
            return RetrofitException(message, response, Kind.HTTP, null)
        }

        fun networkError(exception: IOException): RetrofitException {
            return RetrofitException(exception.message, null, Kind.NETWORK, exception)
        }

        fun unexpectedError(exception: Throwable): RetrofitException {
            return RetrofitException(
                exception.message,
                null,
                Kind.UNEXPECTED,
                exception
            )
        }
    }

    /** Response object containing status code, containers, body, etc. */
    fun getResponse() = _response

    /** The event kind which triggered this error. */
    fun getKind() = _kind

    enum class Kind {
        /** An [IOException] occurred while communicating to the server.  */
        NETWORK,

        /** A non-200 HTTP status code was received from the server.  */
        HTTP,

        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }
}