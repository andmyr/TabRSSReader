package ua.od.and.tabrssreader.container1;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;

import com.prof.rssparser.Article;

import java.util.ArrayList;

import ua.od.and.tabrssreader.interfaces.Interfaces;
import ua.od.and.tabrssreader.activities.MainActivity;
import ua.od.and.tabrssreader.constants.Constants;

public class Container1Presenter {
    private Container1View container1View;
    private Container1Model container1Model;
    private Activity activity;

    private Interfaces.RequestInterfaceContainerModel1 requestInterfaceContainerModel1;

    public Container1Presenter(final Container1View container1View, Container1Model container1Model, final Activity activity) {
        this.container1View = container1View;
        this.container1Model = container1Model;
        this.activity = activity;

        if (requestInterfaceContainerModel1 == null) {
            requestInterfaceContainerModel1 = new Interfaces.RequestInterfaceContainerModel1() {
                @Override
                public void onDownloaded(ArrayList<Article> list) {
                    container1View.onLoadData(list);
                }

                @Override
                public void onError() {
                    container1View.showError();
                }

                @Override
                public void onBeginCheck() {
                    ((MainActivity)activity).showProgressbar();
                }

                @Override
                public void onFinishCheck() {
                    ((MainActivity)activity).hideProgressbar();
                }
            };
        }
    }

    public void onResume() {
        container1Model.setParamsAndRun(Constants.URL_1, requestInterfaceContainerModel1);
    }

    public void onPause() {
        container1Model.stopTask();
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
}
