package com.abdullah996.leadscrm.model.user


import com.google.gson.annotations.SerializedName

data class Page(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("value")
    val value: Boolean?
)