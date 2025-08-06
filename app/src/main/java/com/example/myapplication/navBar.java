package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class navBar extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nav_bar);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  // Establece el toolbar como el action bar

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, // checar daba error aqui
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        // Manejo de clics en NavigationView
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            if(menuItem.getItemId()==R.id.menu_settings){
                Log.i("ActionBar", "info2!");
                //Creamos el Intent
                Intent intent = new Intent(this, escogerVersion.class);
                startActivity(intent);


            }
            if(menuItem.getItemId()==R.id.menu_info){
                Log.i("ActionBar", "info 2!");
                //Creamos el Intent
                Intent intent = new Intent(this, escogerVersion.class);
                startActivity(intent);

            }


            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });






/*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

  */


    }



    //Para el boton
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    //Del lado derecho
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it i  present.
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Tuve que usar if , porque switch necesita valores conocidos en tiempo de compilacion y
         * alparecer los id no son conocidos en ese momento*/
        /*switch (item.getItemId()) {
            case R.id.menu_save:
                Log.i("ActionBar", "Nuevo!");
                return true;
            case R.id.menu_new:
                Log.i("ActionBar", "Guardar!");;
                return true;

            case R.id.menu_settings:
                Log.i("ActionBar", "Settings!");;
                return true;
            default:
                return super.onOptionsItemSelected(item);

            */
        if (item.getItemId()==R.id.menu_info){
            Log.i("ActionBar", "info!");
            // Ahora lanzamos CalendarActivity
            Intent intent = new Intent(this, escogerVersion.class);
            startActivity(intent);
            return true;

        }

        if (item.getItemId()==R.id.menu_share){
            Log.i("ActionBar", "Share!");;
            Intent intent = new Intent(this, escogerVersion.class);
            startActivity(intent);
            return true;
        }
        if( R.id.menu_settings==item.getItemId()){
            Log.i("ActionBar", "Settings!");

            return true;
        }

        if( R.id.menu_profile==item.getItemId()){
            Log.i("ActionBar", "Profile!");
            Intent intent = new Intent(this, escogerVersion.class);
            startActivity(intent);
            return true;
        }
        if( R.id.menu_modify_Plan==item.getItemId()){
            Log.i("ActionBar", "Modify Plan!");;
            Intent intent = new Intent(this, escogerVersion.class);
            startActivity(intent);
            return true;
        }

        if( R.id.menu_exit==item.getItemId()){
            Log.i("ActionBar", "Exit!");
            //Para eliminar el usuario actual
            SharedPreferences sharedPreferences = getSharedPreferences("UsuarioActual", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("usuario"); // Elimina solo el valor de "nombre"
            editor.clear(); // Limpia todas las preferencias
            editor.apply();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        if (toggle.onOptionsItemSelected(item)) {
            Log.i("ActionBar", "entra!");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }








}