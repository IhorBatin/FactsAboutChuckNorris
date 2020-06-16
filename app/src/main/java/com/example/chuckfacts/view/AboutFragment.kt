package com.example.chuckfacts.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.chuckfacts.R

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.removeItem(R.id.mi_about)
        menu.removeItem(R.id.mi_category)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_saved_facts -> {
                view?.findNavController()?.navigate(R.id.action_aboutFragment_to_savedFactsFragment)
                true
            }
            R.id.mi_random_facts -> {
                view?.findNavController()?.navigate(R.id.action_aboutFragment_to_factsFragment)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

}