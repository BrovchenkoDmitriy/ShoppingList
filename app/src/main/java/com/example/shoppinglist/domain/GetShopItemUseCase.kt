package com.example.shoppinglist.domain

import javax.inject.Inject

class GetShopItemUseCase @Inject constructor(private val shopListRepository: ShopListRepository){
    suspend fun getShopItem(shopItemID: Int): ShopItem{
        return shopListRepository.getShopItem(shopItemID)
    }
}