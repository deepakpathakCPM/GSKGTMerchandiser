package com.cpm.gskgtmerchandiser.upload;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cpm.gskgtmerchandiser.Database.GSKGTMerDB;
import com.cpm.gskgtmerchandiser.R;
import com.cpm.gskgtmerchandiser.constant.CommonString;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

public class PreviousDataUploadActivity extends AppCompatActivity {

    GSKGTMerDB db;
    String date, userId, app_version;
    String datacheck = "";
    String[] words;
    String validity;
    int mid;
    String errormsg = "", Path;
    Data data;

    private Dialog dialog;
    private ProgressBar pb;
    private TextView percentage, message;
    private SharedPreferences preferences;
    private int factor, k = 0;


    Object result = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);


        date = preferences.getString(CommonString.KEY_DATE, null);
        userId = preferences.getString(CommonString.KEY_USERNAME, null);
        app_version = preferences.getString(CommonString.KEY_VERSION, null);

        db = new GSKGTMerDB(this);
        db.open();

        Path = CommonString.FILE_PATH;

        //start upload
        new UploadTask(this).execute();
    }

    class Data {
        int value;
        String name;
    }

    private class UploadTask extends AsyncTask<Void, Data, String> {
        private Context context;

        UploadTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom);
            dialog.setTitle("Uploading Data");
            dialog.setCancelable(false);
            dialog.show();

            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);
        }

        @Override
        protected void onProgressUpdate(Data... values) {
            // TODO Auto-generated method stub

            pb.setProgress(values[0].value);
            percentage.setText(values[0].value + "%");
            message.setText(values[0].name);
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                data = new Data();

                for (int i = 0; i < 1; i++) {

                    //if (storeData.getSTORE_ID() != null)
                    if (true) {

                    /*    if (storeData.getCHECKOUT_STATUS().equals(CommonString.KEY_Y) ||
                                storeData.getCHECKOUT_STATUS().equals(CommonString.KEY_L) ||
                                !storeData.getUPLOAD_STATUS().equals(CommonString.KEY_U)) */
                        if (true) {

                            result = CommonString.KEY_SUCCESS;
                            if (result.toString().equalsIgnoreCase("1")) {
                                throw new MalformedURLException();
                            } else if (result.toString().equalsIgnoreCase("2")) {
                                throw new SocketTimeoutException();
                            } else if (result.toString().equalsIgnoreCase("3")) {
                                throw new InterruptedIOException();
                            } else if (result.toString().equalsIgnoreCase("4")) {
                                throw new IOException();
                            } else if (result.toString().equalsIgnoreCase("5")) {
                                throw new Exception();
                            }

                            if (validity.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                //  db.updateCoverageStatus(coverageList.get(i).getStoreId(), CommonString.KEY_P);
                                // db.updateStoreStatusOnLeave(coverageList.get(i).getStoreId(), date, CommonString.KEY_P);
                            } else {
                                continue;
                                //return CommonString.METHOD_UPLOAD_COVERAGE;
                            }
                        }
                    }
                }
                return result.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            dialog.dismiss();
            db.deleteAllTables();
            if (result.contains(CommonString.KEY_SUCCESS)) {
                showAlert(getString(R.string.menu_upload_data));
            } else {
                showAlert(getString(R.string.error) + result.toString());
            }
        }
    }


    public void showAlert(String str) {

        AlertDialog.Builder builder = new AlertDialog.Builder(PreviousDataUploadActivity.this);
        builder.setTitle("Parinaam");
        builder.setMessage(str).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
