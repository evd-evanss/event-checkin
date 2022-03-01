package com.sugarspoon.eventcheckin.ui.widgets

import android.content.Context
import com.sugarspoon.eventcheckin.R

class MessageDialogFactory(val context: Context) {

    fun createSuccess() =
        MessageBottomDialog.Builder(context)
            .setTitle()
            .setMessage()
            .setButtonText()
            .setStatusColor()
            .setCancelable()
            .build()

    fun createError() =
        MessageBottomDialog.Builder(context)
            .setTitle(R.string.ops)
            .setMessage(R.string.default_error)
            .setButtonText(R.string.action_try_again)
            .setStatusColor(R.color.red)
            .setCancelable(false)
            .build()
}