package views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;

import com.example.viewphotos.R;

import butterknife.ButterKnife;

/**
 * Launcher Activity.
 */
public class SplashActivity extends Activity {
	
	private boolean m_isAuthorizedUser = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		mShowLoginScreen();
	}

	private void mShowLoginScreen() {
		new Handler().postDelayed(() -> {
			startActivityForResult(new Intent(SplashActivity.this, MainActivity.class), 1);
			finish();
		}, 1000);
	}
}