package it.wirge.clickpilot.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class IntentServiceAutoPilot extends IntentService {
	
	private static AutoPilotService autoPilotService;
	
	public IntentServiceAutoPilot() {
		super("IntentServiceAutoPilot");
		Log.i(this.getClass().getName(), "Contructed");
	}

	@Override
    protected void onHandleIntent(Intent workIntent) {
		
		Log.i(this.getClass().getName(), "onHandle");
		
        //String dataString = workIntent.getDataString();
        
        IntentServiceAutoPilot.autoPilotService = AutoPilotService.getInstance();
        

    }
}
