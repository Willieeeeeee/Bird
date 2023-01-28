package edu.ucsd.cse110.team13.bof;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.team13.bof.model.IClass;
import edu.ucsd.cse110.team13.bof.model.IProfile;
import edu.ucsd.cse110.team13.bof.model.database.ProfileWithCourses;
import edu.ucsd.cse110.team13.bof.util.AppDatabaseUtil;
import edu.ucsd.cse110.team13.bof.util.UserProfileUtil;

public class StudentsViewAdapter extends RecyclerView.Adapter<StudentsViewAdapter.ViewHolder>{
    public static final int SORT_BY_COMMON = 0,
                            SORT_BY_RECENT = 1,
                            SORT_BY_SIZE   = 2,
                            SORT_BY_FILTER = 3;

    private UserProfileUtil userProfileUtil;
    private AppDatabaseUtil appDatabaseUtil;
    private int sessionId;
    private final boolean isActive, isViewingFavorite;

    private int sortStrategy = SORT_BY_COMMON;

    private List<ProfileWithCourses> students;
    private ProfileWithCourses user;

    public ArrayList<String> wavingUids;

    /* Constructor: display incoming students */
    public StudentsViewAdapter(UserProfileUtil userProfileUtil, AppDatabaseUtil appDatabaseUtil, int sessionId, boolean isActive, boolean isViewingFavorite) {
        super();

        this.userProfileUtil = userProfileUtil;
        this.appDatabaseUtil = appDatabaseUtil;
        this.sessionId = sessionId;
        this.isActive  = !isViewingFavorite || isActive;  // view favorite means inactive
        this.isViewingFavorite = isViewingFavorite;
        wavingUids = new ArrayList<>();
        refresh();
    }

    /* Inflate with students_row.xml and wrap it in a ViewHolder */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.student_row, parent, false);

        return new ViewHolder(view,userProfileUtil,isActive);
    }

    /* Connect profile data to a ViewHolder at a given position */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setStudent(
                students.get(position),
                wavingUids.contains(students.get(position).getUid()));

        String url = students.get(position).getHeadshotUrl();
        if(holder.studentImageView != null && holder.studentImageView.getContext() != null) {
            Glide.with(holder.studentImageView.getContext())
                    .load(url)
                    .circleCrop()
                    .placeholder(R.drawable.ic_default_headshot_50)
                    .fallback(R.drawable.ic_default_headshot_50)
                    .into(holder.studentImageView);
        }
    }

    /* Sorting Methods */
    public void setSortStrategy(int strategy) { sortStrategy = strategy; refresh(); }

    public void refresh() {
        user = userProfileUtil.getUserProfile();
        if(isViewingFavorite) {
            students = appDatabaseUtil.getFavorites();
        } else {
            students = appDatabaseUtil.hasSession(sessionId) ?
                    appDatabaseUtil.getProfilesInSession(sessionId) :
                    new ArrayList<>();
        }

        switch(sortStrategy) {
            case SORT_BY_COMMON:
                students.sort((a,b) -> {
                    if(wavingUids.contains(a.getUid()) != wavingUids.contains(b.getUid())) {
                        return wavingUids.contains(a.getUid()) ? -1 : 1;
                    }
                    return user.getCommonClasses(b).size() - user.getCommonClasses(a).size();
                });
                break;
            case SORT_BY_RECENT:
                students.sort((a,b) -> {
                    if(wavingUids.contains(a.getUid()) != wavingUids.contains(b.getUid())) {
                        return wavingUids.contains(a.getUid()) ? -1 : 1;
                    }
                    return user.getMostRecentScore(b) - user.getMostRecentScore(a);
                });
                break;
            case SORT_BY_SIZE:
                students.sort((a,b) -> {
                    if(wavingUids.contains(a.getUid()) != wavingUids.contains(b.getUid())) {
                        return wavingUids.contains(a.getUid()) ? -1 : 1;
                    }
                    float aScore = 0f, bScore = 0f;
                    for(IClass c : a.getClasses()) { aScore += c.getSize().getSizeWeight(); }
                    for(IClass c : b.getClasses()) { bScore += c.getSize().getSizeWeight(); }
                    if(aScore == bScore) { return 0; }
                    return (aScore > bScore) ? -1 : 1;
                });
            case SORT_BY_FILTER:
                // TODO:
                break;
            default:
        }

        this.notifyDataSetChanged();
    }

    /* displays a waving hand by the student corresponding to the student_id
    *  moves the student into the "waved" section at the top of the list */
    public void receiveWaveFrom(String uid) {
        /* Maintain order of list with waving students at top  */
        wavingUids.add(uid);
        refresh();
    }

    /* Count number of stored profiles */
    @Override
    public int getItemCount() { return this.students.size(); }

    public static class ViewHolder extends
            RecyclerView.ViewHolder
            implements View.OnClickListener {
        // instance variables
        private final TextView  studentNameView,
                                studentCountView;
        public ImageView        studentImageView, studentWavingView, filledStarView;
        private ProfileWithCourses student;
        private UserProfileUtil userProfileUtil;
        private boolean isActive;

        public ViewHolder(@NonNull View itemView, UserProfileUtil userProfileUtil, boolean isActive) {
            super(itemView);
            this.userProfileUtil    = userProfileUtil;
            this.isActive           = isActive;
            this.studentNameView    = (TextView)  itemView.findViewById(R.id.student_row_name);
            this.studentImageView   = (ImageView) itemView.findViewById(R.id.student_row_headshot);
            this.studentCountView   = (TextView)  itemView.findViewById(R.id.student_row_common_class_count);
            this.studentWavingView  = (ImageView) itemView.findViewById(R.id.wavingHand);
            this.filledStarView     = (ImageView) itemView.findViewById(R.id.filled_star);

            itemView.setOnClickListener(this);
        }

        public void setStudent(ProfileWithCourses student, boolean isWaving) {
            this.student = student;
            this.studentNameView.setText(student.getFirstName());
            this.studentCountView.setText(String.valueOf(userProfileUtil.getUserProfile().getCommonClasses(student).size()));
            this.studentWavingView.setVisibility(isWaving ? View.VISIBLE : View.INVISIBLE);
            this.filledStarView.setVisibility(student.isFavorite() ? View.VISIBLE : View.INVISIBLE);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent  intent  = new Intent(context, StudentDetailActivity.class);
            // sends profile uid to StudentDetailActivity
            intent.putExtra(StudentDetailActivity.UID_CODE,student.getUid());
            intent.putExtra(StudentDetailActivity.IS_ACTIVE_CODE,isActive);
            ((Activity)context).startActivityForResult(intent, MainActivity.CODE_FAVORITE_UPDATE); // allows StudentDetailActivity to return data
        }
    }
}
