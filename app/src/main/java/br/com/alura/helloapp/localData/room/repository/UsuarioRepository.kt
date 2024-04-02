package br.com.alura.helloapp.localData.room.repository

import br.com.alura.helloapp.localData.room.database.HelloAppDatabase
import br.com.alura.helloapp.localData.room.entity.Usuario
import javax.inject.Inject

class UsuarioRepository @Inject constructor(db: HelloAppDatabase) {

    val usuarioDao = db.usuarioDao()

    fun insert(usuario: Usuario){
        val usuarioPesquisado = searchUsername(usuario.username)
        usuarioPesquisado?.let {
            //nao inserir se ja existe
        } ?: return usuarioDao.insert(usuario)
    }

    fun delete(usuario: Usuario){
        val usuarioPesquisado = searchUsername(usuario.username)
        usuarioPesquisado?.let {
            return usuarioDao.delete(usuario)
        }
    }

    fun update(usuario: Usuario){
        val usuarioPesquisado = searchUsername(usuario.username)
        usuarioPesquisado?.let {
            return usuarioDao.update(usuario)
        }
    }

    fun searchUsername(username: String): Usuario?{
        return usuarioDao.searchUserFromUsername(username)
    }
}