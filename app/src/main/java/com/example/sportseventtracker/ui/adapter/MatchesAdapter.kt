package com.example.sportseventtracker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sportseventtracker.R
import com.example.sportseventtracker.databinding.MatchItemBinding
import com.example.sportseventtracker.ui.MatchUiModel

class MatchesAdapter: RecyclerView.Adapter<MatchesAdapter.VH>() {

    private val matchesList = mutableListOf<MatchUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesAdapter.VH {
        return VH(MatchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MatchesAdapter.VH, position: Int) {
        holder.bind(matchesList[position])
    }

    override fun getItemCount(): Int = matchesList.size

    fun setItems(matches: List<MatchUiModel>) {
        updateItems(matches)
    }

    private fun updateItems(newItems: List<MatchUiModel>) {
        val diffCallback = MatchesDiffCallback(matchesList, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        matchesList.clear()
        matchesList.addAll(newItems)

        diffResult.dispatchUpdatesTo(this)
    }

    inner class VH(private val binding: MatchItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(match: MatchUiModel) {
            with(binding) {
                countdownTimer.text = match.timeLeft
                competitorOneTV.text = match.competitor1
                competitorTwoTV.text = match.competitor2
                favoriteIcon.setOnClickListener {
                    //TODO on click change favourite and color
                    favoriteIcon.setColorFilter(ContextCompat.getColor(favoriteIcon.context, R.color.yellow))
                }
            }
        }
    }
}

class MatchesDiffCallback(
    private val oldList: List<MatchUiModel>,
    private val newList: List<MatchUiModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].matchId == newList[newItemPosition].matchId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
