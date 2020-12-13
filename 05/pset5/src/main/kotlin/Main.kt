import java.io.File
import kotlin.math.abs

fun main() {
    val stream = File("input.txt").readLines()
    val rawinput = stream[0].split(",").toTypedArray()
    val input = rawinput.map { it.toInt() }.toIntArray()
    var i = 0
    while (true) {
        val current = input[i]
        // println(i)
        when (current) {
            99 -> break
            3 -> input[input[i + 1]] = 1
            4 -> println(input[input[i + 1]])
            1 -> input[input[i + 3]] = input[input[i + 1]] + input[input[i + 2]]
            2 -> input[input[i + 3]] = input[input[i + 1]] * input[input[i + 2]]
        }
        if (current >= 100) {
            val op = current % 100
            val mode1 : Int = ((current / 100) % 10)
            val mode2 : Int  = ((current / 1000) % 10)
            val mode3 : Int  = ((current / 10000) % 10)
            val par1 = (if (mode1 == 1) input[i + 1] else input[abs(input[i + 1])])
            var par2 = 0
            var par3 = 0
            if (op == 1 || op == 2) {
                par2 = (if (mode2 == 1) input[i + 2] else input[abs(input[i + 2])])
                par3 = (if (mode3 == 1) i + 3 else abs(input[i + 3]))
            }
            when (op) {
                1 -> input[par3] = par1 + par2
                2 -> input[par3] = par1 * par2
                3 -> input[par1] = readLine()!!.toInt()
                4 -> println(input[input[i + 1]])
                else -> break
            }
        }

        i += if (input[i] == 3 || input[i] == 4) 2 else 4
    }
}