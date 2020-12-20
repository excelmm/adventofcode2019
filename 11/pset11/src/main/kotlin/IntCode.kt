import kotlin.system.exitProcess

class IntCode (computer: LongArray) {

    private var computerInUse = computer.copyOf()
    private val computerCopy = computer.copyOf()
    private val inputQueue = arrayListOf<Long>()
    private val outputQueue = arrayListOf<Long>()
    private var result : Long = 0
    private var index : Long = 0
    private var relativeBase : Long = 0
    private var done = false

    init {}

    fun addInput (input: Long) {
        inputQueue.add(input)
    }

    fun hasOutput() : Boolean {
        return outputQueue.size > 0
    }

    fun getOutput () : Long {
        return outputQueue.removeAt(0)
    }

    fun isDone() : Boolean {
        return done
    }

    fun reset () {
        computerInUse = computerCopy.copyOf()
        result = 0
        index = 0
        relativeBase = 0
        done = false
        inputQueue.clear()
    }

    private fun getParameter (position: Long, mode: Long) : Long {
        return computerInUse[getDestination(position, mode).toInt()]
    }

    private fun getDestination (position: Long, mode: Long) : Long {
        var destination: Long = 0
        when(mode.toInt()) {
            0 -> destination = computerInUse[position.toInt()]
            1 -> destination = position
            2 -> destination = computerInUse[position.toInt()] + relativeBase
        }
        return destination
    }

    fun run() {
        while (index < computerInUse.size) {
            val currentOpCode = computerInUse[index.toInt()]
            when (currentOpCode % 100) {
                1L -> {
                    val par1 : Long = getParameter(index + 1, (currentOpCode / 100) % 10)
                    val par2 : Long = getParameter(index + 2, (currentOpCode / 1000) % 10)
                    val par3 : Long = getDestination(index + 3, (currentOpCode / 10000) % 10)
                    computerInUse[par3.toInt()] = par1 + par2
                    index += 4
                }
                2L -> {
                    val par1 : Long = getParameter(index + 1, (currentOpCode / 100) % 10)
                    val par2 : Long = getParameter(index + 2, (currentOpCode / 1000) % 10)
                    val par3 : Long = getDestination(index + 3, (currentOpCode / 10000) % 10)
                    computerInUse[par3.toInt()] = par1 * par2
                    index += 4
                }
                3L -> {
                    if (inputQueue.size == 0) return
                    val input = inputQueue.removeAt(0)
                    val par1 : Long = getDestination(index + 1, (currentOpCode / 100) % 10)
                    computerInUse[par1.toInt()] = input
                    index += 2
                }
                4L -> {
                    val par1 : Long = getParameter(index + 1, (currentOpCode / 100) % 10)
                    outputQueue.add(par1)
                    index += 2
                }
                5L -> {
                    val par1 : Long = getParameter(index + 1, (currentOpCode / 100) % 10)
                    val par2 : Long = getParameter(index + 2, (currentOpCode / 1000) % 10)
                    index = if (par1.toInt() != 0) par2 else index + 3
                }
                6L -> {
                    val par1 : Long = getParameter(index + 1, (currentOpCode / 100) % 10)
                    val par2 : Long = getParameter(index + 2, (currentOpCode / 1000) % 10)
                    index = if (par1.toInt() == 0) par2 else index + 3
                }
                7L -> {
                    val par1 : Long = getParameter(index + 1, (currentOpCode / 100) % 10)
                    val par2 : Long = getParameter(index + 2, (currentOpCode / 1000) % 10)
                    val par3 : Long = getDestination(index + 3, (currentOpCode / 10000) % 10)
                    computerInUse[par3.toInt()] = if (par1 < par2) 1 else 0
                    index += 4
                }
                8L -> {
                    val par1 : Long = getParameter(index + 1, (currentOpCode / 100) % 10)
                    val par2 : Long = getParameter(index + 2, (currentOpCode / 1000) % 10)
                    val par3 : Long = getDestination(index + 3, (currentOpCode / 10000) % 10)
                    computerInUse[par3.toInt()] = if (par1 == par2) 1 else 0
                    index += 4
                }
                9L -> {
                    val par1 : Long = getParameter(index + 1, (currentOpCode / 100) % 10)
                    relativeBase += par1
                    index += 2
                }
                99L -> {
                    done = true
                    return
                }
                else -> exitProcess(1)
            }
        }
    }
}