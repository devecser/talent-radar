package com.menatwork.location;

import java.util.HashSet;
import java.util.Set;

import android.location.Location;
import android.util.Log;

public class LocationSourceManager {

	private static final long DEFAULT_MILLISECONDS_BETWEEN_UPDATES = 10000;

	private final Set<LocationSourceManagerListener> listeners;
	private final Set<LocationSource> locationSources;

	private boolean active;
	private long millisecondsBetweenUpdates;
	private UpdatingThread updatingThread;

	public LocationSourceManager() {
		this(DEFAULT_MILLISECONDS_BETWEEN_UPDATES);
	}

	public LocationSourceManager(final long millisecondsBetweenUpdates) {
		this.listeners = new HashSet<LocationSourceManagerListener>();
		this.locationSources = new HashSet<LocationSource>();
		this.active = false;

		this.millisecondsBetweenUpdates = millisecondsBetweenUpdates;
	}

	public void setMillisecondsBetweenUpdates(
			final long millisecondsBetweenUpdates) {
		this.millisecondsBetweenUpdates = millisecondsBetweenUpdates;
	}

	// ************************************************ //
	// ====== LocationSource ======
	// ************************************************ //

	public void addLocationSource(final LocationSource locationSource) {
		locationSources.add(locationSource);

		locationSource.register();
	}

	public void removeLocationSource(final LocationSource locationSource) {
		locationSources.remove(locationSource);

		locationSource.unregister();
	}

	public void removeAllLocationSources() {
		for (final LocationSource locationSource : locationSources)
			locationSource.unregister();

		locationSources.clear();
	}

	// ************************************************ //
	// ====== Activation methods ======
	// ************************************************ //

	public void activate() {
		Log.d("LocationSourceManager", "activate()");

		active = true;
		updatingThread = new UpdatingThread();
		updatingThread.start();
	}

	public void deactivate() {
		Log.d("LocationSourceManager", "deactivate()");

		active = false;
		updatingThread = null;
	}

	public boolean isActive() {
		return active;
	}

	// ************************************************ //
	// ====== Listeners ======
	// ************************************************ //

	public void addListener(final LocationSourceManagerListener listener) {
		listeners.add(listener);
	}

	public void removeListener(final LocationSourceManagerListener listener) {
		listeners.remove(listener);
	}

	// ************************************************ //
	// ====== UpdatingThread ======
	// ************************************************ //

	private class UpdatingThread extends Thread {

		@Override
		public void run() {
			while (active) {
				try {
					Location bestLocation = null;
					LocationSource bestLocationSource = null;

					// compute best recent location update
					for (final LocationSource locationSource : locationSources) {
						final Location location = locationSource
								.getLastKnownLocation();

						if (bestLocation == null || location != null
								&& isBetter(location, bestLocation)) {
							bestLocation = location;
							bestLocationSource = locationSource;
						}
					}

					// notify listeners
					for (final LocationSourceManagerListener listener : listeners)
						listener.onLocationUpdate(bestLocation,
								bestLocationSource);

					Thread.sleep(millisecondsBetweenUpdates);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Tests whether a given comparing location is better than the original one
	 * by some criteria.
	 *
	 * @param comparing
	 * @param original
	 * @return <code>true</code> - if comparing is better than the original
	 */
	private boolean isBetter(final Location comparing, final Location original) {
		// TODO - for the time being, a location is better than another one
		// if it's more recent - boris - 29/08/2012
		return comparing.getTime() > original.getTime();
	}

}