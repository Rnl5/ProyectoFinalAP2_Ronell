package com.edu.ucne.proyectofinalap2_ronell.data.remote.dto

data class ProductoDto(
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