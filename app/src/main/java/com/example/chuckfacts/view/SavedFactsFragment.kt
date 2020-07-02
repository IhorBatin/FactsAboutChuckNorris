package com.example.chuckfacts.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chuckfacts.R
import com.example.chuckfacts.adapter.FactsAdapter
import com.example.chuckfacts.ext.showView
import com.example.chuckfacts.util.ChuckFactResponse
import kotlinx.android.synthetic.main.fragment_saved_facts.*
import timber.log.Timber

// TODO: If no saved items, display some default message

class SavedFactsFragment : Fragment() {

    private val viewModel by lazy { (requireActivity() as MainActivity).viewModel }

    private var allFactsFromDB: List<ChuckFactResponse> = listOf()
    private var rvAdapter: FactsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Timber.i("onCreateView")
        return inflater.inflate(R.layout.fragment_saved_facts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated")

        setHasOptionsMenu(true)
        setupObservers()
        viewModel.getAllSavedFacts()

        // Setting up RecyclerView
        rvAdapter = FactsAdapter(viewModel)
        rv_facts_list.adapter = rvAdapter
        rv_facts_list.layoutManager = LinearLayoutManager(context)
        rv_facts_list.setHasFixedSize(true)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.removeItem(R.id.mi_saved_facts)
        menu.removeItem(R.id.mi_category)

        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_random_facts -> {
                navigateToRandomFacts()
                true
            }
            R.id.mi_about -> {
                navigateToAbout()
                Toast.makeText(activity, "About", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setupObservers(){
        viewModel.getAllSavedFactsLiveData().observe(viewLifecycleOwner, Observer {factsList ->
            allFactsFromDB = factsList
            rvAdapter!!.updateFactsList(allFactsFromDB)

            // Setting visibility of message to show if no facts are stored in DB
            if(factsList.isEmpty()) tv_no_saved_facts.showView(true)
            else tv_no_saved_facts.showView(false)
        })
    }

    private fun navigateToRandomFacts(){
        view?.findNavController()?.navigate(R.id.action_savedFactsFragment_to_factsFragment)
    }

    private fun navigateToAbout(){
        view?.findNavController()?.navigate(R.id.action_savedFactsFragment_to_aboutFragment)
    }
}