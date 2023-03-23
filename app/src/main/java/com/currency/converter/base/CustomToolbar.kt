package com.currency.converter.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.converter.R
import com.example.converter.databinding.CustomToolbarBinding


class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyle: Int = 0
) :
   ConstraintLayout(
        context,
        attrs,
        defStyle
    ) {

    private val binding: CustomToolbarBinding = CustomToolbarBinding.inflate(LayoutInflater.from(context),this)

    init {

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar, defStyle, 0)
        val title = typedArray.getString(R.styleable.CustomToolbar_ctbTitle)
        val isBtnEndEnabled = typedArray.getBoolean(R.styleable.CustomToolbar_ctbIsBtnEnd, false)
        val isBtnStartEnabled = typedArray.getBoolean(R.styleable.CustomToolbar_ctbIsBtnStart, false)
        val buttonCountry =
            typedArray.getResourceId(R.styleable.CustomToolbar_ctbButtonChangeCountry, 0)
        setTitle(title)
        setButtonEndEnabled(isBtnEndEnabled)
        setButtonStartEnabled(isBtnStartEnabled)
        //setStartButton(buttonCountry)
    }

    private fun setTitle(title: String?) {
        binding.headingMainScreen1.text = title
    }

    private fun setButtonEndEnabled(isEnabled: Boolean) {
        binding.changeCurrencyButton1.visibility = if (isEnabled) View.VISIBLE else View.GONE
    }

    private fun setButtonStartEnabled(isEnabled: Boolean) {
        binding.cardViewForButton1.visibility = if (isEnabled) View.VISIBLE else View.GONE
    }

     fun setStartButton(drawableId: Int) {
        binding.buttonOpenBottomSheetMainScreen1.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                drawableId
            )
        )
    }

    fun setIconClickListener(action: () -> Unit) {
        binding.changeCurrencyButton1.setOnClickListener {
            action.invoke()
        }
    }

    fun setFavouriteButtonListener(action: () -> Unit) {
        binding.changeCurrencyButton1.setOnClickListener {
            action.invoke()
        }
    }

    /*override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }*/
}
