package com.example.base.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.base.utils.TLog

open class BaseFragment : Fragment() {
    companion object {
        private const val TAG = "BaseFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TLog.i(TAG, "$this onCreate $savedInstanceState")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TLog.i(TAG, "$this onViewCreated $savedInstanceState")
    }

    override fun onStart() {
        super.onStart()
        TLog.i(TAG, "$this onStart")
    }

    override fun onResume() {
        super.onResume()
        TLog.i(TAG, "$this onResume")
    }

    override fun onPause() {
        super.onPause()
        TLog.i(TAG, "$this onPause")
    }

    override fun onStop() {
        super.onStop()
        TLog.i(TAG, "$this onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        TLog.i(TAG, "$this onSaveInstanceState")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        TLog.i(TAG, "$this onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        TLog.i(TAG, "$this onDestroy")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        TLog.i(TAG, "$this onHiddenChanged $hidden")
    }
}