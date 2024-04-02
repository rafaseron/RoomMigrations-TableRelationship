package br.com.alura.helloapp.localData.room.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import br.com.alura.helloapp.localData.room.entity.Contato
import kotlinx.coroutines.flow.Flow

@Dao
interface ContatoDao {

    @Insert(onConflict = REPLACE)
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
}