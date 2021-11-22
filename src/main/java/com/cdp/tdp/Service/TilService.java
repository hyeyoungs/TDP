package com.cdp.tdp.Service;

import com.cdp.tdp.Domain.Til;
import com.cdp.tdp.Dto.TilRequestDto;
import com.cdp.tdp.Repository.TilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TilService {
    private final TilRepository tilRepository;

    public List<Til> getAllTil() {
        return tilRepository.findAll();
    }

    public Til createTil(TilRequestDto tilRequestDto) throws SQLException{
        Til til = new Til(tilRequestDto);
        tilRepository.save(til);
        return til;
    }

}
