package br.com.alura.helloapp.localData.room.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import br.com.alura.helloapp.localData.room.converter.TypeConverter
import br.com.alura.helloapp.localData.room.entity.Contato
import br.com.alura.helloapp.localData.room.dao.ContatoDao
import br.com.alura.helloapp.localData.room.dao.UsuarioDao
import br.com.alura.helloapp.localData.room.entity.Usuario

@Database(entities = [Contato::class, Usuario::class], version = 5, exportSchema = true, autoMigrations = [AutoMigration(from = 1,to = 2, spec = MIGRATION_1_2::class)])
@TypeConverters(TypeConverter::class)
abstract class HelloAppDatabase : RoomDatabase() {
    abstract fun contatoDao(): ContatoDao
    abstract fun usuarioDao(): UsuarioDao
}

@RenameColumn(tableName = "Usuario", fromColumnName = "profileName", toColumnName = "name")
class MIGRATION_1_2: AutoMigrationSpec

//autoMigrations suportam pequenas alteracoes no Banco de Dados, como:
/*  - adicionar nova coluna a uma tabela existente
    - remover uma coluna de uma tabela existente
    - renomear uma coluna em uma tabela existente
    - alterar o tipo de dados de uma coluna em uma tabla existente
Porem dependendo da atualizacao ela nao eh tao automatica assim e vai requerir que tu faca uma classe herdando de AutoMigrationSpec com a notacao @RenameColumn ou o que vc quiser

  Não suporta: adicionar ou remover uma tabela, adicionar ou remover indices ou chaves unicas, alterar a chave primaria de uma tabela
 */