package edu.ucsd.cse110.team13.bof;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ucsd.cse110.team13.bof.model.IClass;

public class ClassViewAdapter extends RecyclerView.Adapter<ClassViewAdapter.ViewHolder> {
    private final List<IClass> classes;

    public ClassViewAdapter(List<IClass> classes) {
        super();
        this.classes = classes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.class_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() { return this.classes.size(); }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setClass(classes.get(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView classTextView;
        private IClass c;

        ViewHolder(View itemView) {
            super(itemView);
            this.classTextView = itemView.findViewById(R.id.class_row_text);
        }

        public void setClass(IClass c) {
            this.c = c;
            this.classTextView.setText(c.getYear() + " " +
                    c.getQuarter().getQuarterName() + " " +
                    c.getSubject() + " " +
                    c.getCourseNumber());
        }
    }
}
