package com.abp.noties.base

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.forEach
import com.abproject.athsample.base.ATHInterface
import java.lang.IllegalStateException

abstract class ATHActivity : AppCompatActivity(), ATHInterface {
    override val rootView: CoordinatorLayout?
        get() {
            val viewGroup = window.decorView.findViewById(android.R.id.content) as ViewGroup
            if (viewGroup !is CoordinatorLayout) {
                viewGroup.forEach {
                    if (it is CoordinatorLayout)
                        return it
                }
                throw IllegalStateException("rootView must be instance of CoordinatorLayout!")
            } else
                return viewGroup
        }
    override val viewContext: Context?
        get() = this
}