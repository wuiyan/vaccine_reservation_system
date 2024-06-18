package com.wuyan.vaccinereservation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuyan.vaccinereservation.common.ResponseBody;
import com.wuyan.vaccinereservation.model.dto.PopularScienceInfoDto;
import com.wuyan.vaccinereservation.model.entity.PopularScienceInfo;
import com.wuyan.vaccinereservation.service.PopularScienceInfoService;
import com.wuyan.vaccinereservation.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class PopularScienceInfoController {
    @Resource
    private PopularScienceInfoService popularScienceInfoService;

    /**
     * 获取科普文章列表的Dto信息
     */
    @GetMapping("/popular")
    public ResponseBody<IPage<PopularScienceInfoDto>> getPopularScienceInfoDtoList(int pageNum, int size) {
        return ResultUtils.success(popularScienceInfoService.getPopularScienceInfoDtoList(pageNum, size));
    }
    /**
     * 获取科普文章列表的信息
     */
    @GetMapping("/populars")
    public ResponseBody<IPage<PopularScienceInfo>> getPopularScienceInfoList(int pageNum, int size) {
        return ResultUtils.success(popularScienceInfoService.getPopularScienceInfoList(pageNum, size));
    }

    /**
     * 通过id获取单一文章信息
     * @param popularId 科普信息ID
     * @return
     */
    @GetMapping("/popular/id")
    public ResponseBody<PopularScienceInfo> searchPopularScienceInfo(int popularId) {
        return ResultUtils.success(popularScienceInfoService.searchPopularScienceInfo(popularId));
    }
    /**
     * 添加指定文章信息
     * @return 是否成功的布尔值
     */
    @PostMapping("/popular")
    public ResponseBody<Boolean> addPopularScienceInfo(@RequestBody PopularScienceInfo popularScienceInfo) {
        return ResultUtils.success(popularScienceInfoService.insertPopularScienceInfo(popularScienceInfo));
    }
    /**
     * 更新指定文章的信息
     * @return boolean
     */
    @PutMapping("/popular")
    public ResponseBody<Boolean> updatePopularScienceInfo(@RequestBody PopularScienceInfo popularScienceInfo) {
        return ResultUtils.success(popularScienceInfoService.updatePopularScienceInfo(popularScienceInfo));
    }
    @DeleteMapping("/popular")
    public ResponseBody<Boolean> deletePopularScienceInfo(int popularId){
        return ResultUtils.success(popularScienceInfoService.deletePopularScienceInfo(popularId));
    }
    @GetMapping("/popular/search")
    public ResponseBody<List<PopularScienceInfo>> searchPopularScienceInfoList(String title) {
        return ResultUtils.success(popularScienceInfoService.searchPopularScienceInfoList(title));
    }
    @GetMapping("/populars/search")
    public ResponseBody<List<PopularScienceInfoDto>> searchAdminPopularScienceInfoList(String title) {
        return ResultUtils.success(popularScienceInfoService.searchAdminPopularScienceInfoList(title));
    }

}
