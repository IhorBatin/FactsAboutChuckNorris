package com.example.chuckfacts.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.chuckfacts.R
import com.example.chuckfacts.R.id.action_factsFragment_to_aboutFragment
import com.example.chuckfacts.R.id.action_factsFragment_to_savedFactsFragment
import com.example.chuckfacts.R.id.mi_about
import com.example.chuckfacts.R.id.mi_category
import com.example.chuckfacts.R.id.mi_random_facts
import com.example.chuckfacts.R.id.mi_saved_facts
import com.example.chuckfacts.R.id.pb_loading
import com.example.chuckfacts.R.id.tv_fact
import com.example.chuckfacts.databinding.FragmentFactBinding
import com.example.chuckfacts.ext.showView
import com.example.chuckfacts.util.ChuckFactResponse
import com.example.chuckfacts.viewmodel.FactsViewModel
import timber.log.Timber
import java.util.Locale

class FactsFragment : Fragment() {

    private lateinit var visibleFact: ChuckFactResponse
    private var currentCategory: String = "random"
    private var listOfCategories: List<String> = listOf()
    private lateinit var factField: TextView
    private lateinit var progressBar: ProgressBar
    private var toast: Toast? = null
    private var _binding: FragmentFactBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FactsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        requireActivity().actionBar?.setDisplayShowTitleEnabled(true)

        _binding = FragmentFactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupObservers()

        factField = view.findViewById(tv_fact)
        progressBar = view.findViewById(pb_loading)

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
        for (category in listOfCategories){
            menu[0].subMenu?.add(category.uppercase())
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
                showToast(R.string.string_about)
                true
            }
            mi_category -> {
                //
                true
            }
            else -> {
                currentCategory = item.toString().lowercase()
                handleOnForwardClick()
                showToast("Selected Category: ${currentCategory.uppercase(Locale.ROOT)}")
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setupObservers(){
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
        Timber.i("Making Request on category: $currentCategory")
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
        showToast(R.string.saving)
        Timber.i("Saving -> ${visibleFact.value}")
        viewModel.saveFact(visibleFact)
    }

    private fun updateFactText(fact: ChuckFactResponse){
        visibleFact = fact
        progressBar.showView(false)

        factField.let {
            it.text = visibleFact.value
            it.showView(true)
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

    private fun showToast(msg: Int) {
        if (toast != null) toast?.cancel()
        toast = Toast.makeText(requireContext(), getString(msg), Toast.LENGTH_SHORT)
        toast?.show()
    }

    private fun showToast(msg: String) {
        if (toast != null) toast?.cancel()
        toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
        toast?.show()
    }

    private fun navigateToSavedFacts(){
        view?.findNavController()?.navigate(action_factsFragment_to_savedFactsFragment)
    }

    private fun navigateToAbout(){
        view?.findNavController()?.navigate(action_factsFragment_to_aboutFragment)
    }
}