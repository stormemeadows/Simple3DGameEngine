package core.display;


import org.lwjgl.Sys;

/**
 * @author storm
 *
 */
public class Clock {

	private long	lastUpdateTime;	// stored in millisecs
	private float	deltaTime;		// stored in millisecs


	public Clock() {
		deltaTime = 0;
		lastUpdateTime = getCurrentSysTimeInMilliseconds();
	}


	public float getTimeSinceLastUpdateInSeconds() {
		// updateTime();
		return deltaTime / 1000f; // use secs
	}


	public float getTimeSinceLastUpdateInMilliseconds() {
		// updateTime();
		return deltaTime;
	}


	public void update() {
		final long curSysTimeInMillis = getCurrentSysTimeInMilliseconds();
		deltaTime = (curSysTimeInMillis - lastUpdateTime);
		lastUpdateTime = curSysTimeInMillis;
	}


	// in milliseconds
	public long getCurrentSysTimeInMilliseconds() {
		return 1000 * Sys.getTime() / Sys.getTimerResolution();
	}

}
