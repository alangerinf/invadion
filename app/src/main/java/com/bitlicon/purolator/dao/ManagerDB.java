package com.bitlicon.purolator.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.bitlicon.purolator.util.LogUtil.LOGW;

public class ManagerDB extends SQLiteOpenHelper {

    /**
     * Nombre de la base de datos SQLite
     */
    private static final String DATABASE_NAME = "Purolator.db";
    /**
     * Version de la base de datos SQLite
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * Manager de la base de datos estatico
     */
    private static ManagerDB helper;
    /**
     * instancia de SqliteDatabase de la aplicacion
     */
    private SQLiteDatabase db;
    /**
     * Variable que maneja el contexto de la clase que lo instancia
     */
    private Context mContext;


    private String DB_PATH = null;

    public ManagerDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        DB_PATH = "/data/data/" + mContext.getPackageName() + "/databases/";
    }

    /**
     * Funcion que obtiene la instancia del manager de la base de datos
     *
     * @param context
     * @return instancia de la base de datos
     */


    public static synchronized ManagerDB getInstance(Context context) {
        if (null == helper) {
            helper = new ManagerDB(context);
        }
        if (!helper.isDataBaseExist()) {
            try {
                helper.createDataBase();
            } catch (IOException e) {
                Log.e("DBHelperProvider", "createDataBase", e);
            }
        }
        return helper;
    }

    public synchronized SQLiteDatabase getDb() {
        if ((db == null) || (!db.isOpen()))
            db = getWritableDatabase();
        return db;
    }

    /**
     * Se ejecuta al crear la base de datos
     *
     * @param database
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        try {
            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Se ejecuta al actualizar la instancia de base de datos
     *
     * @param database   nombre de bd
     * @param oldVersion version anterior de bd
     * @param newVersion version nueva de bd
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        LOGW(ManagerDB.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS todo");
        onCreate(database);

    }

    private void copyDataBase() throws IOException {
        InputStream myInput = mContext.getAssets().open("databases/" + DATABASE_NAME);
        String outFileName = DB_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public boolean isDataBaseExist() {
        File dbFile = new File(DB_PATH + DATABASE_NAME);
        return dbFile.exists();
    }

    @Override
    protected void finalize() throws Throwable {
        if (null != helper)
            helper.close();
        if (null != db) {
            db.close();
        }
        super.finalize();
    }

    public void createDataBase() throws IOException {
        boolean isExisteDb = isDataBaseExist();
        if (!isExisteDb) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (Exception e) {
                throw new Error("Error copying database");
            }
        }
    }

    public SQLiteDatabase getDataBase() {
        return db;
    }


    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.NO_LOCALIZED_COLLATORS);
    }

    public void beginTransaction() {
        db.beginTransaction();
    }

    public void setTransactionSuccessful() {
        db.setTransactionSuccessful();
    }

    public void endTransaction() {
        db.endTransaction();
    }


}
