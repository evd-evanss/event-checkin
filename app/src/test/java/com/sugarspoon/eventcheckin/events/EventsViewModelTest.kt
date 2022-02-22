package com.sugarspoon.eventcheckin.events

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sugarspoon.data.model.entity.CustomerEntity
import com.sugarspoon.data.model.entity.EventEntity
import com.sugarspoon.data.repositories.EventRepository
import com.sugarspoon.eventcheckin.ui.events.EventsIntent
import com.sugarspoon.eventcheckin.ui.events.EventsState
import com.sugarspoon.eventcheckin.ui.events.EventsViewModel
import com.sugarspoon.rules.CoroutineTestRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
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
class EventsViewModelTest {
    
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: EventsViewModel

    @MockK
    private lateinit var state: Observer<EventsState>

    @RelaxedMockK
    private lateinit var repository: Repository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = spyk(Repository())
        viewModel = EventsViewModel(repository)
        state = spyk<Observer<EventsState>>()
        viewModel.state.observeForever(state)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should load events, display shimmer and hide shimmer when I open the app`() = runBlockingTest {
        val eventsResponse = getFakeEventsList()
        every { repository.getEvents() } returns flowOf(eventsResponse)

        viewModel.handle(EventsIntent.LoadEvents)

        verify(exactly = 1) { state.onChanged(EventsState.DisplayShimmer(isLoading = true)) }
        verify(exactly = 1) { state.onChanged(EventsState.DisplayShimmer(isLoading = false)) }
        verify(exactly = 1) { state.onChanged(EventsState.UpdateData(eventsResponse)) }
    }

    @Test
    fun `should open event details`() = runBlockingTest {
        val eventResponse = getFakeEventResponse()

        every { repository.getEventDetail("1") } returns flowOf(eventResponse)

        viewModel.handle(EventsIntent.GetDetailsById("1"))

        verify(exactly = 1) { state.onChanged(EventsState.DisplayLoading(isLoading = true)) }
        verify(exactly = 1) { state.onChanged(EventsState.DisplayLoading(isLoading = false)) }
        verify(exactly = 1) { state.onChanged(EventsState.OpenDetail(eventResponse)) }
    }

    private fun getFakeEventResponse() = EventEntity(
        date = 1645464356,
        title = "",
        description = "Feira de adoção de animais",
        image = "www.google.com.br/logo.png",
        latitude = -23.6073315,
        longitude = -46.5252506,
        price = 18.90,
        id = "1"
    )

    private fun getFakeEventsList(): List<EventEntity> {
        val events = mutableListOf<EventEntity>()
        repeat(2) {
            events.add(getFakeEventResponse())
        }
        return events
    }
}

class Repository() : EventRepository {

    override fun getEvents() = flow {
        emit(listOf<EventEntity>())
    }

    override fun getEventDetail(id: String) = flow {
        emit(getFakeEventResponse())
    }

    override fun checkin(customerResponse: CustomerEntity) = flow {
        emit(Any())
    }

    private fun getFakeEventResponse() = EventEntity(
        date = 1645464356,
        title = "",
        description = "Feira de adoção de animais",
        image = "www.google.com.br/logo.png",
        latitude = -23.6073315,
        longitude = -46.5252506,
        price = 18.90,
        id = "1"
    )
}