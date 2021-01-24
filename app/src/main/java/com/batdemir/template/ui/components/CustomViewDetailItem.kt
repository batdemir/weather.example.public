package com.batdemir.template.ui.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.batdemir.template.R

class CustomViewDetailItem(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {
    private val imageViewIcon: ImageView
    private val textViewTitle: TextView
    private val textViewSubTitle: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_detail_items, this, true)
        imageViewIcon = view.findViewById(R.id.image_view_icon)
        textViewTitle = view.findViewById(R.id.text_view_title)
        textViewSubTitle = view.findViewById(R.id.text_view_sub_title)
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.CustomViewDetailItem,
            0,
            0
        ).apply {
            try {
                imageViewIcon.setImageDrawable(getDrawable(R.styleable.CustomViewDetailItem_icon))
                textViewTitle.text = getString(R.styleable.CustomViewDetailItem_title)
                textViewSubTitle.text = getString(R.styleable.CustomViewDetailItem_sub_title)
            } finally {
                recycle()
            }
        }
    }

    fun setTitle(title: String) {
        textViewTitle.text = title
        invalidate()
    }

    fun setSubTitle(subTitle: String) {
        textViewSubTitle.text = subTitle
        invalidate()
    }

    fun setIcon(drawable: Drawable?) {
        imageViewIcon.setImageDrawable(drawable)
        invalidate()
    }

    fun setIcon(resId: Int?) {
        if (resId == null)
            return
        imageViewIcon.setImageResource(resId)
        invalidate()
    }
}