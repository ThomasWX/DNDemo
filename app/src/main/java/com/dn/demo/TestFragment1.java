package com.dn.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TestFragment1 extends Fragment {
    public static final String TAG = "TestFragment1";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CommonUtils.printLogs(TAG,"onCreateView");
        return inflater.inflate(R.layout.test_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CommonUtils.printLogs(TAG,"onViewCreated");
        TextView textView = view.findViewById(R.id.test_fragment_name);
        if (getArguments() != null) {
            textView.setText(getArguments().getString(CommonUtils.TEST_FRAGMENT_TAG));
        }
    }

    @Override
    public void onAttach(Context context) {
        CommonUtils.printLogs(TAG,"onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        CommonUtils.printLogs(TAG,"onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonUtils.printLogs(TAG,"onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        CommonUtils.printLogs(TAG,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        CommonUtils.printLogs(TAG,"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        CommonUtils.printLogs(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        CommonUtils.printLogs(TAG,"onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CommonUtils.printLogs(TAG,"onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CommonUtils.printLogs(TAG,"onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        CommonUtils.printLogs(TAG,"onDetach");
    }
}
