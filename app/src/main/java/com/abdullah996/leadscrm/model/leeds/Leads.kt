package com.abdullah996.leadscrm.model.leeds


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


@Parcelize
data class Leads(
    @SerializedName("auto_switch")
    val autoSwitch: Int,
    @SerializedName("company_id")
    val companyId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("evaluations")
    val evaluations: @RawValue List<Any>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("notes")
    val notes: String,
    @SerializedName("phones")
    val phones: @RawValue List<String>,
    @SerializedName("requests_count")
    val requestsCount: Int,
    @SerializedName("users")
    val users: @RawValue List<User>,
    @SerializedName("users_count")
    val usersCount: Int
):Parcelable