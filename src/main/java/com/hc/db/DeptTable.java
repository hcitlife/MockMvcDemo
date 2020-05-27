package com.hc.db;

import com.hc.domain.Dept;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 梁云亮
 * @Date 2020/5/24 16:01
 * @Description:
 */
public class DeptTable {
    private static ArrayList<Dept> depts = new ArrayList<>();

    static {
        depts.add(new Dept(10, "ACCOUNTING", "CHICAGO"));
        depts.add(new Dept(20, "RESEARCH", "DALLAS"));
        depts.add(new Dept(30, "SALES", "CHICAGO"));
        depts.add(new Dept(40, "OPERATIONS", "BOSTON"));
    }

    public static boolean insert(Dept dept) {
        boolean res = depts.add(dept);
        return res;
    }

    public static boolean update(Dept dept) {
        Integer deptno = dept.getDeptno();
        boolean flag = false;
        for (int i = 0; i < depts.size(); i++) {
            Dept temp = depts.get(i);
            if (temp.getDeptno().equals(deptno)) {
                depts.set(i, dept);
                flag = true;
            }
        }
        return flag;
    }

    public static boolean delete(Integer deptno) {
        boolean flag = false;
        for (int i = 0; i < depts.size(); i++) {
            Dept dept = depts.get(i);
            if (dept.getDeptno().equals(deptno)) {
                depts.remove(i);
                flag = true;
            }
        }
        return flag;
    }

    public static Dept selectByDeptno(Integer deptno) {
        for (int i = 0; i < depts.size(); i++) {
            Dept dept = depts.get(i);
            if (dept.getDeptno().equals(deptno)) {
                return dept;
            }
        }
        return null;
    }
    public static List<Dept> selectAll() {
        return depts;
    }

    public static void output() {
        for (Dept dept : depts) {
            System.out.println(dept);
        }
    }
}
