package edu.wgu.student.viewmodel;

import android.annotation.TargetApi;
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

@TargetApi(24)
public class ShowAssessmentViewModel extends AndroidViewModel {
    private AppRepository mRepo;
    private AppDatabase mDb;
    private Executor executor;

    public ShowAssessmentViewModel(@NonNull Application application) {
        super(application);

        mRepo = AppRepository.getInstance(application.getApplicationContext());
        mDb = mRepo.getDB();
        executor = mRepo.getExecutor();
    }

    public Executor getExecutor() {
        return executor;
    }

    public LiveData<AssessmentEntity> getAssessment(int id) {
        return mDb.assessmentDao().getAssessmentById(id);
    }
    public int updateAssessment(AssessmentEntity assessment) {
        return mDb.assessmentDao().updateAssessment(assessment);
    }
    public void deleteAssessmentById(int assessmentId ) { mDb.assessmentDao().deleteById(assessmentId);}

    public void deleteCourseAssessmentJoin(int courseId, int assessmentId) {
        mDb.courseAssessmentJoinDao().delete(courseId, assessmentId);
    }

    public List<CourseEntity> getCoursesForAssessment(int assessmentId) {
        return mDb.courseAssessmentJoinDao().getCoursesForAssessment(assessmentId);
    }
}
