import java.util.*

// Range information
const val RANGE_START = 1
const val RANGE_END = 100
// Levels
const val LEVEL_EASY = 1
const val LEVEL_MEDIUM = 2
const val LEVEL_HARD = 3
// Step count
const val STEP_COUNT_EASY = 7
const val STEP_COUNT_MEDIUM = 5
const val STEP_COUNT_HARD = 3
const val INPUT_ERROR = -1

fun main(arguments: Array<String>) {

    val randomNumber = Random().nextInt(RANGE_END - RANGE_START + 1) + RANGE_START

    println("Please select a difficulty level: 1 - Easy, 2 - Medium, 3 - Hard")
    var stepCount = INPUT_ERROR
    while (stepCount != STEP_COUNT_EASY && stepCount != STEP_COUNT_MEDIUM && stepCount != STEP_COUNT_HARD) {
        try {
            stepCount = handleInput(readLine())
        } catch (e: Exception) {
            println(e.message)
        }
    }


    val constStepCount = stepCount
    var gameIsStarted = false

    while (true) {
        if (!gameIsStarted) {
            println(
                    """
                Please enter a number between $RANGE_START and $RANGE_END.
                You have $stepCount steps
                """.trimIndent())
            gameIsStarted = true
        }
        var answerNumber: Int
        try {
            answerNumber = stringToInt(readLine())
        } catch (e: Exception) {
            println(e.message)
            continue
        }
        --stepCount
        if (gameJudge(score = stepCount, answerNumber = answerNumber, randomNumber = randomNumber) == 0) break
        if (stepCount <= 0) {
            stepCount = constStepCount
            gameIsStarted = false
            println("You died. Restart")
        }

    }
}


fun handleLevelInput(input: Int): Int {
    return when (input) {
        LEVEL_EASY -> STEP_COUNT_EASY
        LEVEL_MEDIUM -> STEP_COUNT_MEDIUM
        LEVEL_HARD -> STEP_COUNT_HARD
        else -> INPUT_ERROR
    }

}

@Throws(Exception::class)
fun handleInput(input: String?): Int {
    val levelInput = handleLevelInput(stringToInt(input))
    if (levelInput == INPUT_ERROR)
        throw Exception("Please enter 1, 2 or 3")
    else return levelInput

}

@Throws(Exception::class)
fun stringToInt(input: String?): Int {
    return if (input != null) {
        try {
            input.toInt()
        } catch (e: NumberFormatException) {
            throw Exception("Please enter a number")
        }
    } else throw Exception("Please enter a number")
}

fun gameJudge(score: Int, answerNumber: Int, randomNumber: Int): Int {
    return when {
        (answerNumber - randomNumber) > 0 -> {
            if (score > 0)
                println("take lower")
            1
        }
        (answerNumber - randomNumber) < 0 -> {
            if (score > 0)
                println("take higher")
            -1
        }
        else -> {
            println("YOU WON!!!!!")
            0
        }
    }
}



