package com.example.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.domain.DeleteShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemListUseCase
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.UpgradeShopItemUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val getShopItemListUseCase: GetShopItemListUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
    private val upgradeShopItemUseCase: UpgradeShopItemUseCase,
    ) : ViewModel() {

    val shopList = getShopItemListUseCase.getShopItemList()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun upgradeShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            val newItem = shopItem.copy(enabled = !shopItem.enabled)
            upgradeShopItemUseCase.upgradeShopItem(newItem)
        }
    }
}