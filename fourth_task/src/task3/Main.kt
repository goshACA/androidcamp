package task3

typealias size = String
typealias all = Int

infix fun IntArray.add(elem: Int): IntArray {
    val res = this.copyOf(this.size + 1)
    res[this.size] = elem
    return res
}

infix fun IntArray.insert(elem: Int): IntArray {
    return this add elem
}

infix fun IntArray.at(pos: Int): IntArray {
    val res = IntArray(this.size)

    for (i in 0 until pos)
        res[i] = this[i]

    res[pos] = this[this.size - 1]

    for (i in pos + 1 until this.size)
        res[i] = this[i - 1]
    return res

}


infix fun IntArray.`remove at`(pos: Int): IntArray {
    val res = this.copyOf(this.size)
    res[pos] = 0
    return res
}

infix fun IntArray.print(range: IntRange) {
    for (i in range)
        print("${this[i]} ")
}

infix fun IntArray.get(size: String.Companion): String {
    return this.size.toString()
}

infix fun IntArray.print(all: Int.Companion) {
    this.forEach { print("$it ") }
}

fun main(args: Array<String>) {

    var arr = IntArray(0)
    arr = arr add 3 // create new array and add element at the end
    arr = arr add 7 add 1
    arr = arr add 9 add 6 add 8

    arr = arr insert 5 at 2 // insert 5 into position 2
    arr = arr `remove at` 3 // set to 0 at position 3
    arr = arr `remove at` 1
    arr print 2..5

    // optional
    println()
    println(arr get size)
    arr print all

}


