package com.tao.bus.user.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.base.data.Result
import com.example.base.entity.BaseEntity
import com.example.base.entity.user.UserEntity
import com.example.base.kt.dataStore
import com.google.gson.Gson
import com.tao.bus.user.data.UserDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 本地数据源
 */
class LocalDataSource(private val context: Context) : UserDataSource {

    private var userInfo: UserEntity? = null

    override suspend fun signIn(
        username: String,
        password: String
    ): Result<BaseEntity<UserEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): Result<BaseEntity<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun register(username: String, password: String): Result<BaseEntity<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveUserInfo(entity: UserEntity) {
        context.dataStore.edit {
            userInfo = entity
            val uid = intPreferencesKey(DataSourceConstant.UID)
            it[uid] = entity.id
            val info = stringPreferencesKey(DataSourceConstant.UINFO)
            it[info] = Gson().toJson(entity)
        }
    }

    override suspend fun clearUserInfo() {
        context.dataStore.edit {
            userInfo = null
            val uid = intPreferencesKey(DataSourceConstant.UID)
            it[uid] = 0
            val info = stringPreferencesKey(DataSourceConstant.UINFO)
            it[info] = ""
        }
    }

    override suspend fun obtainUserInfo(): Result<UserEntity> {
        if (userInfo != null) {
            return Result.Success(userInfo!!)
        } else {
            return runBlocking {
                context.dataStore.data
                    .map { preferences ->
                        val info = stringPreferencesKey(DataSourceConstant.UINFO)
                        preferences[info] ?: ""
                    }
                    .map {
                        if (it.isNotEmpty()) {
                            userInfo = Gson().fromJson(it, UserEntity::class.java)
                            Result.Success(userInfo!!)
                        } else {
                            Result.Error(NullPointerException("存储数据字段为null"))
                        }
                    }.first()
            }
        }
    }
}