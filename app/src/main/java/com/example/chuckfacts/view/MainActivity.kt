package com.example.chuckfacts.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hiding upper bar if present
        //this.supportActionBar?.hide()

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
