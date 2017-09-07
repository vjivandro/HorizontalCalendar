package com.gaodun.horizontalcalendar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sniper on 2017/9/7.
 */

public class CalendarAdapter extends SetBaseAdapter
{

    CalendarAdapter(Context ctx)
    {
        super(ctx);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if (convertView == null)
        {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_glive_calendar, null);
            holder = new ViewHolder();
            holder.weekName = convertView.findViewById(R.id.weekName);
            holder.tvWeekIndex = convertView.findViewById(R.id.weekIndex);
            holder.point = convertView.findViewById(R.id.point);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        CalendarData data = (CalendarData) datas.get(position);
        holder.weekName.setText(data.getWeekDayName());
        holder.tvWeekIndex.setText(String.valueOf(position + 1));
        if (data.isSelected())
        {
            holder.tvWeekIndex.setTextColor(0XFFFFFFFF);
            changeViewBgcolor(holder.tvWeekIndex, 0xFF4ECB18);
        } else
        {
            holder.tvWeekIndex.setTextColor(0XFF626B7B);
            changeViewBgcolor(holder.tvWeekIndex, 0xFFFFFFFF);
        }

        holder.point.setVisibility(data.getData() != null ? View.VISIBLE : View.GONE);
        changeViewBgcolor(holder.point, 0xFF000000);

        return convertView;
    }

    /**
     * 修改背景颜色
     */
    public final void changeViewBgcolor(View v, int color)
    {
        Drawable drawable = v.getBackground();
        GradientDrawable d = (GradientDrawable) drawable;
        d.setColor(color);
    }

    public class ViewHolder
    {
        public TextView weekName, tvWeekIndex;
        public View point;
    }
}
