package br.com.alura.helloapp.localData.room.repository

import br.com.alura.helloapp.localData.room.converter.HashConverter.Companion.toHash256
import br.com.alura.helloapp.localData.room.database.HelloAppDatabase
import br.com.alura.helloapp.localData.room.entity.Usuario
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsuarioRepository @Inject constructor(db: HelloAppDatabase) {

    private val usuarioDao = db.usuarioDao()

    suspend fun insert(usuario: Usuario){
        val newUser = Usuario(name = usuario.name, username = usuario.username, password = usuario.password.toHash256(), fotoPerfil = usuario.fotoPerfil)
        val usuarioPesquisado = searchUsername(usuario.username)
        usuarioPesquisado?.let {
            //nao inserir se ja existe
        } ?: return usuarioDao.insert(newUser)
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

    suspend fun searchUsername(username: String): Usuario?{
        return usuarioDao.searchUserFromUsername(username)
    }

    suspend fun autenticarUsuario(username: String, password: String): Boolean{
        val pesquisarUsuario = searchUsername(username)
        pesquisarUsuario?.let {
            return it.password == password.toHash256()
        }?: return false
    }

    fun getAllUsers(): Flow<List<Usuario>>{
        return usuarioDao.getAllUsers()
    }
}