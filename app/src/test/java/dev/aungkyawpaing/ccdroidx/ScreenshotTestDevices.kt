package dev.aungkyawpaing.ccdroidx

import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers

val testDevices = listOf(
  arrayOf("Small_Phone", RobolectricDeviceQualifiers.SmallPhone),
  arrayOf("Medium_Phone", RobolectricDeviceQualifiers.MediumPhone),
  arrayOf("Medium_Tablet", RobolectricDeviceQualifiers.MediumTablet)
)