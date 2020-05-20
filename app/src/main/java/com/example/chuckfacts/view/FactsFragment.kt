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
import kotlinx.android.synthetic.main.fragment_fact.*
import timber.log.Timber


class FactsFragment: Fragment() {
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

        setupObservers()
        viewModel.getRandomFact()
        viewModel.getAllCategories()
        viewModel.getRandomByCategory("dev")

        iv_test.setOnClickListener {
            Toast.makeText(activity, "CLICKED!!!", Toast.LENGTH_LONG).show()
            viewModel.getRandomFact()
            viewModel.getAllCategories()
            viewModel.getRandomByCategory("dev")
        }
    }

    private fun setupObservers(){
        // When checking updates on live data make sure app is in the foreground,
        // otherwise it will no update values
        viewModel.getFactsLiveData().observe(viewLifecycleOwner, Observer {fact ->
            Timber.i("LiveData RandomFact updating...")
            Timber.i("Fact: ${fact.value}")
        })

        viewModel.getAllCategoriesLiveData().observe(viewLifecycleOwner, Observer {categories ->
            Timber.i("LiveData Categories updating...")
            Timber.i("Num of categories: ${categories.size}")
        })

        viewModel.getFromCategoryLiveData().observe(viewLifecycleOwner, Observer {factFromCategory ->
            Timber.i("LiveData Fact from Category updating...")
            Timber.i("Fact from category: ${factFromCategory.value}")
        })
    }
}