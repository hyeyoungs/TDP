package com.cdp.tdp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Slf4j
@Service
public class HttpCallService {

    public String Call(String method, String reqURL, String header, String param) {
        String result = "";
        try {
            String response = "";
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Authorization", header);
            if(param != null) {
                log.info("param : " + param);
                conn.setDoOutput(true);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                bw.write(param);
                bw.flush();

            }
            int responseCode = conn.getResponseCode();

            InputStream stream = conn.getErrorStream();
            if (stream != null) {
                try (Scanner scanner = new Scanner(stream)) {
                    scanner.useDelimiter("\\Z");
                    response = scanner.next();
                }
                log.info("error response : " + response);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);
            log.info(result);
            br.close();
        } catch (IOException e) {
            return e.getMessage();
        }
        return result;
    }
//
//    public String CallwithToken(String method, String reqURL, String access_Token) {
//        return CallwithToken(method, reqURL, access_Token, null);
//    }

    public String CallwithToken(String method, String reqURL, String access_Token, String param)
    {

        String header = "Bearer " + access_Token;
        return Call(method, reqURL, header, param);
    }
}
