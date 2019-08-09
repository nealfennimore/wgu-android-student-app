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
import edu.wgu.student.database.AssessmentEntity;
import edu.wgu.student.database.CourseAssessmentJoinEntity;;
import edu.wgu.student.database.CourseEntity;

@TargetApi(24)
public class ShowCourseViewModel extends AndroidViewModel {
    private AppRepository mRepo;
    private AppDatabase mDb;
    private Executor executor;

    private List<Integer> initialSelectedAssessments = new ArrayList<>();
    private List<AssessmentEntity> selectedAssessments = new ArrayList<>();

    public ShowCourseViewModel(@NonNull Application application) {
        super(application);

        mRepo = AppRepository.getInstance(application.getApplicationContext());
        mDb = mRepo.getDB();
        executor = mRepo.getExecutor();
    }

    public Executor getExecutor() {
        return executor;
    }

    public List<Integer> getInitialSelectedAssessments() {
        return initialSelectedAssessments;
    }
    public void setInitialSelectedAssessments(List<AssessmentEntity> selectedAssessments) {
        this.initialSelectedAssessments = selectedAssessments.stream()
                .map( assessment -> assessment.getId() )
                .collect(Collectors.toList());
    }

    public List<AssessmentEntity> getSelectedAssessments() {
        return selectedAssessments;
    }
    public void setSelectedAssessments(List<AssessmentEntity> selectedAssessments) {
        this.selectedAssessments = selectedAssessments;
    }
    public List<Integer> getSelectedAssessmentIds() {
        return selectedAssessments.stream()
                .map( assessment -> assessment.getId() )
                .collect(Collectors.toList());
    }

    public LiveData<CourseEntity> getCourse(int id) {
        return mDb.courseDao().getCourseById(id);
    }
    public int updateCourse(CourseEntity term) {
        return mDb.courseDao().updateCourse(term);
    }
    public void deleteCourseById(int courseId ) { mDb.courseDao().deleteById(courseId);}

    public LiveData<List<AssessmentEntity>> getAllAssessments() { return mDb.assessmentDao().getAll(); }
    public LiveData<List<AssessmentEntity>> getSelectedAssessmentsForCourse(int courseId) {
        return mDb.courseAssessmentJoinDao().getAssessmentsForCourse(courseId);
    }
    public void insertCourseAssessmentJoin(int courseId, int assessmentId) {
        mDb.courseAssessmentJoinDao().insert(new CourseAssessmentJoinEntity(courseId, assessmentId));
    }
    public void deleteCourseAssessmentJoin(int courseId, int assessmentId) {
        mDb.courseAssessmentJoinDao().delete(courseId, assessmentId);
    }
}
