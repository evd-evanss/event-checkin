package com.sugarspoon.eventcheckin.ui.details

import com.sugarspoon.data.model.entity.CustomerEntity
import com.sugarspoon.data.model.entity.EventShareEntity

sealed class DetailsIntent {
    data class SetCheckin(val customer: CustomerEntity) : DetailsIntent()
    data class ShareEvent(val event: EventShareEntity) : DetailsIntent()
}