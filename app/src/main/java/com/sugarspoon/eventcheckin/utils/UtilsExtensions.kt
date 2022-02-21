package com.sugarspoon.eventcheckin.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.location.Geocoder
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.util.Patterns
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.sugarspoon.eventcheckin.R
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("CheckResult")
fun AppCompatImageView.loadImage(
    context: Context,
    url: String,
    onError: () -> Unit,
    onLoading: (Boolean) -> Unit,
    onSuccess: () -> Unit
) {
    onLoading.invoke(true)
    Glide.with(context)
        .load(url.fixProtocol())
        .centerCrop()
        .listener(
            @SuppressLint("CheckResult")
            object : RequestListener<Drawable?> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    onError.invoke()
                    onLoading.invoke(false)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    onSuccess.invoke()
                    onLoading.invoke(false)
                    return false
                }
            }
        )
        .into(this)
}

private fun String.fixProtocol() : String {
    return if (this.take(5).contains("https")) {
        this
    } else {
        replace("http", "https")
    }
}

fun Context.getAddress(
    latitude: Double,
    longitude: Double
) : String {
    return  Geocoder(this, Locale.getDefault()).getFromLocation(latitude, longitude, 1)[0].getAddressLine(0)
}

fun Number.toLocalDate(): String {
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val netDate = Date(Timestamp(toLong()).time)
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}

fun String.toHtml(): Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
} else {
    Html.fromHtml(this)
}

fun String.isValidEmail(): Boolean {
    return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches().not()
}

fun String.isValidName(): Boolean {
    return this.isNotEmpty() and(this.split(" ").size >= 2)
}