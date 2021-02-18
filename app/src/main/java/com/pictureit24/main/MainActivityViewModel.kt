package com.pictureit24.main

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject


class MainActivityViewModel(private val repository: MainRepository) :ViewModel() {


    private val subject = BehaviorSubject.create<State>()
    private val disposables = CompositeDisposable()

    init {
        subject.onNext(State.Init)
    }

    fun onCreate(): Observable<State> {

        return subject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadData() {

        subject.onNext(State.Loading)
        disposables.add(
            repository
                .getList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.d("TAG", it.toString()) }
                .doOnEach { Log.d("TAG", it.toString()) }
                .subscribe(
                    { list -> processLoadedList(list)},
                    { throwable ->
                        subject.onNext(
                            State.Error.GeneralError(throwable)
                        )
                    })
        )
    }

    private fun processLoadedList(list: List<MainItem>) {
        if(list.isNullOrEmpty()){
            subject.onNext(State.Error.EmptyList)
        }else{
            subject.onNext(State.Loaded(list))
        }

    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun openDetail() {
        subject.onNext(State.OpenDetail)
    }

    sealed class State {
        object Init : State()
        object Loading : State()
        object OpenDetail: State()
        data class Loaded(val items: List<MainItem>) : State()
        sealed class Error : State() {
            object ConnectionError : Error()
            data class GeneralError(val throwable: Throwable) : Error()
            object EmptyList: Error()
        }
    }

}
