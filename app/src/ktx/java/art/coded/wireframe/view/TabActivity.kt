package art.coded.wireframe.view

import art.coded.wireframe.view.adapter.TabPagerAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.View
import art.coded.wireframe.databinding.ActivityTabBinding
import art.coded.wireframe.model.entity.Element

private val LOG_TAG = Element::class.java.simpleName

class TabActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTabBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fab = binding.fabTab
        fab.setOnClickListener { view: View ->
            startActivity(
                Intent(this@TabActivity, DetailActivity::class.java)
            )
        }

        // Required for Up-nav with res-defined AppBar (along with Manifest meta-data definition)
        setSupportActionBar(binding.toolbarTab)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Tabs
        val tabPagerAdapter = TabPagerAdapter(this, supportFragmentManager)
        val viewPager = binding.viewPager
        viewPager.adapter = tabPagerAdapter
        val tabs = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }
}