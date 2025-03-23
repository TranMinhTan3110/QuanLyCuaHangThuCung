package controller;

import dao.LoginDAO.implement.UserResposittoryImpl;
import model.entity.User;
import model.request.LoginRequest;
import model.response.LoginResponse;
import respository.userRespositorty;
import service.AuthService;
import view.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginView views;
    private  AuthService authService;
    public LoginController(LoginView view,  AuthService authService) {
        this.views = view;
        this.authService = authService;
        this.views.addLoginListener(e->getRequest()

                );
    }
   // @Override
//    public void actionPerformed(ActionEvent e) {
//        String command = e.getActionCommand(); // Lấy action command của button
//
//        if ("btnLogin".equals(command)) {
//            System.out.println("Nút đăng nhập được nhấn!");
//            LoginResponse response = authService.login(getRequest());
//            if(!response.isSuccess()){
//                System.out.println(response.getMessage());
//            } else {
//                System.out.println(response.getMessage());
//            }
//        }
//    }


    public void getRequest() {
        String password = views.getPassword();
        String userName = views.getUsername();
    boolean check =     authService.checkLogin(userName,password);
      if(check==true){
          System.out.println("Đăng nhập thành công!");
      }
      else{
          System.out.println("Đăng nhập thất bại!");
      }
    }
}

