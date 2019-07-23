package edu.wgu.student;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import edu.wgu.student.database.AppDatabase;
import edu.wgu.student.database.MentorDao;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private static final String TAG = "JUnit";
    private AppDatabase mDb;
    private MentorDao mDao;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mDao = mDb.mentorDao();
        Log.i(TAG, "createDb");
    }

    @After
    public void closeDb() {
        mDb.close();
        Log.i(TAG, "closeDb");
    }
}
