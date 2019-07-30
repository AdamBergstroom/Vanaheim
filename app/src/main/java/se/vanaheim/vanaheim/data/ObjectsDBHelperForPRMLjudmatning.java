package se.vanaheim.vanaheim.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ObjectsDBHelperForPRMLjudmatning extends SQLiteOpenHelper {

    public static final String LOG_TAG = ObjectsDBHelperForPRMLjudmatning.class.getSimpleName();

    private static final String DATABASE_NAME = "objectsDBForPRM";

    private static final String SQL_CREATE_PRM_SOUND_MEASURE_OBJECTS_TABLE =
            "CREATE TABLE " + ContractObjectsDBForPRMLjudmatning.ObjectEntry.TABLE_NAME_PRM_LJUDMATNING_OBJECTS + " ("
                    + ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS + " TEXT, "
                    + ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_OBJEKT + " TEXT, "
                    + ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ARVARDE + " TEXT, "
                    + ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_BORVARDE + " TEXT, "
                    + ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_MEDELVARDE + " TEXT, "
                    + ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_AVVIKELSE + " TEXT, "
                    + ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ANMARKNING + " TEXT, "
                    + ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_EDITOR_NAME + " TEXT, "
                    + ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED + " INTEGER, "
                    + ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE + " DOUBLE, "
                    + ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE + " DOUBLE);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContractObjectsDBForPRMLjudmatning.ObjectEntry.TABLE_NAME_PRM_LJUDMATNING_OBJECTS;

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    public ObjectsDBHelperForPRMLjudmatning(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //execSQL only execute statements and does not return any (like read etc)
        db.execSQL(SQL_CREATE_PRM_SOUND_MEASURE_OBJECTS_TABLE);
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