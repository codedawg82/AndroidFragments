/***
  Copyright (c) 2012 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Android Development_
    http://commonsware.com/Android
 */

package com.tomra.stores;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SimpleListFragment extends ListFragment {
  private static final String KEY_CONTENTS="contents";
  
  static final String TAG = "SimpleListFragment";
  
  final static String ARG_POSITION = "position";
  int mCurrentPosition = -1;
  
  private static Context context;

  public static SimpleListFragment newInstance(String[] contents, Context c) {
    return(newInstance(new ArrayList<String>(Arrays.asList(contents)), c));
  }

  public static SimpleListFragment newInstance(ArrayList<String> contents, Context c) {
    SimpleListFragment result=new SimpleListFragment();
    Bundle args=new Bundle();

    args.putStringArrayList(KEY_CONTENTS, contents);
    result.setArguments(args);
    
    context = c;

    return(result);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    setContents(getArguments().getStringArrayList(KEY_CONTENTS));
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
	  ((StoreMapActivity)getActivity()).onListItemClick(this, position);
  }

  void setContents(ArrayList<String> contents) {
    setListAdapter(new ArrayAdapter<String>(
                                            getActivity(),
                                            android.R.layout.simple_list_item_1,
                                            contents));
  }
  
  void setSelected(int position){
	  
  }
  
  @Override
  public void onStart() {
      super.onStart();

      // During startup, check if there are arguments passed to the fragment.
      // onStart is a good place to do this because the layout has already been
      // applied to the fragment at this point so we can safely call the method
      // below that sets the article text.
      Bundle args = getArguments();
      if (args != null) {
          // Set article based on argument passed in
          setSelected(args.getInt(ARG_POSITION));
      } else if (mCurrentPosition != -1) {
          // Set article based on saved instance state defined during onCreateView
    	  setSelected(mCurrentPosition);
      }
  }
  
  @Override
  public void onSaveInstanceState(Bundle outState) {
      super.onSaveInstanceState(outState);

      // Save the current article selection in case we need to recreate the fragment
      outState.putInt(ARG_POSITION, mCurrentPosition);
  }
  
}
