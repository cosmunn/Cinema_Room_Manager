package cinema

import java.util.*

fun main() {
    println("Enter the number of rows:")
    val rows = readLine()!!.toInt()

    println("Enter the number of seats in each row:")
    val seatsPerRow = readLine()!!.toInt()

    val numberOfSeats = rows * seatsPerRow

    val seatsArray = Array(rows) { CharArray(seatsPerRow) }
    for (i in seatsArray.indices) {
        for (j in 0 until seatsPerRow) {
            if (seatsArray[i][j] != 'B') {
                seatsArray[i][j] = 'S'
            }
        }
    }

    do {
        println("""
         
        1. Show the seats
        2. Buy a ticket
        3. Statistics
        0. Exit
        """.trimIndent())

        val menuInput = readLine()!!.toInt()

        when (menuInput) {
            0 -> return
            1 -> showSeats(seatsPerRow, seatsArray)
            2 -> buyTickets(rows, numberOfSeats, seatsArray)
            3 -> statistics(rows, seatsPerRow, numberOfSeats, seatsArray)
        }
    } while (menuInput in 1..3)
}

fun buyTickets(rows: Int, numberOfSeats: Int, seatsArray: Array<CharArray>) {
    println("\nEnter a row number:")
    val chooseRow = readLine()!!.toInt()

    println("Enter a seat number in that row:")
    val chooseSeat = readLine()!!.toInt()

    try {
        if (seatsArray[chooseRow - 1][chooseSeat - 1] == 'B') {
            println("\nThat ticket has already been purchased!")
            buyTickets(rows, numberOfSeats, seatsArray)
        } else
            seatsArray[chooseRow - 1][chooseSeat - 1] = 'B'
            if (numberOfSeats <= 60) {
                println("\nTicket price: $10")
            } else if (numberOfSeats > 60) {
                if (chooseRow <= rows / 2) {
                    println("\nTicket price: $10")
                } else if (chooseRow > rows / 2) {
                    println("\nTicket price: $8")
                }
            }
    } catch (e: IndexOutOfBoundsException) {
        println("\nWrong input!")
        buyTickets(rows, numberOfSeats, seatsArray)
    }
}

fun showSeats(seatsPerRow: Int, seatsArray: Array<CharArray>) {
    println("\nCinema:")
    print(" ")

    for (i in 1..seatsPerRow) {
        print(" $i")
    }
    println()

    for (i in seatsArray.indices) {
        println("${i + 1} ${seatsArray[i].joinToString(" ")}")
    }
}

fun statistics(rows: Int, seatsPerRow: Int, numberOfSeats: Int, seatsArray: Array<CharArray>) {
    val frontRows = rows / 2
    val backRows = rows - frontRows

    var seatsOccupied = 0
    var seatsTotalIncome = 0

    for (i in seatsArray.indices) {
        for (j in 0 until seatsPerRow) {
            if (seatsArray[i][j] == 'B') {
                ++seatsOccupied
                if (numberOfSeats <= 60) {
                    seatsTotalIncome += numberOfSeats * 10
                } else if (i + 1 <= frontRows) {
                    seatsTotalIncome += 10
                    println(i)
                } else if (i + 1 >= backRows) {
                    seatsTotalIncome += 8
                    println(i)
                }
            }
        }
    }

    var totalIncome = 0
    if (numberOfSeats <= 60) {
        totalIncome = numberOfSeats * 10
    } else if (numberOfSeats > 60) {
        totalIncome = (frontRows * 10 + backRows * 8) * seatsPerRow
    }

    println("""
     
    Number of purchased tickets: $seatsOccupied
    Percentage: ${"%.2f".format(Locale("en", "US"), (seatsOccupied.toDouble() / numberOfSeats.toDouble()) * 100.0)}%
    Current income: $$seatsTotalIncome
    Total income: $$totalIncome
    """.trimIndent())
}