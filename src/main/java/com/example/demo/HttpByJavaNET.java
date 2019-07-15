package com.example.demo;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpByJavaNET {
    static{
        //for localhost testing only
        HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier(){
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        if (s.equals("localhost")) {
                            return true;
                        }
                        return false;
                    }
                });
        try {
            SSLSocketFactory factory = getSSLFactory();
            HttpsURLConnection.setDefaultSSLSocketFactory(factory);
        } catch (KeyManagementException | NoSuchAlgorithmException e) {}
    }

    /**
     * 調用 Http Get 方法
     *
     */
    public static String get(String url) throws IOException {
        URL target_url =new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) target_url.openConnection();
        return getResponse(conn.getInputStream());
    }
    /**
     * 從InputStream 取得回應資料
     * @param  inStream 串流資料
     * @return 伺服器回應
     * @throws IOException
     */
    private static String getResponse(InputStream inStream) throws IOException {
        //資料串流處理利用構造函數傳入  InputStream->InputStreamReader->BufferedReader
        BufferedReader rsv =new BufferedReader(new InputStreamReader(inStream));
        String line =null;
        StringBuffer buffer =new StringBuffer();
        while ((line = rsv.readLine()) != null) {
            buffer.append(line+"\n");
        }
        return buffer.toString();
    }
    /**
     * 產生一個SSL連線
     * @return SSLSocketFactory SSL連線工廠
     * @throws NoSuchAlgorithmException  未找到文件，錯誤的密碼，錯誤的密鑰存儲類型...
     * @throws KeyManagementException    密鑰管理異常
     *
     */
    private  static SSLSocketFactory getSSLFactory() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] tm =new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    @Override
                    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    }
                    @Override
                    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    }
                }
        };
        SSLContext ctx = SSLContext.getInstance("SSL");
        ctx.init(null, tm, new java.security.SecureRandom());
        return ctx.getSocketFactory();
    }

}