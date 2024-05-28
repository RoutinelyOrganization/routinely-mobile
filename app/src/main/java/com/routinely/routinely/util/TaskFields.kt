package com.routinely.routinely.util

import android.os.Parcelable

sealed class TaskFields(var stringId: Int, val apiString: String) : Parcelable {
    companion object {
        /**
         * Return all stringId from subclass
         */
        inline fun <reified T : TaskFields> allStringIds(): List<Int> {
            return T::class.sealedSubclasses.map { it.objectInstance!!.stringId }
        }

        /**
         * Return the stringId by api string response
         */
        inline fun <reified T : TaskFields> getStringIdByApiString(apiString: String): Int? {
            return T::class.sealedSubclasses
                .mapNotNull { it.objectInstance }
                .find { it.apiString == apiString }?.stringId
        }

        /**
         * Get task field by stringId
         */
        inline fun <reified T : TaskFields> getTaskFieldByStringId(stringId: Int): T {
            return T::class.sealedSubclasses
                .mapNotNull { it.objectInstance }
                .find { it.stringId == stringId }!!
        }

        /**
         * Get all options of T : TaskFields
         */
        inline fun <reified T : TaskFields> getAllOptions(): List<T> {
            return T::class.sealedSubclasses
                .mapNotNull { it.objectInstance }
        }
    }
}