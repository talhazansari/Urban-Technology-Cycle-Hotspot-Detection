package com.example.berlinhotspots.controller;

import com.example.berlinhotspots.dto.HotspotDto;
import com.example.berlinhotspots.model.Accident;
import com.example.berlinhotspots.model.Hotspot;
import com.example.berlinhotspots.service.DataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ApiController {

    private final DataService service;

    public ApiController(DataService service) {
        this.service = service;
    }

    @GetMapping("/accidents")
    public List<Accident> getAccidents(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer severity
    ) {
        return service.getAccidents(month, severity);
    }

    @GetMapping("/hotspots")
    public List<HotspotDto> getHotspots() {
        return service.getHotspots();
    }
}
