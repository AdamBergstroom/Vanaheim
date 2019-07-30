package se.vanaheim.vanaheim.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ObjectsDBHelperForINF extends SQLiteOpenHelper {

    public static final String LOG_TAG = ObjectsDBHelperForINF.class.getSimpleName();

    private static final String DATABASE_NAME = "objectsDBForINF";

    private static final String SQL_CREATE_INF_OBJECTS_TABLE =
            "CREATE TABLE " + ContractObjectsDBForINF.ObjectEntry.TABLE_NAME_INF_OBJECTS + " ("
                    + ContractObjectsDBForINF.ObjectEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER + " TEXT, "
                    + ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD + " TEXT, "
                    + ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING + " TEXT, "
                    + ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND + " TEXT, "
                    + ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND + " TEXT, "
                    + ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET + " TEXT, "
                    + ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER + " TEXT, "
                    + ContractObjectsDBForINF.ObjectEntry.COLUMN_EDITOR_NAME + " TEXT, "
                    + ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED + " INTEGER, "
                    + ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE + " DOUBLE, "
                    + ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE + " DOUBLE);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContractObjectsDBForINF.ObjectEntry.TABLE_NAME_INF_OBJECTS;

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    public ObjectsDBHelperForINF(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //execSQL only execute statements and does not return any (like read etc)
        db.execSQL(SQL_CREATE_INF_OBJECTS_TABLE);
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
