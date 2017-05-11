package org.erickson_foundation.miltonhericksonfoundation.HelperClasses;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by User on 5/10/2017.
 */

public class MHEFProgressDialog {
    private ProgressDialog mProgressDialog;
    private String message;
    private Context ctx;

    public MHEFProgressDialog(MHEFProgressDialog.Builder builder){
        this.ctx = builder.ctx;
        this.message = builder.message;
        mProgressDialog = new ProgressDialog(ctx);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(message);
        mProgressDialog.setIndeterminate(builder.isIndeterminate);
        mProgressDialog.setCancelable(builder.isCancelable);
    }
    public void show(){
        mProgressDialog.show();
    }
    public boolean isShowing(){
        return mProgressDialog.isShowing();
    }
    public void hide(){
        mProgressDialog.hide();
    }
    public void setCancelable(boolean cancel){
        mProgressDialog.setCancelable(cancel);
    }
    public void setIndeterminate(boolean i){
        mProgressDialog.setIndeterminate(i);
    }
    public static class Builder{
        private String message;
        private Context ctx;
        private boolean isIndeterminate, isCancelable;
        public Builder message(String m){
            this.message = m;
            return this;
        }
        public Builder context(Context context){
            this.ctx = context;
            return this;
        }
        public Builder indeterminate(boolean i){
            this.isIndeterminate = i;
            return this;
        }
        public Builder cancelable(boolean cancel){
            this.isCancelable = cancel;
            return this;
        }
        public MHEFProgressDialog build(){
            return new MHEFProgressDialog(this);
        }
    }
}

