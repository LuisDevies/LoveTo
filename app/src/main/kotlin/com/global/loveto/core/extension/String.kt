package com.global.loveto.core.extension

import android.text.Editable
import android.text.Html
import android.text.Spanned
import java.math.BigInteger
import java.security.MessageDigest
import java.util.regex.Pattern


fun String.Companion.empty() = ""

fun String.capitalizeFirst(): String {
    val strArrayOBJ = this.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    val builder = StringBuilder()
    for (s in strArrayOBJ) {
        val cap = s.substring(0, 1).toUpperCase() + s.substring(1)
        builder.append("$cap ")
    }
    return builder.toString()
}

fun Any?.toEditable(): Editable {
    return if (this != null)
        Editable.Factory.getInstance().newEditable(this.toString())
    else {
        Editable.Factory.getInstance().newEditable("")
    }
}

fun String.formatAsPhone(): String {
    val phoneFormated = if (this.substring(0, 2) == "00") {
        "+${this.subSequence(2, this.length)}"
    } else {
        this
    }
    return phoneFormated.replace("-", "").replace(" ", "")
}

fun String.sha512() = this.hashString("SHA-512")

fun String.sha256() = this.hashString("SHA-256")

fun String.sha1() = this.hashString("SHA-1")

private fun String.hashString(type: String): String {
    val HEX_CHARS = "0123456789ABCDEF"
    val bytes = MessageDigest
            .getInstance(type)
            .digest(this.toByteArray())
    val result = StringBuilder(bytes.size * 2)

    bytes.forEach {
        val i = it.toInt()
        result.append(HEX_CHARS[i shr 4 and 0x0f])
        result.append(HEX_CHARS[i and 0x0f])
    }

    return result.toString()
}

fun String.removeArray(): String {
    return this.removePrefix("[").removeSuffix("]")
}

fun String.hashToInt(): Int {

    val number = BigInteger(1, this.toByteArray())

    return number.toInt()
}

fun String.isValidEmail(): Boolean {
    return when {
        this.isEmpty() -> false
        else -> android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}

fun String.getLinks(): List<String> {
    val links = mutableListOf<String>()
    val regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]"
    val p = Pattern.compile(regex)
    val m = p.matcher(this)
    while (m.find()) {
        var urlStr = m.group()
        if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
            urlStr = urlStr.substring(1, urlStr.length - 1);

        }
        links.add(urlStr)
    }
    return links
}

fun String.getFromHtml(): Spanned {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY);
    } else {
        Html.fromHtml(this);
    }
}