package com.bestscore.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.makeToast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}