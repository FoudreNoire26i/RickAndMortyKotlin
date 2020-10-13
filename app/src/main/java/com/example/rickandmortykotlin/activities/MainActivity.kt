package com.example.rickandmortykotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.FrameMetrics
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import com.example.rickandmortykotlin.R
import com.example.rickandmortykotlin.fragments.CharacterDetailsFragment
import com.example.rickandmortykotlin.fragments.OtherFragment

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, CharacterDetailsFragment.newInstance())
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
            .commit()

        findViewById<Button>(R.id.button_fragment).setOnClickListener{
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, OtherFragment.newInstance())
                .commit()
        }
    }

    fun openListActivity(v : View){
        val intent = Intent(this, CharacterListActivity::class.java)
        startActivity(intent)
    }

}