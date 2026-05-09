package com.onlineservise.service.impl;

import com.onlineservise.entity.Master;
import com.onlineservise.repository.MasterRepository;
import com.onlineservise.service.MasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MasterServiceImpl implements MasterService {

    private final MasterRepository masterRepository;

    @Override
    public List<Master> getAllMasters() {

        return masterRepository.findAll();
    }
}
