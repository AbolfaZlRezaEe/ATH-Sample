package com.abproject.athsample.data.dataclass

import timber.log.Timber

object UserExisting {
    var isExisting: Boolean = false
        private set

    fun update(existing: Boolean) {
        this.isExisting = existing
    }
}