package com.egnize.deviceinfo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowId
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.egnize.deviceinfo.adapter.MenuAdapter
import com.egnize.deviceinfo.data.MenuList
import com.egnize.deviceinfo.databinding.ActivityMainBinding
import com.egnize.deviceinfo.fragments.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_bottom_sheet.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var sheetBehavior: BottomSheetBehavior<View>
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
       binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
//        setSupportActionBar(toolbar)

//        binding.include.toolbarTv.setText("Hello")

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        initUI()
    }

    private fun initUI() {

        binding.include.mRecyclerView.layoutManager = GridLayoutManager(this, 4)
        val adapter = MenuAdapter(R.layout.row_menu_view, MenuList.getMenuList(this))
        adapter.openLoadAnimation()
        binding.include.mRecyclerView.adapter = adapter

        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
           val metadata = adapter.data[position]
            toggleBottomSheet()
            selectedButton(metadata.name)

        }


        sheetBehavior = BottomSheetBehavior.from(binding.include)
        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         */
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN ->
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })


    }

    private fun selectedButton(selectedValue:String) {
        when (selectedValue) {
            resources.getString(R.string.device) -> {
                replicasFragments(R.id.homeFragment)
                binding.include.toolbar.setBackgroundResource(R.color.colorPrimary)
            }
            resources.getString(R.string.mobile_info) -> {
                replicasFragments(R.id.deviceInfoFragment)
                binding.include.setBackgroundResource(R.color.colorPrimary)
            }
            resources.getString(R.string.os) -> {
                replicasFragments(R.id.OSFragment)
                binding.include.setBackgroundResource(R.color.dark_blue)
            }
            resources.getString(R.string.sensor) -> {
                replicasFragments(R.id.sensorCategoryFragment)
                binding.include.setBackgroundResource(R.color.dark_purple)
            }
            resources.getString(R.string.processor_label) -> {
                replicasFragments(R.id.CPUFragment)
                binding.include.setBackgroundResource(R.color.dark_violet)
            }
            resources.getString(R.string.battery) -> {
                replicasFragments(R.id.batteryFragment)
                binding.include.setBackgroundResource(R.color.dark_green)
            }
            resources.getString(R.string.network) -> {
                replicasFragments(R.id.networkFragment)
                binding.include.setBackgroundResource(R.color.dark_sky_blue)
            }
            resources.getString(R.string.sim) -> {
//                replicasFragments(R.id.)
                binding.include.setBackgroundResource(R.color.dark_parrot_green)
            }
            resources.getString(R.string.camera) -> {
//                replicasFragments(R.id.cameraFragment)
                binding.include.setBackgroundResource(R.color.dark_green_blue)
            }
            resources.getString(R.string.storage) -> {
//                replicasFragments(R.id.storageFragment)
                binding.include.setBackgroundResource(R.color.dark_red)
            }
            resources.getString(R.string.bluetooth) -> {
//                replicasFragments(R.id.blueToothFragment)
                binding.include.setBackgroundResource(R.color.dark_blue_one)
            }
            resources.getString(R.string.display) -> {
//                replicasFragments(R.id.displayFragment)
                binding.include.setBackgroundResource(R.color.dark_violet_one)
            }
            resources.getString(R.string.features) -> {
//                replicasFragments(R.id.phoneFeaturesFragment)
                binding.include.setBackgroundResource(R.color.dark_brown)
            }
            resources.getString(R.string.user_apps) -> {
//                replicasFragments(R.id.userAppsFragment)
                binding.include.setBackgroundResource(R.color.dark_parrot_green_blue)
            }
            resources.getString(R.string.system_apps) -> {
//                replicasFragments(R.id.systemAppsFragment)
                binding.include.setBackgroundResource(R.color.dark_parrot_green_blue)
            }
            resources.getString(R.string.about_us) -> {
//                replicasFragments(R.id.aboutUsFragment)
                binding.include.setBackgroundResource(R.color.colorPrimaryDark)
            }
            resources.getString(R.string.share) -> {
    //            Methods.sharing("https://play.google.com/store/apps/details?id=com.master.deviceinfo")
            }
        }


    }

    private fun replicasFragments(id: Int) {
        navController.popBackStack(id, true)
        navController.navigate(id)

    }

    private fun toggleBottomSheet() {
        if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED ){
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }else{
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
