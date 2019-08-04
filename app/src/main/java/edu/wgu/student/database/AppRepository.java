package edu.wgu.student.database;

import android.content.Context;
import android.util.Log;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
    }

    public AppDatabase getDB() {
        return mDb;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.i("SAMPLE_DATA", "Adding in sample data");
                MentorEntity mentor = new MentorEntity(1, "Neal", "99999999999", "cool@cool.com" );
                TermEntity term = new TermEntity(1, "First Term", new Date(), new Date());
                CourseEntity course = new CourseEntity(1, "Course 1", new Date(), new Date(), CourseStatus.IN_PROGRESS);
                CourseEntity course1 = new CourseEntity(2, "Course 2", new Date(), new Date(), CourseStatus.PLANNED);
                AssessmentEntity assessment = new AssessmentEntity(1, "Assessment", AssessmentType.OBJECTIVE, new Date());

                TermCourseJoinEntity termCourseJoin = new TermCourseJoinEntity(term.getId(), course.getId());
                CourseMentorJoinEntity courseMentorJoinEntity = new CourseMentorJoinEntity(course.getId(), mentor.getId());
                CourseAssessmentJoinEntity courseAssessmentJoinEntity = new CourseAssessmentJoinEntity(course.getId(), assessment.getId());


                mDb.mentorDao().insertMentor(mentor);
                mDb.termDao().insertTerm(term);
                mDb.courseDao().insertCourse(course);
                mDb.courseDao().insertCourse(course1);
                mDb.assessmentDao().insertAssessment(assessment);

                mDb.termCourseJoinDao().insert(termCourseJoin);
                mDb.courseMentorJoinDao().insert(courseMentorJoinEntity);
                mDb.courseAssessmentJoinDao().insert(courseAssessmentJoinEntity);

                Log.i("SAMPLE_DATA", "Done adding sample data");

                Log.i("MENTOR COUNT", Integer.toString(mDb.mentorDao().getCount()));
                Log.i("TERM COUNT", Integer.toString(mDb.termDao().getCount()));
                Log.i("COURSE COUNT", Integer.toString(mDb.courseDao().getCount()));
                Log.i("ASSESSMENT COUNT", Integer.toString(mDb.assessmentDao().getCount()));
            }
        });
    }
}
