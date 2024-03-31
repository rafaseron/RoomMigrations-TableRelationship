package br.com.alura.helloapp.localData.room.converter

import androidx.room.TypeConverter
import java.util.*

class TypeConverter {
    @TypeConverter
    fun deDateParaLong(valor: Date?): Long?{
        return valor?.time
    }

    @TypeConverter
    fun deLongParaDate(valor: Long?): Date?{
        return valor?.let { Date(it) }
    }
}