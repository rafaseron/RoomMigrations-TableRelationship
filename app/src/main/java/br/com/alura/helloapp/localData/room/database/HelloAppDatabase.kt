package br.com.alura.helloapp.localData.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.helloapp.localData.room.converter.TypeConverter
import br.com.alura.helloapp.localData.room.entity.Contato
import br.com.alura.helloapp.localData.room.dao.ContatoDao

@Database(entities = [Contato::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class HelloAppDatabase : RoomDatabase() {
    abstract fun contatoDao(): ContatoDao
}