package edu.wgu.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.Executor;

import edu.wgu.student.database.TermEntity;
import edu.wgu.student.utilities.DateHelper;
import edu.wgu.student.viewmodel.CreateTermViewModel;

public class CreateTermActivity extends AppCompatActivity {
    private CreateTermViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_term);

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

        TextView tvTermName = findViewById(R.id.termName);
        String termName = tvTermName.getText().toString();

        TextView tvStartDate = findViewById(R.id.startDate);
        TextView tvEndDate = findViewById(R.id.endDate);

        Date startDate = DateHelper.toDate(tvStartDate.getText().toString());
        Date endDate = DateHelper.toDate(tvEndDate.getText().toString());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                int id = mViewModel.getTermCount() + 1;
                TermEntity term = new TermEntity(id, termName, startDate, endDate);
                mViewModel.insertTerm(term);
                startActivity(intent);
            }
        });
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(CreateTermViewModel.class);
    }
}
