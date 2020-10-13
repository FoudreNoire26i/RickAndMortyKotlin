package com.example.rickandmortykotlin.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortykotlin.API
import com.example.rickandmortykotlin.BASE_URL
import com.example.rickandmortykotlin.MyAdapter
import com.example.rickandmortykotlin.R
import com.example.rickandmortykotlin.dataclass.Character
import com.example.rickandmortykotlin.dataclass.Page
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterListActivity : AppCompatActivity() {

    //---------API---------
    lateinit var service : API

    var characterList = ArrayList<Character>()


    lateinit var mAdapter : MyAdapter
    lateinit var recyclerView : RecyclerView
    lateinit var viewManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        recyclerView = findViewById<RecyclerView>(R.id.character_list_recycler_view)

        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build()
        service = retrofit.create(API::class.java)


        val callCharacterList= service.getCharacterPageX(1)
        callCharacterList.enqueue(object : Callback<Page> {
            override fun onResponse(call: Call<Page>, response: Response<Page>) {
                response.body()?.characters?.let { characterList.addAll(it) }
                mAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Page>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        viewManager = LinearLayoutManager(this)
        mAdapter = MyAdapter(characterList)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = mAdapter

        }
    }

}