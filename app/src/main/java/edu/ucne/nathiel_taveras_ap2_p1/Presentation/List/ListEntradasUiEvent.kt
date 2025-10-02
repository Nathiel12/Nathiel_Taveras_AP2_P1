package edu.ucne.nathiel_taveras_ap2_p1.Presentation.List

sealed interface ListEntradasUiEvent{
    data object Load: ListEntradasUiEvent
    data class Delete(val id: Int) : ListEntradasUiEvent
    data object CreateNew: ListEntradasUiEvent
    data class Edit(val id: Int) : ListEntradasUiEvent
    data class ShowMessage(val message: String) : ListEntradasUiEvent
}