package ma.ensa.sqliteproject.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ma.ensa.sqliteproject.MainActivity;
import ma.ensa.sqliteproject.classes.Etudiant;
import ma.ensa.sqliteproject.util.MySQLiteHelper;

public class EtudiantService {

    private static final String TABLE_NAME ="etudiant";

    private static final String KEY_ID = "id";
    private static final String KEY_NOM = "nom";
    private static final String KEY_PRENOM ="prenom";

    private static String [] COLUMNS = {KEY_ID, KEY_NOM, KEY_PRENOM};

    private MySQLiteHelper helper;

    public EtudiantService(MainActivity context) {
        this.helper = new MySQLiteHelper(context);
    }

    public void create(Etudiant e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM, e.getNom());
        values.put(KEY_PRENOM, e.getPrenom());
        db.insert(TABLE_NAME,
                null,
                values);
        Log.d("insert", e.getNom());
        db.close();
    }

    public void update(Etudiant e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, e.getId());
        values.put(KEY_NOM, e.getNom());
        values.put(KEY_PRENOM, e.getPrenom());

        db.update(TABLE_NAME,
                values,
                "id = ?",
                new String[]{e.getId()+""});
        db.close();
    }
    public Etudiant findById(int id){
        Etudiant e = null;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c;
        c = db.query(TABLE_NAME,
                COLUMNS,
                "id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
        if(c.moveToFirst()){
            e = new Etudiant();
            e.setId(c.getInt(0));
            e.setNom(c.getString(1));
            e.setPrenom(c.getString(2));
        }
        db.close();
        return e;
    }

    public void delete(Etudiant e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        db.delete(TABLE_NAME,
                "id = ?",
                new String[]{String.valueOf(e.getId())});
        db.close();
    }

    public List<Etudiant> findAll(){
        List<Etudiant> eds = new ArrayList<>();
        String req ="select * from "+TABLE_NAME;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        Etudiant e = null;
        if(c.moveToFirst()){
            do{
                e = new Etudiant();
                e.setId(c.getInt(0));
                e.setNom(c.getString(1));
                e.setPrenom(c.getString(2));
                eds.add(e);
                Log.d("id = ", e.getId()+"");
            }while(c.moveToNext());
        }
        return eds;
    }

    }
