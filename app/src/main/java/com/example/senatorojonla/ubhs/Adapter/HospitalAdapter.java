package com.example.senatorojonla.ubhs.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senatorojonla.ubhs.Model.HospitalModel;
import com.example.senatorojonla.ubhs.R;
import com.example.senatorojonla.ubhs.ViewAvBus;
import com.example.senatorojonla.ubhs.interfaces.ItemClickListener;

import java.util.List;


public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.MyViewHolder> {

    private List<HospitalModel> eventslist;
    Activity context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView busname, buscat, busmodel, busprice, busregno;
        ImageView busimage;
        Button hirebtn;



        public MyViewHolder(View view) {
            super(view);
            busname = view.findViewById(R.id.displaybusname);
            buscat = view.findViewById(R.id.displaybuscategory);
            busmodel = view.findViewById(R.id.displaybusmodel);
            busprice = view.findViewById(R.id.displaybusprice);
            busregno = view.findViewById(R.id.displaybusregno);
            hirebtn = view.findViewById(R.id.hirebusbtn);

        }

    }


    public HospitalAdapter(List<HospitalModel> developerList, Activity context) {
        this.eventslist = developerList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        HospitalModel event = eventslist.get(position);
        holder.busname.setText(event.getBusName());
        holder.buscat.setText(event.getBusCategory());
        holder.busmodel.setText(event.getBusModel());
        holder.busprice.setText(event.getBusPrice());
        holder.busregno.setText(event.getBusReg());



    }


    @Override
    public int getItemCount() {
        try {
            return eventslist.size();
        } catch (Exception e) {
            return 0;
        }
    }


}
