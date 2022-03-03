package com.sugarspoon.eventcheckin.details

import androidx.lifecycle.Observer
import com.sugarspoon.data.model.entity.CustomerEntity
import com.sugarspoon.data.model.entity.EventShareEntity
import com.sugarspoon.eventcheckin.base.BaseViewModelTest
import com.sugarspoon.eventcheckin.base.emittedOnce
import com.sugarspoon.eventcheckin.repositoryfake.Repository
import com.sugarspoon.eventcheckin.ui.details.DetailsIntent
import com.sugarspoon.eventcheckin.ui.details.DetailsState
import com.sugarspoon.eventcheckin.ui.details.DetailsViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class DetailsViewModelTest : BaseViewModelTest() {

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

        coEvery { repository.checkin(customer) } returns flowOf(Any())

        viewModel.handle(DetailsIntent.SetCheckin(customer))

        state emittedOnce DetailsState.DisplayLoading(isLoading = true)
        state emittedOnce DetailsState.DisplayLoading(isLoading = false)
        state emittedOnce DetailsState.DisplaySuccess
    }

    @Test
    fun `should share event on click in button share`() {
        //given
        val event = EventShareEntity(
            content = "Lorem Ipsum is simply dummy text"
        )

        //when
        viewModel.handle(DetailsIntent.ShareEvent(event))

        //then
        state emittedOnce DetailsState.ShareEvent(event.content)
    }

    private fun getFakeCustomer() = CustomerEntity(
        eventId = "1",
        name = "Evandro Costa",
        email = "revandro77@yahoo.com.br"
    )
}