package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.ApiControllers.AccountController;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.DTO.UserDTO;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.DTO.UserLoginDTO;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.PasswordResetToken;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.Truong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.User;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.IUserRepository;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.TokenRepository;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Request.UserCreateRequest;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Request.UserUpdateRequest;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.TruongService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.UserDetailsServiceImpl;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.UserService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Viewmodels.UserGetVM;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    @Autowired

    private final UserService userService;
    private final TruongService truongService;
//    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    IUserRepository userRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    TokenRepository tokenRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/login")
    public String login() {
        return "/Account/login";
    }
    @PostMapping("/login")
    public void login(@ModelAttribute UserLoginDTO userLoginDTO, Model model) {
        userDetailsService.loadUserByUsername(userLoginDTO.getUsername());
    }

    @GetMapping("/register")
    public String register(@NotNull Model model) {
        model.addAttribute("user", new UserCreateRequest());
        model.addAttribute("listTruong", truongService.getAllTruongsHien());
        return "/Account/register";
    }




    @PostMapping("/register")
    public String register(UserCreateRequest userRequest,
                           @NotNull BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("listTruong", truongService.getAllTruongsHien());
            return "/Account/register";
        }

        String image = userService.saveImage(userRequest.getImageUrl());
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setHoten(userRequest.getHoten());
        user.setCccd(userRequest.getCccd());
        user.setPassword(userRequest.getPassword());
        user.setPhone(userRequest.getPhone());
        user.setEmail(userRequest.getEmail());
        user.setImageUrl(image);
        user.setTruong(userRequest.getTruong());
        user.setTrangThai(1);
        userService.Save(user);
        userService.setDefaultRole(user.getUsername());
        return "redirect:/login";
    }

//    @GetMapping("/forgotPassword")
//    public String forgotPassword() {
//        return "/Account/forgotPassword";
//    }

//    @PostMapping("/forgotPassword")
//    public String forgotPasswordProcess(@ModelAttribute UserDTO userDTO, RedirectAttributes redirectAttributes) {
//        String output = "";
//        User user = userRepository.findByEmail(userDTO.getEmail());
//        if (user != null) {
//            output = userDetailsService.sendEmail(user);
//        } else {
//            redirectAttributes.addFlashAttribute("error", "Email không tồn tại.");
//            return "redirect:/forgotPassword";
//        }
//
//        if (output.equals("success")) {
//            redirectAttributes.addFlashAttribute("success", "Email đã được gửi. Vui lòng kiểm tra hộp thư.");
//            return "redirect:/forgotPassword";
//        }
//
//        redirectAttributes.addFlashAttribute("error", "Đã có lỗi xảy ra. Vui lòng thử lại.");
//        return "redirect:/forgotPassword";
//    }

//    @GetMapping("/userDashboard")
//    public String showUserDashboardForm() {
//        return "userDashboard";
//    }
//
//    @GetMapping("/forgotPassword")
//    public String forgotPassword() {
//        return "forgotPassword";
//    }
//
//    @PostMapping("/forgotPassword")
//    public String forgotPassordProcess(@ModelAttribute UserDTO userDTO) {
//        String output = "";
//        User user = userRepository.findByEmail(userDTO.getEmail());
//        if (user != null) {
//            output = userDetailsService.sendEmail(user);
//        }
//        if (output.equals("success")) {
//            return "redirect:/forgotPassword?success";
//        }
//        return "redirect:/login?error";
//    }
//
//
//    @GetMapping("/resetPassword/{token}")
//    public String resetPasswordForm(@PathVariable String token, Model model) {
//        PasswordResetToken reset = tokenRepository.findByToken(token);
//        if (reset != null && !userDetailsService.hasExipred(reset.getExpiryDateTime())) { // Đảm bảo token không hết hạn
//            model.addAttribute("email", reset.getUser().getEmail());
//            return "resetPassword"; // Trả về trang reset mật khẩu
//        }
//        return "redirect:/forgotPassword?error"; // Token không hợp lệ hoặc đã hết hạn
//    }
//
//    @PostMapping("/resetPassword")
//    public String passwordResetProcess(@ModelAttribute UserDTO userDTO, RedirectAttributes redirectAttributes) {
//        User user = userRepository.findByEmail(userDTO.getEmail());
//        if (user != null) {
//            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//            userRepository.save(user);
//            redirectAttributes.addFlashAttribute("success", "Mật khẩu đã được đặt lại thành công.");
//        } else {
//            redirectAttributes.addFlashAttribute("error", "Email không tồn tại.");
//        }
//        return "redirect:/login";
//    }
@GetMapping("/userDashboard")
public String showUserDashboardForm() {
    return "userDashboard";
}

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";  // Đảm bảo trang forgotPassword tồn tại trong templates
    }

    @PostMapping("/forgotPassword")
    public String forgotPasswordProcess(@ModelAttribute UserDTO userDTO, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByEmail(userDTO.getEmail());
        if (user != null) {
            String output = userDetailsService.sendEmail(user);
            if ("success".equals(output)) {
                redirectAttributes.addFlashAttribute("success", "Email đã được gửi. Vui lòng kiểm tra hộp thư.");
                return "redirect:/forgotPassword";
            } else {
                redirectAttributes.addFlashAttribute("error", "Đã có lỗi khi gửi email. Vui lòng thử lại.");
                return "redirect:/forgotPassword";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Email không tồn tại.");
            return "redirect:/forgotPassword";
        }
    }

    @GetMapping("/resetPassword/{token}")
    public String resetPasswordForm(@PathVariable String token, Model model, RedirectAttributes redirectAttributes) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken != null && !userDetailsService.hasExipred(resetToken.getExpiryDateTime())) {
            model.addAttribute("email", resetToken.getUser().getEmail());
            return "resetPassword";  // Trả về trang reset mật khẩu
        }
        redirectAttributes.addFlashAttribute("error", "Token không hợp lệ hoặc đã hết hạn.");
        return "redirect:/forgotPassword";
    }

    @PostMapping("/resetPassword")
    public String passwordResetProcess(@ModelAttribute UserDTO userDTO, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByEmail(userDTO.getEmail());
        if (user != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("success", "Mật khẩu đã được đặt lại thành công.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Email không tồn tại.");
        }
        return "redirect:/login";
    }




    //API lấy thông tin user
    @GetMapping("/User/id/{id}")
    public ResponseEntity<UserGetVM> getUserByCCCD(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserByCCCD(id)
                .map(UserGetVM::from)
                .orElse(null));
    }

    @GetMapping("/User/edit")
    public String editUser(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("listTruong", truongService.getAllTruongsHien());
        return "/Account/edit";
    }

    @PostMapping("/edit")
    public String saveEditedUser(@Valid @ModelAttribute("user") UserUpdateRequest updateUser,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("error", "Validation errors occurred");
            return "redirect:/User/edit";
        }

        userService.saveUser(updateUser);
        return "redirect:/User/edit";
    }

    @GetMapping("/list")
    public String showAllUser(@NotNull Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "/Account/list";
    }

}