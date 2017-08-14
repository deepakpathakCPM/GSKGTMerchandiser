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
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.cpm.gskgtmerchandiser.Database.GSKGTMerDB;
import com.cpm.gskgtmerchandiser.GetterSetter.CoverageBean;
import com.cpm.gskgtmerchandiser.GetterSetter.StoreBean;
import com.cpm.gskgtmerchandiser.R;
import com.cpm.gskgtmerchandiser.constant.CommonString;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class UploadActivity extends AppCompatActivity {

    GSKGTMerDB db;
    ArrayList<CoverageBean> coverageList;
    String date, userId, app_version;
    StoreBean storeData;
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
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        date = preferences.getString(CommonString.KEY_DATE, null);
        userId = preferences.getString(CommonString.KEY_USERNAME, null);
        app_version = preferences.getString(CommonString.KEY_VERSION, null);

        db = new GSKGTMerDB(this);
        db.open();

        Path = CommonString.FILE_PATH;

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
            dialog.setTitle(getString(R.string.uploaddata));
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

                if(result.toString().equalsIgnoreCase("1"))
                {
                    throw new MalformedURLException();
                }
                else   if(result.toString().equalsIgnoreCase("2"))
                {
                    throw new SocketTimeoutException();
                }
                else   if(result.toString().equalsIgnoreCase("3"))
                {
                    throw new InterruptedIOException();
                }
                else   if(result.toString().equalsIgnoreCase("4"))
                {
                    throw new IOException();
                }
                else   if(result.toString().equalsIgnoreCase("5"))
                {
                    throw new Exception();
                }

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
            if (result.contains(CommonString.KEY_SUCCESS)) {
                showAlert(getString(R.string.menu_upload_data));
            } else {
                showAlert(getString(R.string.error) + result.toString());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle(getString(R.string.title_activity_upload));
    }

    public void showAlert(String str) {

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
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
