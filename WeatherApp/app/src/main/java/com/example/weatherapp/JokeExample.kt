//package com.example.apiapp
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.http.GET
//
//// 1. Data Model
//data class JokeResponse(
//    val id: Int,
//    val setup: String,
//    val punchline: String
//)
//
//// 2. Retrofit API Interface
//interface JokeApiService {
//    @GET("jokes/random")
//    suspend fun getRandomJoke(): JokeResponse
//}
//
//// 3. Retrofit Instance
//object RetrofitInstance {
//    private const val BASE_URL = "https://official-joke-api.appspot.com/"
//
//    val api: JokeApiService by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(JokeApiService::class.java)
//    }
//}
//
//// 4. ViewModel (Stores List of Jokes)
//class JokeViewModel : ViewModel() {
//    private val _jokes = mutableStateListOf<String>()
//    val jokes: List<String> get() = _jokes
//
//    fun fetchJoke() {
//        viewModelScope.launch {
//            try {
//                val response = RetrofitInstance.api.getRandomJoke()
//                val jokeText = "${response.setup}\n\n${response.punchline}"
//                _jokes.add(jokeText) // Add new joke to the list
//            } catch (e: Exception) {
//                e.printStackTrace()
//                _jokes.add("Oops! Something went wrong.")
//            }
//        }
//    }
//}
//
//// 5. Main Activity
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent { JokeScreen() }
//    }
//}
//
//// 6. Jetpack Compose UI
//@Composable
//fun JokeScreen(viewModel: JokeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
//    val jokes by remember { mutableStateOf(viewModel.jokes) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Button(
//            onClick = { viewModel.fetchJoke() },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Get Joke")
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        LazyColumn(
//            modifier = Modifier.fillMaxSize(),
//            contentPadding = PaddingValues(16.dp)
//        ) {
//            items(jokes) { joke ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//                ) {
//                    Text(
//                        text = joke,
//                        modifier = Modifier.padding(16.dp),
//                        style = MaterialTheme.typography.bodyLarge
//                    )
//                }
//            }
//        }
//    }
//}
