package com.egnize.deviceinfo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.egnize.deviceinfo.R
import com.egnize.deviceinfo.model.MenuItems
import com.google.android.material.textview.MaterialTextView


/**
 * Created by VINAY on 2019-12-16.
 * vinay6kr@gmail.com
 */
class MenuAdapter (layoutResId: Int, data: List<MenuItems>) : BaseQuickAdapter<MenuItems, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: MenuItems) {
        helper.setText(R.id.txtName, item.name)
        helper.setImageDrawable(R.id.category_image, item.img)


    }
}