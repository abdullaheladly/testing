package com.abdullah996.leadscrm.model.user


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("company")
    val company: Any?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("pages")
    val pages: List<Page>?,
    @SerializedName("role")
    val role: Role?,
    @SerializedName("role_id")
    val roleId: Int?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("username")
    val username: String?
)