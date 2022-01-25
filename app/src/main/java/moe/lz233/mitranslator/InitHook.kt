package moe.lz233.mitranslator

import android.app.Activity
import android.app.AndroidAppHelper
import android.os.Bundle
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage
import moe.lz233.mitranslator.module.HookSetting
import moe.lz233.mitranslator.util.LogUtil
import moe.lz233.mitranslator.util.ktx.hookAfterMethod

class InitHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName == PACKAGE_NAME) {
            try {
                Config.classLoader = lpparam.classLoader
                "com.cleargrass.app.babel.launcher.launch.LaunchActivity".hookAfterMethod(
                    "onCreate",
                    Bundle::class.java
                ) {
                    Config.context = AndroidAppHelper.currentApplication()
                    Config.activity = it.thisObject as Activity
                }
                init()
            } catch (e: Throwable) {
                LogUtil.e(e)
            }
        }
    }

    private fun init() {
        //设置
        HookSetting().init()
    }
}