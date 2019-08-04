package edu.wgu.student.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;

import edu.wgu.student.database.AppDatabase;
import edu.wgu.student.database.AppRepository;
import edu.wgu.student.database.CourseEntity;
import edu.wgu.student.database.TermEntity;

public class ShowTermViewModel extends AndroidViewModel {
    private AppRepository mRepo;
    private AppDatabase mDb;
    private Executor executor;

    public ShowTermViewModel(@NonNull Application application) {
        super(application);

        mRepo = AppRepository.getInstance(application.getApplicationContext());
        mDb = mRepo.getDB();
        executor = mRepo.getExecutor();
    }

    public Executor getExecutor() {
        return executor;
    }

    public LiveData<TermEntity> getTerm(int id) {
        return mDb.termDao().getTermById(id);
    }

    public int updateTerm(TermEntity term) {
        return mDb.termDao().updateTerm(term);
    }

    public LiveData<List<CourseEntity>> getAllCourses() { return mDb.courseDao().getAll(); }
}
