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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TilController {
    public List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @CrossOrigin
    @RequestMapping(value = "/subscribe", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe() {
        SseEmitter sseEmitter = new SseEmitter();
        try {
            sseEmitter.send(SseEmitter.event().name("INIT"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
        emitters.add(sseEmitter);
        return sseEmitter;
    }

    private final TilService tilService;

    @GetMapping("/tils")
    public List<Til> getAllTil(){
        return tilService.getAllTil();
    }

    @GetMapping("/til_board")
    public TilListDto getTilList(@RequestParam int page, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        page --;
        int size = 10;

        Page<Til> tils = tilService.getTilList(page, size, userDetails.getUser());
        TilListDto tilList = TilListDto.builder()
                .tils(tils)
                .page(tils.getNumber())
                .size(tils.getSize())
                .totalPages(tils.getTotalPages()).build();
        return tilList;
    }

    @GetMapping("/til_board/{id}")
    public Til getTil(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return tilService.getTil(id);
    }


    @GetMapping("/til/{username}")
    public List<Til> getTil_name(@PathVariable String username){

        return tilService.getTil_name(username);
    }


    @PostMapping("/til")
    public Til createTil(@RequestBody TilRequestDto tilRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws SQLException {
        Til til = tilService.createTil(tilRequestDto, userDetails.getUser().getId());
        String eventName;
        if(tilRequestDto.isTilView() == true) {  eventName = "newPublicPost";    }
        else {  eventName = "newPrivatePost";   }
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name(eventName).data(til));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
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
    public TilListDto searchTil(@RequestParam int page, @RequestParam String keyword, @RequestParam String setting, @AuthenticationPrincipal UserDetailsImpl userDetails){
        page --;
        int size = 10;

        Page<Til> tils = tilService.searchTil(page, size, keyword, setting, userDetails.getUser());
        TilListDto tilList = TilListDto.builder()
                .tils(tils)
                .page(tils.getNumber())
                .size(tils.getSize())
                .totalPages(tils.getTotalPages()).build();
        return tilList;

    }
}