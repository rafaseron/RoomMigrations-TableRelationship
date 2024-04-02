package br.com.alura.helloapp.localData.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.alura.helloapp.localData.room.entity.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioDao {

    @Insert
    suspend fun insert(usuario: Usuario)

    @Delete
    suspend fun delete(usuario: Usuario)

    @Update
    suspend fun update(usuario: Usuario)

    @Query("SELECT * FROM Usuario WHERE username = :username")
    suspend fun searchUserFromUsername(username: String): Usuario?

    @Query("SELECT * FROM Usuario")
    fun getAllUsers(): Flow<List<Usuario>>
}