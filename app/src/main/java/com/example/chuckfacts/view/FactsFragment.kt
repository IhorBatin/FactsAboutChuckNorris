package com.example.chuckfacts.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.chuckfacts.R
import com.example.chuckfacts.R.id.*
import com.example.chuckfacts.ext.showView
import com.example.chuckfacts.util.ChuckFactResponse
import com.example.chuckfacts.viewmodel.FactsViewModel
import kotlinx.android.synthetic.main.bottom_control_bar.*
import timber.log.Timber
import java.util.*

class FactsFragment : Fragment() {

    private lateinit var visibleFact: ChuckFactResponse
    private var currentCategory: String = "random"
    private var listOfCategories: List<String> = listOf()
    private lateinit var factField: TextView
    private lateinit var progressBar: ProgressBar
    private var toast: Toast? = null

    private val viewModel: FactsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().actionBar?.setDisplayShowTitleEnabled(true)

        return inflater.inflate(R.layout.fragment_fact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupObservers()

        factField = view.findViewById(tv_fact)
        progressBar = view.findViewById(pb_loading)

        button_forward.setOnClickListener { handleOnForwardClick() }
        button_share.setOnClickListener { handleOnShareClick() }
        button_save.setOnClickListener { handleOnSaveClick() }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.removeItem(mi_random_facts)

        // Populating category sub-menu with categories received from API
        for (i in listOfCategories){
            menu[0].subMenu.add(i.toUpperCase(Locale.ROOT))
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
                currentCategory = item.toString().toLowerCase(Locale.ROOT)
                handleOnForwardClick()
                showToast("Selected Category: ${currentCategory.toUpperCase(Locale.ROOT)}")
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
        Timber.i("Sharing -> ${visibleFact.value}")
        shareFact(visibleFact)
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