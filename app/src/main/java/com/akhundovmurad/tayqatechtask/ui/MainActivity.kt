package com.akhundovmurad.tayqatechtask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.akhundovmurad.tayqatechtask.databinding.ActivityMainBinding
import com.akhundovmurad.tayqatechtask.filter.FilterType
import com.akhundovmurad.tayqatechtask.ui.adapter.MainAdapter
import com.akhundovmurad.tayqatechtask.ui.fragment.BottomSheetFragment
import com.akhundovmurad.tayqatechtask.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val adapter = MainAdapter(emptyList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : MainViewModel by viewModels()
        viewModel = tempViewModel

        binding = ActivityMainBinding.inflate(layoutInflater)
        setUpClickListeners()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUI()
        observeViewModel()
        setUpClickListeners()
        setContentView(binding.root)
    }
    private fun setUpUI() {
        with(binding) {
            recycler.adapter = adapter
            swipeToRefresh.setOnRefreshListener {
                viewModel.refreshData()
                swipeToRefresh.isRefreshing = false
            }
        }
    }

    private fun observeViewModel() {
        viewModel.adapterData.observe(this) {
            adapter.updateData(it)
        }
    }

    private fun showFilterBottomSheet(type: FilterType) {
        val filterBottomSheetFragment = BottomSheetFragment(type)
        filterBottomSheetFragment.show(supportFragmentManager, filterBottomSheetFragment.tag)
    }

    private fun setUpClickListeners() {
        binding.filterCountry.setOnClickListener { showFilterBottomSheet(FilterType.COUNTRY) }
        binding.filterCity.setOnClickListener { showFilterBottomSheet(FilterType.CITY) }
    }
}


