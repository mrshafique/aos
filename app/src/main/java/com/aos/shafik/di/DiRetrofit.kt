package com.aos.shafik.di

import com.aos.shafik.retrofit.AlbumService
import com.aos.shafik.retrofit.MyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @Author: Shafik Shaikh
 * @Date: 09-10-2022
 */
@Module
@InstallIn(SingletonComponent::class)
class DiRetrofit {

    /**
     * @Usage: create MyInterceptor obj; Hilt will inject MyInterceptor obj in providesOkHttpClient
     * function
     * @Author: Shafik Shaikh
     */
    @Provides
    @Singleton
    fun providesMyInterceptor(): MyInterceptor {
        return MyInterceptor()
    }

    /**
     * @Usage: create ScalarsConverterFactory obj; Hilt will inject ScalarsConverterFactory obj in
     * providesRetrofitInstance function
     * @Author: Shafik Shaikh
     */
    @Provides
    @Singleton
    fun providesScalarsConverterFactory(): ScalarsConverterFactory {
        return ScalarsConverterFactory.create()
    }

    /**
     * @Usage: create GsonConverterFactory obj; Hilt will inject GsonConverterFactory obj in
     * providesRetrofitInstance function
     * @Author: Shafik Shaikh
     */
    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    /**
     * @Usage: create OkHttpClient obj; Hilt will inject OkHttpClient obj in
     * providesRetrofitInstance function
     * @Author: Shafik Shaikh
     */
    @Provides
    @Singleton
    fun providesOkHttpClient(myInterceptor: MyInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(myInterceptor)
            .readTimeout(45, TimeUnit.SECONDS)
            .connectTimeout(45, TimeUnit.SECONDS)
            .build()
    }

    /**
     * @Usage: create Retrofit obj; Hilt will inject Retrofit obj in providesStudentService function
     * @Author: Shafik Shaikh
     */
    @Provides
    @Singleton
    fun providesRetrofitInstance(
        okHttpClient: OkHttpClient,
        scalarsConverterFactory: ScalarsConverterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://ws.audioscrobbler.com/2.0/")
            .client(okHttpClient)
            .addConverterFactory(scalarsConverterFactory)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    /**
     * @Usage: create AlbumService obj; Hilt will inject AlbumService obj in DaoDamRepo class
     * @Author: Shafik Shaikh
     */
    @Provides
    @Singleton
    fun providesStudentService(retrofit: Retrofit): AlbumService =
        retrofit.create(AlbumService::class.java)
}