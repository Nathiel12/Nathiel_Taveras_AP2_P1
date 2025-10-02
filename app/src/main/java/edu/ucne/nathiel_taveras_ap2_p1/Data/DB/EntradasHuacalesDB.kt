package edu.ucne.nathiel_taveras_ap2_p1.Data.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.nathiel_taveras_ap2_p1.Data.Local.EntradasHuacalesDAO
import edu.ucne.nathiel_taveras_ap2_p1.Data.Local.EntradasHuacalesEntity

@Database(entities = [EntradasHuacalesEntity::class],
    version = 1,
    exportSchema = false)

abstract class EntradasHuacalesDB: RoomDatabase() {

    abstract fun entradasDao(): EntradasHuacalesDAO
}