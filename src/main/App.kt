package com.neelkamath.life

/**
 * Uses [seed] to create a [System] having the specified number of [columns].
 *
 * [seed] should contain `A` for [Cell.ALIVE], and `D` for [Cell.DEAD]. For example, if [columns] is `4`, and [seed] is
 * `ADDDADDDDAAD`, the [System] returned will have three rows, and four columns.
 */
private fun createSystem(seed: String, columns: Int) =
    seed.map { if (it == 'A') Cell.ALIVE else Cell.DEAD }.chunked(columns).map { it.toMutableList() }

/** Returns whether [system] has at least one [Cell.ALIVE]. */
private fun hasPopulation(system: System) = system.flatten().count { it == Cell.ALIVE } > 0

/**
 * Returns [system] as a human readable [String].
 *
 * The [String] will look like:
 * ```
 * 1 0 1
 * 1 1 0
 * 0 0 0
 * ```
 * Each row is separated by a new line, and each column a space. `1` denotes [Cell.ALIVE], and `0` [Cell.DEAD].
 */
private fun prettifySystem(system: System) = system
    .asSequence()
    .flatten()
    .map { if (it == Cell.ALIVE) 1 else 0 }
    .chunked(system[0].size)
    .map { it.joinToString(" ") }
    .joinToString("\n")

fun main() {
    println("Enter options without quotation marks.")
    print("Enter the number of ticks to perform (\"-1\" generates until the game ends): ")
    val ticks = readLine()!!.toInt()
    print("Enter the number of columns: ")
    val columns = readLine()!!.toInt()
    val life: Life
    loop@ while (true) {
        print("Enter \"s\" to specify the seed, or \"r\" to use a randomly generated seed: ")
        life = when (readLine()) {
            "s" -> {
                print("Use \"A\" for alive cells, and \"D\" for dead cells. ")
                print("Enter the seed (e.g., \"ADDDADDDDAAD\" is a grid with three rows and four columns): ")
                Life(createSystem(readLine()!!, columns))
            }
            "r" -> {
                print("Enter the number of rows: ")
                Life(readLine()!!.toInt(), columns)
            }
            else -> continue@loop
        }
        break
    }
    with(life) {
        println("Seed:\n${prettifySystem(system)}")
        if (ticks == -1) {
            var generation = 0
            while (true) {
                if (!hasPopulation(system)) {
                    println("The game is over.")
                    return
                }
                tick()
                println("Generation ${++generation}:\n${prettifySystem(system)}")
            }
        } else {
            repeat(ticks) {
                if (!hasPopulation(system)) {
                    println("The game is over.")
                    return
                }
                tick()
                println("Generation ${it + 1}:\n${prettifySystem(system)}")
            }
        }
    }
}
