package com.saad.invitation.models

data class SingleCardItemsModel(
    val background: String = "",
    val arrayOfMaps: List<Map<String, Any>> = emptyList(),
)