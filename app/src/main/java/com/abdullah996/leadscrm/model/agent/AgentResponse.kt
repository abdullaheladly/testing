package com.abdullah996.leadscrm.model.agent


import com.google.gson.annotations.SerializedName

data class AgentResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)