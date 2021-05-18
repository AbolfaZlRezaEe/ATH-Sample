package com.abproject.athsample.util

import android.util.Patterns

/**
 * Created by Abolfazl on 5/17/21
 */
fun CharSequence.checkEmailIsValid() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun validationIranianPhoneNumber(number: String): Boolean {
    val phoneNumberPattern =
        Regex("(0|\\+98)?([ ]|,|-|[()]){0,2}9[1|2|3|4]([ ]|,|-|[()]){0,2}(?:[0-9]([ ]|,|-|[()]){0,2}){8}")
    return (number.isNotEmpty() && number.matches(phoneNumberPattern))
}