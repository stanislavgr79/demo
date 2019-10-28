package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(value = { "index", "home"}, method = RequestMethod.GET)
    public String doIndex() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        if(email.equals("anonymousUser")){
            System.out.println("========");
            System.out.println("index controllerGET say: email principal context : " + email +
                    " you index page not have role view");
            System.out.println("========");
        } else {
            System.out.println("==========");
            System.out.print("index controllerGET say: email principal context : " + email +
                    " you index page change by role");
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            System.out.println(". You: "+ userDetail.getUsername());
            System.out.println("=========== ");
        }

        return "index";
    }

    @RequestMapping("login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout, Model model) {
        if (error != null)
            model.addAttribute("error", "Invalid username and Password");
        if (logout != null){

            model.addAttribute("logout", "You have logged out successfully");
        }

        return "login";
    }

    @RequestMapping(value = { "accountInfo" }, method = RequestMethod.GET)
    public String accountInfo(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getPassword());
        System.out.println(userDetails.getUsername());
//        System.out.println(userDetails.isEnabled());

        model.addAttribute("userDetails", userDetails);
        return "accountInfo";
    }
}
