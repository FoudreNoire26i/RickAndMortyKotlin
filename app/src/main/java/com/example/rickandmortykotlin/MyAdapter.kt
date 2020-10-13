package com.example.rickandmortykotlin

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortykotlin.dataclass.Character
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class MyAdapter(val listCharacter : ArrayList<Character>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    lateinit var mPrefs: SharedPreferences
    lateinit var prefsEditor: SharedPreferences.Editor

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false) as CardView

        val mViewHolder = MyViewHolder(cardView)

        mPrefs = parent.context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
        prefsEditor = mPrefs.edit()

        val savedCharacter = mPrefs.all




        mViewHolder.itemView.setOnClickListener {
            println("Hello click")
            //TODO : new fragment
        }

        mViewHolder.itemView.setOnLongClickListener {
            val me = listCharacter[mViewHolder.adapterPosition]
            println("Hello long click ${mViewHolder.adapterPosition}")

            val savedCharacter = mPrefs.all

            if (savedCharacter.containsKey("${me.id}")){
                prefsEditor.remove("${me.id}")
                inverseViewsVisibility(it.findViewById(R.id.is_favoris), it.findViewById(R.id.is_not_favoris))
            }
            else {
                val gson = Gson()
                val json: String = gson.toJson(me)
                prefsEditor.putString("${me.id}", json)

                inverseViewsVisibility(it.findViewById(R.id.is_not_favoris), it.findViewById(R.id.is_favoris))
            }
            prefsEditor.commit()
            //TODO : new favoris
            true
        }

        return mViewHolder
    }


    fun inverseViewsVisibility(viewGoDisappear: View, viewGoAppear: View) {
        viewGoAppear.visibility = View.VISIBLE
        viewGoDisappear.visibility = View.INVISIBLE
    }




    override fun getItemCount(): Int {
        return listCharacter.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val me = listCharacter[position]
        holder.itemView.findViewById<TextView>(R.id.name_text_view).text = me.name
        Picasso.get().load(me.image).resize(ITEM_LIST_SIZE, ITEM_LIST_SIZE).into(holder.itemView.findViewById<ImageView>(R.id.character_image_view))

        if (mPrefs.all.containsKey("${listCharacter[position].id}")) { //WARN : peut engendrer une erreur -> cf id
            inverseViewsVisibility(holder.itemView.findViewById(R.id.is_not_favoris), holder.itemView.findViewById(R.id.is_favoris))
        }
    }


}