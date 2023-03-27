package com.volunteer.service;

import com.volunteer.common.Result;
import com.volunteer.dto.InstitutionActivityDTO;
import com.volunteer.dto.UserDTO;
import com.volunteer.entity.InstitutionActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 刘毅晨
 * @since 2023-03-22
 */
public interface InstitutionActivityService extends IService<InstitutionActivity> {

    Result<List<InstitutionActivityDTO>> getInstitutionActivity(Integer id);
}
