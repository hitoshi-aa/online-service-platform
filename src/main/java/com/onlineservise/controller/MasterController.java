package com.onlineservise.controller;

import com.onlineservise.dto.MasterDTO;
import com.onlineservise.service.MasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/masters")
@RequiredArgsConstructor
public class MasterController {

    private final MasterService masterService;

    @GetMapping
    public List<MasterDTO> getAllMasters() {
        return masterService.getAllMasters();
    }

    @GetMapping("/{id}")
    public MasterDTO getMasterById(@PathVariable Long id) {
        return masterService.getMasterById(id);
    }

    @PostMapping
    public MasterDTO createMaster(@RequestBody MasterDTO masterDTO) {
        return masterService.saveMaster(masterDTO);
    }

    @PutMapping("/{id}")
    public void updateMaster(@PathVariable Long id, @RequestBody MasterDTO masterDTO) {
        masterDTO.setId(id);
        masterService.updateMaster(masterDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMaster(@PathVariable Long id) {
        masterService.deleteMaster(id);
    }
}
