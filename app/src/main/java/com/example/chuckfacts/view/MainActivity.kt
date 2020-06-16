package com.example.chuckfacts.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.example.chuckfacts.R
import com.example.chuckfacts.repository.local.FactsDb
import com.example.chuckfacts.viewmodel.FactsViewModel
import timber.log.Timber

/** TODO GENERAL THINGS TO B DONE:
 *  TODO: Convert all gradle to Kotlin build scripts
 *  TODO: Implement Room Database for saving favorite facts
 *  TODO: Design apps general layout & navigation
 *  TODO: Have Dark/ Light Themes?
 *  TODO: Write Unit & Instrumentation Tests
 *
 *  TODO: Add info menu item to tell about app and API it uses
*/

class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<FactsViewModel>()
    val permissions: List<String> = arrayListOf("android.permission.INTERNET")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.tb_top_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        Timber.plant(Timber.DebugTree())
        Timber.i("onCreate called")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart called")
    }

    override fun onResume(){
        super.onResume()
        Timber.i("onResume called")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause called")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy called, Bye Bye!")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.i("onRestart called")
    }
}
