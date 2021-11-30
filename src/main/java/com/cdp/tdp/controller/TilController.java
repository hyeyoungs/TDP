package com.cdp.tdp.controller;

import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.User;
import com.cdp.tdp.dto.TilRequestDto;
import com.cdp.tdp.security.UserDetailsImpl;
import com.cdp.tdp.service.TilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/til_board/{id}")
    public Til getTil(@PathVariable Long id){
        return tilService.getTil(id);
    }

    @PostMapping("/til")
    public Til createTil(@RequestBody TilRequestDto tilRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws SQLException{
        Til til = tilService.createTil(tilRequestDto, userDetails.getUser().getId());
        return til;
    }

    @DeleteMapping("/til_board/{id}")
    public void deleteTil(@PathVariable Long id){
        tilService.deleteTil(id);
    }

    @PutMapping("/til_board/{id}")
    public void updateTil(@PathVariable Long id, @RequestBody TilRequestDto tilRequestDto) throws SQLException{
        tilService.updateTil(id, tilRequestDto);
    }

    @GetMapping("/til/user")
    public List<Til> getUserTil(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return tilService.getUserTil(userDetails.getUser());
    }

    @PutMapping("/til_board/{id}/view")
    public void updateTilView(@PathVariable Long id) throws SQLException{
        tilService.updateTilView(id);
    }

    @GetMapping("/til/search")
    public List<Til> SearchTil(@RequestParam String keyword, @RequestParam String setting){

        return tilService.SearchTil(keyword,setting);
    }

}
