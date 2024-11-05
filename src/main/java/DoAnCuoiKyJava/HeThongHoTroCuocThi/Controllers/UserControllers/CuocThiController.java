package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers.UserControllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.*;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/User/CuocThis")
public class CuocThiController {
    private final CuocThiService cuocThiService;
    private final MonThiService monThiService;
    private final ChiTietNoiDungService CTNDService;
    private final ChiTietQuyDinhService CTQDService;
    private final LoaiTruongService loaiTruongService;
    private final PhieuDangKyService phieuDangKyService;

    @GetMapping
    public String showAllCuocThi(@NotNull Model model) {
        model.addAttribute("cuocThis", cuocThiService.getAllCuocThisHien());
        model.addAttribute("loaiTruongService", loaiTruongService);
        model.addAttribute("phieuDangKyService", phieuDangKyService);
        model.addAttribute("monThis", monThiService.getAllMonThisHien());
        model.addAttribute("loaiTruongs", loaiTruongService.getAllLoaiTruongsHien());
        return "/User/CuocThi/list";
    }

    @GetMapping("/details/{id}")
    public String detailsCuocThi(@PathVariable Long id, Model model) {
        CuocThi cuocThi = cuocThiService.getCuocThiById(id)
                .orElseThrow(() -> new EntityNotFoundException(""));
        List<ChiTietNoiDung> chiTietNoiDungs = CTNDService.getChiTietNoiDungsByCuocThiId(id);
        List<ChiTietQuyDinh> chiTietQuyDinhs = CTQDService.getChiTietQuyDinhsByCuocThiId(id);

        model.addAttribute("cuocThi", cuocThi);
        model.addAttribute("chiTietNoiDungs", chiTietNoiDungs);
        model.addAttribute("chiTietQuyDinhs", chiTietQuyDinhs);
        model.addAttribute("loaiTruongService", loaiTruongService);
        return "/User/CuocThi/details";
    }

    @GetMapping("/search")
    public String searchCuocThiChoUser(
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(name = "location", required = false) String diaDiemThi,
            @RequestParam(name = "monThi", required = false) Long monThiId,
            @RequestParam(name = "capThi", required = false) Long loaiTruongId,
            Model model) {

        List<CuocThi> results = cuocThiService.getAllCuocThisHien();

        if ((startDate == null || endDate == null) && diaDiemThi == null  && monThiId == -1 && loaiTruongId == -1) {
            model.addAttribute("cuocThis", results);
            model.addAttribute("loaiTruongService", loaiTruongService);
            model.addAttribute("phieuDangKyService", phieuDangKyService);
            model.addAttribute("monThis", monThiService.getAllMonThisHien());
            model.addAttribute("loaiTruongs", loaiTruongService.getAllLoaiTruongsHien());
            return "/User/CuocThi/list";
        }

        if(startDate != null && endDate != null)
        {
            results = cuocThiService.searchByNgayThi(startDate, endDate, results);
        }

        if(diaDiemThi != null)
        {
            results = cuocThiService.searchByDiadime(diaDiemThi, results);
        }

        if(monThiId != -1)
        {
            results = cuocThiService.searchByMonThi(monThiId, results);
        }

        if(loaiTruongId != -1)
        {
            results = cuocThiService.searchByLoaiTruong(loaiTruongId, results);
        }

        model.addAttribute("cuocThis", results);
        model.addAttribute("monThis", monThiService.getAllMonThisHien());
        model.addAttribute("loaiTruongs", loaiTruongService.getAllLoaiTruongsHien());
        model.addAttribute("loaiTruongService", loaiTruongService);
        model.addAttribute("phieuDangKyService", phieuDangKyService);
        return "/User/CuocThi/list";
    }

}