package com.upadhyay.nilay.tictactoedemo.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.upadhyay.nilay.tictactoedemo.R
import com.upadhyay.nilay.tictactoedemo.presenter.MainPresenter
import com.upadhyay.nilay.tictactoedemo.util.AnimationUtil
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.android.synthetic.main.activity_level_selection.*

class LevelSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_selection)
        val intent = Intent(this,BoardActivity::class.java );

        button_4.setOnClickListener{
            intent.putExtra("Level","4");
            startActivity(intent)
            overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)
        }
    }
    override fun onResume() {
        super.onResume()

        AnimationUtil.slideInFromRight(this,button_4)
    }
}