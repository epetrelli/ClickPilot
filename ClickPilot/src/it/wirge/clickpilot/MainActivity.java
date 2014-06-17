package it.wirge.clickpilot;

import it.wirge.clickpilot.service.AutoPilotService;
import it.wirge.clickpilot.service.IntentServiceAutoPilot;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

public class MainActivity extends ActionBarActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.i(this.getClass().getName(), "onCreate(...)");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null)
		{
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}

		
		Intent intentServiceAutoPilot = new Intent(this, IntentServiceAutoPilot.class);
		startService(intentServiceAutoPilot);
	}

	public void setLoopMode(View view)
	{
		AutoPilotService autoPilotService = AutoPilotService.getInstance();
		ToggleButton toggleButtonPilotMode = (ToggleButton) view;
		if (toggleButtonPilotMode.isChecked())
		{
			Log.i(this.getLocalClassName(), "setLoopMode(1)");
			toggleButtonPilotMode.setChecked(true);
			autoPilotService.setMode(AutoPilotService.MODE_AUTO);
		}
		else
		{
			Log.i(this.getLocalClassName(), "setLoopMode(0)");
			toggleButtonPilotMode.setChecked(false);
			autoPilotService.setMode(AutoPilotService.MODE_STANDBY);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if (id == R.id.action_exit)
		{
			Log.i(this.getLocalClassName(), "exit.");
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment
	{

		public PlaceholderFragment()
		{
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			return rootView;
		}
	}

}
