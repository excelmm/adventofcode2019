import java.io.File
import kotlin.math.abs


fun main() {
    val stream = File("input.txt").readLines()
    val rawinput = stream[0].split(",").toTypedArray()
    val input = rawinput.map { it.toInt() }.toIntArray()
    val input2 = rawinput.map { it.toInt() }.toIntArray()

    solveA(input)
    solveB(input2)
}

fun solveA(input: IntArray) {
    var i = 0
    while (true) {
        val current = input[i]
        // println(i)
        val op = current % 100
        val mode1: Int = ((current / 100) % 10)
        val mode2: Int = ((current / 1000) % 10)
        val mode3: Int = ((current / 10000) % 10)
        var par1 = 0
        var par2 = 0
        var par3 = 0
        try {
            par1 = (if (mode1 == 1) i + 1 else abs(input[i + 1]))
            par2 = (if (mode2 == 1) i + 2 else abs(input[i + 2]))
            par3 = (if (mode3 == 1) i + 3 else abs(input[i + 3]))
        } catch (e: Exception) {
            println("error")
        }
        when (op) {
            1 -> input[par3] = input[par1] + input[par2]
            2 -> input[par3] = input[par1] * input[par2]
            3 -> input[input[i + 1]] = 1
            4 -> println(input[par1])
            else -> break
        }

        i += if (op == 3 || op == 4) 2 else 4
    }
}

fun solveB (input: IntArray) {
    var i = 0
    while (true) {
        val current = input[i]
        val op = current % 100
        val mode1: Int = ((current / 100) % 10)
        val mode2: Int = ((current / 1000) % 10)
        val mode3: Int = ((current / 10000) % 10)
        var par1 = 0
        var par2 = 0
        var par3 = 0
        try {
            par1 = (if (mode1 == 1) input[i + 1] else input[abs(input[i + 1])])
            par2 = (if (mode2 == 1) input[i + 2] else input[abs(input[i + 2])])
            par3 = (if (mode3 == 1) i + 3 else abs(input[i + 3]))
        } catch (e : Exception) {}
        when (op) {
            1 -> input[par3] = par1 + par2
            2 -> input[par3] = par1 * par2
            3 -> input[input[i + 1]] = 5
            4 -> println(par1)
            5 -> i = (if (par1 != 0) par2 else i + 3)
            6 -> i = (if (par1 == 0) par2 else i + 3)
            7 -> input[par3] = (if (par1 < par2) 1 else 0)
            8 -> input[par3] = (if (par1 == par2) 1 else 0)
            else -> break
        }
        if (op == 5 || op == 6) {
            continue
        }
        i += if (op == 3 || op == 4) 2 else 4
    }
}