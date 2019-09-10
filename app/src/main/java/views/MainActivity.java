package views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.viewphotos.R;

import butterknife.BindView;
import views.fragments.PhotoFragment;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.action_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
            init();
    }

    public void init() {
        setSupportActionBar(toolbar);
        Fragment fragment = new PhotoFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_photos, fragment)
                .commitAllowingStateLoss();
    }
}

