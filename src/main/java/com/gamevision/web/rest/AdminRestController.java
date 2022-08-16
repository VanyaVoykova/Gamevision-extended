package com.gamevision.web.rest;

import com.gamevision.errorhandling.exceptions.UserNotFoundException;
import com.gamevision.errors.ErrorApiResponse;
import com.gamevision.model.binding.UserManageBindingModel;
import com.gamevision.model.view.UserAdministrationViewModel;
import com.gamevision.service.AdminService;
import com.gamevision.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
//@RequestMapping("/admin")
public class AdminRestController {
    private final UserService userService;
    private final AdminService adminService;

    public AdminRestController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    //  @GetMapping("/admin") //is loaded by a regular @AdminController
    @RequestMapping("/admin")
    public ModelAndView adminPanel() {
        return new ModelAndView("admin-panel");
    }

    //These return UserAdministrationViewModel that has extra data compared from the regular UserViewModel.
    //This was meant to provide admins a way to check the user's role and active status from the app itself.
    //But time is short...

    @PutMapping("/admin/promote") //todo need to get the value somehow
    @ResponseStatus(HttpStatus.OK)
    //UserManageBindingModel holds username only; UserViewModel holds name and profilePictureId
    public ResponseEntity<UserAdministrationViewModel> promoteUser(@AuthenticationPrincipal UserDetails userDetails,
                                                                   @Validated @RequestBody UserManageBindingModel userManageBindingModel) {

        //ExceptionHandler should handle any exceptions here
        //NO ERROR HANDLING HERE!!!!!!!!!! The JS needs either a throw from here or the @ExceptionHandler below!!!

        adminService.promoteUserToAdmin(userManageBindingModel.getUsername());

        UserAdministrationViewModel userAdminVM = adminService.promoteUserToAdmin(userManageBindingModel.getUsername());

        return ResponseEntity.status(200).body(userAdminVM);
        // return String.format("User %s promoted successfully", userManageBindingModel.getUsername());

        //ResponseEntity.ok();
        //                      (URI.create(String.format("/admin/promote/%d", userAdminVM.getId()))).body(userAdminVM);
    }


    @PutMapping("/admin/demote")
    public ResponseEntity<UserAdministrationViewModel> demoteUser(@AuthenticationPrincipal UserDetails userDetails,
                                                                  @Validated @RequestBody UserManageBindingModel userManageBindingModel) {

        //ExceptionHandler should handle any exceptions here
        UserAdministrationViewModel userAdminVM = adminService.demoteUserToUser(userManageBindingModel.getUsername());

        return ResponseEntity.status(200).body(userAdminVM);
    }


    @PutMapping("/admin/ban")
    public ResponseEntity<UserAdministrationViewModel> banUser(@AuthenticationPrincipal UserDetails userDetails,
                                                               @Validated @RequestBody UserManageBindingModel userManageBindingModel) {

        //ExceptionHandler should handle any exceptions here
        UserAdministrationViewModel userAdminVM = adminService.banUser(userManageBindingModel.getUsername());

        return ResponseEntity.status(200).body(userAdminVM);
    }

    @PutMapping("/admin/unban")
    public ResponseEntity<UserAdministrationViewModel> unbanUser(@AuthenticationPrincipal UserDetails userDetails,
                                                                 @Validated @RequestBody UserManageBindingModel userManageBindingModel) {

        //ExceptionHandler should handle any exceptions here
        UserAdministrationViewModel userAdminVM = adminService.unbanUser(userManageBindingModel.getUsername());

        return ResponseEntity.status(200).body(userAdminVM);
    }


    @ExceptionHandler({UserNotFoundException.class}) //overrides the response with our custom ErrorApiResponse
    public ResponseEntity<ErrorApiResponse> handleUserNotFound() {
        return ResponseEntity.status(404).body(new ErrorApiResponse("User not found!", 1004));
    }


}

