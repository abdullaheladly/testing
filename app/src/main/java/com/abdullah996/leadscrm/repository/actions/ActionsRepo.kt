package com.abdullah996.leadscrm.repository.actions

import com.abdullah996.leadscrm.model.baseactions.BaseAction
import com.abdullah996.leadscrm.model.getstatus.AllStatusReponse
import com.abdullah996.leadscrm.model.updateaction.AddActionResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface ActionsRepo {

   suspend fun getStatus(

    ):Response<BaseAction>



    suspend fun updateStatus(
         lead_id: String?,
          comment: String?,
         status_id: String?
    ):Response<AddActionResponse>


    suspend fun getAllStatus(

         lead_id: String?
    ):Response<AllStatusReponse>
}