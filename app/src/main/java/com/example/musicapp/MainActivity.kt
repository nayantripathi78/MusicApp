package com.example.musicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var myRecyclerView: RecyclerView
    private lateinit var myProgressBar: ProgressBar
    private lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myProgressBar = findViewById(R.id.progressBar)
        myProgressBar.visibility = View.VISIBLE

        myRecyclerView = findViewById(R.id.recyclerView)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofitBuilder.getData("eminem")

            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        val dataList = response.body()?.data!!
                        myAdapter = MyAdapter(this@MainActivity, dataList)
                        myRecyclerView.adapter = myAdapter
                        myRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: HttpException) {
                    Toast.makeText(
                        this@MainActivity,
                        "Error: ${e.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Oops: Something else went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                myProgressBar.visibility = View.INVISIBLE
            }
        }
    }
}