import java.io.File

fun main() {
    val stream = File("input.txt").readLines()
    val rawInput = stream[0].split(",").toTypedArray()
    val input = rawInput.map { it.toLong() }.toLongArray()

    println(solveA(input))
    println(solveB(input))
}

fun solveA (input : LongArray) : Long {

    val computers = arrayListOf<IntCode>()

    for (i in 1..5) {
        val newComputer = IntCode(input)
        computers.add(newComputer)
    }

    val configs = arrayListOf<String>()
    for (i in 1234..54321) {
        var istring = i.toString()
        if (istring.length == 4) istring = "0$istring"
        val count1 = "0".count{ istring.contains(it) }
        val count2 = "1".count{ istring.contains(it) }
        val count3 = "2".count{ istring.contains(it) }
        val count4 = "3".count{ istring.contains(it) }
        val count5 = "4".count{ istring.contains(it) }
        if (count1 == 1 && count2 == 1 && count3 == 1 && count4 == 1 && count5 == 1) configs.add(istring)
    }

    var maxOutput : Long = 0
    for (config in configs) {

        // Reset computers
        for (i in 0..4) {
            computers[i].reset()
            computers[i].addInput(config[i].toString().toLong())
        }

        // Run the computer
        var userInput : Long = 0
        for (i in 0..4) {
            computers[i].addInput(userInput)
            computers[i].run()
            userInput = computers[i].getOutput()
        }
        if (userInput > maxOutput) maxOutput = userInput
    }
    return maxOutput
}

fun solveB (input : LongArray) : Long {

    val computers = arrayListOf<IntCode>()

    for (i in 1..5) {
        val newComputer = IntCode(input)
        computers.add(newComputer)
    }

    val configs = arrayListOf<String>()
    for (i in 56789..98765) {
        var istring = i.toString()
        val count5 = "5".count{ istring.contains(it) }
        val count6 = "6".count{ istring.contains(it) }
        val count7 = "7".count{ istring.contains(it) }
        val count8 = "8".count{ istring.contains(it) }
        val count9 = "9".count{ istring.contains(it) }
        if (count5 == 1 && count6 == 1 && count7 == 1 && count8 == 1 && count9 == 1) configs.add(istring)
    }

    var maxOutput : Long = 0
    for (config in configs) {

        // Reset computers
        for (i in 0..4) {
            computers[i].reset()
            computers[i].addInput(config[i].toString().toLong())
        }

        var halted = false
        var userInput: Long = 0
        while (!halted) {
            for (i in 0..4) {
                computers[i].addInput(userInput)
                computers[i].run()
                if (computers[i].hasOutput()) userInput = computers[i].getOutput()
                if (computers[i].isDone()) {
                    halted = true
                    break
                }
            }
        }
        if (userInput > maxOutput) maxOutput = userInput
    }

    return maxOutput
}