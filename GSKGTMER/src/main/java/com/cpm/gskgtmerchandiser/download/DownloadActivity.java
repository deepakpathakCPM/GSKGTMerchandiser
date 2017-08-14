package com.cpm.gskgtmerchandiser.download;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cpm.gskgtmerchandiser.Database.GSKGTMerDB;
import com.cpm.gskgtmerchandiser.Exception.TableNotCreatedException;
import com.cpm.gskgtmerchandiser.R;
import com.cpm.gskgtmerchandiser.constant.AlertandMessages;
import com.cpm.gskgtmerchandiser.constant.CommonString;
import com.cpm.gskgtmerchandiser.gsonGetterSetter.BrandGroupMasterGetterSetter;
import com.cpm.gskgtmerchandiser.gsonGetterSetter.BrandMasterGetterSetter;
import com.cpm.gskgtmerchandiser.gsonGetterSetter.CategoryMasterGetterSetter;
import com.cpm.gskgtmerchandiser.gsonGetterSetter.JCPGetterSetter;
import com.cpm.gskgtmerchandiser.gsonGetterSetter.MappingPosmGetterSetter;
import com.cpm.gskgtmerchandiser.gsonGetterSetter.MappingStockGetterSetter;
import com.cpm.gskgtmerchandiser.gsonGetterSetter.MappingWindowGetterSetter;
import com.cpm.gskgtmerchandiser.gsonGetterSetter.NonWorkingReasonGetterSetter;
import com.cpm.gskgtmerchandiser.gsonGetterSetter.NonWorkingSubReasonGetterSetter;
import com.cpm.gskgtmerchandiser.gsonGetterSetter.PosmMasterGetterSetter;
import com.cpm.gskgtmerchandiser.gsonGetterSetter.SkuMasterGetterSetter;
import com.cpm.gskgtmerchandiser.gsonGetterSetter.TableQuery;
import com.cpm.gskgtmerchandiser.gsonGetterSetter.TableStructureGetterSetter;
import com.cpm.gskgtmerchandiser.gsonGetterSetter.WindowChecklistGetterSetter;
import com.cpm.gskgtmerchandiser.gsonGetterSetter.WindowLocationGetterSetter;
import com.cpm.gskgtmerchandiser.gsonGetterSetter.WindowMasterGetterSetter;
import com.cpm.gskgtmerchandiser.upload.Retrofit_method.UploadImageWithRetrofit;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.List;


public class DownloadActivity extends AppCompatActivity {

    Data data;
    int eventType;
    GSKGTMerDB db;
    String userId;
    private Dialog dialog;
    private ProgressBar pb;
    private TextView percentage, message;
    private SharedPreferences preferences = null;
    Toolbar toolbar;
    String str;
    boolean ResultFlag = true;
    TableStructureGetterSetter tableStructureObj;
    JCPGetterSetter jcpObject;
    NonWorkingReasonGetterSetter nonWorkingObj;
    NonWorkingSubReasonGetterSetter nonWorkingSubObj;
    SkuMasterGetterSetter skuObject;
    CategoryMasterGetterSetter categoryObject;
    BrandGroupMasterGetterSetter brandGObject;
    BrandMasterGetterSetter brandMObject;
    WindowMasterGetterSetter windowMObject;
    WindowChecklistGetterSetter windowCObject;
    PosmMasterGetterSetter posmMObject;
    WindowLocationGetterSetter windowLObject;
    MappingWindowGetterSetter mappingWObject;
    MappingStockGetterSetter mappingSObject;
    MappingPosmGetterSetter mappingPObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new GSKGTMerDB(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        userId = preferences.getString(CommonString.KEY_USERNAME, null);
        new UploadTask(DownloadActivity.this).execute();
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
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.custom_dialog_progress);
            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                data = new Data();
                data.value = 6;
                data.name = "" + getResources().getString(R.string.download_data);
                publishProgress(data);

                UploadImageWithRetrofit downloadData = new UploadImageWithRetrofit(context);

                //region download structure
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Downloadtype", "Table_Structure");
                jsonObject.put("Username", userId);
                String jsonString2 = jsonObject.toString();

                String result = downloadData.downloadDataUniversal(jsonString2, CommonString.DOWNLOAD_ALL_SERVICE);
                data.value = 6;
                data.name = "Table_Structure " + getResources().getString(R.string.download_data);
                publishProgress(data);

                if (result.equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Table_Structure");
                } else if (result.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    Gson gson = new Gson();
                    tableStructureObj = gson.fromJson(result, TableStructureGetterSetter.class);
                }
                //endregion

                //region download Journey plan
                jsonObject = new JSONObject();
                jsonObject.put("Downloadtype", "Journey_Plan");
                jsonObject.put("Username", userId);
                jsonString2 = jsonObject.toString();

                result = downloadData.downloadDataUniversal(jsonString2, CommonString.DOWNLOAD_ALL_SERVICE);
                if (result.toString() != null) {
                    data.value = 12;
                    data.name = "JCP " + getResources().getString(R.string.download_data);
                }
                publishProgress(data);

                if (result.equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Journey_Plan");
                } else if (result.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    Gson gson = new Gson();
                    jcpObject = gson.fromJson(result, JCPGetterSetter.class);
                }
                //endregion

                //region download non working reason
                jsonObject = new JSONObject();
                jsonObject.put("Downloadtype", "Non_Working_Reason");
                jsonObject.put("Username", userId);
                jsonString2 = jsonObject.toString();

                result = downloadData.downloadDataUniversal(jsonString2, CommonString.DOWNLOAD_ALL_SERVICE);
                if (result.toString() != null) {
                    data.value = 18;
                    data.name = "Non_Working_Reason " + getResources().getString(R.string.download_data);
                }
                publishProgress(data);

                if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Non_Working_Reason");
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    Gson gson = new Gson();
                    nonWorkingObj = gson.fromJson(result, NonWorkingReasonGetterSetter.class);
                }
                //endregion

                //region non working sub reason
                jsonObject = new JSONObject();
                jsonObject.put("Downloadtype", "non_working_sub_reason");
                jsonObject.put("Username", userId);
                jsonString2 = jsonObject.toString();

                result = downloadData.downloadDataUniversal(jsonString2, CommonString.DOWNLOAD_ALL_SERVICE);
                if (result.toString() != null) {
                    data.value = 24;
                    data.name = "non_working_sub_reason " + getResources().getString(R.string.download_data);
                }
                publishProgress(data);

                if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("non_working_sub_reason");
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    Gson gson = new Gson();
                    nonWorkingSubObj = gson.fromJson(result, NonWorkingSubReasonGetterSetter.class);
                }
                //endregion

                //region download skumaster
                jsonObject = new JSONObject();
                jsonObject.put("Downloadtype", "Sku_Master");
                jsonObject.put("Username", userId);
                jsonString2 = jsonObject.toString();

                result = downloadData.downloadDataUniversal(jsonString2, CommonString.DOWNLOAD_ALL_SERVICE);
                if (result.toString() != null) {
                    data.value = 30;
                    data.name = "Sku_Master " + getResources().getString(R.string.download_data);
                }
                publishProgress(data);

                if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Sku_Master");
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    Gson gson = new Gson();
                    skuObject = gson.fromJson(result, SkuMasterGetterSetter.class);
                }
                //endregion

                //region download Category_Master
                jsonObject = new JSONObject();
                jsonObject.put("Downloadtype", "Category_Master");
                jsonObject.put("Username", userId);
                jsonString2 = jsonObject.toString();

                result = downloadData.downloadDataUniversal(jsonString2, CommonString.DOWNLOAD_ALL_SERVICE);
                if (result.toString() != null) {
                    data.value = 36;
                    data.name = "Category_Master " + getResources().getString(R.string.download_data);
                }
                publishProgress(data);

                if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Category_Master");
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    Gson gson = new Gson();
                    categoryObject = gson.fromJson(result, CategoryMasterGetterSetter.class);
                }
                //endregion

                //region download Brand_Group_Master
                jsonObject = new JSONObject();
                jsonObject.put("Downloadtype", "Brand_Group_Master");
                jsonObject.put("Username", userId);
                jsonString2 = jsonObject.toString();

                result = downloadData.downloadDataUniversal(jsonString2, CommonString.DOWNLOAD_ALL_SERVICE);
                if (result.toString() != null) {
                    data.value = 42;
                    data.name = "Brand_Group_Master " + getResources().getString(R.string.download_data);
                }
                publishProgress(data);

                if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Brand_Group_Master");
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    Gson gson = new Gson();
                    brandGObject = gson.fromJson(result, BrandGroupMasterGetterSetter.class);
                }
                //endregion

                //region download Brand_Master
                jsonObject = new JSONObject();
                jsonObject.put("Downloadtype", "Brand_Master");
                jsonObject.put("Username", userId);
                jsonString2 = jsonObject.toString();

                result = downloadData.downloadDataUniversal(jsonString2, CommonString.DOWNLOAD_ALL_SERVICE);
                if (result.toString() != null) {
                    data.value = 48;
                    data.name = "Brand_Master " + getResources().getString(R.string.download_data);
                }
                publishProgress(data);

                if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Brand_Master");
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    Gson gson = new Gson();
                    brandMObject = gson.fromJson(result, BrandMasterGetterSetter.class);
                }
                //endregion

                //region download Window_Master
                jsonObject = new JSONObject();
                jsonObject.put("Downloadtype", "Window_Master");
                jsonObject.put("Username", userId);
                jsonString2 = jsonObject.toString();

                result = downloadData.downloadDataUniversal(jsonString2, CommonString.DOWNLOAD_ALL_SERVICE);
                if (result.toString() != null) {
                    data.value = 54;
                    data.name = "Window_Master " + getResources().getString(R.string.download_data);
                }
                publishProgress(data);

                if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Window_Master");
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    Gson gson = new Gson();
                    windowMObject = gson.fromJson(result, WindowMasterGetterSetter.class);
                }
                //endregion

                //region download Window_Checklist
                jsonObject = new JSONObject();
                jsonObject.put("Downloadtype", "Window_Checklist");
                jsonObject.put("Username", userId);
                jsonString2 = jsonObject.toString();

                result = downloadData.downloadDataUniversal(jsonString2, CommonString.DOWNLOAD_ALL_SERVICE);
                if (result.toString() != null) {
                    data.value = 60;
                    data.name = "Window_Checklist " + getResources().getString(R.string.download_data);
                }
                publishProgress(data);

                if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Window_Checklist");
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    Gson gson = new Gson();
                    windowCObject = gson.fromJson(result, WindowChecklistGetterSetter.class);
                }
                //endregion

                //region download Posm_Master
                jsonObject = new JSONObject();
                jsonObject.put("Downloadtype", "Posm_Master");
                jsonObject.put("Username", userId);
                jsonString2 = jsonObject.toString();

                result = downloadData.downloadDataUniversal(jsonString2, CommonString.DOWNLOAD_ALL_SERVICE);
                if (result.toString() != null) {
                    data.value = 66;
                    data.name = "Posm_Master " + getResources().getString(R.string.download_data);
                }
                publishProgress(data);

                if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Posm_Master");
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    Gson gson = new Gson();
                    posmMObject = gson.fromJson(result, PosmMasterGetterSetter.class);
                }
                //endregion

                //region download Window_Location
                jsonObject = new JSONObject();
                jsonObject.put("Downloadtype", "Window_Location");
                jsonObject.put("Username", userId);
                jsonString2 = jsonObject.toString();

                result = downloadData.downloadDataUniversal(jsonString2, CommonString.DOWNLOAD_ALL_SERVICE);
                if (result.toString() != null) {
                    data.value = 72;
                    data.name = "Window_Location " + getResources().getString(R.string.download_data);
                }
                publishProgress(data);

                if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Window_Location");
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    Gson gson = new Gson();
                    windowLObject = gson.fromJson(result, WindowLocationGetterSetter.class);
                }
                //endregion

                //region download Mapping_Window
                jsonObject = new JSONObject();
                jsonObject.put("Downloadtype", "Mapping_Window");
                jsonObject.put("Username", userId);
                jsonString2 = jsonObject.toString();

                result = downloadData.downloadDataUniversal(jsonString2, CommonString.DOWNLOAD_ALL_SERVICE);
                if (result.toString() != null) {
                    data.value = 78;
                    data.name = "Mapping_Window " + getResources().getString(R.string.download_data);
                }
                publishProgress(data);

                if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Mapping_Window");
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    Gson gson = new Gson();
                    mappingWObject = gson.fromJson(result, MappingWindowGetterSetter.class);
                }
                //endregion

                //region download Mapping_Stock
                jsonObject = new JSONObject();
                jsonObject.put("Downloadtype", "Mapping_Stock");
                jsonObject.put("Username", userId);
                jsonString2 = jsonObject.toString();

                result = downloadData.downloadDataUniversal(jsonString2, CommonString.DOWNLOAD_ALL_SERVICE);
                if (result.toString() != null) {
                    data.value = 84;
                    data.name = "Mapping_Stock " + getResources().getString(R.string.download_data);
                }
                publishProgress(data);

                if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Mapping_Stock");
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    Gson gson = new Gson();
                    mappingSObject = gson.fromJson(result, MappingStockGetterSetter.class);
                }
                //endregion

                //region download Mapping_Posm
                jsonObject = new JSONObject();
                jsonObject.put("Downloadtype", "Mapping_Posm");
                jsonObject.put("Username", userId);
                jsonString2 = jsonObject.toString();

                result = downloadData.downloadDataUniversal(jsonString2, CommonString.DOWNLOAD_ALL_SERVICE);
                if (result.toString() != null) {
                    data.value = 100;
                    data.name = "Mapping_Posm " + getResources().getString(R.string.download_data);
                }
                publishProgress(data);

                if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Mapping_Posm");
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    Gson gson = new Gson();
                    mappingPObject = gson.fromJson(result, MappingPosmGetterSetter.class);
                }
                //endregion


                db.open();
                String isAllTableCreated = createTable(tableStructureObj);
                if (isAllTableCreated != CommonString.KEY_SUCCESS) {
                    throw new TableNotCreatedException(isAllTableCreated);
                } else {
                    // db.open();
                    if (!db.insertJCPData(jcpObject)) {
                        AlertandMessages.showSnackbarMsg(toolbar, "JCP data not saved");
                    }
                    if (!db.insertNonWorkingData(nonWorkingObj)) {
                        AlertandMessages.showSnackbarMsg(toolbar, "Non Working Reason not saved");
                    }
                    if (!db.insertNonWorkingSubReasonData(nonWorkingSubObj)) {
                        AlertandMessages.showSnackbarMsg(toolbar, "Non Working Sub Reason not saved");
                    }
                    if (!db.InsertSkuMaster(skuObject)) {
                        AlertandMessages.showSnackbarMsg(toolbar, "Sku Master Reason not saved");
                    }
                    if (!db.InsertCategoryMaster(categoryObject)) {
                        AlertandMessages.showSnackbarMsg(toolbar, "Category Master data not saved");
                    }
                    if (!db.InsertBrandGroupMaster(brandGObject)) {
                        AlertandMessages.showSnackbarMsg(toolbar, "Brand Group data not saved");
                    }
                    if (!db.InsertBrandMaster(brandMObject)) {
                        AlertandMessages.showSnackbarMsg(toolbar, "Brand Master not saved");
                    }
                    if (!db.insertWindowMaster(windowMObject)) {
                        AlertandMessages.showSnackbarMsg(toolbar, "Window Master not saved");
                    }
                    if (!db.insertWindowChecklist(windowCObject)) {
                        AlertandMessages.showSnackbarMsg(toolbar, "Window Checklist not saved");
                    }
                    if (!db.insertPosmMaster(posmMObject)) {
                        AlertandMessages.showSnackbarMsg(toolbar, "Posm Master not saved");
                    }
                    if (!db.insertWindowLocation(windowLObject)) {
                        AlertandMessages.showSnackbarMsg(toolbar, "Window Location not saved");
                    }
                    if (!db.insertMappingWindow(mappingWObject)) {
                        AlertandMessages.showSnackbarMsg(toolbar, "Mapping Window not saved");
                    }
                    if (!db.insertMappingStock(mappingSObject)) {
                        AlertandMessages.showSnackbarMsg(toolbar, "Mapping Stock not saved");
                    }
                    if (!db.insertMappingPosm(mappingPObject)) {
                        AlertandMessages.showSnackbarMsg(toolbar, "Mapping Posm not saved");
                    }
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
                ResultFlag = false;
                str = CommonString.MESSAGE_EXCEPTION;
                return CommonString.MESSAGE_EXCEPTION;
            } catch (SocketTimeoutException e) {
                ResultFlag = false;
                str = CommonString.MESSAGE_NO_RESPONSE_SERVER;
                return CommonString.MESSAGE_NO_RESPONSE_SERVER;
            } catch (IOException e) {
                ResultFlag = false;
                str = CommonString.MESSAGE_SOCKETEXCEPTION;
                return CommonString.MESSAGE_SOCKETEXCEPTION;
            } catch (TableNotCreatedException e) {
                ResultFlag = false;
                str = CommonString.MESSAGE_ERROR_IN_EXECUTING + e.getMessage();
                return CommonString.MESSAGE_ERROR_IN_EXECUTING + e.getMessage();
            } catch (JsonSyntaxException e) {
                ResultFlag = false;
                str = CommonString.MESSAGE_INVALID_JSON;
                return CommonString.MESSAGE_INVALID_JSON;
            } catch (Exception e) {
                e.printStackTrace();
                ResultFlag = false;
                str = CommonString.MESSAGE_EXCEPTION;
                return CommonString.MESSAGE_EXCEPTION;
            }

            if (ResultFlag) {
                return "";
            } else {
                return str;
            }
        }

        @Override
        protected void onProgressUpdate(Data... values) {
            // TODO Auto-generated method stub

            pb.setProgress(values[0].value);
            percentage.setText(values[0].value + "%");
            message.setText(values[0].name);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.equalsIgnoreCase("")) {
                dialog.dismiss();

                showAlert(getString(R.string.data_downloaded_successfully));
            } else {
                dialog.dismiss();
                showAlert(getString(R.string.datanotfound) + " " + s);
            }
        }

    }

    public void showAlert(String str) {

        AlertDialog.Builder builder = new AlertDialog.Builder(DownloadActivity.this);
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

    String createTable(TableStructureGetterSetter tableGetSet) {
        List<TableQuery> tableList = tableGetSet.getResult();
        for (int i = 0; i < tableList.size(); i++) {
            String table = tableList.get(i).getSqlText();
            if (db.createtable(table) == 0) {
                return table;
            }
        }
        return CommonString.KEY_SUCCESS;
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle(getString(R.string.main_menu_activity_name));
    }


}
