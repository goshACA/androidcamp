package task1

fun String.forEachWord(lambda: (String) -> Unit){
    val arr = this.split(" ")
    for(str in arr)
        lambda(str)
}

fun Int.bitIsOneAtPosition(pos: Int): Boolean{
    return this shr pos and 1 == 1
}


fun main(args: Array<String>) {
    // Create task1.forEachWord function
    "please print each word".forEachWord { word ->
        println(word)
    }

  // create task1.toColor function
    val c = (-0x775FB34F).toColor()
    println(c) // task1.Color(a=136, r=160, g=76, b=177)

    // create task1.bitIsOneAtPosition
    print(4.bitIsOneAtPosition(3))
}