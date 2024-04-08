package br.com.alura.helloapp.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.alura.helloapp.localData.room.dao.ContatoDao
import br.com.alura.helloapp.localData.room.database.HelloAppDatabase
import br.com.alura.helloapp.localData.room.repository.ContatoRepository
import br.com.alura.helloapp.localData.room.repository.UsuarioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "helloApp.db"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): HelloAppDatabase {
        return Room.databaseBuilder(
            context,
            HelloAppDatabase::class.java,
            DATABASE_NAME
        )/*.fallbackToDestructiveMigration()*/
            .addMigrations(Migrations.MIGRATION_2_3)
            .addMigrations(Migrations.MIGRATION_3_4)
            .addMigrations(Migrations.MIGRATION_4_5)
            .build()
    }

    object Migrations{
        val MIGRATION_2_3 = object: Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE 'Contato' ADD COLUMN 'usernameAtual' TEXT NOT NULL DEFAULT ''")
            }
        }

        val MIGRATION_3_4 = object: Migration(3,4){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE 'Usuario' ADD COLUMN 'fotoPerfil' TEXT NOT NULL DEFAULT ''")
            }
        }

        val MIGRATION_4_5 = object: Migration(4,5){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS 'ContatoCopia' ('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'nome' TEXT NOT NULL, 'sobrenome' TEXT NOT NULL, 'telefone' TEXT NOT NULL, 'fotoPerfil' TEXT NOT NULL, 'aniversario' INTEGER, 'usernameAtual' TEXT NOT NULL DEFAULT '', FOREIGN KEY('usernameAtual') REFERENCES 'Usuario'('username') ON UPDATE NO ACTION ON DELETE CASCADE )")
                //esse SQL acima vem pronto na 5.json - gerado pela auto migration do room, em schemas location
                database.execSQL("INSERT INTO ContatoCopia SELECT * FROM Contato") //copiar tabela: Contato -> ContatoCopia
                database.execSQL("DROP TABLE Contato") //excluir tabela Contato
                database.execSQL("ALTER TABLE ContatoCopia RENAME TO Contato") //renomear tabela de ContatoCopia para Contato
            }

        }
    }

    @Provides
    fun provideContatoDao(db: HelloAppDatabase): ContatoDao {
        return db.contatoDao()
    }

    @Provides
    fun provideContatoRepository(db: HelloAppDatabase): ContatoRepository{
        return ContatoRepository(db)
    }

    @Provides
    fun provideUsuarioRepository(db: HelloAppDatabase): UsuarioRepository{
        return UsuarioRepository(db)
    }
}