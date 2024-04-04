package br.com.alura.helloapp.localData.room.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.helloapp.localData.room.converter.TypeConverter
import br.com.alura.helloapp.localData.room.entity.Contato
import br.com.alura.helloapp.localData.room.dao.ContatoDao
import br.com.alura.helloapp.localData.room.dao.UsuarioDao
import br.com.alura.helloapp.localData.room.entity.Usuario

@Database(entities = [Contato::class, Usuario::class], version = 2, exportSchema = true, /* autoMigrations = [AutoMigration(1,2), AutoMigration(3,4)] */)
@TypeConverters(TypeConverter::class)
abstract class HelloAppDatabase : RoomDatabase() {
    abstract fun contatoDao(): ContatoDao
    abstract fun usuarioDao(): UsuarioDao
}

//autoMigrations suportam pequenas alteracoes no Banco de Dados, como:
/*  - adicionar nova coluna a uma tabela existente
    - remover uma coluna de uma tabela existente
    - renomear uma coluna em uma tabela existente
    - alterar o tipo de dados de uma coluna em uma tabla existente

  Não suporta: adicionar ou remover uma tabela, adicionar ou remover indices ou chaves unicas, alterar a chave primaria de uma tabela
 */