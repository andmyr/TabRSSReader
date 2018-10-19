package ua.od.and.tabrssreader.interfaces;

import com.prof.rssparser.Article;

import java.util.ArrayList;

public interface Interfaces {

    interface Container1ViewInterface {

        void onLoadData(ArrayList<Article> list);

        void showError();
    }

    interface RequestInterfaceContainerModel1 {

        void onDownloaded(ArrayList<Article> list);

        void onError();

        void onBeginCheck();

        void onFinishCheck();
    }

    interface RequestInterfaceContainerModel2 {

        void onEntertainmentDownloaded(ArrayList<Article> list);

        void onEnvironmentDownloaded(ArrayList<Article> list);

        void onError();

        void onEntertainmentBeginCheck();

        void onEntertainmentFinishCheck();

        void onEnvironmentBeginCheck();

        void onEnvironmentFinishCheck();

    }
}
