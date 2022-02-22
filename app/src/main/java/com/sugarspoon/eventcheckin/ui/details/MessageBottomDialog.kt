package com.sugarspoon.eventcheckin.ui.details

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sugarspoon.eventcheckin.R
import com.sugarspoon.eventcheckin.databinding.MessageDialogBinding

class MessageBottomDialog(
    context: Context,
    private val isSuccessDialog: Boolean = true,
    private val titleDialog: Int = R.string.welcome,
    private val message: Int = R.string.success_checkin,
    private val buttonText: Int = R.string.action_close
): BottomSheetDialog(context) {

    var actionListener: () -> Unit = { }

    private val binding: MessageDialogBinding by lazy {
        MessageDialogBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        setupListener()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        dismiss()
    }

    private fun setupView() = WindowManager.LayoutParams(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN).run {
        copyFrom(window?.attributes)
        width = WindowManager.LayoutParams.MATCH_PARENT
        height = WindowManager.LayoutParams.MATCH_PARENT
        screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window?.attributes = this
        with(binding) {
            messageTitleTv.text = context.getString(titleDialog)
            messageBodyTv.text = context.getString(message)
            messageActionBt.text = context.getString(buttonText)
        }
    }

    private fun setupListener() = with(binding) {
        messageActionBt.setOnClickListener {
            actionListener.invoke()
            dismiss()
        }
        messageCloseIv.setOnClickListener {
            actionListener.invoke()
            dismiss()
        }
    }

    companion object {

        fun create(context: Context) = MessageBottomDialog(context)
    }
}