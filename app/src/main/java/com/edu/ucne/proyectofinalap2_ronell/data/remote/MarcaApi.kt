package com.edu.ucne.proyectofinalap2_ronell.data.remote

import com.edu.ucne.proyectofinalap2_ronell.data.remote.dto.MarcaDto
import com.edu.ucne.proyectofinalap2_ronell.data.remote.dto.ProductoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MarcaApi {
    @GET("api/marcas/{id}")
    suspend fun getMarca(@Path("id") id: Int) : MarcaDto

    @GET("api/marcas")
    suspend fun getMarcas() : List<MarcaDto>

    @PUT("api/marcas/{id}")
    suspend fun putMarca(@Path("id") id: Int, @Body marcaDto: MarcaDto?) : Response<MarcaDto>

    @POST("api/marcas")
    suspend fun postMarca(@Body marcaDto: MarcaDto?) : MarcaDto?

    @DELETE("api/marcas/{id}")
    suspend fun deleteMarca(@Path("id") id: Int) : Response<Unit>
}