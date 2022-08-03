package com.bf.helpergomdori.ui.ex

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseActivity
import com.bf.helpergomdori.data.remote.RemoteDataSourceImpl
import com.bf.helpergomdori.data.remote.RetrofitBuilder
import com.bf.helpergomdori.databinding.ActivityExBinding
import com.bf.helpergomdori.databinding.ActivityMainBinding
import com.bf.helpergomdori.model.DataIntent
import com.bf.helpergomdori.model.DataState
import com.bf.helpergomdori.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExActivity : BaseActivity() {
    private val binding by bindingRes<ActivityExBinding>(R.layout.activity_ex)
    private lateinit var viewModel: ExViewModel
    private val TAG = "MainActivity"

    private var adapter = ExAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    private fun setupUI(){
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@ExActivity)
            recyclerView.run {
                addItemDecoration(
                    DividerItemDecoration(
                        recyclerView.context,
                        (recyclerView.layoutManager as LinearLayoutManager).orientation
                    )
                )
            }
            recyclerView.adapter = adapter
        }
    }

    private fun setupClicks() {
        binding.buttonShowUsers.setOnClickListener {
            lifecycleScope.launch {
                viewModel.dataIntent.send(DataIntent.FetchData)
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                RemoteDataSourceImpl(
                    RetrofitBuilder.apiService,
                    Dispatchers.IO
                )
            )
        )[ExViewModel::class.java]
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.dataState.collect{
                when(it) {
                    is DataState.Inactive -> {
                        Log.d(TAG, "observeViewModel: Initial state of StateFlow")
                    }
                    is DataState.Loading -> {
                        binding.buttonShowUsers.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is DataState.ResponseData -> {
                        binding.progressBar.visibility = View.GONE
                        binding.buttonShowUsers.visibility = View.GONE
                        renderList(it.data.data)
                    }
                    is DataState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.buttonShowUsers.visibility = View.VISIBLE
                        Toast.makeText(this@ExActivity, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun renderList(users: List<User>) {
        binding.recyclerView.visibility = View.VISIBLE
        users.let { listOfUsers -> listOfUsers.let { adapter.addData(it) } }
        adapter.notifyDataSetChanged()
    }

}