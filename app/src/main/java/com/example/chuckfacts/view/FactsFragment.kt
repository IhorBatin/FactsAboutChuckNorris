package com.example.chuckfacts.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chuckfacts.R
import com.example.chuckfacts.viewmodel.FactsViewModel
import kotlinx.android.synthetic.main.bottom_control_bar.*
import kotlinx.android.synthetic.main.fragment_fact.*
import timber.log.Timber


class FactsFragment: Fragment() {
    private var factsCount: Int = -1
    private val viewModel: FactsViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(FactsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView")
        return inflater.inflate(R.layout.fragment_fact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated")

        tv_fact.let {
            it.text = resources.getText(R.string.no_facts)

        }

        setupObservers()

        button_forward.setOnClickListener { handleOnForwardClick(it) }
        button_back.setOnClickListener { handleOnBackClick(it) }


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

    }

    //TODO: Put condition to check if its Random or from Category, then call appropriate function
    private fun handleOnForwardClick(button: View){
        Toast.makeText(activity, "Next", Toast.LENGTH_SHORT).show()
        factsCount++

        viewModel.getRandomFact()

    }

    private fun handleOnBackClick(button: View) {
        Toast.makeText(activity, "Previous", Toast.LENGTH_SHORT).show()
        if(factsCount != 0) {
            factsCount--
        }
        viewModel.getPreviousFact(factsCount)

    }

    private fun updateFactText(fact: String){
        tv_fact.text = fact
    }
}