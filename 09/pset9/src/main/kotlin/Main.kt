import java.io.File

fun main() {

    val stream = File("input.txt").readLines()
    val rawInput = stream[0].split(",").toTypedArray()
    val prelimInput = rawInput.map { it.toLong() }.toLongArray()
    val input = LongArray(10000)
    for (i in 0..9999) input[i] = if (i < prelimInput.size) prelimInput[i] else  0

    println(solveA(input))
    println(solveB(input))
}

fun solveA(input : LongArray) : Long {
    val computer = IntCode(input)
    computer.addInput(1)
    computer.run()
    return computer.getOutput()
}

fun solveB(input : LongArray) : Long {
    val computer = IntCode(input)
    computer.addInput(2)
    computer.run()
    return computer.getOutput()
}