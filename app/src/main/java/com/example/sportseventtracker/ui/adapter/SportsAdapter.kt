package com.example.sportseventtracker.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportseventtracker.R
import com.example.sportseventtracker.databinding.SportItemBinding
import com.example.sportseventtracker.ui.model.MatchUiModel
import com.example.sportseventtracker.ui.model.SportUiModel

private const val ROTATE_ANGLE = 180

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
        private var isExpanded = false

        fun bind(sport: SportUiModel) {
            with(binding) {
                sportName.text = sport.sportName

                matchesRecyclerView.apply {
                    adapter = matchesAdapter
                    layoutManager = GridLayoutManager(root.context, 3)
                }

                val matchesToShow = if (sport.showFavoritesOnly) {
                    sport.matches.filter { it.isFavourite }
                } else {
                    sport.matches
                }

                //Needed for when favourite switch is trigger
                changeVisibilityRecyclerAndTextView(matchesToShow)

                matchesAdapter.setItems(matchesToShow)

                showMatchesIcon.setOnClickListener {
                    isExpanded = !isExpanded
                    changeVisibilityRecyclerAndTextView(matchesToShow)

                    showMatchesIcon.rotation += ROTATE_ANGLE
                }

                favouriteSportSwitch.setOnCheckedChangeListener { _, isChecked ->
                    filterFavourite(sport.copy(showFavoritesOnly = isChecked))
                    noMatchFoundTV.text = if (isChecked) {
                        getString(R.string.no_favorite_matches)
                    } else {
                        getString(R.string.no_matches_found)
                    }
                }
            }
        }

        private fun SportItemBinding.changeVisibilityRecyclerAndTextView(matchesToShow: List<MatchUiModel>) {
            if (isExpanded) {
                if (matchesToShow.isEmpty()) {
                    matchesRecyclerView.visibility = View.GONE
                    noMatchFoundTV.visibility = View.VISIBLE
                } else {
                    matchesRecyclerView.visibility = View.VISIBLE
                    noMatchFoundTV.visibility = View.GONE
                }
            } else {
                matchesRecyclerView.visibility = View.GONE
                noMatchFoundTV.visibility = View.GONE
            }
        }

        private fun getString(stringResource: Int) =
            binding.root.context.getString(stringResource)
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
