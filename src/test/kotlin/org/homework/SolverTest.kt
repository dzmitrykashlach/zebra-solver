package org.homework

import Solver
import kotlin.test.Test
import kotlin.test.assertEquals

class SolverTest {

    @Test
    fun positive() {
        val solver = Solver()

        assertEquals(
            "[{\"nationality\":\"NORWEGIAN\"},{},{\"drink\":\"MILK\"},{},{}]",
            solver.execute(5)
        )
    }


}