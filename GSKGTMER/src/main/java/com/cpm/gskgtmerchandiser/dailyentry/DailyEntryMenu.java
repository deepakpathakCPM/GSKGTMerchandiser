package com.cpm.gskgtmerchandiser.dailyentry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/*import com.squareup.picasso.Picasso;*/

import java.util.ArrayList;

import com.cpm.gskgtmerchandiser.GetterSetter.DailyEntryGetterSetter;
import com.cpm.gskgtmerchandiser.R;

public class DailyEntryMenu extends AppCompatActivity {
    private RecyclerView rec_daily_entry_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_entry_menu);
        rec_daily_entry_menu=(RecyclerView)findViewById(R.id.rec_daily_entry_menu);
    }


    public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
        private ArrayList<DailyEntryGetterSetter> android;
        private Context context;

        public DataAdapter(Context context,ArrayList<DailyEntryGetterSetter> android) {
            this.android = android;
            this.context = context;
        }

        @Override
        public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.daily_entry_menu, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

            viewHolder.daily_entrydata.setText(android.get(i).getDaily_entry_name());
          //  Picasso.with(context).load(android.get(i).getAndroid_image_url()).resize(240, 120).into(viewHolder.img_android);
        }

        @Override
        public int getItemCount() {
            return android.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView daily_entrydata;
            private ImageView daily_entry_image;
            public ViewHolder(View view) {
                super(view);

                daily_entrydata = (TextView)view.findViewById(R.id.daily_entrydata);
                daily_entry_image = (ImageView) view.findViewById(R.id.daily_entry_image);
            }
        }

    }
}
