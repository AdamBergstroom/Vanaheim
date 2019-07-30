package se.vanaheim.vanaheim.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PropertyListDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = PropertyListDBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "propertyListDB";

    private static final String SQL_CREATE_PROPERTY_LIST_TABLE =
            "CREATE TABLE "
                    + ContractPropertyListDB.PropertyListEntry.TABLE_NAME_PROPERTY_LIST + " ("
                    + ContractPropertyListDB.PropertyListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                    //************ 4.2.1.1 Parkeringsmöjligheter för funktionshindrade *************1
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_1_1 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_1_1_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_1_2 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_1_2_EXtRA_OBJECtS + " TEXT, "

                    //************ 4.2.1.2 Hinderfri gångväg*************2
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_2_1 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_2_1_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_2_2 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_2_2_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_2_3 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_2_3_EXTRA_OBJECTS + " TEXT, "

                    //************ 4.2.1.2.1 Horisontell förflyttning************3
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_3_1 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_3_1_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_3_2 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_3_2_EXTRA_OBJECTS + " TEXT, "

                    //************ 4.2.1.2.2 Vertikal förflyttning************4
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_4_1 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_4_1_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_4_2 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_4_2_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_4_3 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_4_3_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_4_4 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_4_4_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_4_5 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_4_5_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_4_6 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_4_6_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_4_7 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_4_7_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_4_8 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_4_8_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_4_9 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_4_9_EXTRA_OBJECTS + " TEXT, "

                    //************ 4.2.1.2.3 Gångvägsmarkering************5
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_5_1 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_5_1_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_5_2 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_5_2_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_5_3 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_5_3_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_5_4 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_5_4_EXTRA_OBJECTS + " TEXT, "

                    //************ 4.2.1.4 Golvytor************6
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_6_1 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_6_1_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_6_2 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_6_2_EXTRA_OBJECTS + " TEXT, "

                    //************ 4.2.1.5 Markering av genomskinliga hinder************7
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_7_1 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_7_1_EXTRA_OBJECTS + " TEXT, "

                    //************ 4.2.1.6 Toaletter och skötplatser************8
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_8_1 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_8_1_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_8_2 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_8_2_EXTRA_OBJECTS + " TEXT, "

                    //************ 4.2.1.7 Inredning och fristående enheter************9
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_9_1 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_9_1_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_9_2 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_9_2_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_9_3 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_9_3_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_9_4 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_9_4_EXTRA_OBJECTS + " TEXT, "

                    //************4.2.1.9 Belysning************10
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_10_1 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_10_1_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_10_2 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_10_2_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_10_3 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_10_3_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_10_4 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_10_4_EXTRA_OBJECTS + " TEXT, "

                    //************4.2.1.10 Visuell information************11
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_11_1 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_11_1_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_11_2 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_11_2_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_11_3 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_11_3_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_11_4 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_11_4_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_11_5 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_11_5_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_11_6 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_11_6_EXTRA_OBJECTS + " TEXT, "

                    //************4.2.1.11 Talad information Sidoplattform***********12
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_12_1 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_12_1_EXTRA_OBJECTS + " TEXT, "

                    //************4.2.1.12 Plattformsbredd och plattformskant************13
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_13_1 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_13_1_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_13_2 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_13_2_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_13_3 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_13_3_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_13_4 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_13_4_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_13_5 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_13_5_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_13_6 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_13_6_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_13_7 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_13_7_EXTRA_OBJECTS + " TEXT, "

                    //************4.2.1.13 Plattformens slut************14
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_14_1 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_14_1_EXTRA_OBJECTS + " TEXT, "

                    //************4.2.1.15 Spårkorsning för passagerare påväg till plattformar************15
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_15_1 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_15_1_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_15_2 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_15_2_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_15_3 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_15_3_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_15_4 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_15_4_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_15_5 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_15_5_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_15_6 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_15_6_EXTRA_OBJECTS + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_15_7 + " TEXT, "
                    + ContractPropertyListDB.PropertyListEntry.COLUMN_15_7_EXTRA_OBJECTS + " TEXT);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContractPropertyListDB.PropertyListEntry.TABLE_NAME_PROPERTY_LIST;

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    public PropertyListDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //execSQL only execute statements and does not return any (like read etc)
        db.execSQL(SQL_CREATE_PROPERTY_LIST_TABLE);
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