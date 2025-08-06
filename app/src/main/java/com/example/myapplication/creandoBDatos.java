package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class creandoBDatos{

    private Context contexto;
    private UsuariosSQLiteHelper usdbh;

    public creandoBDatos (Context contexto) throws FileNotFoundException, IOException {
        this.contexto = contexto;

        // Inicializamos la base de datos
        usdbh = new UsuariosSQLiteHelper(contexto, "DBUsuarios", null, 9);


        // Abrir la base de datos en modo escritura
        SQLiteDatabase db = usdbh.getWritableDatabase();


        // ESTO ES PARA PASAR TODO EL ARCHIVO A LA BASE DE DATOS


        File directorioPrivado = contexto.getExternalFilesDir(null);

        File archivo = new File(directorioPrivado, "Diccionario"+"6"+".txt");
          //File archivo = new File(directorioPrivado, "Diccionario1"+".txt");


        LinkedList<String> listaElementos = new LinkedList<>();
        final String[] cadenaActual = {""};

        try{
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            StringBuilder contenido = new StringBuilder();
            String linea;
            Log.i("Tag", "paso linea: ");

            while ((linea = reader.readLine()) != null) {
                Log.i("Tag", "entra al if : ");
                if (linea.trim().isEmpty()) {
                    continue; // Saltar esta iteración
                }

                //listaElementos contiene todas las lineas con texto
                listaElementos.add(linea);
                String[] partes = linea.split(";|:|=");



                //para validar si tiene la estructura correcta la linea leida

                String espaniol ="";
                String ingles="";
                if (partes.length ==2){
                    ingles=partes[0];
                    espaniol=partes[1];
                }
                else {
                    ingles=partes[0];
                    espaniol="error* ";
                }




                db = usdbh.getWritableDatabase();

                //hacemos un contenValues
                ContentValues values2 = new ContentValues();
                try {


                    values2.put("ingles",ingles);
                    values2.put("espaniol",espaniol );
                    values2.put("priori_visto",0 );
                    values2.put("priori_repaso",0 );


                    // Inserta o lanza error , depende si los valores ingresados son correctos
                    db.insertOrThrow("Palabras", null, values2);  // Usa insertOrThrow para lanzar una excepción en caso de error

                } catch (SQLiteConstraintException e) {
                    Log.e("SQLite", e.toString() + values2);
                }





            }

            reader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }


    }

}
