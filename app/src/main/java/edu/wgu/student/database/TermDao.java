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
public interface TermDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(TermEntity term);

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TermEntity> terms);

    @Update
    int updateTerm(TermEntity term);

    @Delete
    void deleteTerm( TermEntity term );

    @Query("SELECT * FROM term WHERE id = :id")
    LiveData<TermEntity> getTermById(int id);

    @Query("SELECT * FROM term")
    LiveData<List<TermEntity>> getAll();

    @Query("DELETE FROM term")
    int deleteAll();

    @Query("DELETE FROM term WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT COUNT(*) FROM term")
    int getCount();
}
