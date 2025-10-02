package edu.ucne.nathiel_taveras_ap2_p1.Domain.UseCase

import edu.ucne.nathiel_taveras_ap2_p1.Domain.Model.EntradasHuacales
import edu.ucne.nathiel_taveras_ap2_p1.Domain.Repository.EntradasHuacalesRepository
import javax.inject.Inject

class GetHuacalesUseCase @Inject constructor(
    private val repository: EntradasHuacalesRepository
) {
    suspend operator fun invoke(id: Int): EntradasHuacales? {
        if (id <= 0) throw IllegalArgumentException("El id debe ser mayor que 0")
        return repository.getEntradas(id)
    }
}