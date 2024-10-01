package org.homework.dto.enums

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable
enum class HouseAttributeType{
    @SerialName("color")
    COLOR,

    @SerialName("drink")
    DRINK,

    @SerialName("nationality")
    NATIONALITY,

    @SerialName("pet")
    PET,

    @SerialName("smoke")
    SMOKE,

    @SerialName("location")
    LOCATION
}

@Serializable(with = HouseAttributeSerializer::class)
sealed class HouseAttribute {
    @SerialName("type")
    abstract val type: HouseAttributeType
}

@Serializable
data class NationalityAttribute(
    override val type: HouseAttributeType = HouseAttributeType.NATIONALITY,

    @SerialName("value")
    val value: Nationality? = null,
) : HouseAttribute()

@Serializable
data class ColorAttribute(
    override val type: HouseAttributeType = HouseAttributeType.COLOR,

    @SerialName("value")
    val value: Color? = null,
) : HouseAttribute()

@Serializable
data class DrinkAttribute(
    override val type: HouseAttributeType = HouseAttributeType.DRINK,

    @SerialName("value")
    val value: Drink? = null,
) : HouseAttribute()

@Serializable
data class PetAttribute(
    override val type: HouseAttributeType = HouseAttributeType.PET,

    @SerialName("value")
    val value: Pet? = null,
) : HouseAttribute()

@Serializable
data class SmokeAttribute(
    override val type: HouseAttributeType = HouseAttributeType.SMOKE,

    @SerialName("value")
    val value: Smoke? = null,
) : HouseAttribute()

@Serializable
data class LocationAttribute(
    override val type: HouseAttributeType = HouseAttributeType.LOCATION,

    @SerialName("direction")
    val direction: Direction? = null,

    @SerialName("houseAttribute")
    val houseAttribute: HouseAttribute? = null,

    ) : HouseAttribute()

enum class Color {
    RED, GREEN, IVORY, YELLOW, BLUE
}

enum class Drink {
    COFFEE, MILK, TEA, ORANGE_JUICE, WATER
}

enum class Nationality {
    ENGLISHMAN, SPANIARD, NORWEGIAN, JAPANESE, UKRAINIAN
}

enum class Pet {
    FOX, HORSE, SNAILS, DOG, ZEBRA
}

enum class Smoke {
    KOOLS, CHESTERFIELD, OLD_GOLD, LUCKY_STRIKE, PARLIAMENT
}

enum class Direction {
    FIRST, CENTRAL, TO_THE_RIGHT, NEXT_TO
}

object HouseAttributeSerializer:
JsonContentPolymorphicSerializer<HouseAttribute>(
HouseAttribute::class
) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<HouseAttribute> {
        return when (element.jsonObject["type"]?.jsonPrimitive?.content) {
            "color" -> ColorAttribute.serializer()
            "nationality" -> NationalityAttribute.serializer()
            "pet" -> PetAttribute.serializer()
            "drink" -> DrinkAttribute.serializer()
            "smoke" -> SmokeAttribute.serializer()
            "location" -> LocationAttribute.serializer()
            else -> throw Exception("ERROR: No Serializer found. Serialization failed.")
        }
    }
}

