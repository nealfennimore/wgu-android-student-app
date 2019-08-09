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
public interface AssessmentDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(AssessmentEntity assessment);

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssessmentEntity> assessments);

    @Update
    int updateAssessment(AssessmentEntity assessment);

    @Delete
    void deleteAssessment( AssessmentEntity assessment );

    @Query("SELECT * FROM assessment WHERE id = :id")
    LiveData<AssessmentEntity> getAssessmentById(int id);

    @Query("SELECT * FROM assessment")
    LiveData<List<AssessmentEntity>> getAll();

    @Query("DELETE FROM assessment")
    int deleteAll();

    @Query("DELETE FROM assessment WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT COUNT(*) FROM assessment")
    int getCount();

    @Query("SELECT * FROM assessment "
            + "WHERE dueDateAlert = 1 "
            + "AND dueDate BETWEEN :from AND :to"
    )
    LiveData<List<AssessmentEntity>> getDueDateAlertsByDateRange(Long from, Long to );
}
