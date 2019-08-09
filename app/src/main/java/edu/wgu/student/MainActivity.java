package edu.wgu.student;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.wgu.student.database.AssessmentEntity;
import edu.wgu.student.database.CourseEntity;
import edu.wgu.student.database.TermEntity;
import edu.wgu.student.ui.AssessmentRecyclerViewAdapter;
import edu.wgu.student.ui.CourseRecyclerViewAdapter;
import edu.wgu.student.ui.TermRecyclerViewAdapter;
import edu.wgu.student.viewmodel.MainViewModel;

@TargetApi(24)
public class MainActivity extends AppCompatActivity {
    public static final String MAIN_ACTIVITY = "edu.wgu.student.MAIN_ACTIVITY";

    private MainViewModel mViewModel;
    private NotificationManager mNotificationManager;
    private static String DEFAULT_CHANNEL_ID = "default_channel";
    private static String DEFAULT_CHANNEL_NAME = "Default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewModel();
        initTermViewRecycler();
        initCourseViewRecycler();
        initAssessmentViewRecycler();
        initEventListeners();

//        initNotificationListeners();

        initAlerts();
    }

    private void initAlerts() {
        mViewModel.getCoursesWithAlertsForToday().observe( this, courses -> {
            courses.forEach( courseEntity -> {
                if ( ! courseEntity.isStartDateAlert() && ! courseEntity.isEndDateAlert() ){
                    return;
                }

                String happening = courseEntity.isStartDateAlert() ? "starting" : "ending";
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle(courseEntity.getTitle());
                alertDialog.setMessage(courseEntity.getTitle() + " is " + happening + " today." );
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            });
        });

        mViewModel.getAssessmentsWithAlertsForToday().observe( this, assessments -> {
            assessments.forEach( assessmentEntity -> {
                if ( ! assessmentEntity.isDueDateAlert() ){
                    return;
                }

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle(assessmentEntity.getTitle());
                alertDialog.setMessage(assessmentEntity.getTitle() + " is due today." );
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            });
        });

    }

    private void initNotificationListeners() {
        //1.Get reference to Notification Manager
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel(mNotificationManager);

        //2.Build Notification with NotificationCompat.Builder
        Notification notification = new NotificationCompat.Builder(this, DEFAULT_CHANNEL_ID)
                .setContentTitle("Simple Notification")   //Set the title of Notification
                .setContentText("I am a boring notification.")    //Set the text for notification
                .setSmallIcon(android.R.drawable.ic_menu_view).build();

        //Send the notification.
        mNotificationManager.notify(1, notification);
    }

    /*
     * Create NotificationChannel as required from Android 8.0 (Oreo)
     * */
    public static void createNotificationChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(DEFAULT_CHANNEL_ID) == null) {
                notificationManager.createNotificationChannel(
                        new NotificationChannel(DEFAULT_CHANNEL_ID, DEFAULT_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
                );
            }
        }
    }

    private void initEventListeners() {
        Button goToCreateTerm = findViewById(R.id.goToCreateTerm);
        goToCreateTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddTermClick();
            }
        });

        Button goToCreateAssessment = findViewById(R.id.goToCreateAssessment);
        goToCreateAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddAssessmentClick();
            }
        });

        Button goToCreateCourse = findViewById(R.id.goToCreateCourse);
        goToCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddCourseClick();
            }
        });
    }

    private void initTermViewRecycler() {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.termRV);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final TermRecyclerViewAdapter adapter = new TermRecyclerViewAdapter(this);
        mViewModel.getAllTerms().observe(this, terms -> adapter.setData(terms));
        adapter.setClickListener(new TermClicker());
        recyclerView.setAdapter(adapter);
    }

    private void initCourseViewRecycler() {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.courseRV);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CourseRecyclerViewAdapter adapter = new CourseRecyclerViewAdapter(this);
        mViewModel.getAllCourses().observe(this, courses -> adapter.setData(courses));
        adapter.setClickListener(new CourseClicker());
        recyclerView.setAdapter(adapter);
    }

    private void initAssessmentViewRecycler() {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.assessmentRV);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final AssessmentRecyclerViewAdapter adapter = new AssessmentRecyclerViewAdapter(this);
        mViewModel.getAllAssessments().observe(this, assessments -> adapter.setData(assessments));
        adapter.setClickListener(new AssessmentClicker());
        recyclerView.setAdapter(adapter);
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                    .get(MainViewModel.class);
    }

    private void onAddTermClick(){
        Intent intent = new Intent(this, CreateTermActivity.class);
        startActivity(intent);
    }

    private void onAddCourseClick(){
        Intent intent = new Intent(this, CreateCourseActivity.class);
        startActivity(intent);
    }

    private void onAddAssessmentClick(){
        Intent intent = new Intent(this, CreateAssessmentActivity.class);
        startActivity(intent);
    }

    private void onTermClick(TermEntity term) {
        Intent intent = new Intent(this, ShowTermActivity.class);
        intent.putExtra(MAIN_ACTIVITY, term.getId());
        startActivity(intent);
    }

    private void onCourseClick(CourseEntity course) {
        Intent intent = new Intent(this, ShowCourseActivity.class);
        intent.putExtra(MAIN_ACTIVITY, course.getId());
        startActivity(intent);
    }

    private void onAssessmentClick(AssessmentEntity assessment) {
        Intent intent = new Intent(this, ShowAssessmentActivity.class);
        intent.putExtra(MAIN_ACTIVITY, assessment.getId());
        startActivity(intent);
    }

    public class TermClicker implements TermRecyclerViewAdapter.ItemClickListener{
        @Override
        public void onItemClick(View view, TermEntity term, int position) {
            onTermClick(term);
        }
    }

    public class CourseClicker implements CourseRecyclerViewAdapter.ItemClickListener{
        @Override
        public void onItemClick(View view, CourseEntity course, int position) {
            onCourseClick(course);
        }
    }

    public class AssessmentClicker implements AssessmentRecyclerViewAdapter.ItemClickListener{
        @Override
        public void onItemClick(View view, AssessmentEntity assessment, int position) {
            onAssessmentClick(assessment);
        }
    }
}
