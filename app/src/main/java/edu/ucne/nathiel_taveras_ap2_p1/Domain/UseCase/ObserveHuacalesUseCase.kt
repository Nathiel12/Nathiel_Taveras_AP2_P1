package edu.ucne.nathiel_taveras_ap2_p1.Domain.UseCase

import edu.ucne.nathiel_taveras_ap2_p1.Domain.Model.EntradasHuacales
import edu.ucne.nathiel_taveras_ap2_p1.Domain.Repository.EntradasHuacalesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveHuacalesUseCase @Inject constructor(
    private val repository: EntradasHuacalesRepository
) {
    operator fun invoke(): Flow<List<EntradasHuacales>> {
        return repository.observeEntradas()
    }
}