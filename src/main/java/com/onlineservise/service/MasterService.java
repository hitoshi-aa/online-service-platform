package com.onlineservise.service;

import com.onlineservise.dto.MasterDTO;
import java.util.List;

public interface MasterService {
    List<MasterDTO> getAllMasters();
    MasterDTO getMasterById(Long id);
    MasterDTO getMasterByLogin(String login);
    boolean authenticate(String login, String password);
    MasterDTO saveMaster(MasterDTO masterDTO);
    void updateMaster(MasterDTO masterDTO);
    void deleteMaster(Long id);
}
