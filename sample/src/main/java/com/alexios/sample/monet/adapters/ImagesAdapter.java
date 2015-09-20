package com.alexios.sample.monet.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexios.monet.cache.ImageCacheManager;
import com.alexios.sample.monet.R;
import com.alexios.sample.monet.SampleApplication;
import com.alexios.sample.monet.model.Image;
import com.android.volley.toolbox.ImageLoader;

import java.util.List;


public class ImagesAdapter extends BaseAdapter {

	private List<Image> offers;

	public ImagesAdapter(List<Image> offers) {
		this.offers = offers;
	}

	@Override
	public int getCount() {
		return offers.size();
	}

	@Override
	public Object getItem(int position) {
		return offers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
		ImageLoader.ImageContainer imageRequest;
		ImageView imageView;
		TextView txtTeaser;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		LayoutInflater mInflater = (LayoutInflater) SampleApplication.getContext().
				getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_item, parent, false);
			holder = new ViewHolder();
			holder.txtTeaser = (TextView) convertView.findViewById(R.id.image_list_row_title);
			holder.imageView = (ImageView) convertView.findViewById(R.id.image_list_row_icon);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Image rowItem = (Image) getItem(position);

		holder.txtTeaser.setText(rowItem.getDescription());

		String url = rowItem.getAssets().getPreview().getUrl();

		if (holder.imageRequest != null) {
			holder.imageRequest.cancelRequest();
		}

		holder.imageRequest = ImageCacheManager
				.INSTANCE.getImageLoader()
				.get(url, ImageLoader.getImageListener(holder.imageView,
						R.mipmap.no_image, R.mipmap.error_image));

		return convertView;
	}

}
