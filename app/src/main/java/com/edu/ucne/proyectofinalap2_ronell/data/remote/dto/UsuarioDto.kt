package com.edu.ucne.proyectofinalap2_ronell.data.remote.dto

data class UsuarioDto (
    val usuarioId: Int = 0,
    val nombreUsuario: String,
    val correoElectronico: String,
    val password: String
)