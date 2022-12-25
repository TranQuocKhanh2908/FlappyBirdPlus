package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static TextView txt_score, txt_best_score, txt_score_over, txt_user, txt_userLogin;
    public static Button txt_restart, btnLogin, btnNoLogin;
    public static ImageView txt_F_B, btn_play;
    public static ImageView btn_trophy;
    public static ImageView btn_setting;
    public static ImageView btn_skinChange;
    public static RelativeLayout rl_over;
    public static RelativeLayout rl_start;
    public static MediaPlayer media;
    public static ImageView btn_bird1, btn_bird2, btn_bird3;
    Switch mediaOff, musicOff;
    int bird_a, bird_b;
    private GameView gv;
    Dialog dialog, dialogSetting, dialogLogin, dialogScore;
    AlertDialog.Builder builder;

    TopScoreDatabase db = new TopScoreDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constaint.SCREEN_HEIGHT = dm.heightPixels;
        Constaint.SCREEN_WIDTH = dm.widthPixels;
        setContentView(R.layout.activity_main);

        //Get views id
        txt_restart = findViewById(R.id.txt_restart);
        txt_score = findViewById(R.id.txt_score);
        txt_F_B = findViewById(R.id.txt_F_bird);
        txt_best_score = findViewById(R.id.txt_best_score);
        txt_score_over = findViewById(R.id.txt_score_over);
        rl_over = findViewById(R.id.rl_over);
        rl_start = findViewById(R.id.rl_start);
        btn_setting = findViewById(R.id.btn_Setting);
        btn_play = findViewById(R.id.btn_play);
        btn_skinChange = findViewById(R.id.btn_skinChange);
        btn_trophy = findViewById(R.id.btn_trophy);
        txt_user = findViewById(R.id.user);
        gv = findViewById(R.id.gv);

        //music
        media = MediaPlayer.create(this, R.raw.sillychipsong);
        media.setLooping(true);
        media.start();

        //set default bird
        bird_a = R.drawable.bird_new1;
        bird_b = R.drawable.bird_new2;

        gv.intBird(bird_a, bird_b);

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSetting.show();
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gv.setStart(true);
                rl_start.setVisibility((View.INVISIBLE));
                txt_score.setVisibility(View.VISIBLE);
                txt_F_B.setVisibility(View.INVISIBLE);
            }
        });

        txt_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bestScore = txt_best_score.getText().toString();
                String[] split = bestScore.split(":");
                Integer score = Integer.valueOf(split[1]);
                String gUserName = txt_user.getText().toString();
                Cursor cursor = db.getScore(gUserName);
                while (cursor.moveToNext()){

                    if(cursor.getInt(0) > score){
                    }else{
                        db.UpdateHighScore(gUserName,score);
                    }

                }
                txt_F_B.setVisibility(View.VISIBLE);
                rl_over.setVisibility(View.INVISIBLE);
                rl_start.setVisibility(View.VISIBLE);
                gv.setStart(false);
                gv.intBird(bird_a, bird_b);
                gv.reset();
            }
        });

        btn_skinChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        showSkinDialog();
        showDialogSetting();
        showDialogLogin();
        dialogLogin.show();

        btn_trophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), top_score.class));
            }
        });

        txt_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogLogin.show();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        media.start();
    }

    protected void onPause() {
        super.onPause();
        String bestScore = txt_best_score.getText().toString();
        String[] split = bestScore.split(":");
        Integer score = Integer.valueOf(split[1]);
        String gUserName = txt_user.getText().toString();
        Cursor cursor = db.getScore(gUserName);
        while (cursor.moveToNext()){

            if(cursor.getInt(0) > score){
            }else{
                db.UpdateHighScore(gUserName,score);
            }

        }
        media.pause();
    }

    public void showSkinDialog() {
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.skindialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_dialog));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        btn_bird1 = dialog.findViewById(R.id.bird1);
        btn_bird2 = dialog.findViewById(R.id.bird2);
        btn_bird3 = dialog.findViewById(R.id.bird3);

        btn_bird1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bird_a = R.drawable.bird1;
                bird_b = R.drawable.bird2;
                gv.intBird(bird_a, bird_b);
                dialog.dismiss();
            }
        });

        btn_bird2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bird_a = R.drawable.bird_new1;
                bird_b = R.drawable.bird_new2;
                gv.intBird(bird_a, bird_b);
                dialog.dismiss();
            }
        });

        btn_bird3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bird_a = R.drawable.twitterlogo;
                bird_b = R.drawable.twitterlogo;
                gv.intBird(bird_a, bird_b);
                dialog.dismiss();
            }
        });
    }

    public void showDialogSetting() {
        dialogSetting = new Dialog(MainActivity.this);
        dialogSetting.setContentView(R.layout.setting_dia_log);
        dialogSetting.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_dialog));
        dialogSetting.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSetting.setCancelable(true);

        mediaOff = dialogSetting.findViewById(R.id.turnOff_media);
        musicOff = dialogSetting.findViewById(R.id.turnOff_music);

        mediaOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    GameView.Loadsound = false;
                }else{
                    GameView.Loadsound = true;
                }
            }
        });

        musicOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    media.pause();
                }else{
                    media.start();
                }
            }
        });

    }

    public void showDialogLogin() {
        dialogLogin = new Dialog(MainActivity.this);
        dialogLogin.setContentView(R.layout.login_user);
        dialogLogin.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_dialog));
        dialogLogin.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogLogin.setCancelable(false);
        dialogLogin.setCanceledOnTouchOutside(false);

        btnLogin = dialogLogin.findViewById(R.id.button_login);
        btnNoLogin = dialogLogin.findViewById(R.id.button_no_login);
        txt_userLogin = dialogLogin.findViewById(R.id.user_name);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Login(txt_userLogin.getText().toString());
            }
        });

        btnNoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogLogin.dismiss();
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Cảnh Báo");
                builder.setMessage("Bạn có chắc chắn? Thành tích của bạn sẽ không được lưu lại")
                        .setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                txt_user.setText("Type name here");
                                GameView.bestscore = 0;
                                dialogLogin.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogLogin.show();
                            }
                        }).show();
            }
        });
    }

    private void Login(String gUserName) {
        if(gUserName.isEmpty()){
            Toast.makeText(this, "Vui Lòng Nhập Tên Của Bạn", Toast.LENGTH_SHORT).show();
        }else{

            Boolean resultCheckName = db.CheckNameExists(gUserName);
            if(resultCheckName == true){

                db.InsertData(gUserName, 0);

            }
            GameView.bestscore = 0;
            dialogLogin.dismiss();
            txt_user.setText(gUserName);
        }
    }

    @Override
    public void onBackPressed() {
        if(dialog.isShowing() || dialogSetting.isShowing() || dialogScore.isShowing()) {
            dialog.dismiss();
            dialogSetting.dismiss();
            dialogScore.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}