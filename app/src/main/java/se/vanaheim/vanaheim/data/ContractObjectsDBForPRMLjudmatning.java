package se.vanaheim.vanaheim.data;

import android.provider.BaseColumns;

public final class ContractObjectsDBForPRMLjudmatning {

    private ContractObjectsDBForPRMLjudmatning() {}

    public static final class ObjectEntry implements BaseColumns{

        public final static String _ID = BaseColumns._ID;

        public final static String TABLE_NAME_PRM_LJUDMATNING_OBJECTS = "prmljudmatningObjects";
        public final static String COLUMN_PLATS = "plats";
        public final static String COLUMN_OBJEKT = "objekt";
        public final static String COLUMN_ARVARDE = "arvarde";
        public final static String COLUMN_BORVARDE = "borvarde";
        public final static String COLUMN_MEDELVARDE = "medelvarde";
        public final static String COLUMN_AVVIKELSE = "avvikelse";
        public final static String COLUMN_ANMARKNING = "anmarkning";
        public final static String COLUMN_COMPLETED = "fardig";
        public final static String COLUMN_EDITOR_NAME = "redigerare";
        public final static String COLUMN_LATITUDE = "latitude";
        public final static String COLUMN_LONGITUDE = "longitude";
    }

}
