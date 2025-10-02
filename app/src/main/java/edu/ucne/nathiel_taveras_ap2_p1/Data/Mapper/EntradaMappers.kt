package edu.ucne.nathiel_taveras_ap2_p1.Data.Mapper

import edu.ucne.nathiel_taveras_ap2_p1.Data.Local.EntradasHuacalesEntity
import edu.ucne.nathiel_taveras_ap2_p1.Domain.Model.EntradasHuacales

fun EntradasHuacalesEntity.toDomain(): EntradasHuacales {
    return EntradasHuacales(
        IdEntrada = IdEntrada,
        Fecha = Fecha,
        NombreCliente = NombreCliente,
        Cantidad = Cantidad,
        Precio = Precio
    )
}

fun EntradasHuacales.toEntity(): EntradasHuacalesEntity {
    return EntradasHuacalesEntity(
        IdEntrada = IdEntrada,
        Fecha = Fecha,
        NombreCliente = NombreCliente,
        Cantidad = Cantidad,
        Precio = Precio

    )
}