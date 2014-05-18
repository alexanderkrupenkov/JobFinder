package de.softan.jobfinder.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.softan.jobfinder.R;
import de.softan.jobfinder.data.workers.PreviewVacancy;
import de.softan.jobfinder.data.workers.Vacancy;
import de.softan.jobfinder.networking.async.GetVacancyWorker;


public class VacancyDetailsActivity extends ActionBarActivity {

    public static final String DETAIL_VACANCY = "vacancy";
    private CharSequence mTitle;
    PreviewVacancy mPreviewVacancy;
    Vacancy mVacancy;
    public static Handler handlerVacancy;
    TextView mVacancyName;
    TextView mEmployerName;
    TextView mDateCreated;
    TextView mDescription;
    private GetVacancyWorker getVacancyWorker = new GetVacancyWorker(this);

    public static void launch(Context context, PreviewVacancy previewVacancy) {
        Intent intent = new Intent(context, VacancyDetailsActivity.class);
        intent.putExtra(DETAIL_VACANCY, previewVacancy);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_details);
        mPreviewVacancy = getIntent().getParcelableExtra(DETAIL_VACANCY);

        getVacancyWorker.loadRequest(mPreviewVacancy.url);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Вакансия");

        mVacancyName = (TextView) findViewById(R.id.vacancyName);
        mEmployerName = (TextView) findViewById(R.id.employerName);
        mDateCreated = (TextView) findViewById(R.id.vacancyDateCreated);
        mDescription = (TextView) findViewById(R.id.description);

        mVacancyName.setText(mPreviewVacancy.vacancyName);
        mEmployerName.setText(mPreviewVacancy.employerName);
        SimpleDateFormat formattedDate = new SimpleDateFormat("yyy-dd-mm");
        try {
            Date date = formattedDate.parse(mPreviewVacancy.dateCreated);
            mDateCreated.setText(formattedDate.format(date));
        } catch(Exception e) {
        }

        handlerVacancy = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 2) {
                    mVacancy = ((Vacancy) msg.obj);
                    mDescription.setText(Html.fromHtml(mVacancy.description));
                }
            }

            ;
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }



}
