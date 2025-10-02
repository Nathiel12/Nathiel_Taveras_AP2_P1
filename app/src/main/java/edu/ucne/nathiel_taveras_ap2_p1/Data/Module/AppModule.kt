package edu.ucne.nathiel_taveras_ap2_p1.Data.Module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.nathiel_taveras_ap2_p1.Data.DB.EntradasHuacalesDB
import edu.ucne.nathiel_taveras_ap2_p1.Data.Local.EntradasHuacalesDAO
import edu.ucne.nathiel_taveras_ap2_p1.Data.Repository.EntradasHuacalesRepositoryImpl
import edu.ucne.nathiel_taveras_ap2_p1.Domain.Repository.EntradasHuacalesRepository
import jakarta.inject.Singleton

@InstallIn(
    SingletonComponent::class)
@Module

object AppModule {
    @Provides
    @Singleton
    fun provideEntradasHuacalesDB(@ApplicationContext appContext: Context): EntradasHuacalesDB {
        return Room.databaseBuilder(
                appContext,
            EntradasHuacalesDB::class.java,
                "EntradasHuacales_DB"
            ).fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideEntradasHuacalesDao(entradasHuacalesDB: EntradasHuacalesDB): EntradasHuacalesDAO {
        return entradasHuacalesDB.entradasDao()
    }

    @Provides
    @Singleton
    fun provideEntradasHuacalesRepository(entradasDao: EntradasHuacalesDAO): EntradasHuacalesRepository {
        return EntradasHuacalesRepositoryImpl(entradasDao)
    }

    @Provides
    @Singleton
    fun provideEntradasHuacalesRepositoryImpl(entradasDao: EntradasHuacalesDAO): EntradasHuacalesRepositoryImpl{
        return EntradasHuacalesRepositoryImpl(entradasDao)
    }
}