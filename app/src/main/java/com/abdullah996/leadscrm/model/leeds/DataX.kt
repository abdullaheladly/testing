package com.abdullah996.leadscrm.model.leeds


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("auto_switch")
    val autoSwitch: Int,
    @SerializedName("company_id")
    val companyId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("evaluations")
    val evaluations: List<Any>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("notes")
    val notes: String,
    @SerializedName("phones")
    val phones: List<String>,
    @SerializedName("requests_count")
    val requestsCount: Int,
    @SerializedName("users")
    val users: List<User>,
    @SerializedName("users_count")
    val usersCount: Int
)