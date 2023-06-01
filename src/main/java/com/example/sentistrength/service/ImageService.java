package com.example.sentistrength.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImageService {
    public void runPy() throws IOException, InterruptedException {

        System.out.println("access service");
        String[] order1=new String[]{"python3","makefile1.py"};
        String[] order2=new String[]{"python3","makefile2.py"};
        String[] order3=new String[]{"python3","makefile3.py"};
        ProcessBuilder po1=new ProcessBuilder();
        ProcessBuilder po2=new ProcessBuilder();
        ProcessBuilder po3=new ProcessBuilder();

        po1.command(order1);
        po2.command(order2);
        po3.command(order3);
        System.out.println("finish build command!");
        Process p1=po1.start();
        int exit1=p1.waitFor();
        System.out.println("code:"+exit1);
        Process p2=po2.start();
        int exit2=p2.waitFor(); System.out.println("code:"+exit2);
        Process p3=po3.start();
        System.out.println("command start!");


        int exit3=p3.waitFor();


        System.out.println("code:"+exit3);
    }
}
