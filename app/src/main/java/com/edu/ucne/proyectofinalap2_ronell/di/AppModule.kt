package com.edu.ucne.proyectofinalap2_ronell.di

import android.content.Context
import androidx.room.Room
import com.edu.ucne.proyectofinalap2_ronell.data.local.database.ProductoDb
import com.edu.ucne.proyectofinalap2_ronell.data.remote.ProductoApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideProductoDb(@ApplicationContext appContext: Context) : ProductoDb {
        return Room.databaseBuilder(
            appContext,
            ProductoDb::class.java,
            "Producto.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesProductoDao(productodb:ProductoDb) = productodb.productoDao()

    @Provides
    @Singleton
    fun providesCategoriaDao(productodb:ProductoDb) = productodb.categoriaDao()

    @Provides
    @Singleton
    fun providesMarcaDao(productodb:ProductoDb) = productodb.marcaDao()

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideesProductoApi(moshi: Moshi) : ProductoApi {
        return Retrofit.Builder()
            .baseUrl("https://inventariobr.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ProductoApi::class.java)
    }
}