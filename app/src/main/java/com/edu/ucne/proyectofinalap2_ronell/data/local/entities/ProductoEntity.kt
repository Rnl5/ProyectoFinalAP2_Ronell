package com.edu.ucne.proyectofinalap2_ronell.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Productos")
data class ProductoEntity(
    @PrimaryKey(autoGenerate = true)
    val productoId: Int,
    val nombreProducto: String,
    val descripcionProducto: String,
    val fechaVencProducto: String,
    val stockProducto: Int,
    val categoriaId: Int,
    val marcaId: Int,
    val precioCompraProducto: Double,
    val precioVentaProducto: Double,
    val codigoBarrasProducto: String
)
