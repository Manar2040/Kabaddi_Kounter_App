package com.example.kabaddi_kounter_app

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kabaddi_kounter_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: ScoreViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val historyAdapter = ScoreHistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Set lifecycle owner for LiveData observation
        binding.lifecycleOwner = this

        // Set the ViewModel for data binding
        binding.viewModel = viewModel

        // Set up RecyclerView
        setupRecyclerView()

        // Observe history and winner
        observeHistory()
        observeWinner()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = historyAdapter
        }
    }

    private fun observeHistory() {
        viewModel.history.observe(this) { history ->
            historyAdapter.submitList(history)
        }
    }

    private fun observeWinner() {
        viewModel.winnerText.observe(this) { winnerText ->
            binding.winnerText.text = winnerText
            binding.winnerText.visibility = if (winnerText.isNotEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}