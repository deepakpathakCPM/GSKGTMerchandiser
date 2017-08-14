package com.cpm.gskgtmerchandiser.dailyentry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cpm.gskgtmerchandiser.Database.GSKGTMerDB;
import com.cpm.gskgtmerchandiser.GetterSetter.CoverageBean;
import com.cpm.gskgtmerchandiser.GetterSetter.FailureGetterSetter;
import com.cpm.gskgtmerchandiser.GetterSetter.StoreBean;
import com.cpm.gskgtmerchandiser.R;
import com.cpm.gskgtmerchandiser.constant.CommonString;
import com.cpm.gskgtmerchandiser.gsonGetterSetter.JourneyPlan;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class NonWorkingReason extends AppCompatActivity implements
        OnItemSelectedListener, OnClickListener {

    ArrayList<com.cpm.gskgtmerchandiser.gsonGetterSetter.NonWorkingReason> reasondata = new ArrayList<>();
    private Spinner reasonspinner;
    private GSKGTMerDB database;
    String reasonname, reasonid, entry_allow, image, entry, reason_reamrk, intime, image_allow;
    Button save;
    boolean ResultFlag = true;
    private ArrayAdapter<CharSequence> reason_adapter;
    protected String _path, str, strflag;
    protected String _pathforcheck = "";
    private ArrayList<StoreBean> storedata = new ArrayList<StoreBean>();
    private String image1 = "";
    private ArrayList<CoverageBean> cdata = new ArrayList<CoverageBean>();
    protected boolean _taken;
    protected static final String PHOTO_TAKEN = "photo_taken";
    private SharedPreferences preferences;
    String _UserId, visit_date, store_id, username;
    protected boolean status = true;
    EditText text;
    AlertDialog alert;
    ImageButton camera;
    RelativeLayout reason_lay, rel_cam;
    String gallery_package = "";
    Uri outputFileUri;
    boolean leave_flag = false;
    ArrayList<CoverageBean> coverage = new ArrayList<CoverageBean>();
    ArrayList<JourneyPlan> storelist = new ArrayList<JourneyPlan>();
    ArrayList<JourneyPlan> jcp;
    private Dialog dialog;
    private TextView percentage, message;
    private ProgressBar pb;
    private FailureGetterSetter failureGetterSetter = null;
    boolean nonflag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nonworkingmainlayout);

        reasonspinner = (Spinner) findViewById(R.id.spinner2);
        camera = (ImageButton) findViewById(R.id.imgcam);
        save = (Button) findViewById(R.id.save);
        text = (EditText) findViewById(R.id.reasontxt);
        reason_lay = (RelativeLayout) findViewById(R.id.layout_reason);
        rel_cam = (RelativeLayout) findViewById(R.id.relimgcam);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        _UserId = preferences.getString(CommonString.KEY_USERNAME, "");
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        username = preferences.getString(CommonString.KEY_USERNAME, null);
        store_id = getIntent().getStringExtra(CommonString.KEY_STORE_ID);
        //store_id = preferences.getString(CommonString.KEY_STORE_ID, "");

        database = new GSKGTMerDB(this);
        database.open();
        str = CommonString.FILE_PATH;

        storelist = database.getStoreData(visit_date);

        coverage = database.getCoverageData(visit_date);

        for (int i = 0; i < storelist.size(); i++) {
            if (storelist.get(i).getUploadStatus().equalsIgnoreCase("U") || storelist.get(i).getUploadStatus().equalsIgnoreCase("D") || storelist.get(i).getUploadStatus().equalsIgnoreCase("C")) {

                nonflag = true;
                break;
            } else {
                nonflag = false;
            }

        }

        if (nonflag) {
            reasondata = database.getNonWorkingEntryAllowData();

        } else {
            reasondata = database.getNonWorkingData();

        }


        intime = getCurrentTime();

        camera.setOnClickListener(this);
        save.setOnClickListener(this);

        reason_adapter = new ArrayAdapter<CharSequence>(this,
                android.R.layout.simple_spinner_item);

        reason_adapter.add(getResources().getString(R.string.select_reason));

        for (int i = 0; i < reasondata.size(); i++) {
            reason_adapter.add(reasondata.get(i).getReason());
        }

        reasonspinner.setAdapter(reason_adapter);

        reason_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        reasonspinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        finish();

        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                               long arg3) {
        // TODO Auto-generated method stub

        switch (arg0.getId()) {
            case R.id.spinner2:
                if (position != 0) {
                    reasonname = reasondata.get(position - 1).getReason();
                    reasonid = String.valueOf(reasondata.get(position - 1).getReasonId());
                    entry_allow = String.valueOf(reasondata.get(position - 1).getEntryAllow());
                    image_allow = String.valueOf(reasondata.get(position - 1).getImageAllow());

                    if (image_allow.equalsIgnoreCase("1")) {
                        rel_cam.setVisibility(View.VISIBLE);
                        image = "true";
                    } else {
                        rel_cam.setVisibility(View.GONE);
                        image = "false";
                    }
                    reason_reamrk = "true";
                    if (reason_reamrk.equalsIgnoreCase("true")) {
                        reason_lay.setVisibility(View.VISIBLE);
                    } else {
                        reason_lay.setVisibility(View.GONE);
                    }
                } else {
                    reasonname = "";
                    reasonid = "";
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    protected void startCameraActivity() {
        try {

            Log.i("MakeMachine", "startCameraActivity()");
            File file = new File(_path);
            outputFileUri = Uri.fromFile(file);

            String defaultCameraPackage = "";
            final PackageManager packageManager = getPackageManager();
            List<ApplicationInfo> list = packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
            for (int n = 0; n < list.size(); n++) {
                if ((list.get(n).flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                    Log.e("TAG", "Installed Applications  : " + list.get(n).loadLabel(packageManager).toString());
                    Log.e("TAG", "package name  : " + list.get(n).packageName);

                    //temp value in case camera is gallery app above jellybean
                    String packag = list.get(n).loadLabel(packageManager).toString();
                    if (packag.equalsIgnoreCase("Gallery") || packag.equalsIgnoreCase("Galeri") || packag.equalsIgnoreCase("الاستوديو")) {
                        gallery_package = list.get(n).packageName;
                    }

                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if (packag.equalsIgnoreCase("Camera") || packag.equalsIgnoreCase("Kamera") || packag.equalsIgnoreCase("الكاميرا")) {
                            defaultCameraPackage = list.get(n).packageName;
                            break;
                        }
                    } else {

                        if (packag.equalsIgnoreCase("Camera") || packag.equalsIgnoreCase("Kamera") || packag.equalsIgnoreCase("الكاميرا")) {

                            defaultCameraPackage = list.get(n).packageName;
                            break;
                        }
                    }
                }
            }

            //com.android.gallery3d

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            intent.setPackage(defaultCameraPackage);
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            intent.setPackage(gallery_package);
            startActivityForResult(intent, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("deprecation")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MakeMachine", "resultCode: " + resultCode);
        switch (resultCode) {
            case 0:
                Log.i("MakeMachine", "User cancelled");
                break;

            case -1:

                if (_pathforcheck != null && !_pathforcheck.equals("")) {
                    if (new File(str + _pathforcheck).exists()) {

                        camera.setImageDrawable(getResources().getDrawable(R.mipmap.camera_green));
                        image1 = _pathforcheck;

                        _pathforcheck = "";

                    }
                }

                break;
        }

    }

    public boolean imageAllowed() {
        boolean result = true;

        if (image.equalsIgnoreCase("true")) {

            if (image1.equalsIgnoreCase("")) {

                result = false;

            }
        }

        return result;

    }

    public boolean textAllowed() {
        boolean result = true;


        if (text.getText().toString().trim().equals("")) {

            result = false;

        }

        return result;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.imgcam) {

            _pathforcheck = store_id + "NonWorking" + visit_date.replace("/", "") + getCurrentTime().replace(":", "") + ".jpg";

            _path = CommonString.FILE_PATH + _pathforcheck;

            startCameraActivity();
        }
        if (v.getId() == R.id.save) {

            if (validatedata()) {

                if (imageAllowed()) {

                    if (textAllowed()) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                NonWorkingReason.this);
                        builder.setMessage(R.string.title_activity_save_data)
                                .setCancelable(false)
                                .setPositiveButton(R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int id) {

                                                alert.getButton(
                                                        AlertDialog.BUTTON_POSITIVE)
                                                        .setEnabled(false);

                                                if (entry_allow.equals("0")) {

                                                    database.deleteAllTables();

                                                    jcp = database.getStoreData(visit_date);

                                                    for (int i = 0; i < jcp.size(); i++) {

                                                        String stoteid = String.valueOf(jcp.get(i).getStoreId());

                                                        CoverageBean cdata = new CoverageBean();
                                                        cdata.setStoreId(stoteid);
                                                        cdata.setVisitDate(visit_date);
                                                        cdata.setUserId(_UserId);
                                                        cdata.setInTime(intime);
                                                        cdata.setOutTime(getCurrentTime());
                                                        cdata.setReason(reasonname);
                                                        cdata.setReasonid(reasonid);
                                                        cdata.setLatitude("0.0");
                                                        cdata.setLongitude("0.0");
                                                        cdata.setImage(image1);

                                                        cdata.setRemark(text.getText().toString().replaceAll("[&^<>{}'$]", " "));
                                                        cdata.setStatus(CommonString.STORE_STATUS_LEAVE);

                                                        database.InsertCoverageData(cdata);

                                                        database.updateStoreStatusOnLeave(store_id, visit_date, CommonString.STORE_STATUS_LEAVE);

                                                        SharedPreferences.Editor editor = preferences.edit();

                                                        editor.putString(CommonString.KEY_STOREVISITED_STATUS + stoteid, "No");
                                                        editor.putString(CommonString.KEY_STOREVISITED_STATUS, "");
                                                        editor.putString(CommonString.KEY_STORE_IN_TIME, "");
                                                        editor.putString(CommonString.KEY_LATITUDE, "");
                                                        editor.putString(CommonString.KEY_LONGITUDE, "");
                                                        editor.commit();

                                                    }

                                                } else {

                                                    CoverageBean cdata = new CoverageBean();
                                                    cdata.setStoreId(store_id);
                                                    cdata.setVisitDate(visit_date);
                                                    cdata.setUserId(_UserId);
                                                    cdata.setInTime(intime);
                                                    cdata.setOutTime(getCurrentTime());
                                                    cdata.setReason(reasonname);
                                                    cdata.setReasonid(reasonid);
                                                    cdata.setLatitude("0.0");
                                                    cdata.setLongitude("0.0");
                                                    cdata.setImage(image1);

                                                    cdata.setRemark(text
                                                            .getText()
                                                            .toString()
                                                            .replaceAll(
                                                                    "[&^<>{}'$]",
                                                                    " "));
                                                    cdata.setStatus(CommonString.STORE_STATUS_LEAVE);

                                                    database.InsertCoverageData(cdata);

                                                    database.updateStoreStatusOnLeave(store_id, visit_date, CommonString.STORE_STATUS_LEAVE);

                                                    SharedPreferences.Editor editor = preferences
                                                            .edit();

                                                    editor.putString(CommonString.KEY_STOREVISITED_STATUS + store_id, "No");
                                                    editor.putString(
                                                            CommonString.KEY_STOREVISITED_STATUS,
                                                            "");
                                                    editor.putString(
                                                            CommonString.KEY_STORE_IN_TIME,
                                                            "");
                                                    editor.putString(
                                                            CommonString.KEY_LATITUDE,
                                                            "");
                                                    editor.putString(
                                                            CommonString.KEY_LONGITUDE,
                                                            "");
                                                    editor.commit();

                                                }

                                                new NonWorkingReason.GeoTagUpload(NonWorkingReason.this).execute();

                                                //  finish();
                                            }
                                        })
                                .setNegativeButton(R.string.closed,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int id) {
                                                dialog.cancel();
                                            }
                                        });

                        alert = builder.create();
                        alert.show();

                    } else {
                        Toast.makeText(getApplicationContext(), R.string.pleaseenterRemarks,
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.title_activity_take_image, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.title_activity_select_dropdown, Toast.LENGTH_SHORT).show();
            }
        }

    }

    public boolean validatedata() {
        boolean result = false;
        if (reasonid != null && !reasonid.equalsIgnoreCase("")) {
            result = true;
        }

        return result;

    }

    private static String arabicToenglish(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }

    public String getCurrentTime() {
        Calendar m_cal = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:mmm");
        String cdate = formatter.format(m_cal.getTime());

        if (preferences.getString(CommonString.KEY_LANGUAGE, "").equalsIgnoreCase(CommonString.KEY_LANGUAGE_ARABIC_KSA)) {
            cdate = arabicToenglish(cdate);
        } else if (preferences.getString(CommonString.KEY_LANGUAGE, "").equalsIgnoreCase(CommonString.KEY_LANGUAGE_ARABIC_UAE)) {
            cdate = arabicToenglish(cdate);
        }


        return cdate;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {

            // NavUtils.navigateUpFromSameTask(this);
            finish();

            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public class GeoTagUpload extends AsyncTask<Void, Void, String> {

        private Context context;

        GeoTagUpload(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom);
            dialog.setTitle(getResources().getString(R.string.dialog_title));
            dialog.setCancelable(false);
            dialog.show();
            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                GSKGTMerDB db = new GSKGTMerDB(NonWorkingReason.this);
                db.open();

                coverage = db.getCoverageWithStoreID_Data(store_id);

                Object result = CommonString.KEY_SUCCESS;

                // uploading Geotag

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

                return CommonString.KEY_SUCCESS;

            } catch (MalformedURLException e) {

                ResultFlag = false;
                strflag = CommonString.MESSAGE_EXCEPTION;

            } catch (SocketTimeoutException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_SOCKETEXCEPTION;

            } catch (InterruptedIOException e) {

                ResultFlag = false;
                strflag = CommonString.MESSAGE_EXCEPTION;


            } catch (IOException e) {

                ResultFlag = false;
                strflag = CommonString.MESSAGE_SOCKETEXCEPTION;

            } catch (XmlPullParserException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_XmlPull;

            } catch (Exception e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_EXCEPTION;

            }

            if (ResultFlag) {
                return CommonString.KEY_SUCCESS;

            } else {

                return strflag;
            }

        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            dialog.dismiss();

            if (result.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                dialog.dismiss();

                finish();

                //showAlert(getString(R.string.uploadeddata));


            } else {

                GSKGTMerDB db = new GSKGTMerDB(NonWorkingReason.this);
                db.open();

                dialog.dismiss();
                db.deleteTableWithStoreID(store_id);

                showAlert(getString(R.string.datanotfound) + " " + result);
            }
        }

    }

    public void showAlert(String str) {

        AlertDialog.Builder builder = new AlertDialog.Builder(NonWorkingReason.this);
        builder.setTitle("Parinaam");
        builder.setMessage(str).setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        finish();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


}
