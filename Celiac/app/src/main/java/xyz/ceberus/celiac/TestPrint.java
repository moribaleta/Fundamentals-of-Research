package xyz.ceberus.celiac;

/**
 * Created by Eli on 1/11/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.util.Log;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class TestPrint extends AsyncTask {


    public static final float COLUMN_WIDTH = 254;
    public static final Rectangle[] COLUMNS = {
            new Rectangle(36, 36, 36 + COLUMN_WIDTH, 806),
            new Rectangle(305, 36, 305 + COLUMN_WIDTH, 806)
    };
    private static final String LOG_TAG = "Printing";

    private File pdfFile;
    ProgressDialog dialog;
    private Context context;
    public TestPrint(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        this.dialog.setMessage("Saving");
        this.dialog.show();
        super.onPreExecute();
    }

    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font){

        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        if(text.trim().equalsIgnoreCase("")){
            cell.setMinimumHeight(10f);
        }
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);

    }

    public File getDataStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }

    @Override
    protected String doInBackground(Object[] objects) {
        UserData userData = (UserData) objects[0];


        Font font = new Font(Font.FontFamily.HELVETICA, 8);
        Font font1 = new Font(Font.FontFamily.HELVETICA, 8);
        Font fontbold = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

        //File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Pushkart");
        File docsFolder = getDataStorageDir("Celiac");
        if (!docsFolder.exists()) {
            Log.e("printing","folder not exist");
            docsFolder.mkdirs();
        }


        try{
            //pdfFile = new File(docsFolder.getAbsolutePath(),"Order-"+usedPL.getPOno()+".pdf");
            //String strFileName = "";
            String strUserName = userData.getStrName().replace(" ","_");
            String strDate = userData.getDate().replace(" ","_");
            pdfFile = new File( context.getFilesDir(), "Test-"+strUserName+strDate+".pdf");
            OutputStream output = new FileOutputStream(pdfFile);
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, output);
            document.open();

            PdfContentByte canvas = writer.getDirectContent();
            ColumnText ct = new ColumnText(canvas);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.ver2);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image i = Image.getInstance(stream.toByteArray());
            i.scaleAbsolute(50, 50);
            Chunk logo = new Chunk(i,0,-24);
            Paragraph logo1 = new Paragraph(logo);
            logo1.setAlignment(Element.ALIGN_CENTER);

            Paragraph headerdtls = new Paragraph(logo1);
            headerdtls.add(new Phrase("\n\n\n\n" +
                    "Celiac Disease Diagnosing System\n\n"+
                    "Computer Science Department,CCIS,\nPolytechnic University of the Philippines,\n"+
                    "Anonas, Santa Mesa, Maynila, Kalakhang Maynila\n"+
                    "", font1));
            headerdtls.setLeading(0,1.2f);
            headerdtls.setAlignment(Element.ALIGN_CENTER);

           /* Paragraph inv = new Paragraph(new Phrase(
                    "INVOICE #:"+usedPL.getPOno(),font
            ));
            inv.setAlignment(Element.ALIGN_LEFT);
            Paragraph name = new Paragraph(new Phrase(
                    "NAME:"+usedPL.getCustomer(),font
            ));
            name.setAlignment(Element.ALIGN_LEFT);
            Paragraph address = new Paragraph(new Phrase(
                    "ADDRESS:"+usedPL.getAddress(),font
            ));
            address.setAlignment(Element.ALIGN_LEFT);
            /*Paragraph mobile = new Paragraph(new Phrase(
                    "mobile:"+usedPL.getMobile(),font
            ));
            mobile.setAlignment(Element.ALIGN_LEFT);*/
            /*Paragraph schedule = new Paragraph(new Phrase(
                    "schedule:"+usedPL.getDeliverySchedule(),font
            ));
            schedule.setAlignment(Element.ALIGN_LEFT);*/
            String strUserDetails = "\n\nNAME: "+userData.getStrName()
                    +"\n"+"AGE: "+userData.getIntAge();
            float[] columnWidths_1 = {6f, 2f, 2f};
            PdfPTable tableUser = new PdfPTable(columnWidths_1);
            tableUser.setWidthPercentage(100f);
            tableUser.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            insertCell(tableUser,strUserDetails,Element.ALIGN_LEFT,1,font);
            insertCell(tableUser, "", Element.ALIGN_RIGHT, 1,font);
            insertCell(tableUser, "", Element.ALIGN_RIGHT, 1, font);
            //insertCell(table, "TIME", Element.ALIGN_RIGHT, 1, font);
            //insertCell(table, "DATE: "+formattedDate, Element.ALIGN_RIGHT, 1,font);
            //insertCell(table, "TIME: "+formattedTime, Element.ALIGN_RIGHT, 1, font);


            float[] columnWidths2 = {7f, 3f};
            PdfPTable tableheader = new PdfPTable(columnWidths2);
            tableheader.setWidthPercentage(100f);
            insertCell(tableheader, "Question.", Element.ALIGN_LEFT, 1, font);
            insertCell(tableheader, "Answer", Element.ALIGN_LEFT, 1,font);

            PdfPTable table2 = new PdfPTable(columnWidths2);
            table2.setWidthPercentage(100f);
            /*insertCell(table2, "Qty.", Element.ALIGN_LEFT, 1, font);
            insertCell(table2, "Item", Element.ALIGN_LEFT, 1,font);
            insertCell(table2, "Amount", Element.ALIGN_LEFT, 1, font);
*/
            int total = 0;
            String strData = userData.getDataSet();
            strData = strData.replace("|",",");
            //int arrIntData[] = new int[10];
            ArrayList<Integer> arrIntData = new ArrayList<>();
            for (String strDataItem : strData.split(",")) {
                int intData = Integer.parseInt(strDataItem);
                Log.e("DATAVIEW",""+strDataItem+": "+intData);
                arrIntData.add(intData);
            }

            FileStorage fileStorage;
            ArrayList<Question> arrQuestion = new ArrayList<>();
            try {
                fileStorage = new FileStorage(context);
                arrQuestion = fileStorage.GetQuestions();
                //ArrayList<String> arrListData = new ArrayList<>();
                int intCount = 0;

                for (Question question : arrQuestion) {
                    Log.e("DATAVIEW", question.getStrQuestion());
                    String arrAnswer[] = question.getArrAnswer();
                    int intIndex = arrIntData.get(intCount);

                    /*txtCount.setText((intCount + 1) + ".  ");
                    txtQuestion.setText(question.getStrQuestion());
                    txtAnswer.setText(arrAnswer[intIndex-1]);*/

                    insertCell(table2, (intCount + 1) + ".  "+question.getStrQuestion(), Element.ALIGN_LEFT, 1, font);
                    insertCell(table2, arrAnswer[intIndex-1], Element.ALIGN_LEFT, 1,font);
                    intCount++;
                }
                //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrListData);
                //listViewData.setAdapter(arrayAdapter);


            } catch (Exception e) {
                e.printStackTrace();
            }

            insertCell(table2, "", Element.ALIGN_LEFT, 3, font);
            String strResult = "RESULT: Negative";
            String strRecommendation = "";
            //String strResult = "The result is Negative, Congratulations you are not diagnose of Celiac Disease";
            Log.e("DATAVIEW","user result: "+userData.getIntResult());
            if (userData.getIntResult() != -1) {
                CeliacType celiacType = new CeliacType();
                int intType = celiacType.TestResult(userData.getDataSet());
                strResult = "RESULT: Positive"+"\nTYPE: " + intType;
                strRecommendation = "\nRECOMMENDATION: we advice you to go to the Doctor " +
                        "(Gastroenterologist) and show this result to confirm if you have Celiac Disease";
            }

            float[] columnWidths_3 = {6f, 2f, 2f};
            PdfPTable tableResult = new PdfPTable(columnWidths_1);
            tableUser.setWidthPercentage(100f);
            tableUser.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            insertCell(tableResult,strResult+strRecommendation,Element.ALIGN_LEFT,1,font);
            insertCell(tableResult, "", Element.ALIGN_RIGHT, 1,font);
            insertCell(tableResult, "", Element.ALIGN_RIGHT, 1, font);

            ct.addElement(headerdtls);
            /*ct.addElement(inv);
            ct.addElement(name);
            ct.addElement(address);*/
            ct.addElement(tableUser);
            ct.addElement(tableheader);
            ct.addElement(table2);
            ct.addElement(tableResult);
            int c = 0;
            int status = ColumnText.START_COLUMN;
            while (ColumnText.hasMoreText(status)) {
                ct.setSimpleColumn(COLUMNS[c]);
                status = ct.go();
                if (++c == 2) {
                    document.newPage();
                    c = 0;
                }
            }

            document.close();
            print(userData, pdfFile);
            return "Success";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error in creating folder to save PDF.";
            //Toast.makeText(SignatureP.this, "PDF - Error in creating folder to save PDF.", Toast.LENGTH_SHORT).show();
        }catch (DocumentException e) {
            e.printStackTrace();
            return "Error in creating PDF file.";
            //Toast.makeText(SignatureP.this,"PDF - Error in creating PDF file.",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            return  "problems in PDF saving.";
            //Toast.makeText(SignatureP.this,"PDF - We're experiencing some problems in PDF saving. Please restart the app.",Toast.LENGTH_SHORT).show();
        }
    }

    private void print(final UserData userData, final File toPrint) {
        PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);
        final String strUserName = userData.getStrName().replace(" ","_");
        final String strDate = userData.getDate().replace(" ","_");
        PrintDocumentAdapter PDA  = new PrintDocumentAdapter() {
            @Override
            public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
                if (cancellationSignal.isCanceled()) {
                    callback.onLayoutCancelled();
                    return;
                }

                PrintDocumentInfo pdi = new PrintDocumentInfo.Builder("Document "+strUserName+strDate).setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();
                callback.onLayoutFinished(pdi, true);
            }

            @Override
            public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
                InputStream input = null;
                OutputStream output = null;

                try {
                    input = new FileInputStream(toPrint);
                    output = new FileOutputStream(destination.getFileDescriptor());

                    byte[] buf = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = input.read(buf)) > 0) {
                        output.write(buf, 0, bytesRead);
                    }

                    callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});

                } catch (FileNotFoundException ee) {
                    //Catch exception
                } catch (Exception e) {
                    //Catch exception
                } finally {
                    try {
                        input.close();
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        printManager.print("Document "+strUserName+strDate, PDA, null);
    }

    @Override
    protected void onPostExecute(Object object) {
        dialog.dismiss();
       /* new android.app.AlertDialog.Builder(context)
                .setMessage(object.toString()+"\nPDF File for printing can be found at "+pdfFile.getAbsolutePath().toString())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .show();*/
        Log.e("Print Document",object.toString()+"\nPDF File for printing can be found at "+pdfFile.getAbsolutePath().toString());
    }
}