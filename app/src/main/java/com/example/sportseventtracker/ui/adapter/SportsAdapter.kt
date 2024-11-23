package com.example.sportseventtracker.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportseventtracker.databinding.SportItemBinding
import com.example.sportseventtracker.ui.model.MatchUiModel
import com.example.sportseventtracker.ui.model.SportUiModel

class SportsAdapter(
    private val onMatchFavoriteClick: (MatchUiModel) -> Unit,
    private val filterFavourite: (SportUiModel) -> Unit,
) : RecyclerView.Adapter<SportsAdapter.VH>() {

    private val sportsList = mutableListOf<SportUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(SportItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SportsAdapter.VH, position: Int) {
        holder.bind(sportsList[position])
    }

    override fun getItemCount(): Int = sportsList.size

    fun setItems(sports: List<SportUiModel>) {
        updateItems(sports)
    }

    private fun updateItems(newItems: List<SportUiModel>) {
        val diffCallback = SportsDiffCallback(sportsList, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        sportsList.clear()
        sportsList.addAll(newItems)

        diffResult.dispatchUpdatesTo(this)
    }

    inner class VH(private val binding: SportItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private val matchesAdapter = MatchesAdapter(onMatchFavoriteClick)

        fun bind(sport: SportUiModel) {
            with(binding) {
                sportName.text = sport.sportName

                matchesRecyclerView.apply {
                    adapter = matchesAdapter
                    layoutManager = GridLayoutManager(root.context, 3)
                }

                val matchsToShow = if(sport.showFavoritesOnly){
                    sport.matches.filter { it.isFavourite }
                }else{
                    sport.matches
                }
                matchesAdapter.setItems(matchsToShow)

                showMatchsIcon.setOnClickListener {
                    matchesRecyclerView.visibility =
                        if (matchesRecyclerView.visibility == View.GONE) View.VISIBLE else View.GONE
                    showMatchsIcon.rotation += 180
                }

                favouriteSportSwitch.setOnCheckedChangeListener { _, isChecked ->
                    filterFavourite(sport.copy(showFavoritesOnly = isChecked))
                }

            }
        }
    }
}

class SportsDiffCallback(
    private val oldList: List<SportUiModel>,
    private val newList: List<SportUiModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].sportId == newList[newItemPosition].sportId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
