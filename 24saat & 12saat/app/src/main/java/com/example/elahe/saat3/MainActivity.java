package com.example.elahe.saat3;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.util.Date;


  public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener
  {
      private TextView hours, minutes;
      private Button say;
      private int loc = 0;
      private int[] seda={R.raw.clock,R.raw.ten,R.raw.thirty,R.raw.minute,0};

        private int[] sounds = {0,R.raw.one, R.raw.two, R.raw.three, R.raw.four,R.raw.five,
              R.raw.six,R.raw.seven,R.raw.eight,R.raw.nine,R.raw.ten,R.raw.eleven,R.raw.twelve,
              R.raw.thirteen,R.raw.fourteen,R.raw.fifteen,R.raw.sixteen,R.raw.seventeen,R.raw.eighteen,
              R.raw.nineteen,R.raw.twenty};


      private  int sound2[]={0,R.raw.ten,R.raw.twenty,R.raw.thirty,R.raw.fourty,R.raw.fifty,
      };

      private int sound3[]={0,R.raw.bisto,R.raw.sio,R.raw.chehelo,R.raw.panjaho};

        /*****************************************************************************/
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            final TextView hours = (TextView) findViewById(R.id.t1);
            final TextView minutes = (TextView) findViewById(R.id.t2);
            final Button say = (Button) findViewById(R.id.btn1);
            final RadioButton saat12= (RadioButton) findViewById(R.id.r1) ;
            final RadioButton saat24=(RadioButton) findViewById(R.id.r2);

                 final SharedPreferences sp = getSharedPreferences("my",MODE_PRIVATE);

                      RadioGroup.OnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                          public void OnCheckedChanged(RadioGroup group, int checkedId) {
                              say.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      Date d = new Date();
                                      int h = d.getHours();
                                      int m = d.getMinutes();
                                      hours.setText(String.valueOf(h));
                                      minutes.setText(String.valueOf(m));

                                      if (saat12.isChecked()) {

                                          if (h == 0)
                                              h = 12;
                                          if (h > 12)
                                              h = h - 12;


                                          if (saat24.isChecked()) {

                                              if (h == 0)
                                                  h = 24;
                                              if (h < 24)
                                                  h = h - 1;

                                          }
                                      }


                                      int index = 0;
                                      seda[index++] = m == 0 ? sounds[h] : sounds[h];

                                      if (m < 20)
                                          seda[index++] = sounds[m];
                                      else {
                                          int m10 = m / 10;
                                          int m1 = m % 10;
                                          seda[index++] = m1 == 0 ? sound2[m10] : sound3[m10];

                                          if (m1 != 0)
                                              seda[index++] = sounds[m1];

                                      }


                                      MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.clock);
                                      mp.setOnCompletionListener(MainActivity.this);
                                      mp.start();
                                  }
                              });
                              sp.edit().putInt("my", h).apply();
                          }

                          public void onCompletion(MediaPlayer mp) {

                              if (seda[loc] != 0) {
                                  MediaPlayer mp2 = MediaPlayer.create(MainActivity.this, seda[loc]);
                                  loc++;
                                  mp2.setOnCompletionListener(MainActivity.this);
                                  mp2.start();
                              }
                          }

                      });

               }
  }
