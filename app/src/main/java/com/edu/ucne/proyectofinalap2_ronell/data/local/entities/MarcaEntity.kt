package com.edu.ucne.proyectofinalap2_ronell.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Marcas")
data class MarcaEntity(
    @PrimaryKey(autoGenerate = true)
    val marcaId: Int,
    val nombreMarca: String,
)
