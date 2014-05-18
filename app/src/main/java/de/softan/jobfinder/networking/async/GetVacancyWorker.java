package de.softan.jobfinder.networking.async;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import de.softan.jobfinder.data.workers.PreviewVacancy;
import de.softan.jobfinder.data.workers.Vacancy;
import de.softan.jobfinder.networking.ServiceHandler;
import de.softan.jobfinder.phone.VacancyDetailsActivity;

/**
 * Created by Alexander on 17.05.2014.
 */
public class GetVacancyWorker {

    private Context mContext;
    private String mUrl;
    private Vacancy mVacancy;

    public GetVacancyWorker(Context context) {
        mContext = context;
    }

    public Bundle loadRequest(String url) {
        Bundle bundle1 = new Bundle();
        mUrl = url;

        WorkerDownloadJson workerDownloadJson = new WorkerDownloadJson();
        workerDownloadJson.execute();

        return null;
    }

    private class WorkerDownloadJson extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(mUrl, ServiceHandler.GET);

            if (jsonStr != null) {
                try {
                    Log.d("xxx", jsonStr);
                    mVacancy = new Vacancy();
                    JSONObject object = new JSONObject(jsonStr);
                    mVacancy.description = object.getString(Vacancy.DESCRIPTION);
                    JSONObject name = object.getJSONObject(Vacancy.EXPERIENCE);
                    mVacancy.experience = name.getString(PreviewVacancy.NAME);
                    JSONObject nameSc = object.getJSONObject(Vacancy.SCHEDULE);
                    mVacancy.schedule = nameSc.getString(PreviewVacancy.NAME);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try {
                Message msg = VacancyDetailsActivity.handlerVacancy.obtainMessage(2, mVacancy);
                VacancyDetailsActivity.handlerVacancy.sendMessage(msg);
            } catch (Exception e) {

            }
        }
    }

}
