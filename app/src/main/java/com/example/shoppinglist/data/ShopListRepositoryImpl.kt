package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl@Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper
    ) : ShopListRepository {


    override suspend fun addShopItem(shopItem: ShopItem) {
        val a = mapper.mapEntityToDbModel(shopItem)
        shopListDao.addShopItem(a)
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(mapper.mapEntityToDbModel(shopItem).id)
    }

    override suspend fun upgradeShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun getShopItem(shopItemID: Int): ShopItem {
        return mapper.mapDbModelToEntity(shopListDao.getShopItem(shopItemID))
    }

    override fun getShopItemList(): LiveData<List<ShopItem>> {
        val listShopItemDbModel = shopListDao.getShopList()
//        return MediatorLiveData<List<ShopItem>>().apply {
//            addSource(listShopItemDbModel) {
//                value = mapper.mapListDbModelToListEntity(it)
//            }
//        }
        return Transformations.map(listShopItemDbModel) {
            mapper.mapListDbModelToListEntity(it)
        }
        //так же можно вместо медиатора использовать Transformation.map
        //Под капотом которого все тот же медиатор
    }
}