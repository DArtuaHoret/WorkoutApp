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

// ─────────────────────────────────────────────────────────────────────────────
// Models – text search
// ─────────────────────────────────────────────────────────────────────────────

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

// Convenience alias so callers don't need to use the nested path
typealias OpenFoodNutrimentsDto = OpenFoodProductDto.OpenFoodNutrimentsDto

// ─────────────────────────────────────────────────────────────────────────────
// Retrofit client
// ─────────────────────────────────────────────────────────────────────────────

object OpenFoodRetrofitClient {

    val apiServiceInstance: OpenFoodApiService by lazy {
        getSearchClient().create(OpenFoodApiService::class.java)
    }

    val productServiceInstance: OpenFoodProductService by lazy {
        getProductClient().create(OpenFoodProductService::class.java)
    }

    // The search endpoint lives at a different base URL than the product lookup
    private const val SEARCH_BASE_URL   = "https://search.openfoodfacts.org/"
    private const val PRODUCT_BASE_URL  = "https://world.openfoodfacts.org/"

    private fun buildMoshi(): Moshi = Moshi.Builder().build()

    private fun getSearchClient(): Retrofit = Retrofit.Builder()
        .baseUrl(SEARCH_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(buildMoshi()))
        .build()

    private fun getProductClient(): Retrofit = Retrofit.Builder()
        .baseUrl(PRODUCT_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(buildMoshi()))
        .build()
}

// ─────────────────────────────────────────────────────────────────────────────
// API interfaces
// ─────────────────────────────────────────────────────────────────────────────

interface OpenFoodApiService {
    @GET("search")
    fun searchProducts(
        @Query("q")         query: String,
        @Query("fields")    fields: String   = "code,product_name,nutriments",
        @Query("page_size") pageSize: Int    = 20,
    ): Call<OpenFoodSearchResponse>
}

interface OpenFoodProductService {

    @GET("api/v2/product/{barcode}")
    fun getProductByBarcode(
        @Path("barcode")  barcode: String,
        @Query("fields")  fields: String = "code,product_name,nutriments",
    ): Call<OpenFoodBarcodeResponse>
}