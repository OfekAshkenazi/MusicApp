package com.example.ofek.musicapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;


import com.claudiodegio.msv.BaseMaterialSearchView;
import com.claudiodegio.msv.OnSearchViewListener;

import org.schabi.newpipe.extractor.search.SearchResult;

import Fragments.YTSearchFragment;
import YoutubeDownloadTasks.Tasks.YoutubeSearchTask;
public class MainActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_REQUEST = 11111;
    private YoutubeSearchTask task;
    public static boolean isPermissionsAllowed=false;
    private BaseMaterialSearchView searchView;
    Toolbar toolbar;
    Handler searchHandler;
    public static ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setHandler();
        isPermissionsAllowed=requestPermissions();
        setViews();
        setSupportActionBar(toolbar);
    }

    private void setHandler() {
        searchHandler=new Handler();
    }

    private void setViews() {
        searchView=findViewById(R.id.songsSV);
        toolbar=findViewById(R.id.toolbar);
        searchView.setOnSearchViewListener(new OnSearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }

            @Override
            public boolean onQueryTextSubmit(final String s) {
                dialog = ProgressDialog.show(MainActivity.this, "Youtube Searching",
                        "Searching. Please wait...", true);
                getSupportFragmentManager().beginTransaction().replace(R.id.ytSearchContainer,new YTSearchFragment());
                return true;
            }

            @Override
            public void onQueryTextChange(String s) {
                searchHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchMusicInLocalStorage();
                    }
                },1000);
            }
        });
    }

    private void searchMusicInLocalStorage() {

    }

    private boolean requestPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.INTERNET}, STORAGE_PERMISSION_REQUEST);
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==STORAGE_PERMISSION_REQUEST){
            if (grantResults[0]!=PackageManager.PERMISSION_GRANTED||grantResults[1]!=PackageManager.PERMISSION_GRANTED||grantResults[2]!=PackageManager.PERMISSION_GRANTED){
                AlertDialog dialog=new AlertDialog.Builder(this).create();
                dialog.setMessage("Please Make Sure to allow all permissions. the application cannot run without those permissions");
                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.this.finish();
                    }
                });
            }
            else {
                isPermissionsAllowed=true;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void dismissSearchDialog(){
        if (dialog!=null)
            dialog.dismiss();
    }

}
