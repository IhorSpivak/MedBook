package mobi.medbook.android.ui.authorization;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.authorization.EventNearestMedicalInstitutions;
import mobi.medbook.android.events.authorization.EventSelectedMedicalInstitution;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.core.EventListener;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.types.MedicalInstitution;


public class NewMedicalInstitutionFragment extends Fragment implements OnMapReadyCallback, EventListener,
                                                                        GoogleMap.OnMapClickListener,
                                                                        GoogleMap.OnMarkerClickListener,
                                                                        GoogleMap.OnCameraIdleListener {


    //Loation  UPDATES
    public static final int LOCATION_UPDATES_INTERVAL = 10000;
    public static final int CAN_HANDLE_UPDATES_INTERVAL = 5000;
    public static final int MIN_DISTANCE_LOCATION_UUPDATES = 100;
    private static final int MAP_ZOOM = 15;
    public static final int REQUEST_CHECK_SETTINGS = 101;

    //private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    //private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds

    //private LocationManager mLocationManager;
    private int mSelectedMarkerId = -1;
    private SupportMapFragment mMapFragment;
    private GoogleMap mGoogleMap;
    private LatLng currentLatLng;

    private FusedLocationProviderClient locationProvider;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private Button mDetailsMarkerSelect;
    private ImageButton mCloseIV;
    private TextView mTextMarkerText;
    private TextView mTextMarkerCaption;
    private ConstraintLayout mViewMarker;
    //private boolean isCollapsed = true;


    private HashMap<Integer, Marker> mHashMapMarkers = new HashMap<>();
    private HashMap<Integer, Data> mHashMapData = new HashMap<>();

    private int mViewMarkerHeight;
    private int mViewMarkerWidth;

    public static NewMedicalInstitutionFragment newInstance() {
        final NewMedicalInstitutionFragment fragment = new NewMedicalInstitutionFragment();
        return fragment;
    }

    private class Data {
        public String caption;
        public String address;

        public Data(String caption, String address) {
            this.caption = caption;
            this.address = address;
        }
    }

    private void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(LOCATION_UPDATES_INTERVAL);
        locationRequest.setFastestInterval(CAN_HANDLE_UPDATES_INTERVAL);
        //change to PRIORITY_BALANCED_POWER_ACCURACY to get balance between accuracy and battery
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void updateUI(Location location) {
        if (mGoogleMap == null) {
            return;
        }
        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, MAP_ZOOM));

        Core.get().getDataForRegistrationControl().getMedicalNearestMedicalInstitutes(currentLatLng.latitude, currentLatLng.longitude, 100);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mViewMarker = rootView.findViewById(R.id.fragment_map_details_marker);
        mDetailsMarkerSelect = rootView.findViewById(R.id.fragment_map_marker_details_ok);
        mDetailsMarkerSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventRouter.send(new EventSelectedMedicalInstitution(mHashMapData.get(mSelectedMarkerId).caption, mSelectedMarkerId));
                getActivity().onBackPressed();
            }
        });
        mCloseIV = rootView.findViewById(R.id.fragment_map_marker_details_close);
        mCloseIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHashMapMarkers.get(mSelectedMarkerId) != null) {
                    mHashMapMarkers.get(mSelectedMarkerId).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.med_inst_mark));
                    resizeConstraintLayout(mViewMarker, 0, 0);
                    mSelectedMarkerId = -1;
                }
            }
        });
        mTextMarkerText = rootView.findViewById(R.id.fragment_map_marker_details_text);
        mTextMarkerCaption = rootView.findViewById(R.id.fragment_map_marker_details_caption);

        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        initializeView();
        mViewMarkerHeight = mViewMarker.getLayoutParams().height;
        mViewMarkerWidth = mViewMarker.getLayoutParams().width;
        resizeConstraintLayout(mViewMarker, 0, 0);
        EventRouter.register(this);
        return rootView;
    }

    public void tryGetLocation() {
        checkLocationSettings();
    }

    private void checkLocationSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(locationSettingsResponse -> {
            // All location settings are satisfied
            startLocationUpdates();

        });

        task.addOnFailureListener(e -> {
            if (e instanceof ResolvableApiException) {
                try {
                    // Show the dialog and check the result in onActivityResult()
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult((AuthorizationActivity) getContext(),
                            REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {

                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        locationProvider.requestLocationUpdates(locationRequest,
                locationCallback,
                null);
    }

    private void initializeView() {
        locationProvider = LocationServices.getFusedLocationProviderClient(getContext());
        mMapFragment.getMapAsync(this);
        createLocationRequest();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                locationProvider.removeLocationUpdates(locationCallback);

                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        updateUI(location);

                        break;
                    }
                }
            }
        };
    }

    @SuppressLint("MissingPermission")
    public void getLastLocation() {
        locationProvider.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        updateUI(location);
                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AuthorizationActivity.PERMISSIONS_REQUEST_LOCATION);
            } else {
                startMap();
            }
        }
        else {
            startMap();
        }
    }

    @SuppressLint("MissingPermission")
    private void startMap(){
        tryGetLocation();
        mGoogleMap.setOnCameraIdleListener(this);
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.getUiSettings().setCompassEnabled(false);
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnMapClickListener(this);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
    }

    @Override
    public void onCameraIdle() {
        LatLng centerLatLng = mGoogleMap.getProjection().getVisibleRegion().latLngBounds.getCenter();
        Core.get().getDataForRegistrationControl().getMedicalNearestMedicalInstitutes(centerLatLng.latitude, centerLatLng.longitude, 100);
    }


    @Override
    public void onDestroy() {
        EventRouter.unregister(this);
        super.onDestroy();
    }

    @Override
    public void onEvent(Event event) {
        switch (event.getEventId()) {
            case Event.EVENT_NEAREST_MEDICAL_INSTITUTIONS:
                resizeConstraintLayout(mViewMarker, 0, 0);
                mCloseIV.setEnabled(false);
                mDetailsMarkerSelect.setEnabled(false);
                mGoogleMap.clear();
                mHashMapData.clear();
                mHashMapMarkers.clear();

                mCloseIV.setEnabled(true);
                mDetailsMarkerSelect.setEnabled(true);
                ArrayList<MedicalInstitution> listMark = ((EventNearestMedicalInstitutions) event).getmListOfNearestMedicalInstitute();
                for (int i = 0; i < listMark.size(); i++) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(listMark.get(i).geo_lat, listMark.get(i).geo_lon));


                    int selectLang = -1;

                    for (int j = 0; j < listMark.get(i).translations.length; j++) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            if (listMark.get(i).translations[j].language.substring(0, 2).equals(Locale.getDefault().toLanguageTag().substring(0, 2))) {
                                selectLang = j;
                            }
                        }
                    }
                    if (selectLang == -1) {
                        for (int j = 0; j < listMark.get(i).translations.length; j++) {
                            if (listMark.get(i).translations[j].language.substring(0, 2).equals("uk")) {
                                selectLang = j;
                            }
                        }
                    }

                    if (mSelectedMarkerId != -1 && mSelectedMarkerId == listMark.get(i).id) {
                        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.mipmap.ic_med_inst_select_mark);
                        Bitmap b = bitmapdraw.getBitmap();

                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(b));

                        resizeConstraintLayout(mViewMarker, mViewMarkerWidth, mViewMarkerHeight);
                        mTextMarkerCaption.setText(listMark.get(i).translations[selectLang].name);
                        mTextMarkerText.setText(listMark.get(i).translations[selectLang].address);
                    } else {


                        int height = 60;
                        int width = 60;
                        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.mipmap.ic_mapmark0);
                        Bitmap b =bitmapdraw.getBitmap();
                        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

                    }

                    Marker marker = mGoogleMap.addMarker(markerOptions);
                    mHashMapMarkers.put(listMark.get(i).id, marker);
                    mHashMapData.put(listMark.get(i).id, new Data(listMark.get(i).translations[selectLang].name, listMark.get(i).translations[selectLang].address));
                }
                if (mHashMapMarkers.get(mSelectedMarkerId) == null) mSelectedMarkerId = -1;
                break;
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (mSelectedMarkerId != -1)
            if (mHashMapMarkers.get(mSelectedMarkerId) != null) {
                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.mipmap.ic_mapmark0);
                Bitmap b =bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 60, 60, false);

                mHashMapMarkers.get(mSelectedMarkerId).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mapmark0));
                resizeConstraintLayout(mViewMarker, 0, 0);
                mSelectedMarkerId = -1;
            }
    }

    private int isNewSelectedMarker(Marker marker) {
        for (Map.Entry<Integer, Marker> entry : mHashMapMarkers.entrySet()) {
            Integer key = entry.getKey();
            Marker value = entry.getValue();
            if (value.getPosition().latitude == marker.getPosition().latitude && value.getPosition().longitude == marker.getPosition().longitude) {
                return key;
            }
        }
        return -1;
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        int markerSelectIdOld = mSelectedMarkerId;
        if (markerSelectIdOld == isNewSelectedMarker(marker)) {
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mapmark0));
            resizeConstraintLayout(mViewMarker, 0, 0);
        } else {
            if (mHashMapMarkers.get(markerSelectIdOld) != null)
                mHashMapMarkers.get(markerSelectIdOld).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mapmark0));
            mSelectedMarkerId = isNewSelectedMarker(marker);
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_med_inst_select_mark));
            resizeConstraintLayout(mViewMarker, mViewMarkerWidth, mViewMarkerHeight);
            if (mHashMapData.get(mSelectedMarkerId) != null) {
                mTextMarkerCaption.setText(mHashMapData.get(mSelectedMarkerId).caption);
                mTextMarkerText.setText(mHashMapData.get(mSelectedMarkerId).address);
            }
        }
        return true;

    }

    private void resizeConstraintLayout(ConstraintLayout view, int newWidth, int newHeight) {
        ConstraintLayout.LayoutParams constraintLayout = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        constraintLayout.width = newWidth;
        constraintLayout.height = newHeight;
        view.setLayoutParams(constraintLayout);
    }
}
