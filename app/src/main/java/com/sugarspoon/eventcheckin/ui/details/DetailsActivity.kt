package com.sugarspoon.eventcheckin.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.sugarspoon.data.model.entity.CustomerEntity
import com.sugarspoon.data.model.entity.EventEntity
import com.sugarspoon.eventcheckin.R
import com.sugarspoon.eventcheckin.databinding.ActivityDetailsBinding
import com.sugarspoon.eventcheckin.ui.base.BaseActivity
import com.sugarspoon.eventcheckin.utils.loadImage
import com.sugarspoon.eventcheckin.utils.getAddress
import com.sugarspoon.eventcheckin.utils.toHtml
import com.sugarspoon.eventcheckin.utils.toLocalDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : BaseActivity<ActivityDetailsBinding>(
    ActivityDetailsBinding::inflate
) {

    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var event: EventEntity

    private val subscribeDialog by lazy {
        SubscribeBottomDialog.create(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        event = intent.extras?.get(EVENT_EXTRA) as EventEntity
        viewModel.handle(DetailsIntent.LoadDetails(eventDetail = event))
        setupState()
        setupListener()
    }

    private fun setupState() = viewModel.state.observe(this) { state ->
        when (state) {
            is DetailsState.LoadEventDetails -> loadEventDetails(state.eventDetail)
            is DetailsState.DismissDialog -> {
                subscribeDialog.dismiss()
                finish()
            }
            is DetailsState.DisplayLoading -> subscribeDialog.displayLoading(state.isLoading)
            is DetailsState.DisplayError -> displayError(state.error)
        }
    }

    private fun setupListener() = with(binding) {
        detailsCheckinBt.setOnClickListener {
            subscribeDialog.show()
        }
        subscribeDialog.subscribeListener = { name, email ->
            viewModel.handle(
                DetailsIntent.SubscribeCustomer(
                    customer = CustomerEntity(
                        eventId = event.id,
                        name = name,
                        email = email
                    )
                )
            )
        }
    }

    private fun loadEventDetails(event: EventEntity) = with(binding) {
        detailsTitleTv.text = event.title
        detailsDescriptionTv.text = event.description
        detailsPictureIv.loadImage(
            context = context,
            url = event.image,
            onLoading = {
                if (it) {
                    detailsShimmerVl.veil()
                } else {
                    detailsShimmerVl.unVeil()
                }
            },
            onSuccess = {

            },
            onError = {
                detailsPictureIv.visibility = View.GONE
            }
        )

        detailsAddressTv.text = getString(
            R.string.address_placeholder,
            getAddress(event.latitude.toDouble(), event.longitude.toDouble())
        ).toHtml()
        detailsDateTv.text = getString(
            R.string.date_placeholder,
            event.date.toLocalDate()
        ).toHtml()
    }

    private fun displayError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        subscribeDialog.clear()
    }

    companion object {
        private const val EVENT_EXTRA = "EVENT_EXTRA"

        fun Context.onDetailsIntent(event: EventEntity): Intent {
            return Intent(this, DetailsActivity::class.java).putExtra(EVENT_EXTRA, event)
        }
    }
}