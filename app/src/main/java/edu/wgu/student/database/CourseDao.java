package edu.wgu.student.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(CourseEntity course);

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CourseEntity> courses);

    @Update
    int updateCourse(CourseEntity course);

    @Delete
    void deleteCourse( CourseEntity course );

    @Query("SELECT * FROM course WHERE id = :id")
    LiveData<CourseEntity> getCourseById(int id);

    @Query("SELECT * FROM course")
    LiveData<List<CourseEntity>> getAll();

    @Query("DELETE FROM course")
    int deleteAll();

    @Query("DELETE FROM course WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT COUNT(*) FROM course")
    int getCount();

    @Query("SELECT * FROM course "
            + "WHERE ( startDateAlert = 1 OR endDateAlert = 1 )"
            + "AND ( startDate BETWEEN date(:from) AND date(:to)"
            + "OR endDate BETWEEN date(:from) AND date(:to) )"
    )
    LiveData<List<CourseEntity>> getActiveCourseAlertsByDateRange(String from, String to );
}
