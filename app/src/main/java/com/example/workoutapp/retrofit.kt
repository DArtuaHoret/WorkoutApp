package com.example.workoutapp

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import okhttp3.OkHttpClient


@JsonClass(generateAdapter = true)
data class OpenFoodSearchResponse(
    @Json(name = "hits")
    val hits: List<OpenFoodProductDto> = emptyList(),
)

@JsonClass(generateAdapter = true)
data class OpenFoodBarcodeResponse(
    @Json(name = "status")
    val status: Int = 0,
    @Json(name = "product")
    val product: OpenFoodProductDto? = null,
)

@JsonClass(generateAdapter = true)
data class OpenFoodProductDto(
    @Json(name = "code")
    val code: String?,
    @Json(name = "product_name")
    val productName: String?,
    @Json(name = "nutriments")
    val nutriments: OpenFoodNutrimentsDto?,
) {
    @JsonClass(generateAdapter = true)
    data class OpenFoodNutrimentsDto(
        @Json(name = "energy-kcal_100g")
        val kcal100g: Double?,
        @Json(name = "proteins_100g")
        val protein100g: Double?,
        @Json(name = "fat_100g")
        val fat100g: Double?,
        @Json(name = "carbohydrates_100g")
        val carbs100g: Double?,
    )
}

typealias OpenFoodNutrimentsDto = OpenFoodProductDto.OpenFoodNutrimentsDto


// retrofit client


object OpenFoodRetrofitClient {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", "WorkoutApp/1.0 (Android)")
                .build()
            chain.proceed(request)
        }
        .build()

    val apiServiceInstance: OpenFoodApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://search.openfoodfacts.org/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()
            .create(OpenFoodApiService::class.java)
    }

    val productServiceInstance: OpenFoodProductService by lazy {
        Retrofit.Builder()
            .baseUrl("https://world.openfoodfacts.org/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()
            .create(OpenFoodProductService::class.java)
    }
}




interface OpenFoodApiService {
    @GET("search")
    fun searchProducts(
        @Query("q")         query: String,
        @Query("fields")    fields: String  = "code,product_name,nutriments",
        @Query("page_size") pageSize: Int   = 20,
    ): Call<OpenFoodSearchResponse>
}

interface OpenFoodProductService {
    @GET("api/v2/product/{barcode}")
    fun getProductByBarcode(
        @Path("barcode") barcode: String,
        @Query("fields") fields: String = "code,product_name,nutriments",
    ): Call<OpenFoodBarcodeResponse>
}