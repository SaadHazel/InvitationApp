package com.saad.invitation

import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.EditText

fun updateText(editText: EditText, style:Int){
    val spannableString = SpannableStringBuilder(editText.text)
    spannableString.setSpan(
        StyleSpan(style),
        editText.selectionStart,
        editText.selectionEnd,
        0
    )
    editText.text = spannableString
}
fun underlineText(editText: EditText){
    val spannableString = SpannableStringBuilder(editText.text)
    spannableString.setSpan(
        UnderlineSpan(),
        editText.selectionStart,
        editText.selectionEnd,
        0
    )
    editText.text = spannableString
}

fun alignText(editText: EditText, view: Int){
        editText.textAlignment = view
    val spannableString = SpannableStringBuilder(editText.text)
    editText.text = spannableString
}