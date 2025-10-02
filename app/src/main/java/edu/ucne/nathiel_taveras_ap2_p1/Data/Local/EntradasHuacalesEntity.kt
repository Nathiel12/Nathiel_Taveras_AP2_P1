package edu.ucne.nathiel_taveras_ap2_p1.Data.Local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EntradasHuacales")
class EntradasHuacalesEntity(
    @PrimaryKey(autoGenerate = true)
    val IdEntrada: Int = 0,
    val Fecha: String = "",
    val NombreCliente: String = "",
    val Cantidad: Int,
    val Precio: Float
)
