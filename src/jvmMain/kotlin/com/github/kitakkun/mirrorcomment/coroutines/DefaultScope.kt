package com.github.kitakkun.mirrorcomment.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DefaultScope : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + Job()

}
