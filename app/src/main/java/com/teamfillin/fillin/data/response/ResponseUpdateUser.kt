package com.teamfillin.fillin.data.response

data class ResponseUpdateUser(
    val updateUser: UpdateUserDto
) {
    data class UpdateUserDto(
        val id: Int,
        val social: String,
        val nickname: String,
        val imageUrl: String,
        val refreshToken: String,
        val isDeleted: Boolean,
        val updatedAt: String,
        val email: String,
        val createdAt: String,
        val idKey: String,
        val camera: List<String>
    )
}

