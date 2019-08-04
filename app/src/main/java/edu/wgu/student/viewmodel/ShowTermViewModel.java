package edu.wgu.student.viewmodel;

import android.annotation.TargetApi;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import edu.wgu.student.database.AppDatabase;
import edu.wgu.student.database.AppRepository;
import edu.wgu.student.database.CourseEntity;
import edu.wgu.student.database.TermCourseJoinEntity;
import edu.wgu.student.database.TermEntity;

@TargetApi(24)
public class ShowTermViewModel extends AndroidViewModel {
    private AppRepository mRepo;
    private AppDatabase mDb;
    private Executor executor;

    private List<Integer> initialSelectedCourses = new ArrayList<>();
    private List<CourseEntity> selectedCourses = new ArrayList<>();

    public ShowTermViewModel(@NonNull Application application) {
        super(application);

        mRepo = AppRepository.getInstance(application.getApplicationContext());
        mDb = mRepo.getDB();
        executor = mRepo.getExecutor();
    }

    public Executor getExecutor() {
        return executor;
    }

    public List<Integer> getInitialSelectedCourses() {
        return initialSelectedCourses;
    }
    public void setInitialSelectedCourses(List<CourseEntity> selectedCourses) {
        this.initialSelectedCourses = selectedCourses.stream()
                .map( course -> course.getId() )
                .collect(Collectors.toList());
    }

    public List<CourseEntity> getSelectedCourses() {
        return selectedCourses;
    }
    public void setSelectedCourses(List<CourseEntity> selectedCourses) {
        this.selectedCourses = selectedCourses;
    }
    public List<Integer> getSelectedCourseIds() {
        return selectedCourses.stream()
                .map( course -> course.getId() )
                .collect(Collectors.toList());
    }

    public LiveData<TermEntity> getTerm(int id) {
        return mDb.termDao().getTermById(id);
    }
    public int updateTerm(TermEntity term) {
        return mDb.termDao().updateTerm(term);
    }

    public LiveData<List<CourseEntity>> getAllCourses() { return mDb.courseDao().getAll(); }
    public LiveData<List<CourseEntity>> getSelectedCoursesForTerm(int termId) {
        return mDb.termCourseJoinDao().getCoursesForTerm(termId);
    }
    public void insertTermCourseJoin(int termId, int courseId) {
        mDb.termCourseJoinDao().insert(new TermCourseJoinEntity(termId, courseId));
    }
}
