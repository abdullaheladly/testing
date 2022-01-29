package com.abdullah996.leadscrm.model.agent


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("company")
    val company: Company,
    @SerializedName("company_id")
    val companyId: Int,
    @SerializedName("counter")
    val counter: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("phones")
    val phones: List<String>,
    @SerializedName("teams_count")
    val teamsCount: Int,
    @SerializedName("title_id")
    val titleId: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user")
    val user: User,
    @SerializedName("user_id")
    val userId: Int
)