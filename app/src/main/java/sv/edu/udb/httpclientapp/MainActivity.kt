package sv.edu.udb.httpclientapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.Request
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostAdapter
    private val client = OkHttpClient()
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = PostAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        scope.launch {
            val result = withContext(Dispatchers.IO) {
                makeRequest()
            }
            adapter.setData(result)
        }
    }

    private fun makeRequest(): List<Post> {
        val request = Request.Builder()
            .url("https://jsonplaceholder.typicode.com/posts")
            .build()

        val response = client.newCall(request).execute()

        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val posts = parsePosts(responseBody)
            return posts
        } else {
            Log.e("MainActivity", "Error: ${response.code}")
            return emptyList()
        }
    }

    private fun parsePosts(json: String?): List<Post> {
        val jsonArray = JSONArray(json)
        val posts = mutableListOf<Post>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val post = Post(
                jsonObject.getInt("id"),
                jsonObject.getInt("userId"),
                jsonObject.getString("title"),
                jsonObject.getString("body")
            )
            posts.add(post)
        }
        return posts
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}
