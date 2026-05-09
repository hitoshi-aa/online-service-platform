package com.onlineservise.service.impl;

import com.onlineservise.dto.MasterDTO;
import com.onlineservise.entity.Master;
import com.onlineservise.repository.MasterRepository;
import com.onlineservise.service.MasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MasterServiceImpl implements MasterService {

    private final MasterRepository masterRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<MasterDTO> getAllMasters() {
        return masterRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MasterDTO getMasterById(Long id) {
        return masterRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Master not found"));
    }

    @Override
    public MasterDTO getMasterByLogin(String login) {
        return masterRepository.findByLogin(login)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public boolean authenticate(String login, String password) {
        return masterRepository.findByLogin(login)
                .map(master -> {
                    boolean matches = passwordEncoder.matches(password, master.getPassword());
                    System.out.println("[AUTH-DEBUG] Login: " + login + " | Matches: " + matches);
                    return matches;
                })
                .orElse(false);
    }

    @Override
    public MasterDTO saveMaster(MasterDTO dto) {
        Master master = convertToEntity(dto);
        master.setPassword(passwordEncoder.encode(dto.getPassword()));
        return convertToDTO(masterRepository.save(master));
    }

    @Override
    public void updateMaster(MasterDTO dto) {
        Master existing = masterRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Master not found"));
        
        existing.setName(dto.getName());
        existing.setSpecialization(dto.getSpecialization());
        existing.setLogin(dto.getLogin());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        
        masterRepository.update(existing);
    }

    @Override
    public void deleteMaster(Long id) {
        masterRepository.deleteById(id);
    }

    private MasterDTO convertToDTO(Master master) {
        return MasterDTO.builder()
                .id(master.getId())
                .name(master.getName())
                .specialization(master.getSpecialization())
                .login(master.getLogin())
                .build();
    }

    private Master convertToEntity(MasterDTO dto) {
        Master master = new Master();
        master.setId(dto.getId());
        master.setName(dto.getName());
        master.setSpecialization(dto.getSpecialization());
        master.setLogin(dto.getLogin());
        return master;
    }
}
