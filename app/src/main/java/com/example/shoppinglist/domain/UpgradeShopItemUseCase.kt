package com.example.shoppinglist.domain

class UpgradeShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun upgradeShopItem(shopItem: ShopItem) {
        shopListRepository.upgradeShopItem(shopItem)
    }
}