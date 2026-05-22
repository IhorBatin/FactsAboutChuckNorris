package com.example.chuckfacts.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.chuckfacts.R
import com.example.chuckfacts.R.id.*
import com.example.chuckfacts.util.ChuckFactResponse
import com.example.chuckfacts.viewmodel.FactsViewModel
import com.example.chuckfacts.databinding.FragmentFactBinding
import timber.log.Timber
import java.util.*


class FactsFragment : Fragment() {
    private var _binding: FragmentFactBinding? = null
    private val binding get() = _binding!!

    private lateinit var visibleFact: ChuckFactResponse
    private var currentCategory: String = "random"
    private var listOfCategories: List<String> = listOf()
    private var factField: TextView? = null

    private val viewModel: FactsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Timber.i("onCreateView")
        requireActivity().actionBar?.setDisplayShowTitleEnabled(true)

        _binding = FragmentFactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated")

        setHasOptionsMenu(true)
        setupObservers()
        viewModel.getAllCategories()
        factField = view.findViewById(tv_fact)

        factField?.let {
            it.text = ". . ."
        }

        binding.controlBar.buttonForward.setOnClickListener { handleOnForwardClick() }
        binding.controlBar.buttonShare.setOnClickListener { handleOnShareClick() }
        binding.controlBar.buttonSave.setOnClickListener { handleOnSaveClick() }
        binding.controlBar.buttonShare.isEnabled = false
        binding.controlBar.buttonSave.isEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.removeItem(mi_random_facts)

        // Populating category sub-menu with categories received from API
        for (i in listOfCategories){
            menu[0].subMenu?.add(i.uppercase(Locale.ROOT))
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
                currentCategory = item.toString().lowercase(Locale.ROOT)
                handleOnForwardClick()
                Toast.makeText(activity,
                    "Selected Category: ${currentCategory.uppercase(Locale.ROOT)}",
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
            updateFactText(fact)
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
        if (currentCategory == "random"){
            viewModel.getRandomFact()
        }
        else if(currentCategory != "random"){
            viewModel.getRandomFact(currentCategory)
        }
    }


    private fun handleOnShareClick(){
        if (this::visibleFact.isInitialized) {
            Timber.i("Sharing -> ${visibleFact.value}")
            shareFact(visibleFact)
        }
    }

    private fun handleOnSaveClick(){
        if (this::visibleFact.isInitialized) {
            Toast.makeText(activity, "Saving...", Toast.LENGTH_SHORT).show()
            Timber.i("Saving -> ${visibleFact.value}")
            viewModel.saveFact(visibleFact)
        }
    }

    private fun updateFactText(fact: ChuckFactResponse){
        visibleFact = fact
        factField?.let {
            it.text = visibleFact.value
        }
        binding.controlBar.buttonShare.isEnabled = true
        binding.controlBar.buttonSave.isEnabled = true
    }

    private fun shareFact(fact: ChuckFactResponse){
        val sendIntent = Intent()
        val shareIntent: Intent = Intent.createChooser(sendIntent, null)

        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT,
            "${fact.value} \n\n -Provided by Chuck Facts App")
        sendIntent.type = "text/plain"
        startActivity(shareIntent)
    }

    private fun navigateToSavedFacts(){
        view?.findNavController()?.navigate(action_factsFragment_to_savedFactsFragment)
    }

    private fun navigateToAbout(){
        view?.findNavController()?.navigate(action_factsFragment_to_aboutFragment)
    }


}