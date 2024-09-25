package org.homework.dto

import kotlinx.serialization.Serializable
import org.homework.dto.enums.HouseAttribute

@Serializable
data class Condition(val attributes: Pair<HouseAttribute, HouseAttribute>)
