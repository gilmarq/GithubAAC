package com.example.logonrmlocal.githubaac.di.modules

import android.app.Application
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import com.example.logonrmlocal.githubaac.data.local.MeuBancoDeDados
import com.example.logonrmlocal.githubaac.data.local.dao.UserDao
import com.example.logonrmlocal.githubaac.data.remote.UserWebService
import com.example.logonrmlocal.githubaac.data.repositories.UserRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Provides
    @Singleton
    fun providedDataBase(application: Application): MeuBancoDeDados {
        return Room.databaseBuilder(
                application,
                MeuBancoDeDados::class.java,
                "meuqueridobanco.bd"
        ).build()
    }


    @Provides
    @Singleton
    fun provideUserDao(database: MeuBancoDeDados): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }


    @Provides
    @Singleton
    fun provodeRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.github.com")
                .build()
    }

     @Provides
     @Singleton
     fun provideUserWebService(retrofit: Retrofit):UserWebService{
        return  retrofit.create(UserWebService::class.java)
     }

     @Provides
     @Singleton
     fun provideUserRepository(
            webServece:UserWebService,
            userDao: UserDao,
            executor: Executor
     ): UserRepository{
        return  UserRepository(webServece,userDao,executor)
     }

}