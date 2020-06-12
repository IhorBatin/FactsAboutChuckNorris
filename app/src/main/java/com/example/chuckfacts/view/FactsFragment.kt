package com.example.chuckfacts.view


import android.app.Application
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chuckfacts.R
import com.example.chuckfacts.viewmodel.FactsViewModel
import kotlinx.android.synthetic.main.bottom_control_bar.*
import kotlinx.android.synthetic.main.fragment_fact.*
import timber.log.Timber


class FactsFragment : Fragment() {
    private var factsCount: Int = -1
    private var currentFact: Int = -1
    private var visibleFact: String = ""

    private val viewModel by lazy { (requireActivity() as MainActivity).viewModel }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView")
        return inflater.inflate(R.layout.fragment_fact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated")

        // Simulating forward click to load first item
        handleOnForwardClick()

        tv_fact.let {
            it.text = resources.getText(R.string.no_facts)
        }

        setupObservers()
        viewModel.getAllSavedFacts()

        button_forward.setOnClickListener { handleOnForwardClick() }
        button_back.setOnClickListener { handleOnBackClick() }
        button_share.setOnClickListener { handleOnShareClick() }
        button_save.setOnClickListener { handleOnSaveClick() }
    }

    private fun setupObservers(){
        // When checking updates on live data make sure app is in the foreground,
        // otherwise it will no update values
        viewModel.getFactsLiveData().observe(viewLifecycleOwner, Observer {fact ->
            Timber.i("LiveData RandomFact updating...")
            Timber.i("Fact: ${fact.value}")

            updateFactText(fact.value)
        })

        viewModel.getAllCategoriesLiveData().observe(viewLifecycleOwner, Observer {categories ->
            Timber.i("LiveData Categories updating...")
            Timber.i("Num of categories: ${categories.size}")
        })

        viewModel.getAllSavedFactsLiveData().observe(viewLifecycleOwner, Observer {

        })


    }

    //TODO: Put condition to check if its Random or from Category, then call appropriate function
    private fun handleOnForwardClick(){
        Toast.makeText(activity, "Next", Toast.LENGTH_SHORT).show()
        if(factsCount > currentFact){
            currentFact++
            viewModel.getPreviousFact(currentFact)
        }
        else{
            factsCount++
            currentFact++
            viewModel.getRandomFact()
        }
    }

    private fun handleOnBackClick() {
        Toast.makeText(activity, "Previous", Toast.LENGTH_SHORT).show()
        if(currentFact != 0) {
            currentFact--
            viewModel.getPreviousFact(currentFact)
        }
    }

    private fun handleOnShareClick(){
        Toast.makeText(activity, "Sharing", Toast.LENGTH_SHORT).show()
        val sendIntent: Intent = Intent()
        val shareIntent: Intent = Intent.createChooser(sendIntent, null)

        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT,
            "$visibleFact \n Provided by Random Chuck Norris Facts App")
        sendIntent.type = "text/plain"
        startActivity(shareIntent)
    }

    // TODO: Implement saving to DB functionality
    private fun handleOnSaveClick(){
        Toast.makeText(activity, "Saving", Toast.LENGTH_SHORT).show()
    }

    private fun updateFactText(fact: String){
        visibleFact = fact
        tv_fact.text = visibleFact
    }
}