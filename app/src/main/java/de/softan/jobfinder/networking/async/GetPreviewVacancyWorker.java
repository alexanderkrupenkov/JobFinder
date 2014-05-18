package de.softan.jobfinder.networking.async;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.softan.jobfinder.data.workers.PreviewVacancy;
import de.softan.jobfinder.fragments.JobFindFragment;
import de.softan.jobfinder.networking.ServiceHandler;

/**
 * Created by Alexander on 17.05.2014.
 */
public class GetPreviewVacancyWorker {

    private Context mContext;
    public static final int GET_PREVIEW_BY_TEXT = 1;
    public static final int GET_VACANCY = 2;
    public static final String SEARCH_TEXT = "search_text";
    private static final String URL_SEARCH = "https://api.hh.ru/vacancies/?";
    private static final String PARAM_TEXT = "text=";
    private String mUrl;
    private List<PreviewVacancy> mVacancy;

    public GetPreviewVacancyWorker(Context context) {
        mContext = context;
    }

    public Bundle loadRequest(int workerType, Bundle bundle) {
        Bundle bundle1 = new Bundle();
        mUrl = URL_SEARCH;
        switch (workerType) {
            case GET_PREVIEW_BY_TEXT:
                mUrl += PARAM_TEXT + bundle.getString(SEARCH_TEXT);
                mUrl = mUrl.replace(" ", "+");
                Log.d("xxx", mUrl);
                break;

            case 2: break;

            case 3: break;
        }

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
                    mVacancy = new ArrayList<PreviewVacancy>();
                    JSONObject object = new JSONObject(jsonStr);

                        String countFound = object.getString(PreviewVacancy.FOUND);
                        JSONArray data = object.getJSONArray(PreviewVacancy.ITEMS);
                        PreviewVacancy previewVacancy;

                    for (int i = 0; i < data.length(); i++) {
                            previewVacancy = new PreviewVacancy();
                            JSONObject name = data.getJSONObject(i);
                            previewVacancy.vacancyName = name.getString(PreviewVacancy.NAME);
                            previewVacancy.dateCreated = name.getString(PreviewVacancy.CREATED_AT);
                            previewVacancy.url = name.getString(PreviewVacancy.URL_DETAILS);

                            JSONObject employer = name.getJSONObject(PreviewVacancy.EMPLOYER);
                            previewVacancy.employerName = employer.getString(PreviewVacancy.NAME);

                            mVacancy.add(previewVacancy);
                        }


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
                Message msg = JobFindFragment.handler.obtainMessage(1, mVacancy);
                JobFindFragment.handler.sendMessage(msg);
            } catch (Exception e) {

            }
        }
    }

}
