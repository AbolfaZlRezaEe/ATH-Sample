package com.abproject.athsample.data.dataclass

object UserExisting {
    var isExisting: Boolean? = null
        private set

    fun update(existing: Boolean?) {
        this.isExisting = existing
    }
}