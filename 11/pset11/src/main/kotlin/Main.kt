import java.io.File
import kotlin.math.floor

const val SIZEOFGRID = 200

fun main() {

    val stream = File("input.txt").readLines()
    val rawInput = stream[0].split(",").toTypedArray()
    val computer = rawInput.map{ it.toLong() }.toLongArray()
    val middle = floor((SIZEOFGRID / 2).toDouble()).toInt()

    var spaceA = ArrayList<ArrayList<Panel>>()
    var spaceB = ArrayList<ArrayList<Panel>>()
    for (i in 0 until SIZEOFGRID) {
        val row = ArrayList<Panel>()
        for (j in 0 until SIZEOFGRID) {
            row.add(Panel())
        }
        spaceA.add(row)
        spaceB.add(row)
    }

    val bigComputer = LongArray(10000)
    for (i in bigComputer.indices) bigComputer[i] = if (i < computer.size) computer[i] else 0

    val robotA = Robot(middle, middle, bigComputer, spaceA, "partA")
    while (true) {
        robotA.runComputer()
        if (robotA.isDone()) break
    }

    val robotB = Robot(middle, middle, bigComputer, spaceB, "partB")
    while (true) {
        robotB.runComputer()
        if (robotB.isDone()) break
    }

    var countA = 0
    for (i in spaceA) {
        for (j in i) {
            if (j.painted) ++countA
        }
    }
    println(countA)

    var countB = 0
    for (i in spaceA) {
        for (j in i) {
            if (j.painted) ++countB
        }
    }
    robotB.printSpace()
    println(countB)

}

