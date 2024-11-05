//package DoAnCuoiKyJava.HeThongHoTroCuocThi.DTO;
//
//public class UserDTO {
//    private String name;
//    private String email;
//    private String password;
//    public String getName() {
//        return name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//    public String getEmail() {
//        return email;
//    }
//    public void setEmail(String email) {
//        this.email = email;
//    }
//    public String getPassword() {
//        return password;
//    }
//    public void setPassword(String password) {
//        this.password = password;
//    }
//}
package DoAnCuoiKyJava.HeThongHoTroCuocThi.DTO;

import org.springframework.web.multipart.MultipartFile;

public class UserDTO {
    private Long id;                     // ID người dùng
    private String name;                 // Họ và tên
    private String username;             // Tên đăng nhập
    private String cccd;                 // CCCD
    private String password;              // Mật khẩu
    private String email;                // Địa chỉ email
    private String phone;                // Số điện thoại
    private MultipartFile imageUrl;      // Hình ảnh người dùng
    private Long truongId;               // ID của trường

    // Getters và Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String setImageUrl() {
        return imageUrl.getOriginalFilename();
    }



    public String getImageUrl() {
        return imageUrl.getOriginalFilename();
    }

    public Long getTruongId() {
        return null;
    }
    }



