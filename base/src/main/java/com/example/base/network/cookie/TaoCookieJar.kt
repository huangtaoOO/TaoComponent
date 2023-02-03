package com.example.base.network.cookie

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.base.kt.dataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * Author: huangtao
 * Date: 2023/2/2
 * Desc: cookie 处理类
 */
class TaoCookieJar(val context: Context) : CookieJar {

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        Log.e("TaoCookieJar", "loadForRequest = $url")
        return runBlocking {
            val currentTimeStamp = System.currentTimeMillis()
            val cookieJson = context.dataStore.data.map { preferences ->
                val info = stringPreferencesKey(DataSourceConstant.PREFIX + url.toString())
                preferences[info] ?: ""
            }.first()
            if (cookieJson.isEmpty())
                return@runBlocking emptyList<Cookie>()
            val needPut = mutableListOf<Cookie>()
            val type = object : TypeToken<List<Cookie>>() {}
            val original = Gson().fromJson<List<Cookie>>(cookieJson, type.type)
            for (cookie in original) {
                Log.e("TaoCookieJar",cookie.toString())
                if (cookie.expiresAt >= currentTimeStamp) {
                    needPut.add(cookie)
                }
            }
            needPut
        }
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        Log.e("TaoCookieJar", "saveFromResponse = $url")
        runBlocking {
            if (cookies.isEmpty()) return@runBlocking
            val currentTimeStamp = System.currentTimeMillis()
            val needSave = mutableListOf<Cookie>()
            for (cookie in cookies) {
                Log.e("TaoCookieJar",cookie.toString())
                if (cookie.expiresAt >= currentTimeStamp) {
                    needSave.add(cookie)
                }
            }
            context.dataStore.edit {
                val urlKey = stringPreferencesKey(DataSourceConstant.PREFIX + url.toString())
                it[urlKey] = Gson().toJson(needSave)
            }
        }
    }
}