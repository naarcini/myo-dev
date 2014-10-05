/*
 * Myo Application Development
 * Copyright (C) 2014  Nicolas Arciniega
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.nicolasarciniega.myoplaground.R;

import java.util.ArrayList;
import DataObjects.MyoMenuItem;

/**
 * Created by nick on 2014-10-04.
 */
public class MyoMenuItemAdapter extends ArrayAdapter<MyoMenuItem>
{
    public MyoMenuItemAdapter(Context context, int textViewResourceId)
    {
        super(context, textViewResourceId);
    }

    public MyoMenuItemAdapter(Context context, int resource, ArrayList<MyoMenuItem> items)
    {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;

        if (view == null)
        {
            LayoutInflater inflater;
            inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.btn_myo_menu_item, null);
        }

        MyoMenuItem myoMenuItem = getItem(position);

        if (myoMenuItem != null)
        {
            TextView title = (TextView) view.findViewById(R.id.btn_title);
            TextView description = (TextView) view.findViewById(R.id.btn_description);

            if (title != null)
            {
                title.setText(myoMenuItem.title);
            }

            if (description != null)
            {
                description.setText(myoMenuItem.description);
            }
        }

        return view;
    }
}