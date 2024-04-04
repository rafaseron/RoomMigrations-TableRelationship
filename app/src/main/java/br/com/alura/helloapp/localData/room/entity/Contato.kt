package br.com.alura.helloapp.localData.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Contato(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "nome")
    val nome: String = "",
    @ColumnInfo(name = "sobrenome")
    val sobrenome: String = "",
    @ColumnInfo(name = "telefone")
    val telefone: String = "",
    @ColumnInfo(name = "fotoPerfil")
    val fotoPerfil: String = "",
    @ColumnInfo(name = "aniversario")
    val aniversario: Date? = null,
    @ColumnInfo(name = "usernameAtual", defaultValue = "")
    val usernameAtual: String = ""
)