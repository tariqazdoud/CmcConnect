package com.example.cmcconnect

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cmcconnect.model.UserInInfo
import com.example.cmcconnect.repository.sharedRepository.AuthenticationRepository
import com.example.cmcconnect.shared.login.LoginActivity
import com.example.cmcconnect.shared.login.SignInViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var menuCloseBtn: ImageButton

    private val signInViewModel : SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.myToolBar)
        setSupportActionBar(toolbar)




        drawerLayout = findViewById(R.id.drawer)
        navigationView = findViewById(R.id.nav_view)

        val navHeader = navigationView.getHeaderView(0)
        val headerTextView : TextView = navHeader.findViewById(R.id.user_name_tv)

        headerTextView.text = UserInInfo.name

        when(UserInInfo.id_type_user_fk){
            1-> {
                navigationView.inflateMenu(R.menu.nav_menu)
                appBarConfiguration = AppBarConfiguration(
                    setOf(
                        R.id.id_dashboardFragment,
                        R.id.id_resourcesFragment, R.id.id_requestsFragment, R.id.id_justifFragment,
                        R.id.id_chatHomeFragment, R.id.id_eventsFragment, R.id.id_profileFragment
                    ),
                    drawerLayout
                )

            }
            2->{
                navigationView.inflateMenu(R.menu.teacher_menu)
                appBarConfiguration = AppBarConfiguration(
                    setOf(
                        R.id.id_dashboardFragment,
                        R.id.id_seeResourcesFragment, R.id.id_postResourcesFragment, R.id.id_requestsFragment,
                        R.id.id_groupsFragment, R.id.id_chatHomeFragment, R.id.id_eventsFragment, R.id.id_profileFragment
                    ),
                    drawerLayout
                )


            }
            3->{
                navigationView.inflateMenu(R.menu.admin_menu)
                setNavigationGraph(R.navigation.admin_mobile_navigation)
                appBarConfiguration = AppBarConfiguration(
                    setOf(
                        R.id.id_adminDashboardFragment,R.id.id_filiereFragment,R.id.id_adminSeeRequestsFragment,
                        R.id.id_answeredRequestsFragment,R.id.id_adminJustifsFragment,
                        R.id.id_adminFormateursFragment,R.id.id_eventsFragment,R.id.id_profileFragment
                    ),
                    drawerLayout
                )


            }
        }

        navController = findNavController(R.id.fragmentContainerView)

        setupActionBarWithNavController(navController, drawerLayout)
        navigationView.setupWithNavController(navController)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(false)

        val customBurgerIcon: ImageButton = findViewById(R.id.customBurgerIcon)
        val headerView = navigationView.getHeaderView(0)
        menuCloseBtn = headerView.findViewById(R.id.menuCloseBtn)
        menuCloseBtn.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        customBurgerIcon.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        val toolbar : Toolbar = findViewById(R.id.myToolBar)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setHomeButtonEnabled(false)
            when(destination.id){
                R.id.id_groupeStudentsFragment , R.id.eventsDetailsFragment,R.id.studentsByGroupeFragment -> toolbar.visibility = View.GONE
                else -> toolbar.visibility = View.VISIBLE
            }
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            menuItem.onNavDestinationSelected(navController)
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val logOutBtn :Button = findViewById(R.id.logOutBtn)
        logOutBtn.setOnClickListener {
            signInViewModel.logout()
            val intent = Intent(this,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()

        }


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    private fun setNavigationGraph(navigationGraphId: Int) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navHostFragment.navController.setGraph(navigationGraphId)
    }
}