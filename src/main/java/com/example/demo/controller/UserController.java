package com.example.demo.controller;

import com.example.demo.domain.entity.person.Role;
import com.example.demo.domain.entity.person.User;
import com.example.demo.domain.model.UserDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "admin/getUsersNotCustomer", method = RequestMethod.GET)
    public String viewUsersManager (Model uiModel){
        List<User> users = userService.getUsersNotCustomer();
        List<Role> roles = userService.getAllRole();
        uiModel.addAttribute("users", users);
        uiModel.addAttribute("Roles", roles);

        return "usersList";
    }

    @RequestMapping(value = "admin/createUser", method = RequestMethod.GET)
    public ModelAndView  getUserForm() {
       List<Role> roles = userService.getAllRole();
       UserDTO userDTO = new UserDTO();
       ModelAndView mav = new ModelAndView();
       mav.addObject("Roles", roles);
       mav.addObject("UserDto", userDTO);
       mav.setViewName("regUser");
        return mav;
    }

    @RequestMapping(value = "admin/createUser", method = RequestMethod.POST)
    public String  regUserForm(@ModelAttribute(value = "UserDto") UserDTO userDTO) {
        userService.createUserFromUserDTO(userDTO);
        return "redirect:/admin/getUsersNotCustomer";
    }

    @RequestMapping(value = "admin/deleteUser/{userId}")
    public String deleteProduct(@PathVariable(value = "userId") Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/getUsersNotCustomer";
    }

    @RequestMapping(value = "admin/editUser/{userId}")
    public ModelAndView getEditUserForm(@PathVariable(value = "userId") Long id) {
        User user = userService.getUserById(id);
        return new ModelAndView("editUser", "user", user);
    }

    @RequestMapping(value = "admin/editUser", method = RequestMethod.POST)
    public String editUser(@ModelAttribute(value = "user") User user) {
        userService.updateUserStatus(user);
        return "redirect:/admin/getUsersNotCustomer";
    }

}
