package com.saad.invitation.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.EditText


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
