package com.example.chuckfacts.ext

import android.view.View

/**
 * Extension function to change visibility of view
 * by passing boolean parameter
 */
fun View.showView(show: Boolean){
    visibility = when(show){
        true -> View.VISIBLE
        false -> View.GONE
    }
}