package com.currency.converter

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineDispatchersRule(
    val dispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestRule {

    override fun apply(base: Statement, description: Description?) = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            Mockito.mockStatic(Dispatchers::class.java, Mockito.CALLS_REAL_METHODS).use { mocked ->
                mocked.`when`<CoroutineDispatcher> { Dispatchers.Default }.thenReturn(dispatcher)
                mocked.`when`<CoroutineDispatcher> { Dispatchers.IO }.thenReturn(dispatcher)

                // Replace with UnconfinedTestDispatcher after resolve https://github.com/cashapp/turbine/issues/112
                mocked.`when`<CoroutineDispatcher> { Dispatchers.Unconfined }.thenReturn(TestCoroutineDispatcher(dispatcher.scheduler))

                Dispatchers.setMain(dispatcher)
                base.evaluate()
                Dispatchers.resetMain()
            }
        }
    }
}
