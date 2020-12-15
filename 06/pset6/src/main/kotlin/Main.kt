import java.io.File

fun main() {
    val stream = File("input.txt").readLines().toTypedArray()
    val mapA = mutableMapOf<String, String>()
    val mapB = mutableMapOf<String, MutableList<String>>()

    // Initialise maps
    stream.forEach {
        val pattern = Regex("(\\w+)\\)(\\w+)")
        val matches = pattern.findAll(it)
        val orbited = matches.map{ it.groupValues[1] }.joinToString()
        val name = matches.map{ it.groupValues[2] }.joinToString()
        mapA[name] = orbited

        val nameList = mutableListOf<String>()
        nameList.add(orbited)
        mapB[name] = nameList
    }

    // Initialise second map to include both orbited and orbiting as neighbors for each planet
    mapA.forEach {
        if (mapB.contains(it.value)) {
            mapB[it.value]?.add(it.key)
        }
    }

    // Solve part A
    var countA = 0
    mapA.forEach {
        countA += dfsA(mapA, it.key)
    }
    println(countA)

    // Solve part B
    val source = mapA["YOU"]
    val destination = mapA["SAN"]

    val queue = mutableListOf<String?>()
    val visited = mutableListOf<String?>()

    val path = bfsB(queue, visited, mapB, source, destination, 0)
    print(path)

}

fun dfsA(adj_list: Map<String, String>, current: String?) : Int {

    var count = 0
    if (!adj_list.contains(current)) return count

    count++
    count += dfsA(adj_list, adj_list[current])
    return count

}

fun bfsB(queue: MutableList<String?>, visited: MutableList<String?>, adj_list: Map<String, MutableList<String>>, current: String?, destination: String?, depth: Int) : Int {

    var numberOfSteps = 0
    if (current == destination) return depth

    if (!visited.contains(current)) {
        visited.add(current)
        queue.add(current)

        while (queue.isNotEmpty()) {
            val currentNode = queue[0]
            queue.removeAt(0)
            adj_list[currentNode]?.forEach { i ->
                numberOfSteps = bfsB(queue, visited, adj_list, i, destination, depth + 1)
                if (numberOfSteps > 0) {
                    return numberOfSteps
                }
            }
        }
    }
    return numberOfSteps

}