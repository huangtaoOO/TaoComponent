package com.example.base.kt

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/**
 * Author: huangtao
 * Date: 2023/1/29
 * Desc: dataStore扩展
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "com.example.base")