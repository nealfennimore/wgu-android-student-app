package edu.wgu.student;

import android.app.Application;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.wgu.student.database.AppRepository;
import edu.wgu.student.ui.TermRecyclerViewAdapter;
import edu.wgu.student.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewModel();

        initViewRecycler();
    }

    private void initViewRecycler() {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.termRV);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final TermRecyclerViewAdapter adapter = new TermRecyclerViewAdapter(this);
        mViewModel.getAllTerms().observe(this, terms -> adapter.setData(terms));
//        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                    .get(MainViewModel.class);
    }
}
