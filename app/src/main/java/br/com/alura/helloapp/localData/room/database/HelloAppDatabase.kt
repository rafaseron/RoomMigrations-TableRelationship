package br.com.alura.helloapp.localData.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.helloapp.localData.room.converter.TypeConverter
import br.com.alura.helloapp.localData.room.entity.Contato
import br.com.alura.helloapp.localData.room.dao.ContatoDao
import br.com.alura.helloapp.localData.room.dao.UsuarioDao
import br.com.alura.helloapp.localData.room.entity.Usuario

@Database(entities = [Contato::class, Usuario::class], version = 2)
@TypeConverters(TypeConverter::class)
abstract class HelloAppDatabase : RoomDatabase() {
    abstract fun contatoDao(): ContatoDao
    abstract fun usuarioDao(): UsuarioDao
}