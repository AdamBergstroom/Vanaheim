package se.vanaheim.vanaheim.data;

import android.provider.BaseColumns;

public final class ContractAreasDB {

    private ContractAreasDB() {}

    public static final class AreaEntry implements BaseColumns {

        public final static String _ID = BaseColumns._ID;

        public final static String TABLE_NAME = "areas";

        public final static String COLUMN_AREA_NAME ="name";

        public final static String COLUMN_OBJECT_TYPE = "object";

        public final static String COLUMN_LATITUDE = "latitude";

        public final static String COLUMN_LONGITUDE = "longitude";
    }
}