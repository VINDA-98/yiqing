package top.weidaboy.servicemain.poi;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;

public class PoiTest {
        /**
         * JXL 读取Excel文件
         * @author yangtingting
         * @date 2019/07/29
         */
        public static void main(String[] args) throws Exception {
            Cell cell = null;
            File fileget = new File("G:/家威的Excel文件/城乡养老批量参保登记模板板含村.xls");
            //创建workbook
            Workbook workbookget = Workbook.getWorkbook(fileget);
            //获取第一个工作表sheet
            Sheet sheetget = workbookget.getSheet(0);
            for (int i=2;i<=201;i++){
                String filemobanname = "G:/家威的Excel文件/城乡居民基本养老保险参保登记表1.xls";
                File  filemoban = new File(filemobanname);

//               //复制文件  FileUtils.copyFile(source, dest);
//               FileUtils.copyFile(filemoban, fileset);
//               //创建工作薄
                Workbook   workbookset = Workbook.getWorkbook(filemoban);
                //输出的模板文件
                String outFileName="G:/家威的Excel文件/"+sheetget.getCell(2,i).getContents()+".xls";

                // jxl.Workbook 对象是只读的，所以如果要修改Excel，需要创建一个可读的副本，
                // 副本指向原Excel文件（即下面的new File(excelpath)）
                //WritableWorkbook如果直接createWorkbook模版文件会覆盖原有的文件
                WritableWorkbook writeBook = Workbook.createWorkbook(new File(outFileName),workbookset);

                //读取第一个sheet
                WritableSheet sheet=writeBook.getSheet(0);

                //读取将要修改的cell
                //姓名  先是列，再是行
                WritableCell namecell=sheet.getWritableCell(1, 4);
                //性别
                WritableCell sexcell=sheet.getWritableCell(3, 4);
                //民族
                WritableCell mingzucell=sheet.getWritableCell(5, 4);
                //出生日期
                WritableCell chushengcell=sheet.getWritableCell(1, 5);
                //联系电话
                WritableCell phonecell=sheet.getWritableCell(4, 5);
                //身份号码
                WritableCell IDcell=sheet.getWritableCell(1, 6);
                //户籍所在地
                WritableCell hujicell=sheet.getWritableCell(1, 7);
                //居住地址
                WritableCell juzhucell=sheet.getWritableCell(1, 8);
                //邮编
                WritableCell youbiancell=sheet.getWritableCell(5, 8);
                //参保登记时间
                WritableCell timecell=sheet.getWritableCell(1, 9);

                //获取上一部cell的格式
                jxl.format.CellFormat cf = namecell.getCellFormat();
                //姓名
                Label lable=new Label(1, 4, sheetget.getCell(2,i).getContents());
                //将修改后的单元格格式设置成和原来一样的
                lable.setCellFormat(cf);
                //将修改后的cell放回sheet中
                sheet.addCell(lable);

                //性别  Cell ： 先是列，后是行   Lable : 先是列，后是行
                lable = new Label(3, 4, sheetget.getCell(3,i).getContents().substring(2));
                //将修改后的单元格格式设置成和原来一样的
                lable.setCellFormat(cf);
                //将修改后的cell放回sheet中
                sheet.addCell(lable);


                //民族  Cell ： 先是列，后是行   Lable : 先是列，后是行
                lable = new Label(5, 4, sheetget.getCell(5,i).getContents().substring(2));
                //将修改后的单元格格式设置成和原来一样的
                lable.setCellFormat(cf);
                //将修改后的cell放回sheet中
                sheet.addCell(lable);

                // 出生日期 Cell ： 先是列，后是行   Lable : 先是列，后是行
                lable = new Label(1, 5, sheetget.getCell(4,i).getContents());
                //将修改后的单元格格式设置成和原来一样的
                lable.setCellFormat(cf);
                //将修改后的cell放回sheet中
                sheet.addCell(lable);


                // 联系电话 Cell ： 先是列，后是行   Lable : 先是列，后是行
                lable = new Label(4, 5, sheetget.getCell(9,i).getContents());
                //将修改后的单元格格式设置成和原来一样的
                lable.setCellFormat(cf);
                //将修改后的cell放回sheet中
                sheet.addCell(lable);

                // 身份证号码 Cell ： 先是列，后是行   Lable : 先是列，后是行
                lable = new Label(1, 6, sheetget.getCell(1,i).getContents());
                //将修改后的单元格格式设置成和原来一样的
                lable.setCellFormat(cf);
                //将修改后的cell放回sheet中
                sheet.addCell(lable);


                // 户籍 Cell ： 先是列，后是行   Lable : 先是列，后是行
                lable = new Label(1, 7, sheetget.getCell(7,i).getContents());
                //将修改后的单元格格式设置成和原来一样的
                lable.setCellFormat(cf);
                //将修改后的cell放回sheet中
                sheet.addCell(lable);


                //居住地址 Cell ： 先是列，后是行   Lable : 先是列，后是行
                lable = new Label(1, 8, sheetget.getCell(10,i).getContents());
                //将修改后的单元格格式设置成和原来一样的
                lable.setCellFormat(cf);
                //将修改后的cell放回sheet中
                sheet.addCell(lable);

                //邮编 Cell ： 先是列，后是行   Lable : 先是列，后是行
                lable = new Label(5, 8, sheetget.getCell(11,i).getContents());
                //将修改后的单元格格式设置成和原来一样的
                lable.setCellFormat(cf);
                //将修改后的cell放回sheet中
                sheet.addCell(lable);

                //参保日期 Cell ： 先是列，后是行   Lable : 先是列，后是行
                lable = new Label(1, 9, sheetget.getCell(15,i).getContents());
                //将修改后的单元格格式设置成和原来一样的
                lable.setCellFormat(cf);
                //将修改后的cell放回sheet中
                sheet.addCell(lable);
                writeBook.write();
                writeBook.close();
                workbookset.close();
                System.out.println();
            }
            workbookget.close();
        }
}

//                for (i=2;i<3;i++){
//                    //获得统计表中每一行数据
//                    for (int j=0;j<sheetget.getColumns();j++){
//                        System.out.print(cell.getContents()+" ");
//                    }
//                    System.out.println();
//                }

//                for (i=0;i<sheetset.getRows();i++){
//                    for (int j=0;j<sheetset.getColumns();j++){
//                        Cell cell=sheetset.getCell(j,i);
//                        label = new Label(i,j,cell.getContents());
//                        System.out.print(cell.getContents()+" ");
//                    }
//                    System.out.println();
//                }
