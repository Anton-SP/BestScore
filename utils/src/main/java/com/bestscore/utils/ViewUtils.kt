package com.bestscore.utils

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * Расширение фрагмента, создающее Toast
 */
fun Fragment.makeToast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}

/**
 * Расшинение View, создающее Snackbar. Есть возможность создания с действием и без, в
 * зависимости от коструктора. Удобнее всего применять на binding.root.makeSnackbar(...)
 */
fun View.makeSnackbar(
    text: String,
    actionText: String? = null,
    action: View.OnClickListener? = null
) {
    if (action != null && actionText != null) {
        Snackbar.make(this, text, Snackbar.LENGTH_LONG).setAction(actionText, action).show()
    } else {
        Snackbar.make(this, text, Snackbar.LENGTH_LONG).show()
    }
}