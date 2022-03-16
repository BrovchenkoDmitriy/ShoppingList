package com.example.shoppinglist.domain

interface ShopListRepository {
    fun addShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)
    fun upgradeShopItem(shopItem: ShopItem)
    fun getShopItem(shopItemID: Int): ShopItem
    fun getShopItemList(): List<ShopItem>
}