package com.example.shoppinglist.domain

class UpgradeShopItemUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun upgradeShopItem(shopItem: ShopItem) {
        shopListRepository.upgradeShopItem(shopItem)
    }
}