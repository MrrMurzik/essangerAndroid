package com.example.messengerAndroid.source
//
//import com.example.messengerAndroid.app.Constants.BASE_URL
//import com.example.messengerAndroid.app.data.SourcesProvider
//import com.squareup.moshi.Moshi
//import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory
//
//object SourceProviderHolder {
//
//    val sourcesProvider: SourcesProvider by lazy {
//        val moshi = Moshi.Builder().build()
//        val config = RetrofitConfig(createRetrofit(moshi), moshi)
//        RetrofitSourcesProvider(config)
//    }
//
//    private fun createRetrofit(moshi: Moshi): Retrofit {
//        return Retrofit
//            .Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .build()
//    }
//
//}