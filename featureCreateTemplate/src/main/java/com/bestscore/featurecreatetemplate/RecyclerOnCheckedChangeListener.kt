package com.bestscore.featurecreatetemplate

import android.widget.CompoundButton.OnCheckedChangeListener

interface RecyclerOnCheckedChangeListener : OnCheckedChangeListener {
    var itemPosition: Int
}