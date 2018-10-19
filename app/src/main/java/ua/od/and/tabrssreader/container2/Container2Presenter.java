package ua.od.and.tabrssreader.container2;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;

import com.prof.rssparser.Article;

import java.util.ArrayList;

import ua.od.and.tabrssreader.activities.MainActivity;
import ua.od.and.tabrssreader.constants.Constants;
import ua.od.and.tabrssreader.interfaces.Interfaces;

public class Container2Presenter {
    private Container2View container2View;
    private Container2Model container2Model;
    private Activity activity;
    private ArrayList<Article> environmentList;
    private ArrayList<Article> entertainmentList;
    private boolean isEnvironmentCheckDone,
            isEntertainmentCheckDone,
            isEnvironmentDataChanged,
            isEntertainmentDataChanged;

    private Interfaces.RequestInterfaceContainerModel2 requestInterface;

    public Container2Presenter(final Container2View container2View, Container2Model container2Model, final Activity activity) {
        this.container2View = container2View;
        this.container2Model = container2Model;
        this.activity = activity;

        if (requestInterface == null) {
            requestInterface = new Interfaces.RequestInterfaceContainerModel2() {

                @Override
                public void onEntertainmentDownloaded(ArrayList<Article> list) {
                    entertainmentList = list;
                    isEntertainmentDataChanged = true;
                }

                @Override
                public void onEnvironmentDownloaded(ArrayList<Article> list) {
                    environmentList = list;
                    isEnvironmentDataChanged = true;
                }

                @Override
                public void onEntertainmentBeginCheck() {
                    ((MainActivity) activity).showProgressbar();
                    isEntertainmentCheckDone = false;
                    isEntertainmentDataChanged = false;
                }

                @Override
                public void onEntertainmentFinishCheck() {
                    isEntertainmentCheckDone = true;
                    if (isEnvironmentCheckDone) {
                        if (isEntertainmentDataChanged || isEnvironmentDataChanged) {
                            dataUnification();
                        } else {
                            ((MainActivity)activity).hideProgressbar();
                        }
                    }
                }

                @Override
                public void onEnvironmentBeginCheck() {
                    ((MainActivity) activity).showProgressbar();
                    isEnvironmentCheckDone = false;
                    isEnvironmentDataChanged = false;
                }

                @Override
                public void onEnvironmentFinishCheck() {
                    isEnvironmentCheckDone = true;
                    if (isEntertainmentCheckDone) {
                        if (isEntertainmentDataChanged || isEnvironmentDataChanged) {
                            dataUnification();
                        } else {
                            ((MainActivity)activity).hideProgressbar();
                        }
                    }
                }

                @Override
                public void onError() {
                    container2View.showError();
                }
            };
        }
    }

    public void onResume() {
        container2Model.setParamsAndRun(Constants.URL_2, Constants.URL_3, requestInterface);
    }

    public void onPause() {
        container2Model.stopTasks();
    }

    public void setLabel(Article article) {
        ((MainActivity) activity).setLabel(article.getTitle());

        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(activity);
        }
        builder.setTitle(article.getTitle())
                .setMessage(article.getDescription())
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void dataUnification() {
        ArrayList<Article> list = new ArrayList<>();
        list.addAll(entertainmentList);
        list.addAll(environmentList);
        container2View.onLoadData(list);
        ((MainActivity) activity).hideProgressbar();
    }
}
