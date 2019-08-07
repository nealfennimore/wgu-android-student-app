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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import edu.wgu.student.database.AssessmentType;
import edu.wgu.student.database.CourseEntity;
import edu.wgu.student.database.AssessmentEntity;
import edu.wgu.student.ui.CourseCheckboxRecyclerViewAdapter;
import edu.wgu.student.ui.TermRecyclerViewAdapter;
import edu.wgu.student.utilities.DateHelper;
import edu.wgu.student.viewmodel.MainViewModel;
import edu.wgu.student.viewmodel.ShowAssessmentViewModel;
@TargetApi(24)
public class ShowAssessmentActivity extends AppCompatActivity {
    private ShowAssessmentViewModel mViewModel;
    private int assessmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_assessment);

        initViewModel();
        initAssessment();
        initListeners();
    }

    private void onDelete() {
        Executor executor = mViewModel.getExecutor();
        Intent intent = new Intent(this, MainActivity.class);
        mViewModel.getAssessment(assessmentId).observe( this, assessment -> {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    List<CourseEntity> courses = mViewModel.getCoursesForAssessment(assessmentId);
                    List<Integer> courseIds = courses.stream()
                            .map( course -> course.getId() )
                            .collect(Collectors.toList());

                    courseIds.forEach(courseId -> {
                        mViewModel.deleteCourseAssessmentJoin(courseId, assessmentId);
                    });

                    mViewModel.deleteAssessmentById(assessmentId);
                    startActivity(intent);
                }
            });
        });
    }

    private void onSubmit() {
        Executor executor = mViewModel.getExecutor();
        Intent intent = new Intent(this, MainActivity.class);

        TextView tvTitle = findViewById(R.id.title);
        String title = tvTitle.getText().toString();

        RadioGroup radioGroup = findViewById(R.id.assessmentType);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radio = findViewById(selectedId);
        String selected = radio.getText().toString().toUpperCase();
        AssessmentType type = AssessmentType.valueOf(selected);

        TextView tvDueDate = findViewById(R.id.dueDate);
        Date dueDate = DateHelper.toDate(tvDueDate.getText().toString());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                AssessmentEntity assessment = new AssessmentEntity(assessmentId, title, type, dueDate);
                mViewModel.updateAssessment(assessment);

                startActivity(intent);
            }
        });
    }

    private void initListeners() {
        Button updateTerm = findViewById(R.id.updateAssessment);
        updateTerm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });
        Button deleteTerm = findViewById(R.id.deleteAssessment);
        deleteTerm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onDelete();
            }
        });
    }

    private void initAssessment() {
        Intent intent = getIntent();
        assessmentId = intent.getIntExtra(MainActivity.MAIN_ACTIVITY, 0);
        mViewModel.getAssessment(assessmentId).observe(this, assessment -> {
            if(assessment == null) return;

            TextView tvTitle = findViewById(R.id.title);
            tvTitle.setText(assessment.getTitle());

            TextView dueDate = findViewById(R.id.dueDate);
            String formattedStartDate = DateHelper.toFormattedString(assessment.getDueDate());
            dueDate.setText(formattedStartDate);

            AssessmentType type = assessment.getType();
            RadioButton button = null;
            if (type == AssessmentType.OBJECTIVE ){
                button = findViewById(R.id.objective);
            } else {
                button = findViewById(R.id.performance);
            }
            button.setChecked(true);
        } );
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(ShowAssessmentViewModel.class);
    }
}
