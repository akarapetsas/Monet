# Monet
Monet is a wrapper of the Volley and the Jackson libraries. It’s 
offering a L1 memory caching by using the LRU Cache, apart from 
the L2 disk caching that Volley offers through the HTTP headers 
"Cache-Control" and "Expires". You can see the library in action 
by running the sample app.


## Instatiation

Include the following in your gradle script:

```java
public class SampleApplication extends MonetApplication {

}


```xml
dependencies {
    compile 'com.klinkerapps:sliding-activity:1.1.1'
}
```


and resync the project.

## Example Usage

Sliding activities are very easy to implement. Here is a simple example:

```java
public class NormalActivity extends SlidingActivity {

    @Override
    public void init(Bundle savedInstanceState) {
        setTitle("Activity Title");

        setPrimaryColors(
                getResources().getColor(R.color.primary_color),
                getResources().getColor(R.color.primary_color_dark)
        );

        setContent(R.layout.activity_content);
    }
}
```

This will create an activity with the given title, the primary colors, and whatever is included in the activity_content layout.

You also need to reference the activity into your AndroidManifest.xml:

```xml
<activity
    android:name=".NormalActivity"
    android:excludeFromRecents="true"
    android:taskAffinity=""
    android:theme="@style/Theme.Sliding.Light"/>

Features to be added soon:

1. Support of Gson library.
2. Configurable L1 disk cache.
