import kotlin.system.exitProcess

class Robot (private var x : Int, private var y : Int, computerInput : LongArray, val space: ArrayList<ArrayList<Panel>>, val part : String){

    private val computer = IntCode(computerInput)
    private val directions : Array<String> = arrayOf("up", "right", "down", "left")
    var dIndex = 0
    var currentPanel = space[y][x]

    init {
        if (part == "part A") currentPanel.color = "black" else "white"
    }

    private fun move(direction: String) {
        dIndex = if (direction == "left") dIndex - 1 else dIndex + 1
        if (dIndex < 0) dIndex += directions.size
        if (dIndex > 3) dIndex -= directions.size
        when (directions[dIndex]) {
            "up" -> x -= 1
            "right" -> y += 1
            "down" -> x += 1
            "left" -> y -= 1
        }
        currentPanel.painted = true
        currentPanel = space[x][y]
        // printSpace()
    }

    fun printSpace() {

        for (i in 0 until SIZEOFGRID) {
            for (j in 0 until SIZEOFGRID) {
                if (i == x && j == y) when (dIndex) {
                    0 -> print("^")
                    1 -> print(">")
                    2 -> print("v")
                    3 -> print("<")
                }
                else if (space[i][j].color == "black") print(".")
                else print("#")
            }
            println()
        }

        var count = 0
        for (i in space) {
            for (j in i) {
                if (j.painted) ++count
            }
        }
        print("Painted: ")
        println(count)
    }

    fun isDone() : Boolean {
        return computer.isDone()
    }

    fun runComputer() {
        val outputs = arrayListOf<Long>()
        val input = if (currentPanel.color == "black") 0L else 1L
        computer.addInput(input)
        computer.run()
        while (computer.hasOutput()) outputs.add(computer.getOutput())
        if (outputs.size != 2) exitProcess(2) // Confirm correct outputs
//        print(outputs[0])
//        println(outputs[1])
        currentPanel.color = if (outputs[0] == 0L) "black" else "white"
        if (outputs[1] == 0L) move("left") else move("right")
        outputs.clear()
    }

}