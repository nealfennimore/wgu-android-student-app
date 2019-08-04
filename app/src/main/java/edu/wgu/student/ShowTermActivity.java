package edu.wgu.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import edu.wgu.student.database.CourseEntity;
import edu.wgu.student.database.TermEntity;
import edu.wgu.student.ui.CourseCheckboxRecyclerViewAdapter;
import edu.wgu.student.ui.TermRecyclerViewAdapter;
import edu.wgu.student.utilities.DateHelper;
import edu.wgu.student.viewmodel.MainViewModel;
import edu.wgu.student.viewmodel.ShowTermViewModel;
@TargetApi(24)
public class ShowTermActivity extends AppCompatActivity {
    private ShowTermViewModel mViewModel;
    private int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_term);

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
                List<Integer> courseIds = mViewModel.getSelectedCourseIds();
                List<Integer> initialCourseIds = mViewModel.getInitialSelectedCourses();

                List<Integer> courseIdsToAdd = courseIds.stream()
                    .filter( courseId -> ! initialCourseIds.contains(courseId))
                    .collect(Collectors.toList());

                List<Integer> courseIdsToRemove = initialCourseIds.stream()
                        .filter( courseId -> ! courseIds.contains(courseId))
                        .collect(Collectors.toList());

                courseIdsToAdd.forEach(courseId -> {
                    mViewModel.insertTermCourseJoin(termId, courseId);
                });

                courseIdsToRemove.forEach(courseId -> {
                    mViewModel.deleteTermCourseJoin(termId, courseId);
                });

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
        final CourseCheckboxRecyclerViewAdapter adapter = new CourseCheckboxRecyclerViewAdapter(this, mViewModel);
        mViewModel.getSelectedCoursesForTerm(termId).observe(this, selectedCourses -> {
            mViewModel.setInitialSelectedCourses(selectedCourses);
            mViewModel.setSelectedCourses(selectedCourses);

            mViewModel.getAllCourses().observe(this, courses -> adapter.setData(courses));
        });
        recyclerView.setAdapter(adapter);
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(ShowTermViewModel.class);
    }
}
