package edu.ucne.nathiel_taveras_ap2_p1.Presentation.List

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.nathiel_taveras_ap2_p1.Domain.Model.EntradasHuacales

@Composable
fun EntradasListScreen(
    onNavigateToEdit: (Int) -> Unit,
    onNavigateToCreate: () -> Unit,
    viewModel: ListEntradasViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EntradasListBody(
        state = state,
        onEvent = { event ->
            when (event) {
                is ListEntradasUiEvent.Edit -> onNavigateToEdit(event.id)
                is ListEntradasUiEvent.CreateNew -> onNavigateToCreate()
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@Composable
private fun EntradasListBody(
    state: ListEntradasUiState,
    onEvent: (ListEntradasUiEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            Row {
                FloatingActionButton(onClick = { onEvent(ListEntradasUiEvent.CreateNew) }) {
                    Text("+")
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(state.entradas) { entrada ->
                    EntradaCard(
                        entrada = entrada,
                        onClick = { onEvent(ListEntradasUiEvent.Edit(entrada.IdEntrada)) },
                        onDelete = { onEvent(ListEntradasUiEvent.Delete(entrada.IdEntrada)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun EntradaCard(
    entrada: EntradasHuacales,
    onClick: (EntradasHuacales) -> Unit,
    onDelete: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(entrada) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(entrada.NombreCliente, style = MaterialTheme.typography.titleMedium)
                Text("Cantidad: ${entrada.Cantidad}")
                Text("Precio: ${entrada.Precio}")
            }
            IconButton(onClick = { onDelete(entrada.IdEntrada) }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}

@Preview
@Composable
private fun PlayerListBodyPreview() {
    MaterialTheme {
        val state = ListEntradasUiState(
            entradas = listOf(
                EntradasHuacales(IdEntrada = 1, NombreCliente = "Juan Pérez", Cantidad = 2, Precio = 1000.00),
                EntradasHuacales(IdEntrada = 2, NombreCliente = "María García", Cantidad= 3, Precio = 2000.00)
            )
        )
        EntradasListBody(
            state = state,
            onEvent = {}
        )
    }
}