package com.dnuv.data.model.response.user

data class UserResponse(
    val id: Int,
    val name: String,
    val document: String,
    val phone: String,
    val email: String,
    val type: String,
    val status: String,
    val image: String?,
    val imageKey: String?,
    val userHasPermissions: List<UserPermission>,
    val createdAt: String,
    val updatedAt: String
)

data class UserPermission(
    val id: Int,
    val createdAt: String,
    val updatedAt: String,
    val permission: Permission
)

data class Permission(
    val id: Int,
    val name: String,
    val createdAt: String,
    val updatedAt: String
)
