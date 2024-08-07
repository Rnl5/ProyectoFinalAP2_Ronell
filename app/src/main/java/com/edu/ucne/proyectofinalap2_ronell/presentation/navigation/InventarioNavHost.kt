package com.edu.ucne.proyectofinalap2_ronell.presentation.navigation


//import com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthManager
//import com.edu.ucne.proyectofinalap2_ronell.presentation.login.ProfileScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.edu.ucne.proyectofinalap2_ronell.presentation.Home.Dashboard
import com.edu.ucne.proyectofinalap2_ronell.presentation.Inventario.InventarioListScreenProximoVencer
import com.edu.ucne.proyectofinalap2_ronell.presentation.Inventario.InventarioListScreenStockBajo
import com.edu.ucne.proyectofinalap2_ronell.presentation.categoria.CategoriaListScreen
import com.edu.ucne.proyectofinalap2_ronell.presentation.categoria.CategoriaScreen
import com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthViewModel
import com.edu.ucne.proyectofinalap2_ronell.presentation.login.LoginScreen
import com.edu.ucne.proyectofinalap2_ronell.presentation.login.SignUpScreen
import com.edu.ucne.proyectofinalap2_ronell.presentation.marca.MarcaListScreen
import com.edu.ucne.proyectofinalap2_ronell.presentation.marca.MarcaScreen
import com.edu.ucne.proyectofinalap2_ronell.presentation.perfil_usuario.PerfilUsuarioScreen
import com.edu.ucne.proyectofinalap2_ronell.presentation.producto.InventarioListScreen
import com.edu.ucne.proyectofinalap2_ronell.presentation.producto.ProductoListScreen
import com.edu.ucne.proyectofinalap2_ronell.presentation.producto.ProductoScreen

@Composable
fun InventarioNavHost(
    navHostController: NavHostController,
    authViewModel: AuthViewModel,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.LoginScreen,
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
                irAProductoList = { navHostController.navigate(Screen.InventarioList) },
                productoId = args.productoId
            )
        }


        composable<Screen.CategoriaList> {
            CategoriaListScreen(
                onVerCategoria = {
                    navHostController.navigate(Screen.Categoria(it.categoriaId ?: 0))
                },
                onAddCategoria = {
                    navHostController.navigate(Screen.Categoria(0))
                },
                irADashboard = { navHostController.navigate(Screen.Dashboard) }
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
                },
                irADashboard = { navHostController.navigate(Screen.Dashboard) }
            )
        }


        composable<Screen.Marca> {
            val args = it.toRoute<Screen.Marca>()
            MarcaScreen(
                irAMarcaList = { navHostController.navigate(Screen.MarcaList) },
                marcaId = args.marcaId
            )
        }

        composable<Screen.InventarioList> {
            InventarioListScreen(onVerProducto = {
                navHostController.navigate(Screen.Producto(it.productoId ?: 0))
            },
                onAddProducto = {
                    navHostController.navigate(Screen.Producto(0))
                },
                irADashboard = { navHostController.navigate(Screen.Dashboard) }
            )
        }

        composable<Screen.Dashboard> {
            Dashboard(
                irAInventario = {
                    navHostController.navigate(Screen.InventarioList)
                },

                irACategoriaList = {
                    navHostController.navigate(Screen.CategoriaList)
                },
                irAMarcasList = {
                    navHostController.navigate(Screen.MarcaList)
                },

                irAInventarioStockBajo = {
                    navHostController.navigate(Screen.InventarioStockBajoList)
                },
                irAInventarioFechaExp = {
                    navHostController.navigate(Screen.InventarioFechaExpList)
                },
                irAPerfilScreen = {
                    navHostController.navigate(Screen.PerfilScreen)
                },
                irALogin = {
                    navHostController.navigate(Screen.LoginScreen)
                },
                authViewModel = authViewModel,


                )
        }

        composable<Screen.InventarioStockBajoList> {
            InventarioListScreenStockBajo(onVerProducto = {
                navHostController.navigate(Screen.Producto(it.productoId ?: 0))
            },
                onAddProducto = {
                    navHostController.navigate(Screen.Producto(0))
                },
                irADashboard = { navHostController.navigate(Screen.Dashboard) }
            )
        }

        composable<Screen.InventarioFechaExpList> {
            InventarioListScreenProximoVencer(onVerProducto = {
                navHostController.navigate(Screen.Producto(it.productoId ?: 0))
            },
                onAddProducto = {
                    navHostController.navigate(Screen.Producto(0))
                },
                irADashboard = { navHostController.navigate(Screen.Dashboard) }
            )
        }

        composable<Screen.PerfilScreen> {
            PerfilUsuarioScreen(
                irADashboard = { navHostController.navigate(Screen.Dashboard) },
                authViewModel = authViewModel,
                irALogin = { navHostController.navigate(Screen.LoginScreen) }

            )
        }

        composable<Screen.LoginScreen> {
            LoginScreen(
                irARegistro = { navHostController.navigate(Screen.RegistroScreen) },
                authViewModel = authViewModel,
                irADashboard = { navHostController.navigate(Screen.Dashboard) }

            )
        }

        composable<Screen.RegistroScreen> {
            SignUpScreen(
                irALogin = { navHostController.navigate(Screen.LoginScreen) },
                authViewModel = authViewModel,
                irADashboard = { navHostController.navigate(Screen.Dashboard) }

            )
        }


    }

}


