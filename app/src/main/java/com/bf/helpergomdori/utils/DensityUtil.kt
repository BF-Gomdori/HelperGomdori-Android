package com.bf.helpergomdori.utils

import android.content.res.Resources
import android.util.DisplayMetrics

object DensityUtil {

    private lateinit var resources: Resources

    fun setResouces(contextResources: Resources){
        resources = contextResources
    }

    fun dp2px(dp: Float): Float {
        val metrics = resources.displayMetrics
        val px = dp * ((metrics.densityDpi)/DisplayMetrics.DENSITY_DEFAULT)
        return px
    }
}