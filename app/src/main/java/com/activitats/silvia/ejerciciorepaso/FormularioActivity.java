package com.activitats.silvia.ejerciciorepaso;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by Silvia on 04/11/2015.
 */
public class FormularioActivity extends Activity {
    private TextView nom, prov, loc, notif;
    private EditText nombre;
    private Spinner provincia, localidad;
    private ToggleButton notific;
    private Button envia;
    ArrayAdapter<String> adap_provincias, adap_localidades;
    String array_provincias[];

    String notifica;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        nom= (TextView)findViewById(R.id.tvNom);
        prov= (TextView)findViewById(R.id.tvProv);
        loc= (TextView)findViewById(R.id.tvLoc);
        notif=(TextView)findViewById(R.id.tvNotif);
        nombre= (EditText)findViewById(R.id.etNom);
        provincia= (Spinner)findViewById(R.id.spProv);
        localidad=(Spinner)findViewById(R.id.spLoc);
        notific= (ToggleButton)findViewById(R.id.tbNot);
        envia= (Button)findViewById(R.id.button);

        //rellenamos el spinner: creamos array de strings de provincias y recogemos la info del archivo arrays
        String array_provincias[] = getResources().getStringArray(R.array.provincias);
        //creamos adaptador de strings y le pasamos el array de provincias
        adap_provincias= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,array_provincias);

        //pasamos al spinner el adaptador que hemos creado
        provincia.setAdapter(adap_provincias);

        //añadimos un listener al spinner
        provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getId()) {
                    case R.id.spProv:
                        TypedArray arrayLocalidades = getResources().obtainTypedArray(R.array.array_provincia_a_localidades);
                        CharSequence[] localidades = arrayLocalidades.getTextArray(position);
                        arrayLocalidades.recycle();
                        // Create an ArrayAdapter using the string array and a default
                        // spinner layout
                        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(FormularioActivity.this, android.R.layout.simple_spinner_item, localidades);

                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // Apply the adapter to the spinner
                        localidad.setAdapter(adapter);

                        break;

                    case R.id.spLoc:

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //añadimos listener al botón
        envia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Llamamos al activity principal
                Intent i = getIntent();
                Bundle b = getIntent().getExtras();
                //agregamos if para el togglebutton

                if (notific.isChecked()) {
                    notifica="Sí";
                }
                else{
                    notifica="No";
                }
                i.putExtra("Nombre", nombre.getText().toString());//agregamos el nombre
                i.putExtra("Localidad", localidad.getSelectedItem().toString());//agregamos la localidad
                i.putExtra("Provincia", provincia.getSelectedItem().toString());//agragamos la provincia
                i.putExtra("Notificación", notifica);//agregamos si se desea notificación o no

                setResult(RESULT_OK, i);  //Establecemos El resultado del activity, como que tod ha ido bien
                finish();   // Indicamos que se debe cerrar el subactivity
            }
        });
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
