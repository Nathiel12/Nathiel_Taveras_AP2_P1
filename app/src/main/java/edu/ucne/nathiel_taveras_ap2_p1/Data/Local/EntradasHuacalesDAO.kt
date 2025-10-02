package edu.ucne.nathiel_taveras_ap2_p1.Data.Local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.nathiel_taveras_ap2_p1.Data.Local.EntradasHuacalesEntity
import edu.ucne.nathiel_taveras_ap2_p1.Domain.Model.EntradasHuacales
import kotlinx.coroutines.flow.Flow

@Dao
interface EntradasHuacalesDAO{
    @Query("SELECT *FROM entradas ORDER BY IdEntrada DESC")
    fun observerAll(): Flow<List<EntradasHuacalesEntity>>

    @Query("SELECT* FROM entradas WHERE IdEntrada=:id")
    suspend fun getById(id:Int): EntradasHuacalesEntity?

    @Query("DELETE FROM entradas WHERE IdEntrada=:id")
    suspend fun deleteById(id:Int)

    @Upsert
    suspend fun upsert(entrada: EntradasHuacalesEntity):Long

    @Delete
    suspend fun delete(entity: EntradasHuacalesEntity)
}
