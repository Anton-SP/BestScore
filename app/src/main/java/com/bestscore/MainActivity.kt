package com.bestscore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bestscore.featuredice.DiceDialogFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DiceDialogFragment().show(supportFragmentManager, null)
    }
}