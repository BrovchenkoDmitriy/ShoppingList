package com.example.shoppinglist.presentation

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.ShoppingListApp
import com.example.shoppinglist.databinding.FragmentShopItemBinding
import com.example.shoppinglist.domain.ShopItem
import javax.inject.Inject
import kotlin.concurrent.thread

class ShopItemFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as ShoppingListApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var shopItemViewModel: ShopItemViewModel

    private var _binding: FragmentShopItemBinding? = null
    private val binding: FragmentShopItemBinding
        get() = _binding ?: throw RuntimeException("FragmentShopItemBinding == null")


    private lateinit var onEditingIsFinishedListener: OnEditingIsFinishedListener

    private var screenMode = SCREEN_MODE_UNDEFINED
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
        if (context is OnEditingIsFinishedListener) {
            onEditingIsFinishedListener = context
        } else throw RuntimeException("Activity must implement OnEditingIsFinishedListener")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopItemBinding.inflate(
            layoutInflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        shopItemViewModel = ViewModelProvider(this, viewModelFactory)[ShopItemViewModel::class.java]
        binding.viewModel = shopItemViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        nameAndCountTextListener()
        chooseRightMode()
        setupViewModelObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupViewModelObservers() {
        shopItemViewModel.closeShopItemScreen.observe(viewLifecycleOwner) {
            onEditingIsFinishedListener.onEditingIsFinishedListener()
        }

    }

    private fun nameAndCountTextListener() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shopItemViewModel.resetInputNameError()
            }

            override fun afterTextChanged(s: Editable?) {
//                if (s?.isBlank() == true) binding.tilName.error =
//                    getString(R.string.error_input_name)
            }
        })

        binding.etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shopItemViewModel.resetInputCountError()
            }

            override fun afterTextChanged(s: Editable?) {
//                if ((s == null) || s.isBlank() || (s.toString().toInt() <= 0)) {
//                    binding.tilCount.error = getString(R.string.error_input_count)
//                }
            }
        })
    }

    private fun chooseRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        shopItemViewModel.getShopItem(shopItemId)
        binding.saveButton.setOnClickListener {
            shopItemViewModel.upgradeShopItem(
                binding.etName.text?.toString(),
                binding.etCount.text?.toString()
            )
        }
    }

    private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
//            shopItemViewModel.addShopItem(
//                binding.etName.text?.toString(),
//                binding.etCount.text?.toString()
//            )
            thread {
                context?.contentResolver?.insert(
                    Uri.parse("content://com.example.shoppinglist/shop_items"),
                    ContentValues().apply {
                        put("id", 0)
                        put("name", binding.etName.text?.toString())
                        put("count", binding.etCount.text?.toString()?.toInt())
                        put("enabled", true)
                    }
                )
            }
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw java.lang.RuntimeException("Param screen mode is wrong")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw java.lang.RuntimeException("Param shopItemId is absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    interface OnEditingIsFinishedListener {
        fun onEditingIsFinishedListener()
    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val SHOP_ITEM_ID = "ExtraShopItemID"
        private const val SCREEN_MODE_UNDEFINED = ""

        fun newInstanceAddItem(): Fragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): Fragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }
}