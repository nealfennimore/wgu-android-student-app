package edu.wgu.student;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import edu.wgu.student.database.AssessmentEntity;
import edu.wgu.student.database.CourseStatus;
import edu.wgu.student.database.CourseEntity;
import edu.wgu.student.ui.AssessmentCheckboxRecyclerViewAdapter;
import edu.wgu.student.utilities.DateHelper;
import edu.wgu.student.viewmodel.ShowCourseViewModel;

@TargetApi(24)
public class ShowCourseActivity extends AppCompatActivity {
    private ShowCourseViewModel mViewModel;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_course);

        initViewModel();
        initCourse();
        initAssessments();
        initTerms();
        initMentors();
        initListeners();
    }

    private void setDeleteButtonEnabledState(){
        List<AssessmentEntity> assessments = mViewModel.getSelectedAssessments();
        Button deleteCourse = findViewById(R.id.deleteCourse);
        if(assessments.size() > 0){
            deleteCourse.setEnabled(false);
        } else {
            deleteCourse.setEnabled(true);
        }
    }

    private void onChange(){
        setDeleteButtonEnabledState();
    }

    private void onDelete() {
        Executor executor = mViewModel.getExecutor();
        Intent intent = new Intent(this, MainActivity.class);
        mViewModel.getCourse(courseId).observe( this, course -> {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    List<Integer> assessmentIds = mViewModel.getSelectedAssessmentIds();
                    List<Integer> initialAssessmentIds = mViewModel.getInitialSelectedAssessments();
                    List<Integer> assessmentIdsToRemove = initialAssessmentIds.stream()
                            .filter( assessmentId -> ! assessmentIds.contains(assessmentId))
                            .collect(Collectors.toList());

                    assessmentIdsToRemove.forEach(assessmentId -> {
                        mViewModel.deleteCourseAssessmentJoin(courseId, assessmentId);
                    });

                    List<Integer> termsToDisassociate = mViewModel.getAssociatedTermIds();
                    termsToDisassociate.forEach( termId -> {
                        mViewModel.deleteTermCourseJoin(termId, courseId);
                    });

                    List<Integer> mentorsToDisassociate = mViewModel.getAssociatedMentorIds();
                    mentorsToDisassociate.forEach( mentorId -> {
                        mViewModel.deleteCourseMentorJoin(courseId, mentorId);
                    });

                    mViewModel.setSelectedAssessments(new ArrayList<>());
                    mViewModel.setInitialSelectedAssessments(new ArrayList<>());
                    mViewModel.setAssociatedTerms(new ArrayList<>());
                    mViewModel.setAssociatedMentors(new ArrayList<>());

                    mViewModel.deleteCourseById(courseId);
                    startActivity(intent);
                }
            });
        });
    }

    private void onSubmit() {
        Executor executor = mViewModel.getExecutor();
        Intent intent = new Intent(this, MainActivity.class);

        TextView tvCourseName = findViewById(R.id.courseName);
        String courseName = tvCourseName.getText().toString();

        TextView tvNote = findViewById(R.id.note);
        String note = tvNote.getText().toString();

        TextView tvStartDate = findViewById(R.id.startDate);
        TextView tvEndDate = findViewById(R.id.endDate);

        Date startDate = DateHelper.toDate(tvStartDate.getText().toString());
        Date endDate = DateHelper.toDate(tvEndDate.getText().toString());

        RadioGroup radioGroup = findViewById(R.id.courseStatus);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radio = findViewById(selectedId);
        String selected = radio.getText().toString().toUpperCase();
        if ( selected.equals("IN PROGRESS") ){
            selected = "IN_PROGRESS";
        }
        CourseStatus status = CourseStatus.valueOf(selected);

        CheckBox alertStartDate = findViewById(R.id.alertStartDate);
        CheckBox alertEndDate = findViewById(R.id.alertEndDate);

        Log.i("START CHECKED", String.valueOf(alertStartDate.isChecked()));
        Log.i("END CHECKED", String.valueOf(alertEndDate.isChecked()));

        executor.execute(new Runnable() {
            @Override
            public void run() {
                CourseEntity course = new CourseEntity(courseId, courseName, startDate, endDate, status, note, alertStartDate.isChecked(), alertEndDate.isChecked());
                mViewModel.updateCourse(course);
                List<Integer> assessmentIds = mViewModel.getSelectedAssessmentIds();
                List<Integer> initialAssessmentIds = mViewModel.getInitialSelectedAssessments();

                List<Integer> assessmentIdsToAdd = assessmentIds.stream()
                    .filter( assessmentId -> ! initialAssessmentIds.contains(assessmentId))
                    .collect(Collectors.toList());

                List<Integer> assessmentIdsToRemove = initialAssessmentIds.stream()
                        .filter( assessmentId -> ! assessmentIds.contains(assessmentId))
                        .collect(Collectors.toList());

                assessmentIdsToAdd.forEach(assessmentId -> {
                    mViewModel.insertCourseAssessmentJoin(courseId, assessmentId);
                });

                assessmentIdsToRemove.forEach(assessmentId -> {
                    mViewModel.deleteCourseAssessmentJoin(courseId, assessmentId);
                });

                startActivity(intent);
            }
        });
    }

    private void initListeners() {
        Button updateCourse = findViewById(R.id.updateCourse);
        updateCourse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });
        Button deleteCourse = findViewById(R.id.deleteCourse);
        deleteCourse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onDelete();
            }
        });
        FloatingActionButton share = findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShare();
            }
        });
    }

    private void onShare(){
        TextView tvNote = findViewById(R.id.note);
        String note = tvNote.getText().toString();

        Intent intent = new Intent(); intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, note );
        startActivity(Intent.createChooser(intent, "Share via"));
    }

    private void initCourse() {
        Intent intent = getIntent();
        courseId = intent.getIntExtra(MainActivity.MAIN_ACTIVITY, 0);
        mViewModel.getCourse(courseId).observe(this, course -> {
            if(course == null) return;

            TextView textView = findViewById(R.id.courseName);
            textView.setText(course.getTitle());

            TextView note = findViewById(R.id.note);
            note.setText(course.getNote());

            TextView startDate = findViewById(R.id.startDate);
            String formattedStartDate = DateHelper.toFormattedString(course.getStartDate());
            startDate.setText(formattedStartDate);

            TextView endDate = findViewById(R.id.endDate);
            String formattedEndDate = DateHelper.toFormattedString(course.getEndDate());
            endDate.setText(formattedEndDate);

            CourseStatus status = course.getStatus();
            RadioButton button = null;
            switch ( status ){
                case DROPPED:
                    button = findViewById(R.id.dropped);
                    break;
                case PLANNED:
                    button = findViewById(R.id.planned);
                    break;
                case COMPLETED:
                    button = findViewById(R.id.completed);
                    break;
                case IN_PROGRESS:
                    button = findViewById(R.id.inprogress);
                    break;
            }
            button.setChecked(true);

            CheckBox alertStartDate = findViewById(R.id.alertStartDate);
            alertStartDate.setChecked(course.isStartDateAlert());

            CheckBox alertEndDate = findViewById(R.id.alertEndDate);
            alertEndDate.setChecked(course.isEndDateAlert());
        } );
    }

    private void initAssessments() {
        RecyclerView recyclerView = findViewById(R.id.assessmentCheckboxRV);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final AssessmentCheckboxRecyclerViewAdapter adapter = new AssessmentCheckboxRecyclerViewAdapter(this, mViewModel);
        mViewModel.getSelectedAssessmentsForCourse(courseId).observe(this, selectedAssessments -> {
            mViewModel.setInitialSelectedAssessments(selectedAssessments);
            mViewModel.setSelectedAssessments(selectedAssessments);
            mViewModel.getAllAssessments().observe(this, assessments -> adapter.setData(assessments));

            setDeleteButtonEnabledState();
        });
        adapter.setClickListener(new CourseClicker());
        recyclerView.setAdapter(adapter);
    }

    private void initTerms() {
        mViewModel.getTermsForCourse(courseId).observe( this, terms -> {
            mViewModel.setAssociatedTerms(terms);
        });
    }

    private void initMentors(){
        mViewModel.getMentorsForCourse(courseId).observe( this, mentors -> {
            mViewModel.setAssociatedMentors(mentors);
        });
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(ShowCourseViewModel.class);
    }

    public class CourseClicker implements AssessmentCheckboxRecyclerViewAdapter.ItemClickListener{
        @Override
        public void onItemClick() {
            onChange();
        }
    }
}
