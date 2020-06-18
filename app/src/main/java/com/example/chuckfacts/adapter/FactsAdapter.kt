package com.example.chuckfacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chuckfacts.R
import com.example.chuckfacts.util.ChuckFactResponse
import kotlinx.android.synthetic.main.fact_item.view.*

class FactsAdapter : RecyclerView.Adapter<FactsAdapter.FactViewHolder>() {

    private var factsList = listOf<ChuckFactResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.fact_item,
            parent,
            false)

        return FactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        val currentFact = factsList[position]
        holder.textViewFact.text = currentFact.value
    }

    override fun getItemCount() = factsList.size

    fun updateFactsList(newList: List<ChuckFactResponse>){
        factsList = newList
        notifyDataSetChanged() // Can use notifyItemRemoved(Int) to have nice animation of removing certain item
    }

    inner class FactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textViewFact: TextView = itemView.tv_fact_text
        val buttonDelete: ImageButton = itemView.ib_delete_fact
        val buttonShare: ImageButton = itemView.ib_share_fact

        fun onClick(){
            buttonDelete.setOnClickListener {

            }
        }

    }
}