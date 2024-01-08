package com.saad.invitation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.EditText
import com.saad.invitation.learning.debug_tag
import com.saad.invitation.learning.listenerTag


var isSelected: Int = 0

fun updateText(editText: EditText, style: Int) {
    val spannableString = SpannableStringBuilder(editText.text)
    spannableString.setSpan(
        StyleSpan(style),
        editText.selectionStart,
        editText.selectionEnd,
        0
    )
    editText.text = spannableString
}

fun underlineText(editText: EditText) {
    val spannableString = SpannableStringBuilder(editText.text)
    spannableString.setSpan(
        UnderlineSpan(),
        editText.selectionStart,
        editText.selectionEnd,
        0
    )
    editText.text = spannableString
}

fun alignText(editText: EditText, view: Int) {
    editText.textAlignment = view
    val spannableString = SpannableStringBuilder(editText.text)
    editText.text = spannableString
}

fun createBitmapDrawableFromView(view: View): BitmapDrawable {
    view.measure(
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    )
    view.layout(0, 0, view.measuredWidth, view.measuredHeight)

    val bitmap = Bitmap.createBitmap(
        view.measuredWidth,
        view.measuredHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
    view.draw(canvas)

    return BitmapDrawable(view.resources, bitmap)
}

fun log(text: String) {
    Log.d(debug_tag, text)
}

fun logListener(text: String){
    Log.d(listenerTag, text)
}

fun generateBitmapFromView(view: View): Bitmap {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val network = connectivityManager.activeNetwork
    val capabilities = connectivityManager.getNetworkCapabilities(network)
    return capabilities != null &&
            (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
}