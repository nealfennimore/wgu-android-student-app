package edu.wgu.student.viewmodel;

import android.annotation.TargetApi;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import edu.wgu.student.database.AppDatabase;
import edu.wgu.student.database.AppRepository;
import edu.wgu.student.database.AssessmentEntity;
import edu.wgu.student.database.CourseEntity;
import edu.wgu.student.database.DateConverter;
import edu.wgu.student.database.TermEntity;
import edu.wgu.student.utilities.DateHelper;

@TargetApi(26)
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
    public LiveData<List<CourseEntity>> getCoursesWithAlertsForToday() {
        LocalDateTime startOfDay = LocalDate.now().atTime(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        Date start = Date.from( startOfDay.atZone( ZoneId.systemDefault()).toInstant());
        Date end = Date.from( endOfDay.atZone( ZoneId.systemDefault()).toInstant());

        Long from = DateConverter.toTimestamp(start);
        Long to = DateConverter.toTimestamp(end);

        return mDb.courseDao().getActiveCourseAlertsByDateRange(from, to);
    }
}
