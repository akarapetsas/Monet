package com.alexios.monet.jackson;

import android.support.annotation.NonNull;

import com.alexios.monet.Monet;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonRequest;

import java.util.Map;

import static com.android.volley.toolbox.HttpHeaderParser.parseCacheHeaders;
import static com.android.volley.toolbox.HttpHeaderParser.parseCharset;

public class JacksonRequest<T> extends JsonRequest<T> {

	private final Class<T> responseType;
	private static Map<String, String> requestHeaders;

	public static <T> Builder builder() {
		return new <T>Builder();
	}

	/**
	 * Creates a new request.
	 *
	 * @param method        the HTTP method to use
	 * @param url           URL to fetch the JSON from
	 * @param requestBody   A {@link Object} to post and convert into json as the request.
	 *                      Null is allowed and indicates no parameters will be posted along with request.
	 * @param responseType  Expected class type for the response. Used by jackson for serialization.
	 * @param listener      Listener to receive the JSON response
	 * @param errorListener Error listener, or null to ignore errors.
	 */
	private JacksonRequest(int method, String url, Object requestBody, Class<T> responseType,
						   Listener<T> listener, ErrorListener errorListener) {
		super(method, url, (requestBody == null) ? null : Mapper.string(requestBody), listener, errorListener);
		this.responseType = responseType;
	}


	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data, parseCharset(response.headers));

			return Response.success(Mapper.objectOrThrow(jsonString, responseType),
					parseCacheHeaders(response));

		} catch (Exception e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		if (requestHeaders != null) {
			return requestHeaders;
		}
		return super.getHeaders();
	}

	public static class Builder<T> {

		private Integer method;
		private String url;
		private Object requestBody;
		private Class<T> responseType;
		private Response.Listener<T> listener;
		private Response.ErrorListener errorListener;
		private Object tag;
		private RetryPolicy retryPolicy;
		private String markerTag;

		public Builder setHttpMethod(int method) {
			this.method = method;
			return this;
		}

		public Builder setUrl(String url) {
			this.url = url;
			return this;
		}

		public Builder setRequestBody(Object requestBody) {
			this.requestBody = requestBody;
			return this;
		}

		public Builder<T> setResponseType(Class<T> responseType) {
			this.responseType = responseType;
			return this;
		}

		public Builder setListener(Response.Listener<T> listener) {
			this.listener = listener;
			return this;
		}

		public Builder setErrorListener(Response.ErrorListener errorListener) {
			this.errorListener = errorListener;
			return this;
		}

		public Builder setTag(Object tag) {
			this.tag = tag;
			return this;
		}

		public Builder setRetryPolicy(RetryPolicy retryPolicy) {
			this.retryPolicy = retryPolicy;
			return this;
		}

		public Builder setHeaders(Map<String, String> mapHeaders) {
			requestHeaders = mapHeaders;
			return this;
		}

		public Builder addMarker(String tag) {
			markerTag = tag;
			return this;
		}

		public JacksonRequest build() {
			return getJacksonRequest();
		}

		public void execute() {
			Monet.INSTANCE.getRequestQueue().add(getJacksonRequest());
		}

		@NonNull
		private JacksonRequest getJacksonRequest() {
			//based on the parameters passed use the appropriate constructor
			assert method != null;
			assert url != null;
			assert responseType != null;
			assert listener != null;
			assert errorListener != null;

			JacksonRequest jacksonRequest = new JacksonRequest<>(this.method, this.url, this.requestBody,
					this.responseType, this.listener, this.errorListener);

			if (tag != null)
				jacksonRequest.setTag(tag);

			if (retryPolicy != null)
				jacksonRequest.setRetryPolicy(retryPolicy);

			if (markerTag != null)
				jacksonRequest.addMarker(markerTag);

			return jacksonRequest;
		}
	}
}