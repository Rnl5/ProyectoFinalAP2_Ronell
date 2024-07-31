package com.edu.ucne.proyectofinalap2_ronell.presentation.navigation


import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.edu.ucne.proyectofinalap2_ronell.presentation.categoria.CategoriaListScreen
import com.edu.ucne.proyectofinalap2_ronell.presentation.categoria.CategoriaScreen
import com.edu.ucne.proyectofinalap2_ronell.presentation.marca.MarcaListScreen
import com.edu.ucne.proyectofinalap2_ronell.presentation.marca.MarcaScreen
import com.edu.ucne.proyectofinalap2_ronell.presentation.producto.ProductoListScreen
import com.edu.ucne.proyectofinalap2_ronell.presentation.producto.ProductoScreen

@Composable
fun InventarioNavHost(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.MarcaList,
    ) {
        composable<Screen.ProductoList> {
            ProductoListScreen(onVerProducto = {
                navHostController.navigate(Screen.Producto(it.productoId ?: 0))
            },
                onAddProducto = {
                    navHostController.navigate(Screen.Producto(0))
                }
            )
        }

        composable<Screen.Producto> {
            val args = it.toRoute<Screen.Producto>()
            ProductoScreen(
                irAProductoList = { navHostController.navigate(Screen.ProductoList) },
                productoId = args.productoId
            )
        }


        composable<Screen.CategoriaList> {
            CategoriaListScreen(onVerCategoria = {
                navHostController.navigate(Screen.Categoria(it.categoriaId ?: 0))
            },
                onAddCategoria = {
                    navHostController.navigate(Screen.Categoria(0))
                }
            )
        }



        composable<Screen.Categoria> {
            val args = it.toRoute<Screen.Categoria>()
            CategoriaScreen(
                irACategoriaList = { navHostController.navigate(Screen.CategoriaList) },
                categoriaId = args.categoriaId
            )
        }


        composable<Screen.MarcaList> {
            MarcaListScreen(onVerMarca = {
                navHostController.navigate(Screen.Marca(it.marcaId ?: 0))
            },
                onAddMarca = {
                    navHostController.navigate(Screen.Marca(0))
                }
            )
        }


        composable<Screen.Marca> {
            val args = it.toRoute<Screen.Marca>()
            MarcaScreen(
                irAMarcaList = { navHostController.navigate(Screen.MarcaList) },
                marcaId = args.marcaId
            )
        }


    }
}


