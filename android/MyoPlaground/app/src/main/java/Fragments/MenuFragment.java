package Fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nicolasarciniega.myoplaground.R;

import java.util.ArrayList;

import Adapters.MyoMenuItemAdapter;
import DataObjects.MyoMenuItem;

public class MenuFragment extends Fragment
{
    TextView txtTitle;
    ListView lstMenuList;

    public MenuFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        txtTitle = (TextView) view.findViewById(R.id.menu_title);
        lstMenuList = (ListView) view.findViewById(R.id.menu_list);

        // Find available menu items
        ArrayList<MyoMenuItem> menuList = new ArrayList<MyoMenuItem>();

        Resources res = getResources();
        String[] titles = res.getStringArray(R.array.menu_title_array);
        String[] descriptions = res.getStringArray(R.array.menu_description_array);

        for (int i = 0; i < titles.length; i++)
        {
            if (i >= descriptions.length)
            {
                Toast.makeText(getActivity().getBaseContext(), "Error in menu string array", Toast.LENGTH_LONG).show();
                break;
            }

            menuList.add(new MyoMenuItem(titles[i], descriptions[i]));
        }

        // Set up menu adapter
        MyoMenuItemAdapter adapter = new MyoMenuItemAdapter(getActivity().getBaseContext(), R.layout.btn_myo_menu_item, menuList);
        lstMenuList.setAdapter(adapter);

        lstMenuList.setOnItemClickListener(myoMenuClickHandler);
        lstMenuList.setItemsCanFocus(true);

        return view;
    }

    // Listeners
    private AdapterView.OnItemClickListener myoMenuClickHandler = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView adapterView, View view, int position, long id)
        {
            FragmentManager manager = getActivity().getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            switch (position)
            {
                case 0:
                    TextResponseFragment textResponseFragment = new TextResponseFragment();
                    transaction.replace(R.id.app_container, textResponseFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                case 1:
                    AudioControlFragment audioControlFragment = new AudioControlFragment();
                    transaction.replace(R.id.app_container, audioControlFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                default:
                    Toast.makeText(getActivity().getBaseContext(), "Bad Click", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
