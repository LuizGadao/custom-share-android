package com.luizgadao.sharetextandimage;

import android.app.Activity;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by luizcarlos on 01/06/15.
 */
public class ShareIntentListAdapter extends ArrayAdapter {

    Activity context;
    List<ResolveInfo> items;
    int layoutId;

    public ShareIntentListAdapter(Activity context, int layoutId, List<ResolveInfo> items) {
        super(context, layoutId, items);

        this.context = context;
        this.items = items;
        this.layoutId = layoutId;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View row = inflater.inflate(layoutId, null);

        TextView label = (TextView) row.findViewById( R.id.tv_app_name);
        label.setText(items.get( pos ).activityInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
        ImageView image = (ImageView) row.findViewById(R.id.iv_icon_app);
        image.setImageDrawable( items.get( pos ).activityInfo.applicationInfo.loadIcon(context.getPackageManager()) );

        return row;
    }

}
