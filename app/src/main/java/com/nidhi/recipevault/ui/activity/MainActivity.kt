package com.nidhi.recipevault.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.nidhi.recipevault.R
import com.nidhi.recipevault.ui.fragment.RecipeListFragment
import com.nidhi.recipevault.utils.LogUtils
import com.nidhi.recipevault.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LogUtils.getTag(this::class.java), "MainActivity class is creating")

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = activityMainBinding.root
        setContentView(view)

        if(savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<RecipeListFragment>(R.id.fragmentContainerView)
            }
        }
    }
}
