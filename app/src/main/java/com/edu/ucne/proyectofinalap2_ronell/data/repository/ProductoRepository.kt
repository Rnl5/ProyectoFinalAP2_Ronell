package com.edu.ucne.proyectofinalap2_ronell.data.repository

import android.util.Log
import com.edu.ucne.proyectofinalap2_ronell.data.local.dao.ProductoDao
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.ProductoEntity
import com.edu.ucne.proyectofinalap2_ronell.data.remote.ProductoApi
import com.edu.ucne.proyectofinalap2_ronell.data.remote.dto.ProductoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductoRepositoryImpl @Inject constructor(
    private val productosAPI: ProductoApi,
    private val productoDao: ProductoDao,

) {
    suspend fun saveProducto(producto: ProductoEntity) =
        productoDao.save(producto)

    fun getProductosLocal(): Flow<List<ProductoEntity>> =
        productoDao.getAll()

    suspend fun deleteProductoLocal(producto: ProductoEntity) =
        productoDao.delete(producto)


    suspend fun getProductoLocal(productoId: Int): ProductoEntity? =
        productoDao.find(productoId)


    suspend fun getProductosLocalSync() : List<ProductoEntity> =
        productoDao.getAllSync()


    suspend fun getProductoByBarcode(barcode: String): ProductoEntity? =
        productoDao.findByCodigoBarras(barcode)

    suspend fun getProducto(id: Int): ProductoDto? {
        return try {
            productosAPI.getProducto(id)
        } catch (e: Exception) {
            Log.e("Repositorio", "getProducto: ${e.message}")
            null
        }
    }

    fun getProductos(): Flow<Resource<List<ProductoDto>>> = flow {
        emit(Resource.Loading())
        try {
            val productos = productosAPI.getProductos()
            emit(Resource.Success(productos))
        } catch (e: Exception) {
            Log.e("Repositorio", "getProductos: ${e.message}")
            emit(Resource.Error(e.message ?: "An unexpected error ocurred"))
        }

    }

    suspend fun putProducto(producto: ProductoDto) {
        try {
            productosAPI.putProducto(producto.productoId, producto)
        } catch (e: Exception) {
            Log.e("Repositorio", "putProducto: ${e.message}")
        }
    }

    suspend fun postProducto(producto: ProductoDto) {
        try {
            productosAPI.postProducto(producto)
        } catch (e: Exception) {
            Log.e("Repositorio", "postProducto: ${e.message}")
        }
    }

    suspend fun deleteProducto(producto: ProductoDto) {
        try {
            productosAPI.deleteProducto(producto.productoId)
        } catch (e: Exception) {
            Log.e("Repositorio", "deleteProducto: ${e.message}")
        }
    }


}


sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}
