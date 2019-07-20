package edu.wgu.student.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MentorDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertMentor(MentorEntity mentor);

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MentorEntity> mentors);

    @Delete
    void deleteMentor( MentorEntity mentor );

    @Query("SELECT * FROM mentor WHERE id = :id")
    MentorEntity getMentorById(int id);

    @Query("SELECT * FROM mentor")
    LiveData<List<MentorEntity>> getAll();

    @Query("DELETE FROM mentor")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM mentor")
    int getCount();
}
