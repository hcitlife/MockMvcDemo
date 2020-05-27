
package com.hc.controller;

import com.hc.bean.Result;
import com.hc.bean.ResultCode;
import com.hc.bean.ResultUtil;
import com.hc.db.DeptTable;
import com.hc.domain.Dept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mock")
public class DeptController {

    //增加Dept ，使用POST方法
    @PostMapping(value = "/dept")
    public Result saveDept(@RequestBody Dept dept) {
        boolean res = DeptTable.insert(dept);
        log.info("saveDept：{}", dept);
        DeptTable.output();
        if (res) {
            return ResultUtil.success(dept);
        } else {
            return ResultUtil.fail(ResultCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allDept")
    public Result getUserList() {
        List<Dept> depts = DeptTable.selectAll();
        return ResultUtil.success(depts);
    }

    @GetMapping("allDept2")
    public List<Dept> allDept2() {
        List<Dept> depts = DeptTable.selectAll();
        return depts;
    }

    @GetMapping("dept/{deptno}")
    public Dept selectByDeptno(@PathVariable int deptno) {
        Dept dept = DeptTable.selectByDeptno(deptno);
        return dept;
    }

    @DeleteMapping("dept/{deptno}")
    public Result deleteByDeptno(@PathVariable int deptno) {
        boolean res = DeptTable.delete(deptno);
        DeptTable.output();
        if (res) {
            return ResultUtil.success(deptno);
        } else {
            return ResultUtil.fail(ResultCode.INTERNAL_SERVER_ERROR);
        }
    }

    //////////////////////////////////////////////////////////////////////////////

    @GetMapping("fun1")
    public String fun1(){
        return "hello";
    }

    @GetMapping("dept2")
    public Dept selectByDeptno2(int deptno) {
        Dept dept =  DeptTable.selectByDeptno(deptno);
        return dept;
    }

    @PostMapping("fun4")
    public Dept fun4(Dept dept) {
        dept.setDeptno(8989);
        return dept;
    }

    @PutMapping("fun5")
    public Dept fun5(Dept dept) {
        dept.setDeptno(8989);
        return dept;
    }

    @PatchMapping("fun6")
    public Dept fun6(Dept dept) {
        dept.setDeptno(8989);
        return dept;
    }


}
