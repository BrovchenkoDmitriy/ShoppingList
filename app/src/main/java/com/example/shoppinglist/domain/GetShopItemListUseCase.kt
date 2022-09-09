package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetShopItemListUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {
    fun getShopItemList(): LiveData<List<ShopItem>> {
        return shopListRepository.getShopItemList()
    }
}