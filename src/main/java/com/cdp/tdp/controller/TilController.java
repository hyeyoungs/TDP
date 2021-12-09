package com.cdp.tdp.controller;

import com.cdp.tdp.domain.Til;
import com.cdp.tdp.dto.TilListDto;
import com.cdp.tdp.dto.TilRequestDto;
import com.cdp.tdp.security.UserDetailsImpl;
import com.cdp.tdp.service.TilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TilController {
    private final TilService tilService;

    @GetMapping("/til_board")
    public TilListDto getTilList(@RequestParam int page) {
        log.info("total page = {}", page);
        page --;
        int size = 10;

        Page<Til> tils = tilService.getTilList(page, size);
        TilListDto tilList = TilListDto.builder()
                .tils(tils)
                .page(tils.getNumber())
                .size(tils.getSize())
                .totalPages(tils.getTotalPages()).build();
        log.info("tilList Value = {}", tilList);
        return tilList;
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

    public TilListDto SearchTil(@RequestParam int page, @RequestParam String keyword, @RequestParam String setting){
        log.info("search page = {}", page);
        page --;
        int size = 10;

        Page<Til> tils = tilService.SearchTil(page, size, keyword, setting);
        TilListDto tilList = TilListDto.builder()
                .tils(tils)
                .page(tils.getNumber())
                .size(tils.getSize())
                .totalPages(tils.getTotalPages()).build();
        log.info("tilList Value = {}", tilList);
        return tilList;

    }
}