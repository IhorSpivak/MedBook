package mobi.medbook.android.ui.authorization;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.authorization.EventNearestMedicalInstitutions;
import mobi.medbook.android.events.authorization.EventSelectedMedicalInstitution;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.core.EventListener;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.types.MedicalInstitution;
import mobi.medbook.android.utils.LogUtils;


import static android.os.Looper.getMainLooper;

public class MedicalInstitutionFragment extends Fragment implements OnMapReadyCallback, EventListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {


    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds

    private LocationManager mLocationManager;
    private int mSelectedMarkerId = -1;
    private SupportMapFragment mMapFragment;
    private double mLatitude;
    private double mLongitude;
    private GoogleMap mGoogleMap;

    private FusedLocationProviderClient locationProvider;

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

    public static MedicalInstitutionFragment newInstance() {
        final MedicalInstitutionFragment fragment = new MedicalInstitutionFragment();
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
                    mHashMapMarkers.get(mSelectedMarkerId).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mapmark0));
                    resizeConstraintLayout(mViewMarker, 0, 0);
                    mSelectedMarkerId = -1;
                }
            }
        });
        mTextMarkerText = rootView.findViewById(R.id.fragment_map_marker_details_text);
        mTextMarkerCaption = rootView.findViewById(R.id.fragment_map_marker_details_caption);

        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        LogUtils.logD("MedicalInstitutionFragment", "onCreateView");

        //locationProvider = LocationServices.getFusedLocationProviderClient(getContext());
        mLocationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        mMapFragment.getMapAsync(this);

        mViewMarkerHeight = mViewMarker.getLayoutParams().height;
        mViewMarkerWidth = mViewMarker.getLayoutParams().width;
        resizeConstraintLayout(mViewMarker, 0, 0);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        LogUtils.logD("MedicalInstitutionFragment", "onMapReady");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, AuthorizationActivity.PERMISSIONS_REQUEST_LOCATION);
            } else {
                //for mLocationManager need check permission ACCESS_FINE_LOCATION
                LogUtils.logD("MedicalInstitutionFragment", "onMapReady point 1");
                mLocationManager.requestSingleUpdate(
                        LocationManager.GPS_PROVIDER,
                        new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                if (location != null) {
                                    LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14), 1500, null);
                                    mLatitude = location.getLatitude();
                                    mLongitude = location.getLongitude();
                                    LogUtils.logD("MedicalInstitutionFragment", "location success");
                                    Core.get().getDataForRegistrationControl().getMedicalNearestMedicalInstitutes(mLatitude, mLongitude, 100);
                                    mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
                                    mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
                                    mLocationManager.removeUpdates(this);
                                }
                            }

                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {
                            }

                            @Override
                            public void onProviderEnabled(String provider) {
                            }

                            @Override
                            public void onProviderDisabled(String provider) {
                            }
                        }, getMainLooper());
            }
        } else {
            mLocationManager.requestSingleUpdate(
                    LocationManager.GPS_PROVIDER,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            if (location != null) {
                                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14), 1500, null);
                                mLatitude = location.getLatitude();
                                mLongitude = location.getLongitude();
                                LogUtils.logD("MedicalInstitutionFragment", "location success");
                                Core.get().getDataForRegistrationControl().getMedicalNearestMedicalInstitutes(mLatitude, mLongitude, 100);
                                mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
                                mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
                                mLocationManager.removeUpdates(this);
                            }
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                        }
                    }, getMainLooper());
        }

        Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15), 333, null);
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
            EventRouter.register(this);
            LogUtils.logD("MedicalInstitutionFragment", "location success");
            Core.get().getDataForRegistrationControl().getMedicalNearestMedicalInstitutes(mLatitude, mLongitude, 100);
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.setOnMarkerClickListener(this);
            mGoogleMap.setOnMapClickListener(this);
            mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
            mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        }

        mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng position = mGoogleMap.getCameraPosition().target;
                mLatitude = position.latitude;
                mLongitude = position.longitude;
                Core.get().getDataForRegistrationControl().getMedicalNearestMedicalInstitutes(mLatitude, mLongitude, 100);
            }
        });
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
                        if (listMark.get(i).translations[j].language.substring(0, 2).equals(Locale.getDefault().toLanguageTag().substring(0, 2))) {
                            selectLang = j;
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
                        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.mipmap.ic_mapmark0);
                        Bitmap b = bitmapdraw.getBitmap();
                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(b));
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
        if(mSelectedMarkerId != -1)
        if (mHashMapMarkers.get(mSelectedMarkerId) != null) {
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
        LogUtils.logD("ghjt78tgjhjg56", "onMarkerClick");
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
