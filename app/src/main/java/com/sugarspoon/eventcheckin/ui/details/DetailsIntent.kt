package com.sugarspoon.eventcheckin.ui.details

import com.sugarspoon.data.model.entity.CustomerEntity
import com.sugarspoon.data.model.entity.EventEntity

sealed class DetailsIntent {
    data class SetCheckin(val customer: CustomerEntity) : DetailsIntent()
}