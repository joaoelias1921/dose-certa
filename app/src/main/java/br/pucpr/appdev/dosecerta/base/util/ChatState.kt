package br.pucpr.appdev.dosecerta.base.util

data class ChatState (
    val isEnteringToken: Boolean = true,
    val remoteToken: String = "",
    val messageText: String = ""
)