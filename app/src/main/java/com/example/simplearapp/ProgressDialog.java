package com.example.simplearapp;

import android.app.Dialog;
import android.content.Context;

public class ProgressDialog {
    private Dialog progressDialog;
    Context context;

    public ProgressDialog(Context context) {
        this.context = context;
        progressDialog = new Dialog(context);
    }

    public void showProgressDialog() {
        progressDialog.setContentView(R.layout.dialog_progress);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.color.colorTransparent));
        progressDialog.show();

    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

}
