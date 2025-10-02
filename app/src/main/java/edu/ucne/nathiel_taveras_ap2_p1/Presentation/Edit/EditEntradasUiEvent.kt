package edu.ucne.nathiel_taveras_ap2_p1.Presentation.Edit

sealed interface EditEntradasUiEvent {
    data class Load(val id: Int?) : EditEntradasUiEvent
    data class NombreClienteChanged(val value: String) : EditEntradasUiEvent
    data class FechaChanged(val value: String) : EditEntradasUiEvent
    data class PrecioChanged(val value: String) : EditEntradasUiEvent
    data class CantidadChanged(val value: String) : EditEntradasUiEvent
    data object Save : EditEntradasUiEvent
    data object Delete : EditEntradasUiEvent
}