package com.bf.helpergomdori.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<B: ViewDataBinding>(private val resId: Int) : Fragment(){

    private var _binding : B? = null
    val binding get() = _binding!!

    private var _navController: NavController? = null
    val navController get() = _navController

    abstract fun createView(binding: B)
    abstract fun createFragment() // livedata observing, adapter setting 등의 작업 진행

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _navController = this.findNavControllerSafely()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, resId, container, false)
        createView(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setCurrentNavController(currentController: NavController?){
        if (currentController != null) {
            _navController = currentController
        }
    }

    fun Fragment.findNavControllerSafely(): NavController? {
        return if (isAdded) {
            findNavController()
        } else {
            null
        }
    }
}