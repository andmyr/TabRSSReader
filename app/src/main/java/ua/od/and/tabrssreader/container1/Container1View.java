package ua.od.and.tabrssreader.container1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prof.rssparser.Article;

import java.util.ArrayList;

import ua.od.and.tabrssreader.interfaces.Interfaces;
import ua.od.and.tabrssreader.R;

public class Container1View extends Fragment implements Interfaces.Container1ViewInterface {

    private OnListFragmentInteractionListener1 listener1;
    private RecyclerView recyclerView;
    private Container1RecyclerViewAdapter adapter;

    private Container1Presenter container1Presenter;

    public Container1View() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_container1_item, container, false);
        initRecyclerView(view);
        if (container1Presenter == null) {
            container1Presenter = new Container1Presenter(this, new Container1Model(), getActivity());
        }
        return view;
    }

    private void initRecyclerView(View view) {
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        listener1 = new OnListFragmentInteractionListener1() {
            @Override
            public void onListFragmentInteraction(Article article) {
                container1Presenter.setLabel(article);
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        container1Presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        container1Presenter.onPause();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        listener1 = null;
    }

    @Override
    public void onLoadData(ArrayList<Article> list) {
        adapter = new Container1RecyclerViewAdapter(list, listener1);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
    }

    public interface OnListFragmentInteractionListener1 {
        void onListFragmentInteraction(Article article);
    }
}
