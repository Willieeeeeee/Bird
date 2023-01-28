package edu.ucsd.cse110.team13.bof;

import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import edu.ucsd.cse110.team13.bof.util.AppDatabaseUtil;

public class SessionViewAdapter extends RecyclerView.Adapter<SessionViewAdapter.ViewHolder> {
    private final AppDatabaseUtil appDatabaseUtil;

    public SessionViewAdapter(AppDatabaseUtil appDatabaseUtil) {
        super();
        this.appDatabaseUtil = appDatabaseUtil;
    }

    /* Inflate with row_session_editable.xml and wrap it in a ViewHolder */
    @NonNull
    @Override
    public SessionViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.row_session_editable, parent, false);

        return new SessionViewAdapter.ViewHolder(view,appDatabaseUtil);
    }

    /* Connect profile data to a ViewHolder at a given position */
    @Override
    public void onBindViewHolder(@NonNull SessionViewAdapter.ViewHolder holder, int position) {
        holder.setSession(
                appDatabaseUtil.getAllSessions().get(position).sessionId,
                appDatabaseUtil.getAllSessions().get(position).sessionName);
    }

    /* Count number of stored profiles */
    @Override
    public int getItemCount() { return appDatabaseUtil.getAllSessions().size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // instance variables
        private final TextView sessionNameTextView;
        private int sessionId;

        public ViewHolder(@NonNull View itemView, AppDatabaseUtil appDatabaseUtil) {
            super(itemView);
            sessionNameTextView = itemView.findViewById(R.id.row_session_text);

            itemView.findViewById(R.id.row_session_edit_btn)
                    .setOnClickListener(view -> {
                        EditText sessionNameInput = new EditText(itemView.getContext());
                        sessionNameInput.setInputType(InputType.TYPE_CLASS_TEXT);
                        sessionNameInput.setHint(sessionNameTextView.getText().toString());

                        new AlertDialog.Builder(itemView.getContext())
                                .setTitle("Name this session as")
                                .setView(sessionNameInput)
                                .setPositiveButton("Rename", (dialogInterface, i) -> {
                                    String input = sessionNameInput.getText().toString().trim();
                                    if(input.isEmpty()) {
                                        new AlertDialog.Builder(itemView.getContext())
                                                .setTitle("Empty Session Name")
                                                .setMessage("Please enter a non-empty session name")
                                                .setNeutralButton("OK", (dialogInterface1, i1) -> { })
                                                .show();
                                        return;
                                    } else if(appDatabaseUtil.getSessionId(input) != -1) {
                                        new AlertDialog.Builder(itemView.getContext())
                                                .setTitle("Duplicate Session Name")
                                                .setMessage("There is already a session named " + input + ". Please try a different name.")
                                                .setNeutralButton("OK", (dialogInterface1, i1) -> { })
                                                .show();
                                        return;
                                    }
                                    appDatabaseUtil.renameSession(
                                            appDatabaseUtil.getRecentId(),
                                            sessionNameInput.getText().toString());
                                    setSession(sessionId,input);
                                })
                                .setNegativeButton("Cancel", (dialogInterface, i) -> { })
                                .show();
                    });

            itemView.setOnClickListener(this);
        }

        public void setSession(int sessionId, String sessionName) {
            this.sessionId = sessionId;
            sessionNameTextView.setText(sessionName);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent  = new Intent(context,ViewSessionActivity.class);
            intent.putExtra(ViewSessionActivity.CODE_SESSION_ID,sessionId);
            intent.putExtra(ViewSessionActivity.CODE_SESSION_NAME,sessionNameTextView.getText().toString());
            view.getContext().startActivity(intent);
        }
    }
}
