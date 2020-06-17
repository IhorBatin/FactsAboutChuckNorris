package com.example.chuckfacts.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.chuckfacts.R
import com.example.chuckfacts.R.id.*
import com.example.chuckfacts.viewmodel.FactsViewModel
import kotlinx.android.synthetic.main.bottom_control_bar.*
import kotlinx.android.synthetic.main.fragment_fact.*
import kotlinx.coroutines.newFixedThreadPoolContext
import timber.log.Timber

// TODO: Dropdown menu for selecting categories just below toolbar ?
// TODO: Fix - saving correct fact to the DB

class FactsFragment : Fragment() {
    private var factsCount: Int = -1
    private var currentFact: Int = -1
    private var visibleFact: String = ""
    private var currentCategory: String = "random"
    private var listOfCategories: List<String> = listOf()

    private val viewModel by lazy { (requireActivity() as MainActivity).viewModel }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView")
        requireActivity().actionBar?.setDisplayShowTitleEnabled(true)
        return inflater.inflate(R.layout.fragment_fact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated")

        setHasOptionsMenu(true)
        setupObservers()
        viewModel.getAllCategories()

        // Loading first fact on launch
        handleOnForwardClick()

        tv_fact.let {
            it.text = resources.getText(R.string.no_facts)
        }

        button_forward.setOnClickListener { handleOnForwardClick() }
        button_back.setOnClickListener { handleOnBackClick() }
        button_share.setOnClickListener { handleOnShareClick() }
        button_save.setOnClickListener { handleOnSaveClick() }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.removeItem(mi_random_facts)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        // Populating category sub-menu with categories received from API
        for (i in listOfCategories){
            menu[0].subMenu.add(i.toUpperCase())
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            mi_saved_facts -> {
                navigateToSavedFacts()
                true
            }
            mi_about -> {
                navigateToAbout()
                Toast.makeText(activity, "About", Toast.LENGTH_SHORT).show()
                true
            }
            mi_category -> {
                //
                true
            }
            else -> {
                currentCategory = item.toString().toLowerCase()
                handleOnForwardClick()
                Toast.makeText(activity,
                    "Selected Category: ${currentCategory.toUpperCase()}",
                    Toast.LENGTH_SHORT).show()
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setupObservers(){
        // When checking updates on live data make sure app is in the foreground,
        // otherwise it will no update values
        viewModel.getFactsLiveData().observe(viewLifecycleOwner, Observer {fact ->
            Timber.i("LiveData RandomFact updating...")
            Timber.i("Fact Category: ${fact.categories}")
            Timber.i("Fact ID: ${fact.id}")
            Timber.i("Fact: ${fact.value}")
            Timber.i("=======================")
            updateFactText(fact.value)
        })

        viewModel.getAllCategoriesLiveData().observe(viewLifecycleOwner, Observer {categories ->
            Timber.i("LiveData Categories updating...")
            Timber.i("Num of categories: ${categories.size}")
            listOfCategories = categories
            activity?.invalidateOptionsMenu()
        })

        viewModel.getAllSavedFactsLiveData().observe(viewLifecycleOwner, Observer {
            Timber.i("# of facts returned from DB:  ${(it.size)}")
        })
    }

    private fun handleOnForwardClick(){
        Timber.i("Making Request on category: ${currentCategory}")
        if(factsCount > currentFact){
            currentFact++
            viewModel.getPreviousFact(currentFact)
        }
        else{
            factsCount++
            currentFact++
            if (currentCategory == "random"){
                viewModel.getRandomFact()
            }
            else if( currentCategory != "random"){
                viewModel.getRandomFact(currentCategory)
            }
        }
    }

    private fun handleOnBackClick() {
        if(currentFact != 0) {
            currentFact--
            viewModel.getPreviousFact(currentFact)
        }
    }

    private fun handleOnShareClick(){
        val sendIntent = Intent()
        val shareIntent: Intent = Intent.createChooser(sendIntent, null)

        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT,
            "$visibleFact \n Provided by Random Chuck Norris Facts App")
        sendIntent.type = "text/plain"
        startActivity(shareIntent)
    }

    // TODO: Implement saving to DB functionality
    private fun handleOnSaveClick(){
        Toast.makeText(activity, "Saving...", Toast.LENGTH_SHORT).show()
        Timber.i("Saving -> ${currentFact}")
        if(currentFact != -1){
            viewModel.saveFact(currentFact)
        }
        else{
            Toast.makeText(activity, "No Facts to save to DB", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateFactText(fact: String){
        visibleFact = fact
        tv_fact.text = visibleFact
    }

    private fun navigateToSavedFacts(){
        view?.findNavController()?.navigate(R.id.action_factsFragment_to_savedFactsFragment)
    }

    private fun navigateToAbout(){
        view?.findNavController()?.navigate(R.id.action_factsFragment_to_aboutFragment)
    }
}