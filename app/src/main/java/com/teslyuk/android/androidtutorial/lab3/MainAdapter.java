package com.teslyuk.android.androidtutorial.lab3;

import android.app.Activity;
import android.app.Dialog;
import android.provider.UserDictionary;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter  extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<UserData> dataList;
    private Activity context;
    private  RoomDB database;

    public MainAdapter(Activity context, List<UserData> dataList) {
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserData data = dataList.get(position);
        database = RoomDB.getInstance(context);

        holder.loginView.setText(data.getLogin());
        holder.passwordView.setText(data.getPassword());
        holder.tokenView.setText(data.getToken());
        holder.locationView.setText(data.getLocation());

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserData d = dataList.get(holder.getAdapterPosition());
                int sID = d.getID();

                String sLogin = d.getLogin();
                String sPassword = d.getPassword();
                String sToken = d.getToken();
                String sLocation = d.getLocation();

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);

                int width = WindowManager.LayoutParams.MATCH_PARENT;

                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width,height);
                dialog.show();

                EditText editLogin = dialog.findViewById(R.id.editLogin);
                EditText editPassword = dialog.findViewById(R.id.editPassword);
                EditText editToken = dialog.findViewById(R.id.editToken);
                EditText editLocation = dialog.findViewById(R.id.editLocation);

                Button updateButton = dialog.findViewById(R.id.updateButton);

                editLogin.setText(sLogin);
                editPassword.setText(sPassword);
                editToken.setText(sToken);
                editLocation.setText(sLocation);

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        String uLogin = editLogin.getText().toString().trim();
                        String uPassword = editPassword.getText().toString().trim();
                        String uToken = editToken.getText().toString().trim();
                        String uLocation = editLocation.getText().toString().trim();

                        database.userDao().update(sID,uLogin,uPassword,uToken,uLocation);
                        dataList.clear();
                        dataList.addAll(database.userDao().getAll());
                        notifyDataSetChanged();

                    }
                });
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserData d = dataList.get(holder.getAdapterPosition());
                database.userDao().delete(d);
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView loginView,passwordView,tokenView,locationView;
        ImageView editButton,deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            loginView = itemView.findViewById(R.id.loginView);
            passwordView = itemView.findViewById(R.id.passwordView);
            tokenView = itemView.findViewById(R.id.tokenView);
            locationView = itemView.findViewById(R.id.locationView);

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
