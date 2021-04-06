package com.arocket.meigenerator;


import java.math.BigInteger;
import java.util.Random;

/**
 * IMEI是国际移动通讯设备识别号(International Mobile Equipment Identity)的缩写，用于GSM系统。
 * 由15位数字组成，前6位(TAC)是型号核准号码，代表手机类型。接着2位(FAC)是最后装配号，代表产地。后6位(SNR)是串号，代表生产顺序号。最后1位(SP)是检验码。
 * MEID是移动通讯设备识别号(Mobile Equipment IDentifier)的缩写，用于CDMA系统。
 * 由15位16进制数字组成，前8位是生产商编号，后6位是串号，最后1位是检验码。
 */
public class MeiUtil {
    //=======================================================================================
    /**
     * Spcified IMEI/MEID number: 869897031961591
     * Reporting Body Identifier (RBI): 86 - Terminal Industry Forum Association (TAF), China (2位)
     * Type Allocation Code (TAC): 989703 (6位)
     * Serial Number (SN): 196159 (6位)
     * Checksum: 1（1位）
     * */
    private final static String[] imei_prefix = {"86989703"};

    /**
     * Spcified IMEI/MEID number: 99 001157 901920
     * Reporting Body Identifier (RBI): 99 - Global Hexadecimal Administrator (GHA) / Telecommunications Industry Association (TIA) (2位)
     * Type Allocation Code (TAC): 001157 (6位)
     * Serial Number (SN): 901920 (6位)
     */
    private final static String[] meid_prefix = {"99001157"};

    private final static String[] wireless_mac_prefix = {"64:A2:F9"};

    private final static String[] p2p0_mac_prefix = {"6C:Q2:R9"};

    //=======================================================================================


    /**
     * 14位的MEID不需要校验码
     * Spcified IMEI/MEID number: 99 001157 901920
     * Reporting Body Identifier (RBI): 99 - Global Hexadecimal Administrator (GHA) / Telecommunications Industry Association (TIA)
     * Type Allocation Code (TAC): 001157
     * Serial Number (SN): 901920
     */
    public static String calculateMeid(String meid) {
        if(14 != meid.length())
            return "";
        return getmeid14(meid);
    }

    /**
     * @param phoneType
     *      0 -- oneplus6
     *
     */
    public static String getRandomIMEI(int phoneType) {
        String imeiId = "" + imei_prefix[phoneType];
        Random rnd = new Random();
        String number = "" + rnd.nextInt(999999);
        imeiId += number;
        imeiId += "x";
        return calculateImei(imeiId);
    }

    public static String getRandomMEID(int phoneType) {
        String meid = "" + meid_prefix[phoneType];
        Random rnd = new Random();
        String number = "" + rnd.nextInt(999999);
        meid += number;
        return calculateMeid(meid);
    }

    public static String getRandomSerialNo() {
        String serialNo = "";
        Random rnd = new Random();
        long serialNo_pre = 0x1111 + rnd.nextInt(0xffff - 0x2222);
        long serialNo_post = 0x1111 + rnd.nextInt(0xffff - 0x2222);
        serialNo += Long.toHexString(serialNo_pre);
        serialNo += Long.toHexString(serialNo_post);
        return serialNo;
    }

    public static String getRandomAndroidId() {
        String serialNo = "";
        Random rnd = new Random();
        long serialNo_1 = 0x1111 + rnd.nextInt(0xffff - 0x2222);
        long serialNo_2 = 0x1111 + rnd.nextInt(0xffff - 0x2222);
        long serialNo_3 = 0x1111 + rnd.nextInt(0xffff - 0x2222);
        long serialNo_4 = 0x1111 + rnd.nextInt(0xffff - 0x2222);
        serialNo += Long.toHexString(serialNo_1);
        serialNo += Long.toHexString(serialNo_2);
        serialNo += Long.toHexString(serialNo_3);
        serialNo += Long.toHexString(serialNo_4);
        return serialNo;
    }

    public static String getRandomWirelessMac(int phoneType) {
        Random random = new Random();
        String wireless_mac = wireless_mac_prefix[phoneType];
        wireless_mac += String.format(":%02X", random.nextInt(0xfe));
        wireless_mac += String.format(":%02X", random.nextInt(0xfe));
        wireless_mac += String.format(":%02X", random.nextInt(0xfe));
        return wireless_mac;
    }

    public static String getRandomP2p0Mac(int phoneType) {
        Random random = new Random();
        String p2p0_mac = p2p0_mac_prefix[phoneType];
        p2p0_mac += String.format(":%02X", random.nextInt(0xfe));
        p2p0_mac += String.format(":%02X", random.nextInt(0xfe));
        p2p0_mac += String.format(":%02X", random.nextInt(0xfe));
        return p2p0_mac;
    }

    // we need wifi mac as template there
    public static String getRandomBtMac(String WifiMac) {
        String btMac;
        Random random = new Random();
        String[] buff = WifiMac.split(":");
        // as real info，we just change last %02x in mac address
        String newMacBit = String.format(":%02X", random.nextInt(0xfe));
        while(newMacBit.equals(buff[buff.length - 1])) {
            newMacBit = String.format(":%02X", random.nextInt(0xfe));
        }
        btMac = WifiMac.substring(0, WifiMac.length() - 3) + newMacBit;
        return btMac;
    }

    /**
     * 格式化IMEI
     * 因为IMEI格式不统一，长度有14位和16位的，所以，为了统一，将14位和16位的MEID，统一设置为15位的 设置格式：
     * 如果IMEI长度为14位，那么直接得到第15位，如果MEID长度为16位，那么直接在根据前14位得到第15位
     * 如果IMEI长度为其他长度，那么直接返回原值
     * @param imei
     * @return
     */
    public static String calculateImei(String imei) {
        int dxml = imei.length();
        if (dxml != 15) {
            return imei;
        }
        String imeiRes = "";

        imeiRes =  imei.substring(0,14) + getimei15(imei.substring(0,14));
        return imeiRes;
    }
    /**
     * 14位的MEID不需要校验码
     * Spcified IMEI/MEID number: 99 001157 901920
     * Reporting Body Identifier (RBI): 99 - Global Hexadecimal Administrator (GHA) / Telecommunications Industry Association (TIA)
     * Type Allocation Code (TAC): 001157
     * Serial Number (SN): 901920
     */
    private static String getmeid14(String meid) {
        return meid;
    }
    /**
     * 根据IMEI的前14位，得到第15位的校验位
     * IMEI校验码算法：
     * (1).将偶数位数字分别乘以2，分别计算个位数和十位数之和
     * (2).将奇数位数字相加，再加上上一步算得的值
     * (3).如果得出的数个位是0则校验位为0，否则为10减去个位数
     * 如：35 89 01 80 69 72 41 偶数位乘以2得到5*2=10 9*2=18 1*2=02 0*2=00 9*2=18 2*2=04 1*2=02,计算奇数位数字之和和偶数位个位十位之和，
     * 得到 3+(1+0)+8+(1+8)+0+(0+2)+8+(0+0)+6+(1+8)+7+(0+4)+4+(0+2)=63
     * 校验位 10-3 = 7
     * @param imei
     * @return
     */
    private static String getimei15(String imei){
        if (imei.length() == 14) {
            char[] imeiChar=imei.toCharArray();
            int resultInt=0;
            for (int i = 0; i < imeiChar.length; i++) {
                int a=Integer.parseInt(String.valueOf(imeiChar[i]));
                i++;
                final int temp=Integer.parseInt(String.valueOf(imeiChar[i]))*2;
                final int b=temp<10?temp:temp-9;
                resultInt+=a+b;
            }
            resultInt%=10;
            resultInt=resultInt==0?0:10-resultInt;
            return resultInt + "";
        }else{
            return "";
        }
    }
}