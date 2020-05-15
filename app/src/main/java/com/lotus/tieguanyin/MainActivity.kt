package com.lotus.tieguanyin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity:AppCompatActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToRepositoryActivity.setOnClickListener {

        }

        goToUserActivity.setOnClickListener {

        }

    }
}