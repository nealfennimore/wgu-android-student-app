package edu.wgu.student.database;

public class AppRepository {
    private static final AppRepository ourInstance = new AppRepository();

    public static AppRepository getInstance() {
        return ourInstance;
    }

    private AppRepository() {
    }
}
