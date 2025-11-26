package com.example.satview

import kotlin.math.floor
import kotlin.math.pow

object TileCalculator {

    // Convert lat/lon to tile coordinates for EPSG:4326 projection
    fun getTileCoordinates(lat: Double, lon: Double, zoom: Int): Triple<Int, Int, Int> {
        val numTiles = 2.0.pow(zoom).toInt()

        // For EPSG:4326 (Geographic projection)
        // Longitude: -180 to 180 maps to 0 to numTiles*2
        // Latitude: -90 to 90 maps to 0 to numTiles

        val tileCol = floor((lon + 180.0) / 360.0 * numTiles * 2).toInt()
        val tileRow = floor((90.0 - lat) / 180.0 * numTiles).toInt()

        return Triple(zoom, tileRow, tileCol)
    }

    // Generate GIBS tile URL
    fun getGibsUrl(
        lat: Double,
        lon: Double,
        zoom: Int = 5,
        date: String = "2024-11-25", // Use recent date
        layer: String = "VIIRS_SNPP_CorrectedReflectance_TrueColor"
    ): String {
        val (tileMatrix, tileRow, tileCol) = getTileCoordinates(lat, lon, zoom)

        return "https://gibs.earthdata.nasa.gov/wmts/epsg4326/best/" +
                "$layer/default/$date/250m/$tileMatrix/$tileRow/$tileCol.jpg"
    }
}