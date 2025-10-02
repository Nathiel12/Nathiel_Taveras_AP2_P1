package edu.ucne.nathiel_taveras_ap2_p1.Presentation.Edit

data class EditEntradasUiState(
    val IdEntrada: Int? = null,
    val fecha: String = "",
    val nombreCliente: String = "",
    val cantidad: Int =0,
    val precio: Double = 0.1,
    val precioError: String? = null,
    val nombreError: String? = null,
    val cantidadError: String? = null,
    val fechaError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false
)