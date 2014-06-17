package it.wirge.clickpilot.service;

import ioio.lib.api.IOIO;
import ioio.lib.api.IOIOFactory;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.IOIOConnectionManager;
import android.util.Log;

public class AutoPilotService extends Thread
{

	public static final int			MODE_STANDBY	= 0;
	public static final int			MODE_AUTO		= 1;

	private static AutoPilotService	autoPilotService;
	private int						serviceMode;
	private IOIO					ioio;
	private boolean					abort			= false;

	private AutoPilotService()
	{
	}

	public static AutoPilotService getInstance()
	{
		if (autoPilotService == null)
		{
			autoPilotService = new AutoPilotService();
			autoPilotService.setMode(MODE_STANDBY);
			autoPilotService.run();
		}
		return autoPilotService;
	}

	@Override
	public void run()
	{
		super.run();
		while (true)
		{
			synchronized (this)
			{
				if (abort)
				{
					break;
				}
				ioio = IOIOFactory.create();
			}
			try
			{
				Log.i("","wait for IOIO");
				ioio.waitForConnect();
				Log.i("","IOIO Connected");
				/*
				 * DigitalOutput led = ioio_.openDigitalOutput(0, true); while
				 * (true) { led.write(!button_.isChecked()); sleep(10); }
				 */
			}
			catch (ConnectionLostException e)
			{
			}
			catch (Exception e)
			{
				Log.e("HelloIOIOPower", "Unexpected exception caught", e);
				ioio.disconnect();
				break;
			}
			finally
			{
				if (ioio != null)
				{
					try
					{
						ioio.waitForDisconnect();
					}
					catch (InterruptedException e)
					{
					}
				}
				synchronized (this)
				{
					ioio = null;
				}
			}
		}
	}

	/**
	 * Abort the connection.
	 * 
	 * This is a little tricky synchronization-wise: we need to be handle the
	 * case of abortion happening before the IOIO instance is created or during
	 * its creation.
	 */
	synchronized public void abort()
	{
		abort = true;
		if (ioio != null)
		{
			ioio.disconnect();
		}
	}

	public void setMode(int mode)
	{

		this.serviceMode = mode;

	}
}
