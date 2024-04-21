/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.FileInputStream;
import java.io.Serializable;

@Named("ErrorBean")
@ViewScoped
@Slf4j
public class ErrorBean implements Serializable {


    @PostConstruct
    public void init() {
    }

    public void error1(){
        try {
            log.error("This is a error");

            int c = 10, b = 0;
            System.out.println(c/b);

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public void error2(){
        try {

            String test = null;
            test.toString();

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public void error3(){
        FileInputStream fis = null;
        try {

            fis = new FileInputStream("B:/myfile.txt");

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public void error4(){
        try {
            int arr[] = new int[5];

            // Array size is 5
            // whereas this statement assigns

            // value 250 at the 10th position.
            arr[9] = 250;

            System.out.println("Value assigned! ");

        }catch (Exception e){
            e.printStackTrace();

        }
    }


}