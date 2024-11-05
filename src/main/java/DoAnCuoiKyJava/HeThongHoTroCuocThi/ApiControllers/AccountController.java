package DoAnCuoiKyJava.HeThongHoTroCuocThi.ApiControllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.DTO.UserDTO;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.User;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api") // Thêm prefix cho các API
public class AccountController {
    private final UserService userService;
    @Autowired
    public AccountController( UserService userService) {

        this.userService = userService;
    }
    //API register/{id} của USER DTO
@GetMapping("/register/{id}")
public ResponseEntity<Map<String, Object>> register(@PathVariable Long id) {
    Map<String, Object> response = new HashMap<>();
    try {
        User user = userService.findById(id); // Lấy người dùng theo id
        UserDTO userDTO = convertToDTO(user); // Chuyển đổi User sang UserDTO
        response.put("user", userDTO);
    } catch (UsernameNotFoundException e) {
        response.put("error", e.getMessage());
        return ResponseEntity.status(404).body(response); // Trả về mã trạng thái 404 nếu không tìm thấy
    }
    return ResponseEntity.ok(response);
}

    @PostMapping("/registerapi")
    User createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }
    // Phương thức chuyển đổi User sang UserDTO
    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getHoten());
        userDTO.setUsername(user.getUsername());
        userDTO.setCccd(user.getCccd());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        return userDTO;
    }
}