package com.example.chuckfacts.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.chuckfacts.util.ChuckFactResponse
import com.example.chuckfacts.viewmodel.FactsViewModel
import com.example.chuckfacts.databinding.FactItemBinding
import timber.log.Timber

class FactsAdapter(
    private val viewModel: FactsViewModel
) : RecyclerView.Adapter<FactsAdapter.FactViewHolder>() {

    private var factsList = listOf<ChuckFactResponse>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val binding = FactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.bind(factsList[position])
    }

    override fun getItemCount() = factsList.size

    fun updateFactsList(newList: List<ChuckFactResponse>){
        Timber.i("Updating RV list")
        factsList = newList
        notifyDataSetChanged()
    }

    inner class FactViewHolder(private val binding: FactItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(factItem: ChuckFactResponse){
            binding.tvFactText.text = factItem.value

            binding.ibDeleteFact.setOnClickListener {
                Timber.i("Clicked DEL on ${factItem.value}")
                viewModel.deleteFact(factItem)
            }

            binding.ibShareFact.setOnClickListener {
                Timber.i("Clicked SHARE on ${factItem.value}")

                val sendIntent = Intent()
                val shareIntent: Intent = Intent.createChooser(sendIntent, null)
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "${factItem.value} \n\n -Provided by Chuck Facts App")
                sendIntent.type = "text/plain"
                startActivity(binding.root.context, shareIntent, null)
            }
        }
    }
}
