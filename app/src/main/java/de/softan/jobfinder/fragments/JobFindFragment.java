package de.softan.jobfinder.fragments;

/**
 * Created by Alexander on 17.05.2014.
 */

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

import de.softan.jobfinder.R;
import de.softan.jobfinder.adapters.PreviewVacancyAdapter;
import de.softan.jobfinder.data.workers.PreviewVacancy;
import de.softan.jobfinder.networking.async.GetPreviewVacancyWorker;
import de.softan.jobfinder.phone.HomeActivity;
import de.softan.jobfinder.phone.VacancyDetailsActivity;

public class JobFindFragment extends Fragment {

    private static final String ARG_TYPE = "type";
    public static final int ARG_WITH_LOCATION = 2;
    EditText mEditText;
    Button mButton;
    Bundle mBundle = new Bundle();
    public static Handler handler;
    PreviewVacancyAdapter mAdapter;
    ListView mListView;
    ViewSwitcher mViewSwitcher;
    ViewSwitcher mViewSwitcherLocal;
    private int mWorkType;

    public static JobFindFragment newInstance() {
        JobFindFragment fragment = new JobFindFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }
    public static JobFindFragment newInstance(int workType) {
        JobFindFragment fragment = new JobFindFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE,workType);
        fragment.setArguments(args);
        return fragment;
    }

    public JobFindFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_job_find, container, false);

        mViewSwitcher = (ViewSwitcher) rootView.findViewById(R.id.viewSwitcher);
        mViewSwitcher.setVisibility(View.GONE);
        mViewSwitcherLocal = (ViewSwitcher) rootView.findViewById(R.id.viewSwitcher2);
        if(getArguments() != null)
        mWorkType = getArguments().getInt(ARG_TYPE, 0);
        mListView = (ListView) rootView.findViewById(R.id.listView);

        if(mWorkType == ARG_WITH_LOCATION) {
            mViewSwitcherLocal.setDisplayedChild(1);
        } else {
            mEditText = (EditText) rootView.findViewById(R.id.textSearch);
            mButton = (Button) rootView.findViewById(R.id.btStartFindJob);

            mButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String searchText = mEditText.getText().toString();
                    Log.d("xxx", "searchText = " + searchText);
                    GetPreviewVacancyWorker getVacancyWorker = new GetPreviewVacancyWorker(getActivity());
                    mBundle.putString(GetPreviewVacancyWorker.SEARCH_TEXT, searchText);
                    mViewSwitcher.setVisibility(View.VISIBLE);
                    mViewSwitcher.setDisplayedChild(0);
                    getVacancyWorker.loadRequest(GetPreviewVacancyWorker.GET_PREVIEW_BY_TEXT, mBundle);
                }
            });
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VacancyDetailsActivity.launch(getActivity(), mAdapter.getItem(position));
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(1);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    ArrayList<PreviewVacancy> previewVacancies = ((ArrayList<PreviewVacancy>)msg.obj);
                    mAdapter = new PreviewVacancyAdapter(previewVacancies, getActivity());
                    mViewSwitcher.setDisplayedChild(1);
                    mListView.setAdapter(mAdapter);
                }
            }

            ;
        };
    }
}