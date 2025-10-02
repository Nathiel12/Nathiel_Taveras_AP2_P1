package edu.ucne.nathiel_taveras_ap2_p1.Domain.Repository

import edu.ucne.nathiel_taveras_ap2_p1.Domain.Model.EntradasHuacales
import kotlinx.coroutines.flow.Flow

interface EntradasHuacalesRepository {

    fun observeEntradas(): Flow<List<EntradasHuacales>>

    suspend fun getEntradas(id:Int): EntradasHuacales?

    suspend fun upsert(entradasHuacales: EntradasHuacales):Int

    suspend fun delete(id:Int)
}