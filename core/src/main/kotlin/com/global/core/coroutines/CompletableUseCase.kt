package com.up.core.coroutines

import com.up.core.extension.LiveCompletable
import com.up.core.extension.postCancel
import com.up.core.extension.postComplete
import com.up.core.extension.postLoading
import com.up.core.extension.postThrowable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext

abstract class CompletableUseCase<Q>(
    override val backgroundContext: CoroutineContext,
    override val foregroundContext: CoroutineContext
) : BaseUseCase<Q, LiveCompletable>(
    backgroundContext, foregroundContext
) {
    protected abstract suspend fun executeOnBackground(params: Q)

    override fun execute(liveData: LiveCompletable, params: Q) {
        CoroutineScope(foregroundContext + newJob()).launch {
            liveData.postLoading()

            runCatching {
                withContext(backgroundContext) { executeOnBackground(params) }
            }.onSuccess {
                liveData.postComplete()
            }.onFailure { throwable ->
                when (throwable) {
                    is CancellationException -> liveData.postCancel()
                    else -> liveData.postThrowable(throwable)
                }
            }
        }
    }
}
