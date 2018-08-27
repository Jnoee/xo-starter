package com.github.jnoee.xo.excel;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.jnoee.xo.excel.config.ExcelAutoConfiguration;
import com.github.jnoee.xo.excel.model.Department;
import com.github.jnoee.xo.excel.model.Employee;
import com.github.jnoee.xo.utils.DateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExcelAutoConfiguration.class)
public abstract class AbstractSample {
  protected String outputDir = "src/test/resources/META-INF/su/export";
  @Autowired
  protected ExcelFactory excelFactory;

  protected List<Department> genDepartments(Integer count) {
    List<Department> departments = new ArrayList<Department>();

    for (int i = 0; i < count; i++) {
      Department department = new Department();
      department.setName("部门" + i);

      Employee chief = genEmployees(1, null).get(0);
      chief.setName("部门主管" + i);

      department.setChief(chief);
      department.setEmployees(genEmployees(10, chief));

      departments.add(department);
    }

    return departments;
  }

  protected List<Employee> genEmployees(Integer count, Employee superior) {
    List<Employee> employees = new ArrayList<Employee>();

    for (int i = 0; i < count; i++) {
      Employee employee = new Employee();
      employee.setName("职员" + i);
      employee.setAge(30 + i);
      employee.setBirthDate(DateUtils.parse(1983 - i + "-01-01"));
      employee.setPayment(20000.00 + 1000 * i);
      employee.setBonus(0.25 - i * 0.01);
      employee.setSuperior(superior);
      employees.add(employee);
    }

    return employees;
  }
}
