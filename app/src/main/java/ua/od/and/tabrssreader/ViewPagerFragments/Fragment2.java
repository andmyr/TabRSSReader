package ua.od.and.tabrssreader.ViewPagerFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import ua.od.and.tabrssreader.R;
import ua.od.and.tabrssreader.container1.Container1View;
import ua.od.and.tabrssreader.container2.Container2View;

public class Fragment2 extends Fragment implements View.OnClickListener {
    private Container1View containerFragment1;
    private Container2View containerFragment2;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pager_fragment_2, container, false);
       /* TextView textView = view.findViewById(R.id.detailsText2);
        textView.setText("Test 2");*/
        Button button1 = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);
        progressBar = view.findViewById(R.id.progressBar);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        if (containerFragment1 == null) {
            containerFragment1 = new Container1View();
        }
        ft.replace(R.id.container, containerFragment1, "containerFragment1");
        ft.commit();
        //fragmentManager.executePendingTransactions();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();

                if (containerFragment1 == null) {
                    containerFragment1 = new Container1View();
                }
                ft.replace(R.id.container, containerFragment1, "containerFragment1");
                ft.commit();
                break;

            case R.id.button2:
                fragmentManager = getFragmentManager();
                ft = fragmentManager.beginTransaction();

                if (containerFragment2 == null) {
                    containerFragment2 = new Container2View();
                }
                ft.replace(R.id.container, containerFragment2, "containerFragment2");
                ft.commit();
                break;
        }

    }

    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }
}