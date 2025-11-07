package br.pucpr.appdev.dosecerta.base.util

import android.content.Context

interface StringProvider {
    fun getString(stringResourceId: Int): String
}

class AndroidStringProvider(private val context: Context): StringProvider {
    override fun getString(stringResourceId: Int): String {
        return context.getString(stringResourceId)
    }
}