package com.dawn.web.solution;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Author: Cc
 * @Date: 2020/7/2 11:40
 * @Description:
 */
public class CebToPdf {
    public static void main(String[] args)
    {
        BufferedReader br = null;
        BufferedReader brError = null;
        String cmd = "E:\\ceb\\ceb2pdf\\64\\ceb2pdf.exe E:\\ceb\\before.ceb";
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            String line = null;
            br = new BufferedReader(new InputStreamReader(p.getInputStream(),"UTF-8"));
            brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = br.readLine()) != null  || (line = brError.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

 

}
