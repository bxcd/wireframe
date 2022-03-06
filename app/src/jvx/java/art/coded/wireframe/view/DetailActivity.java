package art.coded.wireframe.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import art.coded.wireframe.model.entity.Element;
import art.coded.wireframe.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = Element.class.getSimpleName();

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Required for Up-nav with res-defined AppBar (along with Manifest meta-data definition)
        setSupportActionBar(binding.toolbarDetail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }
}