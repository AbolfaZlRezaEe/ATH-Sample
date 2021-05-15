package com.abp.noties.base

import android.content.Context
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.abproject.athsample.base.ATHInterface

abstract class ATHFragment : Fragment(), ATHInterface {
    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout?
    override val viewContext: Context?
        get() = context

}