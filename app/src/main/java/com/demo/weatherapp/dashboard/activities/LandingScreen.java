package com.demo.weatherapp.dashboard.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.demo.weatherapp.R;
import com.demo.weatherapp.dashboard.adapters.SectionsPagerAdapter;
import com.demo.weatherapp.dashboard.loader.WeatherLoader;
import com.demo.weatherapp.model.Weather;
import com.demo.weatherapp.utils.Utils;

import static com.demo.weatherapp.dashboard.loader.WeatherLoader.URL;
import static com.demo.weatherapp.utils.LoaderIds.WEATHER;

/**
 * Weather Application 1.0
 * <p>
 *     Parent activity to hold the {@link com.demo.weatherapp.dashboard.fragments.CurrentWeatherFragment}
 *     and {@link com.demo.weatherapp.dashboard.fragments.DailyWeatherFragment} to show user its
 *     near by weather info.
 *     <p>
 *         Weather information will be loaded once the used launched the application from fresh or
 *         user presses hard refresh.
 *     </p>
 * </p>
 */
public class LandingScreen extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Weather> {


    /**
     * request code sent while asking the location access permission.
     */
    private static final int LOCATION_ACCESS = 235;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    @Nullable
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @Nullable
    private ViewPager mViewPager;

    /**
     * Tab layout to show the header of the tabs.
     */
    private TabLayout mTabs;

    /**
     * ProgressBar to show the indication of the data being loaded.
     */
    private ProgressBar mBar;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);
        mBar = (ProgressBar) findViewById(R.id.progressBar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionAndFetchLocation();
            }
        });
        checkPermissionAndFetchLocation();
    }

    /**
     * This method will check if application is having permission to access the user's location.
     *
     * <p>
     *     If yes it will invoke the call to get the location of the user.
     *     It will also check if the location it is trying to access is available or not. If yes
     *     then it will go on to load the data using {@link WeatherLoader}. If no then it will
     *     pop up a snack bar for the bottom of the sheet stating the problem
     *     while fetching the data.
     * </p>
     * <p>
     *     If there is not permission present to access the user's location, then this will ask user
     *     to grant the permission of the location. If user grants it will go on to load the data.
     *     If not then it will halt the progress. And will wait unless and until app is relaunched
     *     or user presses hard refresh.
     * </p>
     */
    private void checkPermissionAndFetchLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_ACCESS
            );
            return;
        }

        LocationManager locationManager =
                (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location location =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (location != null) {
            String url =
                    getString(R.string.base_url)
                            .concat(String.valueOf(location.getLatitude()))
                            .concat(",").concat(String.valueOf(location.getLongitude()));
            if (mBar != null) {
                mBar.setVisibility(View.VISIBLE);
            }
            Loader loader = getSupportLoaderManager().getLoader(WEATHER);
            if (loader != null && loader instanceof WeatherLoader) {
                ((WeatherLoader) loader).forceLoad(url);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString(URL, url);
                getSupportLoaderManager().initLoader(WEATHER, bundle, this);
            }
        } else {
            if (mBar != null) {
                mBar.setVisibility(View.GONE);
                Snackbar.make(mBar, "Unable to fetch location.",
                        Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_ACCESS:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        checkPermissionAndFetchLocation();
                        break;
                    }
                }

                break;
        }
    }

    @Override
    public Loader<Weather> onCreateLoader(int id, Bundle args) {
        return new WeatherLoader(getApplicationContext(), args.getString(URL));
    }

    @Override
    public void onLoadFinished(Loader<Weather> loader, @Nullable Weather data) {
        if (mViewPager == null) {
            mViewPager = (ViewPager) findViewById(R.id.container);
        }

        if (mBar.getVisibility() == View.VISIBLE) {
            mBar.setVisibility(View.GONE);
        }

        if (data != null) {
            if (mSectionsPagerAdapter == null)
            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            {
                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), data);
            }

            // Set up the ViewPager with the sections adapter.
            //noinspection ConstantConditions
            mViewPager.setAdapter(mSectionsPagerAdapter);

            if (mTabs == null) {
                mTabs = (TabLayout) findViewById(R.id.tabs);
            }

            //noinspection ConstantConditions
            mTabs.setupWithViewPager(mViewPager);

            Snackbar.make(mViewPager, "Load Success",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show();
        } else if (!Utils.isNetworkConnected(getApplicationContext())) {
            //noinspection ConstantConditions
            Snackbar.make(mBar, R.string.network_connection,
                    Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

    }

    @Override
    public void onLoaderReset(Loader<Weather> loader) {
        //DO NOTHING
    }
}
