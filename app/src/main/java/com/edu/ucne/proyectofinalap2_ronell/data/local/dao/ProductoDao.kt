package com.edu.ucne.proyectofinalap2_ronell.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.ProductoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    @Upsert
    suspend fun save(producto: ProductoEntity)

    @Query(
        """
            SELECT *
            FROM Productos
            WHERE productoId=:id
            LIMIT 1
        """
    )
    suspend fun find(id:Int): ProductoEntity?

    @Delete
    suspend fun delete(producto: ProductoEntity)

    @Query("SELECT * FROM Productos")
    fun getAll(): Flow<List<ProductoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(productos: List<ProductoEntity>)

    @Query("SELECT * FROM Productos WHERE codigoBarrasProducto = :codigoBarras LIMIT 1")
    suspend fun findByCodigoBarras(codigoBarras: String): ProductoEntity?

    @Query("SELECT * FROM Productos")
    suspend fun getAllSync(): List<ProductoEntity>
}