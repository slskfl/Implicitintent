package com.example.implicitintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.CookieHandler;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnDial, btnWeb, btnMap, btnSearch, btnSms, btnPhoto, btnPhotoView, btnSpeech;
    EditText edtContent;
    ImageView img1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDial=findViewById(R.id.btnDial);
        btnWeb=findViewById(R.id.btnWeb);
        btnMap=findViewById(R.id.btnMap);
        btnSearch=findViewById(R.id.btnSearch);
        btnSms=findViewById(R.id.btnSms);
        btnPhoto=findViewById(R.id.btnPhoto);
        btnPhotoView=findViewById(R.id.btnPhotoView);
        btnSpeech=findViewById(R.id.btnSpeech);
        edtContent=findViewById(R.id.edtContent);
        img1=findViewById(R.id.imgV);

        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("tel:010-5672-0000");
                Intent intent=new Intent(Intent.ACTION_DIAL,uri);
                startActivity(intent);
            }
        });
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("http://www.naver.com");
                Intent intent=new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("http:maps.google.com/maps?q="+37.350197+","+127.108963);
                Intent intent=new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, edtContent.getText().toString());
                startActivity(intent);
            }
        });
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SENDTO);
                intent.putExtra("sms_body", "안녕하세요.");
                intent.setData(Uri.parse("smsto:"+Uri.encode("010-8888-9999")));
                startActivity(intent);
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }
        });
        btnPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //양방향
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 30);
            }
        });
        btnSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //양방향
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "보고싶은 동물은?");
                startActivityForResult(intent, 50);
            }
        });
    }
    //btnPhotoView>> 찍은 사진을 돌려줘야함(양방향)

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==30){
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            img1.setImageBitmap(bitmap);
        } else if(resultCode==RESULT_OK && requestCode==50){
            ArrayList<String> results=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String pet=results.get(0);
            if(pet.equals("강아지")){
                img1.setImageResource(R.drawable.dog);
            } else if(pet.equals("고양이")){
                img1.setImageResource(R.drawable.cat);
            }else if(pet.equals("원숭이")){
                img1.setImageResource(R.drawable.mon);
            }else if(pet.equals("토끼")){
                img1.setImageResource(R.drawable.rabbit);
            }else{
                Toast.makeText(getApplicationContext(), "이미지가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}