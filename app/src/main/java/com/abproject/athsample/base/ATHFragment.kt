package com.abproject.athsample.base

import android.content.Context
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.abproject.athsample.base.ATHInterface

/**
 * Created by Abolfazl on 5/13/21
 */
abstract class ATHFragment : Fragment(), ATHInterface {
    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout?
    override val viewContext: Context?
        get() = context

}