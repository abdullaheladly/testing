package com.abdullah996.leadscrm.model.leeds


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Agent(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user_id")
    val userId: Int
):Serializable