package edu.ucne.nathiel_taveras_ap2_p1.Presentation.Edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun EditEntradasScreen(
    IdEntrada: Int?,
    onSaveComplete: () -> Unit,
    onDeleteComplete: () -> Unit,
    viewModel: EditEntradasViewModel = hiltViewModel()
) {
    LaunchedEffect(IdEntrada) {
        viewModel.onEvent(EditEntradasUiEvent.Load(IdEntrada))
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(state.saved) {
        if (state.saved) {
            onSaveComplete()
        }
    }
    LaunchedEffect(state.deleted) {
        if (state.deleted) {
            onDeleteComplete()
        }
    }
    EditEntradasBody(state, viewModel::onEvent)
}

@Composable
private fun EditEntradasBody(
    state: EditEntradasUiState,
    onEvent: (EditEntradasUiEvent) -> Unit
) {

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.nombreCliente,
                onValueChange = { onEvent(EditEntradasUiEvent.NombreClienteChanged(it)) },
                label = { Text("Nombre") },
                isError = state.nombreError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.nombreError != null) {
                Text(
                    state.nombreError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.cantidad.toString() ?: "",
                onValueChange = { onEvent(EditEntradasUiEvent.CantidadChanged(it)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Cantidad:") },
                isError = state.cantidadError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.cantidadError != null) {
                Text(
                    state.cantidadError,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.fecha,
                onValueChange = { onEvent(EditEntradasUiEvent.FechaChanged(it)) },
                placeholder = { Text(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))) },
                label = { Text("Fecha") },
                isError = state.cantidadError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.fechaError != null) Text(
                state.fechaError,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = state.precio.toString() ?: "",
                onValueChange = { onEvent(EditEntradasUiEvent.PrecioChanged(it)) },
                label = { Text("Precio") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.precioError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.precioError != null) Text(
                state.precioError,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(Modifier.height(16.dp))

            Row {
                Button(
                    onClick = { onEvent(EditEntradasUiEvent.Save) },
                    enabled = !state.isSaving,
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Guardar") }
                Spacer(Modifier.fillMaxWidth())
                if (!state.isNew) {
                    OutlinedButton(
                        onClick = { onEvent(EditEntradasUiEvent.Delete) },
                        enabled = !state.isDeleting
                    ) { Text("Eliminar") }
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditEntradasBodyPreview() {
    val state = EditEntradasUiState()
    MaterialTheme {
        EditEntradasBody(state = state) {}
    }
}