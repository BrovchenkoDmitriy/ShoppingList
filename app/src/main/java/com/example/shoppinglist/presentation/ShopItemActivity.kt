package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout


class ShopItemActivity : AppCompatActivity() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button
    lateinit var shopItemViewModel: ShopItemViewModel
    private var screenMode = SCREEN_MODE_UNDEFINED
    private var shopItemId: Int = ShopItem.UNDEFINED_ID
    //private var mode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        //mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        //Log.d("ShopItemActivity", "$mode")

        parseIntent()
//        initViews()
//        shopItemViewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
//        nameAndCountTextListener()
        chooseRightMode()
//        setupViewModelObservers()
    }

//    private fun setupViewModelObservers() {
//        shopItemViewModel.errorInputName.observe(this) {
//            val message = if (it) {
//                getString(R.string.error_input_name)
//            } else null
//            tilName.error = message
//        }
//        shopItemViewModel.errorInputCount.observe(this) {
//            val message = if (it) {
//                getString(R.string.error_input_count)
//            } else null
//            tilCount.error = message
//        }
//        shopItemViewModel.closeShopItemScreen.observe(this) {
//            finish()
//        }
//
//    }
//
//    private fun nameAndCountTextListener() {
//        etName.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                shopItemViewModel.resetInputNameError()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                //if (s?.isBlank()==true) tilName.error = getString(R.string.error_input_name)
//            }
//        })
//
//        etCount.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                shopItemViewModel.resetInputCountError()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                //if ((s == null) || s.isBlank() || (s.toString().toInt() <= 0)) {
//                //    tilCount.error = getString(R.string.error_input_count)
//                //}
//            }
//        })
//    }
//
    private fun chooseRightMode() {
    val fragment = when (screenMode) {
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screenMode $screenMode")
        }
    supportFragmentManager.beginTransaction().add(R.id.shop_item_container, fragment)
        .commit()
    }
//
//    private fun launchEditMode() {
//        shopItemViewModel.getShopItem(shopItemId)
//        shopItemViewModel.shopItem.observe(this) {
//            etName.setText(it.name)
//            etCount.setText(it.count.toString())
//        }
//        buttonSave.setOnClickListener {
//            shopItemViewModel.upgradeShopItem(
//                etName.text?.toString(),
//                etCount.text?.toString()
//            )
//        }
//    }
//
//    private fun launchAddMode() {
//        buttonSave.setOnClickListener {
//            shopItemViewModel.addShopItem(
//                etName.text?.toString(),
//                etCount.text?.toString()
//            )
//        }
//    }
//
    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw java.lang.RuntimeException("Param screen mode is wrong")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw java.lang.RuntimeException("Param shopItemId is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }


    }
//
//    private fun initViews() {
//        tilName = findViewById(R.id.til_name)
//        tilCount = findViewById(R.id.til_count)
//        etName = findViewById(R.id.et_name)
//        etCount = findViewById(R.id.et_count)
//        buttonSave = findViewById(R.id.save_button)
//    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_SHOP_ITEM_ID = "ExtraShopItemID"
        private const val SCREEN_MODE_UNDEFINED = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}