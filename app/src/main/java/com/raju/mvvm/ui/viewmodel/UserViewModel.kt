package com.raju.mvvm.ui.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.raju.mvvm.data.model.User
import com.raju.mvvm.data.source.UserRepository
import javax.inject.Inject
import android.arch.lifecycle.Transformations
import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.raju.mvvm.R
import com.raju.mvvm.data.model.base.Resource
import com.raju.mvvm.ui.livedata.AbsentLiveData


class UserViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    private val _users = MutableLiveData<List<User>>()

    val users: LiveData<Resource<List<User>>> = Transformations
            .switchMap(_users) { login ->
                userRepository.loadUser()
            }

    fun retry() {
        _users.value?.let {
            _users.value = it
        }
    }

    fun loadUsers() {
        _users.postValue(userRepository.loadUser().value?.data)
    }
}