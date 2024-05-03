package com.example.ozinshe.Data.FavoriteGET


import com.google.gson.annotations.SerializedName

data class FavoriteSpisokItem(
    @SerializedName("id")
    val id: Int, // 123
    @SerializedName("movieType")
    val movieType: String, // SERIAL
    @SerializedName("name")
    val name: String, // Расулдың хикаялары
    @SerializedName("keyWords")
    val keyWords: String, // тәрбие ұшқыр бала мектеп махаббат достық
    @SerializedName("description")
    val description: String, // Расулмен байланысты әртүрлі оқиғалар туралы сериал. Бұл өтекүлкілі әрі түп-төркінінде тәрбиелік мәні бар және кішкентай көрермендердіойландыратын ситуациялар болады. Басты ерекшелігі, оқиғалардың басым бөлігі қиялдантуған емес, яғни бас кейіпкердің өмірінен алынады. Өйткені, Расул 3 жасынан анықсөйлеп, ұшқыр жауаптарымен айналасын тәнті еткен, тапқыр бала болып өсіп келеді.Сондықтан, бұл жоба «деген екен» үлгісіндегі, балаларға ұнайтын, әзіл аралас тартымдыскетчком болады.
    @SerializedName("year")
    val year: Int, // 2021
    @SerializedName("trend")
    val trend: Boolean, // true
    @SerializedName("timing")
    val timing: Int, // 13
    @SerializedName("director")
    val director: String, // Жебегенова А.Т.
    @SerializedName("producer")
    val producer: String, // Жебегенова А.Т.
    @SerializedName("poster")
    val poster: Poster,
    @SerializedName("video")
    val video: Any, // null
    @SerializedName("watchCount")
    val watchCount: Int, // 4895
    @SerializedName("seasonCount")
    val seasonCount: Int, // 1
    @SerializedName("seriesCount")
    val seriesCount: Int, // 20
    @SerializedName("createdDate")
    val createdDate: String, // 2022-06-17T06:35:42.186+00:00
    @SerializedName("lastModifiedDate")
    val lastModifiedDate: String, // 2023-09-29T13:18:38.538+00:00
    @SerializedName("screenshots")
    val screenshots: List<Screenshot>,
    @SerializedName("categoryAges")
    val categoryAges: List<CategoryAge>,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("favorite")
    val favorite: Boolean // true
)