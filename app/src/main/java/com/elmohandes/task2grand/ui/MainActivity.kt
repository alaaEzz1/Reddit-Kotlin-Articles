package com.elmohandes.task2grand.ui

/*
*** Author ::: Alaa Ahmed Ez-eldin ***
*** Task Requirements ***
* get data from Reddit JSON file
* display the results in rv with the ability to tap into the full article
* use navigation components
* show full Article
* use MVVM
* add cache memory (Room)
* add check internet connection
 */

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.elmohandes.task2grand.R

class MainActivity : AppCompatActivity() {

    private var appBarConfiguration: AppBarConfiguration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: set title of action bar

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                as NavHostFragment?
        val navController = navHostFragment!!.navController
        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        NavigationUI.setupActionBarWithNavController(
            this, navController, appBarConfiguration!!
        )

    }


    override fun onSupportNavigateUp(): Boolean {
        val controller = findNavController(
            this,
            R.id.fragment_container
        )
        return controller.navigateUp() || super.onSupportNavigateUp()
    }

}