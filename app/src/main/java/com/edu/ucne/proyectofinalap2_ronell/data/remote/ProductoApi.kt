package com.edu.ucne.proyectofinalap2_ronell.data.remote

import com.edu.ucne.proyectofinalap2_ronell.data.remote.dto.ProductoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductoApi {

    @GET("api/productos/{id}")
    suspend fun getProducto(@Path("id") id: Int) : ProductoDto

    @GET("api/productos")
    suspend fun getProductos() : List<ProductoDto>

    @PUT("api/productos/{id}")
    suspend fun putProducto(@Path("id") id: Int, @Body productoDto: ProductoDto?) : Response<ProductoDto>

    @POST("api/productos")
    suspend fun postProducto(@Body productoDto: ProductoDto?) : ProductoDto?

    @DELETE("api/productos/{id}")
    suspend fun deleteProducto(@Path("id") id: Int) : Response<Unit>
}