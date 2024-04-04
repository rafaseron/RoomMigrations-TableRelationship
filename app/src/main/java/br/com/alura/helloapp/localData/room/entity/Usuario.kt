package br.com.alura.helloapp.localData.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario (
    @ColumnInfo(name = "name")
    val name: String,
    @PrimaryKey
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "fotoPerfil", defaultValue = "")
    val fotoPerfil: String = ""
)