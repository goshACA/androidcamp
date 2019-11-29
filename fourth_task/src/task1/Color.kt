package task1

class Color(val a: Int = 1, val r: Int = 0, val g: Int = 0, val b: Int = 0) {
    override fun toString(): String {
        return "task1.Color(a=$a, r=$r, g=$g, b=$b)"
    }
}

fun Int.toColor(): Color {
    val byte = 255
    return Color(
        a=this shr 24 and byte,
        r=this shr 16 and byte,
        g=this shr 8 and byte,
        b=this and byte
    )
}