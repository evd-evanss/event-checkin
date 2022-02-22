package com.sugarspoon.eventcheckin.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sugarspoon.data.model.entity.CustomerEntity
import com.sugarspoon.eventcheckin.repositoryfake.Repository
import com.sugarspoon.eventcheckin.ui.details.DetailsIntent
import com.sugarspoon.eventcheckin.ui.details.DetailsState
import com.sugarspoon.eventcheckin.ui.details.DetailsViewModel
import com.sugarspoon.rules.CoroutineTestRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DetailsViewModelTest {
    
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailsViewModel

    @MockK
    private lateinit var state: Observer<DetailsState>

    @RelaxedMockK
    private lateinit var repository: Repository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = spyk(Repository())
        viewModel = DetailsViewModel(repository)
        state = spyk<Observer<DetailsState>>()
        viewModel.state.observeForever(state)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should make ckeck-in on click checkin button`() = runBlockingTest {
        val customer = getFakeCustomer()

        every { repository.checkin(customer) } returns flowOf(Any())

        viewModel.handle(DetailsIntent.SetCheckin(customer))

        verify(atLeast = 1) { state.onChanged(DetailsState.DisplayLoading(isLoading = true)) }
        verify(atLeast = 1) { state.onChanged(DetailsState.DisplayLoading(isLoading = false)) }
        verify(atLeast = 1) { state.onChanged(DetailsState.DisplaySuccess) }
    }

    private fun getFakeCustomer() = CustomerEntity(
        eventId = "1",
        name = "Evandro Costa",
        email = "revandro77@yahoo.com.br"
    )
}