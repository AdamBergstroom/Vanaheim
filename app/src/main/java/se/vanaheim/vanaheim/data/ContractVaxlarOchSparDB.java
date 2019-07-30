package se.vanaheim.vanaheim.data;

import android.provider.BaseColumns;

public final class ContractVaxlarOchSparDB {

    private ContractVaxlarOchSparDB() {}

    public static final class AreaEntry implements BaseColumns {

        public final static String _ID = BaseColumns._ID;

        public final static String TABLE_NAME = "vaxlarOchSparAnteckningar";

        public final static String COLUMN_VAXLAR ="vaxlar";

        public final static String COLUMN_SPAR = "spar";

        public final static String COLUMN_KOMMENTARER = "kommentarer";

        public final static String COLUMN_LATITUDE = "latitude";

        public final static String COLUMN_LONGITUDE = "longitude";
    }
}
