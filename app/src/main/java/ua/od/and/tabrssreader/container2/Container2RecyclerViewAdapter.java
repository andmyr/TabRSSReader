package ua.od.and.tabrssreader.container2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prof.rssparser.Article;

import java.util.ArrayList;

import ua.od.and.tabrssreader.R;


public class Container2RecyclerViewAdapter extends RecyclerView.Adapter<Container2RecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Article> list;
    private final Container2View.OnListFragmentInteractionListener2 listener2;

    Container2RecyclerViewAdapter(ArrayList<Article> list, Container2View.OnListFragmentInteractionListener2 listener) {
        this.list = list;
        listener2 = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_container1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Article article = list.get(position);
        holder.tvTitle.setText(article.getTitle());
        holder.lvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener2) {
                    listener2.onListFragmentInteraction(article);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle;
        final LinearLayout lvView;

        ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            lvView = view.findViewById(R.id.lvView);
        }
    }
}
