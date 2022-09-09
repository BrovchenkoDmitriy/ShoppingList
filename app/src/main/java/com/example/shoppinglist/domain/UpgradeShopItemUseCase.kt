package com.example.shoppinglist.domain

import javax.inject.Inject

class UpgradeShopItemUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {
    suspend fun upgradeShopItem(shopItem: ShopItem) {
        shopListRepository.upgradeShopItem(shopItem)
    }
}