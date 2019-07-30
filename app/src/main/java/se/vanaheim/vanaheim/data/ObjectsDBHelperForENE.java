package se.vanaheim.vanaheim.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ObjectsDBHelperForENE extends SQLiteOpenHelper {

    public static final String LOG_TAG = ObjectsDBHelperForENE.class.getSimpleName();

    private static final String DATABASE_NAME = "objectsDBForENE";

    private static final String SQL_CREATE_ENE_OBJECTS_TABLE =
            "CREATE TABLE " + ContractObjectsDBForENE.ObjectEntry.TABLE_NAME_ENE_OBJECTS + " ("
                    + ContractObjectsDBForENE.ObjectEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR + " TEXT, "
                    + ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT + " TEXT, "
                    + ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD + " TEXT, "
                    + ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED + " TEXT, "
                    + ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR + " TEXT, "
                    + ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR + " TEXT, "
                    + ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR + " TEXT, "
                    + ContractObjectsDBForENE.ObjectEntry.COLUMN_EDITOR_NAME + " TEXT, "
                    + ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED + " INTEGER, "
                    + ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE + " DOUBLE, "
                    + ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE + " DOUBLE);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContractObjectsDBForENE.ObjectEntry.TABLE_NAME_ENE_OBJECTS;

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    public ObjectsDBHelperForENE(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //execSQL only execute statements and does not return any (like read etc)
        db.execSQL(SQL_CREATE_ENE_OBJECTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }
}
