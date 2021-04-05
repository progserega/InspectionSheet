package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.AboutContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.AboutPresenter;


public class About extends AppCompatActivity implements AboutContract.View {

    private AboutContract.Presenter presenter;
    private InspectionSheetApplication application;

    private DownloadManager downloadManager;
    private long downloadReference;

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 54654;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        this.application = (InspectionSheetApplication) this.getApplication();
        this.presenter = new AboutPresenter(this, application);

        setTitle("О программе");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setAboutText();

        initCheckUpdateTextView();

        presenter.onViewCreated();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();
        // Операции для выбранного пункта меню
        switch (id) {

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void setAboutText() {
        TextView textView = (TextView) findViewById(R.id.about_text);
        // textView.setText(Html.fromHtml(getString(R.string.text_about), Html.FROM_HTML_MODE_COMPACT));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(getString(R.string.text_about), Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(getString(R.string.text_about)));
        }
    }

    @Override
    public PackageInfo getPackageInfo() {
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return pInfo;
    }

    @Override
    public void setVersionName(String versionName) {
        TextView versionTextView = (TextView) findViewById(R.id.about_version);
        versionTextView.setText(versionName);
    }

    private void initCheckUpdateTextView() {
        TextView checkUpdateTextView = (TextView) findViewById(R.id.about__check_update);
        checkUpdateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCheckUpdatePressed();
            }
        });
    }

    @Override
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();
    }

    public void showNeedUpdateDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(Html.fromHtml(message))
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                            return;
                        }


                    }
                })
                .setNegativeButton("Не сейчас", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView aboutText = (TextView) dialog.findViewById(android.R.id.message);

        aboutText.setMovementMethod(LinkMovementMethod.getInstance());
        aboutText.setLinksClickable(true);

    }

    @Override
    public void startDownload(String fileUrl, String fileName) {
        startDownload(fileUrl, Environment.DIRECTORY_DOWNLOADS.concat("/"), fileName);
    }

    private void startDownload(String downloadPath, String destinationPath, String fileName) {
        Uri uri = Uri.parse(downloadPath); // Path where you want to download file.
        DownloadManager.Request request = new DownloadManager.Request(uri);
       // request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);  // Tell on which network you want to download file.
        request.setAllowedNetworkTypes( DownloadManager.Request.NETWORK_WIFI);  // Tell on which network you want to download file.
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);  // This will show notification on top when downloading the file.
        request.setTitle(fileName); // Title for notification.
        request.setDescription(fileName);
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(destinationPath, fileName);  // Storage directory path

        final long downloadId = ((DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request); // This will start downloading

        final int DOWNLOAD_PROGRESS = 5020;
        final int DOWNLOAD_COMPLETE = 5021;
        DownloadManager manager = (DownloadManager) getApplicationContext()
                .getSystemService(Context.DOWNLOAD_SERVICE);
        final TextView progressText = (TextView) findViewById(R.id.pbText);
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == DOWNLOAD_PROGRESS) {
                    String downloaded = String.format("%.2f MB", (double) (msg.arg1) / 1024.0 / 1024);
                    String total = String.format("%.2f MB", (double) (msg.arg2) / 1024.0 / 1024);
                    String status = downloaded + " / " + total;
                    //Log.d("DOWNLOAD", status);

                    progressText.setText(status);

                }
                if (msg.what == DOWNLOAD_COMPLETE) {
                    HideProgressBar();

                    Intent intent = new Intent();
                    intent.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
                    startActivity(intent);

                }
                super.handleMessage(msg);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean downloading = true;
                while (downloading) {
                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(downloadId);
                    Cursor cursor = manager.query(q);
                    cursor.moveToFirst();
                    int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false;
                    }
                    //Post message to UI Thread
                    Message msg = handler.obtainMessage();
                    msg.what = DOWNLOAD_PROGRESS;
                    //msg.obj = statusMessage(cursor);
                    msg.arg1 = bytes_downloaded;
                    msg.arg2 = bytes_total;
                    handler.sendMessage(msg);
                    cursor.close();
                }
                Message msg = handler.obtainMessage();
                msg.what = DOWNLOAD_COMPLETE;
                handler.sendMessage(msg);

            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ShowProgressBar();
                presenter.onDownloadBtnPressed();
            }
        }
    }


    @Override
    public void ShowProgressBar() {
        TextView progressText = (TextView) findViewById(R.id.pbText);
        progressText.setText("");
        LinearLayout progress = (LinearLayout) findViewById(R.id.about__progress_bar);
        progress.setVisibility(View.VISIBLE);

    }

    @Override
    public void HideProgressBar() {
        LinearLayout progress = (LinearLayout) findViewById(R.id.about__progress_bar);
        progress.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

//    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            //check if the broadcast message is for our Enqueued download
//            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//            if (downloadReference == referenceId) {
//                //parse the JSON data and display on the screen
//                Toast toast = Toast.makeText(About.this, "Downloading of data just finished", Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.TOP, 25, 400);
//                toast.show();
//
//
//            }
//        }
//    };
}

