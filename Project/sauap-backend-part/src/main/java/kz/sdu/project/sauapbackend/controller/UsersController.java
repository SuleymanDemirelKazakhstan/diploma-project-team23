package kz.sdu.project.sauapbackend.controller;

import kz.sdu.project.sauapbackend.dto.UserDto;
import kz.sdu.project.sauapbackend.dto.request.AddNewMonthlyDonationRequestDto;
import kz.sdu.project.sauapbackend.dto.request.ChangePasswordDto;
import kz.sdu.project.sauapbackend.dto.request.UpdateUserDto;
import kz.sdu.project.sauapbackend.dto.response.GetUserMonthDonationResponseDto;
import kz.sdu.project.sauapbackend.dto.response.ServerDefaultResponse;
import kz.sdu.project.sauapbackend.service.UserMonthlyDonateService;
import kz.sdu.project.sauapbackend.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    private final UserMonthlyDonateService userMonthlyDonateService;

    @Autowired
    public UsersController(UsersService usersService,
                           UserMonthlyDonateService userMonthlyDonateService) {
        this.usersService = usersService;
        this.userMonthlyDonateService = userMonthlyDonateService;
    }

    @PostMapping(value = "/registration",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServerDefaultResponse> registerNewAccount(@RequestBody UserDto userDto) {
        return ResponseEntity.ok().body(usersService.registerNewAccount(userDto));
    }

    @GetMapping(value = "/by-email",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok().body(usersService.getUserByEmail(email));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDto userDto,
                                        @RequestParam("email") String email) {
        usersService.updateUser(userDto, email);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/check-password")
    public ResponseEntity<?> checkUserPassword(@RequestParam("email") String email,
                                               @RequestParam("password") String password) {
        return ResponseEntity.ok().body(usersService.checkUserPassword(email, password));
    }

    @PutMapping(value = "/change-password")
    public ResponseEntity<?> changeUserPassword(@RequestBody ChangePasswordDto passwordDto) {
        return ResponseEntity.ok().body(usersService.changeUserPassword(passwordDto));
    }

    @PutMapping(value = "/uploadImage")
    public ResponseEntity<?> uploadUserImage(@RequestParam("userId") Long userId,
                                             @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok().body(usersService.updateUserImage(userId, image));
    }

}
