package com.bestscore.core.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

const val NAV_BUNDLE_KEY = "nav_bundle_key"

/**
 * Расширение фрагмента, выполняющее переход навигации по переданному действию и опционально
 * позволяющее передать вперед данные
 */
fun Fragment.navigate(actionId: Int, data: Parcelable? = null) {
    val navController = findNavController()
    val bundle = Bundle().apply {
        putParcelable(NAV_BUNDLE_KEY, data)
    }
    navController.navigate(actionId, bundle)
}

/**
 * Расширение фрагмента, позволяющее получить переданные ему данные
 */
val Fragment.navigationData: Parcelable?
    get() = arguments?.getParcelable(NAV_BUNDLE_KEY)