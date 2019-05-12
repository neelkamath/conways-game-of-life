package com.neelkamath.life.test

import com.neelkamath.life.Cell
import com.neelkamath.life.Life
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class LifeTest {
    @Test
    fun `Creating a Life with an invalid number of columns should throw Exception`() {
        assertFails { Life(3, 0) }
    }

    @Test
    fun `Creating a Life with an empty system should throw Exception`() {
        assertFails { Life(List(3) { MutableList(0) { Cell.ALIVE } }) }
    }

    @Test
    fun `A tick should return the next generation of the system`() = with(
        Life(
            listOf(
                mutableListOf(Cell.ALIVE, Cell.DEAD, Cell.DEAD),
                mutableListOf(Cell.DEAD, Cell.ALIVE, Cell.ALIVE),
                mutableListOf(Cell.DEAD, Cell.DEAD, Cell.DEAD)
            )
        )
    ) {
        tick()
        assertEquals(
            listOf(
                mutableListOf(Cell.DEAD, Cell.ALIVE, Cell.DEAD),
                mutableListOf(Cell.DEAD, Cell.ALIVE, Cell.DEAD),
                mutableListOf(Cell.DEAD, Cell.DEAD, Cell.DEAD)
            ), system
        )
        tick()
        assertEquals(
            listOf(
                mutableListOf(Cell.DEAD, Cell.DEAD, Cell.DEAD),
                mutableListOf(Cell.DEAD, Cell.DEAD, Cell.DEAD),
                mutableListOf(Cell.DEAD, Cell.DEAD, Cell.DEAD)
            ), system
        )
    }
}