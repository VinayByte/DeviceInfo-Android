package com.egnize.deviceinfo.activity

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.egnize.deviceinfo.MainActivity
import com.egnize.deviceinfo.R
import com.egnize.deviceinfo.constants.AppConst
import com.egnize.deviceinfo.model.DeviceInfoModel

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            launchActivity()
        }, 2500)
    }

    private fun launchActivity() {
        getAppList()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getAppList() {
        AppConst.systemAppsList.clear()
        AppConst.userAppsList.clear()

        val flags = PackageManager.GET_META_DATA or PackageManager.GET_SHARED_LIBRARY_FILES
        val application = packageManager.getInstalledApplications(flags)

        application.forEach {
            val icon = packageManager.getApplicationIcon(it)

            if ((it.flags and ApplicationInfo.FLAG_SYSTEM) == 1){
                //System Application
                AppConst.systemAppsList.add(DeviceInfoModel(1, icon, packageManager.getApplicationLabel(it).toString(), it.packageName))
            }else{
                AppConst.userAppsList.add(DeviceInfoModel(2, icon, packageManager.getApplicationLabel(it).toString(), it.packageName))
            }
        }


    }
}
