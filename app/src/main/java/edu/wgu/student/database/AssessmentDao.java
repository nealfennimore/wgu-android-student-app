package edu.wgu.student.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AssessmentDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(AssessmentEntity assessment);

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssessmentEntity> assessments);

    @Delete
    void deleteAssessment( AssessmentEntity assessment );

    @Query("SELECT * FROM assessment WHERE id = :id")
    AssessmentEntity getAssessmentById(int id);

    @Query("SELECT * FROM assessment")
    LiveData<List<AssessmentEntity>> getAll();

    @Query("DELETE FROM assessment")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM assessment")
    int getCount();
}
