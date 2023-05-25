package com.builditcreative.qfonapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.builditcreative.qfonapp.data.remote.Data
import com.builditcreative.qfonapp.databinding.FragmentHomeBinding
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    private var currentPage = 1
    private var isNestable = false
    private var dataList = mutableListOf<Data>()
    private var orignalList = mutableListOf<Data>()
    private lateinit var adapter: PassengerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

        setObservers()

        viewModel.getPassenger()

    }

    private fun setListeners() {

        binding.searchBar.doOnTextChanged { text, start, before, count ->
            if (orignalList.isEmpty()) {
                orignalList = dataList
            }
            dataList = orignalList.toMutableList().filter {
                it.name.contains(binding.searchBar.text.toString(), true) ||
                        it.airline[0].country.contains(binding.searchBar.text.toString(), true) ||
                        it.airline[0].name.contains(binding.searchBar.text.toString(), false)
            } as MutableList<Data>
            if (binding.searchBar.text.isNullOrEmpty()) {
                dataList = orignalList
            }
            setRecyclerView()
        }


        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight && isNestable && binding.searchBar.text.isNullOrEmpty()) {
                currentPage++
                binding.progressBar.isVisible = true
                viewModel.getPassenger(currentPage)
            }
        })

    }

    private fun setObservers() {
        viewModel.passengerDataResponse.observe(viewLifecycleOwner) {
            currentPage = it.pagination.currentPage
            isNestable = it.pagination.totalPages > currentPage
            if (dataList.isEmpty()) {
                dataList = it.data.toMutableList()
                setRecyclerView()
            } else {
                dataList = dataList.toMutableList().apply { addAll(it.data) }
                setRecyclerView()
            }
            orignalList = dataList
        }
    }

    private fun setRecyclerView() {
        binding.rvList.apply {
            binding.progressBar.isVisible = false
            layoutManager = LinearLayoutManager(requireContext())
            this@HomeFragment.adapter = PassengerAdapter(dataList)
            adapter = this@HomeFragment.adapter
        }

    }

}