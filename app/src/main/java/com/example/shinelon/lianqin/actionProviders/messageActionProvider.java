package com.example.shinelon.lianqin.actionProviders;

import android.content.Context;
import android.net.sip.SipSession;
import android.support.v4.view.ActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.shinelon.lianqin.R;
import com.example.shinelon.lianqin.listener.ActionProviderListener;

/**
 * Created by  HB on 2017/12/9.
 */

public class messageActionProvider extends ActionProvider {
   private View v;
   private View.OnClickListener listener;
   public boolean hasMessage = true;
   public messageActionProvider(Context context){
        super(context);
    }
    @Override
    public View onCreateActionView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.message_provider,null,false);
        v = view;
        Log.d("Listener",""+ (listener==null));
        v.setOnClickListener(listener);
        if (hasMessage){
            showCircle(v);
        }
        return view;
    }

    public void setListener(ActionProviderListener clickListener){
        listener = clickListener;
    }

    public void hideCircle(){
        hasMessage = false;
        ImageView imageView = (ImageView) v.findViewById(R.id.red_circle);
        imageView.setVisibility(View.INVISIBLE);
    }

    public void showCircle(View v){
        hasMessage = true;
        ImageView imageView = (ImageView) v.findViewById(R.id.red_circle);
        imageView.setVisibility(View.VISIBLE);
    }


}
