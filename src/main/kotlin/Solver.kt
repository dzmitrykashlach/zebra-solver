import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import org.homework.dto.Condition
import org.homework.dto.House
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
            .let { splitConditions(it) }

        processAbsLocations(conditions.first)
        processOtherConditions(conditions.second.plus(conditions.third))

        return Json.encodeToString(houses)
    }

    private fun processAbsLocations(absLocations: List<Condition>) {
        for (condition in absLocations) {
            when ((condition.attributes.second as LocationAttribute).direction) {
                Direction.FIRST -> houses[0] = mapAttribute(
                    attribute = condition.attributes.first
                )

                Direction.CENTRAL -> houses[houses.size / 2] = mapAttribute(
                    attribute = condition.attributes.first
                )

                else -> break
            }

        }
    }

    /* FIXME
    - process relative locations & other conditions
     */

    private fun processOtherConditions(otherConditions: List<Condition>) {
//        while (true){ FIXME - iterate through all permutations
        otherConditions.forEach {
            val condition = it
            houses.forEach {
                if (it.match(condition.attributes.first) && (condition.attributes.second.type != HouseAttributeType.LOCATION)) {
                    it.apply(condition.attributes.second)
                }
//            find house with tha same attribute type and value as in condition.first
//            apply condition.second
            }
        }

//        }
    }

    private fun mapAttribute(attribute: HouseAttribute): House = when (attribute.type) {
        HouseAttributeType.COLOR -> House(color = (attribute as ColorAttribute).value)
        HouseAttributeType.DRINK -> House(drink = (attribute as DrinkAttribute).value)
        HouseAttributeType.NATIONALITY -> House(nationality = (attribute as NationalityAttribute).value)
        HouseAttributeType.PET -> House(pet = (attribute as PetAttribute).value)
        HouseAttributeType.SMOKE -> House(smoke = (attribute as SmokeAttribute).value)
        HouseAttributeType.LOCATION -> House()
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


    private fun splitConditions(conditions: List<Condition>): Triple<List<Condition>, List<Condition>, List<Condition>> {
        val (locations, other) = conditions.partition {
            ((it.attributes.first.type == HouseAttributeType.LOCATION
                    || it.attributes.second.type == HouseAttributeType.LOCATION))

        }
        locations.apply { this.toMutableList() }
        other.apply { this.toMutableList() }
        val (absLocations, relLocations) = locations.partition {

            ((it.attributes.second.type == HouseAttributeType.LOCATION)
                    && (((it.attributes.second as LocationAttribute).direction == Direction.FIRST)
                    || ((it.attributes.second as LocationAttribute).direction == Direction.CENTRAL)))
        }
        return Triple(absLocations, relLocations, other)
    }


    private companion object {
        const val HOUSE_ATTRIBUTE = "houseAttribute"
        var CONDITIONS_PATH = System.getProperty("user.dir") + "/conditions.json"
    }
}