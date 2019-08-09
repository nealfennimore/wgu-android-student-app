package edu.wgu.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.wgu.student.database.AssessmentEntity;
import edu.wgu.student.database.CourseEntity;
import edu.wgu.student.database.TermEntity;
import edu.wgu.student.ui.AssessmentRecyclerViewAdapter;
import edu.wgu.student.ui.CourseRecyclerViewAdapter;
import edu.wgu.student.ui.TermRecyclerViewAdapter;
import edu.wgu.student.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    public static final String MAIN_ACTIVITY = "edu.wgu.student.MAIN_ACTIVITY";

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewModel();
        initTermViewRecycler();
        initCourseViewRecycler();
        initAssessmentViewRecycler();
        initEventListeners();
    }

    private void initEventListeners() {
        Button goToCreateTerm = findViewById(R.id.goToCreateTerm);
        goToCreateTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddTermClick();
            }
        });

        Button goToCreateAssessment = findViewById(R.id.goToCreateAssessment);
        goToCreateAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddAssessmentClick();
            }
        });

        Button goToCreateCourse = findViewById(R.id.goToCreateCourse);
        goToCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddCourseClick();
            }
        });
    }

    private void initTermViewRecycler() {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.termRV);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final TermRecyclerViewAdapter adapter = new TermRecyclerViewAdapter(this);
        mViewModel.getAllTerms().observe(this, terms -> adapter.setData(terms));
        adapter.setClickListener(new TermClicker());
        recyclerView.setAdapter(adapter);
    }

    private void initCourseViewRecycler() {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.courseRV);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CourseRecyclerViewAdapter adapter = new CourseRecyclerViewAdapter(this);
        mViewModel.getAllCourses().observe(this, courses -> adapter.setData(courses));
        adapter.setClickListener(new CourseClicker());
        recyclerView.setAdapter(adapter);
    }

    private void initAssessmentViewRecycler() {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.assessmentRV);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final AssessmentRecyclerViewAdapter adapter = new AssessmentRecyclerViewAdapter(this);
        mViewModel.getAllAssessments().observe(this, assessments -> adapter.setData(assessments));
        adapter.setClickListener(new AssessmentClicker());
        recyclerView.setAdapter(adapter);
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                    .get(MainViewModel.class);
    }

    private void onAddTermClick(){
        Intent intent = new Intent(this, CreateTermActivity.class);
        startActivity(intent);
    }

    private void onAddCourseClick(){
        Intent intent = new Intent(this, CreateCourseActivity.class);
        startActivity(intent);
    }

    private void onAddAssessmentClick(){
        Intent intent = new Intent(this, CreateAssessmentActivity.class);
        startActivity(intent);
    }

    private void onTermClick(TermEntity term) {
        Intent intent = new Intent(this, ShowTermActivity.class);
        intent.putExtra(MAIN_ACTIVITY, term.getId());
        startActivity(intent);
    }

    private void onCourseClick(CourseEntity course) {
        Intent intent = new Intent(this, ShowCourseActivity.class);
        intent.putExtra(MAIN_ACTIVITY, course.getId());
        startActivity(intent);
    }

    private void onAssessmentClick(AssessmentEntity assessment) {
        Intent intent = new Intent(this, ShowAssessmentActivity.class);
        intent.putExtra(MAIN_ACTIVITY, assessment.getId());
        startActivity(intent);
    }

    public class TermClicker implements TermRecyclerViewAdapter.ItemClickListener{
        @Override
        public void onItemClick(View view, TermEntity term, int position) {
            onTermClick(term);
        }
    }

    public class CourseClicker implements CourseRecyclerViewAdapter.ItemClickListener{
        @Override
        public void onItemClick(View view, CourseEntity course, int position) {
            onCourseClick(course);
        }
    }

    public class AssessmentClicker implements AssessmentRecyclerViewAdapter.ItemClickListener{
        @Override
        public void onItemClick(View view, AssessmentEntity assessment, int position) {
            onAssessmentClick(assessment);
        }
    }
}
