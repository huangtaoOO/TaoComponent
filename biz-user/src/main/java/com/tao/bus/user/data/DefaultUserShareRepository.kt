package com.tao.bus.user.data

import com.example.base.data.user.UserShareRepository
import com.example.base.entity.user.UserEntity
import kotlinx.coroutines.flow.StateFlow

class DefaultUserShareRepository constructor(
    private val repository : UserRepository
) : UserShareRepository {

    override suspend fun obtainUserInfo(): Result<UserEntity> {
        return repository.obtainUserInfo()
    }

    override fun obtainUserInfoFlow(): StateFlow<UserEntity?> {
        return repository.obtainUserInfoFlow()
    }

}