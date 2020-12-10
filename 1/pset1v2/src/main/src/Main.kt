import java.io.File

fun main() {
    val stream = File("input.txt").readLines()
    var suma = 0
    var sumb = 0
    stream.forEach {
        suma += it.toInt() / 3 - 2
        sumb += add(it.toInt())
    }
    println(suma)
    println(sumb)
}

fun add(i: Int): Int {
    val next = i / 3 - 2
    return (if (next <= 0) 0 else next + add(next))
}