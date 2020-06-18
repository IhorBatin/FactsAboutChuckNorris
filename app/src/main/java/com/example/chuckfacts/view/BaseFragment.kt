package com.example.chuckfacts.view

import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.chuckfacts.util.ChuckFactResponse

open class BaseFragment : Fragment() {

    /**
     * Common method to share facts
     */
    open fun shareFact(fact: ChuckFactResponse){
        val sendIntent = Intent()
        val shareIntent: Intent = Intent.createChooser(sendIntent, null)

        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT,
            "${fact.value} \n\n -Provided by Chuck Facts App")
        sendIntent.type = "text/plain"
        startActivity(shareIntent)
    }

}