
package com.hc.controller;

import com.hc.db.DeptTable;
import com.hc.domain.Dept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/mock2")
public class DeptController2 {

    @GetMapping("/dept/{deptno}")
    public ModelAndView selectByDeptno(@PathVariable("deptno") Integer deptno) {
        Dept dept = DeptTable.selectByDeptno(deptno);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("dept/detail");
        mav.addObject("dept", dept);
        return mav;
    }


    @PostMapping("/dept")
    public String saveDept(Dept dept, RedirectAttributes redirect) {
        boolean res = DeptTable.insert(dept);
        redirect.addFlashAttribute("dept", res);
        return "redirect:/dept/list/";
    }

    // 单文件上传
    @RequestMapping("/fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("fileName") MultipartFile file) {
        if (file.isEmpty()) {
            return "file is empty";
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);
        String path = "E:/test";
        File dest = new File(path + "/" + fileName);
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            return "true";
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return "false";
        }
    }

}
