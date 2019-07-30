package se.vanaheim.vanaheim.data;

import android.provider.BaseColumns;

public final class ContractObjectsDBForINF {

    private ContractObjectsDBForINF() {
    }

    public static final class ObjectEntry implements BaseColumns {

        public final static String _ID = BaseColumns._ID;

        public final static String TABLE_NAME_INF_OBJECTS = "infobjects";
        public final static String COLUMN_KM_NUMMER = "kmtal";
        public final static String COLUMN_SPARVIDD = "sparvidd";
        public final static String COLUMN_RALSFORHOJNING = "ralsforhojning";
        public final static String COLUMN_SLIPERSAVSTAND = "slipersavstand";
        public final static String COLUMN_SPARAVSTAND = "sparavstand";
        public final static String COLUMN_FRIA_RUMMET = "friaRummet";
        public final static String COLUMN_KOMMENTARER = "kommentar";
        public final static String COLUMN_COMPLETED = "fardig";
        public final static String COLUMN_EDITOR_NAME = "redigerare";
        public final static String COLUMN_LATITUDE = "latitude";
        public final static String COLUMN_LONGITUDE = "longitude";

    }
}