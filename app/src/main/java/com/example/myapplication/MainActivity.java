package com.example.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;



//Tengo que ver la base de datos formalmente
// Lo mas importante separar entre las 2 opciones de incio
//seperar las funciones para que pueda crecer



// Tengo que pensar en la logica que va a llevar y separar todo en metodos
//EN LA LOGICA PENSAR EN LOS RANGOS
// subirlo a github




public class MainActivity extends navBar {
    /* Para que aparezca la barra de navegacion tengo que extender de navBar
     * Ademas tengo que cambiar el setContainView por el getLayoutInflater(dsfsdfsd) y ya
     * no tengo que moverle al AndroidManifest
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getLayoutInflater().inflate(R.layout.activity_main,findViewById(R.id.container));




        try {
            creandoBDatos hola = new creandoBDatos (this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //Cambio de orden en el key y value"
        //ocupo los indices para cambiar el orden
        int [] indices ={1 , 2} ;
        final Button btnCambiar = (Button)findViewById(R.id.BtnCambiar);

        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int x = indices[0];
                int y = indices[1];
                indices[0]=y;
                indices[1]=x;

            }
        });





        //Para instanciar la base de datos
        UsuariosSQLiteHelper usdbh =
                new UsuariosSQLiteHelper(this, "DBUsuarios", null, 9);

        // Abrir la base de datos en modo escritura
        SQLiteDatabase db = usdbh.getWritableDatabase();






        // obtiene la longitud de la base de datos
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Palabras", null);
        int count =0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0); // Obtener el número de filas
            }
            cursor.close(); // Cerrar el cursor para evitar memory leaks
        }



        //hace una lista de la longitud de la base de datos
        List<Integer> lista = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            lista.add(i);
        }
        //Barajea la lista
        Collections.shuffle(lista);






        //Para imprimir todos los valores en consola

        /*
        Cursor c = db.rawQuery("SELECT * FROM Palabras", null);

        if (c.moveToFirst()) {
            do {
                String codigo1 = c.getString(0);
                String nombre1 = c.getString(1);
                String  email1= c.getString(2);


                Log.d("DB", "Ingles: " + codigo1 + ", Espaniol: " + nombre1 +", priority: " + email1);
            } while (c.moveToNext());
        }else{
            Log.d("DB", "vacio");

        }

        // Cerrar el cursor y la base de datos
        c.close();

        */



        //crea iterador y forma de usarlo
        ListIterator<Integer> iterador = lista.listIterator();
        // Recorrer la lista usando el iterador
       /* while (iterador.hasNext()) {
            int numero = iterador.next();
            System.out.println(numero); // Imprimir cada número
        }*/


        //TODO  Tengo que hacer esta variable persistente a reinicios
        final int[] restantelista = {lista.size()};





        // ADMINISTRAR LOS BOTONES



        // EL boton siguiente

        final Button btnSiguiente = (Button)findViewById(R.id.BtnSiguiente);
        //variable contador se usa para saber si ya se apreto una vez el boton
        final int[] contador = {0};
        String [] espaniol1={""};

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //si solo se ha apretado una vez el boton
                if (contador[0] < 1){

                    SQLiteDatabase db = usdbh.getWritableDatabase();

                    //borra el contenido de value anterior (por comodidad)
                    TextView textView = findViewById(R.id.TextView02_1);
                    textView.setText("");

                    textView = findViewById(R.id.TextView01_2);

                    if (iterador.hasNext()) {
                        int numero = iterador.next();
                        //System.out.println("el numero del iterador es "+ numero);
                        Cursor cursor = db.rawQuery("SELECT * FROM Palabras WHERE id = ? AND priori_visto != ?", new String [] {String.valueOf(numero),"1"});


                        // TODO  Aqui tengo que llamar la base de datos y mediante la lista actualizar el valor de las
                        //palabras ya vistas


                        if (cursor.moveToFirst()) { //requiere el moveToFirst
                            String ingles1 = cursor.getString(indices[0]);
                            espaniol1[0] = cursor.getString(indices[1]);
                            textView.setText(ingles1);
                            textView = findViewById(R.id.TextView04);
                            restantelista[0] = restantelista[0] -1;
                            textView.setText(""+ restantelista[0]);


                        }
                        else {
                            System.out.println("no va a imprimir ndad");
                        }



                        System.out.println(numero); // Imprimir cada número
                    }
                    contador[0]++;
                }else {

                        TextView textView = findViewById(R.id.TextView02_1);
                        textView.setText(espaniol1[0]);
                        contador[0]=0;

                }



            }
        });





        // para el boton de previo
        final Button btnAnterior = (Button)findViewById(R.id.BtnAnterior);

        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("Tag", "onClickSiguiente : ");

                SQLiteDatabase db = usdbh.getReadableDatabase();

                //antes que nada borra el contenido anterior
                TextView textView = findViewById(R.id.TextView02_1);
                textView.setText("");


                 textView = findViewById(R.id.TextView01_2);

                if (iterador.hasPrevious()) {
                    int numero = iterador.previous();
                    System.out.println("el numero del iterador es "+ numero);
                    Cursor cursor = db.rawQuery("SELECT * FROM Palabras WHERE id = ?", new String [] {String.valueOf(numero)});

                    if (cursor.moveToFirst()) { //requiere el moveToFirst
                        String ingles1 = cursor.getString(indices[0]);
                        //String espaniol1 = cursor.getString(2);
                        textView.setText(ingles1);
                    }
                    else {
                        System.out.println("no va a imprimir ndad");
                    }



                    System.out.println(numero); // Imprimir cada número
                }


            }
        });




        //Implementa el boton de mostrar respuesta

        final Button btnMostrar = (Button)findViewById(R.id.BtnMostrar);

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("Tag", "onClick2: ");


                TextView textView = findViewById(R.id.TextView02_1);

                String elemento =  espaniol1[0] .substring(0, 2); ;
                textView.setText(elemento);



            }
        });




        //DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD














        final Button btnArchivo = (Button)findViewById(R.id.BtnArchivo);
        String[] archivoString = {""};





        /*
        btnArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "onClick: si entro a Escoger");
                EditText archivo1 = (EditText)findViewById(R.id.Archivo);
                archivoString[0] =archivo1.getText().toString();
                Log.i("TAG", "onClick: "+archivoString[0]);



                //ESTO ES PARA ES PARA ABRIR UN ARCHIVO


                File directorioPrivado = MainActivity.this.getExternalFilesDir(null);
                Log.i("TAG", "onClickkkkk: "+archivoString[0]);
                File archivo = new File(directorioPrivado, "Diccionario"+archivoString[0]+".txt");
                //  File archivo = new File(directorioPrivado, "Diccionario1"+".txt");

                //ArrayList<String> listaElementos; // Lista dinámica
                //listaElementos = new ArrayList<>();
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

                        Log.i("Tag", "sale del if: ");

                    }

                    reader.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }



                TextView textView = findViewById(R.id.TextView04);
                textView.setText(""+listaElementos.size());


                Collections.shuffle(listaElementos);


                //Implementamos el evento "click" del botón "Siguiente"

                final Button btnHola = (Button)findViewById(R.id.BtnSiguiente);

                btnHola.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.i("Tag", "onClick1: ");


                        TextView textView = findViewById(R.id.TextView01_2);
                        cadenaActual[0] = listaElementos.removeFirst();

                        String[] partes = cadenaActual[0].split(";|:|=");

                        textView.setText( partes[indices[0]]);


                        textView = findViewById(R.id.TextView04);
                        textView.setText(""+listaElementos.size());
                        //Para borrar el contenido del boton de borrar
                        textView = findViewById(R.id.TextView02_1);
                        textView.setText("");


                    }
                });











        */





        /*
        //ESTO VALIDA QUE ESTE DISPONIBLE LA MEMORIA EXTERNA
        boolean sdDisponible = false;
        boolean sdAccesoEscritura = false;
        //Comprobamos el estado de la memoria externa (tarjeta SD)
        String estado = Environment.getExternalStorageState();
        if (estado.equals(Environment.MEDIA_MOUNTED))
        {
            sdDisponible = true;
            Log.i("ActionBar", "si montado");
            sdAccesoEscritura = true;
        }
        else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
        {
            Log.i("ActionBar", "solo lectura");
            sdDisponible = true;
            sdAccesoEscritura = false;
        }
        else
        {
            sdDisponible = false;
            sdAccesoEscritura = false;
            Log.i("ActionBar", "no esta ");
        }
        guardarEnAlmacenamientoPrivado(this);


        */






















        //Log.d("LeerArchivo", "Contenido: " + contenido.toString());












        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


/*
    public void guardarEnAlmacenamientoPrivado(Context  context) {
        // Ruta al directorio privado de la app
        File directorioPrivado = context.getExternalFilesDir(null);

        // Crea el archivo dentro del directorio
        File archivo = new File(directorioPrivado, "mi_archivo_privado.txt");

        try {
            // Escribir contenido al archivo
            FileOutputStream fos = new FileOutputStream(archivo);
            fos.write("Contenido del archivo en almacenamiento privado.".getBytes());
            fos.close();
            Log.d("GuardarArchivo", "Archivo guardado en: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


 */




}