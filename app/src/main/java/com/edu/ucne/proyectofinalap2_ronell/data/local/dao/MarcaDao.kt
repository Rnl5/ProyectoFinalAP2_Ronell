package com.edu.ucne.proyectofinalap2_ronell.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.MarcaEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MarcaDao {

    @Upsert
    suspend fun saveMarca(marca: MarcaEntity)

    @Query(
        """
            SELECT *
            FROM Marcas
            WHERE marcaId=:id
            LIMIT 1
        """
    )
    suspend fun findMarca(id: Int): MarcaEntity?

    @Delete
    suspend fun deleteMarca(marca: MarcaEntity)


    @Query("SELECT * FROM Marcas")
    fun getAllMarcas(): Flow<List<MarcaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMarca(marcas: List<MarcaEntity>)
}