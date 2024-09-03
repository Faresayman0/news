package com.example.whatnew

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatnew.databinding.ActivityFavoritesBinding
import com.google.firebase.firestore.FirebaseFirestore

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favoritesRv.layoutManager = LinearLayoutManager(this)

        loadFavorites()
    }

    private fun loadFavorites() {
        firestore.collection("favorites")
            .get()
            .addOnSuccessListener { documents ->
                val articles = ArrayList<Article>()
                for (document in documents) {
                    val title = document.getString("title") ?: ""
                    val url = document.getString("url") ?: ""
                    val urlToImage = document.getString("urlToImage") ?: ""
                    articles.add(Article(title, url, urlToImage))
                }
                displayFavorites(articles)
            }
            .addOnFailureListener { e -> Log.w("Firestore", "Error getting documents: ", e) }
    }

    private fun displayFavorites(articles: ArrayList<Article>) {
        val adapter = NewsAdapter(this, articles) { position, _ ->
            val article = articles[position]
            val i = Intent(Intent.ACTION_VIEW, article.url.toUri())
            startActivity(i)
        }
        binding.favoritesRv.adapter = adapter
    }
}

