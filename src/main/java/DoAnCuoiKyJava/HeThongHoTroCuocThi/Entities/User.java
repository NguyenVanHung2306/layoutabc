//package DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities;
//
//import DoAnCuoiKyJava.HeThongHoTroCuocThi.Validators.annotations.ValidUsername;
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
//import jakarta.validation.constraints.Size;
//import lombok.*;
//import org.hibernate.Hibernate;
//import org.hibernate.validator.constraints.Length;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import java.util.*;
//@Getter
//@Setter
//@ToString
//@RequiredArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//@Table(name = "user")
//public class User implements UserDetails {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "username", length = 50, unique = true)
//    @NotBlank(message = "Username is required")
//    @Size(min = 1, max = 50, message = "Username must be between 1 and 50 characters")
//    @ValidUsername
//    private String username;
//
//    private String hoten;
//
//    private String cccd;
//
//    @Column(name = "password", length = 250)
//    @NotBlank(message = "Password is required")
//    private String password;
//
//    @Column(name = "email", length = 50, unique = true)
//    @NotBlank(message = "Email is required")
//    @Size(min = 1, max = 50, message = "Email must be between 1 and 50 characters")
//    @Email
//    private String email;
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    @Column(name = "phone", length = 10, unique = true)
//    @Length(min = 10, max = 10, message = "Phone must be 10 characters")
//    @Pattern(regexp = "^[0-9]*$", message = "Phone must be number")
//    private String phone;
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "truong_id", referencedColumnName = "id")
//    @ToString.Exclude
//    private Truong truong;
//
//    @Column(name = "imageUrl")
//    private String imageUrl;
//
//    private int trangThai;
//
//    @Column(name = "provider", length = 50)
//    private String provider;
//
//    @ManyToMany(fetch=FetchType.EAGER)
//    @JoinTable(name = "user_role",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles = new HashSet<>();
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<Role> userRoles = this.getRoles();
//        return userRoles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .toList();
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) !=
//                Hibernate.getClass(o)) return false;
//        User user = (User) o;
//        return getId() != null && Objects.equals(getId(),
//                user.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
//}
package DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Validators.annotations.ValidUsername;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 50, unique = true)
    @NotBlank(message = "Username is required")
    @Size(min = 1, max = 50, message = "Username must be between 1 and 50 characters")
    @ValidUsername
    private String username;

    private String hoten;

    private String cccd;

    @Column(name = "password", length = 250)
    @NotBlank(message = "Password is required")
    private String password;

    @Column(name = "email", length = 50, unique = true)
    @NotBlank(message = "Email is required")
    @Size(min = 1, max = 50, message = "Email must be between 1 and 50 characters")
    @Email
    private String email;

    @Column(name = "phone", length = 10, unique = true)
    @Length(min = 10, max = 10, message = "Phone must be 10 characters")
    @Pattern(regexp = "^[0-9]*$", message = "Phone must be number")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "truong_id", referencedColumnName = "id")
    @ToString.Exclude
    private Truong truong;

    @Column(name = "imageUrl")
    private String imageUrl;

    private int trangThai;

    @Column(name = "provider", length = 50)
    private String provider;

    @Column(name = "count_fail", nullable = false)
    private int countFail = 0; // Giá trị mặc định cho count_fail

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true; // Giá trị mặc định cho enabled

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PasswordResetToken passwordResetToken;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> userRoles = this.getRoles();
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(@NotBlank(message = "Username is required") @Size(min = 1, max = 50, message = "Username must be between 1 and 50 characters") String username) {
        this.username = username;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public void setPassword(@NotBlank(message = "Password is required") String password) {
        this.password = password;
    }

    public @NotBlank(message = "Email is required") @Size(min = 1, max = 50, message = "Email must be between 1 and 50 characters") @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Size(min = 1, max = 50, message = "Email must be between 1 and 50 characters") @Email String email) {
        this.email = email;
    }

    public @Length(min = 10, max = 10, message = "Phone must be 10 characters") @Pattern(regexp = "^[0-9]*$", message = "Phone must be number") String getPhone() {
        return phone;
    }

    public void setPhone(@Length(min = 10, max = 10, message = "Phone must be 10 characters") @Pattern(regexp = "^[0-9]*$", message = "Phone must be number") String phone) {
        this.phone = phone;
    }

    public Truong getTruong(Long truongId) {
        return truong;
    }

    public void setTruong(Truong truong) {
        this.truong = truong;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getCountFail() {
        return countFail;
    }

    public void setCountFail(int countFail) {
        this.countFail = countFail;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public PasswordResetToken getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(PasswordResetToken passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public void setName(String hoten) {
        this.hoten = hoten;
    }
}

