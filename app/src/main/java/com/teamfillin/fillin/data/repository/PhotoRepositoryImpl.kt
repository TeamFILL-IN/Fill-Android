package com.teamfillin.fillin.data.repository

import com.teamfillin.fillin.data.datasource.PhotoDataSource
import com.teamfillin.fillin.domain.entity.UserPhoto
import com.teamfillin.fillin.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val dataSource: PhotoDataSource
): PhotoRepository {
    override suspend fun getUserPhotos(): List<UserPhoto> {
        return dataSource.getUserPhotos().map {
            it.toUserPhoto()
        }
    }
}