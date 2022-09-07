package com.example.shoppinglist.data

import android.app.Application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl(application: Application) : ShopListRepository {

    private val shopListDao = AppDataBase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()


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