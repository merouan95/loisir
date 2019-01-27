package com.merouan.loisir;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    public ImageView img;
    // Constants:
    final int REQUEST_CODE=123;
    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    // id fourni par le site openweathermap
    final String APP_ID = "6fd4c2b94f4450443c4fef8d2c554760";
    // location est mise a jour chaque 5 seconds)
    final long MIN_TIME = 5000;
    // location est mise a jour chaque 1km)
    final float MIN_DISTANCE = 1000;
    // fournir location
    String LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;
 //Obtenir la localisation de l'utilisateur sur Android fonctionne par rappel.
 // Vous indiquez que vous souhaitez recevoir les mises à jour
 // de position de l’ LocationManager appel en l’appelant
 // requestLocationUpdates(), en le transmettant a LocationListener.
 // Vous LocationListenerdevez implémenter plusieurs méthodes de rappel
 // que les LocationManager appels appellent lorsque l'emplacement de l'utilisateur
 // change ou lorsque l'état du service change.
    LocationManager mLocationManager;
    LocationListener mLocationListener;
    TextView mCityLabel;
    ImageView mWeatherImage;
    TextView mTemperatureLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //conecter au layout correspondant
        setContentView(R.layout.activity_main);
        //creer correspondance entre les idfferents components of meteo
        mCityLabel = (TextView) findViewById(R.id.locationTV);
        mWeatherImage = (ImageView) findViewById(R.id.weatherSymbolIV);
        mTemperatureLabel = (TextView) findViewById(R.id.tempTV);
        // imporation du gif
        img=findViewById(R.id.imageView);
        Glide.with(this).load("https://media.giphy.com/media/AtF34Gz1m2BuU/giphy.gif").into(img);

        //declaration de bottom navigation bar
        BottomNavigationView b = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        b.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.onglet1:
                                Intent it1= new Intent(MainActivity.this,math.class);
                                startActivity(it1);
                                break;
                            case R.id.onglet2:
                                Intent it2= new Intent(MainActivity.this,Meteo.class);
                                startActivity(it2);
                                break;

                           
                        }
                        return false;
                    }
    })

    ;}
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Clima", "onResume() est démaré");

        Log.d("Clima", "on va essayer de recuperer la météo de la localisation actuelle");
            getWeatherForCurrentLocation();


    }
    public void getWeatherForCurrentLocation(){
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //location est changer on vas prendre variable location fourni par la fonction
                //et retirer notre longitute et latitude
                Log.d("Clima", "location est changé");
                String longitude =String.valueOf(location.getLongitude());
                String latitude=String.valueOf(location.getLatitude());

                Log.d("Clima","longitude est"+longitude);
                Log.d("Clima","latitude est démaré"+latitude);
                //on utilise librarie de james
                RequestParams params=new RequestParams();
                params.put("lat",latitude);
                params.put("lon",longitude);
                params.put("appid",APP_ID);
                letsDoSomeNetworking(params);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Clima", "gps est désactivé");
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        //pour faire des mis ajour frequente pour notre position
        //premier parametre fourni notre position
        //2et3 paramettre fournissent temp et distance necessaire pour fair mis a jour
        //4eme parametre fournit componnent qui va entendre les changements
        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                Log.d("Clima","onrequestionPermission() est démaré");
                getWeatherForCurrentLocation();
            } else {
                Log.d("Clima","permission  est réfusé");
            }
        }
    }
    private  void letsDoSomeNetworking(RequestParams params){
        AsyncHttpClient client=new AsyncHttpClient();
        //on utilise cette variable pour fournir requette http
        // param 1 le site ou on va trouver nos info
        //param2 inclut longitude et latitude et id
        //param 3 va recupere reponse et 2cas succes ou echec
        client.get(WEATHER_URL,params,new JsonHttpResponseHandler(){
            //en cas de succes
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Log.d("clima","succes et json"+response.toString());
               WeatherDataModel weatherData = WeatherDataModel.fromJson(response);
                updateUI(weatherData);
            }
            // en cas d echec
            @Override
            public void onFailure(int statusCode,Header[] headers,Throwable e,JSONObject response)
            { Log.e("clima","echec"+e.toString());
                Log.d("clima","statu du code "+statusCode);
                Toast.makeText(MainActivity.this, "echec de la requete ", Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void updateUI(WeatherDataModel weather){
        mTemperatureLabel.setText(weather.getTemperature());
        mCityLabel.setText(weather.getCity());
        int resourceID=getResources().getIdentifier(weather.getIconName(),"drawable",getPackageName());
        mWeatherImage.setImageResource(resourceID);
    }
}

