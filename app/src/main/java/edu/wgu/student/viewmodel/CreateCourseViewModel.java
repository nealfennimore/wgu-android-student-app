package edu.wgu.student.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.concurrent.Executor;

import edu.wgu.student.database.AppDatabase;
import edu.wgu.student.database.AppRepository;
import edu.wgu.student.database.CourseEntity;
import edu.wgu.student.database.TermEntity;

public class CreateCourseViewModel extends AndroidViewModel {
    private AppRepository mRepo;
    private AppDatabase mDb;
    private Executor executor;

    public CreateCourseViewModel(@NonNull Application application) {
        super(application);

        mRepo = AppRepository.getInstance(application.getApplicationContext());
        mDb = mRepo.getDB();
        executor = mRepo.getExecutor();
    }

    public Executor getExecutor() {
        return executor;
    }

    public void insertCourse(CourseEntity course) {
        mDb.courseDao().insertCourse(course);
    }
    public int getCourseCount() { return mDb.courseDao().getCount(); }
}
