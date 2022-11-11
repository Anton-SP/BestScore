package com.bestscore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bestscore.featurecreatetemplate.CreateTemplateFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CreateTemplateFragment())
            .commit()
    }
}