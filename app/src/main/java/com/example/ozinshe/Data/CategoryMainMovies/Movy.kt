package com.example.ozinshe.Data.CategoryMainMovies


import com.google.gson.annotations.SerializedName

data class Movy(
    @SerializedName("id")
    val id: Int, // 124
    @SerializedName("movieType")
    val movieType: String, // SERIAL
    @SerializedName("name")
    val name: String, // Bala love 
    @SerializedName("keyWords")
    val keyWords: String, // бала мектеп махаббат достық
    @SerializedName("description")
    val description: String, // Жаңа оқу жылында қаладағы жекеменшік мектептердің бірі жаңа заң қабылдайды. Инвесторлардың көмегімен бұл мектепте әр сыныпқа 10 гранттан бөлінген. Сондықтан, қаржылық мүмкіндігі шектеулі отбасының балалары да емтиханнан өте алса, осы мектепте оқи алады. Соның нәтижесінде жаңа оқу жылында мектепте түрлі балалар оқуға қабылданады. Солардың бірі - Махамбет есімді әп-әдемі бала. Ол барлық қыздарға ұнайды. Алайда оның өзі Кәсар есімді ең әдемі қызды ұнатып қалады. Жаңадан келген оқушылар бұл мектепте оқып жүрген балалардың бәрімен бірдей тіл табыса алмайды.бір сөзбен айтқанда, бұл сериалда балалар арасындағы кикілжің, жаңа достар, бала махаббат тақырыптары тереңінен қозғалады.
    @SerializedName("year")
    val year: Int, // 2022
    @SerializedName("trend")
    val trend: Boolean, // true
    @SerializedName("timing")
    val timing: Int, // 15
    @SerializedName("director")
    val director: String, // Зарина Муслим
    @SerializedName("producer")
    val producer: String, // Зарина Муслим
    @SerializedName("poster")
    val poster: Poster,
    @SerializedName("video")
    val video: Video,
    @SerializedName("watchCount")
    val watchCount: Int, // 495
    @SerializedName("seasonCount")
    val seasonCount: Int, // 1
    @SerializedName("seriesCount")
    val seriesCount: Int, // 15
    @SerializedName("createdDate")
    val createdDate: String, // 2022-12-06T13:22:12.771+00:00
    @SerializedName("lastModifiedDate")
    val lastModifiedDate: String, // 2022-12-06T13:22:12.771+00:00
    @SerializedName("screenshots")
    val screenshots: List<Screenshot>,
    @SerializedName("categoryAges")
    val categoryAges: List<CategoryAge>,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("favorite")
    val favorite: Boolean // false
)