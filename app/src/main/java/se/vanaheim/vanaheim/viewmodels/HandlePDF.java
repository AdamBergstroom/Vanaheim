package se.vanaheim.vanaheim.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import androidx.core.content.FileProvider;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import se.vanaheim.vanaheim.R;
import se.vanaheim.vanaheim.models.EditPDFObject;
import se.vanaheim.vanaheim.models.Object;
import se.vanaheim.vanaheim.models.PropertyListObjects;

public class HandlePDF {

    private Context context;
    private File dir;

    public HandlePDF(Context context) {
        this.context = context;

        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Vanaheim/";
        dir = new File(rootPath);

        if (!dir.exists()) {
            dir.mkdir();

            /*
            if (dir.exists() && dir.isDirectory()) {
                Toast toast = Toast.makeText(context, "Mapp skapad", Toast.LENGTH_LONG);
                toast.show();
            } else {
                Toast toast = Toast.makeText(context, "Mapp blev inte skapad", Toast.LENGTH_LONG);
                toast.show();
            }
             */
        }
    }

    public void createPDFForINFObjects(ArrayList<Object> objectList, EditPDFObject pdfValues) {

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {
            //special font sizes
            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf10 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            Font bf8 = new Font(Font.FontFamily.TIMES_ROMAN, 8);
            Font header = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD | Font.UNDERLINE);

            File file = new File(dir, "Vanaheim.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(document, fOut);

            //document header attributes
            document.addAuthor("Vanaheim");
            document.addCreationDate();
            document.addProducer();
            document.addCreator("Vanaheim");
            document.addTitle("Checklista för spårkontroll (TSD INF)");
            document.setPageSize(PageSize.LETTER);

            //open the document
            document.open();

            BaseFont bf = BaseFont.createFont("assets/freesans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font f8 = new Font(bf, 8);

            BaseFont bf2 = BaseFont.createFont("assets/freesans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font f10 = new Font(bf2, 10);

            //specify column widths
            float[] columnWidths = {2f, 1f, 1f, 1f, 1f, 1f};

            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setWidthPercentage(100f);

            Paragraph paragraph = new Paragraph();

            try {
                document.open();
                Drawable d = context.getResources().getDrawable(R.drawable.vanaheim_logga);
                BitmapDrawable bitDw = ((BitmapDrawable) d);
                Bitmap bmp = bitDw.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 30, stream);
                Image image = Image.getInstance(stream.toByteArray());

                int indentation = 0;
                float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                        - document.rightMargin() - indentation) / image.getWidth()) * 20;
                image.scalePercent(scaler);
                paragraph.add(image);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Måste ha en paragraf, annars kraschar formatet: The document has no pages
            paragraph.add("Checklista för spårkontroll (TSD INF)");


            insertCell(table, "Projektnummer/Driftplats", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Dokumentnummer", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Ansvarig", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Mätdatum", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Beställaren", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Referens", Element.ALIGN_LEFT, 1, bf8);

            insertCell(table, String.valueOf(pdfValues.getProjektnummer()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfValues.getDokumentnummer()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfValues.getAnsvarig()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfValues.getMatdatum()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfValues.getBestallare()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfValues.getReferens()), Element.ALIGN_LEFT, 1, f10);

            table.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph.add(table);
            // add the paragraph to the document
            document.add(paragraph);

            float[] columnWidths2 = {2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};

            //create PDF table with the given widths
            PdfPTable table2 = new PdfPTable(columnWidths2);
            // set table width a percentage of the page width
            table2.setWidthPercentage(100f);

            Paragraph paragraph2 = new Paragraph();

            insertCellSideBorders(table2, "Spår:", Element.ALIGN_LEFT, 1, f10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getSpar()), Element.ALIGN_LEFT, 2, f10, 1);
            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 5, f10, 3);

            insertCellSideBorders(table2, "Datum:", Element.ALIGN_LEFT, 1, f10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getDatum()), Element.ALIGN_LEFT, 3, f10, 1);
            insertCellSideBorders(table2, "Mätinstrument:", Element.ALIGN_LEFT, 2, f10, 1);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getMatinstrument()), Element.ALIGN_LEFT, 2, f10, 3);

            insertCellSideBorders(table2, "Klockslag:", Element.ALIGN_LEFT, 1, f10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getTid()), Element.ALIGN_LEFT, 2, f10, 1);
            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 5, f10, 3);

            insertCellSideBorders(table2, "Väder:", Element.ALIGN_LEFT, 1, f10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getVader()), Element.ALIGN_LEFT, 2, f10, 1);
            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 5, f10, 3);

            insertCellSideBorders(table2, "Temperatur:", Element.ALIGN_LEFT, 1, f10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getTemperatur()), Element.ALIGN_LEFT, 2, f10, 1);
            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 5, f10, 3);

            insertCellSideBorders(table2, "Kontrollanter:", Element.ALIGN_LEFT, 1, f10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getKontrollanter()), Element.ALIGN_LEFT, 2, f10, 1);
            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 5, f10, 3);

            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 8, f10, 0);

            table2.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph2.add(table2);
            // add the paragraph to the document
            document.add(paragraph2);

            //**************************************************************************************

            float[] columnWidths3 = {2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};

            //create PDF table with the given widths
            PdfPTable table3 = new PdfPTable(columnWidths3);
            // set table width a percentage of the page width
            table3.setWidthPercentage(100f);

            Paragraph paragraph3 = new Paragraph();

            //insert column headings
            insertCell(table3, "Km-tal", Element.ALIGN_CENTER, 1, f10);
            insertCell(table3, "Spårvidd\n(mm)", Element.ALIGN_CENTER, 1, f10);
            insertCell(table3, "Rälsförhöjning\n(mm)", Element.ALIGN_CENTER, 1, f10);
            insertCell(table3, "Slipersavstånd\nm/(antal/xm)", Element.ALIGN_CENTER, 1, f10);
            insertCell(table3, "Spåravstånd\n(till närmast spår)", Element.ALIGN_CENTER, 1, f10);
            insertCell(table3, "Fria rummet\nAvstånd till hinder", Element.ALIGN_CENTER, 1, f10);
            insertCell(table3, "Status", Element.ALIGN_CENTER, 1, f10);
            insertCell(table3, "Kommentar\n(Anm)", Element.ALIGN_CENTER, 1, f10);

            for (int i = 0; i < objectList.size(); i++) {
                Object object = objectList.get(i);
                //Km-tal
                insertCell(table3, String.valueOf(object.getKmNummer()), Element.ALIGN_LEFT, 1, f10);
                //Spårvidd (mm)
                insertCell(table3, String.valueOf(object.getSparvidd()), Element.ALIGN_LEFT, 1, f10);
                //Rälsförhöjning (mm)
                insertCell(table3, String.valueOf(object.getRalsforhojning()), Element.ALIGN_LEFT, 1, f10);
                //Slipersavstånd m/(antal/xm)
                insertCell(table3, String.valueOf(object.getSlipersavstand()), Element.ALIGN_LEFT, 1, f10);
                //Spåravstånd (till närmaste spår)
                insertCell(table3, String.valueOf(object.getSparavstand()), Element.ALIGN_LEFT, 1, f10);
                //Fria rummet Avstånd till hinder
                insertCell(table3, String.valueOf(object.getFriaRummet()), Element.ALIGN_LEFT, 1, f10);
                //Status
                int completed = object.getCompleted();
                if (completed == 0)
                    insertCell(table3, "", Element.ALIGN_CENTER, 1, f10);
                else
                    insertCell(table3, "OK", Element.ALIGN_CENTER, 1, f10);
                //Kommentar (Anm)
                insertCell(table3, String.valueOf(object.getComments()), Element.ALIGN_LEFT, 1, f10);
            }

            table3.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph3.add(table3);
            // add the paragraph to the document
            document.add(paragraph3);

            //**************************************************************************************

            float[] columnWidths4 = {3f, 1f};

            //create PDF table with the given widths
            PdfPTable table4 = new PdfPTable(columnWidths4);
            // set table width a percentage of the page width
            table4.setWidthPercentage(100f);

            Paragraph paragraph4 = new Paragraph();

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 0);

            insertCellSideBorders(table4, "Spårkomponenter:", Element.ALIGN_LEFT, 2, header, 0);

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 0);

            insertCellSideBorders(table4, String.valueOf(pdfValues.getSparkomponenter()), Element.ALIGN_LEFT, 2, f10, 0);

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 0);

            insertCellSideBorders(table4, "Växlar:", Element.ALIGN_LEFT, 2, header, 0);

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 0);

            insertCellSideBorders(table4, String.valueOf(pdfValues.getVaxlar()), Element.ALIGN_LEFT, 2, bf10, 0);

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 0);

            insertCellSideBorders(table4, "Övriga kommentarer:", Element.ALIGN_LEFT, 2, header, 0);

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 0);

            insertCellSideBorders(table4, String.valueOf(pdfValues.getOvrigaKommentarer()), Element.ALIGN_LEFT, 2, f10, 0);

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 2);


            table4.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph4.add(table4);
            // add the paragraph to the document
            document.add(paragraph4);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            document.close();
        }
    }

    public void createPDFForENEObjects(ArrayList<Object> objectList, EditPDFObject pdfValues) {

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {

            //special font sizes
            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf10 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            Font bf8 = new Font(Font.FontFamily.TIMES_ROMAN, 8);

            Font header = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD | Font.UNDERLINE);

            File file = new File(dir, "Vanaheim.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(document, fOut);

            //document header attributes
            document.addAuthor("Vanaheim");
            document.addCreationDate();
            document.addProducer();
            document.addCreator("Vanaheim");
            document.addTitle("Checklista för ENE-kontroll");
            document.setPageSize(PageSize.LETTER);

            //open the document
            document.open();

            BaseFont bf = BaseFont.createFont("assets/freesans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font f8 = new Font(bf, 8);

            BaseFont bf2 = BaseFont.createFont("assets/freesans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font f10 = new Font(bf2, 10);

            //specify column widths
            float[] columnWidths = {2f, 1f, 1f, 1f, 1f, 1f};

            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setWidthPercentage(100f);


            Paragraph paragraph = new Paragraph();

            try {
                document.open();
                Drawable d = context.getResources().getDrawable(R.drawable.vanaheim_logga);
                BitmapDrawable bitDw = ((BitmapDrawable) d);
                Bitmap bmp = bitDw.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 30, stream);
                Image image = Image.getInstance(stream.toByteArray());

                int indentation = 0;
                float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                        - document.rightMargin() - indentation) / image.getWidth()) * 20;
                image.scalePercent(scaler);
                paragraph.add(image);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Måste ha en paragraf, annars kraschar formatet: The document has no pages
            paragraph.add("Checklista för ENE-kontroll");

            insertCell(table, "Projektnummer", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Dokumentnummer", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Ansvarig", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Mätdatum", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Beställaren", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Referens", Element.ALIGN_LEFT, 1, bf8);

            insertCell(table, String.valueOf(pdfValues.getProjektnummer()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfValues.getDokumentnummer()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfValues.getAnsvarig()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfValues.getMatdatum()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfValues.getBestallare()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfValues.getReferens()), Element.ALIGN_LEFT, 1, f10);

            table.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph.add(table);
            // add the paragraph to the document
            document.add(paragraph);

            float[] columnWidths2 = {2f, 2f, 2f, 2f, 2f, 2f, 2f};

            //create PDF table with the given widths
            PdfPTable table2 = new PdfPTable(columnWidths2);
            // set table width a percentage of the page width
            table2.setWidthPercentage(100f);

            Paragraph paragraph2 = new Paragraph();

            insertCellSideBorders(table2, "Spår:", Element.ALIGN_LEFT, 1, bf10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getSpar()), Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 5, bf10, 3);

            insertCellSideBorders(table2, "Datum:", Element.ALIGN_LEFT, 1, bf10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getDatum()), Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 1, bf10, 1);
            insertCellSideBorders(table2, "Mätinstrument:", Element.ALIGN_LEFT, 1, bf10, 1);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getMatinstrument()), Element.ALIGN_LEFT, 3, f10, 3);

            insertCellSideBorders(table2, "Klockslag:", Element.ALIGN_LEFT, 1, bf10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getTid()), Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 5, bf10, 3);

            insertCellSideBorders(table2, "Väder:", Element.ALIGN_LEFT, 1, bf10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getVader()), Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 5, bf10, 3);

            insertCellSideBorders(table2, "Temperatur:", Element.ALIGN_LEFT, 1, bf10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getTemperatur()), Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 5, bf10, 3);

            insertCellSideBorders(table2, "Kontrollanter:", Element.ALIGN_LEFT, 1, bf10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getKontrollanter()), Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 5, bf10, 3);

            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 7, bf10, 0);

            table2.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph2.add(table2);
            // add the paragraph to the document
            document.add(paragraph2);

            //**************************************************************************************
            float[] columnWidths3 = {2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};

            //create PDF table with the given widths
            PdfPTable table3 = new PdfPTable(columnWidths3);
            // set table width a percentage of the page width
            table3.setWidthPercentage(100f);

            Paragraph paragraph3 = new Paragraph();

            //insert column headings
            insertCell(table3, "Stople Nr.", Element.ALIGN_CENTER, 1, bf10);
            insertCell(table3, "Objekt", Element.ALIGN_CENTER, 1, bf10);
            insertCell(table3, "Höjd av kontakttråd\n(mm)", Element.ALIGN_CENTER, 1, bf10);
            insertCell(table3, "Avvikelse i sidled\n(mm)", Element.ALIGN_CENTER, 1, bf10);
            insertCell(table3, "Höjd av underrör\n(mm)", Element.ALIGN_CENTER, 1, bf10); //Höjd av utliggarrör är bytt till Höjd av underrör
            insertCell(table3, "Trådläget\n(mm)", Element.ALIGN_CENTER, 1, bf10); //Upphöjd av tillsatsrör bytt till Trådläget
            insertCell(table3, "Status", Element.ALIGN_CENTER, 1, bf10); //Upphöjd av tillsatsrör bytt till Trådläget
            insertCell(table3, "Kommentar", Element.ALIGN_CENTER, 1, bf10);

            for (int i = 0; i < objectList.size(); i++) {
                Object object = objectList.get(i);
                //Stolpnummer
                insertCell(table3, String.valueOf(object.getStolpnummer()), Element.ALIGN_LEFT, 1, f10);
                //Objekt
                insertCell(table3, String.valueOf(object.getObjektForENE()), Element.ALIGN_LEFT, 1, f10);
                //Höjd av kontakttråd(mm)
                insertCell(table3, String.valueOf(object.getHojdAvKontakttrad()), Element.ALIGN_LEFT, 1, f10);
                //Avvikelse i sidled(mm)
                insertCell(table3, String.valueOf(object.getAvvikelseISidled()), Element.ALIGN_LEFT, 1, f10);
                //Höjd av utliggarrör (mm)
                insertCell(table3, String.valueOf(object.getHojdAvUtliggarror()), Element.ALIGN_LEFT, 1, f10);
                //Upphöjd av tillsatsrör (mm)
                insertCell(table3, String.valueOf(object.getUpphojdAvTillsatsror()), Element.ALIGN_LEFT, 1, f10);
                //Status
                int completed = object.getCompleted();
                if (completed == 0)
                    insertCell(table3, "", Element.ALIGN_CENTER, 1, f10);
                else
                    insertCell(table3, "OK", Element.ALIGN_CENTER, 1, f10);
                //Kommentar
                insertCell(table3, String.valueOf(object.getComments()), Element.ALIGN_LEFT, 1, f10);
            }

            table3.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph3.add(table3);
            // add the paragraph to the document
            document.add(paragraph3);
            //**************************************************************************************

            float[] columnWidths4 = {3f, 1f};

            //create PDF table with the given widths
            PdfPTable table4 = new PdfPTable(columnWidths4);
            // set table width a percentage of the page width
            table4.setWidthPercentage(100f);

            Paragraph paragraph4 = new Paragraph();

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 0);

            insertCellSideBorders(table4, "Övriga kommentarer:", Element.ALIGN_LEFT, 2, header, 0);

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 0);

            insertCellSideBorders(table4, String.valueOf(pdfValues.getOvrigaKommentarer()), Element.ALIGN_LEFT, 2, f10, 0);

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 2);

            table4.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph4.add(table4);
            // add the paragraph to the document
            document.add(paragraph4);


        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            document.close();
        }
    }

    public void createPDFForENEObjectsVersionTwo(ArrayList<Object> objectList, EditPDFObject pdfValues) {

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {

            //special font sizes
            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf10 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            Font bf8 = new Font(Font.FontFamily.TIMES_ROMAN, 8);

            Font header = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD | Font.UNDERLINE);

            File file = new File(dir, "Vanaheim.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(document, fOut);

            //document header attributes
            document.addAuthor("Vanaheim");
            document.addCreationDate();
            document.addProducer();
            document.addCreator("Vanaheim");
            document.addTitle("Checklista för ENE-kontroll");
            document.setPageSize(PageSize.LETTER);

            //open the document
            document.open();

            BaseFont bf = BaseFont.createFont("assets/freesans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font f8 = new Font(bf, 8);

            BaseFont bf2 = BaseFont.createFont("assets/freesans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font f10 = new Font(bf2, 10);

            //specify column widths
            float[] columnWidths = {2f, 1f, 1f, 1f, 1f, 1f};

            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setWidthPercentage(100f);

            Paragraph paragraph = new Paragraph();

            try {
                document.open();
                Drawable d = context.getResources().getDrawable(R.drawable.vanaheim_logga);
                BitmapDrawable bitDw = ((BitmapDrawable) d);
                Bitmap bmp = bitDw.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 30, stream);
                Image image = Image.getInstance(stream.toByteArray());

                int indentation = 0;
                float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                        - document.rightMargin() - indentation) / image.getWidth()) * 20;
                image.scalePercent(scaler);
                paragraph.add(image);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Måste ha en paragraf, annars kraschar formatet: The document has no pages
            paragraph.add("Checklista för ENE-kontroll");

            insertCell(table, "Projektnummer", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Dokumentnummer", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Ansvarig", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Mätdatum", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Beställaren", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Referens", Element.ALIGN_LEFT, 1, bf8);

            insertCell(table, String.valueOf(pdfValues.getProjektnummer()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfValues.getDokumentnummer()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfValues.getAnsvarig()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfValues.getMatdatum()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfValues.getBestallare()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfValues.getReferens()), Element.ALIGN_LEFT, 1, f10);

            table.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph.add(table);
            // add the paragraph to the document
            document.add(paragraph);

            float[] columnWidths2 = {2f, 2f, 2f, 2f, 2f, 2f, 2f};

            //create PDF table with the given widths
            PdfPTable table2 = new PdfPTable(columnWidths2);
            // set table width a percentage of the page width
            table2.setWidthPercentage(100f);

            Paragraph paragraph2 = new Paragraph();

            insertCellSideBorders(table2, "Spår:", Element.ALIGN_LEFT, 1, bf10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getSpar()), Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 5, bf10, 3);

            insertCellSideBorders(table2, "Datum:", Element.ALIGN_LEFT, 1, bf10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getDatum()), Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 1, bf10, 1);
            insertCellSideBorders(table2, "Mätinstrument:", Element.ALIGN_LEFT, 1, bf10, 1);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getMatinstrument()), Element.ALIGN_LEFT, 3, f10, 3);

            insertCellSideBorders(table2, "Klockslag:", Element.ALIGN_LEFT, 1, bf10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getTid()), Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 5, bf10, 3);

            insertCellSideBorders(table2, "Väder:", Element.ALIGN_LEFT, 1, bf10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getVader()), Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 5, bf10, 3);

            insertCellSideBorders(table2, "Temperatur:", Element.ALIGN_LEFT, 1, bf10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getTemperatur()), Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 5, bf10, 3);

            insertCellSideBorders(table2, "Kontrollanter:", Element.ALIGN_LEFT, 1, bf10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfValues.getKontrollanter()), Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 5, bf10, 3);

            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 7, bf10, 0);

            table2.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph2.add(table2);
            // add the paragraph to the document
            document.add(paragraph2);

            //**************************************************************************************
            float[] columnWidths3 = {2f, 2f, 2f, 2f, 2f, 2f};

            //create PDF table with the given widths
            PdfPTable table3 = new PdfPTable(columnWidths3);
            // set table width a percentage of the page width
            table3.setWidthPercentage(100f);

            Paragraph paragraph3 = new Paragraph();

            //insert column headings
            insertCell(table3, "Stople Nr.", Element.ALIGN_CENTER, 1, bf10);
            insertCell(table3, "Objekt", Element.ALIGN_CENTER, 1, bf10);
            insertCell(table3, "Höjd av kontakttråd\n(mm)", Element.ALIGN_CENTER, 1, bf10);
            insertCell(table3, "Avvikelse i sidled\n(mm)", Element.ALIGN_CENTER, 1, bf10);
            insertCell(table3, "Status", Element.ALIGN_CENTER, 1, bf10); //Upphöjd av tillsatsrör bytt till Trådläget
            insertCell(table3, "Kommentar", Element.ALIGN_CENTER, 1, bf10);

            for (int i = 0; i < objectList.size(); i++) {
                Object object = objectList.get(i);
                //Stolpnummer
                insertCell(table3, String.valueOf(object.getStolpnummer()), Element.ALIGN_LEFT, 1, f10);
                //Objekt
                insertCell(table3, String.valueOf(object.getObjektForENE()), Element.ALIGN_LEFT, 1, f10);
                //Höjd av kontakttråd(mm)
                insertCell(table3, String.valueOf(object.getHojdAvKontakttrad()), Element.ALIGN_LEFT, 1, f10);
                //Avvikelse i sidled(mm)
                insertCell(table3, String.valueOf(object.getAvvikelseISidled()), Element.ALIGN_LEFT, 1, f10);
                //Status
                int completed = object.getCompleted();
                if (completed == 0)
                    insertCell(table3, "", Element.ALIGN_CENTER, 1, f10);
                else
                    insertCell(table3, "OK", Element.ALIGN_CENTER, 1, f10);
                //Kommentar
                insertCell(table3, String.valueOf(object.getComments()), Element.ALIGN_LEFT, 1, f10);
            }

            table3.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph3.add(table3);
            // add the paragraph to the document
            document.add(paragraph3);
            //**************************************************************************************

            float[] columnWidths4 = {3f, 1f};

            //create PDF table with the given widths
            PdfPTable table4 = new PdfPTable(columnWidths4);
            // set table width a percentage of the page width
            table4.setWidthPercentage(100f);

            Paragraph paragraph4 = new Paragraph();

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 0);

            insertCellSideBorders(table4, "Övriga kommentarer:", Element.ALIGN_LEFT, 2, header, 0);

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 0);

            insertCellSideBorders(table4, String.valueOf(pdfValues.getOvrigaKommentarer()), Element.ALIGN_LEFT, 2, f10, 0);

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 2);

            table4.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph4.add(table4);
            // add the paragraph to the document
            document.add(paragraph4);


        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            document.close();
        }
    }

    public void createPDFForPRMLjudmatningObjects(ArrayList<Object> objectList, EditPDFObject pdfObjectValues) {

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {

            //special font sizes
            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf10 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            Font bf8 = new Font(Font.FontFamily.TIMES_ROMAN, 8);
            Font header = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD | Font.UNDERLINE);

            File file = new File(dir, "Vanaheim.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(document, fOut);

            //document header attributes
            document.addAuthor("Vanaheim");
            document.addCreationDate();
            document.addProducer();
            document.addCreator("Vanaheim");
            document.addTitle("Checklista för ljudmätning");
            document.setPageSize(PageSize.LETTER);

            //open the document
            document.open();

            BaseFont bf = BaseFont.createFont("assets/freesans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font f8 = new Font(bf, 8);

            BaseFont bf2 = BaseFont.createFont("assets/freesans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font f10 = new Font(bf2, 10);

            //specify column widths
            float[] columnWidths = {2f, 2f, 2f, 1f, 2f, 1f};

            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setWidthPercentage(100f);

            Paragraph paragraph = new Paragraph();

            try {
                document.open();
                Drawable d = context.getResources().getDrawable(R.drawable.vanaheim_logga);
                BitmapDrawable bitDw = ((BitmapDrawable) d);
                Bitmap bmp = bitDw.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 30, stream);
                Image image = Image.getInstance(stream.toByteArray());

                int indentation = 0;
                float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                        - document.rightMargin() - indentation) / image.getWidth()) * 30;
                image.scalePercent(scaler);
                paragraph.add(image);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Måste ha en paragraf, annars kraschar formatet: The document has no pages
            paragraph.add("Checklista för ljudmätning");


            insertCell(table, "Projektnummer", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Dokumentnummer", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Ansvarig", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Mätdatum", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Beställaren", Element.ALIGN_LEFT, 1, bf8);
            insertCell(table, "Referens", Element.ALIGN_LEFT, 1, bf8);

            insertCell(table, String.valueOf(pdfObjectValues.getProjektnummer()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfObjectValues.getDokumentnummer()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfObjectValues.getAnsvarig()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfObjectValues.getMatdatum()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfObjectValues.getBestallare()), Element.ALIGN_LEFT, 1, f10);
            insertCell(table, String.valueOf(pdfObjectValues.getReferens()), Element.ALIGN_LEFT, 1, f10);


            table.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph.add(table);
            // add the paragraph to the document
            document.add(paragraph);


            float[] columnWidths2 = {2f, 2f, 2f, 2f, 2f, 2f, 2f};

            //create PDF table with the given widths
            PdfPTable table2 = new PdfPTable(columnWidths2);
            // set table width a percentage of the page width
            table2.setWidthPercentage(100f);

            Paragraph paragraph2 = new Paragraph();

            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 7, bf10, 0);

            insertCellSideBorders(table2, "Datum:", Element.ALIGN_LEFT, 1, bf10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfObjectValues.getDatum()), Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(table2, "Mätinstrument:", Element.ALIGN_LEFT, 1, bf10, 1);
            insertCellSideBorders(table2, String.valueOf(pdfObjectValues.getMatinstrument()), Element.ALIGN_LEFT, 4, f10, 3);

            insertCellSideBorders(table2, "Klockslag:", Element.ALIGN_LEFT, 1, bf10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfObjectValues.getTid()), Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(table2, "Kalibreringsinstrument:", Element.ALIGN_LEFT, 1, bf10, 1);
            insertCellSideBorders(table2, String.valueOf(pdfObjectValues.getKalibreringsinstrument()), Element.ALIGN_LEFT, 4, f10, 3);

            insertCellSideBorders(table2, "Väder:", Element.ALIGN_LEFT, 1, bf10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfObjectValues.getVader()), Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(table2, "Testsignal:", Element.ALIGN_LEFT, 1, bf10, 1);
            insertCellSideBorders(table2, String.valueOf(pdfObjectValues.getTestsignal()), Element.ALIGN_LEFT, 4, f10, 3);

            insertCellSideBorders(table2, "Temperatur:", Element.ALIGN_LEFT, 1, bf10, 4);
            insertCellSideBorders(table2, String.valueOf(pdfObjectValues.getTemperatur()), Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(table2, "Signalkälla:", Element.ALIGN_LEFT, 1, bf10, 1);
            insertCellSideBorders(table2, String.valueOf(pdfObjectValues.getSignalkalla()), Element.ALIGN_LEFT, 4, f10, 3);

            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 2, bf10, 4);
            insertCellSideBorders(table2, "Bakgrund STIPA:", Element.ALIGN_LEFT, 1, bf10, 1);
            insertCellSideBorders(table2, String.valueOf(pdfObjectValues.getBakgrundStipa()), Element.ALIGN_LEFT, 4, f10, 3);

            insertCellSideBorders(table2, "", Element.ALIGN_LEFT, 7, bf10, 0);

            table2.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph2.add(table2);
            // add the paragraph to the document
            document.add(paragraph2);

            //**************************************************************************************

            float[] columnWidths3 = {2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};

            //create PDF table with the given widths
            PdfPTable table3 = new PdfPTable(columnWidths3);
            // set table width a percentage of the page width
            table3.setWidthPercentage(100f);

            Paragraph paragraph3 = new Paragraph();

            //insert column headings
            insertCell(table3, "Plats", Element.ALIGN_CENTER, 1, bf10);
            insertCell(table3, "Objekt", Element.ALIGN_CENTER, 1, bf10);
            insertCell(table3, "Ärvärde\nSTIPA", Element.ALIGN_CENTER, 1, bf10);
            insertCell(table3, "Börvärde\nSTIPA", Element.ALIGN_CENTER, 1, bf10);
            insertCell(table3, "Medelvärde", Element.ALIGN_CENTER, 1, bf10);
            insertCell(table3, "Avvikelse", Element.ALIGN_CENTER, 1, bf10);
            insertCell(table3, "Status", Element.ALIGN_CENTER, 1, bf10);
            insertCell(table3, "Anmärkning", Element.ALIGN_CENTER, 1, bf10);

            for (int i = 0; i < objectList.size(); i++) {

                Object object = objectList.get(i);

                String plats = object.getPlats();
                String objektValue = object.getObjektForPRMLjudmatning();
                String borvardeValue = object.getBorvarde();
                String retrievedArvarde = object.getArvarde();
                String retrievedBorvarde = object.getBorvarde();
                String medelvarde = object.getMedelvarde();
                String avvikelse = object.getAvvikelse();
                String anmarkning = object.getAnmarkning();

                List<String> splitedArvardeValues;
                splitedArvardeValues = Arrays.asList(retrievedArvarde.split(","));
                String arvardeOne = String.valueOf(splitedArvardeValues.get(0));
                String arvardeTwo = String.valueOf(splitedArvardeValues.get(1));
                String arvardeThree = String.valueOf(splitedArvardeValues.get(2));

                //Plats
                insertCell(table3, plats, Element.ALIGN_LEFT, 1, f10);
                //Objekt
                insertCell(table3, "", Element.ALIGN_CENTER, 1, f10);
                //Ärvärde ett
                insertCell(table3, arvardeOne, Element.ALIGN_CENTER, 1, f10);
                //Börvärde ett
                insertCell(table3, borvardeValue, Element.ALIGN_CENTER, 1, f10);
                //Medelvärde tomt
                insertCell(table3, "", Element.ALIGN_CENTER, 1, f10);
                //Avvikelse tomt
                insertCell(table3, "", Element.ALIGN_CENTER, 1, f10);
                // Status tomt
                insertCell(table3, "", Element.ALIGN_CENTER, 1, f10);
                //Anmärkning tomt
                insertCell(table3, "", Element.ALIGN_CENTER, 1, f10);

                //Plats
                insertCell(table3, "", Element.ALIGN_CENTER, 1, f10);
                //Objekt
                insertCell(table3, objektValue, Element.ALIGN_CENTER, 1, f10);
                //Ärvärde ett
                insertCell(table3, arvardeTwo, Element.ALIGN_CENTER, 1, f10);
                //Börvärde ett
                insertCell(table3, borvardeValue, Element.ALIGN_CENTER, 1, f10);
                //Medelvärde tomt
                insertCell(table3, medelvarde, Element.ALIGN_CENTER, 1, f10);
                //Avvikelse tomt
                insertCell(table3, avvikelse, Element.ALIGN_CENTER, 1, f10);
                //Status värde
                int status = object.getCompleted();
                if (status == 0)
                    insertCell(table3, "", Element.ALIGN_CENTER, 1, f10);
                else
                    insertCell(table3, "OK", Element.ALIGN_CENTER, 1, f10);
                //Anmärkning tomt
                insertCell(table3, anmarkning, Element.ALIGN_CENTER, 1, f10);

                //Plats
                insertCell(table3, "", Element.ALIGN_CENTER, 1, f10);
                //Objekt
                insertCell(table3, "", Element.ALIGN_CENTER, 1, f10);
                //Ärvärde ett
                insertCell(table3, arvardeThree, Element.ALIGN_CENTER, 1, f10);
                //Börvärde ett
                insertCell(table3, borvardeValue, Element.ALIGN_CENTER, 1, f10);
                //Medelvärde tomt
                insertCell(table3, "", Element.ALIGN_CENTER, 1, f10);
                //Avvikelse tomt
                insertCell(table3, "", Element.ALIGN_CENTER, 1, f10);
                //Status tomt
                insertCell(table3, "", Element.ALIGN_CENTER, 1, f10);
                //Anmärkning tomt
                insertCell(table3, "", Element.ALIGN_CENTER, 1, f10);
            }

            table3.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph3.add(table3);
            // add the paragraph to the document
            document.add(paragraph3);

            //**************************************************************************************

            float[] columnWidths4 = {3f, 1f};

            //create PDF table with the given widths
            PdfPTable table4 = new PdfPTable(columnWidths4);
            // set table width a percentage of the page width
            table4.setWidthPercentage(100f);

            Paragraph paragraph4 = new Paragraph();

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 0);

            insertCellSideBorders(table4, "Övriga kommentarer:", Element.ALIGN_LEFT, 2, header, 0);

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 0);

            insertCellSideBorders(table4, String.valueOf(pdfObjectValues.getOvrigaKommentarer()), Element.ALIGN_LEFT, 2, f10, 0);

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 2);

            table4.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph4.add(table4);
            // add the paragraph to the document
            document.add(paragraph4);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            document.close();
        }
    }

    public void createPDFForPRMLjusmatningObjects(ArrayList<Object> ljusmatningslist, int numberOfColumns, EditPDFObject pdfValues, boolean rotate) {
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {

            //special font sizes
            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf10 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            Font bf8 = new Font(Font.FontFamily.TIMES_ROMAN, 8);
            Font bf6 = new Font(Font.FontFamily.TIMES_ROMAN, 6);
            Font header = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD | Font.UNDERLINE);

            File file = new File(dir, "Vanaheim.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(document, fOut);

            //document header attributes
            document.addAuthor("Vanaheim");
            document.addCreationDate();
            document.addProducer();
            document.addCreator("Vanaheim");
            document.addTitle("Checklista för ljusmätning");

            if (rotate == false)
                document.setPageSize(PageSize.LETTER);
            else
                document.setPageSize(PageSize.A4.rotate());

            //open the document
            document.open();

            BaseFont bf = BaseFont.createFont("assets/freesans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font f8 = new Font(bf, 8);

            BaseFont bf2 = BaseFont.createFont("assets/freesans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font f10 = new Font(bf2, 10);


            //specify column widths
            float[] columnWidthsForTable1 = {3f, 1f, 3f, 3f, 3f, 3f};

            //create PDF table with the given widths
            PdfPTable firstTable = new PdfPTable(columnWidthsForTable1);
            // set table width a percentage of the page width
            firstTable.setWidthPercentage(100f);

            Paragraph paragraph = new Paragraph();

            try {
                document.open();
                Drawable d = context.getResources().getDrawable(R.drawable.vanaheim_logga);
                BitmapDrawable bitDw = ((BitmapDrawable) d);
                Bitmap bmp = bitDw.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 30, stream);
                Image image = Image.getInstance(stream.toByteArray());

                int indentation = 0;
                float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                        - document.rightMargin() - indentation) / image.getWidth()) * 20;
                image.scalePercent(scaler);
                paragraph.add(image);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Måste ha en paragraf, annars kraschar formatet: The document has no pages
            paragraph.add("Checklista för ljusmätning");

            //Räkna ut värdena innan de sätts in överst i PDF:en
            int lowestValue = Integer.MAX_VALUE;
            int highestValue = Integer.MIN_VALUE;
            int numberOfValuesInserted = 0;
            int totalValueOfTable = 0;
            int averageValue = 0;

            double average;
            double lowest;
            double highest;

            String minDividedByAverageString = "";
            String minDividedByMaxString = "";

            Object currentObjectForValue;

            switch (numberOfColumns) {

                case 3:
                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentObjectForValue = ljusmatningslist.get(i);

                        String firstValueString = currentObjectForValue.getFirstValue();
                        String secondValueString = currentObjectForValue.getSecondValue();
                        String thirdValueString = currentObjectForValue.getThirdValue();

                        int firstValue;
                        int secondValue;
                        int thirdValue;

                        if (!firstValueString.trim().isEmpty()) {
                            firstValue = Integer.valueOf(firstValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += firstValue;
                            if (firstValue > highestValue) {
                                highestValue = firstValue;
                            } else if (firstValue < lowestValue) {
                                lowestValue = firstValue;
                            }
                        }

                        if (!secondValueString.trim().isEmpty()) {
                            secondValue = Integer.valueOf(secondValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += secondValue;
                            if (secondValue > highestValue) {
                                highestValue = secondValue;
                            } else if (secondValue < lowestValue) {
                                lowestValue = secondValue;
                            }
                        }

                        if (!thirdValueString.trim().isEmpty()) {
                            thirdValue = Integer.valueOf(thirdValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += thirdValue;
                            if (thirdValue > highestValue) {
                                highestValue = thirdValue;
                            } else if (thirdValue < lowestValue) {
                                lowestValue = thirdValue;
                            }
                        }
                    }
                    averageValue = totalValueOfTable / numberOfValuesInserted;
                    average = Double.valueOf(averageValue);
                    lowest = Double.valueOf(lowestValue);
                    highest = Double.valueOf(highestValue);

                    minDividedByAverageString = String.format("%.2f", lowest / average);
                    minDividedByMaxString = String.format("%.2f", lowest / highest);

                    break;
                case 4:
                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentObjectForValue = ljusmatningslist.get(i);

                        String firstValueString = currentObjectForValue.getFirstValue();
                        String secondValueString = currentObjectForValue.getSecondValue();
                        String thirdValueString = currentObjectForValue.getThirdValue();
                        String fourthValueString = currentObjectForValue.getFourthValue();

                        int firstValue;
                        int secondValue;
                        int thirdValue;
                        int fourthValue;

                        if (!firstValueString.trim().isEmpty()) {
                            firstValue = Integer.valueOf(firstValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += firstValue;
                            if (firstValue > highestValue) {
                                highestValue = firstValue;
                            } else if (firstValue < lowestValue) {
                                lowestValue = firstValue;
                            }
                        }

                        if (!secondValueString.trim().isEmpty()) {
                            secondValue = Integer.valueOf(secondValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += secondValue;
                            if (secondValue > highestValue) {
                                highestValue = secondValue;
                            } else if (secondValue < lowestValue) {
                                lowestValue = secondValue;
                            }
                        }

                        if (!thirdValueString.trim().isEmpty()) {
                            thirdValue = Integer.valueOf(thirdValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += thirdValue;
                            if (thirdValue > highestValue) {
                                highestValue = thirdValue;
                            } else if (thirdValue < lowestValue) {
                                lowestValue = thirdValue;
                            }
                        }

                        if (!fourthValueString.trim().isEmpty()) {
                            fourthValue = Integer.valueOf(fourthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fourthValue;
                            if (fourthValue > highestValue) {
                                highestValue = fourthValue;
                            } else if (fourthValue < lowestValue) {
                                lowestValue = fourthValue;
                            }
                        }
                    }
                    averageValue = totalValueOfTable / numberOfValuesInserted;
                    average = Double.valueOf(averageValue);
                    lowest = Double.valueOf(lowestValue);
                    highest = Double.valueOf(highestValue);

                    minDividedByAverageString = String.format("%.2f", lowest / average);
                    minDividedByMaxString = String.format("%.2f", lowest / highest);

                    break;
                case 5:

                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentObjectForValue = ljusmatningslist.get(i);

                        String firstValueString = currentObjectForValue.getFirstValue();
                        String secondValueString = currentObjectForValue.getSecondValue();
                        String thirdValueString = currentObjectForValue.getThirdValue();
                        String fourthValueString = currentObjectForValue.getFourthValue();
                        String fifthValueString = currentObjectForValue.getFifthValue();


                        int firstValue;
                        int secondValue;
                        int thirdValue;
                        int fourthValue;
                        int fifthValue;

                        if (!firstValueString.trim().isEmpty()) {
                            firstValue = Integer.valueOf(firstValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += firstValue;
                            if (firstValue > highestValue) {
                                highestValue = firstValue;
                            } else if (firstValue < lowestValue) {
                                lowestValue = firstValue;
                            }
                        }

                        if (!secondValueString.trim().isEmpty()) {
                            secondValue = Integer.valueOf(secondValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += secondValue;
                            if (secondValue > highestValue) {
                                highestValue = secondValue;
                            } else if (secondValue < lowestValue) {
                                lowestValue = secondValue;
                            }
                        }

                        if (!thirdValueString.trim().isEmpty()) {
                            thirdValue = Integer.valueOf(thirdValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += thirdValue;
                            if (thirdValue > highestValue) {
                                highestValue = thirdValue;
                            } else if (thirdValue < lowestValue) {
                                lowestValue = thirdValue;
                            }
                        }

                        if (!fourthValueString.trim().isEmpty()) {
                            fourthValue = Integer.valueOf(fourthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fourthValue;
                            if (fourthValue > highestValue) {
                                highestValue = fourthValue;
                            } else if (fourthValue < lowestValue) {
                                lowestValue = fourthValue;
                            }
                        }

                        if (!fifthValueString.trim().isEmpty()) {
                            fifthValue = Integer.valueOf(fifthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fifthValue;
                            if (fifthValue > highestValue) {
                                highestValue = fifthValue;
                            } else if (fifthValue < lowestValue) {
                                lowestValue = fifthValue;
                            }
                        }
                    }
                    averageValue = totalValueOfTable / numberOfValuesInserted;
                    average = Double.valueOf(averageValue);
                    lowest = Double.valueOf(lowestValue);
                    highest = Double.valueOf(highestValue);

                    minDividedByAverageString = String.format("%.2f", lowest / average);
                    minDividedByMaxString = String.format("%.2f", lowest / highest);

                    break;
                case 6:

                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentObjectForValue = ljusmatningslist.get(i);

                        String firstValueString = currentObjectForValue.getFirstValue();
                        String secondValueString = currentObjectForValue.getSecondValue();
                        String thirdValueString = currentObjectForValue.getThirdValue();
                        String fourthValueString = currentObjectForValue.getFourthValue();
                        String fifthValueString = currentObjectForValue.getFifthValue();
                        String sixthValueString = currentObjectForValue.getSixthValue();

                        int firstValue;
                        int secondValue;
                        int thirdValue;
                        int fourthValue;
                        int fifthValue;
                        int sixthValue;

                        if (!firstValueString.trim().isEmpty()) {
                            firstValue = Integer.valueOf(firstValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += firstValue;
                            if (firstValue > highestValue) {
                                highestValue = firstValue;
                            } else if (firstValue < lowestValue) {
                                lowestValue = firstValue;
                            }
                        }

                        if (!secondValueString.trim().isEmpty()) {
                            secondValue = Integer.valueOf(secondValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += secondValue;
                            if (secondValue > highestValue) {
                                highestValue = secondValue;
                            } else if (secondValue < lowestValue) {
                                lowestValue = secondValue;
                            }
                        }

                        if (!thirdValueString.trim().isEmpty()) {
                            thirdValue = Integer.valueOf(thirdValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += thirdValue;
                            if (thirdValue > highestValue) {
                                highestValue = thirdValue;
                            } else if (thirdValue < lowestValue) {
                                lowestValue = thirdValue;
                            }
                        }

                        if (!fourthValueString.trim().isEmpty()) {
                            fourthValue = Integer.valueOf(fourthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fourthValue;
                            if (fourthValue > highestValue) {
                                highestValue = fourthValue;
                            } else if (fourthValue < lowestValue) {
                                lowestValue = fourthValue;
                            }
                        }

                        if (!fifthValueString.trim().isEmpty()) {
                            fifthValue = Integer.valueOf(fifthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fifthValue;
                            if (fifthValue > highestValue) {
                                highestValue = fifthValue;
                            } else if (fifthValue < lowestValue) {
                                lowestValue = fifthValue;
                            }
                        }

                        if (!sixthValueString.trim().isEmpty()) {
                            sixthValue = Integer.valueOf(sixthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += sixthValue;
                            if (sixthValue > highestValue) {
                                highestValue = sixthValue;
                            } else if (sixthValue < lowestValue) {
                                lowestValue = sixthValue;
                            }
                        }
                    }
                    averageValue = totalValueOfTable / numberOfValuesInserted;
                    average = Double.valueOf(averageValue);
                    lowest = Double.valueOf(lowestValue);
                    highest = Double.valueOf(highestValue);

                    minDividedByAverageString = String.format("%.2f", lowest / average);
                    minDividedByMaxString = String.format("%.2f", lowest / highest);

                    break;
                case 7:

                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentObjectForValue = ljusmatningslist.get(i);

                        String firstValueString = currentObjectForValue.getFirstValue();
                        String secondValueString = currentObjectForValue.getSecondValue();
                        String thirdValueString = currentObjectForValue.getThirdValue();
                        String fourthValueString = currentObjectForValue.getFourthValue();
                        String fifthValueString = currentObjectForValue.getFifthValue();
                        String sixthValueString = currentObjectForValue.getSixthValue();
                        String seventhValueString = currentObjectForValue.getSeventhValue();

                        int firstValue;
                        int secondValue;
                        int thirdValue;
                        int fourthValue;
                        int fifthValue;
                        int sixthValue;
                        int seventhValue;

                        if (!firstValueString.trim().isEmpty()) {
                            firstValue = Integer.valueOf(firstValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += firstValue;
                            if (firstValue > highestValue) {
                                highestValue = firstValue;
                            } else if (firstValue < lowestValue) {
                                lowestValue = firstValue;
                            }
                        }

                        if (!secondValueString.trim().isEmpty()) {
                            secondValue = Integer.valueOf(secondValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += secondValue;
                            if (secondValue > highestValue) {
                                highestValue = secondValue;
                            } else if (secondValue < lowestValue) {
                                lowestValue = secondValue;
                            }
                        }

                        if (!thirdValueString.trim().isEmpty()) {
                            thirdValue = Integer.valueOf(thirdValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += thirdValue;
                            if (thirdValue > highestValue) {
                                highestValue = thirdValue;
                            } else if (thirdValue < lowestValue) {
                                lowestValue = thirdValue;
                            }
                        }

                        if (!fourthValueString.trim().isEmpty()) {
                            fourthValue = Integer.valueOf(fourthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fourthValue;
                            if (fourthValue > highestValue) {
                                highestValue = fourthValue;
                            } else if (fourthValue < lowestValue) {
                                lowestValue = fourthValue;
                            }
                        }

                        if (!fifthValueString.trim().isEmpty()) {
                            fifthValue = Integer.valueOf(fifthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fifthValue;
                            if (fifthValue > highestValue) {
                                highestValue = fifthValue;
                            } else if (fifthValue < lowestValue) {
                                lowestValue = fifthValue;
                            }
                        }

                        if (!sixthValueString.trim().isEmpty()) {
                            sixthValue = Integer.valueOf(sixthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += sixthValue;
                            if (sixthValue > highestValue) {
                                highestValue = sixthValue;
                            } else if (sixthValue < lowestValue) {
                                lowestValue = sixthValue;
                            }
                        }

                        if (!seventhValueString.trim().isEmpty()) {
                            seventhValue = Integer.valueOf(seventhValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += seventhValue;
                            if (seventhValue > highestValue) {
                                highestValue = seventhValue;
                            } else if (seventhValue < lowestValue) {
                                lowestValue = seventhValue;
                            }
                        }
                    }
                    averageValue = totalValueOfTable / numberOfValuesInserted;
                    average = Double.valueOf(averageValue);
                    lowest = Double.valueOf(lowestValue);
                    highest = Double.valueOf(highestValue);

                    minDividedByAverageString = String.format("%.2f", lowest / average);
                    minDividedByMaxString = String.format("%.2f", lowest / highest);

                    break;
                case 8:

                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentObjectForValue = ljusmatningslist.get(i);

                        String firstValueString = currentObjectForValue.getFirstValue();
                        String secondValueString = currentObjectForValue.getSecondValue();
                        String thirdValueString = currentObjectForValue.getThirdValue();
                        String fourthValueString = currentObjectForValue.getFourthValue();
                        String fifthValueString = currentObjectForValue.getFifthValue();
                        String sixthValueString = currentObjectForValue.getSixthValue();
                        String seventhValueString = currentObjectForValue.getSeventhValue();
                        String eightValueString = currentObjectForValue.getEightValue();

                        int firstValue;
                        int secondValue;
                        int thirdValue;
                        int fourthValue;
                        int fifthValue;
                        int sixthValue;
                        int seventhValue;
                        int eightValue;

                        if (!firstValueString.trim().isEmpty()) {
                            firstValue = Integer.valueOf(firstValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += firstValue;
                            if (firstValue > highestValue) {
                                highestValue = firstValue;
                            } else if (firstValue < lowestValue) {
                                lowestValue = firstValue;
                            }
                        }

                        if (!secondValueString.trim().isEmpty()) {
                            secondValue = Integer.valueOf(secondValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += secondValue;
                            if (secondValue > highestValue) {
                                highestValue = secondValue;
                            } else if (secondValue < lowestValue) {
                                lowestValue = secondValue;
                            }
                        }

                        if (!thirdValueString.trim().isEmpty()) {
                            thirdValue = Integer.valueOf(thirdValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += thirdValue;
                            if (thirdValue > highestValue) {
                                highestValue = thirdValue;
                            } else if (thirdValue < lowestValue) {
                                lowestValue = thirdValue;
                            }
                        }

                        if (!fourthValueString.trim().isEmpty()) {
                            fourthValue = Integer.valueOf(fourthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fourthValue;
                            if (fourthValue > highestValue) {
                                highestValue = fourthValue;
                            } else if (fourthValue < lowestValue) {
                                lowestValue = fourthValue;
                            }
                        }

                        if (!fifthValueString.trim().isEmpty()) {
                            fifthValue = Integer.valueOf(fifthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fifthValue;
                            if (fifthValue > highestValue) {
                                highestValue = fifthValue;
                            } else if (fifthValue < lowestValue) {
                                lowestValue = fifthValue;
                            }
                        }

                        if (!sixthValueString.trim().isEmpty()) {
                            sixthValue = Integer.valueOf(sixthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += sixthValue;
                            if (sixthValue > highestValue) {
                                highestValue = sixthValue;
                            } else if (sixthValue < lowestValue) {
                                lowestValue = sixthValue;
                            }
                        }

                        if (!seventhValueString.trim().isEmpty()) {
                            seventhValue = Integer.valueOf(seventhValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += seventhValue;
                            if (seventhValue > highestValue) {
                                highestValue = seventhValue;
                            } else if (seventhValue < lowestValue) {
                                lowestValue = seventhValue;
                            }
                        }

                        if (!eightValueString.trim().isEmpty()) {
                            eightValue = Integer.valueOf(eightValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += eightValue;
                            if (eightValue > highestValue) {
                                highestValue = eightValue;
                            } else if (eightValue < lowestValue) {
                                lowestValue = eightValue;
                            }
                        }
                    }
                    averageValue = totalValueOfTable / numberOfValuesInserted;
                    average = Double.valueOf(averageValue);
                    lowest = Double.valueOf(lowestValue);
                    highest = Double.valueOf(highestValue);

                    minDividedByAverageString = String.format("%.2f", lowest / average);
                    minDividedByMaxString = String.format("%.2f", lowest / highest);

                    break;
                case 9:

                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentObjectForValue = ljusmatningslist.get(i);

                        String firstValueString = currentObjectForValue.getFirstValue();
                        String secondValueString = currentObjectForValue.getSecondValue();
                        String thirdValueString = currentObjectForValue.getThirdValue();
                        String fourthValueString = currentObjectForValue.getFourthValue();
                        String fifthValueString = currentObjectForValue.getFifthValue();
                        String sixthValueString = currentObjectForValue.getSixthValue();
                        String seventhValueString = currentObjectForValue.getSeventhValue();
                        String eightValueString = currentObjectForValue.getEightValue();
                        String ninthValueString = currentObjectForValue.getNinthValue();

                        int firstValue;
                        int secondValue;
                        int thirdValue;
                        int fourthValue;
                        int fifthValue;
                        int sixthValue;
                        int seventhValue;
                        int eightValue;
                        int ninthValue;

                        if (!firstValueString.trim().isEmpty()) {
                            firstValue = Integer.valueOf(firstValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += firstValue;
                            if (firstValue > highestValue) {
                                highestValue = firstValue;
                            } else if (firstValue < lowestValue) {
                                lowestValue = firstValue;
                            }
                        }

                        if (!secondValueString.trim().isEmpty()) {
                            secondValue = Integer.valueOf(secondValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += secondValue;
                            if (secondValue > highestValue) {
                                highestValue = secondValue;
                            } else if (secondValue < lowestValue) {
                                lowestValue = secondValue;
                            }
                        }

                        if (!thirdValueString.trim().isEmpty()) {
                            thirdValue = Integer.valueOf(thirdValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += thirdValue;
                            if (thirdValue > highestValue) {
                                highestValue = thirdValue;
                            } else if (thirdValue < lowestValue) {
                                lowestValue = thirdValue;
                            }
                        }

                        if (!fourthValueString.trim().isEmpty()) {
                            fourthValue = Integer.valueOf(fourthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fourthValue;
                            if (fourthValue > highestValue) {
                                highestValue = fourthValue;
                            } else if (fourthValue < lowestValue) {
                                lowestValue = fourthValue;
                            }
                        }

                        if (!fifthValueString.trim().isEmpty()) {
                            fifthValue = Integer.valueOf(fifthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fifthValue;
                            if (fifthValue > highestValue) {
                                highestValue = fifthValue;
                            } else if (fifthValue < lowestValue) {
                                lowestValue = fifthValue;
                            }
                        }

                        if (!sixthValueString.trim().isEmpty()) {
                            sixthValue = Integer.valueOf(sixthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += sixthValue;
                            if (sixthValue > highestValue) {
                                highestValue = sixthValue;
                            } else if (sixthValue < lowestValue) {
                                lowestValue = sixthValue;
                            }
                        }

                        if (!seventhValueString.trim().isEmpty()) {
                            seventhValue = Integer.valueOf(seventhValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += seventhValue;
                            if (seventhValue > highestValue) {
                                highestValue = seventhValue;
                            } else if (seventhValue < lowestValue) {
                                lowestValue = seventhValue;
                            }
                        }

                        if (!eightValueString.trim().isEmpty()) {
                            eightValue = Integer.valueOf(eightValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += eightValue;
                            if (eightValue > highestValue) {
                                highestValue = eightValue;
                            } else if (eightValue < lowestValue) {
                                lowestValue = eightValue;
                            }
                        }

                        if (!ninthValueString.trim().isEmpty()) {
                            ninthValue = Integer.valueOf(ninthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += ninthValue;
                            if (ninthValue > highestValue) {
                                highestValue = ninthValue;
                            } else if (ninthValue < lowestValue) {
                                lowestValue = ninthValue;
                            }
                        }
                    }

                    averageValue = totalValueOfTable / numberOfValuesInserted;
                    average = Double.valueOf(averageValue);
                    lowest = Double.valueOf(lowestValue);
                    highest = Double.valueOf(highestValue);

                    minDividedByAverageString = String.format("%.2f", lowest / average);
                    minDividedByMaxString = String.format("%.2f", lowest / highest);

                    break;
                case 10:

                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentObjectForValue = ljusmatningslist.get(i);

                        String firstValueString = currentObjectForValue.getFirstValue();
                        String secondValueString = currentObjectForValue.getSecondValue();
                        String thirdValueString = currentObjectForValue.getThirdValue();
                        String fourthValueString = currentObjectForValue.getFourthValue();
                        String fifthValueString = currentObjectForValue.getFifthValue();
                        String sixthValueString = currentObjectForValue.getSixthValue();
                        String seventhValueString = currentObjectForValue.getSeventhValue();
                        String eightValueString = currentObjectForValue.getEightValue();
                        String ninthValueString = currentObjectForValue.getNinthValue();
                        String tenthValueString = currentObjectForValue.getTenthValue();

                        int firstValue;
                        int secondValue;
                        int thirdValue;
                        int fourthValue;
                        int fifthValue;
                        int sixthValue;
                        int seventhValue;
                        int eightValue;
                        int ninthValue;
                        int tenthValue;

                        if (!firstValueString.trim().isEmpty()) {
                            firstValue = Integer.valueOf(firstValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += firstValue;
                            if (firstValue > highestValue) {
                                highestValue = firstValue;
                            } else if (firstValue < lowestValue) {
                                lowestValue = firstValue;
                            }
                        }

                        if (!secondValueString.trim().isEmpty()) {
                            secondValue = Integer.valueOf(secondValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += secondValue;
                            if (secondValue > highestValue) {
                                highestValue = secondValue;
                            } else if (secondValue < lowestValue) {
                                lowestValue = secondValue;
                            }
                        }

                        if (!thirdValueString.trim().isEmpty()) {
                            thirdValue = Integer.valueOf(thirdValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += thirdValue;
                            if (thirdValue > highestValue) {
                                highestValue = thirdValue;
                            } else if (thirdValue < lowestValue) {
                                lowestValue = thirdValue;
                            }
                        }

                        if (!fourthValueString.trim().isEmpty()) {
                            fourthValue = Integer.valueOf(fourthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fourthValue;
                            if (fourthValue > highestValue) {
                                highestValue = fourthValue;
                            } else if (fourthValue < lowestValue) {
                                lowestValue = fourthValue;
                            }
                        }

                        if (!fifthValueString.trim().isEmpty()) {
                            fifthValue = Integer.valueOf(fifthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fifthValue;
                            if (fifthValue > highestValue) {
                                highestValue = fifthValue;
                            } else if (fifthValue < lowestValue) {
                                lowestValue = fifthValue;
                            }
                        }

                        if (!sixthValueString.trim().isEmpty()) {
                            sixthValue = Integer.valueOf(sixthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += sixthValue;
                            if (sixthValue > highestValue) {
                                highestValue = sixthValue;
                            } else if (sixthValue < lowestValue) {
                                lowestValue = sixthValue;
                            }
                        }

                        if (!seventhValueString.trim().isEmpty()) {
                            seventhValue = Integer.valueOf(seventhValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += seventhValue;
                            if (seventhValue > highestValue) {
                                highestValue = seventhValue;
                            } else if (seventhValue < lowestValue) {
                                lowestValue = seventhValue;
                            }
                        }

                        if (!eightValueString.trim().isEmpty()) {
                            eightValue = Integer.valueOf(eightValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += eightValue;
                            if (eightValue > highestValue) {
                                highestValue = eightValue;
                            } else if (eightValue < lowestValue) {
                                lowestValue = eightValue;
                            }
                        }

                        if (!ninthValueString.trim().isEmpty()) {
                            ninthValue = Integer.valueOf(ninthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += ninthValue;
                            if (ninthValue > highestValue) {
                                highestValue = ninthValue;
                            } else if (ninthValue < lowestValue) {
                                lowestValue = ninthValue;
                            }
                        }

                        if (!tenthValueString.trim().isEmpty()) {
                            tenthValue = Integer.valueOf(tenthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += tenthValue;
                            if (tenthValue > highestValue) {
                                highestValue = tenthValue;
                            } else if (tenthValue < lowestValue) {
                                lowestValue = tenthValue;
                            }
                        }
                    }

                    averageValue = totalValueOfTable / numberOfValuesInserted;
                    average = Double.valueOf(averageValue);
                    lowest = Double.valueOf(lowestValue);
                    highest = Double.valueOf(highestValue);

                    minDividedByAverageString = String.format("%.2f", lowest / average);
                    minDividedByMaxString = String.format("%.2f", lowest / highest);

                    break;
                case 11:

                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentObjectForValue = ljusmatningslist.get(i);

                        String firstValueString = currentObjectForValue.getFirstValue();
                        String secondValueString = currentObjectForValue.getSecondValue();
                        String thirdValueString = currentObjectForValue.getThirdValue();
                        String fourthValueString = currentObjectForValue.getFourthValue();
                        String fifthValueString = currentObjectForValue.getFifthValue();
                        String sixthValueString = currentObjectForValue.getSixthValue();
                        String seventhValueString = currentObjectForValue.getSeventhValue();
                        String eightValueString = currentObjectForValue.getEightValue();
                        String ninthValueString = currentObjectForValue.getNinthValue();
                        String tenthValueString = currentObjectForValue.getTenthValue();
                        String eleventhValueString = currentObjectForValue.getEleventhValue();

                        int firstValue;
                        int secondValue;
                        int thirdValue;
                        int fourthValue;
                        int fifthValue;
                        int sixthValue;
                        int seventhValue;
                        int eightValue;
                        int ninthValue;
                        int tenthValue;
                        int eleventhValue;

                        if (!firstValueString.trim().isEmpty()) {
                            firstValue = Integer.valueOf(firstValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += firstValue;
                            if (firstValue > highestValue) {
                                highestValue = firstValue;
                            } else if (firstValue < lowestValue) {
                                lowestValue = firstValue;
                            }
                        }

                        if (!secondValueString.trim().isEmpty()) {
                            secondValue = Integer.valueOf(secondValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += secondValue;
                            if (secondValue > highestValue) {
                                highestValue = secondValue;
                            } else if (secondValue < lowestValue) {
                                lowestValue = secondValue;
                            }
                        }

                        if (!thirdValueString.trim().isEmpty()) {
                            thirdValue = Integer.valueOf(thirdValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += thirdValue;
                            if (thirdValue > highestValue) {
                                highestValue = thirdValue;
                            } else if (thirdValue < lowestValue) {
                                lowestValue = thirdValue;
                            }
                        }

                        if (!fourthValueString.trim().isEmpty()) {
                            fourthValue = Integer.valueOf(fourthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fourthValue;
                            if (fourthValue > highestValue) {
                                highestValue = fourthValue;
                            } else if (fourthValue < lowestValue) {
                                lowestValue = fourthValue;
                            }
                        }

                        if (!fifthValueString.trim().isEmpty()) {
                            fifthValue = Integer.valueOf(fifthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fifthValue;
                            if (fifthValue > highestValue) {
                                highestValue = fifthValue;
                            } else if (fifthValue < lowestValue) {
                                lowestValue = fifthValue;
                            }
                        }

                        if (!sixthValueString.trim().isEmpty()) {
                            sixthValue = Integer.valueOf(sixthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += sixthValue;
                            if (sixthValue > highestValue) {
                                highestValue = sixthValue;
                            } else if (sixthValue < lowestValue) {
                                lowestValue = sixthValue;
                            }
                        }

                        if (!seventhValueString.trim().isEmpty()) {
                            seventhValue = Integer.valueOf(seventhValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += seventhValue;
                            if (seventhValue > highestValue) {
                                highestValue = seventhValue;
                            } else if (seventhValue < lowestValue) {
                                lowestValue = seventhValue;
                            }
                        }

                        if (!eightValueString.trim().isEmpty()) {
                            eightValue = Integer.valueOf(eightValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += eightValue;
                            if (eightValue > highestValue) {
                                highestValue = eightValue;
                            } else if (eightValue < lowestValue) {
                                lowestValue = eightValue;
                            }
                        }

                        if (!ninthValueString.trim().isEmpty()) {
                            ninthValue = Integer.valueOf(ninthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += ninthValue;
                            if (ninthValue > highestValue) {
                                highestValue = ninthValue;
                            } else if (ninthValue < lowestValue) {
                                lowestValue = ninthValue;
                            }
                        }

                        if (!tenthValueString.trim().isEmpty()) {
                            tenthValue = Integer.valueOf(tenthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += tenthValue;
                            if (tenthValue > highestValue) {
                                highestValue = tenthValue;
                            } else if (tenthValue < lowestValue) {
                                lowestValue = tenthValue;
                            }
                        }

                        if (!eleventhValueString.trim().isEmpty()) {
                            eleventhValue = Integer.valueOf(eleventhValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += eleventhValue;
                            if (eleventhValue > highestValue) {
                                highestValue = eleventhValue;
                            } else if (eleventhValue < lowestValue) {
                                lowestValue = eleventhValue;
                            }
                        }
                    }

                    averageValue = totalValueOfTable / numberOfValuesInserted;
                    average = Double.valueOf(averageValue);
                    lowest = Double.valueOf(lowestValue);
                    highest = Double.valueOf(highestValue);

                    minDividedByAverageString = String.format("%.2f", lowest / average);
                    minDividedByMaxString = String.format("%.2f", lowest / highest);

                    break;
                case 12:

                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentObjectForValue = ljusmatningslist.get(i);

                        String firstValueString = currentObjectForValue.getFirstValue();
                        String secondValueString = currentObjectForValue.getSecondValue();
                        String thirdValueString = currentObjectForValue.getThirdValue();
                        String fourthValueString = currentObjectForValue.getFourthValue();
                        String fifthValueString = currentObjectForValue.getFifthValue();
                        String sixthValueString = currentObjectForValue.getSixthValue();
                        String seventhValueString = currentObjectForValue.getSeventhValue();
                        String eightValueString = currentObjectForValue.getEightValue();
                        String ninthValueString = currentObjectForValue.getNinthValue();
                        String tenthValueString = currentObjectForValue.getTenthValue();
                        String eleventhValueString = currentObjectForValue.getEleventhValue();
                        String twelfthValueString = currentObjectForValue.getTwelfthValue();

                        int firstValue;
                        int secondValue;
                        int thirdValue;
                        int fourthValue;
                        int fifthValue;
                        int sixthValue;
                        int seventhValue;
                        int eightValue;
                        int ninthValue;
                        int tenthValue;
                        int eleventhValue;
                        int twelfthValue;

                        if (!firstValueString.trim().isEmpty()) {
                            firstValue = Integer.valueOf(firstValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += firstValue;
                            if (firstValue > highestValue) {
                                highestValue = firstValue;
                            } else if (firstValue < lowestValue) {
                                lowestValue = firstValue;
                            }
                        }

                        if (!secondValueString.trim().isEmpty()) {
                            secondValue = Integer.valueOf(secondValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += secondValue;
                            if (secondValue > highestValue) {
                                highestValue = secondValue;
                            } else if (secondValue < lowestValue) {
                                lowestValue = secondValue;
                            }
                        }

                        if (!thirdValueString.trim().isEmpty()) {
                            thirdValue = Integer.valueOf(thirdValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += thirdValue;
                            if (thirdValue > highestValue) {
                                highestValue = thirdValue;
                            } else if (thirdValue < lowestValue) {
                                lowestValue = thirdValue;
                            }
                        }

                        if (!fourthValueString.trim().isEmpty()) {
                            fourthValue = Integer.valueOf(fourthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fourthValue;
                            if (fourthValue > highestValue) {
                                highestValue = fourthValue;
                            } else if (fourthValue < lowestValue) {
                                lowestValue = fourthValue;
                            }
                        }

                        if (!fifthValueString.trim().isEmpty()) {
                            fifthValue = Integer.valueOf(fifthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fifthValue;
                            if (fifthValue > highestValue) {
                                highestValue = fifthValue;
                            } else if (fifthValue < lowestValue) {
                                lowestValue = fifthValue;
                            }
                        }

                        if (!sixthValueString.trim().isEmpty()) {
                            sixthValue = Integer.valueOf(sixthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += sixthValue;
                            if (sixthValue > highestValue) {
                                highestValue = sixthValue;
                            } else if (sixthValue < lowestValue) {
                                lowestValue = sixthValue;
                            }
                        }

                        if (!seventhValueString.trim().isEmpty()) {
                            seventhValue = Integer.valueOf(seventhValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += seventhValue;
                            if (seventhValue > highestValue) {
                                highestValue = seventhValue;
                            } else if (seventhValue < lowestValue) {
                                lowestValue = seventhValue;
                            }
                        }

                        if (!eightValueString.trim().isEmpty()) {
                            eightValue = Integer.valueOf(eightValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += eightValue;
                            if (eightValue > highestValue) {
                                highestValue = eightValue;
                            } else if (eightValue < lowestValue) {
                                lowestValue = eightValue;
                            }
                        }

                        if (!ninthValueString.trim().isEmpty()) {
                            ninthValue = Integer.valueOf(ninthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += ninthValue;
                            if (ninthValue > highestValue) {
                                highestValue = ninthValue;
                            } else if (ninthValue < lowestValue) {
                                lowestValue = ninthValue;
                            }
                        }

                        if (!tenthValueString.trim().isEmpty()) {
                            tenthValue = Integer.valueOf(tenthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += tenthValue;
                            if (tenthValue > highestValue) {
                                highestValue = tenthValue;
                            } else if (tenthValue < lowestValue) {
                                lowestValue = tenthValue;
                            }
                        }

                        if (!eleventhValueString.trim().isEmpty()) {
                            eleventhValue = Integer.valueOf(eleventhValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += eleventhValue;
                            if (eleventhValue > highestValue) {
                                highestValue = eleventhValue;
                            } else if (eleventhValue < lowestValue) {
                                lowestValue = eleventhValue;
                            }
                        }

                        if (!twelfthValueString.trim().isEmpty()) {
                            twelfthValue = Integer.valueOf(twelfthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += twelfthValue;
                            if (twelfthValue > highestValue) {
                                highestValue = twelfthValue;
                            } else if (twelfthValue < lowestValue) {
                                lowestValue = twelfthValue;
                            }
                        }
                    }

                    averageValue = totalValueOfTable / numberOfValuesInserted;
                    average = Double.valueOf(averageValue);
                    lowest = Double.valueOf(lowestValue);
                    highest = Double.valueOf(highestValue);

                    minDividedByAverageString = String.format("%.2f", lowest / average);
                    minDividedByMaxString = String.format("%.2f", lowest / highest);

                    break;
                case 13:

                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentObjectForValue = ljusmatningslist.get(i);

                        String firstValueString = currentObjectForValue.getFirstValue();
                        String secondValueString = currentObjectForValue.getSecondValue();
                        String thirdValueString = currentObjectForValue.getThirdValue();
                        String fourthValueString = currentObjectForValue.getFourthValue();
                        String fifthValueString = currentObjectForValue.getFifthValue();
                        String sixthValueString = currentObjectForValue.getSixthValue();
                        String seventhValueString = currentObjectForValue.getSeventhValue();
                        String eightValueString = currentObjectForValue.getEightValue();
                        String ninthValueString = currentObjectForValue.getNinthValue();
                        String tenthValueString = currentObjectForValue.getTenthValue();
                        String eleventhValueString = currentObjectForValue.getEleventhValue();
                        String twelfthValueString = currentObjectForValue.getTwelfthValue();
                        String thirteenthValueString = currentObjectForValue.getThirteenthValue();

                        int firstValue;
                        int secondValue;
                        int thirdValue;
                        int fourthValue;
                        int fifthValue;
                        int sixthValue;
                        int seventhValue;
                        int eightValue;
                        int ninthValue;
                        int tenthValue;
                        int eleventhValue;
                        int twelfthValue;
                        int thirteenthValue;

                        if (!firstValueString.trim().isEmpty()) {
                            firstValue = Integer.valueOf(firstValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += firstValue;
                            if (firstValue > highestValue) {
                                highestValue = firstValue;
                            } else if (firstValue < lowestValue) {
                                lowestValue = firstValue;
                            }
                        }

                        if (!secondValueString.trim().isEmpty()) {
                            secondValue = Integer.valueOf(secondValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += secondValue;
                            if (secondValue > highestValue) {
                                highestValue = secondValue;
                            } else if (secondValue < lowestValue) {
                                lowestValue = secondValue;
                            }
                        }

                        if (!thirdValueString.trim().isEmpty()) {
                            thirdValue = Integer.valueOf(thirdValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += thirdValue;
                            if (thirdValue > highestValue) {
                                highestValue = thirdValue;
                            } else if (thirdValue < lowestValue) {
                                lowestValue = thirdValue;
                            }
                        }

                        if (!fourthValueString.trim().isEmpty()) {
                            fourthValue = Integer.valueOf(fourthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fourthValue;
                            if (fourthValue > highestValue) {
                                highestValue = fourthValue;
                            } else if (fourthValue < lowestValue) {
                                lowestValue = fourthValue;
                            }
                        }

                        if (!fifthValueString.trim().isEmpty()) {
                            fifthValue = Integer.valueOf(fifthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fifthValue;
                            if (fifthValue > highestValue) {
                                highestValue = fifthValue;
                            } else if (fifthValue < lowestValue) {
                                lowestValue = fifthValue;
                            }
                        }

                        if (!sixthValueString.trim().isEmpty()) {
                            sixthValue = Integer.valueOf(sixthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += sixthValue;
                            if (sixthValue > highestValue) {
                                highestValue = sixthValue;
                            } else if (sixthValue < lowestValue) {
                                lowestValue = sixthValue;
                            }
                        }

                        if (!seventhValueString.trim().isEmpty()) {
                            seventhValue = Integer.valueOf(seventhValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += seventhValue;
                            if (seventhValue > highestValue) {
                                highestValue = seventhValue;
                            } else if (seventhValue < lowestValue) {
                                lowestValue = seventhValue;
                            }
                        }

                        if (!eightValueString.trim().isEmpty()) {
                            eightValue = Integer.valueOf(eightValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += eightValue;
                            if (eightValue > highestValue) {
                                highestValue = eightValue;
                            } else if (eightValue < lowestValue) {
                                lowestValue = eightValue;
                            }
                        }

                        if (!ninthValueString.trim().isEmpty()) {
                            ninthValue = Integer.valueOf(ninthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += ninthValue;
                            if (ninthValue > highestValue) {
                                highestValue = ninthValue;
                            } else if (ninthValue < lowestValue) {
                                lowestValue = ninthValue;
                            }
                        }

                        if (!tenthValueString.trim().isEmpty()) {
                            tenthValue = Integer.valueOf(tenthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += tenthValue;
                            if (tenthValue > highestValue) {
                                highestValue = tenthValue;
                            } else if (tenthValue < lowestValue) {
                                lowestValue = tenthValue;
                            }
                        }

                        if (!eleventhValueString.trim().isEmpty()) {
                            eleventhValue = Integer.valueOf(eleventhValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += eleventhValue;
                            if (eleventhValue > highestValue) {
                                highestValue = eleventhValue;
                            } else if (eleventhValue < lowestValue) {
                                lowestValue = eleventhValue;
                            }
                        }

                        if (!twelfthValueString.trim().isEmpty()) {
                            twelfthValue = Integer.valueOf(twelfthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += twelfthValue;
                            if (twelfthValue > highestValue) {
                                highestValue = twelfthValue;
                            } else if (twelfthValue < lowestValue) {
                                lowestValue = twelfthValue;
                            }
                        }

                        if (!thirteenthValueString.trim().isEmpty()) {
                            thirteenthValue = Integer.valueOf(thirteenthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += thirteenthValue;
                            if (thirteenthValue > highestValue) {
                                highestValue = thirteenthValue;
                            } else if (thirteenthValue < lowestValue) {
                                lowestValue = thirteenthValue;
                            }
                        }
                    }

                    averageValue = totalValueOfTable / numberOfValuesInserted;
                    average = Double.valueOf(averageValue);
                    lowest = Double.valueOf(lowestValue);
                    highest = Double.valueOf(highestValue);

                    minDividedByAverageString = String.format("%.2f", lowest / average);
                    minDividedByMaxString = String.format("%.2f", lowest / highest);

                    break;
                case 14:

                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentObjectForValue = ljusmatningslist.get(i);

                        String firstValueString = currentObjectForValue.getFirstValue();
                        String secondValueString = currentObjectForValue.getSecondValue();
                        String thirdValueString = currentObjectForValue.getThirdValue();
                        String fourthValueString = currentObjectForValue.getFourthValue();
                        String fifthValueString = currentObjectForValue.getFifthValue();
                        String sixthValueString = currentObjectForValue.getSixthValue();
                        String seventhValueString = currentObjectForValue.getSeventhValue();
                        String eightValueString = currentObjectForValue.getEightValue();
                        String ninthValueString = currentObjectForValue.getNinthValue();
                        String tenthValueString = currentObjectForValue.getTenthValue();
                        String eleventhValueString = currentObjectForValue.getEleventhValue();
                        String twelfthValueString = currentObjectForValue.getTwelfthValue();
                        String thirteenthValueString = currentObjectForValue.getThirteenthValue();
                        String fourteenthValueSting = currentObjectForValue.getFourteenthValue();

                        int firstValue;
                        int secondValue;
                        int thirdValue;
                        int fourthValue;
                        int fifthValue;
                        int sixthValue;
                        int seventhValue;
                        int eightValue;
                        int ninthValue;
                        int tenthValue;
                        int eleventhValue;
                        int twelfthValue;
                        int thirteenthValue;
                        int fourteenthValue;

                        if (!firstValueString.trim().isEmpty()) {
                            firstValue = Integer.valueOf(firstValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += firstValue;
                            if (firstValue > highestValue) {
                                highestValue = firstValue;
                            } else if (firstValue < lowestValue) {
                                lowestValue = firstValue;
                            }
                        }

                        if (!secondValueString.trim().isEmpty()) {
                            secondValue = Integer.valueOf(secondValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += secondValue;
                            if (secondValue > highestValue) {
                                highestValue = secondValue;
                            } else if (secondValue < lowestValue) {
                                lowestValue = secondValue;
                            }
                        }

                        if (!thirdValueString.trim().isEmpty()) {
                            thirdValue = Integer.valueOf(thirdValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += thirdValue;
                            if (thirdValue > highestValue) {
                                highestValue = thirdValue;
                            } else if (thirdValue < lowestValue) {
                                lowestValue = thirdValue;
                            }
                        }

                        if (!fourthValueString.trim().isEmpty()) {
                            fourthValue = Integer.valueOf(fourthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fourthValue;
                            if (fourthValue > highestValue) {
                                highestValue = fourthValue;
                            } else if (fourthValue < lowestValue) {
                                lowestValue = fourthValue;
                            }
                        }

                        if (!fifthValueString.trim().isEmpty()) {
                            fifthValue = Integer.valueOf(fifthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += fifthValue;
                            if (fifthValue > highestValue) {
                                highestValue = fifthValue;
                            } else if (fifthValue < lowestValue) {
                                lowestValue = fifthValue;
                            }
                        }

                        if (!sixthValueString.trim().isEmpty()) {
                            sixthValue = Integer.valueOf(sixthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += sixthValue;
                            if (sixthValue > highestValue) {
                                highestValue = sixthValue;
                            } else if (sixthValue < lowestValue) {
                                lowestValue = sixthValue;
                            }
                        }

                        if (!seventhValueString.trim().isEmpty()) {
                            seventhValue = Integer.valueOf(seventhValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += seventhValue;
                            if (seventhValue > highestValue) {
                                highestValue = seventhValue;
                            } else if (seventhValue < lowestValue) {
                                lowestValue = seventhValue;
                            }
                        }

                        if (!eightValueString.trim().isEmpty()) {
                            eightValue = Integer.valueOf(eightValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += eightValue;
                            if (eightValue > highestValue) {
                                highestValue = eightValue;
                            } else if (eightValue < lowestValue) {
                                lowestValue = eightValue;
                            }
                        }

                        if (!ninthValueString.trim().isEmpty()) {
                            ninthValue = Integer.valueOf(ninthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += ninthValue;
                            if (ninthValue > highestValue) {
                                highestValue = ninthValue;
                            } else if (ninthValue < lowestValue) {
                                lowestValue = ninthValue;
                            }
                        }

                        if (!tenthValueString.trim().isEmpty()) {
                            tenthValue = Integer.valueOf(tenthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += tenthValue;
                            if (tenthValue > highestValue) {
                                highestValue = tenthValue;
                            } else if (tenthValue < lowestValue) {
                                lowestValue = tenthValue;
                            }
                        }

                        if (!eleventhValueString.trim().isEmpty()) {
                            eleventhValue = Integer.valueOf(eleventhValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += eleventhValue;
                            if (eleventhValue > highestValue) {
                                highestValue = eleventhValue;
                            } else if (eleventhValue < lowestValue) {
                                lowestValue = eleventhValue;
                            }
                        }

                        if (!twelfthValueString.trim().isEmpty()) {
                            twelfthValue = Integer.valueOf(twelfthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += twelfthValue;
                            if (twelfthValue > highestValue) {
                                highestValue = twelfthValue;
                            } else if (twelfthValue < lowestValue) {
                                lowestValue = twelfthValue;
                            }
                        }

                        if (!thirteenthValueString.trim().isEmpty()) {
                            thirteenthValue = Integer.valueOf(thirteenthValueString);
                            numberOfValuesInserted++;
                            totalValueOfTable += thirteenthValue;
                            if (thirteenthValue > highestValue) {
                                highestValue = thirteenthValue;
                            } else if (thirteenthValue < lowestValue) {
                                lowestValue = thirteenthValue;
                            }
                        }
                        if (!fourteenthValueSting.trim().isEmpty()) {
                            fourteenthValue = Integer.valueOf(fourteenthValueSting);
                            numberOfValuesInserted++;
                            totalValueOfTable += fourteenthValue;
                            if (fourteenthValue > highestValue) {
                                highestValue = fourteenthValue;
                            } else if (fourteenthValue < lowestValue) {
                                lowestValue = fourteenthValue;
                            }
                        }
                    }

                    averageValue = totalValueOfTable / numberOfValuesInserted;
                    average = Double.valueOf(averageValue);
                    lowest = Double.valueOf(lowestValue);
                    highest = Double.valueOf(highestValue);

                    minDividedByAverageString = String.format("%.2f", lowest / average);
                    minDividedByMaxString = String.format("%.2f", lowest / highest);

                    break;
            }

            //First table
            insertCell(firstTable, "Driftplats", Element.ALIGN_LEFT, 1, bf8);
            insertCell(firstTable, "Projektnmr.", Element.ALIGN_LEFT, 1, bf8);
            insertCell(firstTable, "Dokumentnmr.", Element.ALIGN_LEFT, 1, bf8);
            insertCell(firstTable, "Ansvarig", Element.ALIGN_LEFT, 1, bf8);
            insertCell(firstTable, "Mätdatum", Element.ALIGN_LEFT, 1, bf8);
            insertCell(firstTable, "Fastställd av", Element.ALIGN_LEFT, 1, bf8);

            //Användaren ska fylla i innan metoden pdf-metoden anropas
            insertCell(firstTable, String.valueOf(pdfValues.getDriftplats()), Element.ALIGN_LEFT, 1, f10);
            insertCell(firstTable, String.valueOf(pdfValues.getProjektnummer()), Element.ALIGN_LEFT, 1, f10);
            insertCell(firstTable, String.valueOf(pdfValues.getDokumentnummer()), Element.ALIGN_LEFT, 1, f10);
            insertCell(firstTable, String.valueOf(pdfValues.getAnsvarig()), Element.ALIGN_LEFT, 1, f10);
            insertCell(firstTable, String.valueOf(pdfValues.getMatdatum()), Element.ALIGN_LEFT, 1, f10);
            insertCell(firstTable, String.valueOf(pdfValues.getFaststalldAv()), Element.ALIGN_LEFT, 1, f10);

            //Plats och väder
            insertCellSideBorders(firstTable, "Plats", Element.ALIGN_LEFT, 1, f10, 4);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getPlats()), Element.ALIGN_LEFT, 2, f10, 1);
            insertCellSideBorders(firstTable, "Väder", Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getVader()), Element.ALIGN_LEFT, 2, f10, 3);

            //Datum och temperatur
            insertCellSideBorders(firstTable, "Datum", Element.ALIGN_LEFT, 1, f10, 4);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getDatum()), Element.ALIGN_LEFT, 2, f10, 1);
            insertCellSideBorders(firstTable, "Temperatur", Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getTemperatur()) + "\u00B0C", Element.ALIGN_LEFT, 2, f10, 3);

            //Tid och nedebörd
            insertCellSideBorders(firstTable, "Tid", Element.ALIGN_LEFT, 1, f10, 4);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getTid()), Element.ALIGN_LEFT, 2, f10, 1);
            insertCellSideBorders(firstTable, "Nedebörd", Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getNederbord()), Element.ALIGN_LEFT, 2, f10, 3);

            //Deltagare och Soluppgång
            insertCellSideBorders(firstTable, "Deltagare", Element.ALIGN_LEFT, 1, f10, 4);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getDeltagare()), Element.ALIGN_LEFT, 2, f10, 1);
            insertCellSideBorders(firstTable, "Soluppgång", Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getSoluppgang()), Element.ALIGN_LEFT, 2, f10, 3);

            //Beställare och Solnedgång
            insertCellSideBorders(firstTable, "Beställare", Element.ALIGN_LEFT, 1, f10, 4);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getBestallare()), Element.ALIGN_LEFT, 2, f10, 1);
            insertCellSideBorders(firstTable, "Solnedgång", Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getSolnedgang()), Element.ALIGN_LEFT, 2, f10, 3);

            //Beställare ref och stationsklass
            insertCellSideBorders(firstTable, "Beställare ref", Element.ALIGN_LEFT, 1, f10, 4);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getBestallareRef()), Element.ALIGN_LEFT, 2, f10, 1);
            insertCellSideBorders(firstTable, "Stationsklass", Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getStationsklass()), Element.ALIGN_LEFT, 2, f10, 3);

            //Mätinstrument och Regelverk
            insertCellSideBorders(firstTable, "Mätinstrument", Element.ALIGN_LEFT, 1, f10, 4);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getMatinstrument()), Element.ALIGN_LEFT, 2, f10, 1);
            insertCellSideBorders(firstTable, "Regelverk", Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getRegelverk()), Element.ALIGN_LEFT, 2, f10, 3);

            //Serienummer och Medelvärde
            insertCellSideBorders(firstTable, "Serienummer", Element.ALIGN_LEFT, 1, f10, 4);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getSerienummer()), Element.ALIGN_LEFT, 2, f10, 1);
            insertCellSideBorders(firstTable, "Medelvärde Em", Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(firstTable, "= " + String.valueOf(averageValue), Element.ALIGN_LEFT, 2, f10, 3);

            //Kalibrerings id och Jämnhet PLF U
            insertCellSideBorders(firstTable, "Kalibrerings id", Element.ALIGN_LEFT, 1, f10, 4);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getKalibreringsId()), Element.ALIGN_LEFT, 2, f10, 1);
            insertCellSideBorders(firstTable, "Jämnhet PLF Uo", Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(firstTable, "= " + minDividedByAverageString + " (min/medel)", Element.ALIGN_LEFT, 2, f10, 3);

            //Kalibreringsdatum och jämnhet PLF U
            insertCellSideBorders(firstTable, "Kalibreringsdatum", Element.ALIGN_LEFT, 1, f10, 4);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getKalibreringsdatum()), Element.ALIGN_LEFT, 2, f10, 1);
            insertCellSideBorders(firstTable, "Jämnhet PLF Ud", Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(firstTable, "= " + minDividedByMaxString + " (min/max)", Element.ALIGN_LEFT, 2, f10, 3);

            //Nästa kalibrering och höjd armatur
            insertCellSideBorders(firstTable, "Nästa kalibrering", Element.ALIGN_LEFT, 1, f10, 4);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getNastaKalibrering()), Element.ALIGN_LEFT, 2, f10, 1);
            insertCellSideBorders(firstTable, "Höjd armatur", Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getHojdArmatur()), Element.ALIGN_LEFT, 2, f10, 3);

            //PLF början till 1 Belysningsstolpe och Stolpe - stople
            insertCellSideBorders(firstTable, "Armatur", Element.ALIGN_LEFT, 1, f10, 4);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getArmatur()), Element.ALIGN_LEFT, 2, f10, 1);
            insertCellSideBorders(firstTable, "Mätområde", Element.ALIGN_LEFT, 1, f10, 1);
            insertCellSideBorders(firstTable, String.valueOf(pdfValues.getMatomrade()), Element.ALIGN_LEFT, 2, f10, 3);

            firstTable.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph.add(firstTable);
            // add the paragraph to the document
            document.add(paragraph);

            //******************************************************

            //create PDF table with the given widths
            PdfPTable secondTable;

            Paragraph paragraph2 = new Paragraph();

            float[] columnWidthsForTable2;

            Object columns;
            Object currentRow;

            switch (numberOfColumns) {

                case 3:
                    columnWidthsForTable2 = new float[]{1f, 1f, 1f, 1f};

                    //create PDF table with the given widths
                    secondTable = new PdfPTable(columnWidthsForTable2);
                    // set table width a percentage of the page width
                    secondTable.setWidthPercentage(100f);
                    secondTable.setHorizontalAlignment(Element.ALIGN_LEFT);

                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 4, bf8, 0);

                    columns = ljusmatningslist.get(0);

                    //Bredd på plf och bak plattform
                    insertCellSideBorders(secondTable, "Bredd på plf " + columns.getFirstValue() + " = plfk", Element.ALIGN_LEFT, 1, bf6, 4);
                    insertCellSideBorders(secondTable, columns.getThirdValue() + " = bak plattform", Element.ALIGN_LEFT, 1, bf6, 1);
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 2, bf6, 3);

                    //insert columns
                    insertCell(secondTable, "bredd | längd", Element.ALIGN_LEFT, 1, bf8, false);

                    columns = ljusmatningslist.get(0);

                    insertCell(secondTable, columns.getFirstValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSecondValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getThirdValue(), Element.ALIGN_CENTER, 1, bf8, false);

                    //insert rows
                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentRow = ljusmatningslist.get(i);
                        String widthValue = currentRow.getWidthValue();
                        if (!widthValue.equals("")) {
                            insertCell(secondTable, currentRow.getWidthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFirstValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSecondValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getThirdValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                        }
                    }

                    break;
                case 4:
                    columnWidthsForTable2 = new float[]{1f, 1f, 1f, 1f, 1f};

                    //create PDF table with the given widths
                    secondTable = new PdfPTable(columnWidthsForTable2);
                    // set table width a percentage of the page width
                    secondTable.setWidthPercentage(100f);
                    secondTable.setHorizontalAlignment(Element.ALIGN_LEFT);

                    columns = ljusmatningslist.get(0);

                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 5, bf8, 0);

                    //Bredd på plf och bak plattform
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 1, bf6, 4);
                    insertCellSideBorders(secondTable, "Bredd på plf " + columns.getFirstValue() + " = plfk", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, columns.getFourthValue() + " = bak plattform", Element.ALIGN_LEFT, 2, bf6, 3);

                    //insert columns
                    insertCell(secondTable, "bredd | längd", Element.ALIGN_LEFT, 1, bf8, false);

                    insertCell(secondTable, columns.getFirstValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSecondValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getThirdValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFourthValue(), Element.ALIGN_CENTER, 1, bf8, false);

                    //insert rows
                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentRow = ljusmatningslist.get(i);
                        String widthValue = currentRow.getWidthValue();
                        if (!widthValue.equals("")) {
                            insertCell(secondTable, currentRow.getWidthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFirstValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSecondValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getThirdValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFourthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                        }
                    }

                    break;
                case 5:
                    columnWidthsForTable2 = new float[]{1f, 1f, 1f, 1f, 1f, 1f};

                    //create PDF table with the given widths
                    secondTable = new PdfPTable(columnWidthsForTable2);
                    // set table width a percentage of the page width
                    secondTable.setWidthPercentage(100f);
                    secondTable.setHorizontalAlignment(Element.ALIGN_LEFT);

                    columns = ljusmatningslist.get(0);

                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 6, bf8, 0);

                    //Bredd på plf och bak plattform
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 1, bf6, 4);
                    insertCellSideBorders(secondTable, "Bredd på plf " + columns.getFirstValue() + " = plfk", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, columns.getFifthValue() + " = bak plattform", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 1, bf6, 3);

                    //insert columns
                    insertCell(secondTable, "bredd | längd", Element.ALIGN_LEFT, 1, bf8, false);

                    insertCell(secondTable, columns.getFirstValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSecondValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getThirdValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFourthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFifthValue(), Element.ALIGN_CENTER, 1, bf8, false);


                    //insert rows
                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentRow = ljusmatningslist.get(i);
                        String widthValue = currentRow.getWidthValue();
                        if (!widthValue.equals("")) {
                            insertCell(secondTable, currentRow.getWidthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFirstValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSecondValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getThirdValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFourthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFifthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                        }
                    }

                    break;
                case 6:
                    columnWidthsForTable2 = new float[]{1f, 1f, 1f, 1f, 1f, 1f, 1f};

                    //create PDF table with the given widths
                    secondTable = new PdfPTable(columnWidthsForTable2);
                    // set table width a percentage of the page width
                    secondTable.setWidthPercentage(100f);
                    secondTable.setHorizontalAlignment(Element.ALIGN_LEFT);

                    columns = ljusmatningslist.get(0);

                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 7, bf8, 0);

                    //Bredd på plf och bak plattform
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 1, bf8, 4);
                    insertCellSideBorders(secondTable, "Bredd på plf " + columns.getFirstValue() + " = plfk", Element.ALIGN_LEFT, 2, bf8, 1);
                    insertCellSideBorders(secondTable, columns.getSixthValue() + " = bak plattform", Element.ALIGN_LEFT, 2, bf8, 1);
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 2, bf8, 3);

                    //insert columns
                    insertCell(secondTable, "bredd | längd", Element.ALIGN_LEFT, 1, bf8, false);

                    insertCell(secondTable, columns.getFirstValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSecondValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getThirdValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFourthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFifthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSixthValue(), Element.ALIGN_CENTER, 1, bf8, false);


                    //insert rows
                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentRow = ljusmatningslist.get(i);
                        String widthValue = currentRow.getWidthValue();
                        if (!widthValue.equals("")) {
                            insertCell(secondTable, currentRow.getWidthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFirstValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSecondValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getThirdValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFourthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFifthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSixthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                        }
                    }


                    break;
                case 7:
                    columnWidthsForTable2 = new float[]{1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};

                    //create PDF table with the given widths
                    secondTable = new PdfPTable(columnWidthsForTable2);
                    // set table width a percentage of the page width
                    secondTable.setWidthPercentage(100f);
                    secondTable.setHorizontalAlignment(Element.ALIGN_LEFT);

                    columns = ljusmatningslist.get(0);

                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 8, bf8, 0);

                    //Bredd på plf och bak plattform
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 1, bf6, 4);
                    insertCellSideBorders(secondTable, "Bredd på plf " + columns.getFirstValue() + " = plfk", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, columns.getSeventhValue() + " = bak plattform", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 3, bf6, 3);

                    //insert columns
                    insertCell(secondTable, "bredd | längd", Element.ALIGN_LEFT, 1, bf8, false);

                    insertCell(secondTable, columns.getFirstValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSecondValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getThirdValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFourthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFifthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSixthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSeventhValue(), Element.ALIGN_CENTER, 1, bf8, false);

                    //insert rows
                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentRow = ljusmatningslist.get(i);
                        String widthValue = currentRow.getWidthValue();
                        if (!widthValue.equals("")) {
                            insertCell(secondTable, currentRow.getWidthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFirstValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSecondValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getThirdValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFourthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFifthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSixthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSeventhValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                        }
                    }

                    break;
                case 8:
                    columnWidthsForTable2 = new float[]{1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};

                    //create PDF table with the given widths
                    secondTable = new PdfPTable(columnWidthsForTable2);
                    // set table width a percentage of the page width
                    secondTable.setWidthPercentage(100f);
                    secondTable.setHorizontalAlignment(Element.ALIGN_LEFT);

                    columns = ljusmatningslist.get(0);

                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 9, bf8, 0);

                    //Bredd på plf och bak plattform
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 1, bf6, 4);
                    insertCellSideBorders(secondTable, "Bredd på plf " + columns.getFirstValue() + " = plfk", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, columns.getEightValue() + " = bak plattform", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 4, bf6, 3);

                    //insert columns
                    insertCell(secondTable, "bredd | längd", Element.ALIGN_LEFT, 1, bf8, false);

                    insertCell(secondTable, columns.getFirstValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSecondValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getThirdValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFourthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFifthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSixthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSeventhValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getEightValue(), Element.ALIGN_CENTER, 1, bf8, false);

                    //insert rows
                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentRow = ljusmatningslist.get(i);
                        String widthValue = currentRow.getWidthValue();
                        if (!widthValue.equals("")) {
                            insertCell(secondTable, currentRow.getWidthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFirstValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSecondValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getThirdValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFourthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFifthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSixthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSeventhValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getEightValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                        }
                    }

                    break;
                case 9:
                    columnWidthsForTable2 = new float[]{1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};

                    //create PDF table with the given widths
                    secondTable = new PdfPTable(columnWidthsForTable2);
                    // set table width a percentage of the page width
                    secondTable.setWidthPercentage(100f);
                    secondTable.setHorizontalAlignment(Element.ALIGN_LEFT);

                    columns = ljusmatningslist.get(0);

                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 10, bf8, 0);

                    //Bredd på plf och bak plattform
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 1, bf6, 4);
                    insertCellSideBorders(secondTable, "Bredd på plf " + columns.getFirstValue() + " = plfk", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, columns.getNinthValue() + " = bak plattform", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 5, bf6, 3);

                    //insert columns
                    insertCell(secondTable, "bredd | längd", Element.ALIGN_LEFT, 1, bf8, false);

                    insertCell(secondTable, columns.getFirstValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSecondValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getThirdValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFourthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFifthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSixthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSeventhValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getEightValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getNinthValue(), Element.ALIGN_CENTER, 1, bf8, false);

                    //insert rows
                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentRow = ljusmatningslist.get(i);
                        String widthValue = currentRow.getWidthValue();
                        if (!widthValue.equals("")) {
                            insertCell(secondTable, currentRow.getWidthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFirstValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSecondValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getThirdValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFourthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFifthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSixthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSeventhValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getEightValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getNinthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                        }
                    }

                    break;
                case 10:
                    columnWidthsForTable2 = new float[]{1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};

                    //create PDF table with the given widths
                    secondTable = new PdfPTable(columnWidthsForTable2);
                    // set table width a percentage of the page width
                    secondTable.setWidthPercentage(100f);
                    secondTable.setHorizontalAlignment(Element.ALIGN_LEFT);

                    columns = ljusmatningslist.get(0);

                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 11, bf8, 0);

                    //Bredd på plf och bak plattform
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 1, bf6, 4);
                    insertCellSideBorders(secondTable, "Bredd på plf " + columns.getFirstValue() + " = plfk", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, columns.getTenthValue() + " = bak plattform", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 6, bf6, 3);

                    //insert columns
                    insertCell(secondTable, "bredd | längd", Element.ALIGN_LEFT, 1, bf8, false);

                    insertCell(secondTable, columns.getFirstValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSecondValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getThirdValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFourthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFifthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSixthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSeventhValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getEightValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getNinthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getTenthValue(), Element.ALIGN_CENTER, 1, bf8, false);

                    //insert rows
                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentRow = ljusmatningslist.get(i);
                        String widthValue = currentRow.getWidthValue();
                        if (!widthValue.equals("")) {
                            insertCell(secondTable, currentRow.getWidthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFirstValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSecondValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getThirdValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFourthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFifthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSixthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSeventhValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getEightValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getNinthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getTenthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                        }
                    }

                    break;
                case 11:
                    columnWidthsForTable2 = new float[]{1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};

                    //create PDF table with the given widths
                    secondTable = new PdfPTable(columnWidthsForTable2);
                    // set table width a percentage of the page width
                    secondTable.setWidthPercentage(100f);
                    secondTable.setHorizontalAlignment(Element.ALIGN_LEFT);

                    columns = ljusmatningslist.get(0);

                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 12, bf8, 0);

                    //Bredd på plf och bak plattform
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 1, bf6, 4);
                    insertCellSideBorders(secondTable, "Bredd på plf " + columns.getFirstValue() + " = plfk", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, columns.getEleventhValue() + " = bak plattform", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 7, bf6, 3);

                    //insert columns
                    insertCell(secondTable, "bredd | längd", Element.ALIGN_LEFT, 1, bf8, false);

                    insertCell(secondTable, columns.getFirstValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSecondValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getThirdValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFourthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFifthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSixthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSeventhValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getEightValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getNinthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getTenthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getEleventhValue(), Element.ALIGN_CENTER, 1, bf8, false);

                    //insert rows
                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentRow = ljusmatningslist.get(i);
                        String widthValue = currentRow.getWidthValue();
                        if (!widthValue.equals("")) {
                            insertCell(secondTable, currentRow.getWidthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFirstValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSecondValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getThirdValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFourthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFifthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSixthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSeventhValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getEightValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getNinthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getTenthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getEleventhValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                        }
                    }
                    break;
                case 12:
                    columnWidthsForTable2 = new float[]{1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};

                    //create PDF table with the given widths
                    secondTable = new PdfPTable(columnWidthsForTable2);
                    // set table width a percentage of the page width
                    secondTable.setWidthPercentage(100f);
                    secondTable.setHorizontalAlignment(Element.ALIGN_LEFT);

                    columns = ljusmatningslist.get(0);

                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 13, bf8, 0);

                    //Bredd på plf och bak plattform
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 1, bf6, 4);
                    insertCellSideBorders(secondTable, "Bredd på plf " + columns.getFirstValue() + " = plfk", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, columns.getTwelfthValue() + " = bak plattform", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 8, bf6, 3);

                    //insert columns
                    insertCell(secondTable, "bredd | längd", Element.ALIGN_LEFT, 1, bf8, false);

                    insertCell(secondTable, columns.getFirstValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSecondValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getThirdValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFourthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFifthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSixthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSeventhValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getEightValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getNinthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getTenthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getEleventhValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getTwelfthValue(), Element.ALIGN_CENTER, 1, bf8, false);

                    //insert rows
                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentRow = ljusmatningslist.get(i);
                        String widthValue = currentRow.getWidthValue();
                        if (!widthValue.equals("")) {
                            insertCell(secondTable, currentRow.getWidthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFirstValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSecondValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getThirdValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFourthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFifthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSixthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSeventhValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getEightValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getNinthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getTenthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getEleventhValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getTwelfthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                        }
                    }

                    break;
                case 13:
                    columnWidthsForTable2 = new float[]{1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};

                    //create PDF table with the given widths
                    secondTable = new PdfPTable(columnWidthsForTable2);
                    // set table width a percentage of the page width
                    secondTable.setWidthPercentage(100f);
                    secondTable.setHorizontalAlignment(Element.ALIGN_LEFT);

                    columns = ljusmatningslist.get(0);

                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 14, bf8, 0);

                    //Bredd på plf och bak plattform
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 1, bf6, 4);
                    insertCellSideBorders(secondTable, "Bredd på plf " + columns.getFirstValue() + " = plfk", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, columns.getThirteenthValue() + " = bak plattform", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 9, bf6, 3);

                    //insert columns
                    insertCell(secondTable, "bredd | längd", Element.ALIGN_LEFT, 1, bf8, false);

                    insertCell(secondTable, columns.getFirstValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSecondValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getThirdValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFourthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFifthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSixthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSeventhValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getEightValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getNinthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getTenthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getEleventhValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getTwelfthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getThirteenthValue(), Element.ALIGN_CENTER, 1, bf8, false);

                    //insert rows
                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentRow = ljusmatningslist.get(i);
                        String widthValue = currentRow.getWidthValue();
                        if (!widthValue.equals("")) {
                            insertCell(secondTable, currentRow.getWidthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFirstValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSecondValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getThirdValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFourthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFifthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSixthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSeventhValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getEightValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getNinthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getTenthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getEleventhValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getTwelfthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getThirteenthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                        }
                    }

                    break;
                case 14:
                    columnWidthsForTable2 = new float[]{1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};

                    //create PDF table with the given widths
                    secondTable = new PdfPTable(columnWidthsForTable2);
                    // set table width a percentage of the page width
                    secondTable.setWidthPercentage(100f);
                    secondTable.setHorizontalAlignment(Element.ALIGN_LEFT);

                    columns = ljusmatningslist.get(0);

                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 15, bf8, 0);

                    //Bredd på plf och bak plattform
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 1, bf6, 4);
                    insertCellSideBorders(secondTable, "Bredd på plf " + columns.getFirstValue() + " = plfk", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, columns.getFourteenthValue() + " = bak plattform", Element.ALIGN_LEFT, 2, bf6, 1);
                    insertCellSideBorders(secondTable, "", Element.ALIGN_LEFT, 10, bf6, 3);

                    //insert columns
                    insertCell(secondTable, "bredd | längd", Element.ALIGN_LEFT, 1, bf8, false);

                    insertCell(secondTable, columns.getFirstValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSecondValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getThirdValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFourthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFifthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSixthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getSeventhValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getEightValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getNinthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getTenthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getEleventhValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getTwelfthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getThirteenthValue(), Element.ALIGN_CENTER, 1, bf8, false);
                    insertCell(secondTable, columns.getFourteenthValue(), Element.ALIGN_CENTER, 1, bf8, false);

                    //insert rows
                    for (int i = 1; i < ljusmatningslist.size(); i++) {
                        currentRow = ljusmatningslist.get(i);
                        String widthValue = currentRow.getWidthValue();
                        if (!widthValue.equals("")) {
                            insertCell(secondTable, currentRow.getWidthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFirstValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSecondValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getThirdValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFourthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFifthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSixthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getSeventhValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getEightValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getNinthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getTenthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getEleventhValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getTwelfthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getThirteenthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                            insertCell(secondTable, currentRow.getFourteenthValue(), Element.ALIGN_RIGHT, 1, bf8, false);
                        }
                    }
                    break;
                default:
                    secondTable = new PdfPTable(columnWidthsForTable1);
                    secondTable.setWidthPercentage(100f);
                    columns = new Object();
                    currentRow = new Object();
            }

            secondTable.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph2.add(secondTable);
            // add the paragraph to the document
            document.add(paragraph2);

            //******************************************************

            //Add statistic values
            PdfPTable thirdTable;

            Paragraph paragraph3 = new Paragraph();

            float[] columnWidthsForTable3 = {1f, 1f, 1f, 1f, 1f, 1f};

            thirdTable = new PdfPTable(columnWidthsForTable3);
            thirdTable.setWidthPercentage(100f);
            thirdTable.setHorizontalAlignment(Element.ALIGN_LEFT);

            insertCellSideBorders(thirdTable, "", Element.ALIGN_CENTER, 6, bf8, 0);

            insertCellSideBorders(thirdTable, "", Element.ALIGN_CENTER, 1, bf8, 4);
            insertCellSideBorders(thirdTable, "Min", Element.ALIGN_LEFT, 1, bf8, 1);
            insertCellSideBorders(thirdTable, "Max", Element.ALIGN_LEFT, 1, bf8, 1);
            insertCellSideBorders(thirdTable, "Medel", Element.ALIGN_LEFT, 1, bf8, 1);
            insertCellSideBorders(thirdTable, "min/medel", Element.ALIGN_LEFT, 1, bf8, 1);
            insertCellSideBorders(thirdTable, "min/max", Element.ALIGN_LEFT, 1, bf8, 3);

            insertCellSideBorders(thirdTable, "Mätta värden:", Element.ALIGN_CENTER, 1, bf8, 4);
            insertCellSideBorders(thirdTable, String.valueOf(lowestValue), Element.ALIGN_LEFT, 1, bf8, 1);
            insertCellSideBorders(thirdTable, String.valueOf(highestValue), Element.ALIGN_LEFT, 1, bf8, 1);
            insertCellSideBorders(thirdTable, String.valueOf(averageValue), Element.ALIGN_LEFT, 1, bf8, 1);
            insertCellSideBorders(thirdTable, minDividedByAverageString, Element.ALIGN_LEFT, 1, bf8, 1);
            insertCellSideBorders(thirdTable, minDividedByMaxString, Element.ALIGN_LEFT, 1, bf8, 3);

            insertCellSideBorders(thirdTable, "", Element.ALIGN_CENTER, 6, bf8, 2);

            thirdTable.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph3.add(thirdTable);
            // add the paragraph to the document
            document.add(paragraph3);

            float[] columnWidths4 = {3f, 1f};

            //create PDF table with the given widths
            PdfPTable table4 = new PdfPTable(columnWidths4);
            // set table width a percentage of the page width
            table4.setWidthPercentage(100f);

            Paragraph paragraph4 = new Paragraph();

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 0);

            insertCellSideBorders(table4, "Övriga kommentarer:", Element.ALIGN_LEFT, 2, header, 0);

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 0);

            insertCellSideBorders(table4, String.valueOf(pdfValues.getOvrigaKommentarer()), Element.ALIGN_LEFT, 2, f10, 0);

            //Tom rad
            insertCellSideBorders(table4, "", Element.ALIGN_LEFT, 2, bf10, 2);

            table4.setHeaderRows(1);
            //add the PDF table to the paragraph
            paragraph4.add(table4);
            // add the paragraph to the document
            document.add(paragraph4);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            document.close();
        }
    }

    public void createPDFForPropertyList(ArrayList<PropertyListObjects> arrayPropertyList, PropertyListObjects propertyList, String kapITsdObjectNumber) {
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {

            //special font sizes
            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf10 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            Font bf10Bold = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
            Font bf8 = new Font(Font.FontFamily.TIMES_ROMAN, 8);

            BaseFont bf = BaseFont.createFont("assets/freesans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font f = new Font(bf, 10);

            File file = new File(dir, "Vanaheim.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(document, fOut);

            //document header attributes
            document.addAuthor("Vanaheim");
            document.addCreationDate();
            document.addProducer();
            document.addCreator("Vanaheim");
            document.setPageSize(PageSize.LETTER);

            //open the document
            document.open();

            //specify column widths
            float[] columnWidths = {2f, 5f, 2f, 2f};

            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setWidthPercentage(100f);


            Paragraph paragraph = new Paragraph();

            try {
                document.open();
                Drawable d = context.getResources().getDrawable(R.drawable.vanaheim_logga);
                BitmapDrawable bitDw = ((BitmapDrawable) d);
                Bitmap bmp = bitDw.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 30, stream);
                Image image = Image.getInstance(stream.toByteArray());

                int indentation = 0;
                float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                        - document.rightMargin() - indentation) / image.getWidth()) * 30;
                image.scalePercent(scaler);
                paragraph.add(image);
            } catch (Exception e) {
                e.printStackTrace();
            }


            //insert column headings
            insertCell(table, "Kap. i TSD", Element.ALIGN_CENTER, 1, bf10, true);
            insertCell(table, "Egenskaper", Element.ALIGN_CENTER, 1, bf10, true);
            insertCell(table, "Krav enligt TSD", Element.ALIGN_LEFT, 1, bf10, true);
            insertCell(table, "Status", Element.ALIGN_LEFT, 1, bf10, true);

            table.setHeaderRows(1);

            PropertyListObjects currentObjectInArrayList;
            String extraObjects;


            for (int i = 0; i < arrayPropertyList.size(); i++) {
                currentObjectInArrayList = arrayPropertyList.get(i);

                String titelName = currentObjectInArrayList.getTitel();

                insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()), Element.ALIGN_LEFT, 1, bf10Bold, true);
                insertCell(table, String.valueOf(currentObjectInArrayList.getTitel()), Element.ALIGN_LEFT, 1, bf10Bold, true);
                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);

                insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                switch (titelName) {
                    case "Parkeringsmöjligheter för funktionshindrade":
                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(1)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Parkeringsmöjligheter för funktionshindrade", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Skall finnas", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getParkeringsmojligheterForFunktionshindrade(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getParkeringsmojligheterForFunktionshindradeExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }

                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(1)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Placering av parkering för funktionshindrade", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Nära ingång", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getPlaceringAvParkeringForFunktionshindrade(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getPlaceringAvParkeringForFunktionshindradeExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        break;
                    case "Hinderfri gångväg":
                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(1)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Förekomst av hinderfri gångväg", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Skall finnas", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getForekomstAvHinderfriGangvag(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getForekomstAvHinderfriGangvagExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }

                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(2)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Längden på hinderfria gångvägar", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Kortast avstånd", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getLangdenPaHindergriaGangvagar(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getLangdenPaHindergriaGangvagarExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }

                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(3)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Reflekterande egenskaper (Mark- och Golvytorna)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Låg", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getReflekterandeEgenskaper(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getReflekterandeEgenskaperExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        break;
                    case "Horisontell förflyttning":
                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(1)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Hinderfri gångvägsbredd", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "\u2265 160 cm", Element.ALIGN_LEFT, 1, f, true);
                        insertCell(table, propertyList.getHinderfriGangvagsbredd(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getHinderfriGangvagsbreddExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }

                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(2)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Trösklar på hinderfri gångväg)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "\u2264 2,5 cm och kontrast", Element.ALIGN_LEFT, 1, f, true);
                        insertCell(table, propertyList.getTrosklarPaHinderfriGangvag(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getTrosklarPaHinderfriGangvagExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        break;
                    case "Vertikal förflyttning":
                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(1)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Trappstegsfri väg", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Ska finnas", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getTrappstegsfriVag(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getTrappstegsfriVagExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }

                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(2)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Bredd på trappor)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "\u2265 160 cm", Element.ALIGN_LEFT, 1, f, true);
                        insertCell(table, propertyList.getBreddPaTrappor(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getBreddPaTrapporExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(2)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Visuel markering på första och sista steget", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Ska finnas enl. NR", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getVisuelMarkeringPaForstaOchSistaSteget(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getVisuelMarkeringPaForstaOchSistaStegetExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }

                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(2)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Taktil varning före första uppgående trappsteg)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Ska finnas enl. NR", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getTaktilVarningForeForstaUppgaendeTrappsteg(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getTaktilVarningForeForstaUppgaendeTrappstegExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(3)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Ramper för personer med funktionsnedsättningar", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Ska finna enl. NR", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getRamperForPersonerMedFunktionsnedsattningar(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getRamperForPersonerMedFunktionsnedsattningarExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }

                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(4)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Ledstänger på båda sidor och två nivåer)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Ska finnas enl. NR", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getLedstangerPaBadaSidorOchTvaNivaer(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getLedstangerPaBadaSidorOchTvaNivaerExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(5)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Hissar", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Enl. EN 81-70:2003", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getHissar(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getHissarExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }

                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(6)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Rulltrappor och Rullramper", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Enl. EN 115-1:2008", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getRulltrapporOchRullramper(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getRulltrapporOchRullramperExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(7)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Plankorsningar", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Enligt krav i 4.2.1.15", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getPlankorsningar(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getPlankorsningarExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }

                        break;
                    case "Gångvägsmarkering":
                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(1)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Tydlig markering", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Markering enl. 4.2.1.10", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getTydligMarkering(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getTydligMarkeringExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(2)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Tillhandahållande av information till synskadade", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Taktila ledstråk", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getTillhandahållandeAvInformationTillSynskadade(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getTillhandahållandeAvInformationTillSynskadadeExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }

                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(3)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Fjärrstyrda ljudanordningar eller teleapplikationer", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Inovativa lösningar", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getFjarrstyrdaLjudanordningarEllerTeleapplikationer(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getFjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(4)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Taktil information på ledstänger eller väggar (145-165)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Skall finnas", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getTaktilInformationPaLedstangerEllerVaggar(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getTaktilInformationPaLedstangerEllerVaggarExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        break;
                    case "Golvytor":
                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(1)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Halksäkerhet", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Halksäkert", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getHalksakerhet(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getHalksakerhetExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }

                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(2)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Ojämnheter som överstiger 0,5 cm", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Ska inte finnas", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getOjamnheterSomOverstiger(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getOjamnheterSomOverstigerExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        break;
                    case "Markering av genomskinliga hinder":
                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(1)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Glasdörrar eller genomskinliga väggar längs gångvägar", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Ska markeras enl. NR", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getGlasdorrarEllerGenomskinligaVaggarLangsGangvagar(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getGlasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        break;
                    case "Toaletter och skötplatser":
                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(1)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Toalettutrymme anpassat för rullstolsanvändning", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Minst en ska finnas", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getToalettutrymmeAnpassatForRullstolsburnaPersoner(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getToalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(2)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Skötplatser tillgängliga för både könen", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Ska finnas", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getSkotplatserTillgangligaForBadeKonen(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getSkotplatserTillgangligaForBadeKonenExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        break;
                    case "Inredning och fristående enheter":
                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(1)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Kontrast mot bakgrund och avrundade kanter", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Kontrast och avrundade kanter", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getKontrastMotBakgrundOchAvrundandeKanter(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getKontrastMotBakgrundOchAvrundandeKanterExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(2)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Placering av inredning och enheter", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Upptäckbara", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getPlaceringAvInredningOchEnheter(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getPlaceringAvInredningOchEnheterExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(3)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Sittmöjlighet och plats för en rullstol", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Ska finnas enl. NR", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getSittmojligheterOchPlatsForEnRullstolsbundenPerson(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getSittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(4)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Väderskyddat område tillgängligt med rullstol", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Enl. NR", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getVaderskyddatOmrade(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getVaderskyddatOmradeExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        break;
                    case "Belysning":
                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(4)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Belysning på stationens externa område", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Tillräckligt ljus enl. NR", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getBelysningPaStationensExternaOmraden(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getBelysningPaStationensExternaOmradenExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(2)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Belysning längs hinderfria gångvägar", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Anpassad enl. NR", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getBelysningLangsHinderfriaGangvagar(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getBelysningLangsHinderfriaGangvagarExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(3)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Belysning på plattformar", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "EN 12464-2:2013 och\nEN 12464-1:2011", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getBelysningPaPlattformar(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getBelysningPaPlattformarExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(4)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Nödbelysning", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Tillräckligt enl. NR", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getNodbelysning(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getNodbelysningExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        break;
                    case "Visuell information":
                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(1)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Skyltar avstånd", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "\u2264 100 m", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getSkyltarAvstand(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getSkyltarAvstandExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(2)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Pictogram", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "\u2264 5 stycken", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getPictogram(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getPictogramExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(3)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Kontrast", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "kontrast", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getKontrast(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getKontrastExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(4)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Enhetlig", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "enhetlig", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getEnhetlig(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getEnhetligExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(5)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Synlig i alla belysningsförhållanden", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "synlig", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getSynligIAllaBelysningsforhallanden(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getSynligIAllaBelysningsforhallandenExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(6)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Skyltar enligt ISO 3864-1", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "ISO 3864-1", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getSkyltarEnligtISO(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getSkyltarEnligtISOExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        break;
                    case "Talad information Sidoplattform":
                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(1)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "STI-PA-nivå", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "\u2265 0,45", Element.ALIGN_LEFT, 1, f, true);
                        insertCell(table, propertyList.getStipaNiva(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getStipaNivaExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        break;
                    case "Plattformsbredd och plattformskant":
                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(1)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Riskområde på plattformskant mot spårsidan", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Ska finnas enl. NR", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getForekomstAvRiskomrade(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getForekomstAvHinderfriGangvagExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(3)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Plattforms minsta bredd", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "\u2265 160 cm + Riskområde", Element.ALIGN_LEFT, 1, f, true);
                        insertCell(table, propertyList.getPlattformsMinstaBredd(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getPlattformsMinstaBreddExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(4)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Avstånd mellan litet hinder (< 1 m) och riskområde", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "\u2265 80 cm", Element.ALIGN_LEFT, 1, f, true);
                        insertCell(table, propertyList.getAvstandMellanLitetHinder(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getAvstandMellanLitetHinderExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(4)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Avstånd mellan stort hinder (1-10 m) och riskområde", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "\u2265 120 mm", Element.ALIGN_LEFT, 1, f, true);
                        insertCell(table, propertyList.getAvstandMellanStortHinder(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getAvstandMellanStortHinderExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(6)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Markering av riskområdets gräns", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Visuell och taktil mark", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getMarkeringAvRiskomradetsGrans(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getMarkeringAvRiskomradetsGransExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(7)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Bredden på varningslinje och halksäkerhet och färg", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "\u2264 10 cm och kontrast", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getBreddenPaVarningslinjeOchHalksakerhetOchFarg(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getBreddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(9)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Material på plattformskanten", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Kontrast mot gapet", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getMaterialPaPlattformskanten(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getMaterialPaPlattformskantenExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        break;
                    case "Plattformens slut":
                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(1)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Markering av plattformens slut", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Taktil och visuell/barriär", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getMarkeringAvPlattformensSlut(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getMarkeringAvPlattformensSlutExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        break;
                    case "Spårkorsning för passagerare påväg till plattformar":
                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(1)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Används som en del av trappstegsri gångväg", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Enl. NR", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getAnvandsSomEnDelAvTrappstegfriGangvag(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getAnvandsSomEnDelAvTrappstegfriGangvagExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(2)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Bredd på gångväg (längden \u2264 10 m eller \u003E 10 m) ", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "120 cm eller\n160 cm", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getBreddPaGangvagg(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getBreddPaGangvaggExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(2)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Lutning", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Måttlig enl. NR", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getLutning(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getLutningExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(2)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Fri passage för minsta hjulet på en rullstol", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Enl. tillägg M.", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getFriPassageForMinstaHjuletPaEnRullstol(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getFriPassageForMinstaHjuletPaEnRullstolExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(2)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Fri passage om säkerhetschikaner förekommer", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "90-120 cm", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getFriPassageOmSakerhetschikanerForekommer(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getFriPassageOmSakerhetschikanerForekommerExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(3)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Markering av gångbaneytan", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "taktil och visuell", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getMarkeringAvGangbaneytan(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getMarkeringAvGangbaneytanExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);

                        insertCell(table, String.valueOf(currentObjectInArrayList.getKapITSD()) + "(3)", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Säker passage", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, "Övervakning eller lämplig utrustning", Element.ALIGN_LEFT, 1, bf10, true);
                        insertCell(table, propertyList.getSakerPassage(), Element.ALIGN_LEFT, 1, bf10, true);

                        extraObjects = propertyList.getSakerPassageExtraObjects();

                        if (extraObjects != null && !extraObjects.isEmpty() && !extraObjects.equals("null")) {


                            ArrayList aList = new ArrayList(Arrays.asList(extraObjects.split("\n")));
                            String newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString = (String) aList.get(j);
                                String[] nameAndStatus;
                                nameAndStatus = newString.split("     ");

                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[0], Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, "", Element.ALIGN_LEFT, 1, bf10, true);
                                insertCell(table, nameAndStatus[1], Element.ALIGN_LEFT, 1, bf10, true);
                            }
                        }
                        break;
                }

                if (i < arrayPropertyList.size() - 1)
                    insertCell(table, "", Element.ALIGN_LEFT, 4, bf10, true);
            }

            //add the PDF table to the paragraph
            paragraph.add(table);
            // add the paragraph to the document
            document.add(paragraph);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            document.close();
        }
    }

    public void deletePDFFile() {

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, "Vanaheim.pdf");
        boolean status = file.delete();
        Log.w("Delete Check", "File deleted: " + dir + "/Vanaheim.pdf " + status);
    }

    public void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));

        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);

        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }

        if (text.contains("Spårkomponenter:")) {
            cell.setPadding(5);
            cell.setMinimumHeight(70);
        }

        table.addCell(cell);

    }

    public void insertCell(PdfPTable table, String text, int align, int colspan, Font font, boolean transparentBorders) {

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));

        if (transparentBorders == true)
            cell.setBorder(Rectangle.NO_BORDER);
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);

        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }

        table.addCell(cell);

    }

    public void insertCellSideBorders(PdfPTable table, String text, int align, int colspan, Font font, int value) {

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));


        switch (value) {
            case 0: //Top and bottom hidden
                cell.setBorderWidthBottom(0);
                cell.setBorderWidthTop(0);
                break;
            case 1: // Alll hidden
                cell.setBorderWidthBottom(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthRight(0);
                break;
            case 2: //Top side hidden
                cell.setBorderWidthTop(0);
                break;
            case 3: //right side showing
                cell.setBorderWidthBottom(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthLeft(0);
                break;
            case 4: //Left side showing
                cell.setBorderWidthBottom(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                break;
        }

        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);

        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }

        table.addCell(cell);

    }

    public void sendPDF() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        File root = Environment.getExternalStorageDirectory();
        String pathToMyAttachedFile = "Vanaheim/Vanaheim.pdf";
        File file = new File(root, pathToMyAttachedFile);
        if (!file.exists() || !file.canRead()) {
            return;
        }
        //OBS Läs: https://stackoverflow.com/questions/48117511/exposed-beyond-app-through-clipdata-item-geturi?rq=1
        Uri uri = FileProvider.getUriForFile(
                context,
                "se.vanaheim.vanaheim.provider", //(use your app signature + ".provider" )
                file);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
    }
}

//************ Källor *******
// För att få in alla utf-tecken  https://itextpdf.com/en/resources/books/best-itext-questions-stack-overflow/why-isnt-rupee-symbol-showing