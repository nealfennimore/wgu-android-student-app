package edu.wgu.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.Executor;

import edu.wgu.student.database.CourseEntity;
import edu.wgu.student.database.TermEntity;
import edu.wgu.student.ui.CourseCheckboxRecyclerViewAdapter;
import edu.wgu.student.ui.TermRecyclerViewAdapter;
import edu.wgu.student.utilities.DateHelper;
import edu.wgu.student.viewmodel.MainViewModel;
import edu.wgu.student.viewmodel.ShowTermViewModel;

public class ShowTermActivity extends AppCompatActivity {
    private ShowTermViewModel mViewModel;
    private int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_term);

        Log.i("IJ", "ije");

        initViewModel();
        initTerm();
        initCourses();
        initListeners();
    }

    private void onSubmit() {
        Executor executor = mViewModel.getExecutor();
        Intent intent = new Intent(this, MainActivity.class);

        TextView tvTermName = findViewById(R.id.termName);
        String termName = tvTermName.getText().toString();

        TextView tvStartDate = findViewById(R.id.startDate);
        TextView tvEndDate = findViewById(R.id.endDate);

        Date startDate = DateHelper.toDate(tvStartDate.getText().toString());
        Date endDate = DateHelper.toDate(tvEndDate.getText().toString());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                TermEntity term = new TermEntity(termId, termName, startDate, endDate);
                mViewModel.updateTerm(term);
                startActivity(intent);
            }
        });
    }

    private void initListeners() {
        Button button = findViewById(R.id.updateTerm);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });
    }

    private void initTerm() {
        Intent intent = getIntent();
        termId = intent.getIntExtra(MainActivity.MAIN_ACTIVITY, 0);
        mViewModel.getTerm(termId).observe(this, term -> {
            TextView textView = findViewById(R.id.termName);
            textView.setText(term.getTitle());

            TextView startDate = findViewById(R.id.startDate);
            String formattedStartDate = DateHelper.toFormattedString(term.getStartDate());
            startDate.setText(formattedStartDate);

            TextView endDate = findViewById(R.id.endDate);
            String formattedEndDate = DateHelper.toFormattedString(term.getEndDate());
            endDate.setText(formattedEndDate);
        } );
    }

    private void initCourses() {
        RecyclerView recyclerView = findViewById(R.id.courseCheckboxRV);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CourseCheckboxRecyclerViewAdapter adapter = new CourseCheckboxRecyclerViewAdapter(this);
        mViewModel.getAllCourses().observe(this, courses -> adapter.setData(courses));
        adapter.setClickListener(new ShowTermActivity.CourseClicker());
        recyclerView.setAdapter(adapter);
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(ShowTermViewModel.class);
    }

    public class CourseClicker implements CourseCheckboxRecyclerViewAdapter.ItemClickListener{
        @Override
        public void onItemClick(View view, CourseEntity course, int position) {
            Log.i("COURSE_ENTITY", course.getTitle());
        }
    }
}
