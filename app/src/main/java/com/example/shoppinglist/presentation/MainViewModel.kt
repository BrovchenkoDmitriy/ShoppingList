package com.example.shoppinglist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.*

class MainViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val upgradeShopItemUseCase = UpgradeShopItemUseCase(repository)

    val shopList = getShopItemUseCase.getShopItemList()

    fun deleteShopItem(shopItem: ShopItem){
        deleteShopItemUseCase.deleteShopItem(shopItem)
        }

    fun upgradeShopItem(shopItem: ShopItem){
        val newItem = shopItem.copy(enabled = shopItem.enabled)
        upgradeShopItemUseCase.upgradeShopItem(newItem)
    }
}