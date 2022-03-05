package com.osisupermoses.healthbloc.domain.model

data class User(
    val id: String?,
    val userId: String,
    val fullName: String,
    val profilePhoto: String,
    val orgName: String,
    val phoneNum: String,
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_id" to this.userId,
            "full_name" to this.fullName,
            "organization's_name" to this.orgName,
            "profile_photo" to this.profilePhoto,
            "phone_num" to this.phoneNum

        )
    }
}

