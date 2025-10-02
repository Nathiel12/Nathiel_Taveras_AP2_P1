package edu.ucne.nathiel_taveras_ap2_p1.Presentation.Edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.nathiel_taveras_ap2_p1.Domain.Model.EntradasHuacales
import edu.ucne.nathiel_taveras_ap2_p1.Domain.Repository.EntradasHuacalesRepository
import edu.ucne.nathiel_taveras_ap2_p1.Domain.UseCase.DeleteHuacalesUseCase
import edu.ucne.nathiel_taveras_ap2_p1.Domain.UseCase.GetHuacalesUseCase
import edu.ucne.nathiel_taveras_ap2_p1.Domain.UseCase.UpsertHuacalesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditEntradasViewModel @Inject constructor(
    private val getHuacalesUseCase: GetHuacalesUseCase,
    private val upsertHuacalesUseCase: UpsertHuacalesUseCase,
    private val deleteLogroUseCase: DeleteHuacalesUseCase,
    private val entradasHuacalesRepository: EntradasHuacalesRepository
) : ViewModel(){
    private val _state = MutableStateFlow(EditEntradasUiState())
    val state: StateFlow<EditEntradasUiState> = _state.asStateFlow()

    fun onEvent(event: EditEntradasUiEvent){
        when(event){
            is EditEntradasUiEvent.Load -> onLoad(event.id)
            is EditEntradasUiEvent.Save -> onSave()
            is EditEntradasUiEvent.Delete -> onDelete()
            is EditEntradasUiEvent.NombreClienteChanged -> onNombreChanged(event.value)
            is EditEntradasUiEvent.CantidadChanged -> onCantidadChanged(event.value)
            is EditEntradasUiEvent.PrecioChanged -> onPrecioChanged(event.value)
            is EditEntradasUiEvent.FechaChanged -> onFechaChanged(event.value)
        }
    }

    private fun onLoad(id: Int?){
        if(id == null || id == 0){
            _state.update { it.copy(isNew = true, IdEntrada = null) }
            return
        }
        viewModelScope.launch{
            val entrada = getHuacalesUseCase(id)
            if(entrada != null){
                _state.update {
                    it.copy(
                        isNew = false,
                        IdEntrada = entrada.IdEntrada,
                        nombreCliente = entrada.NombreCliente,
                        cantidad = entrada.Cantidad,
                        precio = entrada.Precio,
                        fecha = entrada.Fecha
                    )
                }
            }
        }
    }

    private fun onNombreChanged(nombre: String){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    nombreCliente = nombre,
                    nombreError = if(nombre.isBlank()) "Nombre requerido" else null
                )
            }
        }
    }

    private fun onFechaChanged(fecha: String){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    fecha = fecha,
                    fechaError = if(fecha.isBlank()) "Fecha requerida" else null
                )
            }
        }
    }

    private fun onCantidadChanged(cantidad: String){
        val cantidadInt = cantidad.toIntOrNull()
        viewModelScope.launch {
            _state.update {
                it.copy(
                    cantidad = cantidadInt ?: 0,
                    cantidadError = if(cantidadInt == null || cantidadInt <= 0) "Cantidad requerida" else null
                )
            }
        }
    }

    private fun onPrecioChanged(precio: String){
        val precioInt = precio.toDoubleOrNull()
        viewModelScope.launch {
            _state.update {
                it.copy(
                    precio = precioInt ?: 0.0,
                    precioError = if(precioInt == null || precioInt <= 0.0) "Precio requerido" else null
                )
            }
        }
    }

    private fun onSave(){
        viewModelScope.launch {
            if(_state.value.nombreCliente.isBlank()){
                _state.update { it.copy(nombreError = "Nombre requerido") }
                return@launch
            }
            if(_state.value.fecha.isBlank()){
                _state.update { it.copy(fechaError = "Fecha requerida") }
                return@launch
            }
            _state.update { it.copy(isSaving = true) }

            try{
                val entrada = EntradasHuacales(
                    IdEntrada = _state.value.IdEntrada ?: 0,
                    Fecha = _state.value.fecha,
                    NombreCliente = _state.value.nombreCliente,
                    Cantidad = _state.value.cantidad,
                    Precio = _state.value.precio
                )

                upsertHuacalesUseCase(entrada)

                _state.update {
                    it.copy(
                        isSaving = false,
                        saved = true
                    )
                }

            }catch (e: Exception){
                _state.update {
                    it.copy(
                        isSaving = false,
                        nombreError = "Error al guardar: ${e.message}"
                    )
                }
            }
        }
    }
    private fun onDelete(){
        val id = _state.value.IdEntrada?: return

        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            try {
                deleteLogroUseCase(id)
                _state.update { it.copy(isDeleting = false, deleted = true) }
            }catch (e: Exception){
                _state.update {
                    it.copy(
                        isDeleting = false,
                        nombreError = "Error al eliminar: ${e.message}"
                    )
                }
            }
        }
    }

}