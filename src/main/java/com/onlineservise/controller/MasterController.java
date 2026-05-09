package com.onlineservise.controller;

import com.onlineservise.entity.Master;
import com.onlineservise.service.MasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/masters")
@RequiredArgsConstructor
public class MasterController {

    private final MasterService masterService;

    @GetMapping
    public List<Master> getAllMasters() {

        return masterService.getAllMasters();
    }
}
