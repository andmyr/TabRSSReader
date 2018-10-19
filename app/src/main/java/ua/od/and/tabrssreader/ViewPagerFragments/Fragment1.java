package ua.od.and.tabrssreader.ViewPagerFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ua.od.and.tabrssreader.R;

public class Fragment1 extends Fragment {

    private TextView tvNewsTitle;

    public Fragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pager_fragment_1, container, false);
        tvNewsTitle = view.findViewById(R.id.tvNewsTitle);

        return view;
    }

    public void setLabel(String label) {
        tvNewsTitle.setText(label);
    }
}