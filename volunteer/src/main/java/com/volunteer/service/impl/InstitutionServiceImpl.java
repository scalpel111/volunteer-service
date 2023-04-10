package com.volunteer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.volunteer.common.JWTUtil;
import com.volunteer.common.Result;
import com.volunteer.dto.InstitutionDTO;
import com.volunteer.entity.Institution;
import com.volunteer.entity.User;
import com.volunteer.entity.UserInstitution;
import com.volunteer.mapper.InstitutionMapper;
import com.volunteer.mapper.UserInstitutionMapper;
import com.volunteer.service.InstitutionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.service.UserInstitutionService;
import com.volunteer.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.volunteer.utils.RedisConstants.*;

@Service
public class InstitutionServiceImpl extends ServiceImpl<InstitutionMapper, Institution> implements InstitutionService {

    @Resource
    private UserInstitutionMapper userInstitutionMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserService userService;

    @Value("${file.save.path}")
    private String fileSavePath;

    public Result<List<InstitutionDTO>> wrap(List<Institution> institutions){
        List<InstitutionDTO> institutionDTOS = new ArrayList<>();
        for (Institution institution : institutions) {
            int number = userInstitutionMapper.selectCount(new QueryWrapper<UserInstitution>()
                    .eq("institution_id",institution.getInstitutionId()));
            InstitutionDTO institutionDTO = new InstitutionDTO(institution.getInstitutionId(),
                    institution.getInstitutionName(),institution.getAddress(),
                    number,institution.getCount());
            institutionDTOS.add(institutionDTO);
        }
        return Result.success(institutionDTOS);
    }

    @Override
    public Result<List<InstitutionDTO>> getInstitution() {
        List<Institution> institutions = list();
        return wrap(institutions);
    }

    @Override
    public Result<List<InstitutionDTO>> getByInstitutionName(String name) {
        QueryWrapper<Institution> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("institution_name",name);
        List<Institution> institutions = list(queryWrapper);
        return wrap(institutions);
    }

    @Override
    public Result<List<Institution>> select() {
        List<Institution> list = list();
        return Result.success(list);
    }

    @Override
    public Result<Object> updateInstitution(Institution institution) {
        QueryWrapper<Institution> queryWrapper = new QueryWrapper<>(institution);
        boolean update = update(queryWrapper);
        if(!update){
            return Result.fail("更新失败");
        }
        return Result.success();
    }

    @Override
    public Result<Object> insert(Institution institution) {
        boolean save = save(institution);
        institution.setInstitutionId(null);
        stringRedisTemplate.opsForZSet().remove(CACHE_INSTITUTIONS_KEY, JSON.toJSONString(institution));
        if (save) {
            //DecodedJWT jwt = JWTUtil.getToken(token);
            //String openid = jwt.getClaim("openid").asString();
            Institution institution1 = query().eq("admin_id", institution.getAdminId()).one();
            User user = userService.query().eq("admin_id", institution.getAdminId()).one();
            if (user != null) {
                UserInstitution userInstitution = new UserInstitution();
                userInstitution.setInstitutionId(institution1.getInstitutionId());
                userInstitution.setOpenid(user.getOpenid());
                userInstitution.setStatus(1);
                userInstitutionMapper.insert(userInstitution);
            }
            return Result.success("组织入驻成功！");
        } else return Result.fail("组织入驻失败！");
    }

    @Override
    public Result<Object> deleteById(int id) {
        boolean remove = removeById(id);
        if(!remove){
            return Result.fail("删除失败");
        }
        return Result.success();
    }

    @Override
    public Result<Object> ratifyInsert(Institution institution) {
        stringRedisTemplate.opsForZSet().add(CACHE_INSTITUTIONS_KEY, JSON.toJSONString(institution), System.currentTimeMillis());
        return Result.success("组织申请已提交，正在审批中！");
    }

    @Override
    public Result<Institution> getInto(Integer id) {
        Institution institution = query().eq("institution_id",id).one();
        return Result.success(institution);
    }

    @Override
    public Result<Object> ratify() {
        Set<String> range = stringRedisTemplate.opsForZSet().range(CACHE_INSTITUTIONS_KEY, 0l, Long.MAX_VALUE);
        List<Institution> institutions = new ArrayList<>();
        for (String s : range) {
            Institution institution = JSON.parseObject(s, Institution.class);
            institutions.add(institution);
        }
        return Result.success(institutions);
    }

    @Override
    public Result<Object> ratifyFalse(Institution institution) {
        String jsonString = JSON.toJSONString(institution);
        stringRedisTemplate.opsForZSet().remove(CACHE_INSTITUTIONS_KEY, jsonString);
        return Result.fail("审批完成，已驳回！");
    }

    @Override
    public Result<Object> upImgs(MultipartFile file) throws IOException {
        File fir=new File(fileSavePath);
        //不存在则创建文件夹
        if(!fir.exists()){
            fir.mkdirs();
        }
        //文件的后缀名
        String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //新文件名字 为了防止重复加上UUID
        String newFileName= UUID.randomUUID().toString().replaceAll("-","")+suffix;
        System.out.println("filesavepath:"+fileSavePath+"   "+"newFileName:"+newFileName);
        //新的文件路径
        File newFile=new File(fileSavePath+newFileName);
        //把文件写入新的File文件
        file.transferTo(newFile);
        //url路径=http + :// + server名字 + port端口 + /imges/ + newFileName
        String url = "http://localhost:8090/images/"+newFileName;
        return Result.success(url);

    }
}
