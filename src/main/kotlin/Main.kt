fun main() {
    while (true) {
        println("Matrix Operations Menu:")
        println("1. Add matrices")
        println("2. Multiply matrix by a constant")
        println("3. Multiply matrices")
        println("4. Transpose matrix")
        println("5. Calculate a determinant")
        println("6. Inverse matrix")
        println("0. Exit")
        print("Your choice: ")

        when (readLine()!!.toInt()) {
            1 -> addMatrices()
            2 -> multiplyByConstant()
            3 -> multiplyMatrices()
            4 -> transposeMatrix()
            5 -> calculateDeterminant()
            6 -> inverseMatrix()
            0 -> {
                println("Exiting the program.")
                return
            }
            else -> println("Invalid choice. Please select a valid option.")
        }
    }
}

fun inverseMatrix() {
    println("Enter matrix size (rows columns): ")
    val (rows, columns) = readLine()!!.split(" ").map { it.toInt() }

    if (rows != columns) {
        println("The matrix must be square (rows = columns) to calculate the inverse.")
        return
    }

    val matrix = Array(rows) {
        println("Enter matrix row ${it + 1} values (separated by spaces):")
        readLine()!!.split(" ").map { it.toDouble() }.toTypedArray()
    }

    val determinant = calculateMatrixDeterminant(matrix)

    if (determinant == 0.0) {
        println("This matrix doesn't have an inverse.")
        return
    }

    val inverse = calculateMatrixInverse(matrix)

    println("The result is:")
    printMatrix(inverse)
}

fun calculateMatrixInverse(matrix: Array<Array<Double>>): Array<Array<Double>> {
    val det = calculateMatrixDeterminant(matrix)
    if (det == 0.0) {
        println("The matrix is singular and does not have an inverse.")
        return matrix
    }

    val adjugate = calculateAdjugateMatrix(matrix)
    val scalar = 1.0 / det

    return adjugate.map { row ->
        row.map { it * scalar }.toTypedArray()
    }.toTypedArray()
}

fun calculateAdjugateMatrix(matrix: Array<Array<Double>>): Array<Array<Double>> {
    val cofactors = Array(matrix.size) { i ->
        Array(matrix[0].size) { j ->
            val minor = getMatrixMinor(matrix, i, j)
            val cofactorSign = if ((i + j) % 2 == 0) 1.0 else -1.0
            calculateMatrixDeterminant(minor) * cofactorSign
        }
    }

    return cofactors.transpose()
}

fun Array<Array<Double>>.transpose(): Array<Array<Double>> {
    val rows = this.size
    val columns = this[0].size
    val transposed = Array(columns) { Array(rows) { 0.0 } }

    for (i in 0 until rows) {
        for (j in 0 until columns) {
            transposed[j][i] = this[i][j]
        }
    }

    return transposed
}


fun calculateDeterminant() {
    println("Enter matrix size (rows columns): ")
    val (rows, columns) = readLine()!!.split(" ").map { it.toInt() }

    if (rows != columns) {
        println("The matrix must be square (rows = columns) to calculate the determinant.")
        return
    }

    val matrix = Array(rows) {
        println("Enter matrix row ${it + 1} values (separated by spaces):")
        readLine()!!.split(" ").map { it.toDouble() }.toTypedArray()
    }

    val determinant = calculateMatrixDeterminant(matrix)

    println("The result is: \n$determinant")
}

fun calculateMatrixDeterminant(matrix: Array<Array<Double>>): Double {
    val size = matrix.size

    if (size == 1) {
        return matrix[0][0]
    }

    if (size == 2) {
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]
    }

    var determinant = 0.0

    for (i in 0 until size) {
        val minor = getMatrixMinor(matrix, 0, i)
        val cofactor = if (i % 2 == 0) 1.0 else -1.0
        determinant += matrix[0][i] * cofactor * calculateMatrixDeterminant(minor)
    }

    return determinant
}

fun getMatrixMinor(matrix: Array<Array<Double>>, row: Int, col: Int): Array<Array<Double>> {
    return matrix
        .filterIndexed { rowIndex, _ -> rowIndex != row }
        .map { it.filterIndexed { colIndex, _ -> colIndex != col }.toTypedArray() }
        .toTypedArray()
}

fun transposeMatrix() {
    println("Choose a type of transposition:")
    println("1. Main diagonal")
    println("2. Side diagonal")
    println("3. Vertical line")
    println("4. Horizontal line")
    print("Your choice: ")

    val transposeChoice = readLine()!!.toInt()

    println("Enter matrix size (rows columns): ")
    val (rows, _) = readLine()!!.split(" ").map { it.toInt() }

    val matrix = Array(rows) {
        println("Enter matrix row ${it + 1} values (separated by spaces):")
        readLine()!!.split(" ").map { it.toDouble() }.toTypedArray()
    }

    val resultMatrix = when (transposeChoice) {
        1 -> transposeMainDiagonal(matrix)
        2 -> transposeSideDiagonal(matrix)
        3 -> transposeVerticalLine(matrix)
        4 -> transposeHorizontalLine(matrix)
        else -> {
            println("Invalid choice. No transpose performed.")
            return
        }
    }

    println("The result is:")
    printMatrix(resultMatrix)
}

fun transposeMainDiagonal(matrix: Array<Array<Double>>): Array<Array<Double>> {
    val transposedMatrix = Array(matrix[0].size) { Array(matrix.size) { 0.0 } }

    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            transposedMatrix[j][i] = matrix[i][j]
        }
    }

    return transposedMatrix
}

fun transposeSideDiagonal(matrix: Array<Array<Double>>): Array<Array<Double>> {
    val transposedMatrix = Array(matrix[0].size) { Array(matrix.size) { 0.0 } }

    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            transposedMatrix[matrix[0].size - 1 - j][matrix.size - 1 - i] = matrix[i][j]
        }
    }

    return transposedMatrix
}

fun transposeVerticalLine(matrix: Array<Array<Double>>): Array<Array<Double>> {
    val transposedMatrix = Array(matrix.size) { Array(matrix[0].size) { 0.0 } }

    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            transposedMatrix[i][matrix[0].size - 1 - j] = matrix[i][j]
        }
    }

    return transposedMatrix
}

fun transposeHorizontalLine(matrix: Array<Array<Double>>): Array<Array<Double>> {
    val transposedMatrix = Array(matrix.size) { Array(matrix[0].size) { 0.0 } }

    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            transposedMatrix[matrix.size - 1 - i][j] = matrix[i][j]
        }
    }

    return transposedMatrix
}

fun readMatrix(): Array<Array<Double>> {
    print("Enter size of the matrix (rows columns): ")
    val (rows, _) = readLine()!!.split(" ").map { it.toInt() }

    val matrix = Array(rows) {
        println("Enter matrix row ${it + 1} values (separated by spaces):")
        readLine()!!.split(" ").map { it.toDouble() }.toTypedArray()
    }

    return matrix
}

fun addMatrices() {
    println("Enter size of the first matrix:")
    val matrixA = readMatrix()

    println("Enter size of the second matrix:")
    val matrixB = readMatrix()

    if (matrixA.size == matrixB.size && matrixA[0].size == matrixB[0].size) {
        val resultMatrix = Array(matrixA.size) { Array(matrixA[0].size) { 0.0 } }

        for (i in matrixA.indices) {
            for (j in matrixA[0].indices) {
                resultMatrix[i][j] = matrixA[i][j] + matrixB[i][j]
            }
        }

        println("The result is:")
        printMatrix(resultMatrix)
    } else {
        println("Matrix dimensions are not compatible for addition.")
    }
}

fun multiplyByConstant() {
    println("Enter the matrix:")
    val matrix = readMatrix()

    print("Enter the constant: ")
    val constant = readLine()!!.toDouble()

    val resultMatrix = Array(matrix.size) { Array(matrix[0].size) { 0.0 } }

    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            resultMatrix[i][j] = matrix[i][j] * constant
        }
    }

    println("The result is:")
    printMatrix(resultMatrix)
}

fun multiplyMatrices() {
    println("Enter size of the first matrix:")
    val matrixA = readMatrix()

    println("Enter size of the second matrix:")
    val matrixB = readMatrix()

    if (matrixA[0].size == matrixB.size) {
        val resultMatrix = Array(matrixA.size) { Array(matrixB[0].size) { 0.0 } }

        for (i in matrixA.indices) {
            for (j in matrixB[0].indices) {
                for (k in matrixB.indices) {
                    resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j]
                }
            }
        }

        println("The result is:")
        printMatrix(resultMatrix)
    } else {
        println("Matrix dimensions are not compatible for multiplication.")
    }
}

fun printMatrix(matrix: Array<Array<Double>>) {
    for (row in matrix) {
        println(row.joinToString(" "))
    }
}
