package com.example.tareavistadenavegacin;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tareavistadenavegacin.modelo.Usuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tareavistadenavegacin.databinding.ActivityNavigationBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class activityNavigation extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationBinding binding;

    private TextView txtNom;
    private ImageView img;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarActivityNavigation.toolbar);
        binding.appBarActivityNavigation.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_opcion1, R.id.nav_opcion2, R.id.nav_opcion3,R.id.nav_opcion4,R.id.nav_opcion5)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_activity_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Se recogen los datos de la otra activity
        Bundle extra = getIntent().getExtras();
        Usuario usu= extra.getParcelable("usuario");

        //En un Array lis pasamos las opciones que tiene habilitado el usuario
        ArrayList<String> opciones= new ArrayList<>();
        opciones=obtenerOpciones(usu.getOpciones());
        
        //Se hace referencia al Navigation View
        NavigationView navi= (NavigationView) findViewById(R.id.nav_view);
        View hview = navi.getHeaderView(0);


        //Se vinculan los componentes
        txtNom=(TextView)hview.findViewById(R.id.txtNombreNav);
        img=(ImageView)hview.findViewById(R.id.imgViewPerfil);


        //Se cargan los elementos en el navigation View
        cargarElementosDelUsuario(txtNom,img,usu,opciones,navi);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_navigation, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_activity_navigation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private ArrayList<String> obtenerOpciones(JSONArray opciones){
        ArrayList<String> opcAux=new ArrayList<>();
        for (int i=0;i<opciones.length();i++){

            try {
                //Pasamos al objeto json las opciones a el objetojson
                JSONObject objetoJson=new JSONObject(opciones.get(i).toString());

                opcAux.add(objetoJson.getString("opt"));



            }
            catch (JSONException e){
                Toast.makeText(this,"Error al cargar los datos al objeto: "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        return opcAux;
    }

    private void cargarElementosDelUsuario(TextView nom,ImageView img,Usuario usu,ArrayList<String> opt,
                                            NavigationView navi){
        //Ubicamos el nombre
        nom.setText(usu.getNombres());

        //Cargamos la imagen
        Glide.with(this).load(usu.getImg().toString()).into(img);

        //Carga las opciones según su Rol: admin o user
        if(usu.getRol().equals("admin")){
            navi.getMenu().setGroupVisible(R.id.groupEstandar,true);
            navi.getMenu().setGroupVisible(R.id.groupAdmin,true);
        }else   {
            navi.getMenu().setGroupVisible(R.id.groupEstandar,true);
        }

    }
}