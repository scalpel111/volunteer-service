package com.volunteer.service;

import com.volunteer.common.Result;
import com.volunteer.dto.InstitutionDTO;
import com.volunteer.entity.Institution;
import com.baomidou.mybatisplus.extension.service.IService;
<<<<<<< HEAD
=======
import io.swagger.models.auth.In;
import org.springframework.web.multipart.MultipartFile;
>>>>>>> feature-yc

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface InstitutionService extends IService<Institution> {

    Result<List<InstitutionDTO>> getInstitution();

    Result<List<InstitutionDTO>> getByInstitutionName(String name);

    Result<List<Institution>> select();

    Result<Object> updateInstitution(Institution institution);

    Result<Object> insert(Institution institution);

    Result<Object> deleteById(int id);

    Result<Object> ratifyInsert(Institution institution);

    Result<Object> ratify();

    Result<Object> ratifyFalse(Institution institution);

    Result<Object> upImgs(HttpServletRequest request, MultipartFile myfile, String desc) throws IOException;
}
