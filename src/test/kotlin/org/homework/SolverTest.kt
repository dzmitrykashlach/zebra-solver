package org.homework

import Solver
import kotlin.test.Test
import kotlin.test.assertEquals

class SolverTest {

    @Test
    fun positive() {
        val solver = Solver()
        assertEquals(
            "[" +
                    "{\"color\":\"YELLOW\",\"nationality\":\"NORWEGIAN\",\"drink\":\"WATER\",\"smoke\":\"KOOLS\",\"pet\":\"FOX\"}," +
                    "{\"color\":\"BLUE\",\"nationality\":\"UKRAINIAN\",\"drink\":\"TEA\",\"smoke\":\"CHESTERFIELD\",\"pet\":\"HORSE\"}," +
                    "{\"color\":\"RED\",\"nationality\":\"ENGLISHMAN\",\"drink\":\"MILK\",\"smoke\":\"OLD_GOLD\",\"pet\":\"SNAILS\"}," +
                    "{\"color\":\"IVORY\",\"nationality\":\"SPANIARD\",\"drink\":\"ORANGE_JUICE\",\"smoke\":\"LUCKY_STRIKE\",\"pet\":\"DOG\"}," +
                    "{\"color\":\"GREEN\",\"nationality\":\"JAPANESE\",\"drink\":\"COFFEE\",\"smoke\":\"PARLIAMENT\",\"pet\":\"ZEBRA\"}" +
                    "]",
            solver.execute(5)
        )
    }


}