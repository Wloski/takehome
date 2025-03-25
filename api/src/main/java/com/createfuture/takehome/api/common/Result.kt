package com.createfuture.takehome.api.common

sealed interface Result<out R> {
    data class Success<out R>(val data: R) : Result<R>
    data class Loading<out R>(val data: R? = null) : Result<R>
    data class Failure<out R>(val error: Exception, val data: R? = null) : Result<R>
    object None : Result<Nothing>
}