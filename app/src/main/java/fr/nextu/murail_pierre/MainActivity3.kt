package fr.nextu.murail_pierre

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.nextu.murail_pierre.entities.MovieAdapter
import fr.nextu.murail_pierre.entities.Movies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class MainActivity3 : AppCompatActivity() {

    lateinit var json: TextView
    lateinit var movie_recycler: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        json = findViewById(R.id.json)

        movie_recycler = findViewById<RecyclerView>(R.id.movie_recycler).apply{
            adapter = MovieAdapter(Movies(emptyList()))
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@MainActivity3)
        }
    }

    fun requestPictureList(callback: (String) -> Unit) {
        val client = OkHttpClient()

        val request: Request = Request.Builder()
            .url("https://api.betaseries.com/movies/list")
            .get()
            .addHeader("X-BetaSeries-Key", "470d2afc452f")
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body?.string() ?: "Failed to retrieve body"
                    callback(responseBody)
                    Log.d("tag", responseBody)
                } else {
                    Log.d("tag", "Failed with status code: ${response.code}")
                }
            } catch (e: Exception) {
                Log.d("tag", "Error: ${e.localizedMessage}")
            }
        }
    }

    fun getPictureList() {
        CoroutineScope(Dispatchers.IO).launch {
            requestPictureList {
                val gson = Gson()
                val converter = gson.fromJson(it, Movies::class.java)
                CoroutineScope(Dispatchers.Main).launch {
                    movie_recycler.adapter = MovieAdapter(converter)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        getPictureList()
    }
}