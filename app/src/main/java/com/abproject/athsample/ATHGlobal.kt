package com.abproject.athsample

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.abproject.athsample.data.database.UserDao
import com.abproject.athsample.data.database.UserDataBase
import com.abproject.athsample.data.repository.MainRepository
import com.abproject.athsample.data.repository.MainRepositoryImpl
import com.abproject.athsample.view.auth.AuthViewModel
import com.abproject.athsample.view.main.MainViewModel
import com.abproject.athsample.view.splash.SplashViewModel
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class ATHGlobal : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@ATHGlobal)
            modules(baseImpl, databaseModule, repositoriesModule, viewModelModule)
        }
        loadUserInformation()
    }

    val baseImpl = module {
        single<SharedPreferences> {
            this@ATHGlobal.getSharedPreferences(
                "ath_file",
                MODE_PRIVATE
            )
        }
    }

    val databaseModule = module {
        fun provideDataBase(application: Application): UserDataBase =
            Room.databaseBuilder(application, UserDataBase::class.java, "note_db")
                .allowMainThreadQueries()
                .build()

        fun provideNoteDao(dataBase: UserDataBase): UserDao = dataBase.userDao
        single { provideDataBase(androidApplication()) }
        single { provideNoteDao(get()) }
    }

    val repositoriesModule = module {
        factory<MainRepository> { MainRepositoryImpl(get()) }
    }

    val viewModelModule = module {
        viewModel { MainViewModel() }
        viewModel { AuthViewModel(get(),get()) }
        viewModel { SplashViewModel(get()) }
    }

    fun loadUserInformation() {
        val authViewModel: AuthViewModel = get()
        authViewModel.checkUsers()
        authViewModel.loadUserExisting()
    }
}