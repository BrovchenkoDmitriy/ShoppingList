package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import java.lang.RuntimeException

object  ShopListRepositoryImpl: ShopListRepository {
    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementID = 0
    private val shopListLD = MutableLiveData<List<ShopItem>>()

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementID++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun upgradeShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        shopList.add(shopItem)

    }

    override fun getShopItem(shopItemID: Int): ShopItem {
        return shopList.find { it.id == shopItemID }?: throw RuntimeException(
            "ShopItem with ID = $shopItemID not found")
    }

    override fun getShopItemList(): LiveData<List<ShopItem>> {
        return shopListLD
    }
    private fun updateList(){
        shopListLD.value = shopList.toList()
    }

}