package com.upadhyay.nilay.tictactoedemo.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.upadhyay.nilay.tictactoedemo.R
import com.upadhyay.nilay.tictactoedemo.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_board.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.Bitmap
import android.os.AsyncTask
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TableRow
import com.upadhyay.nilay.tictactoedemo.util.AppUtils
import com.upadhyay.nilay.tictactoedemo.util.BlurView
import com.upadhyay.nilay.tictactoedemo.util.AnimationUtil


class BoardActivity : AppCompatActivity() {

    companion object {
        private const val TAG: String = "BoardActivity"
    }
    private val presenter: MainPresenter by lazy { MainPresenter() }
    private val count: Int by lazy { tableLayout.childCount }
    private lateinit var blurrEffectDialog: Dialog
    var numbers= Array(3) { 0 }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        AnimationUtil.slideInFromRight(this,tableLayout)
    }

    fun mainGameButtonClick(view: View) {
        presenter.imageButtonClick(view, { value ->
            run {
                if (value) {
                    numbers = presenter.getStatistics()
                    setImageButtonsEnable(false)
                    tvP1.setText(numbers[1].toString())
                    tvP2.setText(numbers[2].toString())

                    blurrEffectDialog = Dialog(this)
                    BlurAsyncTask().execute("val")
                }
            }
        })
    }

    fun mainReplayButtonClick(view: View) {
        val restartDialog =  AlertDialog.Builder(this).create()
        restartDialog.setCancelable(false)

        val restartText = TextView(this)
        restartText.gravity = Gravity.CENTER_HORIZONTAL
        restartText.text =  "Do you want to Restart"
        restartText.setTextSize(20F)

        restartDialog.setView(restartText)
        restartDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES" , {
            dialogInterface, which ->
            presenter.buttonClick()
            setImageButtonsEnable(true)
        })
        restartDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", {
            dialogInterface, which ->
        restartDialog.cancel()
        })

        restartDialog.show()
    }

    private fun setImageButtonsEnable(value: Boolean) {
        Log.d(TAG,(count - 1).toString())
        (0..count - 1)
                .filter { tableLayout.getChildAt(it) is TableRow }
                .forEach {
                    Log.d(TAG,mainContainer.getChildAt(it).toString())
                    mainContainer.getChildAt(it).isEnabled = value }

    }

    internal inner class BlurAsyncTask : AsyncTask<String, Int, Bitmap>() {


        override fun doInBackground(vararg arg0: String): Bitmap? {

            val map = AppUtils.takeScreenShot(this@BoardActivity)
            return BlurView().fastBlur(map, 10)
        }
        override fun onPostExecute(result: Bitmap?) {
            if (result != null) {
                val draw = BitmapDrawable(resources, result)
                val window = blurrEffectDialog.getWindow()
                window.setBackgroundDrawable(draw)
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
                window.setGravity(Gravity.CENTER)
                blurrEffectDialog.show()

                // custom dialog
                val dialog = Dialog(this@BoardActivity)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.layout_dialoge)


                // set the custom dialog components - text, image and button
                val P1 = dialog.findViewById<View>(R.id.tvP1) as TextView
                val P2 = dialog.findViewById<View>(R.id.tvP2) as TextView
                val p1_Image = dialog.findViewById<View>(R.id.ivP1) as ImageView
                val p2_Image = dialog.findViewById<View>(R.id.ivP2) as ImageView
                val p1_Score = dialog.findViewById<View>(R.id.tvP1Score) as TextView
                val p2_Score = dialog.findViewById<View>(R.id.tvP2Score) as TextView
                val newgame = dialog.findViewById<View>(R.id.btnNewGame) as Button
                val exit = dialog.findViewById<View>(R.id.btnExit) as Button
                p1_Score.setText(numbers[1].toString())
                p2_Score.setText(numbers[2].toString())

                // if button is clicked, close the custom dialog
                newgame.setOnClickListener(
                        View.OnClickListener {
                            presenter.buttonClick()
                            setImageButtonsEnable(true)
                            blurrEffectDialog.dismiss()
                            dialog.cancel()
                        }
                )
                exit.setOnClickListener(
                        View.OnClickListener {
                            val intent = Intent(this@BoardActivity, LevelSelectionActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            blurrEffectDialog.dismiss()
                            dialog.cancel()
                        }
                )
                dialog.show()

            }

        }
    }

}

