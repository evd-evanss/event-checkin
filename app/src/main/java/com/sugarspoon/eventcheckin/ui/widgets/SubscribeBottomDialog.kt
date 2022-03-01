package com.sugarspoon.eventcheckin.ui.widgets

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sugarspoon.eventcheckin.R
import com.sugarspoon.eventcheckin.databinding.CheckingDialogBinding
import com.sugarspoon.eventcheckin.utils.isValidEmail
import com.sugarspoon.eventcheckin.utils.isValidName

class SubscribeBottomDialog(context: Context): BottomSheetDialog(context) {

    var subscribeListener: (String, String) -> Unit = {_,_ -> }

    private val binding: CheckingDialogBinding by lazy {
        CheckingDialogBinding.inflate(layoutInflater)
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
    }

    private fun setupListener() = with(binding) {
        checkingSubscribeBt.setOnClickListener {
            validate()
        }
        checkingCloseIv.setOnClickListener {
            dismiss()
        }
    }

    private fun validate() = with(binding) {
        val name = checkingNameTv.editText?.text?.toString().orEmpty()
        val email = checkingEmailTv.editText?.text?.toString().orEmpty()
        checkingNameTv.error = null
        checkingEmailTv.error = null
        when {
            name.isValidName().not() -> checkingNameTv.error = context.getString(R.string.invalid_name)
            email.isValidEmail() -> checkingEmailTv.error = context.getString(R.string.invalid_email)
            else -> subscribeListener.invoke(name, email)
        }
    }

    fun displayLoading(isLoading: Boolean) = with(binding) {
        if (isLoading) {
            checkingLoadingPb.visibility = View.VISIBLE
            checkingSubscribeBt.visibility = View.GONE
        } else {
            checkingLoadingPb.visibility = View.GONE
            checkingSubscribeBt.visibility = View.VISIBLE
        }
    }

    fun clear() = with(binding) {
        checkingNameTv.editText?.setText("")
        checkingEmailTv.editText?.setText("")
        checkingNameTv.requestFocus()
    }
    companion object {

        fun create(context: Context) = SubscribeBottomDialog(context)
    }
}