package se.vanaheim.vanaheim.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AreaDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = AreaDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "areaDB";

    private static final String SQL_CREATE_AREA_TABLE =
            "CREATE TABLE " + ContractAreasDB.AreaEntry.TABLE_NAME + " ("
                    + ContractAreasDB.AreaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ContractAreasDB.AreaEntry.COLUMN_AREA_NAME + " TEXT, "
                    + ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE + " INTEGER, "
                    + ContractAreasDB.AreaEntry.COLUMN_LATITUDE + " DOUBLE, "
                    + ContractAreasDB.AreaEntry.COLUMN_LONGITUDE + " DOUBLE);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContractAreasDB.AreaEntry.TABLE_NAME;

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    public AreaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //execSQL only execute statements and does not return any (like read etc)
        db.execSQL(SQL_CREATE_AREA_TABLE);
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