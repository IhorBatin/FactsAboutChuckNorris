package com.example.chuckfacts.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chuckfacts.R
import com.example.chuckfacts.viewmodel.FactsViewModel
import timber.log.Timber

/** TODO GENERAL THINGS TO B DONE:
 *  TODO: Convert all gradle to Kotlin build scripts
 *  TODO: Implement Room Database for saving favorite facts
 *  TODO: Design apps general layout & navigation
 *
  */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())
        Timber.i("onCreate called")
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
