# Monet
Monet is a wrapper of the Volley and the Jackson libraries. It’s 
offering a L1 memory caching by using the LRU Cache, apart from 
the L2 disk caching that Volley offers through the HTTP headers 
"Cache-Control" and "Expires". You can see the library in action 
by running the sample app.


## Instatiation

For instantiating Monet you have to either extend the MonetApplication class and add the class package into the application tag of the AndroidManifest.xml file:

```java
public class SampleApplication extends MonetApplication {

}
```

```xml
 <application
        android:name=".SampleApplication"
```

Or, to call the 	Monet.INSTANCE.init(context) method, preferably when your application is starting:

```java
import android.app.Application;
import android.content.Context;

public class YourAppApplication extends Application {
	public static MonetApplication instance;

	public void onCreate() {
		super.onCreate();

		instance = this;
		//Instantiate the volleyRequest once and only once
		// when the application is starting.
		Monet.INSTANCE.init(instance);
	}

	public static Context getContext() {
		return instance;
	}

}
```

## Example Usage

For executing a network call:

```java
JacksonRequest.<Response>builder()
				.setHttpMethod(Request.Method.GET)
				.setUrl(URL)
				.setResponseType(Response.class)
				.setListener(ImagesFragment.this)
				.setErrorListener(ImagesFragment.this)
				//optionally set a tag for this specific network call
				.setTag(ImagesFragment.class.getName())
				.execute();
				
```
Where Response is the POJO class which is using the Jackson library and will bind the response.
For receiving either the succesfull or the error response you have to implement the necessary callback methods.

```java
public class ImagesFragment extends Fragment implements Response.Listener<Response>, ErrorListener {


	public void yourMethod() {

		//create and execute a jackson request
		JacksonRequest.<Response>builder()
				.setHttpMethod(Request.Method.GET)
				.setUrl(requestedUrl)
				.setResponseType(Response.class)
				.setListener(ImagesFragment.this)
				.setErrorListener(ImagesFragment.this)
				.setTag(ImagesFragment.class.getName())
				.execute();

	}


    @Override
	public void onErrorResponse(VolleyError error) {
		
	}

	@Override
	public void onResponse(Response response) {
		// process with the response
	}
	
	

```

Where Response is the POJO class which is using the Jackson lib and will bind the response. Also, for receiving either the succesfull or the error response you have to implement the onResponse and onErrorResponse callbacks respectively.

## Features to be added soon:

1. Support of Gson library.
2. Configurable L1 disk cache.
