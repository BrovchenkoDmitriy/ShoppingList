package com.example.shoppinglist.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider


class ViewModelFactory @Inject constructor(
    private val viewModelProviders: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d("TAGI","modelClass.simpleName")
        return viewModelProviders[modelClass]?.get() as T
    }
}



// if we get Inheritance from an interface with '@JvmDefault' members is only allowed with -Xjvm-default option
// solution below
// https://stackoverflow.com/questions/70992947/how-do-i-resolve-error-message-inheritance-from-an-interface-with-jvmdefault