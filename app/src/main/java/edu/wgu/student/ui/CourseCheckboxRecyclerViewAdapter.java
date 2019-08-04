package edu.wgu.student.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.wgu.student.R;
import edu.wgu.student.database.CourseEntity;
import edu.wgu.student.viewmodel.ShowTermViewModel;

@TargetApi(24)
public class CourseCheckboxRecyclerViewAdapter extends RecyclerView.Adapter<CourseCheckboxRecyclerViewAdapter.ViewHolder> {
    private ShowTermViewModel model;
    private List<CourseEntity> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public CourseCheckboxRecyclerViewAdapter(Context context, ShowTermViewModel model) {
        this.mInflater = LayoutInflater.from(context);
        this.model = model;
    }

    public void setData(List<CourseEntity> newData) {
        this.mData = newData;
        notifyDataSetChanged();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.course_checkbox_recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CourseEntity course = mData.get(position);
        holder.checkboxView.setText(course.getTitle());
        holder.checkboxView.setChecked(model.getInitialSelectedCourses().contains(course.getId()));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox checkboxView;

        ViewHolder(View itemView) {
            super(itemView);
            checkboxView = itemView.findViewById(R.id.courseName);
            checkboxView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            CourseEntity course = getItem(position);
            List<Integer> currentSelectedIds = getCurrentSelectedIds();

            if( currentSelectedIds.contains(course.getId())){
                model.getSelectedCourses().removeIf(selected -> selected.getId() == course.getId());
            } else {
                model.getSelectedCourses().add(course);
            }

            if (mClickListener != null) mClickListener.onItemClick();
        }
    }

    public CourseEntity getItem(int id) {
        return mData.get(id);
    }
    public List<Integer> getCurrentSelectedIds() {
        return model.getSelectedCourses().stream()
                .map( course -> course.getId() )
                .collect(Collectors.toList());
    }


    public void setClickListener(ItemClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public interface ItemClickListener {
        void onItemClick();
    }
}