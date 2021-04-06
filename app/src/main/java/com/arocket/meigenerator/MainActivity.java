package com.arocket.meigenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.arocket.meigenerator.MeiUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // oneplus6 869897031961591
        System.out.println("Checksum OnePlus6 IMEI:" + MeiUtil.calculateImei("86989703196159x"));
        // mi6 865441036374965
        System.out.println("Checksum Mi6 IMEI:" + MeiUtil.calculateImei("86544103637496x"));

        // oneplus6 99001157901920 (14 Byte String has no Chechsum, get origin return)
        System.out.println("Checksum OnePlus6 MEID:" + MeiUtil.calculateMeid("9900115790192x"));
        // mi6 A0000073352494 (14 Byte String has no Chechsum, get origin return)
        System.out.println("Mi6 MEID:" + MeiUtil.calculateMeid("A000007335249x"));

        // random generate IMEI & meid, we can checksum it as follow link, to "Decode" the "IMEI/MEID Number"
        // http://phone.fyicenter.com/1268_IMEI_MEID_Number_Checker_Decoder.html#Result
        System.out.println("OnePlus6 Random IMEI:" + MeiUtil.getRandomIMEI(0));
        System.out.println("OnePlus6 Random IMEI:" + MeiUtil.getRandomMEID(0));

    }
}