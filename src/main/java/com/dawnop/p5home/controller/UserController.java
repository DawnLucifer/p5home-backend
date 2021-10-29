package com.dawnop.p5home.controller;

import com.alibaba.fastjson.JSONObject;
import com.dawnop.p5home.service.UserService;

import com.dawnop.p5home.commons.AccessToken;
import com.dawnop.p5home.commons.EncryptUtils;
import com.dawnop.p5home.commons.Result;

import com.dawnop.p5home.entity.User;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result queryUserName(@RequestBody User reqUser) {
        String username = reqUser.getUsername();
        String password = reqUser.getPassword();
        if (username.equals("")) {
            return Result.failed("账号不能为空");
        }
        /*根据账号查询User信息*/
        User user = userService.queryUsername(username);
        /*判断user是不是空的*/
        if (ObjectUtils.isEmpty(user)) {
            return Result.failed("用户名密码错误");
        }
        /*user不是空的 转换password 判断密码是否正确*/
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        if (!passwordEncoder.matches(password, encodedPassword)) {
            return Result.failed("用户名密码错误");
        }
        /*创建map 返回 前端*/
        Map<String, Object> map = new HashMap<>();
        /*转换user 创建Token*/
        AccessToken accessToken = new AccessToken(username);
        String json = JSONObject.toJSONString(accessToken);
        String token = EncryptUtils.encrypt(json);
        map.put("token", token);
        map.put("user", user);
        return Result.success(map);
    }

//    /**
//     * 根据id修改
//     */
//    @PostMapping("/updateUser")
//    public Result<String> updateUser(@RequestBody User user) {
//        if (ObjectUtils.isEmpty(user)) {
//            return Result.failed("网络异常");
//        }
//        int count = userService.updateUser(user);
//        return Result.success("修改成功");
//    }
//
//    /**
//     * 根据id查询用户
//     */
//    @PostMapping("/queryUserId")
//    public Result<String> queryUserId(@RequestParam Integer id) {
//        User user = userService.queryUserId(id);
//        return Result.success(user);
//    }
//
//    /**
//     * 添加用户
//     */
//    @PostMapping("/add")
//    public Result<String> addHouse(@RequestBody User user) {
//        if (ObjectUtils.isEmpty(user)) {
//            return Result.failed("网络异常");
//        }
//        int count = userService.addUser(user);
//        if (count == 0) {
//            return Result.failed("参数异常");
//        }
//        return Result.success("成功");
//    }
//
//    /**
//     * 根据id修改密码
//     * originalPassword 原来密码
//     * encryptedPassword 加密密码
//     * newPassword 新密码
//     */
//    @PostMapping("/updatePassword")
//    public Result<String> updatePassword(@RequestParam Integer id,
//                                         @RequestParam String originalPassword,
//                                         @RequestParam String encryptedPassword,
//                                         @RequestParam String newPassword) {
//        String oldpasswords = DigestUtils.md5Hex(originalPassword);
//        if (!StringUtils.equals(encryptedPassword, oldpasswords)) {
//            return Result.failed("用户名密码错误");
//        }
//        int count = userService.updatePassword(id, DigestUtils.md5Hex(newPassword));
//        if (count == 0) {
//            return Result.failed("参数异常");
//        }
//        return Result.success("成功");
//    }
//
//    /**
//     * 图片上传
//     */
//    @PostMapping("/upload")
//    public Result<String> upload(MultipartFile file) throws IOException {
//        if (file.getSize() <= 0) {
//            return Result.failed("请上传图片");
//        }
//        String originalFilename = file.getOriginalFilename();
//        String suffix = FilenameUtils.getExtension(originalFilename);
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        String newFileName = uuid + "." + suffix;
//        File uploadDirectory = new File("d:/upload");
//        if (!uploadDirectory.exists()) {
//            uploadDirectory.mkdirs();
//        }
//        File destFile = new File(uploadDirectory, newFileName);
//        file.transferTo(destFile);
//
//        String picPath = "/pic/" + newFileName;
//        log.info("图片地址 - {}", picPath);
//
//        return Result.success(picPath);
//    }
}
