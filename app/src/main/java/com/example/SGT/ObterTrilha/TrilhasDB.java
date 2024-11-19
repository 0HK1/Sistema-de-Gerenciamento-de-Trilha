package com.example.SGT.ObterTrilha;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.SGT.Waypoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TrilhasDB extends SQLiteOpenHelper {
    private static final String DATABASE = "trilha_database";
    private static final int VERSION = 2;  // Atualização da versão do banco

    public TrilhasDB(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_way_points_table =
                "CREATE TABLE waypoints(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "latitude NUMERIC NOT NULL,longitude NUMERIC NOT NULL,altitude NUMERIC NOT NULL," +
                        "data DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, velocidadeMedia REAL NOT NULL, distancia INTEGER NOT NULL);";
        db.execSQL(create_way_points_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {

            db.execSQL("ALTER TABLE waypoints ADD COLUMN data DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP");
        }
    }

    public void registrarWaypoint(Waypoint waypoint) {
        ContentValues values = new ContentValues();
        values.put("latitude", waypoint.getLatitude());
        values.put("longitude", waypoint.getLongitude());
        values.put("altitude", waypoint.getAltitude());


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = sdf.format(new Date());
        values.put("data", currentDate);  // Usar a data formatada corretamente

        values.put("velocidadeMedia", waypoint.getVelocidadeMedia());
        values.put("distancia", waypoint.getDistancia());

        SQLiteDatabase db = getWritableDatabase();
        db.insert("waypoints", null, values);
    }

    public ArrayList<Waypoint> recuperarWaypoints() {
        ArrayList<Waypoint> waypoints = new ArrayList<>();

        String[] columns = {"id", "latitude", "longitude", "altitude", "data", "velocidadeMedia", "distancia"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("waypoints", columns, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Waypoint waypoint = new Waypoint();
                waypoint.setId(cursor.getLong(0));
                waypoint.setLatitude(cursor.getDouble(1));
                waypoint.setLongitude(cursor.getDouble(2));
                waypoint.setAltitude(cursor.getDouble(3));
                waypoint.setDateString(cursor.getString(4));
                waypoint.setVelocidadeMedia(cursor.getDouble(5));
                waypoint.setDistancia(cursor.getDouble(6));

                waypoints.add(waypoint);
            }
            cursor.close();
        }
        return waypoints;
    }

    public void apagaTrilha() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM waypoints");
    }
}
