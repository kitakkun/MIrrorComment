package com.github.kitakkun.mirrorcomment.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

class IOScope : CoroutineScope {
    override val coroutineContext = kotlinx.coroutines.Dispatchers.IO + Job()
}
