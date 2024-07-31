package com.edu.ucne.proyectofinalap2_ronell.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.CategoriaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriaDao {
    @Upsert
    suspend fun saveCategoria(categoria: CategoriaEntity)

    @Query(
        """
            SELECT *
            FROM Categorias
            WHERE categoriaId=:id
            LIMIT 1
        """
    )

    suspend fun findCategoria(id: Int) : CategoriaEntity?

    @Delete
    suspend fun deleteCategoria(categoria: CategoriaEntity)

    @Query("SELECT * FROM Categorias")
    fun getAllCategorias(): Flow<List<CategoriaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategorias(categorias: List<CategoriaEntity>)
}