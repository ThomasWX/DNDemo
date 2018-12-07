package com.dn.demo;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;


public class WatchDialogFragment extends DialogFragment {
    public static final String TAG = "WatchDialogFragment";
    private static final String KEY_ARGUMENTS_TITLE = "key_arguments_title";
    private static final String KEY_ARGUMENTS_MESSAGES = "key_arguments_messages";
    private static final String KEY_ARGUMENTS_MESSAGE_ACTIONS = "key_arguments_message_actions";
    private static final String KEY_ARGUMENTS_BOTTOM_ACTIONS = "key_arguments_bottom_actions";

    private TextView tv_Title;
    private LinearLayout msgContainer, actionContainer;

    public WatchDialogFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        CommonUtils.printLogs(TAG, "onCreate");
        setStyle(DialogFragment.STYLE_NORMAL, R.style.WatchDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CommonUtils.printLogs(TAG, "onCreateView");
        return inflater.inflate(R.layout.watch_dialog_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CommonUtils.printLogs(TAG, "onViewCreated");
        tv_Title = (TextView) view.findViewById(R.id.watch_dialog_tv_title);
        msgContainer = (LinearLayout) view.findViewById(R.id.watch_dialog_msg_container);
        actionContainer = (LinearLayout) view.findViewById(R.id.watch_dialog_action_container);
        Bundle arguments = getArguments();
        if (arguments != null) {
            init(arguments);
        }
    }

    private void init(Bundle arguments) {
        CharSequence title = arguments.getCharSequence(KEY_ARGUMENTS_TITLE);
        if (TextUtils.isEmpty(title)) {
            tv_Title.setVisibility(View.GONE);
        } else {
            tv_Title.setVisibility(View.VISIBLE);
            tv_Title.setText(title);
        }
        CharSequence[] messages = arguments.getCharSequenceArray(KEY_ARGUMENTS_MESSAGES);
        Action[] messageActions = (Action[]) arguments.getParcelableArray(KEY_ARGUMENTS_MESSAGE_ACTIONS);
        boolean isMsgNull = (messages == null || messages.length == 0);
        boolean isMsgActionNull = (messageActions == null || messageActions.length == 0);
        if (isMsgNull && isMsgActionNull) {
            msgContainer.setVisibility(View.GONE);
        } else {
            msgContainer.setVisibility(View.VISIBLE);
            if (!isMsgNull)
                addMessage(messages);
            else if (!isMsgActionNull)
                addAction(msgContainer, messageActions);
        }

        Action[] bottomActions = (Action[]) arguments.getParcelableArray(KEY_ARGUMENTS_BOTTOM_ACTIONS);

        if (bottomActions == null || bottomActions.length == 0) {
            actionContainer.setVisibility(View.GONE);
        } else {
            actionContainer.setVisibility(View.VISIBLE);
            addAction(actionContainer, bottomActions);
        }
    }


    private void addMessage(CharSequence[] messages) {
        msgContainer.removeAllViews();
        TextView textView;
        for (int i = 0; i < messages.length; i++) {
            textView = new TextView(getActivity());
            textView.setText(messages[i]);
            textView.setTextSize(CommonUtils.px2sp(getContext(), getResources().getDimension(R.dimen.text_size)));
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorText));
            textView.setMaxLines(1);
            textView.setGravity(Gravity.CENTER);
            msgContainer.addView(textView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    private void addAction(LinearLayout container, Action[] actions) {
        container.removeAllViews();
        Button button;
        LinearLayout.LayoutParams params;
        for (int i = 0; i < actions.length; i++) {
            button = new Button(getActivity());
            button.setId(actions[i].id);
            button.setText(actions[i].label);
            if (mActionListener != null) button.setOnClickListener(mActionListener);
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                params.setMargins(0, getActivity().getResources().getDimensionPixelSize(R.dimen.watch_dialog_element_margin_top), 0, 0);
            }
            container.addView(button, params);
        }
    }

    private View.OnClickListener mActionListener;

    public void setOnClickListener(View.OnClickListener listener) {
        mActionListener = listener;
    }

    // Build Instance
    public static class Builder {
        Context mContext;
        CharSequence mTitle;
        CharSequence[] mMessages;
        Action[] mMsgActions;
        Action[] mBottomActions;

        public Builder(@NonNull Context context) {
            this.mContext = context;
        }

        public WatchDialogFragment.Builder setTitle(@StringRes int titleId) {
            this.mTitle = this.mContext.getText(titleId);
            return this;
        }

        public WatchDialogFragment.Builder setTitle(@Nullable CharSequence title) {
            this.mTitle = title;
            return this;
        }

        public WatchDialogFragment.Builder setMessages(@Nullable CharSequence... message) {
            this.mMessages = message;
            return this;
        }

        public WatchDialogFragment.Builder setMessageActions(@Nullable Action... msgActions) {
            this.mMsgActions = msgActions;
            return this;
        }

        public WatchDialogFragment.Builder setBottomActions(@Nullable Action... bottomActions) {
            this.mBottomActions = bottomActions;
            return this;
        }

        public WatchDialogFragment create() {
            WatchDialogFragment dialogFragment = new WatchDialogFragment();
            Log.d(TAG, "[create] new WatchDialogFragment()");

            Bundle bundle = new Bundle();
            bundle.putCharSequence(KEY_ARGUMENTS_TITLE, mTitle);
            bundle.putCharSequenceArray(KEY_ARGUMENTS_MESSAGES, mMessages);
            bundle.putParcelableArray(KEY_ARGUMENTS_MESSAGE_ACTIONS, mMsgActions);
            bundle.putParcelableArray(KEY_ARGUMENTS_BOTTOM_ACTIONS, mBottomActions);
            dialogFragment.setArguments(bundle);
            Log.d(TAG, "[create] setArguments Successful");
            return dialogFragment;
        }
    }

    public static class Action implements Parcelable {
        int label;
        int id;

        public Action(int labelResId, int id) {
            this.label = labelResId;
            this.id = id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(label);
            dest.writeInt(id);
        }

        public static final Parcelable.Creator<Action> CREATOR = new Creator<Action>() {
            @Override
            public Action createFromParcel(Parcel source) {
                return new Action(source.readInt(), source.readInt());
            }

            @Override
            public Action[] newArray(int size) {
                return new Action[size];
            }
        };
    }


    public Bundle buildArgs(CharSequence title, CharSequence[] msgs, WatchDialogFragment.Action... bottomActions) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(KEY_ARGUMENTS_TITLE, title);
        bundle.putCharSequenceArray(KEY_ARGUMENTS_MESSAGES, msgs);
        bundle.putParcelableArray(KEY_ARGUMENTS_BOTTOM_ACTIONS, bottomActions);
        return bundle;
    }
    // Watch Life Cycle

    @Override
    public void onAttach(Context context) {
        CommonUtils.printLogs(TAG, "onAttach");
        super.onAttach(context);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonUtils.printLogs(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        CommonUtils.printLogs(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        CommonUtils.printLogs(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        CommonUtils.printLogs(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        CommonUtils.printLogs(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CommonUtils.printLogs(TAG, "onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CommonUtils.printLogs(TAG, "onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        CommonUtils.printLogs(TAG, "onDetach");
    }
}
