package com.abdullah996.leadscrm.model.leeds


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User (
    @SerializedName("agent")
    val agent: Agent,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("pivot")
    val pivot: Pivot,
    @SerializedName("role_id")
    val roleId: Int,
    @SerializedName("username")
    val username: String
):Serializable