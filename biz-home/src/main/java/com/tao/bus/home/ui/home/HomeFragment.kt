package com.tao.bus.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.base.base.BaseFragment
import com.example.bus.home.databinding.FragmentHomeBinding
import com.example.lib_ktx.viewbinding.Method
import com.example.lib_ktx.viewbinding.binding
import com.example.lib_widget.LoadingLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val binding by binding<FragmentHomeBinding>(Method.INFLATE)

    private val mViewModel by viewModels<HomeViewModel>()

    private val mAdapter by lazy { ArticleAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.run {
            list.layoutManager = LinearLayoutManager(requireContext())
            list.adapter = mAdapter

            smartRefresh.setOnRefreshListener {
                mViewModel.requestData(true)
            }

            smartRefresh.setOnLoadMoreListener {
                mViewModel.requestData(false)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mViewModel.uiState.collect {
                    binding.smartRefresh.setEnableRefresh(it.loadState != LoadingLayout.State.Loading)
                    binding.smartRefresh.setEnableLoadMore((it.loadState != LoadingLayout.State.Loading) || it.over)
                    if (it.loadState != LoadingLayout.State.Loading) {
                        binding.smartRefresh.finishRefresh()
                        binding.smartRefresh.finishLoadMore()
                    }

                    binding.loadState.setState(it.loadState)

                    mAdapter.submitList(it.dataList)
                }
            }

        }
        return binding.root
    }

}