package com.cpm.gskgtmerchandiser.dailyentry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpm.gskgtmerchandiser.Database.GSKGTMerDB;
import com.cpm.gskgtmerchandiser.GetterSetter.CoverageBean;
import com.cpm.gskgtmerchandiser.R;
import com.cpm.gskgtmerchandiser.constant.CommonString;
import com.cpm.gskgtmerchandiser.download.DownloadActivity;
import com.cpm.gskgtmerchandiser.gsonGetterSetter.JourneyPlan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by ashishc on 29-12-2016.
 */

public class StoreListActivity extends AppCompatActivity {
    ArrayList<CoverageBean> coverage = new ArrayList<CoverageBean>();
    ArrayList<JourneyPlan> storelist = new ArrayList<JourneyPlan>();
    //ListView list;
    private SharedPreferences preferences;
    String date, visit_status;
    GSKGTMerDB db;
    StoreListActivity.ValueAdapter adapter;
    RecyclerView recyclerView;
    private SharedPreferences.Editor editor = null;
    LinearLayout linearlay;
    String store_id;
    private Dialog dialog;
    boolean result_flag = false, leaveflag = false;
    FloatingActionButton fab;
    String storeid;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storelistfablayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        date = preferences.getString(CommonString.KEY_DATE, null);
        visit_status = preferences.getString(CommonString.KEY_STOREVISITED_STATUS, "");
        db = new GSKGTMerDB(StoreListActivity.this);
        db.open();

        linearlay = (LinearLayout) findViewById(R.id.no_data_lay);
        recyclerView = (RecyclerView) findViewById(R.id.drawer_layout_recycle);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getApplicationContext(), DownloadActivity.class);
                startActivity(in);
                finish();
            }
        });

    }


    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        toolbar.setTitle(getString(R.string.title_activity_store_list));
        db.open();
        storelist = db.getStoreData(date);
        coverage = db.getCoverageData(date);


        if (storelist.size() > 0) {
            //list.setAdapter(new MyAdaptor());
            adapter = new StoreListActivity.ValueAdapter(getApplicationContext(), storelist);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        } else {

            recyclerView.setVisibility(View.INVISIBLE);
            linearlay.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
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


    public class ValueAdapter extends RecyclerView.Adapter<StoreListActivity.ValueAdapter.MyViewHolder> {

        private LayoutInflater inflator;

        List<JourneyPlan> data = Collections.emptyList();

        public ValueAdapter(Context context, List<JourneyPlan> data) {
            inflator = LayoutInflater.from(context);
            this.data = data;

        }

        @Override
        public StoreListActivity.ValueAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

            View view = inflator.inflate(R.layout.storeviewlist, parent, false);

            StoreListActivity.ValueAdapter.MyViewHolder holder = new StoreListActivity.ValueAdapter.MyViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(final StoreListActivity.ValueAdapter.MyViewHolder viewHolder, final int position) {

            final JourneyPlan current = data.get(position);

            viewHolder.chkbtn.setBackgroundResource(R.mipmap.checkout);

            storeid = String.valueOf(current.getStoreId());
            //viewHolder.txt.setText(current.txt);

            viewHolder.txt.setText(current.getStoreName());
            viewHolder.address.setText(current.getAddress1());

           /* if (current.getCHECKOUT_STATUS().equalsIgnoreCase(CommonString.KEY_VALID)) {
                viewHolder.chkbtn.setVisibility(View.VISIBLE);
                viewHolder.imageview.setVisibility(View.INVISIBLE);
            } else*/
            if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_U)) {
                viewHolder.imageview.setVisibility(View.VISIBLE);
                viewHolder.imageview.setBackgroundResource(R.mipmap.tick);
                viewHolder.chkbtn.setVisibility(View.INVISIBLE);
            } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_D)) {
                viewHolder.imageview.setVisibility(View.VISIBLE);
                viewHolder.imageview.setBackgroundResource(R.mipmap.exclamation);
                viewHolder.chkbtn.setVisibility(View.INVISIBLE);
            } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_Y)) {
                viewHolder.imageview.setVisibility(View.VISIBLE);
                viewHolder.imageview.setBackgroundResource(R.mipmap.exclamation);
                viewHolder.chkbtn.setVisibility(View.INVISIBLE);
            } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_P)) {
                viewHolder.imageview.setVisibility(View.VISIBLE);
                viewHolder.imageview.setBackgroundResource(R.mipmap.exclamation);
                viewHolder.chkbtn.setVisibility(View.INVISIBLE);
            } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_L)) {
                viewHolder.imageview.setVisibility(View.VISIBLE);
                viewHolder.imageview.setBackgroundResource(R.mipmap.exclamation);
                viewHolder.chkbtn.setVisibility(View.INVISIBLE);
            } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.STORE_STATUS_LEAVE)) {

                viewHolder.imageview.setVisibility(View.VISIBLE);
                viewHolder.imageview.setBackgroundResource(R.mipmap.exclamation);
                viewHolder.chkbtn.setVisibility(View.INVISIBLE);
            }/* else if (checkleavestatus(storeid)) {
                viewHolder.imageview.setVisibility(View.VISIBLE);
                viewHolder.imageview.setBackgroundResource(R.mipmap.exclamation);
                viewHolder.chkbtn.setVisibility(View.INVISIBLE);
            }*/
            /*else if (current.getCHECKOUT_STATUS().equalsIgnoreCase(CommonString.KEY_INVALID)) {

            }*/
            else if (coverage.size() > 0) {
                String statusleave = "";

                for (int i = 0; i < coverage.size(); i++) {

                    if (storeid.equals(coverage.get(i).getStoreId())) {
                        statusleave = coverage.get(i).getStatus();


                        if (statusleave.equalsIgnoreCase(CommonString.STORE_STATUS_LEAVE)) {

                            viewHolder.imageview.setVisibility(View.VISIBLE);
                            viewHolder.imageview.setBackgroundResource(R.mipmap.exclamation);
                            viewHolder.chkbtn.setVisibility(View.INVISIBLE);


                        } else if (coverage.get(i).getStatus().equalsIgnoreCase(CommonString.KEY_VALID)) {
                            viewHolder.Cardbtn.setCardBackgroundColor(getResources().getColor(R.color.colorOrange));
                            viewHolder.chkbtn.setVisibility(View.VISIBLE);
                            viewHolder.imageview.setVisibility(View.INVISIBLE);

                        } else if (coverage.get(i).getStatus().equalsIgnoreCase(CommonString.KEY_INVALID)) {

                            viewHolder.imageview.setVisibility(View.INVISIBLE);
                            viewHolder.chkbtn.setVisibility(View.INVISIBLE);
                            viewHolder.Cardbtn.setCardBackgroundColor(getResources().getColor(R.color.green));
                        } else {

                            viewHolder.Cardbtn.setCardBackgroundColor(getResources().getColor(R.color.colorOrange));
                            viewHolder.imageview.setVisibility(View.INVISIBLE);
                            viewHolder.chkbtn.setVisibility(View.INVISIBLE);
                        }

                        break;


                    }

                       /* if (coverage.get(i).getInTime() != null) {

                            if (coverage.get(i).getOutTime() == null) {

                                if (storeid.equals(coverage.get(i).getStoreId())) {
                                    viewHolder.imageview.setVisibility(View.VISIBLE);
                                    //  viewHolder.imageview.setBackgroundResource(R.mipmap.checkin);
                                    viewHolder.chkbtn.setVisibility(View.INVISIBLE);
                                    viewHolder.Cardbtn.setCardBackgroundColor(getResources().getColor(R.color.green));


                                }
                                break;
                            }

                        }*/

                }

            } else {

                viewHolder.Cardbtn.setCardBackgroundColor(getResources().getColor(R.color.colorOrange));
                viewHolder.imageview.setVisibility(View.INVISIBLE);
                viewHolder.chkbtn.setVisibility(View.INVISIBLE);
            }


            viewHolder.relativelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    store_id = String.valueOf(current.getStoreId());

                    if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_U)) {
                        Snackbar.make(v, R.string.title_store_list_activity_store_already_done, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_D)) {

                        Snackbar.make(v, R.string.title_store_list_activity_store_data_uploaded, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_Y)) {

                        Snackbar.make(v, R.string.title_store_list_activity_store_already_checkout, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_P)) {

                        Snackbar.make(v, R.string.title_store_list_activity_store_again_uploaded, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_L)) {
                        Snackbar.make(v, R.string.title_store_list_activity_store_closed, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.STORE_STATUS_LEAVE)) {
                        Snackbar.make(v, R.string.title_store_list_activity_already_store_closed, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    } else if (checkleavestatus(store_id)) {

                        Snackbar.make(v, R.string.title_store_list_activity_already_store_closed, Snackbar.LENGTH_LONG).setAction("Action", null).show();


                    } else {

                        // PUT IN PREFERENCES

                        // showMyDialog(store_id, current.getSTORE_NAME(), "Yes", current.getVISIT_DATE(), current.getCHECKOUT_STATUS());

                        if (!setcheckedmenthod(store_id)) {
                            boolean enteryflag = true;
                            if (coverage.size() > 0) {
                                int i;
                                for (i = 0; i < coverage.size(); i++) {

                                    if (coverage.get(i).getInTime() != null) {

                                        if (coverage.get(i).getOutTime() == null) {
                                            if (!store_id.equals(coverage.get(i).getStoreId())) {
                                                Snackbar.make(v, R.string.title_store_list_checkout_current, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                                enteryflag = false;

                                            }
                                            break;
                                        }
                                    }
                                }
                            }

                            if (enteryflag) {
                                showMyDialog(store_id, current.getStoreName(), "Yes", current.getVisitDate(), current.getUploadStatus(), current.getGeoTag(), current);
                            }
                        } else {
                            Snackbar.make(v, R.string.title_store_list_checkout_Already_filled, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                        }
                    }
                }
            });


            viewHolder.chkbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StoreListActivity.this);
                    builder.setMessage(R.string.wantcheckout)
                            .setCancelable(false)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    if (CheckNetAvailability()) {
                                        /*Intent i = new Intent(StoreListActivity.this, CheckoutActivity.class);
                                        i.putExtra(CommonString.KEY_STORE_ID, current.getSTORE_ID());
                                        startActivity(i);*/

                                       // Intent i = new Intent(StoreListActivity.this, StoreCheckoutImageActivity.class);
                                      //  i.putExtra(CommonString.KEY_STORE_ID, current.getSTORE_ID());
                                       // startActivity(i);
                                    } else {
                                        Snackbar.make(recyclerView, R.string.nonetwork, Snackbar.LENGTH_SHORT)
                                                .setAction("Action", null).show();
                                    }
                                }
                            })
                            .setNegativeButton(R.string.closed, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

        }

        public boolean CheckNetAvailability() {

            boolean connected = false;
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                    .getState() == NetworkInfo.State.CONNECTED
                    || connectivityManager.getNetworkInfo(
                    ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                // we are connected to a network
                connected = true;
            }
            return connected;
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView txt, address;
            ImageView icon;
            RelativeLayout relativelayout;
            ImageView imageview;
            Button chkbtn;
            CardView Cardbtn;

            public MyViewHolder(View itemView) {
                super(itemView);
                txt = (TextView) itemView.findViewById(R.id.storelistviewxml_storename);
                address = (TextView) itemView.findViewById(R.id.storelistviewxml_storeaddress);

                relativelayout = (RelativeLayout) itemView.findViewById(R.id.storenamelistview_layout);
                //imageview = (ImageView) itemView.findViewById(R.id.imageView2);

                imageview = (ImageView) itemView.findViewById(R.id.storelistviewxml_storeico);


                chkbtn = (Button) itemView.findViewById(R.id.chkout);
                Cardbtn = (CardView) itemView.findViewById(R.id.card_view);


            }
        }

    }


    void showMyDialog(final String storeCd, final String storeName, final String status, final String visitDate, final String checkout_status, final String GeotagStatus, final JourneyPlan current) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogbox);

        RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radiogrpvisit);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.yes) {

                    if (!GeotagStatus.equalsIgnoreCase("N")) {

                        editor = preferences.edit();
                        editor.putString(CommonString.KEY_STORE_ID, String.valueOf(current.getStoreId()));
                        editor.putString(CommonString.KEY_STORE_NAME, current.getStoreName());
                        editor.putString(CommonString.KEY_VISIT_DATE, current.getVisitDate());
                       // editor.putString(CommonString.KEY_CHECKOUT_STATUS, current.getCheckout());
                        editor.putString(CommonString.KEY_CLASS_ID, String.valueOf(current.getClassificationId()));
                        editor.putString(CommonString.KEY_GEO_TAG, current.getGeoTag());
                        editor.putString(CommonString.KEY_STORETYPE_ID, String.valueOf(current.getStoreTypeId()));
                        editor.putString(CommonString.KEY_UPLOAD_STATUS, current.getUploadStatus());

                        editor.commit();


                        boolean flag = true;
                        if (coverage.size() > 0) {
                            for (int i = 0; i < coverage.size(); i++) {
                                if (store_id.equals(coverage.get(i).getStoreId())) {
                                    flag = false;
                                    break;
                                }
                            }
                        }
                        if (flag == true) {

                            Intent in = new Intent(StoreListActivity.this, StoreimageActivity.class);
                            in.putExtra(CommonString.TAG_OBJECT,current);
                            startActivity(in);
                            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                            dialog.cancel();
                        } else {
                            //Intent in = new Intent(StoreListActivity.this, StoreWisePerformanceActivity.class);
                            //in.putExtra(CommonString.TAG_OBJECT,current);
                           // startActivity(in);
                           // overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                           // dialog.cancel();
                        }

                    } else {
                        dialog.cancel();
                        AlertDialog.Builder builder = new AlertDialog.Builder(StoreListActivity.this);
                        builder.setTitle(getResources().getString(R.string.dialog_title));
                        builder.setMessage(R.string.first_geotag_the_store).setCancelable(false)
                                .setPositiveButton(getResources().getString(R.string.ok),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog1,
                                                                int id) {

                                                dialog1.cancel();

                                            }
                                        });


                        AlertDialog alert = builder.create();

                        alert.show();

                    }


                } else if (checkedId == R.id.no) {

                    dialog.cancel();

                    GSKGTMerDB db = new GSKGTMerDB(StoreListActivity.this);
                    db.open();

                    coverage = db.getCoverageWithStoreID_Data(storeCd);

                    if (coverage.size() > 0) {

                        if (coverage.get(0).getStatus().equals(CommonString.KEY_INVALID) || coverage.get(0).getStatus().equals(CommonString.KEY_VALID)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(StoreListActivity.this);
                            builder.setMessage(R.string.DELETE_ALERT_MESSAGE)
                                    .setCancelable(false)
                                    .setPositiveButton(getResources().getString(R.string.yes),
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,
                                                                    int id) {

                                                    UpdateStore(store_id);

                                                    Intent in = new Intent(StoreListActivity.this, NonWorkingReason.class);
                                                    in.putExtra(CommonString.KEY_STORE_ID, current.getStoreId());
                                                    startActivity(in);

                                                }
                                            })
                                    .setNegativeButton(getResources().getString(R.string.no),
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,
                                                                    int id) {


                                                    dialog.cancel();
                                                }
                                            });
                            AlertDialog alert = builder.create();

                            alert.show();
                        } else {
                            Intent in = new Intent(StoreListActivity.this, NonWorkingReason.class);
                            in.putExtra(CommonString.KEY_STORE_ID, current.getStoreId());
                            startActivity(in);
                        }

                    } else {

                        Intent in = new Intent(StoreListActivity.this, NonWorkingReason.class);
                        in.putExtra(CommonString.KEY_STORE_ID, current.getStoreId());
                        startActivity(in);
                    }

                }
            }

        });


        dialog.show();
    }


    public boolean setcheckedmenthod(String store_cd) {

        boolean result_flag = false;
        for (int i = 0; i < coverage.size(); i++) {
            if (store_cd.equals(coverage.get(i).getStoreId())) {
                if (coverage.get(i).getOutTime() != null) {
                    result_flag = true;
                    break;
                }
            }
        }

        return result_flag;
    }


    public void UpdateStore(String storeid) {

        db.open();
        db.deleteTableWithStoreID(storeid);
        db.updateStoreStatus(storeid, storelist.get(0).getVisitDate(), "N");

    }


    public boolean checkleavestatus(String store_cd) {

        if (coverage.size() > 0) {


            for (int i = 0; i < coverage.size(); i++) {
                if (store_cd.equals(coverage.get(i).getStoreId())) {
                    if (coverage.get(i).getStatus().equalsIgnoreCase(CommonString.STORE_STATUS_LEAVE)) {
                        result_flag = true;
                        break;
                    }
                } else {

                    result_flag = false;

                }
            }
        }
        return result_flag;
    }


}



