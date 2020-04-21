package com.kcc.generatepdf.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.kcc.generatepdf.domain.Account;
import com.kcc.generatepdf.domain.Company;
import com.kcc.generatepdf.domain.Order;
import com.kcc.generatepdf.domain.OrderDetail;

import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        //1.新建document对象，可通过一下三种任意一种
        Document document = new Document(); // 默认页面大小是A4
        document.addTitle("k.c communication");
        document.addAuthor("kcc-tech");
        document.addSubject("order");
        document.addKeywords("kcc");
        document.addCreator("sulei");

        String filePath = "C://Users//w10ws//Documents//generatepdf//PDFDemo.pdf";
        PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(filePath));
        writer.setPageEvent(new Watermark());
        writer.setPageEvent(new MyHeaderFooter());

        document.open();

        new Main().generatePDF(document);

        document.close();

    }

    // 定义全局的字体静态变量
    private static Font titlefont;
    private static Font headfont;
    private static Font keyfont;
    private static Font textfont;
    private static Font orderId;
    // 最大宽度
    private static int maxWidth = 520;
    {
        try {
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            titlefont = new Font(bfChinese, 16, Font.BOLD);
            headfont = new Font(bfChinese, 12, Font.BOLD);
            keyfont = new Font(bfChinese, 10, Font.BOLD);
            textfont = new Font(bfChinese, 10, Font.NORMAL);
            orderId = new Font(bfChinese, 8, Font.BOLD);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public void generatePDF(Document document) throws DocumentException {
        String orderJson = "{\n" +
                "\t\"orderUuid\": \"PO201912260609\",\n" +
                "\t\"comment\": \"这是一条订单\",\n" +
                "\t\"companyList\": [{\n" +
                "\t\t\t\"orderUuid\": \"PO201912260609\",\n" +
                "\t\t\t\"name\": \"深圳捷联讯通科技有限公司\",\n" +
                "\t\t\t\"cellphone\": \"13802255316\",\n" +
                "\t\t\t\"type\": 1,\n" +
                "\t\t\t\"contacts\": \"聂小姐\",\n" +
                "\t\t\t\"email\": \"limei@edcwifi.com\",\n" +
                "\t\t\t\"fax\": \"000000\",\n" +
                "\t\t\t\"location\": \"我也不知道\",\n" +
                "\t\t\t\"telephone\": \"我也不知道\",\n" +
                "\t\t\t\"account\": {\n" +
                "\t\t\t\t\"bankName\": \"浙江义乌农村商业银行股份有限公司北苑支行凯吉分理处\",\n" +
                "\t\t\t\t\"bankAccount\": \"201000230648017\",\n" +
                "\t\t\t\t\"location\": \"我也不知道\",\n" +
                "\t\t\t\t\"telephone\": \"我也不知道\",\n" +
                "\t\t\t\t\"fax\": \"111111\",\n" +
                "\t\t\t\t\"tfn\": \"92330782MA2E7E1H5K\"\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"北京康诚尚德通信技术有限公\",\n" +
                "\t\t\t\"cellphone\": \"15711459123\",\n" +
                "\t\t\t\"type\": 1,\n" +
                "\t\t\t\"contacts\": \"苏雷\",\n" +
                "\t\t\t\"email\": \"lei.su@kc-tech.com\",\n" +
                "\t\t\t\"fax\": \"000000\",\n" +
                "\t\t\t\"location\": \"我也不知道\",\n" +
                "\t\t\t\"telephone\": \"我也不知道\",\n" +
                "\t\t\t\"account\": {\n" +
                "\t\t\t\t\"bankName\": \"华夏银行北京光华支\",\n" +
                "\t\t\t\t\"bankAccount\": \"4057200001804000031645\",\n" +
                "\t\t\t\t\"location\": \"北京市朝阳区东四环中路62号楼远洋国际中心D座10层1005\",\n" +
                "\t\t\t\t\"telephone\": \"15711459123\",\n" +
                "\t\t\t\t\"fax\": \"111111\",\n" +
                "\t\t\t\t\"tfn\": \"911101056949533316\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\n" +
                "\t],\n" +
                "\t\"orderDetailList\": [{\n" +
                "\t\t\t\"id\": 1,\n" +
                "\t\t\t\"orderId\": 8,\n" +
                "\t\t\t\"currency\": \"人民币\",\n" +
                "\t\t\t\"describe\": \"金属LOGO\",\n" +
                "\t\t\t\"pack\": \"1000个\",\n" +
                "\t\t\t\"price\": 180.0,\n" +
                "\t\t\t\"quantity\": 1,\n" +
                "\t\t\t\"supplyDate\": \"现货60片，期货交期15天\",\n" +
                "\t\t\t\"taxRate\": 0.03\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"id\": 1,\n" +
                "\t\t\t\"orderId\": 9,\n" +
                "\t\t\t\"currency\": \"人民币\",\n" +
                "\t\t\t\"describe\": \"金属LOGO\",\n" +
                "\t\t\t\"pack\": \"1000个\",\n" +
                "\t\t\t\"price\": 180.0,\n" +
                "\t\t\t\"quantity\": 1,\n" +
                "\t\t\t\"supplyDate\": \"现货60片，期货交期15天\",\n" +
                "\t\t\t\"taxRate\": 0.03\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"id\": 1,\n" +
                "\t\t\t\"orderId\": 10,\n" +
                "\t\t\t\"currency\": \"人民币\",\n" +
                "\t\t\t\"describe\": \"金属LOGO\",\n" +
                "\t\t\t\"pack\": \"1000个\",\n" +
                "\t\t\t\"price\": 180.0,\n" +
                "\t\t\t\"quantity\": 1,\n" +
                "\t\t\t\"supplyDate\": \"现货60片，期货交期15天\",\n" +
                "\t\t\t\"taxRate\": 0.03\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"id\": 1,\n" +
                "\t\t\t\"orderId\": 11,\n" +
                "\t\t\t\"currency\": \"人民币\",\n" +
                "\t\t\t\"describe\": \"金属LOGO\",\n" +
                "\t\t\t\"pack\": \"1000个\",\n" +
                "\t\t\t\"price\": 180.0,\n" +
                "\t\t\t\"quantity\": 1,\n" +
                "\t\t\t\"supplyDate\": \"现货60片，期货交期15天\",\n" +
                "\t\t\t\"taxRate\": 0.03\n" +
                "\t\t}\n" +
                "\n" +
                "\t]\n" +
                "}";

        Gson gson = new Gson();
        Order order = gson.fromJson(orderJson, new TypeToken<Order>() {}.getType());

        //段落
        Paragraph titleOrder = new Paragraph("ORDER", titlefont);
        titleOrder.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        titleOrder.setIndentationLeft(12); //设置左缩进
        titleOrder.setIndentationRight(12); //设置右缩进
        titleOrder.setFirstLineIndent(24); //设置首行缩进
        titleOrder.setLeading(20f); //行间距
        titleOrder.setSpacingBefore(5f); //设置段落上空白
        titleOrder.setSpacingAfter(10f); //设置段落下空白

        Paragraph titleBuyerAccount = new Paragraph("采购商账户信息", headfont);
        titleBuyerAccount.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        titleBuyerAccount.setIndentationLeft(12); //设置左缩进
        titleBuyerAccount.setIndentationRight(12); //设置右缩进
        titleBuyerAccount.setFirstLineIndent(24); //设置首行缩进
        titleBuyerAccount.setLeading(20f); //行间距
        titleBuyerAccount.setSpacingBefore(20f); //设置段落上空白
        titleBuyerAccount.setSpacingAfter(10f); //设置段落下空白

        Paragraph titleSupplierAccount = new Paragraph("供应商账户信息", headfont);
        titleSupplierAccount.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        titleSupplierAccount.setIndentationLeft(12); //设置左缩进
        titleSupplierAccount.setIndentationRight(12); //设置右缩进
        titleSupplierAccount.setFirstLineIndent(24); //设置首行缩进
        titleSupplierAccount.setLeading(20f); //行间距
        titleSupplierAccount.setSpacingBefore(20f); //设置段落上空白
        titleSupplierAccount.setSpacingAfter(10f); //设置段落下空白

        Paragraph titleOrderDetails = new Paragraph("订单产品明细", headfont);
        titleOrderDetails.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        titleOrderDetails.setIndentationLeft(12); //设置左缩进
        titleOrderDetails.setIndentationRight(12); //设置右缩进
        titleOrderDetails.setFirstLineIndent(24); //设置首行缩进
        titleOrderDetails.setLeading(20f); //行间距
        titleOrderDetails.setSpacingBefore(20f); //设置段落上空白
        titleOrderDetails.setSpacingAfter(10f); //设置段落下空白

        Phrase number = new Phrase();
        number.setLeading(30f);
        number.setFont(orderId);
        number.add("Number: " + order.getOrderUuid());

        // 直线
        Paragraph p1 = new Paragraph();
        p1.setSpacingBefore(-10f);
        p1.setSpacingAfter(40f);

        p1.add(new Chunk(new LineSeparator()));

        Company supplier;
        Company buyer;
        List<Company> companyList = order.getCompanyList();
        if(companyList.get(0).getType().equals(1)){
            buyer = companyList.get(1);
            supplier = companyList.get(0);
        }else {
            buyer = companyList.get(0);
            supplier = companyList.get(1);
        }

        PdfPTable tableCompany = createTable(new float[] { 60, 120, 60, 120 });

        addTableCell(tableCompany, "供应商:", supplier.getName());
        addTableCell(tableCompany, "采购商:", buyer.getName());

        addTableCell(tableCompany, "联系人姓名:", supplier.getContacts());
        addTableCell(tableCompany, "联系人姓名:", buyer.getContacts());

        addTableCell(tableCompany, "电子邮件:", supplier.getEmail());
        addTableCell(tableCompany, "电子邮件:", buyer.getEmail());

        addTableCell(tableCompany, "电话:", supplier.getCellphone());
        addTableCell(tableCompany, "电话:", buyer.getCellphone());

        addTableCell(tableCompany, "传真:", supplier.getFax());
        addTableCell(tableCompany, "传真:", buyer.getFax());

        addTableCell(tableCompany, "手机:", supplier.getTelephone());
        addTableCell(tableCompany, "手机:", buyer.getTelephone());

        addTableCell(tableCompany, "地址:", supplier.getLocation());
        addTableCell(tableCompany, "地址:", buyer.getLocation());

        PdfPTable tableBuyerAccount = createTable(new float[] { 60, 120, 60, 120});
        Account buyerAccount = buyer.getAccount();

        addTableCell(tableBuyerAccount, "公司全称:", buyer.getName());
        addTableCell(tableBuyerAccount, "电话号码:", buyerAccount.getTelephone());

        addTableCell(tableBuyerAccount, "交货地址:",0);
        addTableCell(tableBuyerAccount, buyerAccount.getLocation(), 3);

        addTableCell(tableBuyerAccount, "开户银行:", buyerAccount.getBankName());
        addTableCell(tableBuyerAccount, "银行账号:", buyerAccount.getBankAccount());

        addTableCell(tableBuyerAccount, "纳税人识别号:", buyerAccount.getFax());
        addTableCell(tableBuyerAccount, "传真号码:", buyerAccount.getTfn());

        PdfPTable tableSupplierAccount = createTable(new float[] { 60, 120, 60, 120});
        Account supplierAccount = supplier.getAccount();

        addTableCell(tableSupplierAccount, "公司全称:", supplier.getName());
        addTableCell(tableSupplierAccount, "电话号码:", supplierAccount.getTelephone());

        addTableCell(tableSupplierAccount, "交货地址:", 0);
        addTableCell(tableSupplierAccount, supplierAccount.getLocation(), 3);

        addTableCell(tableSupplierAccount, "开户银行:", supplierAccount.getBankName());
        addTableCell(tableSupplierAccount, "银行账号:", supplierAccount.getBankAccount());

        addTableCell(tableSupplierAccount, "纳税人识别号:", supplierAccount.getFax());
        addTableCell(tableSupplierAccount, "传真号码:", supplierAccount.getTfn());

        List<OrderDetail> orderDetailList = order.getOrderDetailList();

        PdfPTable tableOrderDetails = createTable(new float[] { 15, 60, 30, 15, 30, 30, 30, 70});

        for (int i = 0; i < orderDetailList.size(); i++) {
            OrderDetail orderDetail = orderDetailList.get(i);
            addOrderTableCell(tableOrderDetails, i+1, orderDetail);
        }

        document.add(titleOrder);
        document.add(number);
        document.add(p1);
        document.add(tableCompany);
        document.add(titleBuyerAccount);
        document.add(tableBuyerAccount);
        document.add(titleSupplierAccount);
        document.add(tableSupplierAccount);
        document.add(titleOrderDetails);
        document.add(tableOrderDetails);
    }

    private void addTableCell(PdfPTable pdfPTable, Object key, Object value){
        pdfPTable.addCell(createCell(String.valueOf(key), keyfont, Element.ALIGN_CENTER));
        pdfPTable.addCell(createCell(String.valueOf(value), keyfont, Element.ALIGN_CENTER));
    }

    private void addTableCell(PdfPTable pdfPTable, Object value, int colspan){
        pdfPTable.addCell(createCell(String.valueOf(value), keyfont, Element.ALIGN_CENTER, colspan));
    }

    public void addOrderTableCell(PdfPTable tableOrderDetails, int index, OrderDetail orderDetail){
        tableOrderDetails.addCell(createCell(String.valueOf(index), keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell(orderDetail.getDescribe(), keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell(orderDetail.getPack(), keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell(String.valueOf(orderDetail.getQuantity()), keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell(orderDetail.getCurrency(), keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell(String.valueOf(orderDetail.getPrice()), keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell(String.valueOf(orderDetail.getPrice() * orderDetail.getQuantity()), keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell(orderDetail.getSupplyDate(), keyfont, Element.ALIGN_CENTER));
    }

    /**------------------------创建表格单元格的方法start----------------------------*/
    /**
     * 创建单元格(指定字体)
     * @param value
     * @param font
     * @return
     */
    public PdfPCell createCell(String value, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平..）
     * @param value
     * @param font
     * @param align
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并）
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并、设置单元格内边距）
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @param boderFlag
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        cell.setPadding(3.0f);
        if (!boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        } else if (boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(0.0f);
            cell.setPaddingBottom(15.0f);
        }
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平..、边框宽度：0表示无边框、内边距）
     * @param value
     * @param font
     * @param align
     * @param borderWidth
     * @param paddingSize
     * @param flag
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, float[] borderWidth, float[] paddingSize, boolean flag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        cell.setBorderWidthLeft(borderWidth[0]);
        cell.setBorderWidthRight(borderWidth[1]);
        cell.setBorderWidthTop(borderWidth[2]);
        cell.setBorderWidthBottom(borderWidth[3]);
        cell.setPaddingTop(paddingSize[0]);
        cell.setPaddingBottom(paddingSize[1]);
        if (flag) {
            cell.setColspan(2);
        }
        return cell;
    }
/**------------------------创建表格单元格的方法end----------------------------*/


/**--------------------------创建表格的方法start------------------- ---------*/
    /**
     * 创建默认列宽，指定列数、水平(居中、右、左)的表格
     * @param colNumber
     * @param align
     * @return
     */
    public PdfPTable createTable(int colNumber, int align) {
        PdfPTable table = new PdfPTable(colNumber);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(align);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
    /**
     * 创建指定列宽、列数的表格
     * @param widths
     * @return
     */
    public PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
    /**
     * 创建空白的表格
     * @return
     */
    public PdfPTable createBlankTable() {
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(0);
        table.addCell(createCell("", keyfont));
        table.setSpacingAfter(20.0f);
        table.setSpacingBefore(20.0f);
        return table;
    }
/**--------------------------创建表格的方法end------------------- ---------*/
}
