package com.cvamedios.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loopj.android.http.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.mime.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import devazt.devazt.networking.HttpClient;
import devazt.devazt.networking.OnHttpRequestComplete;
import devazt.devazt.networking.Response;

public class MainActivity extends AppCompatActivity{

    EditText nombre, apellido, usuario, contraseña;
    Button registar;


    //lonque deberia hacer aca es crear un objeto json y obtener el usuario y la xcontraseña "dependiendo como este el objeto en erl servidor"
    // y una ves hecho esto generar una url que se conecte al servido y pasarle el usuario y una contraseña.
    // Eso lo hacemos con un httpCliente haciendo peticiones al servidor.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = (EditText)findViewById(R.id.editTextNombre);
        apellido = (EditText)findViewById(R.id.editTextApellido);
        usuario =(EditText)findViewById(R.id.editTextUsuario);
        contraseña = (EditText)findViewById(R.id.editTextContraseña);

        registar= (Button)findViewById(R.id.btnRegistrar);
        registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newUsuario();
                try {
                    generarPeticionLogin(usuario.toString(),contraseña.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void newUsuario(){
        usuarios u = new usuarios();
        u.setMombre(nombre.toString());
        u.setApellido(apellido.toString());
        u.setUser(usuario.toString());
        u.setPassword(contraseña.toString());


    }

    public void generarPeticionLogin(String usuario, String contraseña) throws JSONException, UnsupportedEncodingException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("usuario",usuario);
        // acordarse que el primer parametro tiene que ser igual al del objeto json que se encuentra en el servidor
        jsonObject.put("contraseña",contraseña);
        ByteArrayEntity oEntity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
        oEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        AsyncHttpClient oHttpClient = new AsyncHttpClient();

        RequestHandle requestHandle = oHttpClient.post(getApplicationContext(),
                "http://www.desarrollohidrocalido.com/ejemplos/Login-AngularJS-PHP-PDO/model/index.php",
                (HttpEntity) oEntity, "application/json" ,new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
            }
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                System.out.println(statusCode);
                try {
                    String content = new String(responseBody, "UTF-8");
                    JSONObject obj = new JSONObject(content);
                    Toast.makeText(getApplicationContext(), obj.getString("usuario"), Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable error) {
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "404 !", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "500 !", Toast.LENGTH_LONG).show();
                } else if (statusCode == 403) {
                    Toast.makeText(getApplicationContext(), "403 !", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
                System.out.println(retryNo);
            }
        });

    }


}
