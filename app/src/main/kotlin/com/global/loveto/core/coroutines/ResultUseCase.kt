package com.global.loveto.core.coroutines

import com.global.loveto.core.extension.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class ResultUseCase<Q, T>(
    override val backgroundContext: CoroutineContext,
    override val foregroundContext: CoroutineContext
) : BaseUseCase<Q, LiveResult<T>>(
    backgroundContext, foregroundContext
) {
    protected abstract suspend fun executeOnBackground(params: Q): T?

    override fun execute(liveData: LiveResult<T>, params: Q) {
        CoroutineScope(foregroundContext + newJob()).launch {
            liveData.postLoading()

            runCatching {
                withContext(backgroundContext) { executeOnBackground(params)!! }
            }.onSuccess { response ->
                liveData.postSuccess(response)
            }.onFailure { throwable ->
                when (throwable) {
                    is CancellationException -> liveData.postCancel()
                    is NullPointerException -> liveData.postEmpty()
                    else -> liveData.postThrowable(throwable)
                }
            }
        }
    }
}
