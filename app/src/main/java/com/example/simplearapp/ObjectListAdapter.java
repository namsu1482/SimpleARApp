package com.example.simplearapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplearapp.Model.ObjectItem;

import java.util.ArrayList;

public class ObjectListAdapter extends RecyclerView.Adapter<ObjectListAdapter.ViewHolder> {
    private static final String TAG = ObjectListAdapter.class.getSimpleName();
    ArrayList<ObjectItem> arrayList;

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
                    int position = getAbsoluteAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ObjectItem objectItem = arrayList.get(position);
                        Context context = itemView.getContext();

                        alertItemSelect(context, objectItem.getObject_type());
                    }

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

//        holder.imageView.setImageDrawable(objectItem.getImage());
        holder.imageView.setImageResource(objectItem.getImgId());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void alertItemSelect(Context context, ArObjectHelper.OBJECT_TYPE object_type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Object Item Selection");
        builder.setMessage("Do you want to change Object Item?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
//                Log.i(TAG, object_type.name());
                intent.putExtra("object_type", object_type);
                Activity activity = (Activity) context;
                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
            }
        });
        builder.show();
    }
}
