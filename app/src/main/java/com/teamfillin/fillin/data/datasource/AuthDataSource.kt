package com.teamfillin.fillin.data.datasource

import com.teamfillin.fillin.data.response.ResponseUserInfo
import com.teamfillin.fillin.data.response.ResponseUserPhotoInfo

interface AuthDataSource {
    suspend fun getUser(): ResponseUserInfo.UserInfo
}