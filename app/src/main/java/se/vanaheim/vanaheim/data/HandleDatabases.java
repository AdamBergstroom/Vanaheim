package se.vanaheim.vanaheim.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import se.vanaheim.vanaheim.models.Area;
import se.vanaheim.vanaheim.models.Object;
import se.vanaheim.vanaheim.models.PropertyListObjects;

public class HandleDatabases {

    private AreaDbHelper areaDbHelper;
    private ObjectsDBHelperForINF objectsDBHelperForINF;
    private ObjectsDBHelperForENE objectsDBHelperForENE;
    private ObjectsDBHelperForPRMLjudmatning objectsDBHelperForPRMLjudmatning;
    private ObjectsDBHelperForPRMLjusmatning objectsDBHelperForPRMLjusmatning;
    private PropertyListDBHelper propertyListDBHelper;
    private VaxlarOchSparDbHelper vaxlarOchSparDbHelper;

    private SQLiteDatabase databaseForAreas;
    private SQLiteDatabase databaseForINF;
    private SQLiteDatabase databaseForENE;
    private SQLiteDatabase databaseForPRMLjudmatning;
    private SQLiteDatabase databaseForPRMLjusmatning;
    private SQLiteDatabase databaseForPropertyList;
    private SQLiteDatabase databaseVaxlarOchSpar;


    public HandleDatabases(Context c) {
        areaDbHelper = new AreaDbHelper(c);
        databaseForAreas = areaDbHelper.getWritableDatabase();
        databaseForAreas = areaDbHelper.getReadableDatabase();

        objectsDBHelperForINF = new ObjectsDBHelperForINF(c);
        databaseForINF = objectsDBHelperForINF.getWritableDatabase();
        databaseForINF = objectsDBHelperForINF.getReadableDatabase();

        objectsDBHelperForENE = new ObjectsDBHelperForENE(c);
        databaseForENE = objectsDBHelperForENE.getWritableDatabase();
        databaseForENE = objectsDBHelperForENE.getReadableDatabase();

        objectsDBHelperForPRMLjudmatning = new ObjectsDBHelperForPRMLjudmatning(c);
        databaseForPRMLjudmatning = objectsDBHelperForPRMLjudmatning.getWritableDatabase();
        databaseForPRMLjudmatning = objectsDBHelperForPRMLjudmatning.getReadableDatabase();

        objectsDBHelperForPRMLjusmatning = new ObjectsDBHelperForPRMLjusmatning(c);
        databaseForPRMLjusmatning = objectsDBHelperForPRMLjusmatning.getWritableDatabase();
        databaseForPRMLjusmatning = objectsDBHelperForPRMLjusmatning.getReadableDatabase();

        propertyListDBHelper = new PropertyListDBHelper(c);
        databaseForPropertyList = propertyListDBHelper.getWritableDatabase();
        databaseForPropertyList = propertyListDBHelper.getReadableDatabase();

        vaxlarOchSparDbHelper = new VaxlarOchSparDbHelper(c);
        databaseVaxlarOchSpar = vaxlarOchSparDbHelper.getWritableDatabase();
        databaseVaxlarOchSpar = vaxlarOchSparDbHelper.getReadableDatabase();
    }

    //**************** Area markers ******************************

    //Save Area kommer sen. Ligger kvar i Main activity.

    public void saveAreaMarker(String projectName, int objectType, double latitude, double longitude) {
        ContentValues values = new ContentValues();
        values.put(ContractAreasDB.AreaEntry.COLUMN_AREA_NAME, projectName);
        values.put(ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE, objectType);
        values.put(ContractAreasDB.AreaEntry.COLUMN_LATITUDE, latitude);
        values.put(ContractAreasDB.AreaEntry.COLUMN_LONGITUDE, longitude);

        databaseForAreas.insert(ContractAreasDB.AreaEntry.TABLE_NAME, null, values);
    }

    public ArrayList<Area> recoverAreaMarkers(int objectType) {

        ArrayList<Area> areaArrayList = new ArrayList<>();

        String[] projection = {
                ContractAreasDB.AreaEntry._ID,
                ContractAreasDB.AreaEntry.COLUMN_AREA_NAME,
                ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE,
                ContractAreasDB.AreaEntry.COLUMN_LATITUDE,
                ContractAreasDB.AreaEntry.COLUMN_LONGITUDE};

        String whereClause = ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE + "=?";
        String[] whereArgs = {String.valueOf(objectType)};

        Cursor cursor = databaseForAreas.query(
                ContractAreasDB.AreaEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {
            int nameColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_AREA_NAME);
            int objectColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE);
            int latColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                String name = cursor.getString(nameColumnIndex);
                int ot = cursor.getInt(objectColumnIndex);
                double latitude = cursor.getDouble(latColumnIndex);
                double longitude = cursor.getDouble(longColumnIndex);
                LatLng latLng = new LatLng(latitude, longitude);

                Area area = new Area();

                area.setAreaName(name);
                area.setAreaObjectTypeNumber(ot);
                area.setLatitude(latitude);
                area.setLongitude(longitude);


                int numberOfObjects = 0;
                if (ot == 0) {
                    numberOfObjects = countINFObjectsForAreaMarker(latLng);
                }
                if (ot == 1) {
                    numberOfObjects = countENEObjectsForAreaMarker(latLng);
                }
                if (ot == 2) {
                    numberOfObjects = countPRMLjudmatningForAreaMarker(latLng);
                }

                area.setNumberOfObject(numberOfObjects);

                areaArrayList.add(area);
            }
        } finally {
            cursor.close();
        }
        return areaArrayList;
    }

    public Area recoverOneAreaMarker(int objectType, LatLng latLng) {

        Area area = new Area();

        String[] projection = {
                ContractAreasDB.AreaEntry._ID,
                ContractAreasDB.AreaEntry.COLUMN_AREA_NAME,
                ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE,
                ContractAreasDB.AreaEntry.COLUMN_LATITUDE,
                ContractAreasDB.AreaEntry.COLUMN_LONGITUDE};


        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);


        String whereClause = ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE + "=? AND "
                + ContractAreasDB.AreaEntry.COLUMN_LATITUDE + " =? AND "
                + ContractAreasDB.AreaEntry.COLUMN_LONGITUDE + " =?";
        String[] whereArgs = {String.valueOf(objectType), lat, lng};

        Cursor cursor = databaseForAreas.query(
                ContractAreasDB.AreaEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {
            int nameColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_AREA_NAME);
            int objectColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE);
            int latColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                String name = cursor.getString(nameColumnIndex);
                int ot = cursor.getInt(objectColumnIndex);
                double latitude = cursor.getDouble(latColumnIndex);
                double longitude = cursor.getDouble(longColumnIndex);

                area.setAreaName(name);
                area.setAreaObjectTypeNumber(ot);
                area.setLatitude(latitude);
                area.setLongitude(longitude);


                int numberOfObjects = 0;
                if (ot == 0) {
                    numberOfObjects = countINFObjectsForAreaMarker(latLng);
                }
                if (ot == 1) {
                    numberOfObjects = countENEObjectsForAreaMarker(latLng);
                }
                if (ot == 2) {
                    numberOfObjects = countPRMLjudmatningForAreaMarker(latLng);
                }

                area.setNumberOfObject(numberOfObjects);
            }
        } finally {
            cursor.close();
        }
        return area;
    }

    public ArrayList<Area> recoverAreaMarkers(String context, int objectType) {

        ArrayList<Area> areaArrayList = new ArrayList<>();

        String[] projection = {
                ContractAreasDB.AreaEntry._ID,
                ContractAreasDB.AreaEntry.COLUMN_AREA_NAME,
                ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE,
                ContractAreasDB.AreaEntry.COLUMN_LATITUDE,
                ContractAreasDB.AreaEntry.COLUMN_LONGITUDE};

        String ot = Integer.toString(objectType);

        String whereClause = ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE + " =? AND " +
                ContractAreasDB.AreaEntry.COLUMN_AREA_NAME + " like?";
        String[] whereArgs = {ot, "%" + context + "%"};

        Cursor cursor = databaseForAreas.query(
                ContractAreasDB.AreaEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {
            int nameColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_AREA_NAME);
            int objectColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE);
            int latColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                String name = cursor.getString(nameColumnIndex);
                int objectTypeInArea = cursor.getInt(objectColumnIndex);
                double latitude = cursor.getDouble(latColumnIndex);
                double longitude = cursor.getDouble(longColumnIndex);
                LatLng latLng = new LatLng(latitude, longitude);

                Area area = new Area();

                area.setAreaName(name);
                area.setAreaObjectTypeNumber(objectTypeInArea);
                area.setLatitude(latitude);
                area.setLongitude(longitude);

                int numberOfObjects = 0;
                if (objectTypeInArea == 0) {
                    numberOfObjects = countINFObjectsForAreaMarker(latLng);
                }
                if (objectTypeInArea == 1) {
                    numberOfObjects = countENEObjectsForAreaMarker(latLng);
                }
                if (objectTypeInArea == 2) {
                    numberOfObjects = countPRMLjudmatningForAreaMarker(latLng);
                }

                area.setNumberOfObject(numberOfObjects);


                areaArrayList.add(area);
            }
        } finally {
            cursor.close();
        }
        return areaArrayList;
    }

    public ArrayList<Area> recoverTwoAreaMarkers(String context, int objectTypeOne, int objectTypeSecond) {

        ArrayList<Area> areaArrayList = new ArrayList<>();

        String[] projection = {
                ContractAreasDB.AreaEntry._ID,
                ContractAreasDB.AreaEntry.COLUMN_AREA_NAME,
                ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE,
                ContractAreasDB.AreaEntry.COLUMN_LATITUDE,
                ContractAreasDB.AreaEntry.COLUMN_LONGITUDE};

        String otOne = Integer.toString(objectTypeOne);
        String otTwo = Integer.toString(objectTypeSecond);


        String whereClause = ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE + " IN(?,?) AND " +
                ContractAreasDB.AreaEntry.COLUMN_AREA_NAME + " like?";
        String[] whereArgs = {otOne, otTwo, "%" + context + "%"};

        Cursor cursor = databaseForAreas.query(
                ContractAreasDB.AreaEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {
            int nameColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_AREA_NAME);
            int objectColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE);
            int latColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                String name = cursor.getString(nameColumnIndex);
                int objectTypeInArea = cursor.getInt(objectColumnIndex);
                double latitude = cursor.getDouble(latColumnIndex);
                double longitude = cursor.getDouble(longColumnIndex);
                LatLng latLng = new LatLng(latitude, longitude);

                Area area = new Area();

                area.setAreaName(name);
                area.setAreaObjectTypeNumber(objectTypeInArea);
                area.setLatitude(latitude);
                area.setLongitude(longitude);

                int numberOfObjects = 0;
                if (objectTypeInArea == 0) {
                    numberOfObjects = countINFObjectsForAreaMarker(latLng);
                }
                if (objectTypeInArea == 1) {
                    numberOfObjects = countENEObjectsForAreaMarker(latLng);
                }
                if (objectTypeInArea == 2) {
                    numberOfObjects = countPRMLjudmatningForAreaMarker(latLng);
                }

                area.setNumberOfObject(numberOfObjects);

                areaArrayList.add(area);
            }
        } finally {
            cursor.close();
        }
        return areaArrayList;

    }

    public ArrayList<Area> recoverAllAreaMarkers(String context) {

        ArrayList<Area> areaArrayList = new ArrayList<>();

        String[] projection = {
                ContractAreasDB.AreaEntry._ID,
                ContractAreasDB.AreaEntry.COLUMN_AREA_NAME,
                ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE,
                ContractAreasDB.AreaEntry.COLUMN_LATITUDE,
                ContractAreasDB.AreaEntry.COLUMN_LONGITUDE};

        String whereClause = ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE + " IN(?,?,?) AND " +
                ContractAreasDB.AreaEntry.COLUMN_AREA_NAME + " like?";
        String[] whereArgs = {"0", "1", "2", "%" + context + "%"};

        Cursor cursor = databaseForAreas.query(
                ContractAreasDB.AreaEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {
            int nameColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_AREA_NAME);
            int objectColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE);
            int latColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                String name = cursor.getString(nameColumnIndex);
                int objectTypeInArea = cursor.getInt(objectColumnIndex);
                double latitude = cursor.getDouble(latColumnIndex);
                double longitude = cursor.getDouble(longColumnIndex);
                LatLng latLng = new LatLng(latitude, longitude);

                Area area = new Area();

                area.setAreaName(name);
                area.setAreaObjectTypeNumber(objectTypeInArea);
                area.setLatitude(latitude);
                area.setLongitude(longitude);

                int numberOfObjects = 0;
                if (objectTypeInArea == 0) {
                    numberOfObjects = countINFObjectsForAreaMarker(latLng);
                }
                if (objectTypeInArea == 1) {
                    numberOfObjects = countENEObjectsForAreaMarker(latLng);
                }
                if (objectTypeInArea == 2) {
                    numberOfObjects = countPRMLjudmatningForAreaMarker(latLng);
                }

                area.setNumberOfObject(numberOfObjects);


                areaArrayList.add(area);
            }
        } finally {
            cursor.close();
        }
        return areaArrayList;
    }

    public void deleteAreaMarker(LatLng latLng) {

        double lat = latLng.latitude;
        double lng = latLng.longitude;

        String latitude = String.valueOf(lat);
        String longitude = String.valueOf(lng);

        String whereClause = ContractAreasDB.AreaEntry.COLUMN_LATITUDE + "=? AND " +
                ContractAreasDB.AreaEntry.COLUMN_LONGITUDE + " =?";
        String[] whereArgs = {latitude, longitude};

        databaseForAreas.delete(ContractAreasDB.AreaEntry.TABLE_NAME, whereClause, whereArgs);

        try{
            deleteOneVaxlarOchSpar(lat,lng);
        }catch(Exception e){
            Log.e("Vanaheim", "exception", e);
        }
    }

    public void deleteObjectFromAreaMarker(int objectType, String idRow) {

        String whereClause;
        if (objectType == 0)
            whereClause = ContractObjectsDBForINF.ObjectEntry._ID + "=?";
        if (objectType == 1)
            whereClause = ContractObjectsDBForENE.ObjectEntry._ID + "=?";
        else
            whereClause = ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID + "=?";

        String[] whereArgs = {idRow};

        if (objectType == 0)
            databaseForINF.delete(ContractObjectsDBForINF.ObjectEntry.TABLE_NAME_INF_OBJECTS, whereClause, whereArgs);
        if (objectType == 1)
            databaseForENE.delete(ContractObjectsDBForENE.ObjectEntry.TABLE_NAME_ENE_OBJECTS, whereClause, whereArgs);
        if (objectType == 2)
            databaseForPRMLjudmatning.delete(ContractObjectsDBForPRMLjudmatning.ObjectEntry.TABLE_NAME_PRM_LJUDMATNING_OBJECTS, whereClause, whereArgs);
    }

    public void deleteObjectsFromAreaMarker(LatLng latLng, int objectType) {

        double lat = latLng.latitude;
        double lng = latLng.longitude;

        String latitude = String.valueOf(lat);
        String longitude = String.valueOf(lng);

        if (objectType == 0) {
            String whereClause = ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                    ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE + " =?";
            String[] whereArgs = {latitude, longitude};

            try {
                databaseForINF.delete(ContractObjectsDBForINF.ObjectEntry.TABLE_NAME_INF_OBJECTS, whereClause, whereArgs);
            } catch (Exception e) {
            }
        }
        if (objectType == 1) {
            String whereClause = ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                    ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE + " =?";
            String[] whereArgs = {latitude, longitude};
            try {
                databaseForENE.delete(ContractObjectsDBForENE.ObjectEntry.TABLE_NAME_ENE_OBJECTS, whereClause, whereArgs);
            } catch (Exception e) {
            }

        }
        if (objectType == 2) {
            String whereClause = ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                    ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE + " =?";
            String[] whereArgs = {latitude, longitude};
            try {
                databaseForPRMLjudmatning.delete(ContractObjectsDBForPRMLjudmatning.ObjectEntry.TABLE_NAME_PRM_LJUDMATNING_OBJECTS, whereClause, whereArgs);
            } catch (Exception e) {
            }
            try {
                databaseForPRMLjusmatning.delete(ContractObjectsDBForPRMLjusmatning.ObjectEntry.TABLE_NAME_PRM_LJUSMATNING_OBJECTS, whereClause, whereArgs);
            } catch (Exception e) {
            }

        }
    }

    public void updateOneAreaName(LatLng latLng, int objectType, String newName){

        double lat = latLng.latitude;
        double lng = latLng.longitude;

        String latitude = String.valueOf(lat);
        String longitude = String.valueOf(lng);

        ContentValues values = new ContentValues();
        values.put(ContractAreasDB.AreaEntry.COLUMN_AREA_NAME, newName);

        if (objectType == 0) {
            String whereClause = ContractAreasDB.AreaEntry.COLUMN_LATITUDE + "=? AND " +
                    ContractAreasDB.AreaEntry.COLUMN_LONGITUDE + " =?";
            String[] whereArgs = {latitude, longitude};

            try {
                databaseForAreas.update(ContractAreasDB.AreaEntry.TABLE_NAME,values, whereClause, whereArgs);
            } catch (Exception e) {
            }
        }
        if (objectType == 1) {
            String whereClause = ContractAreasDB.AreaEntry.COLUMN_LATITUDE + "=? AND " +
                    ContractAreasDB.AreaEntry.COLUMN_LONGITUDE + " =?";
            String[] whereArgs = {latitude, longitude};
            try {
                databaseForAreas.update(ContractAreasDB.AreaEntry.TABLE_NAME,values, whereClause, whereArgs);
            } catch (Exception e) {
            }

        }
        if (objectType == 2) {
            String whereClause = ContractAreasDB.AreaEntry.COLUMN_LATITUDE + "=? AND " +
                    ContractAreasDB.AreaEntry.COLUMN_LONGITUDE + " =?";
            String[] whereArgs = {latitude, longitude};
            try {
                databaseForAreas.update(ContractAreasDB.AreaEntry.TABLE_NAME,values, whereClause, whereArgs);
            } catch (Exception e) {
            }
        }
    }

    public void onDownGradeAreaDatabase() {
        areaDbHelper.onDowngrade(databaseForAreas, 1, 1);
    }

    //**************** INF objects ******************************

    public void saveINFObject(String kmVarde, String lat, String lng) {

        ContentValues values = new ContentValues();
        values.put(ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER, kmVarde);
        values.put(ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED, 0);
        values.put(ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE, lat);
        values.put(ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE, lng);

        databaseForINF.insert(ContractObjectsDBForINF.ObjectEntry.TABLE_NAME_INF_OBJECTS, null, values);
    }

    public ArrayList<Object> recoverINFObjects(LatLng latLng, Boolean objectsReady) {

        ArrayList<Object> objectList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForINF.ObjectEntry._ID,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE};

        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause;
        String[] whereArgs;


        if (objectsReady == true) {
            whereClause = ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED + "=? AND " +
                    ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                    ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE + " =?";
            whereArgs = new String[]{"1", lat, lng};
        } else {
            whereClause = ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                    ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE + " =?";
            whereArgs = new String[]{lat, lng};
        }


        Cursor cursor = databaseForINF.query(
                ContractObjectsDBForINF.ObjectEntry.TABLE_NAME_INF_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry._ID);
            int kmNummerColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER);
            int sparviddColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD);
            int ralsforhojningColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING);
            int slipersavstandColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND);
            int sparavstandColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND);
            int friaRummetColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET);
            int kommentarColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED);
            int latColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                int id = cursor.getInt(idColumnIndex);
                String kmTal = cursor.getString(kmNummerColumnIndex);
                String sparvidd = cursor.getString(sparviddColumnIndex);
                String ralsforhojning = cursor.getString(ralsforhojningColumnIndex);
                String slipersavstand = cursor.getString(slipersavstandColumnIndex);
                String sparavstand = cursor.getString(sparavstandColumnIndex);
                String friaRummet = cursor.getString(friaRummetColumnIndex);
                String kommentar = cursor.getString(kommentarColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                double latitude = cursor.getDouble(latColumnIndex);
                double longitude = cursor.getDouble(longColumnIndex);

                Object object = new Object();

                object.setIdRow(id);
                object.setKmNummer(kmTal);
                object.setSparvidd(sparvidd);
                object.setRalsforhojning(ralsforhojning);
                object.setSlipersavstand(slipersavstand);
                object.setSparavstand(sparavstand);
                object.setFriaRummet(friaRummet);
                object.setComments(kommentar);
                object.setCompleted(completedStatus);
                object.setLatitude(latitude);
                object.setLongitude(longitude);

                objectList.add(object);
            }
        } finally {

            cursor.close();
        }
        return objectList;
    }

    public ArrayList<Object> recoverNotReadyINFObjects(LatLng latLng) {

        ArrayList<Object> objectList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForINF.ObjectEntry._ID,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE};

        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause;
        String[] whereArgs;

        whereClause = ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED + "=? AND " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE + " =?";
        whereArgs = new String[]{"0", lat, lng};


        Cursor cursor = databaseForINF.query(
                ContractObjectsDBForINF.ObjectEntry.TABLE_NAME_INF_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry._ID);
            int kmNummerColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER);
            int sparviddColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD);
            int ralsforhojningColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING);
            int slipersavstandColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND);
            int sparavstandColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND);
            int friaRummetColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET);
            int kommentarColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED);
            int latColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                int id = cursor.getInt(idColumnIndex);
                String kmTal = cursor.getString(kmNummerColumnIndex);
                String sparvidd = cursor.getString(sparviddColumnIndex);
                String ralsforhojning = cursor.getString(ralsforhojningColumnIndex);
                String slipersavstand = cursor.getString(slipersavstandColumnIndex);
                String sparavstand = cursor.getString(sparavstandColumnIndex);
                String friaRummet = cursor.getString(friaRummetColumnIndex);
                String kommentar = cursor.getString(kommentarColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                double latitude = cursor.getDouble(latColumnIndex);
                double longitude = cursor.getDouble(longColumnIndex);

                Object object = new Object();

                object.setIdRow(id);
                object.setKmNummer(kmTal);
                object.setSparvidd(sparvidd);
                object.setRalsforhojning(ralsforhojning);
                object.setSlipersavstand(slipersavstand);
                object.setSparavstand(sparavstand);
                object.setFriaRummet(friaRummet);
                object.setComments(kommentar);
                object.setCompleted(completedStatus);
                object.setLatitude(latitude);
                object.setLongitude(longitude);

                objectList.add(object);
            }
        } finally {

            cursor.close();
        }
        return objectList;
    }

    public Object recoverOneINFObject(int idRow) {

        Object object = new Object();

        String[] projection = {
                ContractObjectsDBForINF.ObjectEntry._ID,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_EDITOR_NAME};


        String whereClause = ContractObjectsDBForINF.ObjectEntry._ID + " = ?";
        String idRowText = String.valueOf(idRow);
        String[] whereArgs = {idRowText};

        Cursor cursor = databaseForINF.query(
                ContractObjectsDBForINF.ObjectEntry.TABLE_NAME_INF_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,   // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {
            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry._ID);
            int kmNummerColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER);
            int sparviddColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD);
            int ralsforhojningColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING);
            int slipersavstandColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND);
            int sparavstandColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND);
            int friaRummetColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET);
            int kommentarColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED);
            int editorNameColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_EDITOR_NAME);


            while (cursor.moveToNext()) {
                int id = cursor.getInt(idColumnIndex);
                String kmTal = cursor.getString(kmNummerColumnIndex);
                String sparvidd = cursor.getString(sparviddColumnIndex);
                String ralsforhojning = cursor.getString(ralsforhojningColumnIndex);
                String slipersavstand = cursor.getString(slipersavstandColumnIndex);
                String sparavstand = cursor.getString(sparavstandColumnIndex);
                String friaRummet = cursor.getString(friaRummetColumnIndex);
                String kommentar = cursor.getString(kommentarColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                String redigerare = cursor.getString(editorNameColumnIndex);

                object.setIdRow(id);
                object.setKmNummer(kmTal);
                object.setSparvidd(sparvidd);
                object.setRalsforhojning(ralsforhojning);
                object.setSlipersavstand(slipersavstand);
                object.setSparavstand(sparavstand);
                object.setFriaRummet(friaRummet);
                object.setComments(kommentar);
                object.setCompleted(completedStatus);
                object.setEditor(redigerare);
            }
        } finally {
            cursor.close();
        }

        return object;

    }

    public Object recoverOneINFObject(String kmVarde, LatLng latLng) {

        Object object = new Object();

        String[] projection = {
                ContractObjectsDBForINF.ObjectEntry._ID,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_EDITOR_NAME};

        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause = ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER + " =? AND "
                + ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE + " =? AND "
                + ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE + " =? ";
        String[] whereArgs = {kmVarde, lat, lng};

        Cursor cursor = databaseForINF.query(
                ContractObjectsDBForINF.ObjectEntry.TABLE_NAME_INF_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,   // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {
            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry._ID);
            int kmNummerColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER);
            int sparviddColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD);
            int ralsforhojningColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING);
            int slipersavstandColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND);
            int sparavstandColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND);
            int friaRummetColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET);
            int kommentarColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED);
            int editorNameColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_EDITOR_NAME);


            while (cursor.moveToNext()) {
                int id = cursor.getInt(idColumnIndex);
                String kmTal = cursor.getString(kmNummerColumnIndex);
                String sparvidd = cursor.getString(sparviddColumnIndex);
                String ralsforhojning = cursor.getString(ralsforhojningColumnIndex);
                String slipersavstand = cursor.getString(slipersavstandColumnIndex);
                String sparavstand = cursor.getString(sparavstandColumnIndex);
                String friaRummet = cursor.getString(friaRummetColumnIndex);
                String kommentar = cursor.getString(kommentarColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                String redigerare = cursor.getString(editorNameColumnIndex);

                object.setIdRow(id);
                object.setKmNummer(kmTal);
                object.setSparvidd(sparvidd);
                object.setRalsforhojning(ralsforhojning);
                object.setSlipersavstand(slipersavstand);
                object.setSparvidd(sparavstand);
                object.setFriaRummet(friaRummet);
                object.setComments(kommentar);
                object.setCompleted(completedStatus);
                object.setEditor(redigerare);

            }
        } finally {
            cursor.close();
        }

        return object;

    }

    public ArrayList<Object> recoverINFObjectsWithContentAndCheckedMarker(String content) {

        ArrayList<Object> objectArrayList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForINF.ObjectEntry._ID,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED};

        String whereClause;
        String[] whereArgs;

        whereClause = ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED + " =? AND " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER + " like? OR " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD + " like? OR " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING + " like? OR " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND + " like? OR " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND + " like? OR " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET + " like? OR " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER + " like? ";
        whereArgs = new String[]{"1", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%"};

        Cursor cursor = databaseForINF.query(
                ContractObjectsDBForINF.ObjectEntry.TABLE_NAME_INF_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);

        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry._ID);
            int kmColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER);
            int trackWidthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD);
            int trackLevelColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING);
            int sliperDistanceColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND);
            int trackDistanceColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND);
            int freeRoomColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET);
            int commentsColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED);
            int latColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                int id = cursor.getInt(idColumnIndex);
                String kmValue = cursor.getString(kmColumnIndex);
                String trackWidth = cursor.getString(trackWidthColumnIndex);
                String trackLevel = cursor.getString(trackLevelColumnIndex);
                String sliperDistance = cursor.getString(sliperDistanceColumnIndex);
                String trackDistance = cursor.getString(trackDistanceColumnIndex);
                String freeRoom = cursor.getString(freeRoomColumnIndex);
                String comments = cursor.getString(commentsColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                double latitude = cursor.getDouble(latColumnIndex);
                double longitude = cursor.getDouble(longColumnIndex);

                Object object = new Object();

                object.setIdRow(id);
                object.setKmNummer(kmValue);
                object.setSparvidd(trackWidth);
                object.setRalsforhojning(trackLevel);
                object.setSlipersavstand(sliperDistance);
                object.setSparavstand(trackDistance);
                object.setFriaRummet(freeRoom);
                object.setComments(comments);

                object.setCompleted(completedStatus);
                object.setLatitude(latitude);
                object.setLongitude(longitude);

                objectArrayList.add(object);

            }
        } finally {
            cursor.close();
        }
        return objectArrayList;
    }

    public ArrayList<Object> recoverINFObjectsWithContentAndNotCheckedMarker(String content) {

        ArrayList<Object> objectArrayList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForINF.ObjectEntry._ID,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED};

        String whereClause;
        String[] whereArgs;

        whereClause = ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED + " =? AND " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER + " like? OR " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD + " like? OR " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING + " like? OR " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND + " like? OR " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND + " like? OR " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET + " like? OR " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER + " like? ";

        whereArgs = new String[]{"0", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%"};

        Cursor cursor = databaseForINF.query(
                ContractObjectsDBForINF.ObjectEntry.TABLE_NAME_INF_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);

        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry._ID);
            int kmColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER);
            int trackWidthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD);
            int trackLevelColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING);
            int sliperDistanceColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND);
            int trackDistanceColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND);
            int freeRoomColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET);
            int commentsColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED);
            int latColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                int id = cursor.getInt(idColumnIndex);
                String kmValue = cursor.getString(kmColumnIndex);
                String trackWidth = cursor.getString(trackWidthColumnIndex);
                String trackLevel = cursor.getString(trackLevelColumnIndex);
                String sliperDistance = cursor.getString(sliperDistanceColumnIndex);
                String trackDistance = cursor.getString(trackDistanceColumnIndex);
                String freeRoom = cursor.getString(freeRoomColumnIndex);
                String comments = cursor.getString(commentsColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                double latitude = cursor.getDouble(latColumnIndex);
                double longitude = cursor.getDouble(longColumnIndex);

                Object object = new Object();

                object.setIdRow(id);
                object.setKmNummer(kmValue);
                object.setSparvidd(trackWidth);
                object.setRalsforhojning(trackLevel);
                object.setSlipersavstand(sliperDistance);
                object.setSparavstand(trackDistance);
                object.setFriaRummet(freeRoom);
                object.setComments(comments);

                object.setCompleted(completedStatus);
                object.setLatitude(latitude);
                object.setLongitude(longitude);

                objectArrayList.add(object);
            }
        } finally {
            cursor.close();
        }
        return objectArrayList;
    }

    public ArrayList<Object> recoverINFObjectsWithContent(String content) {

        ArrayList<Object> objectArrayList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForINF.ObjectEntry._ID,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED};

        String whereClause;
        String[] whereArgs;

        whereClause = ((ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER + " like? OR " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD + " like? OR " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING + " like? OR " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND + " like? OR " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND + " like? OR " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET + " like? OR " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER) + " like? ");
        whereArgs = new String[]{"%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%"};

        Cursor cursor = databaseForINF.query(
                ContractObjectsDBForINF.ObjectEntry.TABLE_NAME_INF_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);

        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry._ID);
            int kmColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER);
            int trackWidthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD);
            int trackLevelColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING);
            int sliperDistanceColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND);
            int trackDistanceColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND);
            int freeRoomColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET);
            int commentsColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED);
            int latColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                int id = cursor.getInt(idColumnIndex);
                String kmValue = cursor.getString(kmColumnIndex);
                String trackWidth = cursor.getString(trackWidthColumnIndex);
                String trackLevel = cursor.getString(trackLevelColumnIndex);
                String sliperDistance = cursor.getString(sliperDistanceColumnIndex);
                String trackDistance = cursor.getString(trackDistanceColumnIndex);
                String freeRoom = cursor.getString(freeRoomColumnIndex);
                String comments = cursor.getString(commentsColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                double latitude = cursor.getDouble(latColumnIndex);
                double longitude = cursor.getDouble(longColumnIndex);

                Object object = new Object();

                object.setIdRow(id);
                object.setKmNummer(kmValue);
                object.setSparvidd(trackWidth);
                object.setRalsforhojning(trackLevel);
                object.setSlipersavstand(sliperDistance);
                object.setSparavstand(trackDistance);
                object.setFriaRummet(freeRoom);
                object.setComments(comments);

                object.setCompleted(completedStatus);
                object.setLatitude(latitude);
                object.setLongitude(longitude);

                objectArrayList.add(object);

            }
        } finally {
            cursor.close();
        }
        return objectArrayList;
    }

    public int countINFObjectsForAreaMarker(LatLng latLng) {

        int numberOfObjects = 0;

        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause = ContractObjectsDBForINF.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                ContractObjectsDBForINF.ObjectEntry.COLUMN_LONGITUDE + " =?";
        String[] whereArgs = {lat, lng};

        Cursor cursor = databaseForINF.query(
                ContractObjectsDBForINF.ObjectEntry.TABLE_NAME_INF_OBJECTS,   // The table to query
                null,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            while (cursor.moveToNext()) {
                numberOfObjects++;
            }
        } finally {
            cursor.close();
            return numberOfObjects;
        }
    }

    public void updateOneINFObject(int idRow, String kmNummer, String sparvidd, String ralsforhojning,
                                   String slipersavstand, String sparavstand, String friaRummet,
                                   String kommentarer, String editor, int completed) {

        ContentValues values = new ContentValues();
        values.put(ContractObjectsDBForINF.ObjectEntry.COLUMN_KM_NUMMER, kmNummer);
        values.put(ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARVIDD, sparvidd);
        values.put(ContractObjectsDBForINF.ObjectEntry.COLUMN_RALSFORHOJNING, ralsforhojning);
        values.put(ContractObjectsDBForINF.ObjectEntry.COLUMN_SLIPERSAVSTAND, slipersavstand);
        values.put(ContractObjectsDBForINF.ObjectEntry.COLUMN_SPARAVSTAND, sparavstand);
        values.put(ContractObjectsDBForINF.ObjectEntry.COLUMN_FRIA_RUMMET, friaRummet);
        values.put(ContractObjectsDBForINF.ObjectEntry.COLUMN_KOMMENTARER, kommentarer);
        values.put(ContractObjectsDBForINF.ObjectEntry.COLUMN_EDITOR_NAME, editor);
        values.put(ContractObjectsDBForINF.ObjectEntry.COLUMN_COMPLETED, completed);

        String whereClause = ContractObjectsDBForINF.ObjectEntry._ID + " = ?";
        String idRowText = String.valueOf(idRow);
        String[] whereArgs = {idRowText};

        databaseForINF.update(ContractObjectsDBForINF.ObjectEntry.TABLE_NAME_INF_OBJECTS, values, whereClause, whereArgs);
    }

    public void onDownGradeINFDatabase() {
        objectsDBHelperForINF.onDowngrade(databaseForINF, 1, 1);
    }


    //**************** ENE objects ******************************

    public void saveENEObject(String stolpNr, String lat, String lng) {

        ContentValues values = new ContentValues();
        values.put(ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR, stolpNr);
        values.put(ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED, 0);
        values.put(ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE, lat);
        values.put(ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE, lng);

        databaseForENE.insert(ContractObjectsDBForENE.ObjectEntry.TABLE_NAME_ENE_OBJECTS, null, values);
    }

    public ArrayList<Object> recoverENEObjects(LatLng latLng, Boolean objectsReady) {

        ArrayList<Object> objectList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForENE.ObjectEntry._ID,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED};

        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause;
        String[] whereArgs;

        if (objectsReady == true) {
            whereClause = ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED + "=? AND " +
                    ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                    ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE + " =?";
            whereArgs = new String[]{"1", lat, lng};
        } else {
            whereClause = ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                    ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE + " =?";
            whereArgs = new String[]{lat, lng};
        }

        Cursor cursor = databaseForENE.query(
                ContractObjectsDBForENE.ObjectEntry.TABLE_NAME_ENE_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry._ID);
            int stolpnrColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR);
            int objektColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT);
            int hojdAvKontakttradColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD);
            int avvikelseISidledColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED);
            int hojdAvUtliggarrorColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR);
            int uppHojdAvTillsatsrorColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR);
            int kommentarColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED);
            int latColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                int id = cursor.getInt(idColumnIndex);
                String stolpnr = cursor.getString(stolpnrColumnIndex);
                String objekt = cursor.getString(objektColumnIndex);
                String hojdAvKontakttrad = cursor.getString(hojdAvKontakttradColumnIndex);
                String avvikelseISidled = cursor.getString(avvikelseISidledColumnIndex);
                String hojdAvUtliggarror = cursor.getString(hojdAvUtliggarrorColumnIndex);
                String upphojdAvTillsatsror = cursor.getString(uppHojdAvTillsatsrorColumnIndex);
                String kommentar = cursor.getString(kommentarColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                double latitude = cursor.getDouble(latColumnIndex);
                double longitude = cursor.getDouble(longColumnIndex);

                Object object = new Object();

                object.setIdRow(id);
                object.setStolpnummer(stolpnr);
                object.setObjektForENE(objekt);
                object.setHojdAvKontakttrad(hojdAvKontakttrad);
                object.setAvvikelseISidled(avvikelseISidled);
                object.setHojdAvUtliggarror(hojdAvUtliggarror);
                object.setUpphojdAvTillsatsror(upphojdAvTillsatsror);
                object.setComments(kommentar);
                object.setCompleted(completedStatus);
                object.setLatitude(latitude);
                object.setLongitude(longitude);

                objectList.add(object);
            }
        } finally {
            cursor.close();
        }
        return objectList;
    }

    public ArrayList<Object> recoverNotReadyENEObjects(LatLng latLng) {

        ArrayList<Object> objectList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForENE.ObjectEntry._ID,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED};

        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause;
        String[] whereArgs;

        whereClause = ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED + "=? AND " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE + " =?";
        whereArgs = new String[]{"0", lat, lng};

        Cursor cursor = databaseForENE.query(
                ContractObjectsDBForENE.ObjectEntry.TABLE_NAME_ENE_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry._ID);
            int stolpnrColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR);
            int objektColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT);
            int hojdAvKontakttradColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD);
            int avvikelseISidledColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED);
            int hojdAvUtliggarrorColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR);
            int uppHojdAvTillsatsrorColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR);
            int kommentarColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED);
            int latColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                int id = cursor.getInt(idColumnIndex);
                String stolpnr = cursor.getString(stolpnrColumnIndex);
                String objekt = cursor.getString(objektColumnIndex);
                String hojdAvKontakttrad = cursor.getString(hojdAvKontakttradColumnIndex);
                String avvikelseISidled = cursor.getString(avvikelseISidledColumnIndex);
                String hojdAvUtliggarror = cursor.getString(hojdAvUtliggarrorColumnIndex);
                String upphojdAvTillsatsror = cursor.getString(uppHojdAvTillsatsrorColumnIndex);
                String kommentar = cursor.getString(kommentarColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                double latitude = cursor.getDouble(latColumnIndex);
                double longitude = cursor.getDouble(longColumnIndex);

                Object object = new Object();

                object.setIdRow(id);
                object.setStolpnummer(stolpnr);
                object.setObjektForENE(objekt);
                object.setHojdAvKontakttrad(hojdAvKontakttrad);
                object.setAvvikelseISidled(avvikelseISidled);
                object.setHojdAvUtliggarror(hojdAvUtliggarror);
                object.setUpphojdAvTillsatsror(upphojdAvTillsatsror);
                object.setComments(kommentar);
                object.setCompleted(completedStatus);
                object.setLatitude(latitude);
                object.setLongitude(longitude);

                objectList.add(object);
            }
        } finally {
            cursor.close();
        }
        return objectList;
    }

    public Object recoverOneENEObject(int idRow) {

        Object object = new Object();

        String[] projection = {
                ContractObjectsDBForENE.ObjectEntry._ID,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_EDITOR_NAME};


        String whereClause = ContractObjectsDBForENE.ObjectEntry._ID + " = ?";
        String idRowText = String.valueOf(idRow);
        String[] whereArgs = {idRowText};

        Cursor cursor = databaseForENE.query(
                ContractObjectsDBForENE.ObjectEntry.TABLE_NAME_ENE_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,   // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry._ID);
            int stolpNrColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR);
            int objektColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT);
            int hojdAvKontakttradColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD);
            int avvikelseISidledColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED);
            int hojdAvUtliggarrorColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR);
            int upphojdAvTillsatsrorColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR);
            int kommentarColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED);
            int editorNameColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_EDITOR_NAME);


            while (cursor.moveToNext()) {

                int id = cursor.getInt(idColumnIndex);
                String stolpnr = cursor.getString(stolpNrColumnIndex);
                String objekt = cursor.getString(objektColumnIndex);
                String hojdAvKontakttrad = cursor.getString(hojdAvKontakttradColumnIndex);
                String avvikelseISidled = cursor.getString(avvikelseISidledColumnIndex);
                String hojdAvUtliggarror = cursor.getString(hojdAvUtliggarrorColumnIndex);
                String upphojdAvTillsatsror = cursor.getString(upphojdAvTillsatsrorColumnIndex);
                String kommentar = cursor.getString(kommentarColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                String editor = cursor.getString(editorNameColumnIndex);

                object.setIdRow(id);
                object.setStolpnummer(stolpnr);
                object.setObjektForENE(objekt);
                object.setHojdAvKontakttrad(hojdAvKontakttrad);
                object.setAvvikelseISidled(avvikelseISidled);
                object.setHojdAvUtliggarror(hojdAvUtliggarror);
                object.setUpphojdAvTillsatsror(upphojdAvTillsatsror);
                object.setComments(kommentar);
                object.setCompleted(completedStatus);
                object.setEditor(editor);
            }
        } finally {
            cursor.close();
        }
        return object;
    }

    public Object recoverOneENEObject(String stolpNr, LatLng latLng) {

        Object object = new Object();

        String[] projection = {
                ContractObjectsDBForENE.ObjectEntry._ID,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_EDITOR_NAME};


        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause = ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR + " = ? AND "
                + ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE + " =? AND "
                + ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE + " =?";
        String[] whereArgs = {stolpNr, lat, lng};

        Cursor cursor = databaseForENE.query(
                ContractObjectsDBForENE.ObjectEntry.TABLE_NAME_ENE_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,   // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry._ID);
            int stolpNrColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR);
            int objektColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT);
            int hojdAvKontakttradColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD);
            int avvikelseISidledColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED);
            int hojdAvUtliggarrorColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR);
            int upphojdAvTillsatsrorColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR);
            int kommentarColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED);
            int editorNameColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_EDITOR_NAME);


            while (cursor.moveToNext()) {

                int id = cursor.getInt(idColumnIndex);
                String stolpnr = cursor.getString(stolpNrColumnIndex);
                String objekt = cursor.getString(objektColumnIndex);
                String hojdAvKontakttrad = cursor.getString(hojdAvKontakttradColumnIndex);
                String avvikelseISidled = cursor.getString(avvikelseISidledColumnIndex);
                String hojdAvUtliggarror = cursor.getString(hojdAvUtliggarrorColumnIndex);
                String upphojdAvTillsatsror = cursor.getString(upphojdAvTillsatsrorColumnIndex);
                String kommentar = cursor.getString(kommentarColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                String editor = cursor.getString(editorNameColumnIndex);

                object.setIdRow(id);
                object.setStolpnummer(stolpnr);
                object.setObjektForENE(objekt);
                object.setHojdAvKontakttrad(hojdAvKontakttrad);
                object.setAvvikelse(avvikelseISidled);
                object.setHojdAvUtliggarror(hojdAvUtliggarror);
                object.setUpphojdAvTillsatsror(upphojdAvTillsatsror);
                object.setComments(kommentar);
                object.setCompleted(completedStatus);
                object.setEditor(editor);

            }
        } finally {
            cursor.close();
        }
        return object;
    }

    public ArrayList<Object> recoverENEObjectsWithContentAndCheckedMarker(String content) {

        ArrayList<Object> objectArrayList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForENE.ObjectEntry._ID,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED};

        String whereClause;
        String[] whereArgs;


        whereClause = ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED + " =? AND " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR + " like? OR " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT + " like? OR " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD + " like? OR " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED + " like? OR " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR + " like? OR " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR + " like? OR " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR + " like?";
        whereArgs = new String[]{"1", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%"};


        Cursor cursor = databaseForENE.query(
                ContractObjectsDBForENE.ObjectEntry.TABLE_NAME_ENE_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry._ID);
            int stolpnrColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR);
            int objektColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT);
            int hojdAvKontakttradColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD);
            int avvikelseISidledColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED);
            int hojdAvUtliggarrorColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR);
            int uppHojdAvTillsatsrorColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR);
            int kommentarColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED);
            int latColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                int id = cursor.getInt(idColumnIndex);
                String stolpnr = cursor.getString(stolpnrColumnIndex);
                String objekt = cursor.getString(objektColumnIndex);
                String hojdAvKontakttrad = cursor.getString(hojdAvKontakttradColumnIndex);
                String avvikelseISidled = cursor.getString(avvikelseISidledColumnIndex);
                String hojdAvUtliggarror = cursor.getString(hojdAvUtliggarrorColumnIndex);
                String upphojdAvTillsatsror = cursor.getString(uppHojdAvTillsatsrorColumnIndex);
                String kommentar = cursor.getString(kommentarColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                double latitude = cursor.getDouble(latColumnIndex);
                double longitude = cursor.getDouble(longColumnIndex);

                Object object = new Object();

                object.setIdRow(id);
                object.setStolpnummer(stolpnr);
                object.setObjektForENE(objekt);
                object.setHojdAvKontakttrad(hojdAvKontakttrad);
                object.setAvvikelseISidled(avvikelseISidled);
                object.setHojdAvUtliggarror(hojdAvUtliggarror);
                object.setUpphojdAvTillsatsror(upphojdAvTillsatsror);
                object.setComments(kommentar);
                object.setCompleted(completedStatus);
                object.setLatitude(latitude);
                object.setLongitude(longitude);

                objectArrayList.add(object);
            }
        } finally {
            cursor.close();
        }

        return objectArrayList;
    }

    public ArrayList<Object> recoverENEObjectsWithContentAndNotCheckedMarker(String content) {

        ArrayList<Object> objectArrayList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForENE.ObjectEntry._ID,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED};

        String whereClause;
        String[] whereArgs;


        whereClause = ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED + " =? AND " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR + " like? OR " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT + " like? OR " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD + " like? OR " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED + " like? OR " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR + " like? OR " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR + " like? OR " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR + " like?";
        whereArgs = new String[]{"0", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%"};


        Cursor cursor = databaseForENE.query(
                ContractObjectsDBForENE.ObjectEntry.TABLE_NAME_ENE_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry._ID);
            int stolpnrColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR);
            int objektColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT);
            int hojdAvKontakttradColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD);
            int avvikelseISidledColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED);
            int hojdAvUtliggarrorColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR);
            int uppHojdAvTillsatsrorColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR);
            int kommentarColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED);
            int latColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                int id = cursor.getInt(idColumnIndex);
                String stolpnr = cursor.getString(stolpnrColumnIndex);
                String objekt = cursor.getString(objektColumnIndex);
                String hojdAvKontakttrad = cursor.getString(hojdAvKontakttradColumnIndex);
                String avvikelseISidled = cursor.getString(avvikelseISidledColumnIndex);
                String hojdAvUtliggarror = cursor.getString(hojdAvUtliggarrorColumnIndex);
                String upphojdAvTillsatsror = cursor.getString(uppHojdAvTillsatsrorColumnIndex);
                String kommentar = cursor.getString(kommentarColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                double latitude = cursor.getDouble(latColumnIndex);
                double longitude = cursor.getDouble(longColumnIndex);

                Object object = new Object();

                object.setIdRow(id);
                object.setStolpnummer(stolpnr);
                object.setObjektForENE(objekt);
                object.setHojdAvKontakttrad(hojdAvKontakttrad);
                object.setAvvikelseISidled(avvikelseISidled);
                object.setHojdAvUtliggarror(hojdAvUtliggarror);
                object.setUpphojdAvTillsatsror(upphojdAvTillsatsror);
                object.setComments(kommentar);
                object.setCompleted(completedStatus);
                object.setLatitude(latitude);
                object.setLongitude(longitude);

                objectArrayList.add(object);
            }
        } finally {
            cursor.close();
        }

        return objectArrayList;
    }

    public ArrayList<Object> recoverENEObjectsWithContent(String content) {

        ArrayList<Object> objectArrayList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForENE.ObjectEntry._ID,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED};

        String whereClause;
        String[] whereArgs;


        whereClause = (ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR + " like? OR " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT + " like? OR " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD + " like? OR " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED + " like? OR " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR + " like? OR " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR + " like? OR " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR + " like?");
        whereArgs = new String[]{"%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%", "%" + content + "%"};


        Cursor cursor = databaseForENE.query(
                ContractObjectsDBForENE.ObjectEntry.TABLE_NAME_ENE_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry._ID);
            int stolpnrColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR);
            int objektColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT);
            int hojdAvKontakttradColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD);
            int avvikelseISidledColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED);
            int hojdAvUtliggarrorColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR);
            int uppHojdAvTillsatsrorColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR);
            int kommentarColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED);
            int latColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                int id = cursor.getInt(idColumnIndex);
                String stolpnr = cursor.getString(stolpnrColumnIndex);
                String objekt = cursor.getString(objektColumnIndex);
                String hojdAvKontakttrad = cursor.getString(hojdAvKontakttradColumnIndex);
                String avvikelseISidled = cursor.getString(avvikelseISidledColumnIndex);
                String hojdAvUtliggarror = cursor.getString(hojdAvUtliggarrorColumnIndex);
                String upphojdAvTillsatsror = cursor.getString(uppHojdAvTillsatsrorColumnIndex);
                String kommentar = cursor.getString(kommentarColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                double latitude = cursor.getDouble(latColumnIndex);
                double longitude = cursor.getDouble(longColumnIndex);

                Object object = new Object();

                object.setIdRow(id);
                object.setStolpnummer(stolpnr);
                object.setObjektForENE(objekt);
                object.setHojdAvKontakttrad(hojdAvKontakttrad);
                object.setAvvikelseISidled(avvikelseISidled);
                object.setHojdAvUtliggarror(hojdAvUtliggarror);
                object.setUpphojdAvTillsatsror(upphojdAvTillsatsror);
                object.setComments(kommentar);
                object.setCompleted(completedStatus);
                object.setLatitude(latitude);
                object.setLongitude(longitude);

                objectArrayList.add(object);
            }
        } finally {
            cursor.close();
        }

        return objectArrayList;
    }

    public int countENEObjectsForAreaMarker(LatLng latLng) {

        int numberOfObjects = 0;

        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause = ContractObjectsDBForENE.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                ContractObjectsDBForENE.ObjectEntry.COLUMN_LONGITUDE + " =?";
        String[] whereArgs = {lat, lng};

        Cursor cursor = databaseForENE.query(
                ContractObjectsDBForENE.ObjectEntry.TABLE_NAME_ENE_OBJECTS,   // The table to query
                null,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            while (cursor.moveToNext()) {
                numberOfObjects++;
            }
        } finally {
            cursor.close();
            return numberOfObjects;
        }
    }

    public void updateOneENEObject(int idRow, String stolpnummer, String objektForENE, String hojdAvKontakttrad,
                                   String avvikelseISidled, String hojdAvUtliggarror, String upphojdAvTillsatsror,
                                   String kommentarer, String editor, int completed) {

        ContentValues values = new ContentValues();
        values.put(ContractObjectsDBForENE.ObjectEntry.COLUMN_STOLP_NR, stolpnummer);
        values.put(ContractObjectsDBForENE.ObjectEntry.COLUMN_OBJEKT, objektForENE);
        values.put(ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_KONTAKTTRAD, hojdAvKontakttrad);
        values.put(ContractObjectsDBForENE.ObjectEntry.COLUMN_AVVIKKELSE_I_SIDLED, avvikelseISidled);
        values.put(ContractObjectsDBForENE.ObjectEntry.COLUMN_HOJD_AV_UTLIGGARROR, hojdAvUtliggarror);
        values.put(ContractObjectsDBForENE.ObjectEntry.COLUMN_UPPHOJD_AV_TILLSATSROR, upphojdAvTillsatsror);
        values.put(ContractObjectsDBForENE.ObjectEntry.COLUMN_KOMMENTAR, kommentarer);
        values.put(ContractObjectsDBForENE.ObjectEntry.COLUMN_EDITOR_NAME, editor);
        values.put(ContractObjectsDBForENE.ObjectEntry.COLUMN_COMPLETED, completed);

        String whereClause = ContractObjectsDBForENE.ObjectEntry._ID + " = ?";
        String idRowText = String.valueOf(idRow);
        String[] whereArgs = {idRowText};

        databaseForENE.update(ContractObjectsDBForENE.ObjectEntry.TABLE_NAME_ENE_OBJECTS, values, whereClause, whereArgs);
    }

    public void onDownGradeENEDatabase() {
        objectsDBHelperForENE.onDowngrade(databaseForENE, 1, 1);
    }

    //**************** PRM Ljudmatning ******************************

    public void savePRMLjudmatningObject(String plats, String lat, String lng) {

        ContentValues values = new ContentValues();
        values.put(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS, plats);
        values.put(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED, 0);
        values.put(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ARVARDE, "0.0,0.0,0.0");
        values.put(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_BORVARDE, "0.45");
        values.put(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE, lat);
        values.put(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE, lng);

        databaseForPRMLjudmatning.insert(ContractObjectsDBForPRMLjudmatning.ObjectEntry.TABLE_NAME_PRM_LJUDMATNING_OBJECTS, null, values);
    }

    public ArrayList<Object> recoverPRMLjudmatningObjects(LatLng latLng, Boolean objectsReady) {

        ArrayList<Object> objectList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_OBJEKT,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ARVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_BORVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_MEDELVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_AVVIKELSE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ANMARKNING,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE,};

        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause;
        String[] whereArgs;

        if (objectsReady == true) {
            whereClause = ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED + "=? AND " +
                    ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                    ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE + " =?";
            whereArgs = new String[]{"1", lat, lng};
        } else {
            whereClause = ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                    ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE + " =?";
            whereArgs = new String[]{lat, lng};
        }
        Cursor cursor = databaseForPRMLjudmatning.query(
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.TABLE_NAME_PRM_LJUDMATNING_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID);
            int platsColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS);
            int objektColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_OBJEKT);
            int arvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ARVARDE);
            int borvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_BORVARDE);
            int medelvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_MEDELVARDE);
            int avvikelseColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_AVVIKELSE);
            int anmarkningColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ANMARKNING);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED);
            int latColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                int id = cursor.getInt(idColumnIndex);
                String plats = cursor.getString(platsColumnIndex);
                String objekt = cursor.getString(objektColumnIndex);
                String arvarde = cursor.getString(arvardeColumnIndex);
                String borvarde = cursor.getString(borvardeColumnIndex);
                String medelvarde = cursor.getString(medelvardeColumnIndex);
                String avvikelse = cursor.getString(avvikelseColumnIndex);
                String anmarkning = cursor.getString(anmarkningColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                double latitude = cursor.getDouble(latColumnIndex);
                double longitude = cursor.getDouble(longColumnIndex);

                Object object = new Object();

                object.setIdRow(id);
                object.setPlats(plats);
                object.setObjektForPRMLjudmatning(objekt);
                object.setArvarde(arvarde);
                object.setBorvarde(borvarde);
                object.setMedelvarde(medelvarde);
                object.setAvvikelse(avvikelse);
                object.setAnmarkning(anmarkning);
                object.setCompleted(completedStatus);
                object.setLatitude(latitude);
                object.setLongitude(longitude);
                object.setPrmType(0);

                objectList.add(object);
            }
        } finally {
            cursor.close();
        }
        return objectList;
    }

    public ArrayList<Object> recoverNotReadyPRMLjudmatningObjects(LatLng latLng) {

        ArrayList<Object> objectList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_OBJEKT,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ARVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_BORVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_MEDELVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_AVVIKELSE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ANMARKNING,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE,};

        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause;
        String[] whereArgs;

        whereClause = ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED + "=? AND " +
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE + " =?";
        whereArgs = new String[]{"0", lat, lng};

        Cursor cursor = databaseForPRMLjudmatning.query(
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.TABLE_NAME_PRM_LJUDMATNING_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID);
            int platsColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS);
            int objektColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_OBJEKT);
            int arvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ARVARDE);
            int borvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_BORVARDE);
            int medelvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_MEDELVARDE);
            int avvikelseColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_AVVIKELSE);
            int anmarkningColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ANMARKNING);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED);
            int latColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                int id = cursor.getInt(idColumnIndex);
                String plats = cursor.getString(platsColumnIndex);
                String objekt = cursor.getString(objektColumnIndex);
                String arvarde = cursor.getString(arvardeColumnIndex);
                String borvarde = cursor.getString(borvardeColumnIndex);
                String medelvarde = cursor.getString(medelvardeColumnIndex);
                String avvikelse = cursor.getString(avvikelseColumnIndex);
                String anmarkning = cursor.getString(anmarkningColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                double latitude = cursor.getDouble(latColumnIndex);
                double longitude = cursor.getDouble(longColumnIndex);

                Object object = new Object();

                object.setIdRow(id);
                object.setPlats(plats);
                object.setObjektForPRMLjudmatning(objekt);
                object.setArvarde(arvarde);
                object.setBorvarde(borvarde);
                object.setMedelvarde(medelvarde);
                object.setAvvikelse(avvikelse);
                object.setAnmarkning(anmarkning);
                object.setCompleted(completedStatus);
                object.setLatitude(latitude);
                object.setLongitude(longitude);
                object.setPrmType(0);

                objectList.add(object);
            }
        } finally {
            cursor.close();
        }
        return objectList;
    }

    public Object recoverOnePRMLjudmatningObject(int idRow) {

        Object object = new Object();

        String[] projection = {
                ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_OBJEKT,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ARVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_BORVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_MEDELVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_AVVIKELSE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ANMARKNING,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_EDITOR_NAME};


        String whereClause = ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID + " = ?";
        String idRowText = String.valueOf(idRow);
        String[] whereArgs = {idRowText};

        Cursor cursor = databaseForPRMLjudmatning.query(
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.TABLE_NAME_PRM_LJUDMATNING_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,   // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID);
            int platsColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS);
            int objektColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_OBJEKT);
            int arvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ARVARDE);
            int borvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_BORVARDE);
            int medelvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_MEDELVARDE);
            int avvikelseColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_AVVIKELSE);
            int anmarkningColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ANMARKNING);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED);
            int editorNameColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_EDITOR_NAME);


            while (cursor.moveToNext()) {
                int id = cursor.getInt(idColumnIndex);
                String plats = cursor.getString(platsColumnIndex);
                String objekt = cursor.getString(objektColumnIndex);
                String arvarde = cursor.getString(arvardeColumnIndex);
                String borvarde = cursor.getString(borvardeColumnIndex);
                String medelvarde = cursor.getString(medelvardeColumnIndex);
                String avvikelse = cursor.getString(avvikelseColumnIndex);
                String anmarkning = cursor.getString(anmarkningColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                String editor = cursor.getString(editorNameColumnIndex);

                object.setIdRow(id);
                object.setPlats(plats);
                object.setObjektForPRMLjudmatning(objekt);
                object.setArvarde(arvarde);
                object.setBorvarde(borvarde);
                object.setMedelvarde(medelvarde);
                object.setAvvikelse(avvikelse);
                object.setAnmarkning(anmarkning);
                object.setCompleted(completedStatus);
                object.setEditor(editor);
            }
        } finally {
            cursor.close();
        }
        return object;
    }

    public Object recoverOnePRMLjudmatningObject(String currentPlats, LatLng latLng) {

        Object object = new Object();

        String[] projection = {
                ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_OBJEKT,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ARVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_BORVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_MEDELVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_AVVIKELSE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ANMARKNING,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_EDITOR_NAME};


        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause = (ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS + " =? AND "
                + ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE + " =? AND "
                + ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE + " =?");
        String[] whereArgs = {currentPlats, lat, lng};

        Cursor cursor = databaseForPRMLjudmatning.query(
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.TABLE_NAME_PRM_LJUDMATNING_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,   // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID);
            int platsColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS);
            int objektColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_OBJEKT);
            int arvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ARVARDE);
            int borvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_BORVARDE);
            int medelvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_MEDELVARDE);
            int avvikelseColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_AVVIKELSE);
            int anmarkningColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ANMARKNING);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED);
            int editorNameColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_EDITOR_NAME);


            while (cursor.moveToNext()) {
                int id = cursor.getInt(idColumnIndex);
                String plats = cursor.getString(platsColumnIndex);
                String objekt = cursor.getString(objektColumnIndex);
                String arvarde = cursor.getString(arvardeColumnIndex);
                String borvarde = cursor.getString(borvardeColumnIndex);
                String medelvarde = cursor.getString(medelvardeColumnIndex);
                String avvikelse = cursor.getString(avvikelseColumnIndex);
                String anmarkning = cursor.getString(anmarkningColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                String editor = cursor.getString(editorNameColumnIndex);

                object.setIdRow(id);
                object.setPlats(plats);
                object.setObjektForPRMLjudmatning(objekt);
                object.setArvarde(arvarde);
                object.setBorvarde(borvarde);
                object.setMedelvarde(medelvarde);
                object.setAvvikelse(avvikelse);
                object.setAnmarkning(anmarkning);
                object.setCompleted(completedStatus);
                object.setEditor(editor);
            }
        } finally {
            cursor.close();
        }
        return object;
    }

    public ArrayList<Object> recoverPRMLjudmatningObjectsWithContent(String content) {

        ArrayList<Object> objectArrayList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_OBJEKT,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ARVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_BORVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_MEDELVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_AVVIKELSE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ANMARKNING,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE,};

        String whereClause;
        String[] whereArgs;

        whereClause = ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS + " like?";
        whereArgs = new String[]{"%" + content + "%"};

        Cursor cursor = databaseForPRMLjudmatning.query(
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.TABLE_NAME_PRM_LJUDMATNING_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID);
            int platsColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS);
            int objektColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_OBJEKT);
            int arvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ARVARDE);
            int borvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_BORVARDE);
            int medelvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_MEDELVARDE);
            int avvikelseColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_AVVIKELSE);
            int anmarkningColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ANMARKNING);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED);
            int latColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                int id = cursor.getInt(idColumnIndex);
                String plats = cursor.getString(platsColumnIndex);
                String objekt = cursor.getString(objektColumnIndex);
                String arvarde = cursor.getString(arvardeColumnIndex);
                String borvarde = cursor.getString(borvardeColumnIndex);
                String medelvarde = cursor.getString(medelvardeColumnIndex);
                String avvikelse = cursor.getString(avvikelseColumnIndex);
                String anmarkning = cursor.getString(anmarkningColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                double latitudeV = cursor.getDouble(latColumnIndex);
                double longitudeV = cursor.getDouble(longColumnIndex);

                Object object = new Object();

                object.setIdRow(id);
                object.setPlats(plats);
                object.setObjektForPRMLjudmatning(objekt);
                object.setArvarde(arvarde);
                object.setBorvarde(borvarde);
                object.setMedelvarde(medelvarde);
                object.setAvvikelse(avvikelse);
                object.setAnmarkning(anmarkning);
                object.setCompleted(completedStatus);
                object.setLatitude(latitudeV);
                object.setLongitude(longitudeV);

                objectArrayList.add(object);
            }
        } finally {
            cursor.close();
        }

        return objectArrayList;
    }

    public ArrayList<Object> recoverPRMLjudmatningObjectsWithContentAndCheckedMarker(String content) {

        ArrayList<Object> objectArrayList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_OBJEKT,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ARVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_BORVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_MEDELVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_AVVIKELSE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ANMARKNING,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE,};

        String whereClause;
        String[] whereArgs;

        whereClause = ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED + " =? AND " +
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS + " like?";
        whereArgs = new String[]{"1", "%" + content + "%"};


        Cursor cursor = databaseForPRMLjudmatning.query(
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.TABLE_NAME_PRM_LJUDMATNING_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID);
            int platsColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS);
            int objektColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_OBJEKT);
            int arvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ARVARDE);
            int borvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_BORVARDE);
            int medelvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_MEDELVARDE);
            int avvikelseColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_AVVIKELSE);
            int anmarkningColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ANMARKNING);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED);
            int latColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                int id = cursor.getInt(idColumnIndex);
                String plats = cursor.getString(platsColumnIndex);
                String objekt = cursor.getString(objektColumnIndex);
                String arvarde = cursor.getString(arvardeColumnIndex);
                String borvarde = cursor.getString(borvardeColumnIndex);
                String medelvarde = cursor.getString(medelvardeColumnIndex);
                String avvikelse = cursor.getString(avvikelseColumnIndex);
                String anmarkning = cursor.getString(anmarkningColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                double latitudeV = cursor.getDouble(latColumnIndex);
                double longitudeV = cursor.getDouble(longColumnIndex);


                Object object = new Object();

                object.setIdRow(id);
                object.setPlats(plats);
                object.setObjektForPRMLjudmatning(objekt);
                object.setArvarde(arvarde);
                object.setBorvarde(borvarde);
                object.setMedelvarde(medelvarde);
                object.setAvvikelse(avvikelse);
                object.setAnmarkning(anmarkning);
                object.setCompleted(completedStatus);
                object.setLatitude(latitudeV);
                object.setLongitude(longitudeV);
                object.setPrmType(0);

                objectArrayList.add(object);
            }

        } finally {
            cursor.close();
        }

        return objectArrayList;
    }

    public ArrayList<Object> recoverPRMLjudmatningObjectsWithContentAndNotCheckedMarker(String content, LatLng latLng) {

        ArrayList<Object> objectArrayList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_OBJEKT,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ARVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_BORVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_MEDELVARDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_AVVIKELSE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ANMARKNING,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE,};

        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        String lat = String.valueOf(latitude);
        String lng = String.valueOf(longitude);

        String whereClause;
        String[] whereArgs;

        whereClause = ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED + " =? AND " +
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS + " like? AND " +
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE + " =? AND " +
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE + " =?";
        whereArgs = new String[]{"0", "%" + content + "%",lat,lng};

        Cursor cursor = databaseForPRMLjudmatning.query(
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.TABLE_NAME_PRM_LJUDMATNING_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID);
            int platsColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS);
            int objektColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_OBJEKT);
            int arvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ARVARDE);
            int borvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_BORVARDE);
            int medelvardeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_MEDELVARDE);
            int avvikelseColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_AVVIKELSE);
            int anmarkningColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ANMARKNING);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED);
            int latColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                int id = cursor.getInt(idColumnIndex);
                String plats = cursor.getString(platsColumnIndex);
                String objekt = cursor.getString(objektColumnIndex);
                String arvarde = cursor.getString(arvardeColumnIndex);
                String borvarde = cursor.getString(borvardeColumnIndex);
                String medelvarde = cursor.getString(medelvardeColumnIndex);
                String avvikelse = cursor.getString(avvikelseColumnIndex);
                String anmarkning = cursor.getString(anmarkningColumnIndex);
                int completedStatus = cursor.getInt(completedColumnIndex);
                double latitudeV = cursor.getDouble(latColumnIndex);
                double longitudeV = cursor.getDouble(longColumnIndex);

                Object object = new Object();

                object.setIdRow(id);
                object.setPlats(plats);
                object.setObjektForPRMLjudmatning(objekt);
                object.setArvarde(arvarde);
                object.setBorvarde(borvarde);
                object.setMedelvarde(medelvarde);
                object.setAvvikelse(avvikelse);
                object.setAnmarkning(anmarkning);
                object.setCompleted(completedStatus);
                object.setLatitude(latitudeV);
                object.setLongitude(longitudeV);
                object.setPrmType(0);

                objectArrayList.add(object);
            }
        } finally {
            cursor.close();
        }

        return objectArrayList;
    }

    public int countPRMLjudmatningForAreaMarker(LatLng latLng) {

        int numberOfObjects = 0;

        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause = ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE + " =?";
        String[] whereArgs = {lat, lng};

        Cursor cursor = databaseForPRMLjudmatning.query(
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.TABLE_NAME_PRM_LJUDMATNING_OBJECTS,   // The table to query
                null,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            while (cursor.moveToNext()) {
                numberOfObjects++;
            }
        } finally {
            cursor.close();
            return numberOfObjects;
        }
    }

    public void updateOnePRMLjudmatningObject(int idRow, String plats, String objektForPRMLjudmatning, String medelvarde, String avvikelse, String anmarkning,
                                              String editor, int completed) {

        ContentValues values = new ContentValues();
        values.put(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS, plats);
        values.put(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_OBJEKT, objektForPRMLjudmatning);
        values.put(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_MEDELVARDE, medelvarde);
        values.put(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_AVVIKELSE, avvikelse);
        values.put(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ANMARKNING, anmarkning);
        values.put(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_EDITOR_NAME, editor);
        values.put(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_COMPLETED, completed);

        String whereClause = ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID + " = ?";
        String idRowText = String.valueOf(idRow);
        String[] whereArgs = {idRowText};

        databaseForPRMLjudmatning.update(ContractObjectsDBForPRMLjudmatning.ObjectEntry.TABLE_NAME_PRM_LJUDMATNING_OBJECTS, values, whereClause, whereArgs);
    }

    public void updateRowsPRMLjudmatningObject(int idRow, String arvarde) {

        ContentValues values = new ContentValues();
        values.put(ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_ARVARDE, arvarde);

        String whereClause = ContractObjectsDBForPRMLjudmatning.ObjectEntry._ID + " = ?";
        String idRowText = String.valueOf(idRow);
        String[] whereArgs = {idRowText};

        databaseForPRMLjudmatning.update(ContractObjectsDBForPRMLjudmatning.ObjectEntry.TABLE_NAME_PRM_LJUDMATNING_OBJECTS, values, whereClause, whereArgs);
    }

    public boolean checkForProjectNameFromPRMLjudmatning(LatLng latLng, String projectName) {

        boolean exists = false;

        String[] projection = {
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE};

        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause = ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_PLATS + "=? AND " +
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.COLUMN_LONGITUDE + " =?";
        String[] whereArgs = new String[]{projectName, lat, lng};

        Cursor cursor = databaseForPRMLjudmatning.query(
                ContractObjectsDBForPRMLjudmatning.ObjectEntry.TABLE_NAME_PRM_LJUDMATNING_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);

        try {
            while (cursor.moveToNext()) {
                exists = true;
            }
        } finally {
            cursor.close();
        }
        return exists;
    }

    public void onDownGradePRMLjudmatningDatabase() {
        objectsDBHelperForPRMLjudmatning.onDowngrade(databaseForPRMLjudmatning, 1, 1);
    }


    //**************** PRM Ljusmatning*****************************

    public void createRowForPRMLjusmatning(String projektNamn, String lat, String lng, int completed, String startOrNot, int row) {

        ContentValues values = new ContentValues();
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN, projektNamn);
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW, row);
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE, "");
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1, "");
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2, "");
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3, "");
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4, "");
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5, "");
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6, "");
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7, "");
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8, "");
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9, "");
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10, "");
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11, "");
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12, "");
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13, "");
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14, "");
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED, 0);
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START, startOrNot);
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE, lat);
        values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE, lng);

        databaseForPRMLjusmatning.insert(ContractObjectsDBForPRMLjusmatning.ObjectEntry.TABLE_NAME_PRM_LJUSMATNING_OBJECTS, null, values);
    }

    public void makeACopyOfPRMLjusmatningObject(ArrayList<Object> ljusmatningList, String plats) {

        for (int i = 0; i <= 35; i++) {

            Object columns;

            if (i == 0) {
                columns = ljusmatningList.get(0);
                ContentValues values = new ContentValues();
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN, plats);
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW, columns.getRowInColumn());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE, columns.getWidthValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1, columns.getFirstValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2, columns.getSecondValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3, columns.getThirdValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4, columns.getFourthValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5, columns.getFifthValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6, columns.getSixthValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7, columns.getSeventhValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8, columns.getEightValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9, columns.getNinthValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10, columns.getTenthValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11, columns.getEleventhValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12, columns.getTwelfthValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13, columns.getThirteenthValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14, columns.getFourteenthValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED, columns.getCompleted());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START, columns.getStartValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE, columns.getLatitude());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE, columns.getLongitude());

                databaseForPRMLjusmatning.insert(ContractObjectsDBForPRMLjusmatning.ObjectEntry.TABLE_NAME_PRM_LJUSMATNING_OBJECTS, null, values);
            } else {

                columns = ljusmatningList.get(i);

                ContentValues values = new ContentValues();
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN, plats);
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW, columns.getRowInColumn());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE, columns.getWidthValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1, columns.getFirstValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2, columns.getSecondValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3, columns.getThirdValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4, columns.getFourthValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5, columns.getFifthValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6, columns.getSixthValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7, columns.getSeventhValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8, columns.getEightValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9, columns.getNinthValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10, columns.getTenthValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11, columns.getEleventhValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12, columns.getTwelfthValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13, columns.getThirteenthValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14, columns.getFourteenthValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED, columns.getCompleted());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START, columns.getStartValue());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE, columns.getLatitude());
                values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE, columns.getLongitude());

                databaseForPRMLjusmatning.insert(ContractObjectsDBForPRMLjusmatning.ObjectEntry.TABLE_NAME_PRM_LJUSMATNING_OBJECTS, null, values);
            }
        }

    }

    //För att titta om projektnamn redan existerar (unikt id)
    public boolean checkForProjectNameFromPRMLjusmatning(LatLng latLng, String projectName) {
        boolean exists = false;

        String[] projection = {
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE};

        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause = ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN + "=? AND " +
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE + " =?";
        String[] whereArgs = new String[]{projectName, lat, lng};

        Cursor cursor = databaseForPRMLjusmatning.query(
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.TABLE_NAME_PRM_LJUSMATNING_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);

        try {

            while (cursor.moveToNext()) {
                exists = true;
            }
        } finally {
            cursor.close();
        }

        return exists;
    }

    public ArrayList<Object> recoverRowsFromOneObjectFromPRMLjusmatning(String projektNamn) {

        ArrayList<Object> objectList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForPRMLjusmatning.ObjectEntry._ID,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE};

        String whereClause = ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN + " =?";
        String[] whereArgs = new String[]{projektNamn};

        Cursor cursor = databaseForPRMLjusmatning.query(
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.TABLE_NAME_PRM_LJUSMATNING_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry._ID);
            int projektNamnColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN);
            int rowColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW);
            int widthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE);
            int firstColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1);
            int secondColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2);
            int thirdColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3);
            int fourthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4);
            int fifthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5);
            int sixthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6);
            int seventhColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7);
            int eighthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8);
            int ninthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9);
            int tenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10);
            int eleventhColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11);
            int twelfthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12);
            int thirteenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13);
            int fourteenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED);
            int startColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START);
            int longitudeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE);
            int latitudeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE);

            while (cursor.moveToNext()) {

                int idValue = cursor.getInt(idColumnIndex);
                String projektNamnValue = cursor.getString(projektNamnColumnIndex);
                int rowValue = cursor.getInt(rowColumnIndex);
                String widthValue = cursor.getString(widthColumnIndex);
                String firstValue = cursor.getString(firstColumnIndex);
                String secondValue = cursor.getString(secondColumnIndex);
                String thirdValue = cursor.getString(thirdColumnIndex);
                String fourthValue = cursor.getString(fourthColumnIndex);
                String fifthValue = cursor.getString(fifthColumnIndex);
                String sixthValue = cursor.getString(sixthColumnIndex);
                String seventhValue = cursor.getString(seventhColumnIndex);
                String eightValue = cursor.getString(eighthColumnIndex);
                String ninthValue = cursor.getString(ninthColumnIndex);
                String tenthValue = cursor.getString(tenthColumnIndex);
                String eleventhValue = cursor.getString(eleventhColumnIndex);
                String twelfthValue = cursor.getString(twelfthColumnIndex);
                String thirteenthValue = cursor.getString(thirteenthColumnIndex);
                String fourteenthValue = cursor.getString(fourteenthColumnIndex);
                int completedValue = cursor.getInt(completedColumnIndex);
                String startValue = cursor.getString(startColumnIndex);
                double longitudeValue = cursor.getDouble(longitudeColumnIndex);
                double latitudeValue = cursor.getDouble(latitudeColumnIndex);

                Object object = new Object();

                object.setIdRow(idValue);
                object.setPlats(projektNamnValue);
                object.setRowInColumn(rowValue);
                object.setWidthValue(widthValue);
                object.setFirstValue(firstValue);
                object.setSecondValue(secondValue);
                object.setThirdValue(thirdValue);
                object.setFourthValue(fourthValue);
                object.setFifthValue(fifthValue);
                object.setSixthValue(sixthValue);
                object.setSeventhValue(seventhValue);
                object.setEightValue(eightValue);
                object.setNinthValue(ninthValue);
                object.setTenthValue(tenthValue);
                object.setEleventhValue(eleventhValue);
                object.setTwelfthValue(twelfthValue);
                object.setThirteenthValue(thirteenthValue);
                object.setFourteenthValue(fourteenthValue);
                object.setCompleted(completedValue);
                object.setStartValue(startValue);
                object.setLatitude(latitudeValue);
                object.setLongitude(longitudeValue);

                objectList.add(object);
            }
        } finally {
            cursor.close();
        }
        return objectList;
    }

    public ArrayList<Object> recoverAllPRMLjusmatningObjectsWithContent(LatLng latLng, String content) {

        ArrayList<Object> objectArrayList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForPRMLjusmatning.ObjectEntry._ID,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE};

        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause;
        String[] whereArgs;

        whereClause = ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN + " like? AND " +
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE + " =?";
        whereArgs = new String[]{"%" + content + "%", lat, lng};

        Cursor cursor = databaseForPRMLjusmatning.query(
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.TABLE_NAME_PRM_LJUSMATNING_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry._ID);
            int projektNamnColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN);
            int rowColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW);
            int widthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE);
            int firstColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1);
            int secondColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2);
            int thirdColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3);
            int fourthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4);
            int fifthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5);
            int sixthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6);
            int seventhColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7);
            int eighthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8);
            int ninthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9);
            int tenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10);
            int eleventhColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11);
            int twelfthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12);
            int thirteenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13);
            int fourteenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED);
            int startColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START);
            int latitudeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE);
            int longitudeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                int idValue = cursor.getInt(idColumnIndex);
                String projektNamnValue = cursor.getString(projektNamnColumnIndex);
                int rowValue = cursor.getInt(rowColumnIndex);
                String widthValue = cursor.getString(widthColumnIndex);
                String firstValue = cursor.getString(firstColumnIndex);
                String secondValue = cursor.getString(secondColumnIndex);
                String thirdValue = cursor.getString(thirdColumnIndex);
                String fourthValue = cursor.getString(fourthColumnIndex);
                String fifthValue = cursor.getString(fifthColumnIndex);
                String sixthValue = cursor.getString(sixthColumnIndex);
                String seventhValue = cursor.getString(seventhColumnIndex);
                String eightValue = cursor.getString(eighthColumnIndex);
                String ninthValue = cursor.getString(ninthColumnIndex);
                String tenthValue = cursor.getString(tenthColumnIndex);
                String eleventhValue = cursor.getString(eleventhColumnIndex);
                String twelfthValue = cursor.getString(twelfthColumnIndex);
                String thirteenthValue = cursor.getString(thirteenthColumnIndex);
                String fourteenthValue = cursor.getString(fourteenthColumnIndex);
                int completedValue = cursor.getInt(completedColumnIndex);
                String startValue = cursor.getString(startColumnIndex);
                double latitiudeValue = cursor.getDouble(latitudeColumnIndex);
                double longitudeValue = cursor.getDouble(longitudeColumnIndex);

                Object object = new Object();

                object.setIdRow(idValue);
                object.setPlats(projektNamnValue);
                object.setRowInColumn(rowValue);
                object.setWidthValue(widthValue);
                object.setFirstValue(firstValue);
                object.setSecondValue(secondValue);
                object.setThirdValue(thirdValue);
                object.setFourthValue(fourthValue);
                object.setFifthValue(fifthValue);
                object.setSixthValue(sixthValue);
                object.setSeventhValue(seventhValue);
                object.setEightValue(eightValue);
                object.setNinthValue(ninthValue);
                object.setTenthValue(tenthValue);
                object.setEleventhValue(eleventhValue);
                object.setTwelfthValue(twelfthValue);
                object.setThirteenthValue(thirteenthValue);
                object.setFourteenthValue(fourteenthValue);
                object.setCompleted(completedValue);
                object.setStartValue(startValue);
                object.setPrmType(1);

                object.setLatitude(latitiudeValue);
                object.setLongitude(longitudeValue);

                objectArrayList.add(object);
            }
        } finally {
            cursor.close();
        }
        return objectArrayList;
    }

    public ArrayList<Object> recoverAllPRMLjusmatningObjects(String content) {

        ArrayList<Object> objectArrayList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForPRMLjusmatning.ObjectEntry._ID,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE};

        String whereClause;
        String[] whereArgs;

        whereClause = ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN + " like?";
        whereArgs = new String[]{"%" + content + "%"};

        Cursor cursor = databaseForPRMLjusmatning.query(
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.TABLE_NAME_PRM_LJUSMATNING_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry._ID);
            int projektNamnColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN);
            int rowColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW);
            int widthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE);
            int firstColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1);
            int secondColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2);
            int thirdColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3);
            int fourthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4);
            int fifthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5);
            int sixthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6);
            int seventhColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7);
            int eighthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8);
            int ninthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9);
            int tenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10);
            int eleventhColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11);
            int twelfthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12);
            int thirteenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13);
            int fourteenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED);
            int startColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START);
            int latitudeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE);
            int longitudeColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                int idValue = cursor.getInt(idColumnIndex);
                String projektNamnValue = cursor.getString(projektNamnColumnIndex);
                int rowValue = cursor.getInt(rowColumnIndex);
                String widthValue = cursor.getString(widthColumnIndex);
                String firstValue = cursor.getString(firstColumnIndex);
                String secondValue = cursor.getString(secondColumnIndex);
                String thirdValue = cursor.getString(thirdColumnIndex);
                String fourthValue = cursor.getString(fourthColumnIndex);
                String fifthValue = cursor.getString(fifthColumnIndex);
                String sixthValue = cursor.getString(sixthColumnIndex);
                String seventhValue = cursor.getString(seventhColumnIndex);
                String eightValue = cursor.getString(eighthColumnIndex);
                String ninthValue = cursor.getString(ninthColumnIndex);
                String tenthValue = cursor.getString(tenthColumnIndex);
                String eleventhValue = cursor.getString(eleventhColumnIndex);
                String twelfthValue = cursor.getString(twelfthColumnIndex);
                String thirteenthValue = cursor.getString(thirteenthColumnIndex);
                String fourteenthValue = cursor.getString(fourteenthColumnIndex);
                int completedValue = cursor.getInt(completedColumnIndex);
                String startValue = cursor.getString(startColumnIndex);
                double latitiudeValue = cursor.getDouble(latitudeColumnIndex);
                double longitudeValue = cursor.getDouble(longitudeColumnIndex);

                Object object = new Object();

                object.setIdRow(idValue);
                object.setPlats(projektNamnValue);
                object.setRowInColumn(rowValue);
                object.setWidthValue(widthValue);
                object.setFirstValue(firstValue);
                object.setSecondValue(secondValue);
                object.setThirdValue(thirdValue);
                object.setFourthValue(fourthValue);
                object.setFifthValue(fifthValue);
                object.setSixthValue(sixthValue);
                object.setSeventhValue(seventhValue);
                object.setEightValue(eightValue);
                object.setNinthValue(ninthValue);
                object.setTenthValue(tenthValue);
                object.setEleventhValue(eleventhValue);
                object.setTwelfthValue(twelfthValue);
                object.setThirteenthValue(thirteenthValue);
                object.setFourteenthValue(fourteenthValue);
                object.setCompleted(completedValue);
                object.setStartValue(startValue);
                object.setPrmType(1);

                object.setLatitude(latitiudeValue);
                object.setLongitude(longitudeValue);

                objectArrayList.add(object);
            }
        } finally {
            cursor.close();
        }
        return objectArrayList;
    }

    public ArrayList<Object> recoverAllPRMLjusmatningObjectsWithContentAndCheckedMarker(LatLng latLng, String content) {

        ArrayList<Object> objectArrayList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForPRMLjusmatning.ObjectEntry._ID,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE};


        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause;
        String[] whereArgs;

        whereClause = ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN + " like? AND " +
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED + " =? AND " +
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE + " =?";

        whereArgs = new String[]{"%" + content + "%", "1", lat, lng};

        Cursor cursor = databaseForPRMLjusmatning.query(
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.TABLE_NAME_PRM_LJUSMATNING_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry._ID);
            int projektNamnColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN);
            int rowColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW);
            int widthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE);
            int firstColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1);
            int secondColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2);
            int thirdColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3);
            int fourthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4);
            int fifthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5);
            int sixthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6);
            int seventhColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7);
            int eighthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8);
            int ninthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9);
            int tenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10);
            int eleventhColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11);
            int twelfthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12);
            int thirteenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13);
            int fourteenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED);
            int startColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START);

            while (cursor.moveToNext()) {

                int idValue = cursor.getInt(idColumnIndex);
                String projektNamnValue = cursor.getString(projektNamnColumnIndex);
                int rowValue = cursor.getInt(rowColumnIndex);
                String widthValue = cursor.getString(widthColumnIndex);
                String firstValue = cursor.getString(firstColumnIndex);
                String secondValue = cursor.getString(secondColumnIndex);
                String thirdValue = cursor.getString(thirdColumnIndex);
                String fourthValue = cursor.getString(fourthColumnIndex);
                String fifthValue = cursor.getString(fifthColumnIndex);
                String sixthValue = cursor.getString(sixthColumnIndex);
                String seventhValue = cursor.getString(seventhColumnIndex);
                String eightValue = cursor.getString(eighthColumnIndex);
                String ninthValue = cursor.getString(ninthColumnIndex);
                String tenthValue = cursor.getString(tenthColumnIndex);
                String eleventhValue = cursor.getString(eleventhColumnIndex);
                String twelfthValue = cursor.getString(twelfthColumnIndex);
                String thirteenthValue = cursor.getString(thirteenthColumnIndex);
                String fourteenthValue = cursor.getString(fourteenthColumnIndex);
                int completedValue = cursor.getInt(completedColumnIndex);
                String startValue = cursor.getString(startColumnIndex);


                Object object = new Object();

                object.setIdRow(idValue);
                object.setPlats(projektNamnValue);
                object.setRowInColumn(rowValue);
                object.setWidthValue(widthValue);
                object.setFirstValue(firstValue);
                object.setSecondValue(secondValue);
                object.setThirdValue(thirdValue);
                object.setFourthValue(fourthValue);
                object.setFifthValue(fifthValue);
                object.setSixthValue(sixthValue);
                object.setSeventhValue(seventhValue);
                object.setEightValue(eightValue);
                object.setNinthValue(ninthValue);
                object.setTenthValue(tenthValue);
                object.setEleventhValue(eleventhValue);
                object.setTwelfthValue(twelfthValue);
                object.setThirteenthValue(thirteenthValue);
                object.setFourteenthValue(fourteenthValue);
                object.setCompleted(completedValue);
                object.setStartValue(startValue);

                object.setLatitude(l1);
                object.setLongitude(l2);

                objectArrayList.add(object);
            }

        } finally {
            cursor.close();
        }
        return objectArrayList;
    }

    public ArrayList<Object> recoverAllPRMLjusmatningObjectsWithContentAndNotCheckedMarker(LatLng latLng, String content) {

        ArrayList<Object> objectArrayList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForPRMLjusmatning.ObjectEntry._ID,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE};


        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause;
        String[] whereArgs;

        whereClause = ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN + " like? AND " +
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED + " =? AND " +
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE + " =?";

        whereArgs = new String[]{"%" + content + "%", "0", lat, lng};

        Cursor cursor = databaseForPRMLjusmatning.query(
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.TABLE_NAME_PRM_LJUSMATNING_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry._ID);
            int projektNamnColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN);
            int rowColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW);
            int widthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE);
            int firstColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1);
            int secondColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2);
            int thirdColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3);
            int fourthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4);
            int fifthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5);
            int sixthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6);
            int seventhColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7);
            int eighthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8);
            int ninthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9);
            int tenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10);
            int eleventhColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11);
            int twelfthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12);
            int thirteenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13);
            int fourteenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED);
            int startColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START);

            while (cursor.moveToNext()) {

                int idValue = cursor.getInt(idColumnIndex);
                String projektNamnValue = cursor.getString(projektNamnColumnIndex);
                int rowValue = cursor.getInt(rowColumnIndex);
                String widthValue = cursor.getString(widthColumnIndex);
                String firstValue = cursor.getString(firstColumnIndex);
                String secondValue = cursor.getString(secondColumnIndex);
                String thirdValue = cursor.getString(thirdColumnIndex);
                String fourthValue = cursor.getString(fourthColumnIndex);
                String fifthValue = cursor.getString(fifthColumnIndex);
                String sixthValue = cursor.getString(sixthColumnIndex);
                String seventhValue = cursor.getString(seventhColumnIndex);
                String eightValue = cursor.getString(eighthColumnIndex);
                String ninthValue = cursor.getString(ninthColumnIndex);
                String tenthValue = cursor.getString(tenthColumnIndex);
                String eleventhValue = cursor.getString(eleventhColumnIndex);
                String twelfthValue = cursor.getString(twelfthColumnIndex);
                String thirteenthValue = cursor.getString(thirteenthColumnIndex);
                String fourteenthValue = cursor.getString(fourteenthColumnIndex);
                int completedValue = cursor.getInt(completedColumnIndex);
                String startValue = cursor.getString(startColumnIndex);


                Object object = new Object();

                object.setIdRow(idValue);
                object.setPlats(projektNamnValue);
                object.setRowInColumn(rowValue);
                object.setWidthValue(widthValue);
                object.setFirstValue(firstValue);
                object.setSecondValue(secondValue);
                object.setThirdValue(thirdValue);
                object.setFourthValue(fourthValue);
                object.setFifthValue(fifthValue);
                object.setSixthValue(sixthValue);
                object.setSeventhValue(seventhValue);
                object.setEightValue(eightValue);
                object.setNinthValue(ninthValue);
                object.setTenthValue(tenthValue);
                object.setEleventhValue(eleventhValue);
                object.setTwelfthValue(twelfthValue);
                object.setThirteenthValue(thirteenthValue);
                object.setFourteenthValue(fourteenthValue);
                object.setCompleted(completedValue);
                object.setStartValue(startValue);

                object.setLatitude(l1);
                object.setLongitude(l2);

                objectArrayList.add(object);
            }

        } finally {
            cursor.close();
        }
        return objectArrayList;
    }

    public void updateOneRowFromPRMLjusmatning(Object object, String plats, int row, boolean columns) {

        if (columns == true) {
            ContentValues values = new ContentValues();
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1, object.getFirstValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2, object.getSecondValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3, object.getThirdValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4, object.getFourthValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5, object.getFifthValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6, object.getSixthValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7, object.getSeventhValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8, object.getEightValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9, object.getNinthValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10, object.getTenthValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11, object.getEleventhValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12, object.getTwelfthValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13, object.getThirteenthValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14, object.getFourteenthValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED, object.getCompleted());

            String whereClause = ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN + " = ? AND " +
                    ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START + " =? ";
            String[] whereArgs = {plats, "start"};

            databaseForPRMLjusmatning.update(ContractObjectsDBForPRMLjusmatning.ObjectEntry.TABLE_NAME_PRM_LJUSMATNING_OBJECTS, values, whereClause, whereArgs);
        } else {
            ContentValues values = new ContentValues();
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE, object.getWidthValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1, object.getFirstValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2, object.getSecondValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3, object.getThirdValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4, object.getFourthValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5, object.getFifthValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6, object.getSixthValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7, object.getSeventhValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8, object.getEightValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9, object.getNinthValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10, object.getTenthValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11, object.getEleventhValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12, object.getTwelfthValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13, object.getThirteenthValue());
            values.put(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14, object.getFourteenthValue());

            String rowInColumn = String.valueOf(row);
            String whereClause = ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN + " = ? AND " +
                    ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW + " =? ";
            String[] whereArgs = {plats, rowInColumn};

            databaseForPRMLjusmatning.update(ContractObjectsDBForPRMLjusmatning.ObjectEntry.TABLE_NAME_PRM_LJUSMATNING_OBJECTS, values, whereClause, whereArgs);
        }
    }

    //För att hämta endast start row och skapa objekt i listview
    public ArrayList<Object> recoverAllRowsFromPRMLjusmatning(LatLng latLng, Boolean objectsReady) {

        ArrayList<Object> objectList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForPRMLjusmatning.ObjectEntry._ID,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE};

        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause;
        String[] whereArgs;

        if (objectsReady == true) {
            whereClause = ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED + "=? AND " +
                    ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                    ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE + " =?";
            whereArgs = new String[]{"1", lat, lng};
        } else {
            whereClause = ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                    ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE + " =?";
            whereArgs = new String[]{lat, lng};
        }
        Cursor cursor = databaseForPRMLjusmatning.query(
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.TABLE_NAME_PRM_LJUSMATNING_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry._ID);
            int projektNamnColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN);
            int rowColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW);
            int textColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE);
            int firstColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1);
            int secondColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2);
            int thirdColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3);
            int fourthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4);
            int fifthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5);
            int sixthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6);
            int seventhColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7);
            int eighthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8);
            int ninthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9);
            int tenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10);
            int eleventhColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11);
            int twelfthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12);
            int thirteenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13);
            int fourteenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED);
            int startColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START);

            while (cursor.moveToNext()) {

                int idValue = cursor.getInt(idColumnIndex);
                String projektNamnValue = cursor.getString(projektNamnColumnIndex);
                int rowValue = cursor.getInt(rowColumnIndex);
                String textValue = cursor.getString(textColumnIndex);
                String firstValue = cursor.getString(firstColumnIndex);
                String secondValue = cursor.getString(secondColumnIndex);
                String thirdValue = cursor.getString(thirdColumnIndex);
                String fourthValue = cursor.getString(fourthColumnIndex);
                String fifthValue = cursor.getString(fifthColumnIndex);
                String sixthValue = cursor.getString(sixthColumnIndex);
                String seventhValue = cursor.getString(seventhColumnIndex);
                String eightValue = cursor.getString(eighthColumnIndex);
                String ninthValue = cursor.getString(ninthColumnIndex);
                String tenthValue = cursor.getString(tenthColumnIndex);
                String eleventhValue = cursor.getString(eleventhColumnIndex);
                String twelfthValue = cursor.getString(twelfthColumnIndex);
                String thirteenthValue = cursor.getString(thirteenthColumnIndex);
                String fourteenthValue = cursor.getString(fourteenthColumnIndex);
                int completedValue = cursor.getInt(completedColumnIndex);
                String startValue = cursor.getString(startColumnIndex);

                Object object = new Object();

                object.setIdRow(idValue);
                object.setPlats(projektNamnValue);
                object.setRowInColumn(rowValue);
                object.setTextValue(textValue);
                object.setFirstValue(firstValue);
                object.setSecondValue(secondValue);
                object.setThirdValue(thirdValue);
                object.setFourthValue(fourthValue);
                object.setFifthValue(fifthValue);
                object.setSixthValue(sixthValue);
                object.setSeventhValue(seventhValue);
                object.setEightValue(eightValue);
                object.setNinthValue(ninthValue);
                object.setTenthValue(tenthValue);
                object.setEleventhValue(eleventhValue);
                object.setTwelfthValue(twelfthValue);
                object.setThirteenthValue(thirteenthValue);
                object.setFourteenthValue(fourteenthValue);
                object.setCompleted(completedValue);
                object.setStartValue(startValue);
                object.setPrmType(1);

                object.setLatitude(l1);
                object.setLongitude(l2);

                objectList.add(object);
            }
        } finally {
            cursor.close();
        }
        return objectList;
    }

    public ArrayList<Object> recoverNotReadyRowsFromPRMLjusmatning(LatLng latLng) {

        ArrayList<Object> objectList = new ArrayList<>();

        String[] projection = {
                ContractObjectsDBForPRMLjusmatning.ObjectEntry._ID,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE,
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE};

        Double l1 = latLng.latitude;
        Double l2 = latLng.longitude;
        String lat = Double.toString(l1);
        String lng = Double.toString(l2);

        String whereClause;
        String[] whereArgs;


        whereClause = ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED + "=? AND " +
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LATITUDE + "=? AND " +
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_LONGITUDE + " =?";
        whereArgs = new String[]{"0", lat, lng};

        Cursor cursor = databaseForPRMLjusmatning.query(
                ContractObjectsDBForPRMLjusmatning.ObjectEntry.TABLE_NAME_PRM_LJUSMATNING_OBJECTS,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {

            int idColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry._ID);
            int projektNamnColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN);
            int rowColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_ROW);
            int textColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_WIDTH_VALUE);
            int firstColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_1);
            int secondColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_2);
            int thirdColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_3);
            int fourthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_4);
            int fifthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_5);
            int sixthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_6);
            int seventhColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_7);
            int eighthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_8);
            int ninthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_9);
            int tenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_10);
            int eleventhColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_11);
            int twelfthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_12);
            int thirteenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_13);
            int fourteenthColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_14);
            int completedColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_COMPLETED);
            int startColumnIndex = cursor.getColumnIndex(ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_START);

            while (cursor.moveToNext()) {

                int idValue = cursor.getInt(idColumnIndex);
                String projektNamnValue = cursor.getString(projektNamnColumnIndex);
                int rowValue = cursor.getInt(rowColumnIndex);
                String textValue = cursor.getString(textColumnIndex);
                String firstValue = cursor.getString(firstColumnIndex);
                String secondValue = cursor.getString(secondColumnIndex);
                String thirdValue = cursor.getString(thirdColumnIndex);
                String fourthValue = cursor.getString(fourthColumnIndex);
                String fifthValue = cursor.getString(fifthColumnIndex);
                String sixthValue = cursor.getString(sixthColumnIndex);
                String seventhValue = cursor.getString(seventhColumnIndex);
                String eightValue = cursor.getString(eighthColumnIndex);
                String ninthValue = cursor.getString(ninthColumnIndex);
                String tenthValue = cursor.getString(tenthColumnIndex);
                String eleventhValue = cursor.getString(eleventhColumnIndex);
                String twelfthValue = cursor.getString(twelfthColumnIndex);
                String thirteenthValue = cursor.getString(thirteenthColumnIndex);
                String fourteenthValue = cursor.getString(fourteenthColumnIndex);
                int completedValue = cursor.getInt(completedColumnIndex);
                String startValue = cursor.getString(startColumnIndex);

                Object object = new Object();

                object.setIdRow(idValue);
                object.setPlats(projektNamnValue);
                object.setRowInColumn(rowValue);
                object.setTextValue(textValue);
                object.setFirstValue(firstValue);
                object.setSecondValue(secondValue);
                object.setThirdValue(thirdValue);
                object.setFourthValue(fourthValue);
                object.setFifthValue(fifthValue);
                object.setSixthValue(sixthValue);
                object.setSeventhValue(seventhValue);
                object.setEightValue(eightValue);
                object.setNinthValue(ninthValue);
                object.setTenthValue(tenthValue);
                object.setEleventhValue(eleventhValue);
                object.setTwelfthValue(twelfthValue);
                object.setThirteenthValue(thirteenthValue);
                object.setFourteenthValue(fourteenthValue);
                object.setCompleted(completedValue);
                object.setStartValue(startValue);
                object.setPrmType(1);

                object.setLatitude(l1);
                object.setLongitude(l2);

                objectList.add(object);
            }
        } finally {
            cursor.close();
        }
        return objectList;
    }

    public void deleteObjectPRMLjusmatning(String plats) {
        String whereClause = ContractObjectsDBForPRMLjusmatning.ObjectEntry.COLUMN_PROJEKTNAMN + "=?";
        String[] whereArgs = {plats};
        databaseForPRMLjusmatning.delete(ContractObjectsDBForPRMLjusmatning.ObjectEntry.TABLE_NAME_PRM_LJUSMATNING_OBJECTS, whereClause, whereArgs);
    }

    public void onDownGradePRMLjusmatningDatabase() {
        objectsDBHelperForPRMLjusmatning.onDowngrade(databaseForPRMLjusmatning, 1, 1);
    }

    //******************         Property list    **********************************
    public void createPropertyList() {

        PropertyListObjects propertyListObject = new PropertyListObjects();

        ContentValues values = new ContentValues();
        values.put(ContractPropertyListDB.PropertyListEntry._ID, "1");
        //************ 4.2.1.1 Parkeringsmöjligheter för funktionshindrade *************1
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_1_1, propertyListObject.getParkeringsmojligheterForFunktionshindrade());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_1_1_EXTRA_OBJECTS, propertyListObject.getParkeringsmojligheterForFunktionshindradeExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_1_2, propertyListObject.getPlaceringAvParkeringForFunktionshindrade());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_1_2_EXtRA_OBJECtS, propertyListObject.getPlaceringAvParkeringForFunktionshindradeExtraObjects());

        //************ 4.2.1.2 Hinderfri gångväg*************2
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_2_1, propertyListObject.getForekomstAvHinderfriGangvag());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_2_1_EXTRA_OBJECTS, propertyListObject.getForekomstAvHinderfriGangvagExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_2_2, propertyListObject.getLangdenPaHindergriaGangvagar());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_2_2_EXTRA_OBJECTS, propertyListObject.getLangdenPaHindergriaGangvagarExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_2_3, propertyListObject.getReflekterandeEgenskaper());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_2_3_EXTRA_OBJECTS, propertyListObject.getReflekterandeEgenskaperExtraObjects());

        //************ 4.2.1.2.1 Horisontell förflyttning************3
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_3_1, propertyListObject.getHinderfriGangvagsbredd());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_3_1_EXTRA_OBJECTS, propertyListObject.getHinderfriGangvagsbreddExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_3_2, propertyListObject.getTrosklarPaHinderfriGangvag());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_3_2_EXTRA_OBJECTS, propertyListObject.getTrosklarPaHinderfriGangvagExtraObjects());

        //************ 4.2.1.2.2 Vertikal förflyttning************4
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_1, propertyListObject.getTrappstegsfriVag());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_1_EXTRA_OBJECTS, propertyListObject.getTrappstegsfriVagExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_2, propertyListObject.getBreddPaTrappor());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_2_EXTRA_OBJECTS, propertyListObject.getBreddPaTrapporExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_3, propertyListObject.getVisuelMarkeringPaForstaOchSistaSteget());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_3_EXTRA_OBJECTS, propertyListObject.getVisuelMarkeringPaForstaOchSistaStegetExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_4, propertyListObject.getTaktilVarningForeForstaUppgaendeTrappsteg());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_4_EXTRA_OBJECTS, propertyListObject.getTaktilVarningForeForstaUppgaendeTrappstegExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_5, propertyListObject.getRamperForPersonerMedFunktionsnedsattningar());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_5_EXTRA_OBJECTS, propertyListObject.getRamperForPersonerMedFunktionsnedsattningarExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_6, propertyListObject.getLedstangerPaBadaSidorOchTvaNivaer());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_6_EXTRA_OBJECTS, propertyListObject.getLedstangerPaBadaSidorOchTvaNivaerExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_7, propertyListObject.getHissar());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_7_EXTRA_OBJECTS, propertyListObject.getHissarExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_8, propertyListObject.getRulltrapporOchRullramper());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_8_EXTRA_OBJECTS, propertyListObject.getRulltrapporOchRullramperExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_9, propertyListObject.getPlankorsningar());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_9_EXTRA_OBJECTS, propertyListObject.getPlankorsningarExtraObjects());

        //************ 4.2.1.2.3 Gångvägsmarkering************5
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_5_1, propertyListObject.getTydligMarkering());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_5_1_EXTRA_OBJECTS, propertyListObject.getTydligMarkeringExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_5_2, propertyListObject.getTillhandahållandeAvInformationTillSynskadade());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_5_2_EXTRA_OBJECTS, propertyListObject.getTillhandahållandeAvInformationTillSynskadadeExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_5_3, propertyListObject.getFjarrstyrdaLjudanordningarEllerTeleapplikationer());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_5_3_EXTRA_OBJECTS, propertyListObject.getFjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_5_4, propertyListObject.getTaktilInformationPaLedstangerEllerVaggar());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_5_4_EXTRA_OBJECTS, propertyListObject.getTaktilInformationPaLedstangerEllerVaggarExtraObjects());

        //************ 4.2.1.4 Golvytor************6
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_6_1, propertyListObject.getHalksakerhet());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_6_1_EXTRA_OBJECTS, propertyListObject.getHalksakerhetExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_6_2, propertyListObject.getOjamnheterSomOverstiger());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_6_2_EXTRA_OBJECTS, propertyListObject.getOjamnheterSomOverstigerExtraObjects());

        //************ 4.2.1.5 Markering av genomskinliga hinder************7
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_7_1, propertyListObject.getGlasdorrarEllerGenomskinligaVaggarLangsGangvagar());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_7_1_EXTRA_OBJECTS, propertyListObject.getGlasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects());

        //************ 4.2.1.6 Toaletter och skötplatser************8
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_8_1, propertyListObject.getToalettutrymmeAnpassatForRullstolsburnaPersoner());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_8_1_EXTRA_OBJECTS, propertyListObject.getToalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_8_2, propertyListObject.getSkotplatserTillgangligaForBadeKonen());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_8_2_EXTRA_OBJECTS, propertyListObject.getSkotplatserTillgangligaForBadeKonenExtraObjects());

        //************ 4.2.1.7 Inredning och fristående enheter************9
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_9_1, propertyListObject.getKontrastMotBakgrundOchAvrundandeKanter());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_9_1_EXTRA_OBJECTS, propertyListObject.getKontrastMotBakgrundOchAvrundandeKanterExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_9_2, propertyListObject.getPlaceringAvInredningOchEnheter());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_9_2_EXTRA_OBJECTS, propertyListObject.getPlaceringAvInredningOchEnheterExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_9_3, propertyListObject.getSittmojligheterOchPlatsForEnRullstolsbundenPerson());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_9_3_EXTRA_OBJECTS, propertyListObject.getSittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_9_4, propertyListObject.getVaderskyddatOmrade());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_9_4_EXTRA_OBJECTS, propertyListObject.getVaderskyddatOmradeExtraObjects());

        //************4.2.1.9 Belysning************10
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_10_1, propertyListObject.getBelysningPaStationensExternaOmraden());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_10_1_EXTRA_OBJECTS, propertyListObject.getBelysningPaStationensExternaOmradenExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_10_2, propertyListObject.getBelysningLangsHinderfriaGangvagar());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_10_2_EXTRA_OBJECTS, propertyListObject.getBelysningLangsHinderfriaGangvagarExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_10_3, propertyListObject.getBelysningPaPlattformar());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_10_3_EXTRA_OBJECTS, propertyListObject.getBelysningPaPlattformarExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_10_4, propertyListObject.getNodbelysning());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_10_4_EXTRA_OBJECTS, propertyListObject.getNodbelysningExtraObjects());

        //************4.2.1.10 Visuell information************11
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_1, propertyListObject.getVisuell_information_row1());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_1_EXTRA_OBJECTS, propertyListObject.getVisuell_information_row2());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_2, propertyListObject.getVisuell_information_row3());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_2_EXTRA_OBJECTS, propertyListObject.getVisuell_information_row4());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_3, propertyListObject.getVisuell_information_row5());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_3_EXTRA_OBJECTS, propertyListObject.getVisuell_information_row6());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_4, propertyListObject.getVisuell_information_row7());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_4_EXTRA_OBJECTS, propertyListObject.getVisuell_information_row8());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_5, propertyListObject.getVisuell_information_row9());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_5_EXTRA_OBJECTS, propertyListObject.getVisuell_information_row10());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_6, propertyListObject.getVisuell_information_row11());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_6_EXTRA_OBJECTS, propertyListObject.getVisuell_information_row12());

        //************4.2.1.11 Talad information Sidoplattform***********12
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_12_1, propertyListObject.getStipaNiva());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_12_1_EXTRA_OBJECTS, propertyListObject.getStipaNivaExtraObjects());

        //************4.2.1.12 Plattformsbredd och plattformskant************13
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_1, propertyListObject.getForekomstAvRiskomrade());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_1_EXTRA_OBJECTS, propertyListObject.getForekomstAvRiskomradeExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_2, propertyListObject.getPlattformsMinstaBredd());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_2_EXTRA_OBJECTS, propertyListObject.getPlattformsMinstaBreddExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_3, propertyListObject.getAvstandMellanLitetHinder());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_3_EXTRA_OBJECTS, propertyListObject.getAvstandMellanLitetHinderExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_4, propertyListObject.getAvstandMellanStortHinder());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_4_EXTRA_OBJECTS, propertyListObject.getAvstandMellanStortHinderExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_5, propertyListObject.getMarkeringAvRiskomradetsGrans());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_5_EXTRA_OBJECTS, propertyListObject.getMarkeringAvRiskomradetsGransExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_6, propertyListObject.getBreddenPaVarningslinjeOchHalksakerhetOchFarg());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_6_EXTRA_OBJECTS, propertyListObject.getBreddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_7, propertyListObject.getMaterialPaPlattformskanten());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_7_EXTRA_OBJECTS, propertyListObject.getMaterialPaPlattformskantenExtraObjects());

        //************4.2.1.13 Plattformens slut************14
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_14_1, propertyListObject.getMarkeringAvPlattformensSlut());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_14_1_EXTRA_OBJECTS, propertyListObject.getMarkeringAvPlattformensSlutExtraObjects());

        //************4.2.1.15 Spårkorsning för passagerare påväg till plattformar************15
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_1, propertyListObject.getAnvandsSomEnDelAvTrappstegfriGangvag());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_1_EXTRA_OBJECTS, propertyListObject.getAnvandsSomEnDelAvTrappstegfriGangvagExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_2, propertyListObject.getBreddPaGangvagg());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_2_EXTRA_OBJECTS, propertyListObject.getBreddPaGangvaggExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_3, propertyListObject.getLutning());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_3_EXTRA_OBJECTS, propertyListObject.getLutningExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_4, propertyListObject.getFriPassageForMinstaHjuletPaEnRullstol());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_4_EXTRA_OBJECTS, propertyListObject.getFriPassageForMinstaHjuletPaEnRullstolExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_5, propertyListObject.getFriPassageOmSakerhetschikanerForekommer());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_5_EXTRA_OBJECTS, propertyListObject.getFriPassageOmSakerhetschikanerForekommerExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_6, propertyListObject.getMarkeringAvGangbaneytan());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_6_EXTRA_OBJECTS, propertyListObject.getMarkeringAvGangbaneytanExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_7, propertyListObject.getSakerPassage());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_7_EXTRA_OBJECTS, propertyListObject.getSakerPassageExtraObjects());

        databaseForPropertyList.insert(ContractPropertyListDB.PropertyListEntry.TABLE_NAME_PROPERTY_LIST, null, values);
    }

    public PropertyListObjects recoverPropertyList() {

        PropertyListObjects newPropertyListObject = new PropertyListObjects();

        String[] projection = {
                ContractPropertyListDB.PropertyListEntry._ID,
                //************ 4.2.1.1 Parkeringsmöjligheter för funktionshindrade *************1
                ContractPropertyListDB.PropertyListEntry.COLUMN_1_1,
                ContractPropertyListDB.PropertyListEntry.COLUMN_1_1_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_1_2,
                ContractPropertyListDB.PropertyListEntry.COLUMN_1_2_EXtRA_OBJECtS,

                //************ 4.2.1.2 Hinderfri gångväg*************2
                ContractPropertyListDB.PropertyListEntry.COLUMN_2_1,
                ContractPropertyListDB.PropertyListEntry.COLUMN_2_1_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_2_2,
                ContractPropertyListDB.PropertyListEntry.COLUMN_2_2_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_2_3,
                ContractPropertyListDB.PropertyListEntry.COLUMN_2_3_EXTRA_OBJECTS,

                //************ 4.2.1.2.1 Horisontell förflyttning************3
                ContractPropertyListDB.PropertyListEntry.COLUMN_3_1,
                ContractPropertyListDB.PropertyListEntry.COLUMN_3_1_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_3_2,
                ContractPropertyListDB.PropertyListEntry.COLUMN_3_2_EXTRA_OBJECTS,

                //************ 4.2.1.2.2 Vertikal förflyttning************4
                ContractPropertyListDB.PropertyListEntry.COLUMN_4_1,
                ContractPropertyListDB.PropertyListEntry.COLUMN_4_1_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_4_2,
                ContractPropertyListDB.PropertyListEntry.COLUMN_4_2_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_4_3,
                ContractPropertyListDB.PropertyListEntry.COLUMN_4_3_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_4_4,
                ContractPropertyListDB.PropertyListEntry.COLUMN_4_4_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_4_5,
                ContractPropertyListDB.PropertyListEntry.COLUMN_4_5_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_4_6,
                ContractPropertyListDB.PropertyListEntry.COLUMN_4_6_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_4_7,
                ContractPropertyListDB.PropertyListEntry.COLUMN_4_7_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_4_8,
                ContractPropertyListDB.PropertyListEntry.COLUMN_4_8_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_4_9,
                ContractPropertyListDB.PropertyListEntry.COLUMN_4_9_EXTRA_OBJECTS,

                //************ 4.2.1.2.3 Gångvägsmarkering************5
                ContractPropertyListDB.PropertyListEntry.COLUMN_5_1,
                ContractPropertyListDB.PropertyListEntry.COLUMN_5_1_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_5_2,
                ContractPropertyListDB.PropertyListEntry.COLUMN_5_2_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_5_3,
                ContractPropertyListDB.PropertyListEntry.COLUMN_5_3_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_5_4,
                ContractPropertyListDB.PropertyListEntry.COLUMN_5_4_EXTRA_OBJECTS,

                //************ 4.2.1.4 Golvytor************6
                ContractPropertyListDB.PropertyListEntry.COLUMN_6_1,
                ContractPropertyListDB.PropertyListEntry.COLUMN_6_1_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_6_2,
                ContractPropertyListDB.PropertyListEntry.COLUMN_6_2_EXTRA_OBJECTS,

                //************ 4.2.1.5 Markering av genomskinliga hinder************7
                ContractPropertyListDB.PropertyListEntry.COLUMN_7_1,
                ContractPropertyListDB.PropertyListEntry.COLUMN_7_1_EXTRA_OBJECTS,

                //************ 4.2.1.6 Toaletter och skötplatser************8
                ContractPropertyListDB.PropertyListEntry.COLUMN_8_1,
                ContractPropertyListDB.PropertyListEntry.COLUMN_8_1_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_8_2,
                ContractPropertyListDB.PropertyListEntry.COLUMN_8_2_EXTRA_OBJECTS,

                //************ 4.2.1.7 Inredning och fristående enheter************9
                ContractPropertyListDB.PropertyListEntry.COLUMN_9_1,
                ContractPropertyListDB.PropertyListEntry.COLUMN_9_1_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_9_2,
                ContractPropertyListDB.PropertyListEntry.COLUMN_9_2_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_9_3,
                ContractPropertyListDB.PropertyListEntry.COLUMN_9_3_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_9_4,
                ContractPropertyListDB.PropertyListEntry.COLUMN_9_4_EXTRA_OBJECTS,

                //************4.2.1.9 Belysning************10
                ContractPropertyListDB.PropertyListEntry.COLUMN_10_1,
                ContractPropertyListDB.PropertyListEntry.COLUMN_10_1_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_10_2,
                ContractPropertyListDB.PropertyListEntry.COLUMN_10_2_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_10_3,
                ContractPropertyListDB.PropertyListEntry.COLUMN_10_3_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_10_4,
                ContractPropertyListDB.PropertyListEntry.COLUMN_10_4_EXTRA_OBJECTS,

                //************4.2.1.10 Visuell information************11
                ContractPropertyListDB.PropertyListEntry.COLUMN_11_1,
                ContractPropertyListDB.PropertyListEntry.COLUMN_11_1_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_11_2,
                ContractPropertyListDB.PropertyListEntry.COLUMN_11_2_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_11_3,
                ContractPropertyListDB.PropertyListEntry.COLUMN_11_3_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_11_4,
                ContractPropertyListDB.PropertyListEntry.COLUMN_11_4_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_11_5,
                ContractPropertyListDB.PropertyListEntry.COLUMN_11_5_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_11_6,
                ContractPropertyListDB.PropertyListEntry.COLUMN_11_6_EXTRA_OBJECTS,

                //************4.2.1.11 Talad information Sidoplattform***********12
                ContractPropertyListDB.PropertyListEntry.COLUMN_12_1,
                ContractPropertyListDB.PropertyListEntry.COLUMN_12_1_EXTRA_OBJECTS,

                //************4.2.1.12 Plattformsbredd och plattformskant************13
                ContractPropertyListDB.PropertyListEntry.COLUMN_13_1,
                ContractPropertyListDB.PropertyListEntry.COLUMN_13_1_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_13_2,
                ContractPropertyListDB.PropertyListEntry.COLUMN_13_2_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_13_3,
                ContractPropertyListDB.PropertyListEntry.COLUMN_13_3_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_13_4,
                ContractPropertyListDB.PropertyListEntry.COLUMN_13_4_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_13_5,
                ContractPropertyListDB.PropertyListEntry.COLUMN_13_5_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_13_6,
                ContractPropertyListDB.PropertyListEntry.COLUMN_13_6_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_13_7,
                ContractPropertyListDB.PropertyListEntry.COLUMN_13_7_EXTRA_OBJECTS,

                //************4.2.1.13 Plattformens slut************14
                ContractPropertyListDB.PropertyListEntry.COLUMN_14_1,
                ContractPropertyListDB.PropertyListEntry.COLUMN_14_1_EXTRA_OBJECTS,

                //************4.2.1.15 Spårkorsning för passagerare påväg till plattformar************15
                ContractPropertyListDB.PropertyListEntry.COLUMN_15_1,
                ContractPropertyListDB.PropertyListEntry.COLUMN_15_1_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_15_2,
                ContractPropertyListDB.PropertyListEntry.COLUMN_15_2_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_15_3,
                ContractPropertyListDB.PropertyListEntry.COLUMN_15_3_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_15_4,
                ContractPropertyListDB.PropertyListEntry.COLUMN_15_4_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_15_5,
                ContractPropertyListDB.PropertyListEntry.COLUMN_15_5_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_15_6,
                ContractPropertyListDB.PropertyListEntry.COLUMN_15_6_EXTRA_OBJECTS,
                ContractPropertyListDB.PropertyListEntry.COLUMN_15_7,
                ContractPropertyListDB.PropertyListEntry.COLUMN_15_7_EXTRA_OBJECTS};

        String whereClause = ContractPropertyListDB.PropertyListEntry._ID + " = ?";
        String[] whereArgs = {"1"};

        Cursor cursor = databaseForPropertyList.query(
                ContractPropertyListDB.PropertyListEntry.TABLE_NAME_PROPERTY_LIST,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {
            //************ 4.2.1.1 Parkeringsmöjligheter för funktionshindrade *************1
            int parkeringsmojligheterForFunktionshindradeColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_1_1);
            int parkeringsmojligheterForFunktionshindradeExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_1_1_EXTRA_OBJECTS);
            int placeringAvParkeringForFunktionshindradeColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_1_2);
            int placeringAvParkeringForFunktionshindradeExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_1_2_EXtRA_OBJECtS);

            //************ 4.2.1.2 Hinderfri gångväg*************2
            int forekomstAvHinderfriGangvagColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_2_1);
            int forekomstAvHinderfriGangvagExtraObjectColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_2_1_EXTRA_OBJECTS);
            int langdenPaHindergriaGangvagarColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_2_2);
            int langdenPaHindergriaGangvagarExtraObjectColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_2_2_EXTRA_OBJECTS);
            int reflekterandeEgenskaperColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_2_3);
            int reflekterandeEgenskaperExtraObjectColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_2_3_EXTRA_OBJECTS);

            //************ 4.2.1.2.1 Horisontell förflyttning************3
            int hinderfriGangvagsbreddColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_3_1);
            int hinderfriGangvagsbreddExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_3_1_EXTRA_OBJECTS);
            int trosklarPaHinderfriGangvagColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_3_2);
            int trosklarPaHinderfriGangvagExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_3_2_EXTRA_OBJECTS);

            //************ 4.2.1.2.2 Vertikal förflyttning************4
            int trappstegsfriVagColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_4_1);
            int trappstegsfriVagExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_4_1_EXTRA_OBJECTS);
            int breddPaTrapporColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_4_2);
            int breddPaTrapporExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_4_2_EXTRA_OBJECTS);
            int visuelMarkeringPaForstaOchSistaStegetColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_4_3);
            int visuelMarkeringPaForstaOchSistaStegetExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_4_3_EXTRA_OBJECTS);
            int taktilVarningForeForstaMedFunktionsnedsattningarColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_4_4);
            int taktilVarningForeForstaMedFunktionsnedsattningarExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_4_4_EXTRA_OBJECTS);
            int ramperForPersonerMedFunktionsnedsättningarColmIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_4_5);
            int ramperForPersonerMedFunktionsnedsättningarExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_4_5_EXTRA_OBJECTS);
            int ledstangerPaBadaSidorOchTvaNivaerColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_4_6);
            int ledstangerPaBadaSidorOchTvaNivaerExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_4_6_EXTRA_OBJECTS);
            int hissarColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_4_7);
            int hissarExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_4_7_EXTRA_OBJECTS);
            int rulltrapporOchRullramperColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_4_8);
            int rulltrapporOchRullramperExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_4_8_EXTRA_OBJECTS);
            int plankorsningarColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_4_9);
            int plankorsningarExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_4_9_EXTRA_OBJECTS);

            //************ 4.2.1.2.3 Gångvägsmarkering************5
            int tydligMarkeringColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_5_1);
            int tydligMarkeringExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_5_1_EXTRA_OBJECTS);
            int tillhandahållandeAvInformationTillSynskadadeColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_5_2);
            int tillhandahållandeAvInformationTillSynskadadeExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_5_2_EXTRA_OBJECTS);
            int fjarrstyrdaLjudanordningarEllerTeleapplikationerColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_5_3);
            int fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_5_3_EXTRA_OBJECTS);
            int taktilInformationPaLedstangerEllerVaggarColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_5_4);
            int taktilInformationPaLedstangerEllerVaggarExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_5_4_EXTRA_OBJECTS);

            //************ 4.2.1.4 Golvytor************6
            int halksakerhetColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_6_1);
            int halksakerhetExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_6_1_EXTRA_OBJECTS);
            int ojamnheterSomOverstigerColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_6_2);
            int ojamnheterSomOverstigerExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_6_2_EXTRA_OBJECTS);

            //************ 4.2.1.5 Markering av genomskinliga hinder************7
            int glasdorrarEllerGenomskinligaVaggarLangsGangvagarColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_7_1);
            int glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_7_1_EXTRA_OBJECTS);

            //************ 4.2.1.6 Toaletter och skötplatser************8
            int toalettutrymmeAnpassatForRullstolsburnaPersonerColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_8_1);
            int toalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_8_1_EXTRA_OBJECTS);
            int skotplatserTillgangligaForBadeKonenColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_8_2);
            int skotplatserTillgangligaForBadeKonenExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_8_2_EXTRA_OBJECTS);

            //************ 4.2.1.7 Inredning och fristående enheter************9
            int kontrastMotBakgrundOchAvrundandeKanterColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_9_1);
            int kontrastMotBakgrundOchAvrundandeKanterExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_9_1_EXTRA_OBJECTS);
            int placeringAvInredningOchEnheterColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_9_2);
            int placeringAvInredningOchEnheterExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_9_2_EXTRA_OBJECTS);
            int sittmojligheterOchPlatsForEnRullstolsbundenPersonColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_9_3);
            int sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_9_3_EXTRA_OBJECTS);
            int vaderskyddatOmradeColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_9_4);
            int vaderskyddatOmradeExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_9_4_EXTRA_OBJECTS);

            //************4.2.1.9 Belysning************10
            int belysningPaStationensExternaOmradenColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_10_1);
            int belysningPaStationensExternaOmradenExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_10_1_EXTRA_OBJECTS);
            int belysningLangsHinderfriaGangvagarColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_10_2);
            int belysningLangsHinderfriaGangvagarExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_10_2_EXTRA_OBJECTS);
            int belysningPaPlattformarColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_10_3);
            int belysningPaPlattformarExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_10_3_EXTRA_OBJECTS);
            int nodbelysningColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_10_4);
            int nodbelysningExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_10_4_EXTRA_OBJECTS);

            //************4.2.1.10 Visuell information************11
            int skyltarAvstandColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_11_1);
            int skyltarAvstandExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_11_1_EXTRA_OBJECTS);
            int pictogramColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_11_2);
            int pictogramExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_11_2_EXTRA_OBJECTS);
            int kontrastColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_11_3);
            int kontrastExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_11_3_EXTRA_OBJECTS);
            int enhetligColumnIndedx = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_11_4);
            int enhetligExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_11_4_EXTRA_OBJECTS);
            int synligIAllaBelysningsforhallandenColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_11_5);
            int synligIAllaBelysningsforhallandenExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_11_5_EXTRA_OBJECTS);
            int skyltarEnligtISOColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_11_6);
            int skyltarEnligtISOExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_11_6_EXTRA_OBJECTS);

            //************4.2.1.11 Talad information Sidoplattform***********12
            int stipaNivaColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_12_1);
            int stipaNivaExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_12_1_EXTRA_OBJECTS);

            //************4.2.1.12 Plattformsbredd och plattformskant************13
            int forekomstAvRiskomradeColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_13_1);
            int forekomstAvRiskomradeExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_13_1_EXTRA_OBJECTS);
            int plattformsMinstaBreddColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_13_2);
            int plattformsMinstaBreddExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_13_2_EXTRA_OBJECTS);
            int avstandMellanLitetHinderColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_13_3);
            int avstandMellanLitetHinderExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_13_3_EXTRA_OBJECTS);
            int avstandMellanStortHinderColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_13_4);
            int avstandMellanStortHinderExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_13_4_EXTRA_OBJECTS);
            int markeringAvRiskomradetsGransColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_13_5);
            int markeringAvRiskomradetsGransExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_13_5_EXTRA_OBJECTS);
            int breddenPaVarningslinjeOchHalksakerhetOchFargColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_13_6);
            int breddenPaVarningslinjeOchHalksakerhetOchFargExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_13_6_EXTRA_OBJECTS);
            int materialPaPlattformskantenColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_13_7);
            int materialPaPlattformskantenExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_13_7_EXTRA_OBJECTS);

            //************4.2.1.13 Plattformens slut************14
            int markeringAvPlattformensSlutColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_14_1);
            int markeringAvPlattformensSlutExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_14_1_EXTRA_OBJECTS);

            //************4.2.1.15 Spårkorsning för passagerare påväg till plattformar************15
            int anvandsSomEnDelAvTrappstegfriGangvagColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_15_1);
            int anvandsSomEnDelAvTrappstegfriGangvagExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_15_1_EXTRA_OBJECTS);
            int breddPaGangvaggColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_15_2);
            int breddPaGangvaggExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_15_2_EXTRA_OBJECTS);
            int lutningColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_15_3);
            int lutningExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_15_3_EXTRA_OBJECTS);
            int friPassageForMinstaHjuletPaEnRullstolColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_15_4);
            int friPassageForMinstaHjuletPaEnRullstolExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_15_4_EXTRA_OBJECTS);
            int friPassageOmSakerhetschikanerForekommerColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_15_5);
            int friPassageOmSakerhetschikanerForekommerExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_15_5_EXTRA_OBJECTS);
            int markeringAvGangbaneytanColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_15_6);
            int markeringAvGangbaneytanExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_15_6_EXTRA_OBJECTS);
            int sakerPassageColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_15_7);
            int sakerPassageExtraObjectsColumnIndex = cursor.getColumnIndex(ContractPropertyListDB.PropertyListEntry.COLUMN_15_7_EXTRA_OBJECTS);

            //Tar stop här. Kommer inte vidare...
            while (cursor.moveToNext()) {
                //************ 4.2.1.1 Parkeringsmöjligheter för funktionshindrade *************
                String parkeringsmojligheterForFunktionshindrade = cursor.getString(parkeringsmojligheterForFunktionshindradeColumnIndex);
                String parkeringsmojligheterForFunktionshindradeExtraObjects = cursor.getString(parkeringsmojligheterForFunktionshindradeExtraObjectsColumnIndex);
                String placeringAvParkeringForFunktionshindrade = cursor.getString(placeringAvParkeringForFunktionshindradeColumnIndex);
                String placeringAvParkeringForFunktionshindradeExtraObjects = cursor.getString(placeringAvParkeringForFunktionshindradeExtraObjectsColumnIndex);

                //************ 4.2.1.2 Hinderfri gångväg*************
                String forekomstAvHinderfriGangvag = cursor.getString(forekomstAvHinderfriGangvagColumnIndex);
                String forekomstAvHinderfriGangvagExtraObjects = cursor.getString(forekomstAvHinderfriGangvagExtraObjectColumnIndex);
                String langdenPaHindergriaGangvagar = cursor.getString(langdenPaHindergriaGangvagarColumnIndex);
                String langdenPaHindergriaGangvagarExtraObjects = cursor.getString(langdenPaHindergriaGangvagarExtraObjectColumnIndex);
                String reflekterandeEgenskaper = cursor.getString(reflekterandeEgenskaperColumnIndex);
                String reflekterandeEgenskaperExtraObjects = cursor.getString(reflekterandeEgenskaperExtraObjectColumnIndex);

                //************ 4.2.1.2.1 Horisontell förflyttning************
                String hinderfriGangvagsbredd = cursor.getString(hinderfriGangvagsbreddColumnIndex);
                String hinderfriGangvagsbreddExtraObjects = cursor.getString(hinderfriGangvagsbreddExtraObjectsColumnIndex);
                String trosklarPaHinderfriGangvag = cursor.getString(trosklarPaHinderfriGangvagColumnIndex);
                String trosklarPaHinderfriGangvagExtraObjects = cursor.getString(trosklarPaHinderfriGangvagExtraObjectsColumnIndex);

                //************ 4.2.1.2.2 Vertikal förflyttning************
                String trappstegsfriVag = cursor.getString(trappstegsfriVagColumnIndex);
                String trappstegsfriVagExtraObjects = cursor.getString(trappstegsfriVagExtraObjectsColumnIndex);
                String breddPaTrappor = cursor.getString(breddPaTrapporColumnIndex);
                String breddPaTrapporExtraObjects = cursor.getString(breddPaTrapporExtraObjectsColumnIndex);
                String visuelMarkeringPaForstaOchSistaSteget = cursor.getString(visuelMarkeringPaForstaOchSistaStegetColumnIndex);
                String visuelMarkeringPaForstaOchSistaStegetExtraObjects = cursor.getString(visuelMarkeringPaForstaOchSistaStegetExtraObjectsColumnIndex);
                String taktilVarningForeForstaMedFunktionsnedsattningar = cursor.getString(taktilVarningForeForstaMedFunktionsnedsattningarColumnIndex);
                String taktilVarningForeForstaMedFunktionsnedsattningarExtraObjects = cursor.getString(taktilVarningForeForstaMedFunktionsnedsattningarExtraObjectsColumnIndex);
                String ramperForPersonerMedFunktionsnedsattningar = cursor.getString(ramperForPersonerMedFunktionsnedsättningarColmIndex);
                String ramperForPersonerMedFunktionsnedsattningarExtraObjects = cursor.getString(ramperForPersonerMedFunktionsnedsättningarExtraObjectsColumnIndex);
                String ledstangerPaBadaSidorOchTvaNivaer = cursor.getString(ledstangerPaBadaSidorOchTvaNivaerColumnIndex);
                String ledstangerPaBadaSidorOchTvaNivaerExtraObjects = cursor.getString(ledstangerPaBadaSidorOchTvaNivaerExtraObjectsColumnIndex);
                String hissar = cursor.getString(hissarColumnIndex);
                String hissarExtraObjects = cursor.getString(hissarExtraObjectsColumnIndex);
                String rulltrapporOchRullramper = cursor.getString(rulltrapporOchRullramperColumnIndex);
                String rulltrapporOchRullramperExtraObjects = cursor.getString(rulltrapporOchRullramperExtraObjectsColumnIndex);
                String plankorsningar = cursor.getString(plankorsningarColumnIndex);
                String plankorsningarExtraObjects = cursor.getString(plankorsningarExtraObjectsColumnIndex);

                //************ 4.2.1.2.3 Gångvägsmarkering************
                String tydligMarkering = cursor.getString(tydligMarkeringColumnIndex);
                String tydligMarkeringExtraObjects = cursor.getString(tydligMarkeringExtraObjectsColumnIndex);
                String tillhandahållandeAvInformationTillSynskadade = cursor.getString(tillhandahållandeAvInformationTillSynskadadeColumnIndex);
                String tillhandahållandeAvInformationTillSynskadadeExtraObjects = cursor.getString(tillhandahållandeAvInformationTillSynskadadeExtraObjectsColumnIndex);
                String fjarrstyrdaLjudanordningarEllerTeleapplikationer = cursor.getString(fjarrstyrdaLjudanordningarEllerTeleapplikationerColumnIndex);
                String fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects = cursor.getString(fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjectsColumnIndex);
                String taktilInformationPaLedstangerEllerVaggar = cursor.getString(taktilInformationPaLedstangerEllerVaggarColumnIndex);
                String taktilInformationPaLedstangerEllerVaggarExtraObjects = cursor.getString(taktilInformationPaLedstangerEllerVaggarExtraObjectsColumnIndex);

                //************ 4.2.1.4 Golvytor************
                String halksakerhet = cursor.getString(halksakerhetColumnIndex);
                String halksakerhetExtraObjects = cursor.getString(halksakerhetExtraObjectsColumnIndex);
                String ojamnheterSomOverstiger = cursor.getString(ojamnheterSomOverstigerColumnIndex);
                String ojamnheterSomOverstigerExtraObjects = cursor.getString(ojamnheterSomOverstigerExtraObjectsColumnIndex);

                //************ 4.2.1.5 Markering av genomskinliga hinder************
                String glasdorrarEllerGenomskinligaVaggarLangsGangvagar = cursor.getString(glasdorrarEllerGenomskinligaVaggarLangsGangvagarColumnIndex);
                String glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects = cursor.getString(glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjectsColumnIndex);

                //************ 4.2.1.6 Toaletter och skötplatser************
                String toalettutrymmeAnpassatForRullstolsburnaPersoner = cursor.getString(toalettutrymmeAnpassatForRullstolsburnaPersonerColumnIndex);
                String toalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects = cursor.getString(toalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjectsColumnIndex);
                String skotplatserTillgangligaForBadeKonen = cursor.getString(skotplatserTillgangligaForBadeKonenColumnIndex);
                String skotplatserTillgangligaForBadeKonenExtraObjects = cursor.getString(skotplatserTillgangligaForBadeKonenExtraObjectsColumnIndex);

                //************ 4.2.1.7 Inredning och fristående enheter************
                String kontrastMotBakgrundOchAvrundandeKanter = cursor.getString(kontrastMotBakgrundOchAvrundandeKanterColumnIndex);
                String kontrastMotBakgrundOchAvrundandeKanterExtraObjects = cursor.getString(kontrastMotBakgrundOchAvrundandeKanterExtraObjectsColumnIndex);
                String placeringAvInredningOchEnheter = cursor.getString(placeringAvInredningOchEnheterColumnIndex);
                String placeringAvInredningOchEnheterExtraObjects = cursor.getString(placeringAvInredningOchEnheterExtraObjectsColumnIndex);
                String sittmojligheterOchPlatsForEnRullstolsbundenPerson = cursor.getString(sittmojligheterOchPlatsForEnRullstolsbundenPersonColumnIndex);
                String sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects = cursor.getString(sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjectsColumnIndex);
                String vaderskyddatOmrade = cursor.getString(vaderskyddatOmradeColumnIndex);
                String vaderskyddatOmradeExtraObjects = cursor.getString(vaderskyddatOmradeExtraObjectsColumnIndex);

                //************4.2.1.9 Belysning************
                String belysningPaStationensExternaOmraden = cursor.getString(belysningPaStationensExternaOmradenColumnIndex);
                String belysningPaStationensExternaOmradenExtraObjects = cursor.getString(belysningPaStationensExternaOmradenExtraObjectsColumnIndex);
                String belysningLangsHinderfriaGangvagar = cursor.getString(belysningLangsHinderfriaGangvagarColumnIndex);
                String belysningLangsHinderfriaGangvagarExtraObjects = cursor.getString(belysningLangsHinderfriaGangvagarExtraObjectsColumnIndex);
                String belysningPaPlattformar = cursor.getString(belysningPaPlattformarColumnIndex);
                String belysningPaPlattformarExtraObjects = cursor.getString(belysningPaPlattformarExtraObjectsColumnIndex);
                String nodbelysning = cursor.getString(nodbelysningColumnIndex);
                String nodbelysningExtraObjects = cursor.getString(nodbelysningExtraObjectsColumnIndex);

                //************4.2.1.10 Visuell information************
                String skyltarAvstand = cursor.getString(skyltarAvstandColumnIndex);
                String skyltarAvstandExtraObjects = cursor.getString(skyltarAvstandExtraObjectsColumnIndex);
                String pictogram = cursor.getString(pictogramColumnIndex);
                String pictogramExtraObjects = cursor.getString(pictogramExtraObjectsColumnIndex);
                String kontrast = cursor.getString(kontrastColumnIndex);
                String kontrastExtraObjects = cursor.getString(kontrastExtraObjectsColumnIndex);
                String enhetlig = cursor.getString(enhetligColumnIndedx);
                String enhetligExtraObjects = cursor.getString(enhetligExtraObjectsColumnIndex);
                String synligIAllaBelysningsforhallanden = cursor.getString(synligIAllaBelysningsforhallandenColumnIndex);
                String synligIAllaBelysningsforhallandenExtraObjects = cursor.getString(synligIAllaBelysningsforhallandenExtraObjectsColumnIndex);
                String skyltarEnligtISO = cursor.getString(skyltarEnligtISOColumnIndex);
                String skyltarEnligtISOExtraObjects = cursor.getString(skyltarEnligtISOExtraObjectsColumnIndex);

                //************4.2.1.11 Talad information Sidoplattform***********
                String stipaNiva = cursor.getString(stipaNivaColumnIndex);
                String stipaNivaExtraObjects = cursor.getString(stipaNivaExtraObjectsColumnIndex);

                //************4.2.1.12 Plattformsbredd och plattformskant************
                String forekomstAvRiskomrade = cursor.getString(forekomstAvRiskomradeColumnIndex);
                String forekomstAvRiskomradeExtraObjects = cursor.getString(forekomstAvRiskomradeExtraObjectsColumnIndex);
                String plattformsMinstaBredd = cursor.getString(plattformsMinstaBreddColumnIndex);
                String plattformsMinstaBreddExtraObjects = cursor.getString(plattformsMinstaBreddExtraObjectsColumnIndex);
                String avstandMellanLitetHinder = cursor.getString(avstandMellanLitetHinderColumnIndex);
                String avstandMellanLitetHinderExtraObjects = cursor.getString(avstandMellanLitetHinderExtraObjectsColumnIndex);
                String avstandMellanStortHinder = cursor.getString(avstandMellanStortHinderColumnIndex);
                String avstandMellanStortHinderExtraObjects = cursor.getString(avstandMellanStortHinderExtraObjectsColumnIndex);
                String markeringAvRiskomradetsGrans = cursor.getString(markeringAvRiskomradetsGransColumnIndex);
                String markeringAvRiskomradetsGransExtraObjects = cursor.getString(markeringAvRiskomradetsGransExtraObjectsColumnIndex);
                String breddenPaVarningslinjeOchHalksakerhetOchFarg = cursor.getString(breddenPaVarningslinjeOchHalksakerhetOchFargColumnIndex);
                String breddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects = cursor.getString(breddenPaVarningslinjeOchHalksakerhetOchFargExtraObjectsColumnIndex);
                String materialPaPlattformskanten = cursor.getString(materialPaPlattformskantenColumnIndex);
                String materialPaPlattformskantenExtraObjects = cursor.getString(materialPaPlattformskantenExtraObjectsColumnIndex);

                //************4.2.1.13 Plattformens slut************
                String markeringAvPlattformensSlut = cursor.getString(markeringAvPlattformensSlutColumnIndex);
                String markeringAvPlattformensSlutExtraObjects = cursor.getString(markeringAvPlattformensSlutExtraObjectsColumnIndex);

                //************4.2.1.15 Spårkorsning för passagerare påväg till plattformar************
                String anvandsSomEnDelAvTrappstegfriGangvag = cursor.getString(anvandsSomEnDelAvTrappstegfriGangvagColumnIndex);
                String anvandsSomEnDelAvTrappstegfriGangvagExtraObjects = cursor.getString(anvandsSomEnDelAvTrappstegfriGangvagExtraObjectsColumnIndex);
                String breddPaGangvagg = cursor.getString(breddPaGangvaggColumnIndex);
                String breddPaGangvaggExtraObjects = cursor.getString(breddPaGangvaggExtraObjectsColumnIndex);
                String lutning = cursor.getString(lutningColumnIndex);
                String lutningExtraObjects = cursor.getString(lutningExtraObjectsColumnIndex);
                String friPassageForMinstaHjuletPaEnRullstol = cursor.getString(friPassageForMinstaHjuletPaEnRullstolColumnIndex);
                String friPassageForMinstaHjuletPaEnRullstolExtraObjects = cursor.getString(friPassageForMinstaHjuletPaEnRullstolExtraObjectsColumnIndex);
                String friPassageOmSakerhetschikanerForekommer = cursor.getString(friPassageOmSakerhetschikanerForekommerColumnIndex);
                String friPassageOmSakerhetschikanerForekommerExtraObjects = cursor.getString(friPassageOmSakerhetschikanerForekommerExtraObjectsColumnIndex);
                String markeringAvGangbaneytan = cursor.getString(markeringAvGangbaneytanColumnIndex);
                String markeringAvGangbaneytanExtraObjects = cursor.getString(markeringAvGangbaneytanExtraObjectsColumnIndex);
                String sakerPassage = cursor.getString(sakerPassageColumnIndex);
                String sakerPassageExtraObjects = cursor.getString(sakerPassageExtraObjectsColumnIndex);


                newPropertyListObject.setEmpty(false);
                //************ 4.2.1.1 Parkeringsmöjligheter för funktionshindrade *************
                newPropertyListObject.setParkeringsmojligheterForFunktionshindrade(parkeringsmojligheterForFunktionshindrade);
                newPropertyListObject.setParkeringsmojligheterForFunktionshindradeExtraObjects(parkeringsmojligheterForFunktionshindradeExtraObjects);
                newPropertyListObject.setPlaceringAvParkeringForFunktionshindrade(placeringAvParkeringForFunktionshindrade);
                newPropertyListObject.setPlaceringAvParkeringForFunktionshindradeExtraObjects(placeringAvParkeringForFunktionshindradeExtraObjects);

                //************ 4.2.1.2 Hinderfri gångväg*************
                newPropertyListObject.setForekomstAvHinderfriGangvag(forekomstAvHinderfriGangvag);
                newPropertyListObject.setForekomstAvHinderfriGangvagExtraObjects(forekomstAvHinderfriGangvagExtraObjects);
                newPropertyListObject.setLangdenPaHindergriaGangvagar(langdenPaHindergriaGangvagar);
                newPropertyListObject.setLangdenPaHindergriaGangvagarExtraObjects(langdenPaHindergriaGangvagarExtraObjects);
                newPropertyListObject.setReflekterandeEgenskaper(reflekterandeEgenskaper);
                newPropertyListObject.setReflekterandeEgenskaperExtraObjects(reflekterandeEgenskaperExtraObjects);

                //************ 4.2.1.2.1 Horisontell förflyttning************
                newPropertyListObject.setHinderfriGangvagsbredd(hinderfriGangvagsbredd);
                newPropertyListObject.setHinderfriGangvagsbreddExtraObjects(hinderfriGangvagsbreddExtraObjects);
                newPropertyListObject.setTrosklarPaHinderfriGangvag(trosklarPaHinderfriGangvag);
                newPropertyListObject.setTrosklarPaHinderfriGangvagExtraObjects(trosklarPaHinderfriGangvagExtraObjects);

                //************ 4.2.1.2.2 Vertikal förflyttning************
                newPropertyListObject.setTrappstegsfriVag(trappstegsfriVag);
                newPropertyListObject.setTrappstegsfriVagExtraObjects(trappstegsfriVagExtraObjects);
                newPropertyListObject.setBreddPaTrappor(breddPaTrappor);
                newPropertyListObject.setBreddPaTrapporExtraObjects(breddPaTrapporExtraObjects);
                newPropertyListObject.setVisuelMarkeringPaForstaOchSistaSteget(visuelMarkeringPaForstaOchSistaSteget);
                newPropertyListObject.setVisuelMarkeringPaForstaOchSistaStegetExtraObjects(visuelMarkeringPaForstaOchSistaStegetExtraObjects);
                newPropertyListObject.setTaktilVarningForeForstaUppgaendeTrappsteg(taktilVarningForeForstaMedFunktionsnedsattningar);
                newPropertyListObject.setTaktilVarningForeForstaUppgaendeTrappstegExtraObjects(taktilVarningForeForstaMedFunktionsnedsattningarExtraObjects);
                newPropertyListObject.setRamperForPersonerMedFunktionsnedsattningar(ramperForPersonerMedFunktionsnedsattningar);
                newPropertyListObject.setRamperForPersonerMedFunktionsnedsattningarExtraObjects(ramperForPersonerMedFunktionsnedsattningarExtraObjects);
                newPropertyListObject.setLedstangerPaBadaSidorOchTvaNivaer(ledstangerPaBadaSidorOchTvaNivaer);
                newPropertyListObject.setLedstangerPaBadaSidorOchTvaNivaerExtraObjects(ledstangerPaBadaSidorOchTvaNivaerExtraObjects);
                newPropertyListObject.setHissar(hissar);
                newPropertyListObject.setHissarExtraObjects(hissarExtraObjects);
                newPropertyListObject.setRulltrapporOchRullramper(rulltrapporOchRullramper);
                newPropertyListObject.setRulltrapporOchRullramperExtraObjects(rulltrapporOchRullramperExtraObjects);
                newPropertyListObject.setPlankorsningar(plankorsningar);
                newPropertyListObject.setPlankorsningarExtraObjects(plankorsningarExtraObjects);

                //************ 4.2.1.2.3 Gångvägsmarkering************
                newPropertyListObject.setTydligMarkering(tydligMarkering);
                newPropertyListObject.setTydligMarkeringExtraObjects(tydligMarkeringExtraObjects);
                newPropertyListObject.setTillhandahållandeAvInformationTillSynskadade(tillhandahållandeAvInformationTillSynskadade);
                newPropertyListObject.setTillhandahållandeAvInformationTillSynskadadeExtraObjects(tillhandahållandeAvInformationTillSynskadadeExtraObjects);
                newPropertyListObject.setFjarrstyrdaLjudanordningarEllerTeleapplikationer(fjarrstyrdaLjudanordningarEllerTeleapplikationer);
                newPropertyListObject.setFjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects(fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects);
                newPropertyListObject.setTaktilInformationPaLedstangerEllerVaggar(taktilInformationPaLedstangerEllerVaggar);
                newPropertyListObject.setTaktilInformationPaLedstangerEllerVaggarExtraObjects(taktilInformationPaLedstangerEllerVaggarExtraObjects);

                //************ 4.2.1.4 Golvytor************
                newPropertyListObject.setHalksakerhet(halksakerhet);
                newPropertyListObject.setHalksakerhetExtraObjects(halksakerhetExtraObjects);
                newPropertyListObject.setOjamnheterSomOverstiger(ojamnheterSomOverstiger);
                newPropertyListObject.setOjamnheterSomOverstigerExtraObjects(ojamnheterSomOverstigerExtraObjects);

                //************ 4.2.1.5 Markering av genomskinliga hinder************
                newPropertyListObject.setGlasdorrarEllerGenomskinligaVaggarLangsGangvagar(glasdorrarEllerGenomskinligaVaggarLangsGangvagar);
                newPropertyListObject.setGlasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects(glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects);

                //************ 4.2.1.6 Toaletter och skötplatser************
                newPropertyListObject.setToalettutrymmeAnpassatForRullstolsburnaPersoner(toalettutrymmeAnpassatForRullstolsburnaPersoner);
                newPropertyListObject.setToalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects(toalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects);
                newPropertyListObject.setSkotplatserTillgangligaForBadeKonen(skotplatserTillgangligaForBadeKonen);
                newPropertyListObject.setSkotplatserTillgangligaForBadeKonenExtraObjects(skotplatserTillgangligaForBadeKonenExtraObjects);

                //************ 4.2.1.7 Inredning och fristående enheter************
                newPropertyListObject.setKontrastMotBakgrundOchAvrundandeKanter(kontrastMotBakgrundOchAvrundandeKanter);
                newPropertyListObject.setKontrastMotBakgrundOchAvrundandeKanterExtraObjects(kontrastMotBakgrundOchAvrundandeKanterExtraObjects);
                newPropertyListObject.setPlaceringAvInredningOchEnheter(placeringAvInredningOchEnheter);
                newPropertyListObject.setPlaceringAvInredningOchEnheterExtraObjects(placeringAvInredningOchEnheterExtraObjects);
                newPropertyListObject.setSittmojligheterOchPlatsForEnRullstolsbundenPerson(sittmojligheterOchPlatsForEnRullstolsbundenPerson);
                newPropertyListObject.setSittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects(sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects);
                newPropertyListObject.setVaderskyddatOmrade(vaderskyddatOmrade);
                newPropertyListObject.setVaderskyddatOmradeExtraObjects(vaderskyddatOmradeExtraObjects);

                //************4.2.1.9 Belysning************
                newPropertyListObject.setBelysningPaStationensExternaOmraden(belysningPaStationensExternaOmraden);
                newPropertyListObject.setBelysningPaStationensExternaOmradenExtraObjects(belysningPaStationensExternaOmradenExtraObjects);
                newPropertyListObject.setBelysningLangsHinderfriaGangvagar(belysningLangsHinderfriaGangvagar);
                newPropertyListObject.setBelysningLangsHinderfriaGangvagarExtraObjects(belysningLangsHinderfriaGangvagarExtraObjects);
                newPropertyListObject.setBelysningPaPlattformar(belysningPaPlattformar);
                newPropertyListObject.setBelysningPaPlattformarExtraObjects(belysningPaPlattformarExtraObjects);
                newPropertyListObject.setNodbelysning(nodbelysning);
                newPropertyListObject.setNodbelysningExtraObjects(nodbelysningExtraObjects);

                //************4.2.1.10 Visuell information************
                newPropertyListObject.setVisuell_information_row1(skyltarAvstand);
                newPropertyListObject.setVisuell_information_row2(skyltarAvstandExtraObjects);
                newPropertyListObject.setVisuell_information_row3(pictogram);
                newPropertyListObject.setVisuell_information_row4(pictogramExtraObjects);
                newPropertyListObject.setVisuell_information_row5(kontrast);
                newPropertyListObject.setVisuell_information_row6(kontrastExtraObjects);
                newPropertyListObject.setVisuell_information_row7(enhetlig);
                newPropertyListObject.setVisuell_information_row8(enhetligExtraObjects);
                newPropertyListObject.setVisuell_information_row9(synligIAllaBelysningsforhallanden);
                newPropertyListObject.setVisuell_information_row10(synligIAllaBelysningsforhallandenExtraObjects);
                newPropertyListObject.setVisuell_information_row11(skyltarEnligtISO);
                newPropertyListObject.setVisuell_information_row12(skyltarEnligtISOExtraObjects);

                //************4.2.1.11 Talad information Sidoplattform***********
                newPropertyListObject.setStipaNiva(stipaNiva);
                newPropertyListObject.setStipaNivaExtraObjects(stipaNivaExtraObjects);

                //************4.2.1.12 Plattformsbredd och plattformskant************
                newPropertyListObject.setForekomstAvRiskomrade(forekomstAvRiskomrade);
                newPropertyListObject.setForekomstAvRiskomradeExtraObjects(forekomstAvRiskomradeExtraObjects);
                newPropertyListObject.setPlattformsMinstaBredd(plattformsMinstaBredd);
                newPropertyListObject.setPlattformsMinstaBreddExtraObjects(plattformsMinstaBreddExtraObjects);
                newPropertyListObject.setAvstandMellanLitetHinder(avstandMellanLitetHinder);
                newPropertyListObject.setAvstandMellanLitetHinderExtraObjects(avstandMellanLitetHinderExtraObjects);
                newPropertyListObject.setAvstandMellanStortHinder(avstandMellanStortHinder);
                newPropertyListObject.setAvstandMellanStortHinderExtraObjects(avstandMellanStortHinderExtraObjects);
                newPropertyListObject.setMarkeringAvRiskomradetsGrans(markeringAvRiskomradetsGrans);
                newPropertyListObject.setMarkeringAvRiskomradetsGransExtraObjects(markeringAvRiskomradetsGransExtraObjects);
                newPropertyListObject.setBreddenPaVarningslinjeOchHalksakerhetOchFarg(breddenPaVarningslinjeOchHalksakerhetOchFarg);
                newPropertyListObject.setBreddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects(breddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects);
                newPropertyListObject.setMaterialPaPlattformskanten(materialPaPlattformskanten);
                newPropertyListObject.setMaterialPaPlattformskantenExtraObjects(materialPaPlattformskantenExtraObjects);

                //************4.2.1.13 Plattformens slut************
                newPropertyListObject.setMarkeringAvPlattformensSlut(markeringAvPlattformensSlut);
                newPropertyListObject.setMarkeringAvPlattformensSlutExtraObjects(markeringAvPlattformensSlutExtraObjects);

                //************4.2.1.15 Spårkorsning för passagerare påväg till plattformar************
                newPropertyListObject.setAnvandsSomEnDelAvTrappstegfriGangvag(anvandsSomEnDelAvTrappstegfriGangvag);
                newPropertyListObject.setAnvandsSomEnDelAvTrappstegfriGangvagExtraObjects(anvandsSomEnDelAvTrappstegfriGangvagExtraObjects);
                newPropertyListObject.setBreddPaGangvagg(breddPaGangvagg);
                newPropertyListObject.setBreddPaGangvaggExtraObjects(breddPaGangvaggExtraObjects);
                newPropertyListObject.setLutning(lutning);
                newPropertyListObject.setLutningExtraObjects(lutningExtraObjects);
                newPropertyListObject.setFriPassageForMinstaHjuletPaEnRullstol(friPassageForMinstaHjuletPaEnRullstol);
                newPropertyListObject.setFriPassageForMinstaHjuletPaEnRullstolExtraObjects(friPassageForMinstaHjuletPaEnRullstolExtraObjects);
                newPropertyListObject.setFriPassageOmSakerhetschikanerForekommer(friPassageOmSakerhetschikanerForekommer);
                newPropertyListObject.setFriPassageOmSakerhetschikanerForekommerExtraObjects(friPassageOmSakerhetschikanerForekommerExtraObjects);
                newPropertyListObject.setMarkeringAvGangbaneytan(markeringAvGangbaneytan);
                newPropertyListObject.setMarkeringAvGangbaneytanExtraObjects(markeringAvGangbaneytanExtraObjects);
                newPropertyListObject.setSakerPassage(sakerPassage);
                newPropertyListObject.setSakerPassageExtraObjects(sakerPassageExtraObjects);
            }
        } finally {
            cursor.close();
        }
        return newPropertyListObject;
    }

    public void updatePropertyListData(PropertyListObjects propertyListObject) {

        ContentValues values = new ContentValues();

        //************ 4.2.1.1 Parkeringsmöjligheter för funktionshindrade *************1
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_1_1, propertyListObject.getParkeringsmojligheterForFunktionshindrade());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_1_1_EXTRA_OBJECTS, propertyListObject.getParkeringsmojligheterForFunktionshindradeExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_1_2, propertyListObject.getPlaceringAvParkeringForFunktionshindrade());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_1_2_EXtRA_OBJECtS, propertyListObject.getPlaceringAvParkeringForFunktionshindradeExtraObjects());

        //************ 4.2.1.2 Hinderfri gångväg*************2
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_2_1, propertyListObject.getForekomstAvHinderfriGangvag());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_2_1_EXTRA_OBJECTS, propertyListObject.getForekomstAvHinderfriGangvagExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_2_2, propertyListObject.getLangdenPaHindergriaGangvagar());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_2_2_EXTRA_OBJECTS, propertyListObject.getLangdenPaHindergriaGangvagarExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_2_3, propertyListObject.getReflekterandeEgenskaper());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_2_3_EXTRA_OBJECTS, propertyListObject.getReflekterandeEgenskaperExtraObjects());

        //************ 4.2.1.2.1 Horisontell förflyttning************3
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_3_1, propertyListObject.getHinderfriGangvagsbredd());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_3_1_EXTRA_OBJECTS, propertyListObject.getHinderfriGangvagsbreddExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_3_2, propertyListObject.getTrosklarPaHinderfriGangvag());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_3_2_EXTRA_OBJECTS, propertyListObject.getTrosklarPaHinderfriGangvagExtraObjects());

        //************ 4.2.1.2.2 Vertikal förflyttning************4
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_1, propertyListObject.getTrappstegsfriVag());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_1_EXTRA_OBJECTS, propertyListObject.getTrappstegsfriVagExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_2, propertyListObject.getBreddPaTrappor());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_2_EXTRA_OBJECTS, propertyListObject.getBreddPaTrapporExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_3, propertyListObject.getVisuelMarkeringPaForstaOchSistaSteget());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_3_EXTRA_OBJECTS, propertyListObject.getVisuelMarkeringPaForstaOchSistaStegetExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_4, propertyListObject.getTaktilVarningForeForstaUppgaendeTrappsteg());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_4_EXTRA_OBJECTS, propertyListObject.getTaktilVarningForeForstaUppgaendeTrappstegExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_5, propertyListObject.getRamperForPersonerMedFunktionsnedsattningar());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_5_EXTRA_OBJECTS, propertyListObject.getRamperForPersonerMedFunktionsnedsattningarExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_6, propertyListObject.getLedstangerPaBadaSidorOchTvaNivaer());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_6_EXTRA_OBJECTS, propertyListObject.getLedstangerPaBadaSidorOchTvaNivaerExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_7, propertyListObject.getHissar());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_7_EXTRA_OBJECTS, propertyListObject.getHissarExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_8, propertyListObject.getRulltrapporOchRullramper());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_8_EXTRA_OBJECTS, propertyListObject.getRulltrapporOchRullramperExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_9, propertyListObject.getPlankorsningar());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_4_9_EXTRA_OBJECTS, propertyListObject.getPlankorsningarExtraObjects());

        //************ 4.2.1.2.3 Gångvägsmarkering************5
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_5_1, propertyListObject.getTydligMarkering());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_5_1_EXTRA_OBJECTS, propertyListObject.getTydligMarkeringExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_5_2, propertyListObject.getTillhandahållandeAvInformationTillSynskadade());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_5_2_EXTRA_OBJECTS, propertyListObject.getTillhandahållandeAvInformationTillSynskadadeExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_5_3, propertyListObject.getFjarrstyrdaLjudanordningarEllerTeleapplikationer());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_5_3_EXTRA_OBJECTS, propertyListObject.getFjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_5_4, propertyListObject.getTaktilInformationPaLedstangerEllerVaggar());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_5_4_EXTRA_OBJECTS, propertyListObject.getTaktilInformationPaLedstangerEllerVaggarExtraObjects());

        //************ 4.2.1.4 Golvytor************6
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_6_1, propertyListObject.getHalksakerhet());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_6_1_EXTRA_OBJECTS, propertyListObject.getHalksakerhetExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_6_2, propertyListObject.getOjamnheterSomOverstiger());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_6_2_EXTRA_OBJECTS, propertyListObject.getOjamnheterSomOverstigerExtraObjects());

        //************ 4.2.1.5 Markering av genomskinliga hinder************7
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_7_1, propertyListObject.getGlasdorrarEllerGenomskinligaVaggarLangsGangvagar());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_7_1_EXTRA_OBJECTS, propertyListObject.getGlasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects());

        //************ 4.2.1.6 Toaletter och skötplatser************8
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_8_1, propertyListObject.getToalettutrymmeAnpassatForRullstolsburnaPersoner());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_8_1_EXTRA_OBJECTS, propertyListObject.getToalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_8_2, propertyListObject.getSkotplatserTillgangligaForBadeKonen());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_8_2_EXTRA_OBJECTS, propertyListObject.getSkotplatserTillgangligaForBadeKonenExtraObjects());

        //************ 4.2.1.7 Inredning och fristående enheter************9
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_9_1, propertyListObject.getKontrastMotBakgrundOchAvrundandeKanter());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_9_1_EXTRA_OBJECTS, propertyListObject.getKontrastMotBakgrundOchAvrundandeKanterExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_9_2, propertyListObject.getPlaceringAvInredningOchEnheter());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_9_2_EXTRA_OBJECTS, propertyListObject.getPlaceringAvInredningOchEnheterExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_9_3, propertyListObject.getSittmojligheterOchPlatsForEnRullstolsbundenPerson());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_9_3_EXTRA_OBJECTS, propertyListObject.getSittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_9_4, propertyListObject.getVaderskyddatOmrade());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_9_4_EXTRA_OBJECTS, propertyListObject.getVaderskyddatOmradeExtraObjects());

        //************4.2.1.9 Belysning************10
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_10_1, propertyListObject.getBelysningPaStationensExternaOmraden());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_10_1_EXTRA_OBJECTS, propertyListObject.getBelysningPaStationensExternaOmradenExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_10_2, propertyListObject.getBelysningLangsHinderfriaGangvagar());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_10_2_EXTRA_OBJECTS, propertyListObject.getBelysningLangsHinderfriaGangvagarExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_10_3, propertyListObject.getBelysningPaPlattformar());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_10_3_EXTRA_OBJECTS, propertyListObject.getBelysningPaPlattformarExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_10_4, propertyListObject.getNodbelysning());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_10_4_EXTRA_OBJECTS, propertyListObject.getNodbelysningExtraObjects());

        //************4.2.1.10 Visuell information************11
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_1, propertyListObject.getVisuell_information_row1());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_1_EXTRA_OBJECTS, propertyListObject.getVisuell_information_row2());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_2, propertyListObject.getVisuell_information_row3());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_2_EXTRA_OBJECTS, propertyListObject.getVisuell_information_row4());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_3, propertyListObject.getVisuell_information_row5());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_3_EXTRA_OBJECTS, propertyListObject.getVisuell_information_row6());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_4, propertyListObject.getVisuell_information_row7());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_4_EXTRA_OBJECTS, propertyListObject.getVisuell_information_row8());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_5, propertyListObject.getVisuell_information_row9());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_5_EXTRA_OBJECTS, propertyListObject.getVisuell_information_row10());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_6, propertyListObject.getVisuell_information_row11());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_11_6_EXTRA_OBJECTS, propertyListObject.getVisuell_information_row12());

        //************4.2.1.11 Talad information Sidoplattform***********12
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_12_1, propertyListObject.getStipaNiva());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_12_1_EXTRA_OBJECTS, propertyListObject.getStipaNivaExtraObjects());

        //************4.2.1.12 Plattformsbredd och plattformskant************13
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_1, propertyListObject.getForekomstAvRiskomrade());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_1_EXTRA_OBJECTS, propertyListObject.getForekomstAvRiskomradeExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_2, propertyListObject.getPlattformsMinstaBredd());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_2_EXTRA_OBJECTS, propertyListObject.getPlattformsMinstaBreddExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_3, propertyListObject.getAvstandMellanLitetHinder());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_3_EXTRA_OBJECTS, propertyListObject.getAvstandMellanLitetHinderExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_4, propertyListObject.getAvstandMellanStortHinder());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_4_EXTRA_OBJECTS, propertyListObject.getAvstandMellanStortHinderExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_5, propertyListObject.getMarkeringAvRiskomradetsGrans());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_5_EXTRA_OBJECTS, propertyListObject.getMarkeringAvRiskomradetsGransExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_6, propertyListObject.getBreddenPaVarningslinjeOchHalksakerhetOchFarg());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_6_EXTRA_OBJECTS, propertyListObject.getBreddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_7, propertyListObject.getMaterialPaPlattformskanten());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_13_7_EXTRA_OBJECTS, propertyListObject.getMaterialPaPlattformskantenExtraObjects());

        //************4.2.1.13 Plattformens slut************14
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_14_1, propertyListObject.getMarkeringAvPlattformensSlut());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_14_1_EXTRA_OBJECTS, propertyListObject.getMarkeringAvPlattformensSlutExtraObjects());

        //************4.2.1.15 Spårkorsning för passagerare påväg till plattformar************15
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_1, propertyListObject.getAnvandsSomEnDelAvTrappstegfriGangvag());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_1_EXTRA_OBJECTS, propertyListObject.getAnvandsSomEnDelAvTrappstegfriGangvagExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_2, propertyListObject.getBreddPaGangvagg());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_2_EXTRA_OBJECTS, propertyListObject.getBreddPaGangvaggExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_3, propertyListObject.getLutning());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_3_EXTRA_OBJECTS, propertyListObject.getLutningExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_4, propertyListObject.getFriPassageForMinstaHjuletPaEnRullstol());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_4_EXTRA_OBJECTS, propertyListObject.getFriPassageForMinstaHjuletPaEnRullstolExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_5, propertyListObject.getFriPassageOmSakerhetschikanerForekommer());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_5_EXTRA_OBJECTS, propertyListObject.getFriPassageOmSakerhetschikanerForekommerExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_6, propertyListObject.getMarkeringAvGangbaneytan());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_6_EXTRA_OBJECTS, propertyListObject.getMarkeringAvGangbaneytanExtraObjects());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_7, propertyListObject.getSakerPassage());
        values.put(ContractPropertyListDB.PropertyListEntry.COLUMN_15_7_EXTRA_OBJECTS, propertyListObject.getSakerPassageExtraObjects());

        databaseForPropertyList.update(ContractPropertyListDB.PropertyListEntry.TABLE_NAME_PROPERTY_LIST, values, null, null);
    }

    public void deletePropertyList() {

        databaseForPropertyList.delete(ContractPropertyListDB.PropertyListEntry.TABLE_NAME_PROPERTY_LIST, null, null);
    }

    //**********************   Vaxlar och Spar **************************************
    public void createVaxlarOchSpar(double latitude, double longitude) {
        ContentValues values = new ContentValues();
        values.put(ContractVaxlarOchSparDB.AreaEntry.COLUMN_LATITUDE, latitude);
        values.put(ContractVaxlarOchSparDB.AreaEntry.COLUMN_LONGITUDE, longitude);

        databaseVaxlarOchSpar.insert(ContractVaxlarOchSparDB.AreaEntry.TABLE_NAME, null, values);
    }

    public Object recoverSparOchVaxlarComments (double latitude, double longitude) {

        Object currentObject = new Object();

        String[] projection = {
                ContractAreasDB.AreaEntry._ID,
                ContractVaxlarOchSparDB.AreaEntry.COLUMN_VAXLAR,
                ContractVaxlarOchSparDB.AreaEntry.COLUMN_SPAR,
                ContractVaxlarOchSparDB.AreaEntry.COLUMN_KOMMENTARER,
                ContractVaxlarOchSparDB.AreaEntry.COLUMN_LATITUDE,
                ContractVaxlarOchSparDB.AreaEntry.COLUMN_LONGITUDE};

        String lat = String.valueOf(latitude);
        String lng = String.valueOf(longitude);

        String whereClause = ContractVaxlarOchSparDB.AreaEntry.COLUMN_LATITUDE + " =? AND " +
                ContractVaxlarOchSparDB.AreaEntry.COLUMN_LONGITUDE + " =?";
        String[] whereArgs = {lat,lng};

        Cursor cursor = databaseVaxlarOchSpar.query(
                ContractVaxlarOchSparDB.AreaEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {
            int vaxlarColumnIndex = cursor.getColumnIndex(ContractVaxlarOchSparDB.AreaEntry.COLUMN_VAXLAR);
            int sparColumnIndex = cursor.getColumnIndex(ContractVaxlarOchSparDB.AreaEntry.COLUMN_SPAR);
            int kommentarertColumnIndex = cursor.getColumnIndex(ContractVaxlarOchSparDB.AreaEntry.COLUMN_KOMMENTARER);
            int latColumnIndex = cursor.getColumnIndex(ContractVaxlarOchSparDB.AreaEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractVaxlarOchSparDB.AreaEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {

                String vaxlarValue = cursor.getString(vaxlarColumnIndex);
                String sparValue = cursor.getString(sparColumnIndex);
                String kommentarerValue = cursor.getString(kommentarertColumnIndex);
                double latitudeValue = cursor.getDouble(latColumnIndex);
                double longitudeValue = cursor.getDouble(longColumnIndex);

                Object object = new Object();

                object.setVaxlarComments(vaxlarValue);
                object.setSparComments(sparValue);
                object.setComments(kommentarerValue);
                object.setLatitude(latitudeValue);
                object.setLongitude(longitudeValue);

                currentObject = object;
            }
        } finally {
            cursor.close();
        }
        return currentObject;
    }

    public Boolean checkIfObjectExistsVaxlarOchSparComments (double latitude, double longitude) {

        Boolean exists = false;

        String[] projection = {
                ContractAreasDB.AreaEntry._ID,
                ContractVaxlarOchSparDB.AreaEntry.COLUMN_VAXLAR,
                ContractVaxlarOchSparDB.AreaEntry.COLUMN_SPAR,
                ContractVaxlarOchSparDB.AreaEntry.COLUMN_KOMMENTARER,
                ContractVaxlarOchSparDB.AreaEntry.COLUMN_LATITUDE,
                ContractVaxlarOchSparDB.AreaEntry.COLUMN_LONGITUDE};

        String lat = String.valueOf(latitude);
        String lng = String.valueOf(longitude);

        String whereClause = ContractVaxlarOchSparDB.AreaEntry.COLUMN_LATITUDE + " =? AND " +
                ContractVaxlarOchSparDB.AreaEntry.COLUMN_LONGITUDE + " =?";
        String[] whereArgs = {lat,lng};

        Cursor cursor = databaseVaxlarOchSpar.query(
                ContractVaxlarOchSparDB.AreaEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                whereClause,                  // The columns for the WHERE clause
                whereArgs,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {
            while (cursor.moveToNext()) {
                exists = true;
            }
        } finally {
            cursor.close();
        }
        return exists;
    }

    public void updateVaxlarOchSpar(Object object, double latitude, double longitude) {

        ContentValues values = new ContentValues();
        values.put(ContractVaxlarOchSparDB.AreaEntry.COLUMN_VAXLAR, object.getVaxlarComments());
        values.put(ContractVaxlarOchSparDB.AreaEntry.COLUMN_SPAR, object.getSparComments());

        String lat = String.valueOf(latitude);
        String lng = String.valueOf(longitude);

        String whereClause = ContractVaxlarOchSparDB.AreaEntry.COLUMN_LATITUDE + " = ? AND " +
                ContractVaxlarOchSparDB.AreaEntry.COLUMN_LONGITUDE + " =?";
        String[] whereArgs = {lat,lng};

        databaseVaxlarOchSpar.update(ContractVaxlarOchSparDB.AreaEntry.TABLE_NAME, values, whereClause, whereArgs);
    }

    public void deleteOneVaxlarOchSpar(double lat, double lng) {

        String latitude = String.valueOf(lat);
        String longitude = String.valueOf(lng);

        String whereClause = ContractVaxlarOchSparDB.AreaEntry.COLUMN_LATITUDE + "=? AND " +
                ContractVaxlarOchSparDB.AreaEntry.COLUMN_LONGITUDE + " =?";
        String[] whereArgs = {latitude, longitude};

        databaseVaxlarOchSpar.delete(ContractVaxlarOchSparDB.AreaEntry.TABLE_NAME, whereClause, whereArgs);
    }

    public void onDownGradeVaxlarOchSparDatabase() {
        vaxlarOchSparDbHelper.onDowngrade(databaseVaxlarOchSpar, 1, 1);
    }

}

// You should not reuse primary key values https://www.quora.com/How-do-I-update-primary-key-in-sqlite-after-deleting-a-row
