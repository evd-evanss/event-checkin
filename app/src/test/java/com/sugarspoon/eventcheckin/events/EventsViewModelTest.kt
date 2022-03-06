package com.sugarspoon.eventcheckin.events

import androidx.lifecycle.Observer
import com.sugarspoon.data.model.entity.CustomerEntity
import com.sugarspoon.data.model.entity.EventEntity
import com.sugarspoon.data.repositories.EventRepository
import com.sugarspoon.eventcheckin.base.BaseViewModelTest
import com.sugarspoon.eventcheckin.base.emittedOnce
import com.sugarspoon.eventcheckin.ui.events.EventsIntent
import com.sugarspoon.eventcheckin.ui.events.EventsState
import com.sugarspoon.eventcheckin.ui.events.EventsViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class EventsViewModelTest : BaseViewModelTest() {

    private lateinit var viewModel: EventsViewModel

    @MockK
    private lateinit var state: Observer<EventsState>

    @RelaxedMockK
    private lateinit var repository: EventRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
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
        coEvery { repository.getEvents() } returns flowOf(eventsResponse)

        viewModel.handle(EventsIntent.LoadEvents)

        state emittedOnce EventsState.DisplayShimmer(isLoading = true)
        state emittedOnce EventsState.UpdateData(eventsResponse)
        state emittedOnce EventsState.DisplayShimmer(isLoading = false)
    }

    @Test
    fun `should load events when click in try again button`() = runBlockingTest {
        val eventsResponse = getFakeEventsList()
        coEvery { repository.getEvents() } returns flowOf(eventsResponse)

        viewModel.handle(EventsIntent.TryAgain)

        state emittedOnce EventsState.DisplayShimmer(isLoading = true)
        state emittedOnce EventsState.UpdateData(eventsResponse)
        state emittedOnce EventsState.DisplayShimmer(isLoading = false)
    }

    @Test
    fun `should open details when click in event`() {
        val eventResponse = response
        val eventId = "1"

        coEvery { repository.getEventDetail(eventId) } returns flowOf(eventResponse)

        viewModel.handle(EventsIntent.GetDetailsById(eventId))

        state emittedOnce EventsState.DisplayLoading(isLoading = true)
        state emittedOnce EventsState.OpenDetail(eventResponse)
        state emittedOnce EventsState.DisplayLoading(isLoading = false)
    }

    private val response = EventEntity(
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
            events.add(response)
        }
        return events
    }
}