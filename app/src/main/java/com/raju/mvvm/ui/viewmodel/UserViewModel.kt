package com.raju.mvvm.ui.viewmodel

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.raju.mvvm.data.model.User
import com.raju.mvvm.data.source.UserRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import android.arch.lifecycle.Transformations



class UserViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    private val matchResult: MutableLiveData<MutableList<User>> = MutableLiveData()
    private val matchError: MutableLiveData<String> = MutableLiveData()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun userResult(): LiveData<MutableList<User>> {
        return matchResult
    }

    fun userError(): LiveData<String> {
        return matchError
    }

    fun loadUsers() {
        compositeDisposable.add(userRepository.getUsers()!!
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        Consumer<MutableList<User>> {
                            it -> matchResult.postValue(it)
                        },
                        Consumer {
                            t -> matchError.postValue(t.message)
                        }
                ))
    }

    fun searchUsers(text:String): Observable<MutableList<User>> {
        return userRepository.searchUsers(text)!!
    }

    fun disposeElements() {
        compositeDisposable.clear()
    }
}