package com.example.simplearapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplearapp.Model.ObjectItem;

import java.util.ArrayList;

public class ObjectListAdapter extends RecyclerView.Adapter<ObjectListAdapter.ViewHolder> {
    ArrayList<ObjectItem> arrayList = new ArrayList<>();

    public ObjectListAdapter(ArrayList<ObjectItem> arrayList) {
        this.arrayList = arrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_object);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = itemView.getContext();
                    alertItemSelect(context);

                }
            });
        }
    }

    @NonNull
    @Override
    public ObjectListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_object, null, false);
        ObjectListAdapter.ViewHolder viewHolder = new ObjectListAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ObjectListAdapter.ViewHolder holder, int position) {
        ObjectItem objectItem = arrayList.get(position);
//        Bitmap bitmap = BitmapFactory.decodeFile(objectItem.getPath());
        holder.imageView.setImageDrawable(objectItem.getImage());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void alertItemSelect(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Object Item Selection");
        builder.setMessage("Do you want to change Object Item?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
