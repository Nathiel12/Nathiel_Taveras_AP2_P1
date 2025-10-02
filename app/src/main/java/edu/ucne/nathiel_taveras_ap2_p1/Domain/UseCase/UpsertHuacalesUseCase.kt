package edu.ucne.nathiel_taveras_ap2_p1.Domain.UseCase

import edu.ucne.nathiel_taveras_ap2_p1.Domain.Model.EntradasHuacales
import edu.ucne.nathiel_taveras_ap2_p1.Domain.Repository.EntradasHuacalesRepository
import javax.inject.Inject

class UpsertHuacalesUseCase @Inject constructor(
    private val repository: EntradasHuacalesRepository
) {
    suspend operator fun invoke(entradasHuacales: EntradasHuacales): Result<Int> {

        if (entradasHuacales.NombreCliente.isBlank()) {
            return Result.failure(IllegalArgumentException("El nombre es requerido"))
        }

        if (entradasHuacales.Cantidad == null) {
            return Result.failure(IllegalArgumentException("La cantidad es requerida"))
        }

        if (entradasHuacales.Precio == null) {
            return Result.failure(IllegalArgumentException("El precio es requerido"))
        }

        return runCatching {
            repository.upsert(entradasHuacales)
        }
    }
}