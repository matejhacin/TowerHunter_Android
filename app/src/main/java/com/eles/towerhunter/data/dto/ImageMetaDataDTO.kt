package com.eles.towerhunter.data.dto

import com.google.gson.annotations.SerializedName

data class ImageMetaDataDTO(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("x") val x: Float,
    @SerializedName("y") val y: Float,
    @SerializedName("z") val z: Float
)
