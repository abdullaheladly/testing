package com.abdullah996.leadscrm.model.user


import com.google.gson.annotations.SerializedName

data class Role(
    @SerializedName("company_id")
    val companyId: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)