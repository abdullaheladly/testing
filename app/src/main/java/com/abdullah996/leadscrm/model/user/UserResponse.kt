package com.abdullah996.leadscrm.model.user


import com.abdullah996.leadscrm.model.user.Data
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    @Expose
    val `data`: Data?,
    @SerializedName("message")
    @Expose
    val message: String?,
    @SerializedName("status")
    @Expose
    val status: Int?,
    @SerializedName("success")
    @Expose
    val success: Boolean?
)