package edu.ucne.nathiel_taveras_ap2_p1.Domain.UseCase

import edu.ucne.nathiel_taveras_ap2_p1.Domain.Repository.EntradasHuacalesRepository
import javax.inject.Inject

class DeleteHuacalesUseCase @Inject constructor(
    private val repository: EntradasHuacalesRepository
) {
    suspend operator fun invoke(id: Int) {
        if (id <= 0) throw IllegalArgumentException("El ID debe ser mayor que 0")
        repository.delete(id)
    }
}