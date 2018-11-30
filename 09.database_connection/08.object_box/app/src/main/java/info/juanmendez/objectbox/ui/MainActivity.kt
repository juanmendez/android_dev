package info.juanmendez.objectbox.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import info.juanmendez.objectbox.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_add_band.setOnClickListener {

        }

        main_add_song.setOnClickListener {
        }
    }
}