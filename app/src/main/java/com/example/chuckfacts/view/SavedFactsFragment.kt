package com.example.chuckfacts.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.chuckfacts.R
import timber.log.Timber

// TODO: Implement RecyclerView to show saved facts
// TODO: Design fact view holder with share and delete buttons
// TODO: In noo saved items display some default mesage

class SavedFactsFragment : Fragment() {

    private val viewModel by lazy { (requireActivity() as MainActivity).viewModel }

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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.removeItem(R.id.mi_saved_facts)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_random_facts -> {
                navigateToRandomFacts()
                true
            }
            R.id.mi_about -> {
                // TODO: Add about info fragment or something similar,with link to OG API
                Toast.makeText(activity, "About", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun navigateToRandomFacts(){
        view?.findNavController()?.navigate(R.id.action_savedFactsFragment_to_factsFragment)
    }

}