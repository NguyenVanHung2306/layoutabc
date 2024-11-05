package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers.AdminControllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.CuocThi;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.PhieuDangKy;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.PhieuKetQua;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.CuocThiService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.PhieuDangKyService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.PhieuKetQuaService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/Admin/PhieuKetQuas")
@RequiredArgsConstructor
public class AdminPhieuKetQuaController {
    private final PhieuDangKyService phieuDangKyService;
    private final PhieuKetQuaService phieuKetQuaService;
    private final CuocThiService cuocThiService;
    private final UserService userService;

    @GetMapping("/cuocThiId/{id}")
    public String showAllPhieuKetQuaByCuocThi(@PathVariable Long id, @NotNull Model model) {
        CuocThi cuocThi = cuocThiService.getCuocThiById(id).orElseThrow(() -> new EntityNotFoundException(""));
        List<PhieuKetQua> phieuKetQuas = phieuKetQuaService.getAllPhieuKetQuastheoCuocThi(cuocThi);
        model.addAttribute("phieuKetQuas", phieuKetQuas);
        return "/Admin/PhieuKetQua/list";
    }

    @GetMapping("/add/pkq/pdkId/{id}")
    public String addPhieuKetQuaForm(@PathVariable Long id, @NotNull Model model) {
        PhieuDangKy phieuDangKy = phieuDangKyService.getPhieuDangKyById(id).orElseThrow(() -> new EntityNotFoundException(""));
        PhieuKetQua phieuKetQua = new PhieuKetQua();
        phieuKetQua.setPhieuDangKy(phieuDangKy);
        model.addAttribute("phieuKetQua", phieuKetQua);
        return "/Admin/PhieuKetQua/add";
    }

    @PostMapping("/add")
    public String addPhieuKetQua(
            @Valid @ModelAttribute("PhieuKetQua") PhieuKetQua phieuKetQua,
            @NotNull BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "/Admin/PhieuKetQua/add";
        }
        phieuKetQuaService.addPhieuKetQua(phieuKetQua);
        Long cuocThiId = phieuKetQua.getPhieuDangKy().getCuocThi().getId();
        return "redirect:/Admin/PhieuDangKys/cuocThi/id/" + cuocThiId;
    }

    @GetMapping("/edit/pkq/pdkId/{id}")
    public String editPhieuKetQuaForm(@PathVariable Long id, @NotNull Model model) {
        PhieuDangKy phieuDangKy = phieuDangKyService.getPhieuDangKyById(id).orElseThrow(() -> new EntityNotFoundException(""));
        PhieuKetQua phieuKetQua = phieuKetQuaService.getPhieuKetQuaByPhieuDangKy(phieuDangKy);
        model.addAttribute("phieuKetQua", phieuKetQua);
        return "/Admin/PhieuKetQua/edit";
    }

    @PostMapping("/edit")
    public String editPhieuKetQua(
            @Valid @ModelAttribute("PhieuKetQua") PhieuKetQua phieuKetQua,
            @NotNull BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "/Admin/PhieuKetQua/edit";
        }
        phieuKetQuaService.updatePhieuKetQua(phieuKetQua);
        Long cuocThiId = phieuKetQua.getPhieuDangKy().getCuocThi().getId();
        return "redirect:/Admin/PhieuDangKys/cuocThi/id/" + cuocThiId;
    }

    @GetMapping("/thongke/cuocThiId/{id}")
    public String thongKeDiem(@PathVariable Long id, Model model) {
        CuocThi cuocThi = cuocThiService.getCuocThiById(id).orElseThrow(() -> new EntityNotFoundException(""));
        List<PhieuKetQua> phieuKetQuas = phieuKetQuaService.getAllPhieuKetQuastheoCuocThi(cuocThi);
        List<Integer> diems = phieuKetQuas.stream().map(phieuKetQua -> phieuKetQua.getDiem()).collect(Collectors.toList());
        model.addAttribute("diems", diems);
        return "/Admin/PhieuKetQua/thongke";
    }
}
