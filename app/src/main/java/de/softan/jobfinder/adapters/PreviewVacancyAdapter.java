package de.softan.jobfinder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.softan.jobfinder.R;
import de.softan.jobfinder.data.workers.PreviewVacancy;

public class PreviewVacancyAdapter extends BaseAdapter {

    private ArrayList<PreviewVacancy> mData;
    private Context mContext;

    TextView mVacancyName;
    TextView mEmployerName;
    TextView mDateCreated;

    public PreviewVacancyAdapter(ArrayList<PreviewVacancy> data, Context context) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public PreviewVacancy getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.preview_vacancy_list_item, parent, false);

            mVacancyName = (TextView)convertView.findViewById(R.id.vacancyName);
            mEmployerName = (TextView)convertView.findViewById(R.id.employerName);
            mDateCreated = (TextView)convertView.findViewById(R.id.vacancyDateCreated);

            ViewHolder vh = new ViewHolder(mVacancyName, mEmployerName,mDateCreated);
            convertView.setTag(vh);
        }

        ViewHolder vh = (ViewHolder) convertView.getTag();
        vh.textVacancyName.setText(mData.get(position).vacancyName);
        vh.textEmployerName.setText(mData.get(position).employerName);

        SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-dd-mm");
        try {
            Date date = formattedDate.parse(mData.get(position).dateCreated); //Сегодняшняя дата
            vh.textDateCreatedVacancy.setText(formattedDate.format(date));

        } catch(Exception e) {

        }

        return convertView;
    }

    private class ViewHolder{
        public final TextView textVacancyName;
        public final TextView textEmployerName;
        public final TextView textDateCreatedVacancy;

        public ViewHolder (TextView text, TextView textEmployer, TextView textDateCreatedVacancy){
            this.textVacancyName = text;
            this.textEmployerName = textEmployer;
            this.textDateCreatedVacancy = textDateCreatedVacancy;
        }
    }

    public void setData(ArrayList<PreviewVacancy> data) {
        for(PreviewVacancy previewVacancy : data) {
            this.mData.add(previewVacancy);
        }

    }
}
