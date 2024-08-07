package com.edu.ucne.proyectofinalap2_ronell.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.edu.ucne.proyectofinalap2_ronell.data.local.dao.CategoriaDao
import com.edu.ucne.proyectofinalap2_ronell.data.local.dao.MarcaDao
import com.edu.ucne.proyectofinalap2_ronell.data.local.dao.ProductoDao
import com.edu.ucne.proyectofinalap2_ronell.data.local.dao.UsuarioDao
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.CategoriaEntity
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.MarcaEntity
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.ProductoEntity
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.UsuarioEntity

@Database(
    entities = [
        ProductoEntity::class,
        CategoriaEntity::class,
        MarcaEntity::class,
        UsuarioEntity::class
    ],
    version = 8,
    exportSchema = false
)


abstract class ProductoDb : RoomDatabase() {
    abstract fun productoDao(): ProductoDao

    abstract fun categoriaDao(): CategoriaDao

    abstract fun marcaDao(): MarcaDao

    abstract fun usuarioDao(): UsuarioDao

}