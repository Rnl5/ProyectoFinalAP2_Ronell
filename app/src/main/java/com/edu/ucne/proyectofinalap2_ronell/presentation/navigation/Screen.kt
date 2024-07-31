package com.edu.ucne.proyectofinalap2_ronell.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen() {
    @Serializable
    object ProductoList : Screen()

    @Serializable
    data class Producto(val productoId: Int) : Screen()

    @Serializable
    object CategoriaList : Screen()

    @Serializable
    data class Categoria(val categoriaId: Int) : Screen()

    @Serializable
    object MarcaList : Screen()

    @Serializable
    data class Marca(val marcaId: Int) : Screen()

    @Serializable
    object Dashboard : Screen()
}