//package edu.wgu.student.ui;
//
//import android.annotation.TargetApi;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import edu.wgu.student.R;
//import edu.wgu.student.database.AssessmentEntity;
//import edu.wgu.student.viewmodel.ShowTermViewModel;
//
//@TargetApi(24)
//public class AssessmentCheckboxRecyclerViewAdapter extends RecyclerView.Adapter<AssessmentCheckboxRecyclerViewAdapter.ViewHolder> {
//    private ShowTermViewModel model;
//    private List<AssessmentEntity> mData = new ArrayList<>();
//    private LayoutInflater mInflater;
//    private ItemClickListener mClickListener;
//
//    // data is passed into the constructor
//    public AssessmentCheckboxRecyclerViewAdapter(Context context, ShowTermViewModel model) {
//        this.mInflater = LayoutInflater.from(context);
//        this.model = model;
//    }
//
//    public void setData(List<AssessmentEntity> newData) {
//        this.mData = newData;
//        notifyDataSetChanged();
//    }
//
//    // inflates the row layout from xml when needed
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.assessment_checkbox_recyclerview_row, parent, false);
//        return new ViewHolder(view);
//    }
//
//    // binds the data to the TextView in each row
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        AssessmentEntity assessment = mData.get(position);
//        holder.checkboxView.setText(assessment.getTitle());
//        holder.checkboxView.setChecked(model.getInitialSelectedAssessments().contains(assessment.getId()));
//    }
//
//    // total number of rows
//    @Override
//    public int getItemCount() {
//        return mData.size();
//    }
//
//
//    // stores and recycles views as they are scrolled off screen
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        CheckBox checkboxView;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            checkboxView = itemView.findViewById(R.id.assessmentName);
//            checkboxView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            int position = getAdapterPosition();
//            AssessmentEntity assessment = getItem(position);
//            List<Integer> currentSelectedIds = getCurrentSelectedIds();
//
//            if( currentSelectedIds.contains(assessment.getId())){
//                model.getSelectedAssessments().removeIf(selected -> selected.getId() == assessment.getId());
//            } else {
//                model.getSelectedAssessments().add(assessment);
//            }
//
//            if (mClickListener != null) mClickListener.onItemClick();
//        }
//    }
//
//    public AssessmentEntity getItem(int id) {
//        return mData.get(id);
//    }
//    public List<Integer> getCurrentSelectedIds() {
//        return model.getSelectedAssessments().stream()
//                .map( assessment -> assessment.getId() )
//                .collect(Collectors.toList());
//    }
//
//
//    public void setClickListener(ItemClickListener mClickListener) {
//        this.mClickListener = mClickListener;
//    }
//
//    public interface ItemClickListener {
//        void onItemClick();
//    }
//}