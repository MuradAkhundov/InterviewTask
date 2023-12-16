package com.akhundovmurad.tayqatechtask.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.akhundovmurad.tayqatechtask.databinding.FragmentBottomSheetBinding
import com.akhundovmurad.tayqatechtask.filter.FilterType
import com.akhundovmurad.tayqatechtask.ui.adapter.FilterAdapter
import com.akhundovmurad.tayqatechtask.ui.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetFragment(private val type: FilterType) : BottomSheetDialogFragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var filterAdapter: FilterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        filterAdapter = FilterAdapter(emptyList())

        binding.recycler.adapter = filterAdapter
        when (type) {
            FilterType.COUNTRY -> {
                viewModel.countryFilter.observe(viewLifecycleOwner) {
                    filterAdapter.updateData(it)
                    val selectedCountries = viewModel.getSelectedCountries()
                    filterAdapter.restoreState(selectedCountries)
                }
            }
            FilterType.CITY -> {
                viewModel.cityFilter.observe(viewLifecycleOwner) {
                    filterAdapter.updateData(it)
                    filterAdapter.restoreState(viewModel.getSelectedCities())
                }
            }
        }

        binding.apply.setOnClickListener {
            val selectedItems = filterAdapter.getSelectedItems(type)
            viewModel.applyFilters(selectedItems, type)

            if (type == FilterType.COUNTRY) {
                viewModel.saveSelectedCountries(selectedItems)
            } else if (type == FilterType.CITY) {
                viewModel.saveSelectedCities(selectedItems)
            }
            dismiss()
        }

        return binding.root
    }
}
