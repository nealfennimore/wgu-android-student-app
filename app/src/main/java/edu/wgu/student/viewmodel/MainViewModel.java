package edu.wgu.student.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;

import edu.wgu.student.database.AppDatabase;
import edu.wgu.student.database.AppRepository;
import edu.wgu.student.database.AssessmentEntity;
import edu.wgu.student.database.CourseEntity;
import edu.wgu.student.database.TermEntity;

public class MainViewModel extends AndroidViewModel {
    private AppRepository mRepo;
    private AppDatabase mDb;
    private Executor executor;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepo = AppRepository.getInstance(application.getApplicationContext());
        mDb = mRepo.getDB();
        executor = mRepo.getExecutor();

//        mRepo.removeAll();
//        mRepo.addSampleData();
    }


    public LiveData<List<TermEntity>> getAllTerms(){
        return mDb.termDao().getAll();
    }
    public LiveData<List<AssessmentEntity>> getAllAssessments(){
        return mDb.assessmentDao().getAll();
    }
    public LiveData<List<CourseEntity>> getAllCourses(){
        return mDb.courseDao().getAll();
    }

}
