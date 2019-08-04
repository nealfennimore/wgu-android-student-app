package edu.wgu.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import edu.wgu.student.database.TermEntity;
import edu.wgu.student.viewmodel.MainViewModel;
import edu.wgu.student.viewmodel.ShowTermViewModel;

public class ShowTermActivity extends AppCompatActivity {
    private ShowTermViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_term);

        initViewModel();

        Intent intent = getIntent();
        int termId = intent.getIntExtra(MainActivity.MAIN_ACTIVITY, 0);
        mViewModel.getTerm(termId).observe(this, term -> {
            TextView textView = findViewById(R.id.termName);
            textView.setText(term.getTitle());

            TextView startDate = findViewById(R.id.startDate);
            startDate.setText(term.getStartDate().toString());

            TextView endDate = findViewById(R.id.endDate);
            endDate.setText(term.getEndDate().toString());
        } );
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(ShowTermViewModel.class);
    }
}
