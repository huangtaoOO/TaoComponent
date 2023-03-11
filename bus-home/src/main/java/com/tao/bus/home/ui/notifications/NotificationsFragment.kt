package com.tao.bus.home.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bus.home.databinding.FragmentNotificationsBinding
import com.example.lib_ktx.viewbinding.Method
import com.example.lib_ktx.viewbinding.binding
import kotlinx.coroutines.launch

class NotificationsFragment : Fragment() {

    private val binding by binding<FragmentNotificationsBinding>(Method.INFLATE)

    private val mViewModel by viewModels<NotificationsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.root
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mViewModel.text.collect {
                    binding.textNotifications.text = it
                }
            }

        }
        return binding.root
    }

}