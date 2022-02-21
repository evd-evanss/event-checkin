package com.sugarspoon.eventcheckin.ui.events

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sugarspoon.data.model.entity.EventEntity
import com.sugarspoon.eventcheckin.databinding.ActivityEventsBinding
import com.sugarspoon.eventcheckin.ui.base.BaseActivity
import com.sugarspoon.eventcheckin.ui.details.DetailsActivity.Companion.onDetailsIntent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsActivity : BaseActivity<ActivityEventsBinding>(ActivityEventsBinding::inflate) {

    private val viewModel: EventsViewModel by viewModels ()

    private val eventsAdapter by lazy {
        EventsAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUi()
        setupStates()
        setupListeners()
        viewModel.handle(EventsIntent.LoadEvents)
    }

    private fun setupUi() = with(binding) {
        eventListRv.apply {
            setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))
            setAdapter(eventsAdapter)
            addVeiledItems(10)
        }
    }

    private fun setupStates() = viewModel.state.observe(this) { state ->
        when(state) {
            is EventsState.UpdateData -> updateData(state.data)
            is EventsState.DisplayShimmer -> displayShimmer(state.isLoading)
            is EventsState.DisplayLoading -> displayLoading(state.isLoading)
            is EventsState.OpenDetail -> openDetail(state.eventDetail)
            is EventsState.DisplayError -> displayError(state.error)
        }
    }

    private fun setupListeners() {
        eventsAdapter.seeMoreClick = { id ->
            viewModel.handle(EventsIntent.GetDetailsById(id = id))
        }
    }

    private fun updateData(data: List<EventEntity>) {
        eventsAdapter.list = data
    }

    private fun openDetail(eventDetails: EventEntity) {
        startActivity(onDetailsIntent(eventDetails))
    }

    private fun displayShimmer(isLoading: Boolean) = with(binding) {
        if (isLoading) {
            eventListRv.veil()
        } else {
            eventListRv.unVeil()
        }
    }

    private fun displayLoading(isLoading: Boolean) = with(binding) {
        if (isLoading) {
            eventLoadingPb.visibility = View.VISIBLE
        } else {
            eventLoadingPb.visibility = View.GONE
        }
    }

    private fun displayError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}