package com.alper.flagquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

public class QuizActivity extends AppCompatActivity {
    private TextView textViewDogru,textViewYanlis,textViewSoruSayisi;
    private ImageView imageViewBayrak;
    private Button ButtonA,ButtonB,ButtonC,ButtonD;
    private ArrayList<Bayraklar> sorularListe;
    private ArrayList<Bayraklar> yanlisSeceneklerListe;
    private Bayraklar dogruSoru;
    private Veritabani vt;
    //Sayclar
    private int soruSayac = 0;
    private int yanlisSayac = 0;
    private int dogruSayac = 0;
    //Secenekleri
    private HashSet<Bayraklar> secenekleriKaristirmaListe = new HashSet<>();
    private ArrayList<Bayraklar> seceneklerListe = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewDogru = findViewById(R.id.textViewDogru);
        textViewYanlis = findViewById(R.id.textViewYanlis);
        textViewSoruSayisi = findViewById(R.id.textViewSoruSayi);

        imageViewBayrak = findViewById(R.id.imageViewBayrak);
        ButtonA = findViewById(R.id.buttonA);
        ButtonB = findViewById(R.id.buttonB);
        ButtonC = findViewById(R.id.buttonC);
        ButtonD = findViewById(R.id.buttonD);

        vt = new Veritabani(this);

        sorularListe = new BayraklarDao().rastgele5Getir(vt);

        soruYukle();

        ButtonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dogruKontrol(ButtonA);
                sayacKontrol();
            }
        });

        ButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dogruKontrol(ButtonB);
                sayacKontrol();
            }
        });

        ButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dogruKontrol(ButtonC);
                sayacKontrol();
            }
        });

        ButtonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dogruKontrol(ButtonD);
                sayacKontrol();
            }
        });

    }

    public void soruYukle(){
        textViewSoruSayisi.setText((soruSayac+1)+".Soru");
        textViewDogru.setText("Dogru : " + dogruSayac);
        textViewYanlis.setText("Dogru : "+ yanlisSayac);

        dogruSoru = sorularListe.get(soruSayac);

        yanlisSeceneklerListe = new BayraklarDao().rasgele3YanlisSecenekGetir(vt,dogruSoru.getBayrak_id());

        imageViewBayrak.setImageResource(getResources().getIdentifier(dogruSoru.getBayrak_resim(),"drawable",getPackageName()));

        secenekleriKaristirmaListe.clear();
        secenekleriKaristirmaListe.add(dogruSoru);
        secenekleriKaristirmaListe.add(yanlisSeceneklerListe.get(0));
        secenekleriKaristirmaListe.add(yanlisSeceneklerListe.get(1));
        secenekleriKaristirmaListe.add(yanlisSeceneklerListe.get(2));

        seceneklerListe.clear();

        for (Bayraklar b: secenekleriKaristirmaListe){
            seceneklerListe.add(b);
        }

        ButtonA.setText(seceneklerListe.get(0).getBayrak_ad());
        ButtonB.setText(seceneklerListe.get(1).getBayrak_ad());
        ButtonC.setText(seceneklerListe.get(2).getBayrak_ad());
        ButtonD.setText(seceneklerListe.get(3).getBayrak_ad());

    }

    public void dogruKontrol(Button button){
        String buttonYazi = button.getText().toString();
        String dogruCevap = dogruSoru.getBayrak_ad();

        if(buttonYazi.equals(dogruCevap)){
            dogruSayac++;
        }else{
            yanlisSayac++;
        }
        textViewDogru.setText("Dogru : "+ dogruSayac);
        textViewDogru.setText("Yanlis : "+ yanlisSayac);
    }

    public void sayacKontrol(){
        soruSayac++;
        if(soruSayac!=5){
            soruYukle();
        }else{
            Intent intent = new Intent(QuizActivity.this,ResultActivity.class);
            intent.putExtra("dogruSayac",dogruSayac);
            startActivity(intent);

            finish();
        }
    }
}