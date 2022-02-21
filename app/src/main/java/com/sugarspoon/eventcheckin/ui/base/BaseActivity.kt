package com.sugarspoon.eventcheckin.ui.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater) -> T

abstract class BaseActivity<Binding: ViewBinding>(
    private val inflater: Inflate<Binding>
): AppCompatActivity() {

    val context: Context
        get() = this

    lateinit var binding: Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        inflater.run { binding = invoke(layoutInflater) }
        setContentView(binding.root)
    }

}