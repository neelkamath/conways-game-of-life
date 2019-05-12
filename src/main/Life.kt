package com.neelkamath.life

enum class Cell { ALIVE, DEAD }

typealias System = List<MutableList<Cell>>

/** @constructor Creates a random seed for [Life] of size [rows] and [columns]. */
class Life(private val rows: Int, private val columns: Int) {
    var system = List(rows) { MutableList(columns) { Cell.values().random() } }
        private set

    init {
        if (rows == 0 || columns == 0) throw Exception("System must have at least one row and column")
    }

    constructor(seed: System) : this(seed.size, seed[0].size) {
        system = seed
    }

    private fun northWestIsAlive(row: Int, column: Int) =
        if (row == 0 || column == 0) false else system[row - 1][column - 1] == Cell.ALIVE

    private fun northIsAlive(row: Int, column: Int) = if (row == 0) false else system[row - 1][column] == Cell.ALIVE

    private fun northEastIsAlive(row: Int, column: Int) =
        if (row == 0 || column == columns - 1) false else system[row - 1][column + 1] == Cell.ALIVE

    private fun westIsAlive(row: Int, column: Int) = if (column == 0) false else system[row][column - 1] == Cell.ALIVE

    private fun eastIsAlive(row: Int, column: Int) =
        if (column == columns - 1) false else system[row][column + 1] == Cell.ALIVE

    private fun southWestIsAlive(row: Int, column: Int) =
        if (row == rows - 1 || column == 0) false else system[row + 1][column - 1] == Cell.ALIVE

    private fun southIsAlive(row: Int, column: Int) =
        if (row == rows - 1) false else system[row + 1][column] == Cell.ALIVE

    private fun southEastIsAlive(row: Int, column: Int) =
        if (row == rows - 1 || column == columns - 1) false else system[row + 1][column + 1] == Cell.ALIVE

    private fun countNeighbors(row: Int, column: Int) = listOf(
        ::northWestIsAlive, ::northIsAlive, ::northEastIsAlive,
        ::westIsAlive, ::eastIsAlive,
        ::southWestIsAlive, ::southIsAlive, ::southEastIsAlive
    ).filter { it(row, column) }.count()

    /** Sets [system] to its next generation. */
    fun tick() {
        val newSystem = List(rows) { row -> MutableList(columns) { column -> system[row][column] } }
        for ((rowIndex, row) in system.withIndex()) {
            for ((columnIndex, column) in row.withIndex()) {
                val neighbors = countNeighbors(rowIndex, columnIndex)
                newSystem[rowIndex][columnIndex] = when {
                    column == Cell.ALIVE && neighbors < 2 -> Cell.DEAD
                    column == Cell.ALIVE && neighbors in 2..3 -> Cell.ALIVE
                    column == Cell.ALIVE && neighbors > 3 -> Cell.DEAD
                    column == Cell.DEAD && neighbors == 3 -> Cell.ALIVE
                    else -> system[rowIndex][columnIndex]
                }
            }
        }
        system = newSystem
    }
}
