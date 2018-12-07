package com.dn.demo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.lang.StringBuilder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Dialog mDialog;
    private AlertDialog mAlertDialog;
    private WatchDialogFragment dialogFragment;

    private Button btnShowDialog, btnSwitchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnShowDialog = findViewById(R.id.showDialog);
        btnSwitchFragment = findViewById(R.id.switchFragment);
        btnShowDialog.setOnClickListener(this);
        btnSwitchFragment.setOnClickListener(this);


        Set<String> packagesToDelete = new HashSet<>();
        packagesToDelete.add("com.nubia.contacts");
        packagesToDelete.add("com.nubia.mms");
        packagesToDelete.add("com.android.contacts");
        printLogs(packagesToDelete);
        printMethodTrace();
    }


    private void printLogs(Set<String> set) {
        try {
            StringBuilder sb = new StringBuilder("packagesToDelete:{");
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next()).append(", ");
            }
            sb.append("}");
            android.util.Log.w(TAG, "[getSystemAppsToRemove] " + sb.toString());
        } catch (Exception e) {
            android.util.Log.w(TAG, "Exception:" + e.getMessage());
        }
    }


    public void printMethodTrace(){
        Log.w(TAG,"[printMethodTrace]",new Throwable("ctsTest"));
    }


    public void showCustomDialog(View view) {
        showDialogFragment();
//        showDialog();
//        showAlertDialog();


    }

    private void showDialog() {
        mDialog = new WatchDialog(this, R.style.WatchDialog);
        View view = getLayoutInflater().inflate(R.layout.watch_dialog_fragment, null, false);


        mDialog.setContentView(view);
        mDialog.setTitle("标题");
        mDialog.show();
    }

    private void showAlertDialog() {
        mAlertDialog = new WatchAlertDialog.Builder(this).setTitle("标题").setMessage("消息内容").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAlertDialog.dismiss();
            }
        }).create();
//        mAlertDialog = new WatchAlertDialog(this);
//        mAlertDialog.setTitle("标题");
//        mAlertDialog.setMessage("消息内容");
//        mAlertDialog.show();
    }

    private void showDialogFragment() {
        if (dialogFragment == null) {
            dialogFragment = new WatchDialogFragment.Builder(this).setTitle("国际通用识别码")
                    .setMessages("ADFSDFSD45665232").setBottomActions(new WatchDialogFragment.Action(R.string.btn_ok, R.id.button_ok))
                    .create();
            CommonUtils.printLogs(TAG, "new WatchDialogFragment()");
            dialogFragment.setOnClickListener(this);
        } else {
            CommonUtils.printLogs(TAG, "Resuse WatchDialogFragment");
            CharSequence[] charSequences = new CharSequence[1];
            charSequences[0] = "afwefsfea";
            dialogFragment.setArguments(dialogFragment.buildArgs("IMEI", charSequences, new WatchDialogFragment.Action(R.string.btn_ok, R.id.button_ok)));
            CommonUtils.printLogs(TAG, "reuse setArguments Successful");
        }


        dialogFragment.show(getSupportFragmentManager(), WatchDialogFragment.TAG);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showDialog:
                showDialogFragment();
                break;
            case R.id.switchFragment:
                String title = btnSwitchFragment.getText().toString();
                String targetTag;
                if (TestFragment1.TAG.equals(title)) {
                    targetTag = TestFragment2.TAG;
                } else if (TestFragment2.TAG.equals(title)) {
                    targetTag = TestFragment1.TAG;
                } else {
                    targetTag = TestFragment1.TAG;
                }
                btnSwitchFragment.setText(targetTag);
                switchFragment(targetTag);
                break;
            case R.id.button_ok:
                if (dialogFragment != null) dialogFragment.dismiss();
                break;
            default:
                break;
        }
    }


    private void switchFragment(String targetTag) {
        Fragment fragment = null;
        if (TextUtils.equals(targetTag, TestFragment1.TAG)) {
            fragment = getSupportFragmentManager().findFragmentByTag(TestFragment1.TAG);
            if (fragment == null) {
                fragment = new TestFragment1();
                Log.d(TAG, "new TestFragment1()");
            } else {
                Log.d(TAG, "reuse TestFragment1");
            }
        } else if (TextUtils.equals(targetTag, TestFragment2.TAG)) {
            fragment = getSupportFragmentManager().findFragmentByTag(TestFragment2.TAG);
            if (fragment == null) {
                fragment = new TestFragment2();
                Log.d(TAG, "new TestFragment2()");
            } else {
                Log.d(TAG, "reuse TestFragment2");
            }
        }

        if (fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putString(CommonUtils.TEST_FRAGMENT_TAG, targetTag);
            fragment.setArguments(bundle);
            Log.d(TAG, "setArguments successful");
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragments_container, fragment, targetTag);


    }


}
