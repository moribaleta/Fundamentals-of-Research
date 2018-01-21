package xyz.ceberus.celiac;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Eli on 11/3/2017.
 */

public class DictionaryHelper extends SQLiteOpenHelper {

    private static final String TAG = "Cdiag";
    private SQLiteDatabase dictionary;
    private Context context;
    private static String PATH;
    private static String NAME = "datastorage.db";

    public DictionaryHelper(Context context) {
        super(context, NAME, null, 1);
        this.context = context;
        PATH = context.getFilesDir().getPath();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createDatabase() {
        boolean exists = checkDatabase();

        Log.v(TAG, exists ? "Database is valid" : "Database is not found or invalid");
        if (!exists) {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getWritableDatabase();

            try {
                copyDatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDatabase() {
        SQLiteDatabase checkDB = null;

        try {
            String path = PATH + "/" + NAME;
            Log.v(TAG, "Checking " + NAME);
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            Log.e(TAG, "checkDatabase", e);
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null;
    }

    /*private void copyDatabase() throws IOException {
        //Open your local db as the input stream
        Log.e(TAG, "Copying Database");
        InputStream myInput = context.getAssets().open(NAME);

        // Path to the just created empty db
        String outFile = PATH + NAME;

        //Open the empty db as the output stream
        OutputStream os = new FileOutputStream(outFile);

        //transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            os.write(buffer, 0, length);
        }

        //Close the streams
        os.flush();
        os.close();
        myInput.close();
    }*/

    private void copyDatabase() throws IOException {
        InputStream is = context.getAssets().open(NAME);
        Log.e(TAG, "Copying Database");
        // Log.v("Tag assets",is.toString());
        String outFileName = PATH + "/" + NAME;
        OutputStream out = new FileOutputStream(outFileName);
        Log.v("Tag assets", out.toString());
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            // Log.v("Tag",out.toString());
            out.write(buffer, 0, length);
            // Log.v("Tag",out.toString());
        }
        Log.e(TAG, "Database created");
        is.close();
        out.flush();
        out.close();

    }

    public void open() throws SQLException {

        //Open the database
        String myPath = PATH + "/" + NAME;
        Log.v(TAG, "Opening " + NAME);
        dictionary = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {
        Log.v(TAG, "Closing database");
        if (dictionary != null)
            dictionary.close();
        super.close();
    }

    public ArrayList<Question> getQuestion() {
        ArrayList<Question> arrQuestion = new ArrayList<>();
        String sql = "SELECT * FROM SURVEY_TBL";

        Log.v(TAG, sql);
        Cursor c = dictionary.rawQuery(sql, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            String strQuestion = c.getString(c.getColumnIndex("QUESTION"));
            String strAnswer = c.getString(c.getColumnIndex("ANSWER"));
            String strInfo = c.getString(c.getColumnIndex("INFO"));
            Question question = new Question(strQuestion, strAnswer,strInfo);
            arrQuestion.add(question);
            c.moveToNext();
        }
        c.close();
        return arrQuestion;
    }

    public void createUser(UserData userData) {
        String sql = "insert into USER_TBL(NAME,AGE)values('" + userData.getStrName() + "','" + userData.getIntAge() + "')";
        dictionary.execSQL(sql);
    }

    public void insertUserDataHistory(UserData userData) {

        String sql = "insert into HISTORY_TBL(USERID,DATASET,RESULT,DATE)" +
                "values(" + userData.getStrUserId() + ",'" + userData.getDataSet() + "'," + userData.getIntResult() + ",'" + userData.getDate() + "')";
        dictionary.execSQL(sql);
    }

    public ArrayList<UserData> getUserdata() {
        String sql = "SELECT * FROM USER_TBL";
        ArrayList<UserData> arrUserData = new ArrayList<>();
        Log.v(TAG, sql);
        Cursor c = dictionary.rawQuery(sql, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            String strId = c.getString(c.getColumnIndex("ID"));
            String strName = c.getString(c.getColumnIndex("NAME"));
            int intAge = c.getInt(c.getColumnIndex("AGE"));
            UserData userData = new UserData(strId, strName, intAge);
            arrUserData.add(userData);
            c.moveToNext();
        }
        c.close();
        return arrUserData;
    }


    public ArrayList<UserData> getUserHistory(UserData userData) {
        ArrayList<UserData> arrayList = new ArrayList<>();
        String sql = "SELECT * FROM HISTORY_TBL WHERE USERID = '" + userData.getStrUserId() + "'";
        Log.v(TAG, sql);
        Cursor c = dictionary.rawQuery(sql, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            // do something
            String strDataset = c.getString(c.getColumnIndex("DATASET"));
            int intResult = c.getInt(c.getColumnIndex("RESULT"));
            String strDate = c.getString(c.getColumnIndex("DATE"));
            UserData userDataItem = new UserData(userData.strUserId, userData.strName, userData.intAge);
            userDataItem.setDate(strDate);
            userDataItem.setStrDataset(strDataset);
            userDataItem.setIntResult(intResult);
            userDataItem.showDataFull();
            arrayList.add(userDataItem);
            c.moveToNext();
        }
        c.close();
        return arrayList;
    }

    public ArrayList<TrainingData> getTrainingData() {
        String sql = "SELECT * FROM TRAINING_TBL";
        ArrayList<TrainingData> arrTrainingData = new ArrayList<>();
        Log.v(TAG, sql);
        Cursor c = dictionary.rawQuery(sql, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            // do something
            String strDataset = c.getString(c.getColumnIndex("DATASET"));
            int intResult = c.getInt(c.getColumnIndex("RESULT"));
            TrainingData trainingData = new TrainingData(strDataset, intResult);
            arrTrainingData.add(trainingData);
            c.moveToNext();
        }
        c.close();
        return arrTrainingData;
    }

    public UserData getUserDataById(String strId) {
        String sql = "select * from USER_TBL where ID='" + strId + "'";
        Log.v(TAG, sql);
        Cursor c = dictionary.rawQuery(sql, null);
        c.moveToFirst();
        String strName = c.getString(c.getColumnIndex("NAME"));
        int intAge = c.getInt(c.getColumnIndex("AGE"));
        UserData userData = new UserData(strId, strName, intAge);
        return userData;
    }

    public UserData getData(UserData userData) {
        UserData finalUserData = userData;
        try {
            String sql = "SELECT * FROM HISTORY_TBL where USERID = " + userData.strUserId + " ORDER BY ID DESC";
            Log.v(TAG, sql);
            Cursor c = dictionary.rawQuery(sql, null);
            c.moveToFirst();
            String strDataset = c.getString(c.getColumnIndex("DATASET"));
            int intResult = c.getInt(c.getColumnIndex("RESULT"));
            String strDate = c.getString(c.getColumnIndex("DATE"));
            finalUserData.setDate(strDate);
            finalUserData.setStrDataset(strDataset);
            finalUserData.setIntResult(intResult);
            c.close();
            return finalUserData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userData;
    }
}
