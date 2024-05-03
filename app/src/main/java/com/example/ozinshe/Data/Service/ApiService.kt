package com.example.ozinshe.Data.Service

import com.example.ozinshe.Data.CategoryAge.CategoryAge
import com.example.ozinshe.Data.CategoryMainMovies.CategoryMainMovies
import com.example.ozinshe.Data.CategoryMainMovies.Movy
import com.example.ozinshe.Data.Favorite.movieIdFavorite
import com.example.ozinshe.Data.FavoriteGET.FavoriteSpisok
import com.example.ozinshe.Data.IDMovies.IDMovies
import com.example.ozinshe.Data.Janr.janr
import com.example.ozinshe.Data.ProfileUser.ProfileUser
import com.example.ozinshe.Data.LoginRequest.loginRequest
import com.example.ozinshe.Data.PageCategory.CategorySpisok
import com.example.ozinshe.Data.ProfileUser.Language
import com.example.ozinshe.Data.ProfileUser.UpdateProfile
import com.example.ozinshe.Data.Seasons.SeasonsList
import com.example.ozinshe.Data.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface apiService {

    @POST("********************")
    suspend fun loginRequest(@Body loginRequest: loginRequest): Response<User>

    @POST("********************")
    suspend fun signUpRequest(@Body loginRequest: loginRequest): Response<User>

    @GET("********************")
    suspend fun profile(@Header("Authorization") token: String):Response<ProfileUser>

    @GET("********************")
    suspend fun janr(  @Header("Authorization") token: String): janr

    @GET("********************")
    suspend fun categoryAge(  @Header("Authorization") token: String): CategoryAge

    @GET("********************")
    suspend fun categoryMainMovies(  @Header("Authorization") token: String): CategoryMainMovies

    @GET("********************")
    suspend fun getMovieInfo(@Header("Authorization") token: String,@Path("id") id: Int): Response<IDMovies>

    @GET("********************")
    suspend fun similarMovies(@Header("Authorization") token: String,@Path("id") id: Int): List<Movy>

    @GET("********************")
    suspend fun moviesSezon(@Header("Authorization") token: String,@Path("movieId") id: Int): SeasonsList

    @POST("********************")
    suspend fun moviesFavoritePlus(@Header("Authorization") token: String, @Body movieIdFavorite: movieIdFavorite): ResponseBody

    @HTTP(method = "DELETE", path = "********************", hasBody = true)
    suspend fun moviesFavoriteDelete(@Header("Authorization") token: String, @Body movieIdFavorite: movieIdFavorite)

    @GET("********************")
    suspend fun moviesFavoriteSpisok(@Header("Authorization") token: String): FavoriteSpisok

    @PUT("********************")
    suspend fun profileLanguage(@Header("Authorization") token: String, @Body language: Language): ProfileUser

    @GET("********************")
    suspend fun moviesSearch(  @Header("Authorization") token: String, @Query("search") search: String): FavoriteSpisok

    @PUT("********************")
    suspend fun profileUpdate(@Header("Authorization") token: String, @Body dto: UpdateProfile): Response<Any>

    @GET("********************")
    suspend fun pageCategory(@Header("Authorization") token: String, @Query("categoryId") categoryId: Int): CategorySpisok
}