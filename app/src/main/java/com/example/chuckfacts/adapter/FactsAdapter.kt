package com.example.chuckfacts.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.chuckfacts.R
import com.example.chuckfacts.util.ChuckFactResponse
import com.example.chuckfacts.viewmodel.FactsViewModel
import kotlinx.android.synthetic.main.fact_item.view.*
import timber.log.Timber

class FactsAdapter(
    private val viewModel: FactsViewModel
) : RecyclerView.Adapter<FactsAdapter.FactViewHolder>() {

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
        holder.bind(factsList[position])
    }

    override fun getItemCount() = factsList.size

    fun updateFactsList(newList: List<ChuckFactResponse>){
        Timber.i("Updating RV list")
        factsList = newList
        notifyDataSetChanged()
    }

    inner class FactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textViewFact: TextView = itemView.tv_fact_text
        private val buttonDelete: ImageButton = itemView.ib_delete_fact
        private val buttonShare: ImageButton = itemView.ib_share_fact

        fun bind(factItem: ChuckFactResponse){
            buttonDelete.setOnClickListener {
                Timber.i("Clicked DEL on ${factItem.value}")
                viewModel.deleteFact(factItem)
            }

            buttonShare.setOnClickListener {
                Timber.i("Clicked SHARE on ${factItem.value}")

                val sendIntent = Intent()
                val shareIntent: Intent = Intent.createChooser(sendIntent, null)
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "${factItem.value} \n\n -Provided by Chuck Facts App")
                sendIntent.type = "text/plain"
                startActivity(itemView.context, shareIntent, null)
            }
        }
    }
}