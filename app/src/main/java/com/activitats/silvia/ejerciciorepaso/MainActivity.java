package com.activitats.silvia.ejerciciorepaso;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
/**
 * Created by Silvia on 04/11/2015.
 */
public class MainActivity extends Activity {
    Button pulsa;
    int pulsaciones=0;
    TextView numpuls;
    TextView mensaje;
    final int FORMULARIOACTIVITY_1=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pulsa= (Button) findViewById(R.id.bPulsa);
        numpuls= (TextView) findViewById(R.id.tvNumPuls);
        mensaje=(TextView)findViewById(R.id.tvNotif);

        //creamos listener para el botón
        pulsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pulsaciones++;
                numpuls.setText("" + pulsaciones);

                //cremos notificaciones para cada click
                Notification.Builder ncb = new Notification.Builder(getApplicationContext());
                ncb.setContentTitle("Pulsaciones");
                ncb.setContentText(" " + pulsaciones);
                ncb.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                ncb.setSmallIcon(R.mipmap.ic_launcher);
                NotificationManager nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nm.notify(pulsaciones,ncb.build());
                //cuando las pulsaciones lleguen a 5 se lanzará el siguiente activity
                if(pulsaciones==5){
                    Intent i = new Intent(getApplicationContext(),FormularioActivity.class);
                    Bundle b = new Bundle();
                    b.putString("Puls",String.valueOf(pulsaciones));  // Agregamos nº de pulsaciones
                    i.putExtras(b);
                    startActivityForResult(i, FORMULARIOACTIVITY_1);

                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case FORMULARIOACTIVITY_1:
                gestionaFormularioActivity(resultCode, data);
                break;
        }
    }

    public void gestionaFormularioActivity(int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            //Recogemos datos que vienen del formulario
            Bundle b = data.getExtras();
            //Creamos variables para asignar los datos recibidos en el bundle del formularioactivity
            String n = b.getString("Nombre");
            String l = b.getString("Localidad");
            String p = b.getString("Provincia");
            String quierenot=b.getString("Notificación");

            //dependiendo de si se ha ecogido o no notificación
                if (quierenot.toString().equals("Sí")) {
                   //cremos notificación
                    Notification.Builder ncb = new Notification.Builder(getApplicationContext());
                    ncb.setContentTitle("Notificación");
                    ncb.setContentText("¿Quieres reiniciar la aplicación?");
                    ncb.setSmallIcon(R.mipmap.ic_launcher);

                    //Definimos objeto InboxStyle (notificación expandida)
                    Notification.InboxStyle iS = new Notification.InboxStyle();

                    //Agregamos las líneas de texto que mostrará cuando se expanda la notificación
                    iS.addLine(n);
                    iS.addLine("de "+l);
                    iS.addLine("("+p+")");
                    iS.addLine("ha pulsado " + pulsaciones + " veces.");

                    //Agregamos a la notificación el InboxStyle
                    ncb.setStyle(iS);

                    //creamos dos acciones a ejecutar desde la notificación
                    Intent iC = new Intent();
                    PendingIntent pi1 = PendingIntent.getActivity(this, 0, iC, PendingIntent.FLAG_UPDATE_CURRENT);
                    ncb.addAction(R.mipmap.ic_launcher, "CONTINUAR", pi1);

                    Intent iR = new Intent(getApplicationContext(),MainActivity.class);
                    PendingIntent pi2 = PendingIntent.getActivity(this, 0, iR, PendingIntent.FLAG_UPDATE_CURRENT);
                    ncb.addAction(R.mipmap.ic_launcher,"REINICIAR",pi2);

                    //agregamos las dos acciones a la notificación expandida
                    ncb.setContentIntent(pi1);
                    ncb.setContentIntent(pi2);

                    //Lanzamos la notificación
                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(2, ncb.build());

                }else{
                    //lanzamos toast con con información recogida en el formularioactivity
                    Toast.makeText(getApplicationContext(),n.toString() + " de " + l.toString() + " (" + p.toString() + ") ha pulsado " + pulsaciones + " veces.",Toast.LENGTH_LONG).show();

                }
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
