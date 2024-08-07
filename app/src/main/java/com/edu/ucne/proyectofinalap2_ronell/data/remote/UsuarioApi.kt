package com.edu.ucne.proyectofinalap2_ronell.data.remote

import com.edu.ucne.proyectofinalap2_ronell.data.remote.dto.UsuarioDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioApi {
    @GET("api/usuarios/{id}")
    suspend fun getUsuario(@Path("id") id: Int) : UsuarioDto

    @GET("api/usuarios")
    suspend fun getUsuarios() : List<UsuarioDto>

    @PUT("api/usuarios/{id}")
    suspend fun putUsuario(@Path("id") id: Int, @Body usuarioDto: UsuarioDto?) : Response<UsuarioDto>

    @POST("api/usuarios")
    suspend fun postUsuario(@Body usuarioDto: UsuarioDto?) : UsuarioDto?

    @DELETE("api/usuarios/{id}")
    suspend fun deleteUsuario(@Path("id") id: Int) : Response<Unit>
}