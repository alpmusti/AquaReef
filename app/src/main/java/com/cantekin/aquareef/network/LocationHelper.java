package com.cantekin.aquareef.network;

import android.content.Context;
import android.content.IntentSender;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cantekin.aquareef.ui.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.List;

/**
 * Created by Cantekin on 6.9.2017.
 */

public class LocationHelper {
    Context context;
    protected static final String TAG = "LocationHelper";

    private GoogleApiClient googleApiClient;
    public final static int REQUEST_LOCATION = 199;

    public LocationHelper(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) && hasGPSDevice(context)) {
          //  Toast.makeText(context, "Gps already enabled", Toast.LENGTH_SHORT).show();
        }

        if (!hasGPSDevice(context)) {
            Log.e(TAG, "Gps not Supported");
            Toast.makeText(context, "Gps not Supported", Toast.LENGTH_SHORT).show();
        }

        if (!manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) && hasGPSDevice(context)) {
            Log.e(TAG, "Gps not enabled");
            enableLoc();
        } else {
            Log.e(TAG, "Gps already enabled");
        }
    }


    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.NETWORK_PROVIDER);
    }

    private void enableLoc() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error", "Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(((MainActivity) context), REQUEST_LOCATION);

                                // finish();
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }
    }
}
