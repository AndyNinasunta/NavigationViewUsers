package com.example.tareavistadenavegacin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tareavistadenavegacin.modelo.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Serializable {

    private Button ingresar;
    private EditText nombreUsu, contrasena;
    private RequestQueue requestQue;
    private String url = "https://my-json-server.typicode.com/AndyBlu/Usuarios/Usuarios";
    private int pos;
    List<Usuario> listUsuario = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Conectamos todos los componentes a una variable
        ingresar = (Button) findViewById(R.id.btnIniciarSesion);
        nombreUsu = (EditText) findViewById(R.id.edtxNombre);
        contrasena = (EditText) findViewById(R.id.edtxContraseña);

        obtenerUsuarios();
    }

    //Procedimiento para Iniciar sesión
    public void iniciarSesion(View view) {

        //Si es Verdadero significa que las credenciales fueron correctas.
        if (validarUsuario(listUsuario))
        {
            //Obtenemos el usuario logeado
            Usuario unUsuario=listUsuario.get(pos);

            //Ejecutamos el NavigationActivity pasandole como parametro el objeto Usuario
            Intent it=new Intent(this,activityNavigation.class);
            it.putExtra("usuario",unUsuario);
            startActivity(it);

        }else{
            Toast.makeText(this,"Usuario y Contraseña INCORRECTOS. ",Toast.LENGTH_LONG).show();
        }

    }


    //Procedimiento para consumir servicio web con Volley
    private void obtenerUsuarios() {

        JsonArrayRequest requestJson = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        cargarUsuarios(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error al conectarse:" + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        );
        requestQue = Volley.newRequestQueue(this);
        requestQue.add(requestJson);
    }


    //Procedimiento para guardar en el objeto Usuario lo consumido en el servicio
    private List<Usuario> cargarUsuarios(JSONArray jArray) {

        for (int i=0;i<jArray.length();i++){
            try {
                //Pasamos al objeto json los valores de el primer usuario
                JSONObject objetoJson=new JSONObject(jArray.get(i).toString());

                //Agregamos al objeto Usuario los datos recogidos
                listUsuario.add(new Usuario(objetoJson.getString("nomUsuario"),
                        objetoJson.getString("nombres"),
                        objetoJson.getString("contraseña"),
                        objetoJson.getString("img"),
                        objetoJson.getJSONArray("opciones"),
                        objetoJson.getString("rol"))
                );


            }
            catch (JSONException e){
                Toast.makeText(this,"Error al cargar los datos al objeto: "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        return listUsuario;
    }


    //Procedimiento para validar que el usuario y la contraseña sean correctas
    private boolean validarUsuario(List<Usuario> listausuario){
        String auxNombreUsuario=nombreUsu.getText().toString();
        String auxContrasena=contrasena.getText().toString();
        pos=0;

        try {
            for(Usuario usu : listausuario) {
                if ((auxNombreUsuario.equals(usu.getNomUsuario().toString()))&&(auxContrasena.equals(usu.getContrasena().toString()))) {
                    return true;
                }
                pos++;
            }

        }
        catch (Exception e){
            Toast.makeText(this,"Error al validar el usuario: "+e.getMessage(),Toast.LENGTH_LONG).show();
        }

        return  false;
    }
}