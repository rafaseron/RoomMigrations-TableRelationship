package br.com.alura.helloapp.localData.room.dao

import androidx.room.*
import br.com.alura.helloapp.localData.room.entity.Contato
import kotlinx.coroutines.flow.Flow

@Dao
interface ContatoDao {

    @Insert
    suspend fun insert(contato: Contato)

    @Delete
    suspend fun delete(contato: Contato)

    @Update
    suspend fun update(contato: Contato)

    @Query("SELECT * FROM Contato")
    fun buscaTodos(): Flow<List<Contato>>

    @Query("SELECT * FROM Contato WHERE id = :id")
    fun buscaPorId(id: Long): Flow<Contato?>

    @Query("DELETE FROM Contato WHERE id = :id")
    suspend fun deleta(id: Long)

    @Query("SELECT * FROM Contato WHERE usernameAtual = :username")
    fun getContactsFromUsername(username: String): Flow<List<Contato>>

    @Query("DELETE FROM Contato WHERE usernameAtual = :username")
    suspend fun deleteAllContactsFromUsername(username: String)
}