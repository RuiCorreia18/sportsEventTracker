package com.example.sportseventtracker.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sportseventtracker.R
import com.example.sportseventtracker.databinding.ActivityMainBinding
import com.example.sportseventtracker.ui.adapter.SportsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private val sportsAdapter = SportsAdapter()

    private var _binding: ActivityMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        _binding = ActivityMainBinding.bind(findViewById(R.id.main))

        binding.sportsRecyclerView.adapter = sportsAdapter

        lifecycleScope.launch {
            viewModel.sports.collect { sports ->
                sportsAdapter.setItems(sports)
            }
        }

    }
}
