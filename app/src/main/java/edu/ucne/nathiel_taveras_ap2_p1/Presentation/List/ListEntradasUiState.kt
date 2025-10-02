package edu.ucne.nathiel_taveras_ap2_p1.Presentation.List

import edu.ucne.nathiel_taveras_ap2_p1.Domain.Model.EntradasHuacales

data class ListEntradasUiState(
    val isLoading: Boolean = false,
    val entradas: List<EntradasHuacales> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)

