package com.example.shinelon.lianqin.actionProviders;

import android.content.Context;
import android.net.sip.SipSession;
import android.support.v4.view.ActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.shinelon.lianqin.R;

/**
 * Created by Shinelon on 2017/12/9.
 */

public class messageActionProvider extends ActionProvider {
   private View v;
   private View.OnClickListener listener;
    public messageActionProvider(Context context){
        super(context);
    }
    @Override
    public View onCreateActionView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.message_provider,null,false);
        v = view;
        Log.d("Listener",""+ (listener==null));
        v.setOnClickListener(listener);
        return view;
    }

    public void setListener(providerClickListener clickListener){
        listener = clickListener;
    }

    public void hideCircle(){
        ImageView imageView = (ImageView) v.findViewById(R.id.red_circle);
        imageView.setVisibility(View.INVISIBLE);
    }

    public void showCircle(){
        ImageView imageView = (ImageView) v.findViewById(R.id.red_circle);
        imageView.setVisibility(View.VISIBLE);
    }

    interface providerClickListener extends View.OnClickListener{
    }
}
