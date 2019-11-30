import java.util.*


fun inputMatrix(scanner: Scanner): Matrix {
    val row1 = scanner.nextInt()
    val column1 = scanner.nextInt()
    val matrixA = Matrix(row1, column1)
    for (i in 0 until row1)
        for (j in 0 until column1)
            matrixA[i][j] = scanner.nextInt()
    return matrixA
}

fun main() {

    val scanner = Scanner(System.`in`)
    val matrixA = inputMatrix(scanner)
    println(matrixA)
    val matrixB = inputMatrix(scanner)
    println(matrixB)
    println()

    println(matrixA + 10)
    println()

    println(matrixA + matrixB)
    println()

    println(matrixB * 34)
    println()

    println(matrixA * matrixB)
    println()
}