package com.service.mvisample.commonutils.extensions

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import org.json.JSONArray
import org.json.JSONObject

data class CoreJsonString(val data: String? = null) {

    fun asJSONObject() = try {
        JSONObject(data)
    } catch (e: Exception) {
        null
    }

    fun asJSONArray() = try {
        JSONArray(data)
    } catch (e: Exception) {
        null
    }

    fun asJsonObject() = try {
        val parser = JsonParser()
        val tradeElement: JsonElement = parser.parse(data)
        tradeElement.asJsonObject
    } catch (e: Exception) {
        null
    }

    fun asJsonArray() = try {
        val parser = JsonParser()
        val tradeElement: JsonElement = parser.parse(data)
        tradeElement.asJsonArray
    } catch (e: Exception) {
        null
    }

    fun isJSONArray() = asJSONArray() != null
    fun isJSONObject() = asJSONObject() != null
}