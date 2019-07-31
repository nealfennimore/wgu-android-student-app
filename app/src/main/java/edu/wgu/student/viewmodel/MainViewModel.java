package edu.wgu.student.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import edu.wgu.student.database.AppRepository;

public class MainViewModel extends AndroidViewModel {
    private AppRepository mRepo;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepo = AppRepository.getInstance(application.getApplicationContext());
        mRepo.addSampleData();
    }
}
