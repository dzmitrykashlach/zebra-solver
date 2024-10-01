package org.homework.dto

import com.github.michaelbull.itertools.permutations
import org.homework.dto.enums.*


fun findIndexByLocation(
    locationAttribute: LocationAttribute,
    nationalities: List<Nationality>,
    colors: List<Color>,
    pets: List<Pet>,
    drinks: List<Drink>,
    smokes: List<Smoke>
): Int {
    val index =
        when (locationAttribute.direction) {
            Direction.FIRST -> 0
            Direction.CENTRAL -> nationalities.size / 2
            Direction.NEXT_TO -> getAttributeFromLocation(
                locationAttribute,
                nationalities,
                colors,
                pets,
                drinks,
                smokes
            ).indexOf(mapAttribute(locationAttribute.houseAttribute!!))

            Direction.TO_THE_RIGHT -> getAttributeFromLocation(
                locationAttribute,
                nationalities,
                colors,
                pets,
                drinks,
                smokes
            ).indexOf(mapAttribute(locationAttribute.houseAttribute!!)) + 1

            null -> throw Exception("Exception in input: `location` should have a value from Direction enum")
        }
    return index
}

private fun mapAttribute(attribute: HouseAttribute): Any = when (attribute.type) {
    HouseAttributeType.COLOR -> Color.valueOf((attribute as ColorAttribute).value.toString())
    HouseAttributeType.DRINK -> Drink.valueOf((attribute as DrinkAttribute).value.toString())
    HouseAttributeType.NATIONALITY -> Nationality.valueOf((attribute as NationalityAttribute).value.toString())
    HouseAttributeType.PET -> Pet.valueOf((attribute as PetAttribute).value.toString())
    HouseAttributeType.SMOKE -> Smoke.valueOf((attribute as SmokeAttribute).value.toString())
    HouseAttributeType.LOCATION -> throw Exception("Exception in input: `location` cannot be mapped")
}

private fun getAttributeFromLocation(
    locationAttribute: LocationAttribute,
    nationalities: List<Nationality>,
    colors: List<Color>,
    pets: List<Pet>,
    drinks: List<Drink>,
    smokes: List<Smoke>
): List<Any> = when (locationAttribute.houseAttribute!!.type) {
    HouseAttributeType.NATIONALITY -> nationalities
    HouseAttributeType.COLOR -> colors
    HouseAttributeType.PET -> pets
    HouseAttributeType.DRINK -> drinks
    HouseAttributeType.SMOKE -> smokes
    HouseAttributeType.LOCATION -> throw Exception("Exception in input: `location` shouldn't have a nested location")
}

fun nationalityPermutations(): Sequence<List<Nationality>> {
    return listOf(
        Nationality.ENGLISHMAN,
        Nationality.SPANIARD,
        Nationality.NORWEGIAN,
        Nationality.JAPANESE,
        Nationality.UKRAINIAN
    ).permutations()
}

fun colorPermutations(): Sequence<List<Color>> {
    return listOf(Color.RED, Color.BLUE, Color.GREEN, Color.IVORY, Color.YELLOW)
        .permutations()
}

fun petPermutations(): Sequence<List<Pet>> {
    return listOf(Pet.DOG, Pet.FOX, Pet.HORSE, Pet.ZEBRA, Pet.SNAILS)
        .permutations()
}

fun drinkPermutations(): Sequence<List<Drink>> {
    return listOf(Drink.TEA, Drink.ORANGE_JUICE, Drink.WATER, Drink.MILK, Drink.COFFEE)
        .permutations()
}

fun smokePermutations(): Sequence<List<Smoke>> {
    return listOf(Smoke.KOOLS, Smoke.CHESTERFIELD, Smoke.LUCKY_STRIKE, Smoke.OLD_GOLD, Smoke.PARLIAMENT)
        .permutations()
}