package com.tomra.stores;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;
import com.tomra.ui.R;
import com.tomra.ui.SampleListFragment;

public class StoreMapActivity extends SlidingFragmentActivity
	implements SampleListFragment.OnSlidingMenuSelectedListener{
	
	private static final String TAG = "StoreMapActivity";
	
	// We use this fragment as a pointer to the visible one, so we can hide it easily.
	private Fragment content = null;
	
	private MapFragment mMapFragment;
	private SimpleListFragment mSimpleListFragment;
//	private StoreListFragment mSimpleListFragment;
	
	final static String ARG_FRAGMENT = "fragment";
	int isMapFragmentVisible = -1;

	private static final String[] items= { "Corporate Drive, Shelton, CT"
			, "Stop N Shop, Shelton, CT"
			, "Shop Rite, Shelton, CT"
			, "Empire State Building, New York, NY"
			, "Soldier Field, Chicago, IL"
	};
	
	private static final ArrayList<String> items_list = new ArrayList();

	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		Log.v(TAG, "onCreate()");
		Log.v(TAG, "bundle " + bundle == null ? "true" : "false");
		
		setTitle("MAP ACTIVITY");
		
		setContentView(R.layout.responsive_content_frame);
		
		for(String s : items){
			items_list.add(s);
		}

		if(findViewById(R.id.menu_frame) == null){
			setBehindContentView(R.layout.menu_frame);
			getSlidingMenu().setSlidingEnabled(true);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
			// show home as up so we can toggle
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		} else {
			// add a dummy view
			View v = new View(this);
			setBehindContentView(v);
			getSlidingMenu().setSlidingEnabled(false);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		}
		
//		// set the Store List View Fragment
//		if (bundle != null)
//			content = getSupportFragmentManager().getFragment(bundle, "content");
//		if (content == null)
////			content = new MapFragment();
////			content = new SampleListFragment();
//			content = new SimpleListFragment().newInstance(items, this);
		
		if(bundle == null){
			// First launch so add the fragments
			Log.d(TAG, "Setting up Fragments...");
			setupFragments();
		} else {
			Log.d(TAG, "Bundle is Present");
			
						// Check to see if we're on not on a tablet		
			if(findViewById(R.id.mapfrag_large_land) == null) {
				mMapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(MapFragment.TAG);
				mSimpleListFragment = (SimpleListFragment) getSupportFragmentManager().findFragmentByTag(SimpleListFragment.TAG);
//				mSimpleListFragment = (StoreListFragment) getSupportFragmentManager().findFragmentByTag(StoreListFragment.TAG);

				// Check to see if the MapFragment is visible
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				
				Log.d(TAG, "ARG_FRAGMENT is " + Integer.toString(bundle.getInt(ARG_FRAGMENT)));
				
				isMapFragmentVisible = bundle.getInt(ARG_FRAGMENT);
				if(isMapFragmentVisible == 1){
					ft.hide(mSimpleListFragment);
					ft.commit();
				} else {
					ft.hide(mMapFragment);
					ft.commit();
				}
			}
			// else the layout is large enough for a map
			else {
				mMapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.store_map);
				
			}
		}
		
		// Set Behind View fragment
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new SampleListFragment())
		.commit();

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindScrollScale(0.25f);
		sm.setFadeDegree(0.25f);
		
		// if we need the width to be set manually
//		Display display = getWindowManager().getDefaultDisplay();
//		Point size = new Point();
//		display.getSize(size);
//		int width = size.x;		// screen width in pixels
//		sm.setBehindWidth((int) (width / 2.5));
	}
	
	/**
	 * This method checks if the fragments for the Store List and the MapFragment exist
	 * If they don't then it creates them and hides them to be called whenever they need to be used.
	 */
	private void setupFragments() {
		final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		// Even if activity is killed, possible that the fragment remains so find it
		// and we won't need to add it again
		mMapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(MapFragment.TAG);
		if(mMapFragment == null){
			if(findViewById(R.id.mapfrag_large_land) != null){
				Log.d(TAG, "Setting up Large Map Layout");
//				mMapFragment = new MapFragment();
				mMapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.store_map);
//				ft.add(R.id.mapfrag_large_land, mMapFragment, MapFragment.TAG);
//				ft.replace(R.id.store_map, mMapFragment, MapFragment.TAG);
//				ft.show(mMapFragment);
			} else {
				Log.d(TAG, "Setting up Small Map Layout");
//				mMapFragment = MapFragment.newInstance();
//				ft.add(R.id.store_map, mMapFragment, MapFragment.TAG);
//				ft.add(R.id.content_frame, mMapFragment, MapFragment.TAG);
				mMapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.store_map);
				ft.hide(mMapFragment);
			}
		}

		mSimpleListFragment = (SimpleListFragment) getSupportFragmentManager().findFragmentByTag(SimpleListFragment.TAG);
//		mSimpleListFragment = (StoreListFragment) getSupportFragmentManager().findFragmentByTag(StoreListFragment.TAG);
		if(mSimpleListFragment == null){
//			mSimpleListFragment = new SimpleListFragment().newInstance(items, this);
//			mSimpleListFragment.
			mSimpleListFragment = (SimpleListFragment) getSupportFragmentManager().findFragmentById(R.id.sample_list);
			mSimpleListFragment.setContents(items_list);
//			ft.add(R.id.content_frame, mSimpleListFragment, SimpleListFragment.TAG);
//			mSimpleListFragment = new StoreListFragment();
//			ft.replace(R.id.content_frame, mSimpleListFragment, StoreListFragment.TAG);
//			ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.slide_out_right);
		}
		ft.show(mSimpleListFragment);
		
		ft.commit();
		
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Save what our last fragment position was for one-pane layout
		if(findViewById(R.id.mapfrag_large_land) == null){
			if(mMapFragment.isHidden()){
				outState.putInt(ARG_FRAGMENT, 0);
			} else {
				outState.putInt(ARG_FRAGMENT, 1);
			}
		}
		
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void switchContent(final Fragment fragment) {
		content = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 50);
	}

	@Override
	public void OnFragmentSelected(int position) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "FRAGMENT IS" + position, Toast.LENGTH_SHORT).show();
		
	}

	public void onListItemClick(SimpleListFragment simpleListFragment,
			int position) {
		
		// TODO Auto-generated method stub
		Toast.makeText(this, "TESTING " + position, Toast.LENGTH_SHORT).show();
		
		double lat, lon;
		
		switch(position) {
		case 1:
			lat=41.31649;
			lon=-73.09316;
			break;
		case 2:
			lat=41.28433;
			lon=-73.11572;
			break;
		case 3:
			lat=40.74828;
			lon=-73.98557;
			break;
		case 4:
			lat=41.86228;
			lon=-87.61674;
			break;
		default:
			lat=41.275984;
			lon=-73.128605;
			break;
			
		}
		
		final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		
		if(findViewById(R.id.mapfrag_large_land) != null) {
			Log.d(TAG, "MULTI-PANE MODE..");
			// If this layout is available, we're in landscape mode ...
	        // Update the map fragment so it animates to the appropriate location
			
			if(mMapFragment != null){
				mMapFragment.animateTo(lat, lon, true);
			} else {
				Log.d(TAG, "Map Fragment is not found!");
			}
			
		} else {
			Log.d(TAG, "SINGLE PANE LAYOUT..");
			
			// If the layout is not available, we're in the one-pane layout
			// and have to hide the storeList fragment and show the mapfragment
			if(mSimpleListFragment!=null){
				ft.hide(mSimpleListFragment);
				ft.addToBackStack(null);
			}
			
//			ft.replace(R.id.content_frame, mMapFragment, MapFragment.TAG);
			ft.show(mMapFragment);
			ft.commit();			
			
			mMapFragment.animateTo(lat, lon, true);
			
		}
	}

}