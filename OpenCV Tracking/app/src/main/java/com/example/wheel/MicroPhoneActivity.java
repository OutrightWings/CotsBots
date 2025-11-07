package com.example.wheel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class MicroPhoneActivity extends AppCompatActivity {

    private Button btnStart;
    private TextView tvResult;

    private ActivityResultLauncher<Intent> speechLauncher;

    // 全局变量，用来保存识别文本
    private String recognizedText = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microphone);

        btnStart = findViewById(R.id.btnStart);
        tvResult = findViewById(R.id.tvResult);

        // 初始化 Activity Result
        speechLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            ArrayList<String> list =
                                    result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                            if (list != null && list.size() > 0) {
                                recognizedText = list.get(0);     //  保存
                                tvResult.setText(recognizedText); //  显示
                            }
                        }
                    }
                }
        );

        btnStart.setOnClickListener(v -> startSpeechToText());
    }

    // 启动语音识别
    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        // 强制英语识别
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US");
        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "en-US");

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...");

        speechLauncher.launch(intent);
    }

    // 外部可调用这个函数获取识别文本（以后你传给 ChatGPT API 就用这个）*********ChatGpt
    public String getRecognizedText() {
        return recognizedText;
    }
}
