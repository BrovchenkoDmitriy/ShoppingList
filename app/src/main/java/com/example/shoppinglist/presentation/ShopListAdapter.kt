package com.example.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallBack()) {
    var times = 0

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    companion object {
        const val VIEW_ENABLED = 0
        const val VIEW_DISABLED = 1
        const val MAX_POOL_SIZE = 10
    }

    //создаем view для каждого итема в зависимости от viewType
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        Log.d("onCreateViewHolder", "onCreateViewHolder use ${++times} times")
        val layout = when (viewType) {
            VIEW_ENABLED -> R.layout.item_shop_enabled
            VIEW_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown viewType $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    //привязываем к вьюшкам информацию
    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()
        holder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        holder.view.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
    }
    //определяем viewType в зависимости от shopItem.enabled
    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        return if (shopItem.enabled) {
            VIEW_ENABLED
        } else {
            VIEW_DISABLED
        }
    }


}