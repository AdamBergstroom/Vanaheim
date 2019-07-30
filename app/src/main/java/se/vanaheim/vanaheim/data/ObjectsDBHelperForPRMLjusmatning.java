package se.vanaheim.vanaheim.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ObjectsDBHelperForPRMLjusmatning extends SQLiteOpenHelper {

    public static final String LOG_TAG = ObjectsDBHelperForPRMLjusmatning.class.getSimpleName();

    private static final String DATABASE_NAME = "objectsDBForPRMLjusmatning";

    private static final String SQL_CREATE_PRM_SOUND_MEASURE_OBJECTS_TABLE =
            "CREATE TABLE " + ContractObjectsDBForPRMLjusmatning.ObjectEntry.TABLE_NAME_PRM_LJUSMATNING_OBJECTS +
                    " ("
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN + " TEXT, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW + " INTEGER, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE + " TEXT, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1 + " TEXT, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2 + " TEXT, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3 + " TEXT, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4 + " TEXT, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5 + " TEXT, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6 + " TEXT, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7 + " TEXT, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8 + " TEXT, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9 + " TEXT, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10 + " TEXT, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11 + " TEXT, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12 + " TEXT, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13 + " TEXT, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14 + " TEXT, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED + " INTEGER, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START + " TEXT, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE + " DOUBLE, "
                    + ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE + " DOUBLE);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContractObjectsDBForPRMLjusmatning.ObjectEntry.TABLE_NAME_PRM_LJUSMATNING_OBJECTS;

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    public ObjectsDBHelperForPRMLjusmatning(Context context) {
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
        onUpgrade(db, oldVersion, newVersion);
    }
}