package edu.ucne.nathiel_taveras_ap2_p1.Presentation.List

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.nathiel_taveras_ap2_p1.Domain.UseCase.DeleteHuacalesUseCase
import edu.ucne.nathiel_taveras_ap2_p1.Domain.UseCase.ObserveHuacalesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListEntradasViewModel @Inject constructor(
    private val observeEntradasUseCase: ObserveHuacalesUseCase,
    private val deleteEntradasUseCase: DeleteHuacalesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ListEntradasUiState(isLoading = true))
    val state: StateFlow<ListEntradasUiState> = _state.asStateFlow()

    init {
        onEvent(ListEntradasUiEvent.Load)
    }

    fun onEvent(event: ListEntradasUiEvent) {
        when (event) {
            ListEntradasUiEvent.Load -> observeEntradas()
            is ListEntradasUiEvent.Delete -> onDelete(event.id)
            ListEntradasUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListEntradasUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
            is ListEntradasUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
        }
    }

    private fun observeEntradas() {
        viewModelScope.launch {
            observeEntradasUseCase().collectLatest { entradasList ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        entradas = entradasList,
                        message = null
                    )
                }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            try {
                deleteEntradasUseCase(id)
                onEvent(ListEntradasUiEvent.ShowMessage("Entrada eliminado"))
            } catch (e: Exception) {
                onEvent(ListEntradasUiEvent.ShowMessage("Error al eliminar: ${e.message}"))
            }
        }
    }

    fun onNavigationHandled() {
        _state.update {
            it.copy(
                navigateToCreate = false,
                navigateToEditId = null,
                message = null
            )
        }
    }
}