package com.egnize.deviceinfo.data

import android.content.Context
import androidx.core.content.ContextCompat
import com.egnize.deviceinfo.R
import com.egnize.deviceinfo.model.MenuItems
import java.util.*


/**
 * Created by VINAY on 2019-12-16.
 * vinay6kr@gmail.com
 */
open class MenuList  private constructor() {
    companion object {
        fun getMenuList(context: Context): ArrayList<MenuItems> {
            val menutList = ArrayList<MenuItems>()


            menutList.add(
                MenuItems(
                    context.resources.getString(R.string.device),
                    ContextCompat.getDrawable(context, R.drawable.ic_home)
                )
            )
            menutList.add(
                MenuItems(
                    context.resources.getString(R.string.mobile_info),
                    ContextCompat.getDrawable(context, R.drawable.ic_tablet)
                )
            )
            menutList.add(
                MenuItems(
                    context.resources.getString(R.string.os),
                    ContextCompat.getDrawable(context, R.drawable.ic_android_logo)
                )
            )
            menutList.add(
                MenuItems(
                    context.resources.getString(R.string.sensor),
                    ContextCompat.getDrawable(context, R.drawable.ic_speedometer)
                )
            )
            menutList.add(
                MenuItems(
                    context.resources.getString(R.string.processor_label),
                    ContextCompat.getDrawable(context, R.drawable.ic_processor)
                )
            )
            menutList.add(
                MenuItems(
                    context.resources.getString(R.string.battery),
                    ContextCompat.getDrawable(context, R.drawable.ic_battery_new)
                )
            )
            menutList.add(
                MenuItems(
                    context.resources.getString(R.string.network),
                    ContextCompat.getDrawable(context, R.drawable.ic_wifi_signal)
                )
            )
//        menutList.add(MenuItems(context.resources.getString(R.string.sim), ContextCompat.getDrawable(context, R.drawable.ic_sim)))
            menutList.add(
                MenuItems(
                    context.resources.getString(R.string.camera),
                    ContextCompat.getDrawable(context, R.drawable.ic_lens)
                )
            )
            menutList.add(
                MenuItems(
                    context.resources.getString(R.string.storage),
                    ContextCompat.getDrawable(context, R.drawable.ic_database)
                )
            )
            menutList.add(
                MenuItems(
                    context.resources.getString(R.string.bluetooth),
                    ContextCompat.getDrawable(context, R.drawable.ic_bluetooth)
                )
            )
            menutList.add(
                MenuItems(
                    context.resources.getString(R.string.display),
                    ContextCompat.getDrawable(context, R.drawable.ic_screens)
                )
            )
            menutList.add(
                MenuItems(
                    context.resources.getString(R.string.features),
                    ContextCompat.getDrawable(context, R.drawable.ic_snowflake)
                )
            )
            menutList.add(
                MenuItems(
                    context.resources.getString(R.string.user_apps),
                    ContextCompat.getDrawable(context, R.drawable.ic_user)
                )
            )
            menutList.add(
                MenuItems(
                    context.resources.getString(R.string.system_apps),
                    ContextCompat.getDrawable(context, R.drawable.ic_solar_system)
                )
            )
            menutList.add(
                MenuItems(
                    context.resources.getString(R.string.about_us),
                    ContextCompat.getDrawable(context, R.drawable.ic_information)
                )
            )
            menutList.add(
                MenuItems(
                    context.resources.getString(R.string.share),
                    ContextCompat.getDrawable(context, R.drawable.ic_share)
                )
            )

            return menutList
        }
    }
}