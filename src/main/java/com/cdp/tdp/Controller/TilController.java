package com.cdp.tdp.Controller;

import com.cdp.tdp.Domain.Til;
import com.cdp.tdp.Dto.TilRequestDto;
import com.cdp.tdp.Service.TilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TilController {
    private final TilService tilService;

    @GetMapping("/til_board")
    public List<Til> getAllTil() {
        return tilService.getAllTil();
    }

    @PostMapping("/til")
    public Til createTil(@RequestBody TilRequestDto tilRequestDto) throws SQLException{
        Til til = tilService.createTil(tilRequestDto);
        return til;
    }

}
