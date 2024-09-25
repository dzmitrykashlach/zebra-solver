package org.homework.dto

import kotlinx.serialization.Serializable
import org.homework.dto.enums.*

@Serializable
class House(
    var color: Color? = null,
    var nationality: Nationality? = null,
    var drink: Drink? = null,
    var smoke: Smoke? = null,
    var pet: Pet? = null,
) {
    fun match(attribute: HouseAttribute): Boolean {
        return (attribute is ColorAttribute && color == attribute.value)
                || (attribute is NationalityAttribute && nationality == attribute.value)
                || (attribute is DrinkAttribute && drink == attribute.value)
                || (attribute is SmokeAttribute && smoke == attribute.value)
                || (attribute is PetAttribute && pet == attribute.value)
    }

    fun apply(attribute: HouseAttribute) = when (attribute.type) {
        HouseAttributeType.COLOR -> this.color = (attribute as ColorAttribute).value
        HouseAttributeType.DRINK -> this.drink = (attribute as DrinkAttribute).value
        HouseAttributeType.NATIONALITY -> this.nationality = (attribute as NationalityAttribute).value
        HouseAttributeType.PET -> this.pet = (attribute as PetAttribute).value
        HouseAttributeType.SMOKE -> this.smoke = (attribute as SmokeAttribute).value
        else -> throw Exception("ERROR: Cannot apply location attribute to house.")
    }
}
