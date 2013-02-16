package com.tomra.stores;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tomra.ui.R;


public class MapFragment extends SherlockMapFragment {
	
	static final String TAG = "MapFragment";
	
	final static String ARG_LAT = "latitude";
	final static String ARG_LNG = "longitude";
//	double mLat = -1;
//	double mLng = -1;
	
	double mLat = 41.275984;
	double mLng = -73.128605;
	
    private GoogleMap mMap;
    
    public MapFragment() {
    	Log.d(TAG, "MapFragment constructor called...");
    }
    
    public static MapFragment newInstance(){ 
    	MapFragment f = new MapFragment();
    	
    	return f;
    }

    @Override
    public void onCreate(Bundle bundle){
    	super.onCreate(bundle);
        Log.d(TAG, "onCreate()");
    	
    	setRetainInstance(true);
    }
    
    @Override
    public void onAttach(Activity activity){
    	super.onAttach(activity);
    	
    	try {
    		MapsInitializer.initialize(this.getActivity());

    	} catch (GooglePlayServicesNotAvailableException e){ 
    		e.printStackTrace();
    	}

    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
    	super.onActivityCreated(savedInstanceState);
    	
        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.

    	if(savedInstanceState != null){
            mLat = savedInstanceState.getDouble(ARG_LAT);
            mLng = savedInstanceState.getDouble(ARG_LNG);    		
    	}

    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	Log.v(TAG, "onCreateView()");
        View root = super.onCreateView(inflater, container, savedInstanceState);
    	
        mMap = getMap();
        if(mMap == null){
        	Log.v(TAG, "Map not available yet...");
        }
//        return inflater.inflate(R.layout.main, null, false);
        return root;
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState){    	
    	super.onSaveInstanceState(outState);
    	
    	outState.putDouble(ARG_LAT, mLat);
    	outState.putDouble(ARG_LNG, mLng);
    }
    
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // During startup, check if there are arguments passed to the fragment.
//        // onStart is a good place to do this because the layout has already been
//        // applied to the fragment at this point so we can safely call the method
//        // below that goes to the point on the map.
//        Bundle args = getArguments();
//        if (args != null) {
//            // Set article based on argument passed in
//            animateTo(args.getDouble(ARG_LAT), args.getDouble(ARG_LNG), false);
//        } else if (mLat != -1) {
//            // Set article based on saved instance state defined during onCreateView
//            animateTo(mLat, mLng, false);
//        }
//    }
    
    public void animateTo(double lat, double lng, boolean withZoom){

    	mLat = lat;
    	mLng = lng;
    	
    	if(mMap == null){
//    		Toast.makeText(this.getSherlockActivity(), "MAP NOT READY", Toast.LENGTH_LONG).show();
    		Log.d(TAG, "Map is not yet available...");
    	} else {
	    	if(withZoom){
	        	Log.d(TAG, "mMap animating...");
	        	LatLng mCurrentPosition = new LatLng(lat, lng);
	        	mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mCurrentPosition, 16));
	        	getMap().addMarker(new MarkerOptions().position(mCurrentPosition).icon(
	        			BitmapDescriptorFactory.fromResource(android.R.drawable.star_on)));
	//        			BitmapDescriptorFactory.fromResource(R.drawable.map_marker_green)));
	    	} else {
	        	Log.d(TAG, "mMap animating...");
	        	LatLng mCurrentPosition = new LatLng(lat, lng);
	        	mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurrentPosition, 16));
	        	getMap().addMarker(new MarkerOptions().position(mCurrentPosition).icon(
	        			BitmapDescriptorFactory.fromResource(android.R.drawable.star_on)));
	//        			BitmapDescriptorFactory.fromResource(R.drawable.map_marker_green)));
	    	}
    	}

    }
    
//    public void initMap(LatLng position){
//    	UiSettings settings = getMap().getUiSettings();
//    	settings.setAllGesturesEnabled(false);
//    	settings.setMyLocationButtonEnabled(false);
    	
//    	LatLng mPosFija = new LatLng(41.275984,-73.128605);
    	
    	
//    	getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));
//    	getMap().addMarker(new MarkerOptions().position(position).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_green)));
//    }
}