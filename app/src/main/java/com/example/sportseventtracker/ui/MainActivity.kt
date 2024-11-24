package com.example.sportseventtracker.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.example.sportseventtracker.R
import com.example.sportseventtracker.databinding.ActivityMainBinding
import com.example.sportseventtracker.ui.adapter.SportsAdapter
import com.example.sportseventtracker.ui.model.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private val sportsAdapter = SportsAdapter(
        onMatchFavoriteClick = { match ->
            viewModel.setMatchFavourite(match)
        },
        filterFavourite = { sport ->
            viewModel.filterSports(sport)
        }
    )

    private var _binding: ActivityMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.top_toolbar)
        setSupportActionBar(toolbar)

        _binding = ActivityMainBinding.bind(findViewById(R.id.main))

        binding.sportsRecyclerView.apply {
            adapter = sportsAdapter
            itemAnimator = null
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UiState.Success -> {
                        sportsAdapter.setItems(state.sports)

                        binding.loading.visibility = View.GONE
                        binding.sportsRecyclerView.visibility = View.VISIBLE
                    }

                    is UiState.Empty -> {
                        sportsAdapter.setItems(emptyList())
                    }

                    is UiState.Error -> {
                        logError(state.tag, state.exception)
                        sportsAdapter.setItems(emptyList())

                        binding.loading.visibility = View.GONE
                        binding.sportsRecyclerView.visibility = View.GONE

                        binding.errorMessage.text = state.message
                        binding.errorMessage.visibility = View.VISIBLE
                    }

                    UiState.Loading -> {
                        sportsAdapter.setItems(emptyList())

                        binding.loading.visibility = View.VISIBLE
                        binding.sportsRecyclerView.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun logError(tag: String, exception: Throwable) {
        val message = exception.message ?: "No message available"
        Log.e(tag, message, exception)
    }
}
