package art.coded.wireframe.view

import art.coded.wireframe.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.content.Intent
import androidx.navigation.ui.NavigationUI
import android.view.*
import androidx.navigation.Navigation
import art.coded.wireframe.databinding.ActivityMainBinding
import art.coded.wireframe.model.entity.Element

class MainActivity : AppCompatActivity() {
    private var mAppBarConfiguration: AppBarConfiguration? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        binding.appBarMain.fabMain.setOnClickListener { view: View ->
            val tabActivityIntent = Intent(this@MainActivity, TabActivity::class.java)
            startActivity(tabActivityIntent)
        }

        // Drawer
        val drawer = binding.drawerLayout
        val navigationView = binding.navView
        mAppBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_home, R.id.nav_list, R.id.nav_work, R.id.nav_paging, R.id.nav_custom
        )
            .setOpenableLayout(drawer)
            .build()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration!!)
        NavigationUI.setupWithNavController(navigationView, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val settingsActivityIntent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(settingsActivityIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        // Custom implementation required for drawer navigation
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
        return (NavigationUI.navigateUp(navController, mAppBarConfiguration!!)
                || super.onSupportNavigateUp())
    }

    companion object {
        private val LOG_TAG = Element::class.java.simpleName
    }
}