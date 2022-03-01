package com.sugarspoon.eventcheckin.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.sugarspoon.data.model.entity.CustomerEntity
import com.sugarspoon.rules.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
open class BaseViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

}

infix fun <T> Observer<T>.emitted(value: T) {
    if (this.javaClass.simpleName.contains("MockitoMock")) {
        verify(this).onChanged(value)
    } else {
        io.mockk.verify { onChanged(value) }
    }
}

infix fun <T> Observer<T>.notEmitted(value: T) {
    if (this.javaClass.simpleName.contains("MockitoMock")) {
        verify(this, never()).onChanged(value)
    } else {
        io.mockk.verify(exactly = 0) { onChanged(value) }
    }
}

infix fun <T> Observer<T>.emittedOnce(value: T) {
    if (this.javaClass.simpleName.contains("MockitoMock")) {
        verify(this, times(1)).onChanged(value)
    } else {
        io.mockk.verify(exactly = 1) { onChanged(value) }
    }
}