package br.com.alura.helloapp.localData.room.repository

import br.com.alura.helloapp.localData.room.database.HelloAppDatabase
import br.com.alura.helloapp.localData.room.entity.Usuario
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsuarioRepository @Inject constructor(db: HelloAppDatabase) {

    private val usuarioDao = db.usuarioDao()

    suspend fun insert(usuario: Usuario){
        val usuarioPesquisado = searchUsername(usuario.username)
        usuarioPesquisado?.let {
            //nao inserir se ja existe
        } ?: return usuarioDao.insert(usuario)
    }

    suspend fun delete(usuario: Usuario){
        val usuarioPesquisado = searchUsername(usuario.username)
        usuarioPesquisado?.let {
            return usuarioDao.delete(usuario)
        }
    }

    suspend fun update(usuario: Usuario){
        val usuarioPesquisado = searchUsername(usuario.username)
        usuarioPesquisado?.let {
            return usuarioDao.update(usuario)
        }
    }

    private suspend fun searchUsername(username: String): Usuario?{
        return usuarioDao.searchUserFromUsername(username)
    }

    fun getAllUsers(): Flow<List<Usuario>>{
        return usuarioDao.getAllUsers()
    }
}