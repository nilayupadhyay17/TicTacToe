package com.upadhyay.nilay.tictactoedemo.presenter

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.upadhyay.nilay.tictactoedemo.R
import com.upadhyay.nilay.tictactoedemo.model.Winner
import com.upadhyay.nilay.tictactoedemo.util.AppUtils
import com.upadhyay.nilay.tictactoedemo.util.BlurView
import com.upadhyay.nilay.tictactoedemo.view.LevelSelectionActivity

/**
 * Created by nilay on 11/9/2017.
 */
class MainPresenter {

    companion object {
        private const val TAG: String = "MainPresenter"
        private const val DRAW: Int = 0
        private const val FIRST: Int = 1
        private const val SECOND: Int = 2
    }

    private var currentPlayer: Int = FIRST
    private var player1 = HashMap<Int, View>()
    private var player2 = HashMap<Int, View>()
    private val stats = Array(3) { 0 }
    private var moves: Int = 0

    /**
     * This function will add appropriate  value (X,O) into players stack and it will check for winning pattern.
     *
     * @param callback
     * @param view
     */
    fun imageButtonClick(view: View, callback: (Boolean) -> Unit) {
        moves++
        val drawable: Int
        when (currentPlayer) {
            FIRST -> {
                drawable = R.drawable.cell_x
                player1.put(getCell(view), view)
                currentPlayer = SECOND
            }
            SECOND -> {
                drawable = R.drawable.cell_o
                player2.put(getCell(view), view)
                currentPlayer = FIRST
            }
            else -> return
        }
        view.isEnabled = false
        (view as ImageView).setImageDrawable(ContextCompat.getDrawable(view.context, drawable))
        val winner: Winner? = checkWinner()
        when (winner?.player) {
            FIRST -> {
                stats[FIRST] = stats[FIRST] + 1
                Log.d("STATS",stats[FIRST].toString());
                callback.invoke(true)
                winner.winnerValues.forEach { it ->
                    (player1[it] as ImageView).setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.cell_x_win))
                }
            }
            SECOND -> {
                stats[SECOND] = stats[SECOND] + 1
                callback.invoke(true)
                winner.winnerValues.forEach { it ->
                    (player2[it] as ImageView).setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.cell_o_win))
                }
            }
            else -> {
                if (moves == 16) {
                    stats[DRAW] = stats[DRAW] + 1
                    callback.invoke(true)
                } else {
                    callback.invoke(false)
                }
            }
        }
    }
    /**
     *  It will check winning patterns in players stack.
     *
     *
     */
    private fun checkWinner(): Winner? {
        if (
        player1.contains(1) && player1.contains(2) && player1.contains(3) && player1.contains(4)) return Winner(FIRST, intArrayOf(1, 2, 3,4))
        else if (player1.contains(5) && player1.contains(6) && player1.contains(7)&& player1.contains(8)) return Winner(FIRST, intArrayOf(5,6,7,8))
        else if (player1.contains(9) && player1.contains(10) && player1.contains(11)&& player1.contains(12)) return Winner(FIRST, intArrayOf(9,10,11,12))
        else if (player1.contains(13) && player1.contains(14) && player1.contains(15)&& player1.contains(16)) return Winner(FIRST, intArrayOf(13,14,15,16))
        else if (player1.contains(1) && player1.contains(6) && player1.contains(11)&& player1.contains(16)) return Winner(FIRST, intArrayOf(1, 6, 11,16))
        else if (player1.contains(2) && player1.contains(6) && player1.contains(10)&& player1.contains(14)) return Winner(FIRST, intArrayOf(2, 6, 10,14))
        else if (player1.contains(3) && player1.contains(7) && player1.contains(11)&& player1.contains(15)) return Winner(FIRST, intArrayOf(3, 7, 11,15))
        else if (player1.contains(4) && player1.contains(8) && player1.contains(12)&& player1.contains(16)) return Winner(FIRST, intArrayOf(4, 8, 12,16))
        else if (player1.contains(1) && player1.contains(5) && player1.contains(9)&& player1.contains(13)) return Winner(FIRST, intArrayOf(1, 5, 9,13))
        else if (player1.contains(4) && player1.contains(7) && player1.contains(10)&& player1.contains(13)) return Winner(FIRST, intArrayOf(4, 7, 10,13))
        else if (player1.contains(1) && player1.contains(4) && player1.contains(13)&& player1.contains(16)) return Winner(FIRST, intArrayOf(1, 4, 13,16))
        else if (player1.contains(1) && player1.contains(2) && player1.contains(5)&& player1.contains(6)) return Winner(FIRST, intArrayOf(1, 2, 5,6))
        else if (player1.contains(2) && player1.contains(3) && player1.contains(6)&& player1.contains(7)) return Winner(FIRST, intArrayOf(2, 3, 6,7))
        else if (player1.contains(3) && player1.contains(4) && player1.contains(7)&& player1.contains(8)) return Winner(FIRST, intArrayOf(3, 4, 7,8))
        else if (player1.contains(5) && player1.contains(6) && player1.contains(9)&& player1.contains(10)) return Winner(FIRST, intArrayOf(5, 6, 9,10))
        else if (player1.contains(6) && player1.contains(7) && player1.contains(10)&& player1.contains(14)) return Winner(FIRST, intArrayOf(6, 7, 10,14))
        else if (player1.contains(7) && player1.contains(8) && player1.contains(11)&& player1.contains(12)) return Winner(FIRST, intArrayOf(7, 8, 11,12))
        else if (player1.contains(9) && player1.contains(10) && player1.contains(13)&& player1.contains(14)) return Winner(FIRST, intArrayOf(9, 10, 13,14))
        else if (player1.contains(10) && player1.contains(11) && player1.contains(14)&& player1.contains(15)) return Winner(FIRST, intArrayOf(10, 11, 14,15))
        else if (player1.contains(11) && player1.contains(12) && player1.contains(15)&& player1.contains(16)) return Winner(FIRST, intArrayOf(11, 12, 15,16))
        else if (player1.contains(6) && player1.contains(7) && player1.contains(10)&& player1.contains(11)) return Winner(FIRST, intArrayOf(6, 7, 10,11))
        else if (player2.contains(6) && player2.contains(7) && player2.contains(10)&& player2.contains(11)) return Winner(SECOND, intArrayOf(6, 7, 10,11))
        else if (player2.contains(1) && player2.contains(2) && player2.contains(5)&& player2.contains(6)) return Winner(SECOND, intArrayOf(1, 2, 5,6))
        else if (player2.contains(2) && player2.contains(3) && player2.contains(6)&& player2.contains(7)) return Winner(SECOND, intArrayOf(2, 3, 6,7))
        else if (player2.contains(3) && player2.contains(4) && player2.contains(7)&& player2.contains(8)) return Winner(SECOND, intArrayOf(3, 4, 7,8))
        else if (player2.contains(5) && player2.contains(6) && player2.contains(9)&& player2.contains(10)) return Winner(SECOND, intArrayOf(5, 6, 9,10))
        else if (player2.contains(6) && player2.contains(7) && player2.contains(10)&& player2.contains(14)) return Winner(SECOND, intArrayOf(6, 7, 10,14))
        else if (player2.contains(7) && player2.contains(8) && player2.contains(11)&& player2.contains(12)) return Winner(SECOND, intArrayOf(7, 8, 11,12))
        else if (player2.contains(9) && player2.contains(10) && player2.contains(13)&& player2.contains(14)) return Winner(SECOND, intArrayOf(9, 10, 13,14))
        else if (player2.contains(10) && player2.contains(11) && player2.contains(14)&& player2.contains(15)) return Winner(SECOND, intArrayOf(10, 11, 14,15))
        else if (player2.contains(11) && player2.contains(12) && player2.contains(15)&& player2.contains(16)) return Winner(SECOND, intArrayOf(11, 12, 15,16))

        else if (player2.contains(1) && player2.contains(4) && player2.contains(13)&& player2.contains(16)) return Winner(SECOND, intArrayOf(1, 4, 13,16))
        else if (player2.contains(1) && player2.contains(2) && player2.contains(3) && player2.contains(4)) return Winner(SECOND, intArrayOf(1, 2, 3,4))
        else if (player2.contains(5) && player2.contains(6) && player2.contains(7)&& player2.contains(8)) return Winner(SECOND, intArrayOf(5,6,7,8))
        else if (player2.contains(9) && player2.contains(10) && player2.contains(11)&& player2.contains(12)) return Winner(SECOND, intArrayOf(9,10,11,12))
        else if (player2.contains(13) && player2.contains(14) && player2.contains(15)&& player2.contains(16)) return Winner(SECOND, intArrayOf(13,14,15,16))
        else if (player2.contains(1) && player2.contains(6) && player2.contains(11)&& player2.contains(16)) return Winner(SECOND, intArrayOf(1, 6, 11,16))
        else if (player2.contains(2) && player2.contains(6) && player2.contains(10)&& player2.contains(14)) return Winner(SECOND, intArrayOf(2, 6, 10,14))
        else if (player2.contains(3) && player2.contains(7) && player2.contains(11)&& player2.contains(15)) return Winner(SECOND, intArrayOf(3, 7, 11,15))
        else if (player2.contains(4) && player2.contains(8) && player2.contains(12)&& player2.contains(16)) return Winner(SECOND, intArrayOf(4, 8, 12,16))
        else if (player2.contains(1) && player2.contains(5) && player2.contains(9)&& player2.contains(13)) return Winner(SECOND, intArrayOf(1, 5, 9,13))
        else if (player2.contains(4) && player2.contains(7) && player2.contains(10)&& player2.contains(13)) return Winner(SECOND, intArrayOf(4, 7, 10,13))


        else return Winner(0, intArrayOf())
    }
    /**
     * This function will return the index of cell
     *
     *
     */
    private fun getCell(view: View): Int {
        when (view.id) {
            R.id.mainGameButton1 -> return 1
            R.id.mainGameButton2 -> return 2
            R.id.mainGameButton3 -> return 3
            R.id.mainGameButton4 -> return 4
            R.id.mainGameButton5 -> return 5
            R.id.mainGameButton6 -> return 6
            R.id.mainGameButton7 -> return 7
            R.id.mainGameButton8 -> return 8
            R.id.mainGameButton9 -> return 9
            R.id.mainGameButton10 -> return 10
            R.id.mainGameButton11 -> return 11
            R.id.mainGameButton12 -> return 12
            R.id.mainGameButton13 -> return 13
            R.id.mainGameButton14 -> return 14
            R.id.mainGameButton15 -> return 15
            R.id.mainGameButton16 -> return 16

            else -> return 0
        }
    }

    fun buttonClick() {
        replay()
    }
    /**
     * This function will clear the player stack whenever the user will confirm the restart
     *
     *
     */
    private fun replay() {
        player1.forEach { it ->
            (it.value as ImageView).setImageDrawable(ContextCompat.getDrawable(it.value.context, android.R.color.transparent))
            it.value.isEnabled = true
        }
        player1.clear()
        player2.forEach { it ->
            (it.value as ImageView).setImageDrawable(ContextCompat.getDrawable(it.value.context, android.R.color.transparent))
            it.value.isEnabled = true
        }
        player2.clear()
        currentPlayer = FIRST
        moves = 0
    }

    fun getStatistics(): Array<Int> {
        return stats
    }}

