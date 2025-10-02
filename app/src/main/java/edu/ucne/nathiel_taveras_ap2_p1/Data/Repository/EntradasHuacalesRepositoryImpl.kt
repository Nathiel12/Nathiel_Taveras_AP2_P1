package edu.ucne.nathiel_taveras_ap2_p1.Data.Repository

import edu.ucne.nathiel_taveras_ap2_p1.Data.Local.EntradasHuacalesDAO
import edu.ucne.nathiel_taveras_ap2_p1.Domain.Model.EntradasHuacales
import edu.ucne.nathiel_taveras_ap2_p1.Domain.Repository.EntradasHuacalesRepository
import edu.ucne.nathiel_taveras_ap2_p1.Data.Mapper.toDomain
import edu.ucne.nathiel_taveras_ap2_p1.Data.Mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EntradasHuacalesRepositoryImpl @Inject constructor(
    private val entradasHuacalesDAO: EntradasHuacalesDAO
) : EntradasHuacalesRepository{

    override fun observeEntradas(): Flow<List<EntradasHuacales>> {
        return entradasHuacalesDAO.observerAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getEntradas(id: Int): EntradasHuacales? {
        return entradasHuacalesDAO.getById(id)?.toDomain()
    }

    override suspend fun upsert(entradasHuacales: EntradasHuacales): Int {
        val entity = entradasHuacales.toEntity()
        val result = entradasHuacalesDAO.upsert(entity)
        return if (entradasHuacales.IdEntrada == 0) result.toInt() else entradasHuacales.IdEntrada
    }

    override suspend fun delete(id: Int) {
        entradasHuacalesDAO.deleteById(id)
    }
}

