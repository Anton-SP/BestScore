package com.bestscore.core.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {
    fun ui(): CoroutineDispatcher

    fun io(): CoroutineDispatcher
}