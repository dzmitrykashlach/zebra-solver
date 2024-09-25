package org.homework.dto

import kotlinx.serialization.Serializable
import org.homework.dto.enums.Direction
import org.homework.dto.enums.HouseAttributeType

@Serializable
data class Location(
    val direction: Direction,
    val houseAttribute: HouseAttributeType
)
