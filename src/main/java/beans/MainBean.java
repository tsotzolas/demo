/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import model.*;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;


/**
 * @author takis
 */

@Named("MainBean")
@ViewScoped
@Getter
@Setter
@Slf4j
public class MainBean implements Serializable {

    private String welcomeMsg;
    private String welcomeWarn;


    @Inject
    private LoginBean loginBean;

    @PostConstruct
    public void init() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 5 && timeOfDay < 12){
            welcomeMsg = "Good Morning ";
        }else if(timeOfDay >= 12 && timeOfDay < 18){
            welcomeMsg = "Good Afternoon ";
        }else if(timeOfDay >= 18 && timeOfDay < 21){
            welcomeMsg = "Good Evening ";
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            welcomeMsg = "Good Night ";
        }else if(timeOfDay >= 0 && timeOfDay < 5){
            welcomeMsg = "It is not a good time for work ";
        }

        Tbluser user = loginBean.getUser();
        if(user.getIdEmployee().getGenderMale()==1){
            welcomeMsg = welcomeMsg + "Mr ";
        } else {
            welcomeMsg = welcomeMsg + "Mrs ";
        }
        welcomeMsg = welcomeMsg + user.getIdEmployee().getEmployeeLname();

        if(Objects.nonNull(user.getExpDate())) {
            Date currentDate = new Date();
            long expDiff = user.getExpDate().getTime() - currentDate.getTime();
            float days = (expDiff / (1000 * 60 * 60 * 24));
            expDiff = (long) days;

            if (expDiff < 15) {
                if (expDiff == 1) {
                    welcomeWarn = "!!!!! Your account password will expire Tomorrow. Please consider to change it!!!!";
                } else if (expDiff == 0) {
                    welcomeWarn = "!!!!! Your account password will expire Today. If you don't change it your account will lock!!!!";
                } else {
                    welcomeWarn = "!!!!! Your account password will expire in " + expDiff + " days. Please consider to change it!!!!";
                }
            }
        }


//        model = new DefaultDashboardModel();
//        DashboardColumn column1 = new DefaultDashboardColumn();
//        DashboardColumn column2 = new DefaultDashboardColumn();
//        DashboardColumn column3 = new DefaultDashboardColumn();
//        DashboardColumn column4 = new DefaultDashboardColumn();
//        DashboardColumn column5 = new DefaultDashboardColumn();
//
//
//        column1.addWidget("weather");
//
//        column2.addWidget("weather1");
//        column2.addWidget("calendar");
//        column3.addWidget("latestNews");
//        column3.addWidget("latestNews1");
//        column4.addWidget("firstPage");
//        column5.addWidget("stockmarket1");
//        column5.addWidget("stockmarket2");
//        column4.addWidget("stockmarket3");
//
//        model.addColumn(column1);
//
//        model.addColumn(column3);
//        model.addColumn(column4);
//        model.addColumn(column5);
//
//        String q = "";
//        Calendar calendar = Calendar.getInstance();
//        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

    }
}

