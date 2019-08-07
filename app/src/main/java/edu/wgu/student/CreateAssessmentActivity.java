package edu.wgu.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.Executor;

import edu.wgu.student.database.AssessmentEntity;
import edu.wgu.student.database.AssessmentType;
import edu.wgu.student.utilities.DateHelper;
import edu.wgu.student.viewmodel.CreateAssessmentViewModel;

public class CreateAssessmentActivity extends AppCompatActivity {
    private CreateAssessmentViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assessment);

        initViewModel();
        initListeners();
    }

    private void initListeners() {
        Button button = findViewById(R.id.updateAssessment);
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
                int id = mViewModel.getAssessmentCount() + 1;
                AssessmentEntity assessment = new AssessmentEntity(id, title, type, dueDate);
                mViewModel.insertAssessment(assessment);
                startActivity(intent);
            }
        });
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(CreateAssessmentViewModel.class);
    }
}
