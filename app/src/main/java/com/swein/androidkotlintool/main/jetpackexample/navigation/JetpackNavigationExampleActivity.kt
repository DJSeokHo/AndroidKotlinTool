package com.swein.androidkotlintool.main.jetpackexample.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.swein.androidkotlintool.R

class JetpackNavigationExampleActivity : AppCompatActivity() {

    private val bottomNavigationView: BottomNavigationView by lazy {
        findViewById(R.id.bottomNavigationView)
    }

    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack_navigation_example)

        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

}