package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShopListDao {

    @Query("SELECT * FROM shop_items")
    fun getShopList(): LiveData<List<ShopItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItemDbModel: ShopItemDbModel)

    @Query("DELETE FROM shop_items WHERE id = :shopItemID")
    suspend fun deleteShopItem(shopItemID: Int)

    @Query("SELECT * FROM shop_items WHERE id = :shopItemID LIMIT 1")
    suspend fun getShopItem(shopItemID: Int): ShopItemDbModel
}