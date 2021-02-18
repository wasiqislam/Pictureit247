package com.pictureit24.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pictureit24.R
import com.pictureit24.main.network.RemoteService
import com.pictureit24.utils.gone
import com.pictureit24.utils.visible
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private val recycler by lazy {
        findViewById<RecyclerView>(R.id.main_recycleview)
    }

    private lateinit var viewModel: MainActivityViewModel
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecycler()

        viewModel = ViewModelProvider(this, MainProviderFactory(MainRepository(createEndpoints())))
            .get(MainActivityViewModel::class.java)
        disposables.add(
            viewModel.onCreate()
                .subscribe(this::render)
        )
    }

    private fun createEndpoints(): RemoteService.Endpoints {
        return RemoteService.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    private fun render(state: MainActivityViewModel.State) {
        Log.d("TAG", "state: $state" )
        when (state) {
            MainActivityViewModel.State.Init -> initialise()
            MainActivityViewModel.State.Loading -> loading()
            is MainActivityViewModel.State.Error.GeneralError -> processGeneralError()
            is MainActivityViewModel.State.Error.EmptyList -> processEmptyList()
            is MainActivityViewModel.State.Error.ConnectionError -> processConnectionError()
            is MainActivityViewModel.State.Loaded -> processLoaded(state.items)
        }
    }

    private fun processConnectionError() {
        TODO("Not yet implemented")
    }

    private fun processEmptyList() {
        TODO("Not yet implemented")
    }

    private fun processGeneralError() {


    }

    private fun initialise() {
        viewModel.loadData()
    }

    private fun loading() {
        findViewById<View>(R.id.main_progressbar).visible()
    }

    private fun processError() {
        Log.i("TAG", "There is error on processing")
    }

    private fun processLoaded(items: List<MainItem>) {
        findViewById<View>(R.id.main_progressbar).gone()
        (recycler.adapter as MainAdapter).updateContent(items)
    }


    private fun setupRecycler() {
        val adapter = MainAdapter()
        val layoutManager = LinearLayoutManager(this)
        recycler.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }
    }
}