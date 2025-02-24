package com.nidhi.recipevault.com.nidhi.recipevault.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.nidhi.recipevault.R
import com.nidhi.recipevault.com.nidhi.recipevault.ui.fragment.RecipeListFragment
import com.nidhi.recipevault.com.nidhi.recipevault.utils.LogUtils
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

        // Set Toolbar as the ActionBar
        val toolbar: Toolbar = activityMainBinding.mainActToolBar
        setSupportActionBar(toolbar)

        if(savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<RecipeListFragment>(R.id.fragmentContainerView)
            }
        }
    }
    fun showToolbar(show: Boolean) {
        activityMainBinding.mainActToolBar.visibility = if (show) View.VISIBLE else View.GONE
    }
}
