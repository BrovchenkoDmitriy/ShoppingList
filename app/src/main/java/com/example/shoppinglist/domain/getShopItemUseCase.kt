package com.example.shoppinglist.domain

class getShopItemUseCase (private val shopListRepository: ShopListRepository){
    fun getShopItem(shopItemID: Int): ShopItem{
        return shopListRepository.getShopItem(shopItemID)
    }
}