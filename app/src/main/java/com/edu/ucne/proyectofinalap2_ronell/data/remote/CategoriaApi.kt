package com.edu.ucne.proyectofinalap2_ronell.data.remote

import com.edu.ucne.proyectofinalap2_ronell.data.remote.dto.CategoriaDto
import com.edu.ucne.proyectofinalap2_ronell.data.remote.dto.ProductoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoriaApi {
    @GET("api/categorias/{id}")
    suspend fun getCategoria(@Path("id") id: Int) : CategoriaDto

    @GET("api/categorias")
    suspend fun getCategorias() : List<CategoriaDto>

    @PUT("api/categorias/{id}")
    suspend fun putCategorias(@Path("id") id: Int, @Body categoriaDto: CategoriaDto?) : Response<CategoriaDto>

    @POST("api/categorias")
    suspend fun postCategoria(@Body categoriaDto: CategoriaDto?) : CategoriaDto?

    @DELETE("api/categorias/{id}")
    suspend fun deleteCategoria(@Path("id") id: Int) : Response<Unit>
}