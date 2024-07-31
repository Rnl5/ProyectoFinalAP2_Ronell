package com.edu.ucne.proyectofinalap2_ronell.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categorias")
data class CategoriaEntity(
    @PrimaryKey(autoGenerate = true)
    val categoriaId: Int,
    val nombreCategoria: String,
    val descripcionCategoria: String,
)
