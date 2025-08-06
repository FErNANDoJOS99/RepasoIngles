package com.example.myapplication;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UsuariosSQLiteHelper extends SQLiteOpenHelper {
    //Sentencia SQL para crear la tabla de Usuarios
    //String sqlCreate = "CREATE TABLE Usuarios (codigo INTEGER, nombre TEXT)";


    String sqlCreate = "CREATE TABLE IF NOT EXISTS Palabras (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " espaniol TEXT , " +
            " ingles  TEXT NOT NULL  UNIQUE, " +
            " priori_visto  INTEGER, " +
            " priori_repaso INTEGER );";



    String sqlCreate2 = "CREATE TABLE IF NOT EXISTS Datos (" +
            "nombre TEXT NOT NULL  PRIMARY KEY, " +
            "genero TEXT NOT NULL,"+
            "meta TEXT,"+
            "comentario TEXT );";


    String sqlCreate3 = "CREATE TABLE IF NOT EXISTS Dias (" +
            "nombre TEXT ," +
            "dia TEXT ,"+
            "PRIMARY KEY (nombre,dia),"+
            "FOREIGN KEY (nombre) REFERENCES Datos(nombre));";


    public UsuariosSQLiteHelper(Context contexto, String nombre,
                                SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //acitiva las llaves foraneas
        db.execSQL("PRAGMA foreign_keys = ON;");
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
        db.execSQL(sqlCreate2);
        db.execSQL(sqlCreate3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior,
                          int versionNueva) {
        Log.i("finalll", "Entro onUpgrade ");
    /*/NOTA: Por simplicidad del ejemplo aquí utilizamos directamente
    //la opción de eliminar la tabla anterior y crearla de nuevo
    //vacía con el nuevo formato.
    //Sin embargo lo normal será que haya que migrar datos de la
    //tabla antigua a la nueva, por lo que este método debería
    //ser más elaborado.
    //Se elimina la versión anterior de la tabla */
        db.execSQL("DROP TABLE IF EXISTS Palabras");
        db.execSQL("DROP TABLE IF EXISTS Datos");
        db.execSQL("DROP TABLE IF EXISTS Dias");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
        db.execSQL(sqlCreate2);
        db.execSQL(sqlCreate3);
    }
}