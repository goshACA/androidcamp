import java.lang.Error
import java.lang.StringBuilder

class Matrix(val rowsCount: Int = 1, val columnCount: Int = 1) {
    private var matrix: ArrayList<ArrayList<Int>> = emptyMatrix()

    constructor(rowsCount: Int = 1, columnCount: Int = 1, matrix: ArrayList<ArrayList<Int>>) : this(
        rowsCount,
        columnCount
    ) {
        this.matrix = matrix
    }

    operator fun get(i: Int) = matrix[i]
    private fun emptyMatrix(): ArrayList<ArrayList<Int>> {
        val emptyArr = ArrayList<ArrayList<Int>>().apply { for (i in 0 until rowsCount) add(ArrayList(columnCount)) }
        for (i in 0 until rowsCount)
            emptyArr[i].apply { for (i in 0 until columnCount) add(0) }
        return emptyArr
    }
    override fun toString(): String {
        val res = StringBuilder()
        for (i in 0 until rowsCount) {
            for (j in 0 until columnCount) {
                res.append(" ${matrix[i][j]}")
            }
            res.append("\n")
        }
        return res.toString()
    }

    @Throws(IncompatibleSizesError::class)
    operator fun plus(other: Matrix): Matrix {
        throwIncompatibleSizesError(other)
        val result = Matrix(rowsCount, columnCount)
        for (i in 0 until rowsCount) {
            for (j in 0 until columnCount) {
                result[i][j] = this[i][j] + other[i][j]
            }
        }
        return result
    }

    operator fun plus(value: Int): Matrix {
        val result = Matrix(rowsCount, columnCount)
        for (i in 0 until rowsCount) {
            for (j in 0 until columnCount) {
                result[i][j] = this[i][j] + value
            }
        }
        return result
    }


    @Throws(IncompatibleSizesError::class)
    operator fun minus(other: Matrix): Matrix {
        throwIncompatibleSizesError(other)
        val result = Matrix(rowsCount, columnCount)
        for (i in 0 until rowsCount) {
            for (j in 0 until columnCount) {
                result[i][j] = this[i][j] - other[i][j]
            }
        }
        return result
    }

    operator fun minus(value: Int): Matrix = this + (-value)


    @Throws(IncompatibleSizesError::class)
    operator fun times(other: Matrix): Matrix {
        if (columnCount != other.rowsCount)
            throw IncompatibleSizesError()
        val result = Matrix(rowsCount, other.columnCount)
        for (i in 0 until rowsCount) {
            for (j in 0 until other.columnCount) {
                var sum = 0
                for (k in 0 until columnCount) {
                    sum += this.matrix[i][k] * other.matrix[k][j]
                }
                result[i][j] = sum
            }
        }
        return result
    }


    operator fun times(value: Int): Matrix {
        val result = Matrix(rowsCount, columnCount)
        for (i in 0 until rowsCount) {
            for (j in 0 until columnCount) {
                result[i][j] = this[i][j] * value
            }
        }
        return result
    }

    operator fun div(value: Int): Matrix {
        val result = Matrix(rowsCount, columnCount)
        for (i in 0 until rowsCount) {
            for (j in 0 until columnCount) {
                result[i][j] = this[i][j] / value
            }
        }
        return result
    }


    @Throws(IncompatibleSizesError::class)
    private fun throwIncompatibleSizesError(other: Matrix) {
        if (rowsCount != other.rowsCount || columnCount != other.columnCount)
            throw IncompatibleSizesError()
    }
}

class IncompatibleSizesError : Error("Sizes are not matching")