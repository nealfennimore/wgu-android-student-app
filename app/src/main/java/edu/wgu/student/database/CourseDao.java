package edu.wgu.student.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(CourseEntity course);

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CourseEntity> courses);

    @Delete
    void deleteCourse( CourseEntity course );

    @Query("SELECT * FROM course WHERE id = :id")
    CourseEntity getCourseById(int id);

    @Query("SELECT * FROM course")
    LiveData<List<CourseEntity>> getAll();

    @Query("DELETE FROM course")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM course")
    int getCount();
}
