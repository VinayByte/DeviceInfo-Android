package com.egnize.deviceinfo.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.telephony.gsm.GsmCellLocation
import android.view.*
import android.view.View.GONE
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.egnize.deviceinfo.R
import com.egnize.deviceinfo.activity.HomeActivity
import com.egnize.deviceinfo.adapters.SimListAdapter
import com.egnize.deviceinfo.models.SimInfoModel
import com.egnize.deviceinfo.utils.KeyUtil
import kotlinx.android.synthetic.main.fragment_sim.*
import kotlinx.android.synthetic.main.toolbar_ui.*


/**
 * A simple [SimFragment] subclass.
 */
class SimFragment : BaseFragment() {

    private var telephonyManager: TelephonyManager? = null
    private var simInfoModelDataList: ArrayList<SimInfoModel>? = ArrayList<SimInfoModel>()

   /* override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_sim, container, false)*/


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.SimTheme)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        val view = localInflater.inflate(R.layout.fragment_sim, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity!!.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.dark_parrot_green)
            window.navigationBarColor = resources.getColor(R.color.dark_parrot_green)

        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        telephonyManager = mActivity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?

        rvSimData?.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(mActivity)
        rvSimData?.hasFixedSize()
        initToolbar()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkCameraPermission()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isAdded) {
            initToolbar()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    /**
     * this method will show permission pop up messages to user.
     */
    private fun checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val hasWriteCameraPermission = mActivity.checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
            if (hasWriteCameraPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), KeyUtil.KEY_READ_PHONE_STATE)
            } else {
                retrieveSimInformation(telephonyManager!!)
            }
        } else {
            retrieveSimInformation(telephonyManager!!)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            KeyUtil.KEY_READ_PHONE_STATE -> if (permissions.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    retrieveSimInformation(telephonyManager!!)
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.GET_ACCOUNTS)) {
                        Toast.makeText(mActivity, "Need to grant account Permission", Toast.LENGTH_LONG).show()
                    }
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission", "HardwareIds")

    private fun retrieveSimInformation(telephonyManager: TelephonyManager) {
        if (telephonyManager != null && isSimAvailable(mActivity, 0) && telephonyManager.simState == TelephonyManager.SIM_STATE_READY) {

            cvSimDataParent.visibility = View.VISIBLE
            if (llEmptyState.isShown) {
                llEmptyState.visibility = GONE
            }

            simInfoModelDataList?.add(SimInfoModel("SIM 1 state", simState(telephonyManager.simState)))
            simInfoModelDataList?.add(SimInfoModel("Integrated circuit card identifier (ICCID)", telephonyManager.simSerialNumber!!))
            simInfoModelDataList?.add(SimInfoModel("Unique device ID (IMEI or MEID/ESN for CDMA)", telephonyManager.getImei(0)))
            simInfoModelDataList?.add(SimInfoModel("International mobile subscriber identity (IMSI)", telephonyManager.subscriberId))
            simInfoModelDataList?.add(SimInfoModel("Service provider name (SPN)", telephonyManager.simOperatorName))
            simInfoModelDataList?.add(SimInfoModel("Mobile country code (MCC)", telephonyManager.networkCountryIso))
            simInfoModelDataList?.add(SimInfoModel("Mobile operator name", telephonyManager.networkOperatorName))
            simInfoModelDataList?.add(SimInfoModel("Network type", networkType(telephonyManager.networkType)))

            simInfoModelDataList?.add(SimInfoModel("Mobile country code + mobile network code (MCC+MNC)", telephonyManager.simOperator))
            simInfoModelDataList?.add(SimInfoModel("Mobile station international subscriber directory number (MSISDN)", telephonyManager.line1Number))

//            (isSimAvailable(mActivity, 1)) {
//                    simInfoModelDataList?.add(SimInfoModel("", ""))
//                        simInfoModelDataList?.add(SimInfoModel("SIM 2 state", simState(getDeviceIdBySlot(mActivity, "getSimState", 1).toInt())))
//                        simInfoModelDataList?.add(SimInfoModel("Unique device ID (IMEI or MEID/ESN for CDMA)", telephonyManager.getImei(1)))
//                        simInfoModelDataList?.add(SimInfoModel("Integrated circuit card identifier (ICCID)", getDeviceIdBySlot(mActivity, "getSimSerialNumber", 1)))
//                        simInfoModelDataList?.add(SimInfoModel("International mobile subscriber identity (IMSI)", ""+getDeviceIdBySlot(mActivity, "getSubscriberId", 1)))
//                        simInfoModelDataList?.add(SimInfoModel("Service provider name (SPN)", getDeviceIdBySlot(mActivity, "getSimOperatorName", 1)))
//                        simInfoModelDataList?.add(SimInfoModel("Mobile country code (MCC)", getDeviceIdBySlot(mActivity, "getNetworkCountryIso", 1)))
//                        simInfoModelDataList?.add(SimInfoModel("Mobile operator name", ""+getDeviceIdBySlot(mActivity, "getNetworkOperatorName", 1)))
//                        simInfoModelDataList?.add(SimInfoModel("Network type", networkType(getDeviceIdBySlot(mActivity, "getNetworkType", 1).toInt())))
//                        simInfoModelDataList?.add(SimInfoModel("Mobile country code + mobile network code (MCC+MNC)", ""+getDeviceIdBySlot(mActivity, "getSimOperator", 1)))
//                        simInfoModelDataList?.add(SimInfoModel("Mobile station international subscriber directory number (MSISDN)", ""+getDeviceIdBySlot(mActivity, "getLine1Number", 1)))
//                }

//            //creating our adapter
            val adapter = simInfoModelDataList?.let { SimListAdapter(mActivity, it) }
            rvSimData?.adapter = adapter
        } else {
            cvSimDataParent.visibility = GONE
            llEmptyState.visibility = View.VISIBLE
        }
    }

    private fun initToolbar() {
        mActivity.iv_menu?.visibility = View.VISIBLE
        mActivity.iv_back?.visibility = GONE
        mActivity.tv_title?.text = getString(R.string.sim)
        mActivity.iv_menu?.setOnClickListener {

        }
    }

    @Throws(DIMethodNotFoundException::class)
    private fun getDeviceIdBySlot(context: HomeActivity, predictedMethodName: String, slotID: Int): String {
        var imei: String? = null
        val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        try {

            val telephonyClass = Class.forName(telephony.javaClass.name)

            val parameter = arrayOfNulls<Class<*>>(1)
            parameter[0] = Int::class.javaPrimitiveType
            val getSimID = telephonyClass.getMethod(predictedMethodName, *parameter)

            val obParameter = arrayOfNulls<Any>(1)
            obParameter[0] = slotID
            val ob_phone = getSimID.invoke(telephony, *obParameter)

            return ob_phone?.toString() ?: "No record"

        } catch (e: Exception) {
            e.printStackTrace()
            throw DIMethodNotFoundException(predictedMethodName)
        }
    }


    @Throws(DIMethodNotFoundException::class)
    private fun getCellLocBySlot(context: Context, predictedMethodName: String, slotID: Int): GsmCellLocation? {

        var cloc: GsmCellLocation? = null
        val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        try {
            val telephonyClass = Class.forName(telephony.javaClass.name)
            val parameter = arrayOfNulls<Class<*>>(1)
            parameter[0] = Int::class.javaPrimitiveType
            val getSimID = telephonyClass.getMethod(predictedMethodName, *parameter)

            val obParameter = arrayOfNulls<Any>(1)
            obParameter[0] = slotID
            val obPhone = getSimID.invoke(telephony, *obParameter)

            if (obPhone != null) {
                cloc = obPhone as GsmCellLocation

            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw DIMethodNotFoundException(predictedMethodName)
        }

        return cloc
    }

    private fun simState(simState: Int): String {
        return when (simState) {
            0 -> "UNKNOWN"
            1 -> "ABSENT"
            2 -> "REQUIRED"
            3 -> "PUK_REQUIRED"
            4 -> "NETWORK_LOCKED"
            5 -> "READY"
            6 -> "NOT_READY"
            7 -> "PERM_DISABLED"
            8 -> "CARD_IO_ERROR"
            else -> "??? " + simState
        }
    }

    private fun networkType(simState: Int): String {
        return when (simState) {
            TelephonyManager.NETWORK_TYPE_GPRS -> "GPRS"
            TelephonyManager.NETWORK_TYPE_EDGE -> "EDGE"
            TelephonyManager.NETWORK_TYPE_CDMA -> "CDMA"
            TelephonyManager.NETWORK_TYPE_1xRTT -> "1xRTT"
            TelephonyManager.NETWORK_TYPE_IDEN -> "IDEN"

            TelephonyManager.NETWORK_TYPE_UMTS -> "UMTS"
            TelephonyManager.NETWORK_TYPE_EVDO_0 -> "EVDO 0"
            TelephonyManager.NETWORK_TYPE_EVDO_A -> "EVDO A"
            TelephonyManager.NETWORK_TYPE_HSDPA -> "HSDPA"
            TelephonyManager.NETWORK_TYPE_HSUPA -> "HSUPA"
            TelephonyManager.NETWORK_TYPE_HSPA -> "HSPA"
            TelephonyManager.NETWORK_TYPE_EVDO_B -> "EVDO B"
            TelephonyManager.NETWORK_TYPE_EHRPD -> "EHRPD"
            TelephonyManager.NETWORK_TYPE_HSPAP -> "HSPAP"

            TelephonyManager.NETWORK_TYPE_LTE -> "LTE"
            else -> "Unknown"
        }
    }

    private class DIMethodNotFoundException(info: String) : Exception(info) {
        companion object {
            private val serialVersionUID = -996812356902545308L
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private fun isSimAvailable(context: HomeActivity, slotId: Int): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val sManager = context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
            val infoSim = sManager.getActiveSubscriptionInfoForSimSlotIndex(slotId)
            if (infoSim != null) {
                return true
            }
        } else {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (telephonyManager.simSerialNumber != null) {
                return true
            }
        }
        return false
    }
}
