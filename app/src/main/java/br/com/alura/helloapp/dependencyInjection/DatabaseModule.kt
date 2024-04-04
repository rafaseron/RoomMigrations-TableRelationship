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
            /*.addMigrations(Migrations.MIGRATION_1_2)*/
            .build()
    }

    object Migrations{
        val MIGRATION_1_2 = object: Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS 'Usuario' ('name' TEXT NOT NULL, 'username' TEXT NOT NULL, 'password' TEXT NOT NULL, PRIMARY KEY('username'))")
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