package top.weidaboy.servicemain.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserExcel {
    @Excel(name = "id", orderNum = "0")
    private Integer id;
    @Excel(name = "姓名", orderNum = "1")
    private String name;
    @Excel(name = "年龄", orderNum = "2")
    private Integer age;

}
