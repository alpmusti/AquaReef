package com.cantekin.aquareef.AquaLink;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

/**
 * Created by Cantekin on 19.8.2017.
 */

public class Utils {
    public static int TTS = 2;
    public static int RESPONSE_CMD = 3;
    public static int RESPONSE_TTS = 4;
    public static final String PREFERENCES_MODULE_MID = "module_mid";
    public static final String PREFERENCES_SCAN_RESULT_PASSWD = "scan_result";
    public static final String SECURITY_WEP = "wep";
    public static final String SECURITY_OPEN = "OPEN";
    public static final String SECURITY_SHARED = "SHARED";
    public static final String SECURITY_WPAPSK = "WPAPSK";
    public static final String SECURITY_WPA2PSK = "WPA2PSK";
    public static final String SECURITY_NONE = "NONE";
    public static final String SECURITY_WEP_A = "WEP-A";
    public static final String SECURITY_WEP_H = "WEP-H";
    public static final String SECURITY_TKIP = "TKIP";
    public static final String SECURITY_AES = "AES";
    public static final String SECURITY_OPEN_NONE = "open,none";
    public static final String SECURITY_OPEN_WEP_A = "open,wep-a";
    public static final String SECURITY_OPEN_WEP_H = "open,wep-h";
    public static final String SECURITY_SHARED_WEP_A = "shared,wep-a";
    public static final String SECURITY_SHARED_WEP_H = "shared,wep-h";
    public static final String SECURITY_WPAPSK_AES = "wpapsk,aes";
    public static final String SECURITY_WPAPSK_TKIP = "wpapsk,tkip";
    public static final String SECURITY_WPA2PSK_AES = "wpa2psk,aes";
    public static final String SECURITY_WPA2PSK_TKIP = "wpa2psk,tkip";
    private static final String KEYSTORE_SPACE = "keystore://";
    public static final String LAST_SCAN_RESULT_CONNECTED = "last_scan_result_connected";
    public static final int WEP_ASCII = 1;
    public static final int WEP_HEX = 2;
    public static final int WEP_INVALID = -1;


    public synchronized static String removeDoubleQuotes(String string) {
        if (string == null) {
            return null;
        }
        int length = string.length();
        if ((length > 1) && (string.charAt(0) == '"')
                && (string.charAt(length - 1) == '"')) {
            return string.substring(1, length - 1);
        }
        return string;
    }


    public static ScanResult scan( List<ScanResult> scanResults,String ssid) {
        ScanResult mConnect2ScanResult = null;
        if (scanResults != null) {
            for (int i = 0; i < scanResults.size(); i++) {
                Log.w("scanResults", scanResults.get(i).SSID);
                if (scanResults.get(i).SSID.equals(ssid)) {
                    mConnect2ScanResult = scanResults.get(i);
                    break;
                }
            }
        }

        return mConnect2ScanResult;
    }

    public static String generateWskeyCmd(ScanResult scanResult, String password) {

        String cmd = null;
        String security = parseSecurity(scanResult.capabilities);
        if (Utils.SECURITY_OPEN_NONE.equals(security)) {
            cmd = generateWskeyCmd(Utils.SECURITY_OPEN, Utils.SECURITY_NONE);
        } else {

            if (Utils.SECURITY_WEP.equals(security)) {

                if (checkWepType(password) == Utils.WEP_ASCII) {
                    cmd = generateWskeyCmd(Utils.SECURITY_SHARED, Utils.SECURITY_WEP_A, password);
                } else {
                    cmd = generateWskeyCmd(Utils.SECURITY_SHARED, Utils.SECURITY_WEP_H, password);
                }
            } else if (Utils.SECURITY_WPA2PSK_AES.equals(security)) {
                cmd = generateWskeyCmd(Utils.SECURITY_WPA2PSK, Utils.SECURITY_AES, password);
            } else if (Utils.SECURITY_WPA2PSK_TKIP.equals(security)) {
                cmd = generateWskeyCmd(Utils.SECURITY_WPA2PSK, Utils.SECURITY_TKIP, password);
            } else if (Utils.SECURITY_WPAPSK_AES.equals(security)) {
                cmd = generateWskeyCmd(Utils.SECURITY_WPAPSK, Utils.SECURITY_AES, password);
            } else if (Utils.SECURITY_WPAPSK_TKIP.equals(security)) {
                cmd = generateWskeyCmd(Utils.SECURITY_WPAPSK, Utils.SECURITY_TKIP, password);
            }
        }

        return cmd;
    }

    public static String generateWskeyCmd(String auth, String encry, String password) {
        return String.format(Constants.CMD_WSKEY, auth + "," + encry + "," + password);
    }

    public static String generateWskeyCmd(String auth, String encry) {
        return String.format(Constants.CMD_WSKEY, auth + "," + encry);
    }

    public synchronized static final String parseSecurity(String capabilities) {

        if (capabilities == null) {
            return null;
        }

        capabilities = capabilities.replace("][", ";").replace("[", "").replace("]", "");
        System.out.println("capabilities: " + capabilities);

        if (capabilities.contains("WEP")) {
            return Utils.SECURITY_WEP;
        }

        int wpa = -1;
        int wpa2 = -1;
        String[] caps = capabilities.split(";");
        for (int i = 0; i < caps.length; i++) {
            if (caps[i].contains("WPA2") && caps[i].contains("PSK")) {
                wpa2 = i;
            } else if (caps[i].contains("WPA") && caps[i].contains("PSK")) {
                wpa = i;
            }
        }

        if (wpa2 != -1) {

            if (caps[wpa2].contains("CCMP")) {
                return Utils.SECURITY_WPA2PSK_AES;
            }
            if (caps[wpa2].contains("TKIP")) {
                return Utils.SECURITY_WPA2PSK_TKIP;
            }
        }

        if (wpa != -1) {

            if (caps[wpa].contains("CCMP")) {
                return Utils.SECURITY_WPAPSK_AES;
            }
            if (caps[wpa].contains("TKIP")) {
                return Utils.SECURITY_WPAPSK_TKIP;
            }
        }

        return Utils.SECURITY_OPEN_NONE;
    }

    public static int checkWepType(String password) {
        if (password == null) {
            return Utils.WEP_INVALID;
        }

        password = password.trim();
        int length = password.length();
        if (length == 5 || length == 13) {
            return Utils.WEP_ASCII;
        } else if ((length == 10 && password.matches("[0-9A-Fa-f]{10}")) || (length == 26 && password.matches("[0-9A-Fa-f]{26}"))) {
            return Utils.WEP_HEX;
        } else {
            return Utils.WEP_INVALID;
        }
    }
}
