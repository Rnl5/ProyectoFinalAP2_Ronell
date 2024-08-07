package com.edu.ucne.proyectofinalap2_ronell.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val usuarioId: Int = 0,
    val nombreUsuario: String,
    val correoElectronico: String,
    val password: String
)
