package com.edu.ucne.proyectofinalap2_ronell.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun BotonFlotante(
    title: String,
    onAgregarTecnico: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = onAgregarTecnico
    ) {
        Text(text = title)
        Icon(Icons.Filled.Add, "Agregar Tecnico")
    }
}