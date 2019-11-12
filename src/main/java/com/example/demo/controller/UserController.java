package com.example.demo.controller;

import com.example.demo.domain.entity.person.Role;
import com.example.demo.domain.entity.person.User;
import com.example.demo.domain.model.UserDTO;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "secure/getUsersNotCustomer", method = RequestMethod.GET)
    public String viewUsersManager (Model uiModel){
        List<User> users = userService.getUsersNotCustomer();
        List<Role> roles = userService.getAllRole();
        uiModel.addAttribute("users", users);
        uiModel.addAttribute("Roles", roles);
        logger.info("Model take List<User>NotCustomer, size= " + users.size() +
                " List<Role>, size= " + roles.size());
        return "usersList";
    }

    @RequestMapping(value = "secure/createUser", method = RequestMethod.GET)
    public ModelAndView  getUserForm() {
       List<Role> roles = userService.getAllRole();
       UserDTO userDTO = new UserDTO();
       ModelAndView mav = new ModelAndView();
       mav.addObject("Roles", roles);
       mav.addObject("UserDto", userDTO);
       mav.setViewName("regUser");
       logger.info("ModelAndView take List<Role>, size= " + roles.size() +
                " and new userDTO" + userDTO);
        return mav;
    }

    @RequestMapping(value = "secure/createUser", method = RequestMethod.POST)
    public String  regUserForm(@ModelAttribute(value = "UserDto") UserDTO userDTO) {
        logger.info("UserDto send to createUserFromUserDTO, userDTO= " + userDTO);
        userService.createUserFromUserDTO(userDTO);
        return "redirect:/secure/getUsersNotCustomer";
    }

    @RequestMapping(value = "secure/deleteUser/{userId}")
    public String deleteUser(@PathVariable(value = "userId") Long userId) {
        logger.info("User Id send to delete by id= " + userId);
        userService.deleteUser(userId);
        return "redirect:/secure/getUsersNotCustomer";
    }

    @RequestMapping(value = "secure/editUser/{userId}")
    public ModelAndView getEditUserForm(@PathVariable(value = "userId") Long id) {
        User user = userService.getUserById(id);
        logger.info("User getById for editForm, user= " + user);
        return new ModelAndView("editUser", "user", user);
    }

    @RequestMapping(value = "secure/editUser", method = RequestMethod.POST)
    public String editUser(@ModelAttribute(value = "user") User user) {
        logger.info("User send to updateUserStatus, user= " + user);
        userService.updateUserStatus(user);
        return "redirect:/secure/getUsersNotCustomer";
    }

}
