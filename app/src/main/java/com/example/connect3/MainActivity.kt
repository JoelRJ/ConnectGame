package com.example.connect3

import android.animation.ObjectAnimator
import android.media.Image
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var screenHeight = 0f
    var isYellow = false
    // Yellow = 1, Red = 4
    var board = Array(3) {Array(3) {0} }

    fun drop(view : View) {
        val piece: ImageView = view as ImageView

        if (piece.tag != "yellow" && piece.tag != "red") {
            val tag = piece.tag as String
            val locations = tag.split(",")
            val intLocations = locations.map {it.toInt()}

            // If next piece is a yellow piece
            if (isYellow) {
                piece.setImageResource(R.drawable.yellow)
                piece.tag = "yellow"
                board[intLocations[0]][intLocations[1]] = 1
            }
            // If next piece is a red piece
            else {
                piece.setImageResource(R.drawable.red)
                piece.tag = "red"
                board[intLocations[0]][intLocations[1]] = 4
            }

            // Check for winner and animate piece
            piece.y = -screenHeight
            piece.animate().translationYBy(screenHeight).setDuration(500).start()
            checkBoard(board)
            isYellow = !isYellow
        }

    }

    // Check board logic to determine winner
    fun checkBoard(arrayIn : Array<Array<Int>>) {
        var sum = 0
//        arrayIn.forEach { array ->
//            array.forEach {
//                sum += it
//            }
//            if (sum == 3) {
//                winnerText.text = "Yellow"
//            }
//            else if (sum == 12) {
//                winnerText.text = "Red"3


//            }
//
//        }


        if (winnerText.text == "") {
            for (y in 0..2) {
                // Check rows
                for (x in arrayIn[y]) {
                    sum += x
                }

                if (sum == 3) {
                    winnerText.text = "Yellow"
                } else if (sum == 12) {
                    winnerText.text = "Red"
                }

                sum = 0

                // Check columns
                for (x in 0..2) {
                    sum += arrayIn[x][y]
                }

                if (sum == arrayIn[0].size) {
                    winnerText.text = "Yellow"
                } else if (sum == arrayIn[0].size * 4) {
                    winnerText.text = "Red"
                }
                sum = 0
            }

            // Check diagonally down
            for (x in 0..2) {
                sum += arrayIn[x][x]
            }
            if (sum == arrayIn[0].size) {
                winnerText.text = "Yellow"
            } else if (sum == arrayIn[0].size * 4) {
                winnerText.text = "Red"
            }
            sum = 0

            // Check diagonally up
            for (x in 0..2) {
                sum += arrayIn[x][2 - x]
            }
            if (sum == arrayIn[0].size) {
                winnerText.text = "Yellow"
            } else if (sum == arrayIn[0].size * 4) {
                winnerText.text = "Red"
            }
        }
    }

    // ResetBoard selects all images inside the layout "tableLayout" and removes them
    fun ResetBoard(view: View) {
        // Reset integer board, yellow is first color again, and winnertext is reset
        board = Array(3) {Array(3) {0} }
        isYellow = false
        winnerText.text = ""

        // Loop through all image elements on page and set image resource to blank, and tag to "empty"
        var layout = tableLayout

        for (rows in 0..(layout.childCount - 1)) {
            var row = layout.getChildAt(rows) as LinearLayout

            for (imageCount in 0..(row.childCount - 1)) {
                var image = row.getChildAt(imageCount) as ImageView
                image.setImageResource(android.R.color.transparent)
                val newTag = "$rows,$imageCount" as String
                image.setTag(newTag)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels.toFloat()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}