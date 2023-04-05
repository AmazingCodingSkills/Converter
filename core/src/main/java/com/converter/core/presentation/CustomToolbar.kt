package com.converter.core.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.converter.core.R
import com.converter.core.databinding.CustomToolbarBinding


class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyle: Int = 0
) : Toolbar(
    context,
    attrs,
    defStyle
) {

    private val binding: CustomToolbarBinding =
        CustomToolbarBinding.inflate(LayoutInflater.from(context), this)

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar, defStyle, 0)
        val title = typedArray.getString(R.styleable.CustomToolbar_ctbTitle)
        val changeFavouriteBtnEnabled =
            typedArray.getBoolean(R.styleable.CustomToolbar_ctbIsBtnChangeCurrency, false)
        val changeCountryBtnEnabled =
            typedArray.getBoolean(R.styleable.CustomToolbar_ctbIsBtnChangeCountry, false)
        setTitle(title)
        setButtonEndEnabled(changeFavouriteBtnEnabled)
        setButtonStartEnabled(changeCountryBtnEnabled)

    }

    private fun setTitle(title: String?) {
        binding.headingMainScreen.text = title
    }

    private fun setButtonEndEnabled(isEnabled: Boolean) {
        binding.changeCurrencyButton.visibility = if (isEnabled) View.VISIBLE else View.GONE
    }

    private fun setButtonStartEnabled(isEnabled: Boolean) {
        binding.cardViewForButton.visibility = if (isEnabled) View.VISIBLE else View.GONE
    }

    fun setBaseCountryBtn(drawableId: Int) {
        binding.buttonOpenBottomSheetMainScreen.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                drawableId
            )
        )
    }

    fun setCountryClickListener(action: () -> Unit) {
        binding.cardViewForButton.setOnClickListener {
            action.invoke()
        }
    }

    fun setFavouriteCurrencyListener(action: () -> Unit) {
        binding.changeCurrencyButton.setOnClickListener {
            action.invoke()
        }
    }
}
