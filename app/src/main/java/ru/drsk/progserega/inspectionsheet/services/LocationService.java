package ru.drsk.progserega.inspectionsheet.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Point;

import static android.content.Context.LOCATION_SERVICE;

public class LocationService implements ILocation, LocationListener {

    private final Context mContext;

    private ILocationChangeListener locationChangeListener;

    private List<WeakReference<ILocationChangeListener>> listeners = new ArrayList<>();
    // flag for GPS status
    boolean isGPSEnabled = false;

    boolean isGPSStarted = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 10 * 1; // 10 sec

    // Declaring a Location Manager
    protected LocationManager locationManager;

    @Override
    public void setLocationChangeListener(WeakReference<ILocationChangeListener> locationChangeListener) {
        // this.locationChangeListener = locationChangeListener;
        listeners.add(locationChangeListener);
    }

    public LocationService(Context context) {
        this.mContext = context;
        //  getLocation();
    }

    @Override
    public Point getUserPosition() {
        Location loc = getLocation();
        if (loc == null) {
            return new Point(0, 0, 0);
        }
        return new Point(loc.getLatitude(), loc.getLongitude(), loc.getAltitude());
    }

    /**
     * Расстояние в метрах между двумя точками в GPS координатах
     *
     * @param equipmentPoint координаты оборудования
     * @param userPosition   координаты пользователя
     * @return float расстояние между точками в метрах
     */
    @Override
    public double distanceBetween(Point equipmentPoint, Point userPosition) {
        Location userLoc = new Location(LocationManager.GPS_PROVIDER);
        userLoc.setLatitude(userPosition.getLat());
        userLoc.setLongitude(userPosition.getLon());

        Location destLoc = new Location(LocationManager.GPS_PROVIDER);
        destLoc.setLatitude(equipmentPoint.getLat());
        destLoc.setLongitude(equipmentPoint.getLon());
        float dist = userLoc.distanceTo(destLoc);
        return dist;

    }

    /**
     * Радиус поиска по умолчанию
     *
     * @return в метрах
     */
    @Override
    public float defaultSearchRadius() {
        return 500;
    }


    @SuppressLint("MissingPermission")
    public Location getLocation() {
        try {

            startGPSListen();

            if (isGPSStarted) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    @SuppressLint("MissingPermission")
    private void startGPSListen() {
        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

        // getting GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isGPSEnabled) {
            isGPSStarted = false;
            return;
        }

        //если уже запущен
        if (isGPSStarted) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

        isGPSStarted = true;
        Log.d("GPS Enabled", "GPS Enabled");
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */

    @Override
    public void stopUsingGPS() {
        isGPSStarted = false;
        if (locationManager != null) {
            locationManager.removeUpdates(LocationService.this);
        }
    }


    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }


    @Override
    public void onLocationChanged(Location location) {

        if (!isGPSStarted) {
            return;
        }

        for (WeakReference<ILocationChangeListener> listener : listeners) {
            if (listener.get() != null) {
                listener.get().onLocationChange(new Point(location.getLatitude(), location.getLongitude(), location.getAltitude()));
            }
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


}
