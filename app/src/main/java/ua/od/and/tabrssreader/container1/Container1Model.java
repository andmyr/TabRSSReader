package ua.od.and.tabrssreader.container1;

import android.os.Handler;
import android.util.Log;

import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.Date;

import ua.od.and.tabrssreader.interfaces.Interfaces;

import static ua.od.and.tabrssreader.constants.Constants.CHECK_INTERVAL;

public class Container1Model {

    private Date lastUpdateDate;
    private Handler handler;
    private Runnable handlerTask;
    private String url;
    private Interfaces.RequestInterfaceContainerModel1 callback;

    Container1Model() {
        handler = new Handler();
        handlerTask = new Runnable() {
            @Override
            public void run() {
                getFeed();
                handler.postDelayed(handlerTask, CHECK_INTERVAL);
            }
        };
    }

    public void setParamsAndRun(String url, Interfaces.RequestInterfaceContainerModel1 callback) {
        this.url = url;
        this.callback = callback;
        startRepeatingTask();
    }

    void stopTask() {
        handler.removeCallbacks(handlerTask);
        Log.i("TAG", "Task stopped");
    }

    private void getFeed() {
        Parser parser = new Parser();
        callback.onBeginCheck();
        Log.i("TAG", "BeginCheck " + url);
        parser.execute(url);
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                if (list.isEmpty()) {
                    callback.onError();
                    return;
                }
                if (lastUpdateDate == null) {
                    lastUpdateDate = list.get(0).getPubDate();
                    callback.onDownloaded(list);
                    Log.i("TAG", "Feed downloaded " + url);
                } else {
                    if (list.get(0).getPubDate().after(lastUpdateDate)) {
                        lastUpdateDate = list.get(0).getPubDate();
                        callback.onDownloaded(list);
                        Log.i("TAG", "Feed downloaded " + url);
                    } else {
                        Log.i("TAG", "Nothing change");
                    }
                }
                callback.onFinishCheck();
            }

            @Override
            public void onError() {
                callback.onError();
                callback.onFinishCheck();
            }
        });
    }

    private void startRepeatingTask() {
        handlerTask.run();
        Log.i("TAG", "Running task");
    }
}
