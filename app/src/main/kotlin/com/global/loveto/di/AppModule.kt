package com.global.loveto.di

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import androidx.room.Room
import com.global.loveto.DATABASE_NAME
import com.global.loveto.URL_BASE_LOVE_TO_API
import com.global.loveto.data.local.database.AppDatabase
import com.global.loveto.data.local.source.OperationLocalDataSource
import com.global.loveto.data.local.source.FarmerLocalDataSource
import com.global.loveto.data.remote.net.OperationRemoteApi
import com.global.loveto.data.remote.net.FarmerRemoteApi
import com.global.loveto.data.remote.net.LoginRemoteApi
import com.global.loveto.data.remote.source.OperationRemoteDataSource
import com.global.loveto.data.remote.source.FarmerRemoteDataSource
import com.global.loveto.data.remote.source.LoginRemoteDataSource
import com.global.loveto.data.repository.OperationRepository
import com.global.loveto.data.repository.FarmerRepository
import com.global.loveto.data.repository.LoginRepository
import com.global.loveto.domain.usecase.*
import com.global.loveto.navigation.Navigator
import com.global.loveto.permissions.PermissionsHelper
import com.global.loveto.preferences.SharedPreferencesHelper
import com.global.loveto.presentation.*
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    /* Android Services */
    single {
        androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
     single<SharedPreferences> {
        androidContext().getSharedPreferences(
            SharedPreferencesHelper.SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }
    single {
        GsonBuilder().create()
    }

    /* Retrofit */
    single {
        val loggin = HttpLoggingInterceptor()
        loggin.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder()
            .callTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
//            .addInterceptor(ChuckInterceptor(androidContext()))
            //           .addInterceptor(AuthInterceptor(AppPreferences))
            //         .addInterceptor(RefreshAuthTokenInterceptor(AppPreferences))
            .addInterceptor(loggin)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(URL_BASE_LOVE_TO_API)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        PermissionsHelper(androidContext())
    }

    single {
        Navigator()
    }

    single { get<Retrofit>().create(LoginRemoteApi::class.java) as LoginRemoteApi }
    single { get<Retrofit>().create(FarmerRemoteApi::class.java) as FarmerRemoteApi }
    single { get<Retrofit>().create(OperationRemoteApi::class.java) as OperationRemoteApi }

    /*Database*/
    scope(named("session")) {
        scoped {
            Room.databaseBuilder(
                androidApplication(),
                AppDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }

    /* Dao Interfaces */
    factory { getScope("session").get<AppDatabase>().userDao()}
    factory { getScope("session").get<AppDatabase>().farmerDao()}
    factory { getScope("session").get<AppDatabase>().operationDao()}

    // DataSource
    factory { LoginRemoteDataSource(get())}
    factory { FarmerRemoteDataSource(get())}
    factory { FarmerLocalDataSource(get()) }
    factory { OperationRemoteDataSource(get()) }
    factory { OperationLocalDataSource(get()) }

    // Repositories
    factory { LoginRepository(get())}
    factory { FarmerRepository(get(),get())}
    factory { OperationRepository(get(),get()) }

    // View models
    viewModel { LoginViewModel(get())}
    viewModel { FarmersViewModel(get()) }
    viewModel { ClaimViewModel(get(),get()) }
    viewModel { AgreementViewModel(get(),get()) }
    viewModel { OperationsViewModel(get(),get(),get(),get()) }

    // UseCases
    factory { LoginUseCase(get()) }
    factory { GetFarmersUseCase(get(),get()) }
    factory { SaveLocalClaimUseCase(get()) }
    factory { SaveRemoteClaimUseCase(get()) }
    factory { SaveLocalAgreementUseCase(get()) }
    factory { SaveRemoteAgreementUseCase(get()) }
    factory { GetLocalOperationsUseCase(get()) }
    factory { GetSyncedOperationsUseCase(get())}
    factory { DeleteSyncedUseCase(get())}
    factory { SaveLocalOperationsUseCase(get())}

    /* Picasso */
    single {
        Picasso.get()
    }


}