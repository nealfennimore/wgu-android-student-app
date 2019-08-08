package edu.wgu.student;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.Executor;

import edu.wgu.student.database.CourseStatus;
import edu.wgu.student.database.CourseEntity;
import edu.wgu.student.utilities.DateHelper;
import edu.wgu.student.viewmodel.CreateCourseViewModel;

public class CreateCourseActivity extends AppCompatActivity {
    private CreateCourseViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        initViewModel();
        initListeners();
    }

    private void initListeners() {
        Button button = findViewById(R.id.updateCourse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    onSubmit();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void onSubmit() throws ParseException {
        Executor executor = mViewModel.getExecutor();
        Intent intent = new Intent(this, MainActivity.class);

        TextView tvTermName = findViewById(R.id.courseName);
        String courseName = tvTermName.getText().toString();

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

        executor.execute(new Runnable() {
            @Override
            public void run() {
                int id = mViewModel.getCourseCount() + 1;
                CourseEntity course = new CourseEntity(id, courseName, startDate, endDate, status);
                mViewModel.insertCourse(course);
                startActivity(intent);
            }
        });
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(CreateCourseViewModel.class);
    }
}
