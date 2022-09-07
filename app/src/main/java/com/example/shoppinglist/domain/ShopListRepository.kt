package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    suspend fun addShopItem(shopItem: ShopItem)
    suspend fun deleteShopItem(shopItem: ShopItem)
    suspend fun upgradeShopItem(shopItem: ShopItem)
    suspend fun getShopItem(shopItemID: Int): ShopItem
    fun getShopItemList(): LiveData<List<ShopItem>>
}