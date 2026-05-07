package com.ElOuedUniv.maktaba.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: String = "",
    val title: String = "",
    val isbn: String = "",
    @SerialName("nb_pages") val nb_pages: Int = 0,
    @SerialName("image_url") val image_url: String? = null,
    @SerialName("is_finished") val is_finished: Boolean = false,
    @SerialName("category_id") val category_id: String? = null
)
