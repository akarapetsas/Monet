package com.alexios.sample.monet.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alexios.monet.Monet;
import com.alexios.monet.jackson.JacksonRequest;
import com.alexios.sample.monet.R;
import com.alexios.sample.monet.adapters.CategoriesAdapter;
import com.alexios.sample.monet.model.CategoriesResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;


public class CategoriesFragment extends Fragment implements Response.Listener<CategoriesResponse>, Response.ErrorListener {

	public static final String REQUESTED_URL = "https://api.shutterstock.com/v2/images/categories";
	public static final String REQUESTED_URL_BUNDLE = "RequestedUrl";
	public static final String DOWNLOADING_CATEGORIES = "Downloading categories";
	public static final String PLEASE_WAIT = "Please wait...";

	private ListView categoriesList;
	private ProgressDialog dialog;
	private ViewStub errorMessage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View returnView = inflater.inflate(R.layout.categories_fragment, container, false);

		categoriesList = (ListView) returnView.findViewById(R.id.list_view_form);
		//display a progress dialog while the user is waiting
		dialog = ProgressDialog.show(getActivity(), DOWNLOADING_CATEGORIES, PLEASE_WAIT, true);
		errorMessage = ((ViewStub) returnView.findViewById(R.id.stub_import));

		//create a jackson request
		JacksonRequest.builder().setHttpMethod(Request.Method.GET)
				.setUrl(REQUESTED_URL)
				.setResponseType(CategoriesResponse.class)
				.setListener(CategoriesFragment.this)
				.setErrorListener(CategoriesFragment.this)
				.setHeaders(basicAuth())
				.setTag(ImagesFragment.class.getName())
				.execute();

		return returnView;
	}


	private void commitFragmentsTransaction(Integer id) {

		String requestUrl = "https://api.shutterstock.com/v2/images/search?category={id}";

		requestUrl = requestUrl.replace("{id}", Integer.toString(id));

		FragmentManager fm = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		//add the url into a bundle because we have to
		//pass it on the offers fragment
		final Bundle bundle = new Bundle();
		bundle.putString(REQUESTED_URL_BUNDLE, requestUrl);


		ImagesFragment offersFragment = new ImagesFragment();
		offersFragment.setArguments(bundle);

		ft.replace(android.R.id.content, offersFragment);

		//We use the addToBackStack method of the FragmentTransaction
		//in the backstack as we want to avoid the closing of
		//the app when the user will click on the back button
		//as the back button operates on the activity level
		ft.addToBackStack(ImagesFragment.class.getName());
		ft.commit();
	}


	@Override
	public void onErrorResponse(VolleyError error) {
		dialog.dismiss();
		errorMessage.setVisibility(View.VISIBLE);
	}

	@Override
	public void onResponse(CategoriesResponse response) {

		CategoriesAdapter mAdapter = new CategoriesAdapter(response.getData());
		categoriesList.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
		dialog.dismiss();
		categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				commitFragmentsTransaction(position);
			}
		});
	}

	@Override
	public void onDestroy() {
		Monet.INSTANCE.getRequestQueue().cancelAll(CategoriesFragment.class.getName());
		super.onDestroy();

	}

	public static Map<String, String> basicAuth(){
		HashMap<String, String> params = new HashMap<>();
		//passing the Client ID and the Client Secret

		//replace the authInfo with your client_id:client_secret
		String authInfo = "";
		String auth = "Basic " + Base64.encodeToString(authInfo.getBytes(), Base64.NO_WRAP);
		params.put("Authorization", auth);

		return params;
	}

}
