package com.app.phant0m.appconmat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

//Se declaran todo lo que tenemos en el XML y algunas variables mas que se utilizaran mas adelante
    private ImageView btnDesactivar;
    private TextView tvLatitud;
    private TextView tvLongitud;
    private TextView tvPrecision;
    private TextView tvEstado;
    private ImageView btnActualizar;
private ImageView btnLimpiar;
    private LocationManager locManager;
    private LocationListener locListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Se iguala la parte grafica con el codigo
btnLimpiar = (ImageView) findViewById(R.id.BtnLimpiar);
            btnActualizar = (ImageView) findViewById(R.id.BtnActualizar);
            btnDesactivar = (ImageView) findViewById(R.id.BtnDesactivar);
            tvLatitud = (TextView) findViewById(R.id.LblPosLatitud);
            tvLongitud = (TextView) findViewById(R.id.LblPosLongitud);
            tvPrecision = (TextView) findViewById(R.id.LblPosPrecision);
            tvEstado = (TextView) findViewById(R.id.LblEstado);
//Aqui dice que si se le da click al btnActualizar se implementara el metodo comenzarLocalizacion
            btnActualizar.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    comenzarLocalizacion();
                }
            });
//Aqui dice que si se le da click en el btnDesactivar se implementara el metodo  locManager.removeUpdates(locListener);
            btnDesactivar.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    locManager.removeUpdates(locListener);
                }
            });
//Aqui dice que si se le da click al btnlimpiar se iniciara el metodo Limpiar();
        btnLimpiar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               Limpiar();
            }
        });


    }
    //Metodo para limpiar lodos los TextView
    private void Limpiar(){
     tvEstado.setText("");
        tvLatitud.setText("");
        tvLongitud.setText("");
        tvPrecision.setText("");

    }


    private void comenzarLocalizacion()
    {

        //Obtenemos una referencia al LocationManager
        locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        //Obtenemos la última posición conocida
        Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //Mostramos la última posición conocida
        mostrarPosicion(loc);

        //Aqui se reciben actualizaciones de la posición

        locListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
            }
            public void onProviderDisabled(String provider){
                tvEstado.setText("Provider OFF");
            }
            public void onProviderEnabled(String provider){
                tvEstado.setText("Provider ON ");
            }
            public void onStatusChanged(String provider, int status, Bundle extras){
                Log.i("", "Provider Status: " + status);
                tvEstado.setText("Provider Status: " + status);
            }
        };

        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 30000, 0, locListener);
    }

    //Aqui este metodo dice que si loc es diferente a null mostrara todos los datos de la localizacion
    private void mostrarPosicion(Location loc) {
        if (loc != null) {
            tvLatitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
            tvLongitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
            tvPrecision.setText("Precision: " + String.valueOf(loc.getAccuracy()));
            Log.i("", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
        } else
        //EN caso contrario la localizacion era igual a Sin datos
        {
            tvLatitud.setText("Latitud: (sin_datos)");
            tvLongitud.setText("Longitud: (sin_datos)");
            tvPrecision.setText("Precision: (sin_datos)");
        }


    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
