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

import edu.wgu.student.database.TermEntity;
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
        initViewRecycler();
        initEventListeners();
    }

    private void initEventListeners() {
        FloatingActionButton button = findViewById(R.id.goToCreateTerm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddTermClick();
            }
        });
    }

    private void initViewRecycler() {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.termRV);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final TermRecyclerViewAdapter adapter = new TermRecyclerViewAdapter(this);
        mViewModel.getAllTerms().observe(this, terms -> adapter.setData(terms));
        adapter.setClickListener(new TermClicker());
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

    private void onTermClick(TermEntity term) {
        Intent intent = new Intent(this, ShowTermActivity.class);
        intent.putExtra(MAIN_ACTIVITY, term.getId());
        startActivity(intent);
    }

    public class TermClicker implements TermRecyclerViewAdapter.ItemClickListener{
        @Override
        public void onItemClick(View view, TermEntity term, int position) {
            onTermClick(term);
        }
    }
}
