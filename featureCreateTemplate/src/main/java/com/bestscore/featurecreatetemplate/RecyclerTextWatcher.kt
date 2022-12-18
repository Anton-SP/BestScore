package com.bestscore.featurecreatetemplate

import android.text.TextWatcher

interface RecyclerTextWatcher: TextWatcher {
    var itemPosition: Int
}