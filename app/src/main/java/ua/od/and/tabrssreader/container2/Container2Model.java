package ua.od.and.tabrssreader.container2;

import android.os.Handler;
import android.util.Log;

import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.Date;

import ua.od.and.tabrssreader.interfaces.Interfaces;

import static ua.od.and.tabrssreader.constants.Constants.CHECK_INTERVAL;

public class Container2Model {

    private Date entertainmentLastUpdateDate,
            environmentLastUpdateDate;
    private Handler handler;
    private Runnable handlerTask;
    private String url1,
            url2;
    private Interfaces.RequestInterfaceContainerModel2 callback;

    Container2Model() {
        handler = new Handler();
        handlerTask = new Runnable() {
            @Override
            public void run() {
                getFeed();
                handler.postDelayed(handlerTask, CHECK_INTERVAL);
            }
        };
    }

    public void setParamsAndRun(String url1, String url2, Interfaces.RequestInterfaceContainerModel2 callback) {
        this.url1 = url1;
        this.url2 = url2;
        this.callback = callback;
        startRepeatingTask();
    }

    void stopTasks() {
        handler.removeCallbacks(handlerTask);
        Log.i("TAG", "Tasks stopped");
    }

    private void getFeed() {
        Parser parser1 = new Parser();
        callback.onEntertainmentBeginCheck();
        Log.i("TAG", "BeginCheck " + url1);
        parser1.execute(url1);
        parser1.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                if (list.isEmpty()) {
                    callback.onError();
                    return;
                }
                if (entertainmentLastUpdateDate == null) {
                    entertainmentLastUpdateDate = list.get(0).getPubDate();
                    callback.onEntertainmentDownloaded(list);
                    Log.i("TAG", "Feed downloaded " + url1);
                } else {
                    if (list.get(0).getPubDate().after(entertainmentLastUpdateDate)) {
                        entertainmentLastUpdateDate = list.get(0).getPubDate();
                        callback.onEntertainmentDownloaded(list);
                        Log.i("TAG", "Feed downloaded " + url1);
                    } else {
                        Log.i("TAG", "Nothing change");
                    }
                }
                callback.onEntertainmentFinishCheck();
            }

            @Override
            public void onError() {
                callback.onError();
                callback.onEntertainmentFinishCheck();
            }
        });

        Parser parser2 = new Parser();
        callback.onEnvironmentBeginCheck();
        Log.i("TAG", "BeginCheck " + url2);
        parser2.execute(url1);
        parser2.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                if (list.isEmpty()) {
                    callback.onError();
                    return;
                }
                if (environmentLastUpdateDate == null) {
                    environmentLastUpdateDate = list.get(0).getPubDate();
                    callback.onEnvironmentDownloaded(list);
                    Log.i("TAG", "Feed downloaded " + url1);
                } else {
                    if (list.get(0).getPubDate().after(environmentLastUpdateDate)) {
                        environmentLastUpdateDate = list.get(0).getPubDate();
                        callback.onEntertainmentDownloaded(list);
                        Log.i("TAG", "Feed downloaded " + url1);
                    } else {
                        Log.i("TAG", "Nothing change");
                    }
                }
                callback.onEnvironmentFinishCheck();
            }

            @Override
            public void onError() {
                callback.onError();
                callback.onEnvironmentFinishCheck();
            }
        });
    }

    private void startRepeatingTask() {
        handlerTask.run();
        Log.i("TAG", "Running tasks");
    }
}
