package com.raju.mvvm.dagger.modules.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.raju.mvvm.dagger.ViewModelKey
import com.raju.mvvm.ui.viewmodel.UserViewModel
import com.raju.mvvm.ui.viewmodel.base.GitHubViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Rajashekhar Vanahalli on 14/08/18.
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    internal abstract fun bindUserViewModel(viewModel: UserViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: GitHubViewModelFactory): ViewModelProvider.Factory
}