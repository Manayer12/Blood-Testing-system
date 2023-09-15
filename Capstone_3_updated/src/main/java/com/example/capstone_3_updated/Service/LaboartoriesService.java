package com.example.capstone_3_updated.Service;

import com.example.capstone_3_updated.Api.ApiException;
import com.example.capstone_3_updated.Model.Laboratories;
import com.example.capstone_3_updated.Model.TestType;
import com.example.capstone_3_updated.Model.User;
import com.example.capstone_3_updated.Repository.LaboartoriesRepository;
import com.example.capstone_3_updated.Repository.TestTypeRepository;
import com.example.capstone_3_updated.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LaboartoriesService {
    private final LaboartoriesRepository laboartoriesRepository;
    private final TestTypeRepository testTypeRepository;
//    private final UserRepository userRepository;

    public List<Laboratories> getAllLaboratory(){
        return laboartoriesRepository.findAll();
    }

    public void addLaboratory(Laboratories laboratories){
        laboartoriesRepository.save(laboratories);
    }

    public void updateLaboratory(Integer id, Laboratories laboratories){
        Laboratories oldLaboratory = laboartoriesRepository.findLaboratoriesById(id);

        if (oldLaboratory ==null)
            throw new ApiException("Sorry the laboratory not found");

        oldLaboratory.setName(laboratories.getName());
        oldLaboratory.setPhone_num(oldLaboratory.getPhone_num());
        oldLaboratory.setEmail(oldLaboratory.getEmail());
        oldLaboratory.setCity(laboratories.getCity());
        oldLaboratory.setDistrict(laboratories.getDistrict());

        laboartoriesRepository.save(oldLaboratory);
    }

    public void deleteLaboratory(Integer id){
        Laboratories deleteLaboratory = laboartoriesRepository.findLaboratoriesById(id);
//        TestType deleteTest = testTypeRepository.findTestTypeById()
        if (deleteLaboratory == null)
            throw new ApiException("Sorry the laboratory not found");
        deleteLaboratory.setTestTypes(null);
        deleteLaboratory.setUsers(null);

        laboartoriesRepository.deleteAllByIdInBatch(Collections.singleton(id));
    }






}
