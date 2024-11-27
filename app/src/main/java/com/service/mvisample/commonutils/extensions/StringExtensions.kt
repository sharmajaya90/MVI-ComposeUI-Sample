package com.service.mvisample.commonutils.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.util.Log
import androidx.core.text.TextUtilsCompat
import androidx.core.view.ViewCompat
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.math.BigDecimal
import java.util.Locale
import java.util.regex.Pattern


fun String?.isNotNullOrEmpty(): Boolean = this != null && this.trim().isNotEmpty()

fun String?.getColor(): Int {
    if (this == "#00000000" || this.isNullOrEmpty() || this.startsWith("#") && this.length > 9) {
        return Color.TRANSPARENT
    }
    return try {
        val color = this?.let {
            if (this.isEmpty()) -1 else this.getOctColor()
        } ?: kotlin.run {
            -1
        }
        color
    } catch (e: Exception) {
        -1
    }
}

private fun String.getOctColor(): Int {
    if (this.contains("rgba")) {
        var tempstr = this.split("rgba\\(".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()//.split(",");
        tempstr = tempstr[1].split("\\)".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        tempstr = tempstr[0].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()


        if (!this.equals("rgba(255,255,255,0)", ignoreCase = true)) {
            return Color.parseColor(
                String.format(
                    "#%02x%02x%02x%02x",
                    Math.round(java.lang.Float.parseFloat(tempstr[3].trim()) * 255),
                    Integer.parseInt(tempstr[0].trim()),
                    Integer.parseInt(tempstr[1].trim()),
                    Integer.parseInt(tempstr[2].trim())
                )
            )
        }
        return Color.parseColor("#00000000")
    } else {
        return this.getObjColor()
    }

}


private fun String.getObjColor(): Int {

    try {
        if (this.contains("rgb")) {
            var tempstr = this.split("rgb\\(".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()//.split(",");
            tempstr =
                tempstr[1].split("\\)".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            tempstr = tempstr[0].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return Color.rgb(
                Integer.parseInt(tempstr[0].trim { it <= ' ' }),
                Integer.parseInt(tempstr[1].trim { it <= ' ' }),
                Integer.parseInt(tempstr[2].trim { it <= ' ' })
            )

        } else if (this.contains("#")) {
            if (this.length < 5) {
                val color = this.replace("#", "")
                var tempColor = ""
                for (i in 0..2) tempColor = color[i].toString() + color[i].toString() + tempColor
                //logReport("AppCompactView", "received color #$tempColor")
                return Color.parseColor("#$tempColor")

            } else return Color.parseColor(this)
        }
    } catch (e: Exception) {
        return Color.parseColor("#000000")
    }

    return Color.parseColor("#000000")
}
fun String?.getFloatValue(defaultValue: Float = 0f): Float {
    return try {
        this?.toFloatOrNull() ?: defaultValue
    } catch (e: java.lang.Exception) {
        // logReport(e.message)
        0f
    }
}

fun String?.getDoubleValue(): Double {
    return try {
        this?.toDoubleOrNull() ?: 0.toDouble()
    } catch (e: java.lang.Exception) {
        // logReport(e.message)
        0.toDouble()
    }
}


fun String?.getIntValue(defaultValue: Int = 0): Int {
    return try {
        this?.trim()?.toIntOrNull() ?: defaultValue
    } catch (e: java.lang.Exception) {
        // logReport(e.message)
        defaultValue
    }
}

fun String?.getLongValue(defaultValue: Long = 0): Long {
    return try {
        this?.toLongOrNull() ?: defaultValue
    } catch (e: java.lang.Exception) {
        // logReport(e.message)
        defaultValue
    }
}

fun String?.getBigDecimalValue(defaultValue: BigDecimal = BigDecimal(0)): BigDecimal {
    return try {
        this?.toBigDecimal() ?: defaultValue
    } catch (e: java.lang.Exception) {
        // logReport(e.message)
        defaultValue
    }
}

fun BigDecimal?.getIntWithBigDecimal(defaultValue: Int = 0): Int {
    return try {
        this?.toInt() ?: defaultValue
    } catch (e: java.lang.Exception) {
        // logReport(e.message)
        defaultValue
    }
}

fun String?.getBooleanValue(): Boolean {
    return try {
        return this?.trim() == "true" || this?.trim() == "True" || this?.trim() == "TRUE" || this?.trim() == "1" || this?.trim() == "Yes" || this?.trim() == "YES" || this?.trim() == "yes"
    } catch (e: java.lang.Exception) {
        // logReport(e.message)
        false
    }
}


fun String?.getPlayStoreUrl(): String {
    return "https://play.google.com/store/apps/details?id=$this"
}

fun String?.validateEmail(): Boolean = this?.let {
    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
        .matcher(this).find()
} ?: kotlin.run { false }


fun String?.isPhoneInput(): Boolean {
    this?.let {
        val doubleValue: Double = this.trim().toDoubleOrNull() ?: 0.toDouble()
        return (doubleValue > 0.toDouble())
    } ?: kotlin.run {
        return false
    }
}


fun String?.isMobileInput(): Boolean {
    this?.let {
        val doubleValue: Double = this.trim().toDoubleOrNull() ?: 0.toDouble()
        return (doubleValue > 0.toDouble()) && this.trim().length >= 10
    } ?: kotlin.run {
        return false
    }
}


fun String?.stableId() = this?.hashCode()?.toLong() ?: 0L


fun String.toJsonObject(): JsonObject? {
    return try {
        Gson().fromJson(this, JsonObject::class.java)
    } catch (e: Throwable) {
        Log.e(e.message,"")
        null
    }
}

fun String.toJSONObject(): JSONObject? {
    return try {
        JSONObject(this)
    } catch (e: Throwable) {
        Log.e(e.message,"")
        null
    }
}

fun String.toJsonArray(): JsonArray? {
    return try {
        Gson().fromJson(this, JsonArray::class.java)
    } catch (e: Throwable) {
        Log.e(e.message,"")
        null
    }
}

fun String.actionViewForUrl(context: Context?): Boolean {
    try {

        val browserIntent = Intent(
            Intent.ACTION_VIEW, Uri.parse(if (this.startsWith("http")) this else "https://$this")
        )
        context?.startActivity(browserIntent)
        return true
    } catch (e: java.lang.Exception) {
        Log.e(e.message,"")
        return false
    }
}


fun <T> String?.convertIntoModel(classRef: Class<T>): T? {
    return try {
        convertIntoModel(classRef = classRef, gson = this!!.provideGsonWithCoreJsonString())
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
        null
    }
}

fun <T> String?.convertIntoModels(type: TypeToken<T>): T? {
    return try {
        this?.provideGsonWithCoreJsonString()?.fromJson(this, type.type)
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
        null
    }
}


fun <T> String?.convertIntoModel(classRef: Class<T>, gson: Gson): T? {
    return try {
        gson.fromJson(this, classRef)
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
        null
    }
}


fun <T> String?.convertIntoModels(type: TypeToken<T>, gson: Gson): T? {
    return try {
        gson.fromJson(this, type.type)
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
        null
    }

}

fun isRTLLocale() = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == ViewCompat.LAYOUT_DIRECTION_RTL
