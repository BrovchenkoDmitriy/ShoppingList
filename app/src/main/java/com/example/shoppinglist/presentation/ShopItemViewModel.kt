package com.example.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemUseCase
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.UpgradeShopItemUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopItemViewModel @Inject constructor(
    private val upgradeShopItemUseCase: UpgradeShopItemUseCase,
    private val addShopItemUseCase: AddShopItemUseCase,
    private val getShopItemUseCase: GetShopItemUseCase
) : ViewModel() {

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _closeShopItemScreen = MutableLiveData<Unit>()
    val closeShopItemScreen: LiveData<Unit>
        get() = _closeShopItemScreen

//    private val scope = CoroutineScope(Dispatchers.IO)
//
//    override fun onCleared() {
//        super.onCleared()
//        scope.cancel()
//    }

    fun getShopItem(shopItemID: Int) {
        viewModelScope.launch {
            val item = getShopItemUseCase.getShopItem(shopItemID)
            _shopItem.value = item
        }


    }

    fun addShopItem(inputName: String?, inputCount: String?) {

        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            viewModelScope.launch {
                addShopItemUseCase.addShopItem(ShopItem(name, count, true))
                finish()
            }
        }
    }

    fun upgradeShopItem(inputName: String?, inputCount: String?) {

        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _shopItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = name, count = count)
                    upgradeShopItemUseCase.upgradeShopItem(item)
                    finish()
                }
            }
        }
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (exception: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    private fun finish() {
        _closeShopItemScreen.value = Unit
    }

    fun resetInputNameError() {
        _errorInputName.value = false
    }

    fun resetInputCountError() {
        _errorInputCount.value = false
    }


}