package com.menatwork;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;

import com.menatwork.radar.RadarActivity;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		initializeTabs();
		startRadarService();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		stopRadarService();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings:
			showPreferences();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showPreferences() {
		final Intent settingsIntent = new Intent(this,
				PreferencesActivity.class);
		startActivity(settingsIntent);
	}

	// ************************************************ //
	// ====== LocationSourceManager ======
	// ************************************************ //

	private void stopRadarService() {
		// stopService(new Intent(this, RadarService.class));
		// Log.i("MainActivity", "stopping radar service");

		getTalentRadarApplication().stopLocationSourceManager();
	}

	private void startRadarService() {
		// startService(new Intent(this, RadarService.class));
		// Log.i("MainActivity", "starting radar service");

		getTalentRadarApplication().startLocationSourceManager();
	}

	public TalentRadarApplication getTalentRadarApplication() {
		return (TalentRadarApplication) getApplication();
	}

	// ************************************************ //
	// ====== Managing Tabs ======
	// ************************************************ //

	private void initializeTabs() {
		addTab(DashboardActivity.class, //
				"dashboard", //
				"Dashboard", //
				R.drawable.icon_dashboard_tab);

		addTab(RadarActivity.class, //
				"radar", //
				"Radar", //
				R.drawable.icon_radar_tab);

		addTab(ProfileActivity.class, //
				"profile", //
				"Profile", //
				R.drawable.icon_profile_tab);
	}

	private void addTab(final Class<? extends Activity> tabClass,
			final String tabTag, final String tabLabel, final int tabIconId) {

		// get tab host
		final TabHost tabHost = getTabHost();

		// create tab spec
		final Drawable tabIcon = getResources().getDrawable(tabIconId);
		final Intent intent = new Intent(this, tabClass);

		final TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabTag) //
				.setIndicator(tabLabel, tabIcon) //
				.setContent(intent);

		// add tabs pec to host
		tabHost.addTab(tabSpec);
	}

}
