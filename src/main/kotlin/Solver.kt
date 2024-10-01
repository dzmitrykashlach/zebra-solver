import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import org.homework.dto.*
import org.homework.dto.enums.*
import java.io.File

/*
Challenge description: https://en.wikipedia.org/wiki/Zebra_Puzzle
 */
class Solver {
    lateinit var houses: Array<House>

    fun execute(numberOfHouses: Int): String {
        houses = Array(numberOfHouses) { House() }
        val conditions = loadConditions()

        val nationalityPermutations = nationalityPermutations()
        val colorPermutations = colorPermutations()
        val petPermutations = petPermutations()
        val drinkPermutations = drinkPermutations()
        val smokePermutations = smokePermutations()
        for (n in nationalityPermutations) {
            for (c in colorPermutations) {
                for (p in petPermutations) {
                    for (d in drinkPermutations) {
                        for (s in smokePermutations) {
                            if (checkAllConditions(conditions, n, c, p, d, s)) {
                                println(n)
                                println(c)
                                println(p)
                                println(d)
                                println(s)
//                                create list of houses from combination which match
                                for (i in 0..4) {
                                    houses[i] = House(c[i], n[i], d[i], s[i], p[i])
                                }
                                return Json.encodeToString(houses)
                            }
                        }
                    }
                }
            }
        }
        throw Exception("ERROR: no valid combination in permutations")
    }


    private fun loadConditions(): List<Condition> {
        val conditionsJson =
            File(CONDITIONS_PATH).readText(Charsets.UTF_8)
                .let { Json.parseToJsonElement(it).jsonObject["conditions"]!! }

        val conditions = mutableListOf<Condition>()
            .apply {
                val conditionsArray = conditionsJson.jsonArray
                for (jsonElement in conditionsArray) {
                    val attributes = Pair(
                        Json.decodeFromJsonElement<HouseAttribute>(jsonElement.jsonObject["first"]!!.jsonObject[HOUSE_ATTRIBUTE]!!),
                        Json.decodeFromJsonElement<HouseAttribute>(jsonElement.jsonObject["second"]!!.jsonObject[HOUSE_ATTRIBUTE]!!),
                    )
                    this.add(Condition(attributes))
                }
            }
        return conditions
    }


    private fun checkAllConditions(
        conditions: List<Condition>,
        nationalities: List<Nationality>,
        colors: List<Color>,
        pets: List<Pet>,
        drinks: List<Drink>,
        smokes: List<Smoke>
    ): Boolean {
        var conditionsMatch = true
        for (condition in conditions) {
            if (!checkSingleCondition(condition, nationalities, colors, pets, drinks, smokes)) {
                conditionsMatch = false
            }
        }
        return conditionsMatch
    }

    private fun checkSingleCondition(
        condition: Condition,
        nationalities: List<Nationality>,
        colors: List<Color>,
        pets: List<Pet>,
        drinks: List<Drink>,
        smokes: List<Smoke>
    ): Boolean {
        val firstIndex =
            when (condition.attributes.first.type) {
                HouseAttributeType.NATIONALITY -> nationalities.indexOf((condition.attributes.first as NationalityAttribute).value)
                HouseAttributeType.COLOR -> colors.indexOf((condition.attributes.first as ColorAttribute).value)
                HouseAttributeType.PET -> pets.indexOf((condition.attributes.first as PetAttribute).value)
                HouseAttributeType.DRINK -> drinks.indexOf((condition.attributes.first as DrinkAttribute).value)
                HouseAttributeType.SMOKE -> smokes.indexOf((condition.attributes.first as SmokeAttribute).value)
                HouseAttributeType.LOCATION -> throw Exception("Exception in input: put `location` to the second part of condition")
            }

        val secondIndex =
            when (condition.attributes.second.type) {
                HouseAttributeType.NATIONALITY -> nationalities.indexOf((condition.attributes.second as NationalityAttribute).value)
                HouseAttributeType.COLOR -> colors.indexOf((condition.attributes.second as ColorAttribute).value)
                HouseAttributeType.PET -> pets.indexOf((condition.attributes.second as PetAttribute).value)
                HouseAttributeType.DRINK -> drinks.indexOf((condition.attributes.second as DrinkAttribute).value)
                HouseAttributeType.SMOKE -> smokes.indexOf((condition.attributes.second as SmokeAttribute).value)
                HouseAttributeType.LOCATION -> {
                    if ((condition.attributes.second as LocationAttribute).direction != Direction.NEXT_TO) {
                        findIndexByLocation(
                            condition.attributes.second as LocationAttribute,
                            nationalities,
                            colors,
                            pets,
                            drinks,
                            smokes
                        )
                    } else {
                        findIndexByLocation(
                            condition.attributes.second as LocationAttribute,
                            nationalities,
                            colors,
                            pets,
                            drinks,
                            smokes
                        ).let {
                            if (it + 1 == firstIndex) {
                                it + 1
                            } else {
                                it - 1
                            }
                        }
                    }
                }
            }
        return firstIndex == secondIndex
    }


    private companion object {
        const val HOUSE_ATTRIBUTE = "houseAttribute"
        var CONDITIONS_PATH = System.getProperty("user.dir") + "/conditions.json"
    }
}