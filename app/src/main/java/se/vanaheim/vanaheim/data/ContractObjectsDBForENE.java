package se.vanaheim.vanaheim.data;

import android.provider.BaseColumns;

public final class ContractObjectsDBForENE {

    private ContractObjectsDBForENE() {
    }

    public static final class ObjectEntry implements BaseColumns {

        public final static String _ID = BaseColumns._ID;

        public final static String TABLE_NAME_ENE_OBJECTS = "eneobjects";
        public final static String COLUMN_STOLP_NR = "stolpnr";
        public final static String COLUMN_OBJEKT = "objekt";
        public final static String COLUMN_HOJD_AV_KONTAKTTRAD = "hojdavkontakttrad";
        public final static String COLUMN_AVVIKKELSE_I_SIDLED = "avvikelseisidled";
        public final static String COLUMN_HOJD_AV_UTLIGGARROR = "hojdavutliggarror";
        public final static String COLUMN_UPPHOJD_AV_TILLSATSROR = "upphojdavtillsatsror";
        public final static String COLUMN_KOMMENTAR = "kommentar";
        public final static String COLUMN_COMPLETED = "fardig";
        public final static String COLUMN_EDITOR_NAME = "redigerare";
        public final static String COLUMN_LATITUDE = "latitude";
        public final static String COLUMN_LONGITUDE = "longitude";
    }
}
