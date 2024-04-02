package br.com.alura.helloapp.localData.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.alura.helloapp.localData.room.entity.Usuario

interface UsuarioDao {

    @Insert
    fun insert(usuario: Usuario)

    @Delete
    fun delete(usuario: Usuario)

    @Update
    fun update(usuario: Usuario)

    @Query("SELECT * FROM Usuario WHERE username = :username")
    fun searchUserFromUsername(username: String): Usuario?
}