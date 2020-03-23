package com.euphorbia.musicquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);

        tv = findViewById(R.id.tv);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"0.5 배속 설정!",Toast.LENGTH_SHORT).show();
                setResult(111);
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"0.75 배속 설정!",Toast.LENGTH_SHORT).show();
                setResult(112);
                finish();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"1.0 배속 설정!",Toast.LENGTH_SHORT).show();
                setResult(113);
                finish();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"1.25 배속 설정!",Toast.LENGTH_SHORT).show();
                setResult(114);
                finish();
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"1.5 배속 설정!",Toast.LENGTH_SHORT).show();
                setResult(115);
                finish();
            }
        });

        tv.setText(Html.fromHtml("<big><big><b>Q&A</b></big></big> <br><br> <big><b>1. 노래 제목이 이상하게 나오거나 가수 이름이 unknown 으로 나와요. 앨범 사진이 나오지 않아요.</b></big> <br> 뮤직 퀴즈 앱은 사용자의 휴대폰 내에 저장된 음악을 인식하여 사용하기 때문에, 저장된 음악 자체에 잘못된 정보가 저장되어 있을 경우, 이러한 현상이 발생합니다. <br><br><big><b>" +
                "2. 휴대폰 내에 저장된 음악 말고도 다른 음악을 사용했으면 좋겠어요! </b></big> <br> 개발자도 다양한 음악을 제공해드리고 싶지만 저작권의 문제로 개발자가 임의로는 음악을 제공해드릴 수 없는 점 이해해 주셨으면 좋겠습니다."
                + "<br><br><big><b>3. 좋은 (재미있는) 아이디어나 고쳐주셨으면 하는 부분이 있어요!</b></big> <br> 저희는 이러한 사용자분들의 관심을 대 환영합니다. 개발자 이메일 <b>tngusrl@gmail.com</b> 이나 플레이스토어에 리뷰 남겨주시면 빠른 시일내에 반영하겠습니다. 감사합니다!"));
    }
}
