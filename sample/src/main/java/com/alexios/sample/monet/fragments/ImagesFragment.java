package com.alexios.sample.monet.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.GridView;

import com.alexios.monet.Monet;
import com.alexios.monet.jackson.JacksonRequest;
import com.alexios.sample.monet.R;
import com.alexios.sample.monet.adapters.ImagesAdapter;
import com.alexios.sample.monet.model.ImagesResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;


public class ImagesFragment extends Fragment implements Response.Listener<ImagesResponse>, ErrorListener {

	public static final String DOWNLOADING_IMAGES = "Downloading images";

	private GridView imagesList;
	private ProgressDialog dialog;
	private ViewStub errorMessage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View returnView = inflater.inflate(R.layout.images_fragment, container, false);

		String requestedUrl = "";

		//get the requested url from the bundle
		Bundle args = getArguments();
		if (args != null && args.containsKey(CategoriesFragment.REQUESTED_URL_BUNDLE))
			requestedUrl = args.getString(CategoriesFragment.REQUESTED_URL_BUNDLE);

		//display a progress dialog while the user is waiting
		dialog = ProgressDialog.show(getActivity(), DOWNLOADING_IMAGES, CategoriesFragment.PLEASE_WAIT, true);

		imagesList = (GridView) returnView.findViewById(R.id.grid_view);
		errorMessage = ((ViewStub) returnView.findViewById(R.id.stub_import));

//		//create a jackson request
//		JacksonRequest<ImagesResponse> request = JacksonRequest.builder().setHttpMethod(Request.Method.GET)
//				.setUrl(requestedUrl)
//				.setResponseType(ImagesResponse.class)
//				.setListener(ImagesFragment.this)
//				.setErrorListener(ImagesFragment.this)
//				.build();
//
//		//add the request in the volley queue
//		Monet.INSTANCE.getRequestQueue().add(request).setTag(ImagesFragment.class.getName());

		//create a jackson request
		JacksonRequest.<ImagesResponse>builder()
				.setHttpMethod(Request.Method.GET)
				.setUrl(requestedUrl)
				.setResponseType(ImagesResponse.class)
				.setListener(ImagesFragment.this)
				.setErrorListener(ImagesFragment.this)
				.setTag(ImagesFragment.class.getName())
				.execute();

		return returnView;
	}


	//cancel the network call in case that
	//the activity will be destroyed and
	//we didn't perform the request yet
	@Override
	public void onDestroy() {
		Monet.INSTANCE.getRequestQueue().cancelAll(ImagesFragment.class.getName());
		super.onDestroy();
	}


	@Override
	public void onErrorResponse(VolleyError error) {
		dialog.dismiss();
		displayErrorMessage();
	}

	@Override
	public void onResponse(ImagesResponse response) {

		dialog.dismiss();

		if (response != null) {

			//In case that we have images pass the list
			//to the ImagesAdapter and it'll display them
			if (response.getData().size() > 0) {
				ImagesAdapter adapter = new ImagesAdapter(response.getData());
				imagesList.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			} else {
				displayErrorMessage();
			}
		} else {
			displayErrorMessage();
		}
	}

	private void displayErrorMessage() {
		errorMessage.setVisibility(View.VISIBLE);
	}

}

