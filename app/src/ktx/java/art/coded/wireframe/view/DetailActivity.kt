package art.coded.wireframe.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import art.coded.wireframe.databinding.ActivityDetailBinding
import art.coded.wireframe.model.entity.Element

class DetailActivity : AppCompatActivity() {
    private var binding: ActivityDetailBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        // Required for Up-nav with res-defined AppBar (along with Manifest meta-data definition)
        setSupportActionBar(binding!!.toolbarDetail)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        private val LOG_TAG = Element::class.java.simpleName
    }
}