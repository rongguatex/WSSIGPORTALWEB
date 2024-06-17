/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletOutputStream;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author PJUNIOR-3
 */
public class E_ReporteClienteExcel {

    private final XSSFWorkbook workbook;
    private final XSSFSheet sheet;
    private final DecimalFormat df;
    private final SimpleDateFormat fechaEmision;
    private final SimpleDateFormat horaEmision;

    public E_ReporteClienteExcel() {
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet("Reporte Clientes");
        this.df = new DecimalFormat("Q###,##0.00");
        this.fechaEmision = new SimpleDateFormat("dd/MM/yyyy");
        this.horaEmision = new SimpleDateFormat("HH:mm:ss");
    }

    public boolean generarXLSX(EReporteClientes cliente, List<EReporteClientes> clientes, ServletOutputStream outputStream) throws IOException {
        boolean respuesta = false;

        // Crear estilos
        CellStyle estilo1 = crearEstilo(workbook, (short) 24, true);
        CellStyle estilo2 = crearEstilo(workbook, (short) 12, true);
        CellStyle estilo2v = crearEstilo(workbook, (short) 12, true);
        estilo2v.setBorderBottom(BorderStyle.MEDIUM);
        CellStyle estiloSimple = crearEstilo(workbook, (short) 10, false);
        estiloSimple.setBorderBottom(BorderStyle.MEDIUM);
        CellStyle estiloDoble = crearEstilo(workbook, (short) 12, true);
        estiloDoble.setBorderBottom(BorderStyle.MEDIUM);
        estiloDoble.setBorderTop(BorderStyle.MEDIUM);
        CellStyle estiloCentrado = workbook.createCellStyle();
        estiloCentrado.setAlignment(HorizontalAlignment.CENTER);

        // Agregar título
        int noColumna = 0;

        // Agregar Título General en la fila 1
        Row t1 = sheet.createRow(noColumna++);

        Cell TituloGeneral = t1.createCell(0);
        TituloGeneral.setCellValue("Transporte, Empaque y Almacenaje, S.A.");
        TituloGeneral.setCellStyle(estilo2);

        // Saltar una fila y agregar el segundo título en la fila 3
        Row t2 = sheet.createRow(noColumna++);

        Cell TituloGeneral2 = t2.createCell(0);
        TituloGeneral2.setCellValue("Reporte de Guias");
        TituloGeneral2.setCellStyle(estilo2);
        noColumna += 1;

        // Agregar Emisión en la fila 3, columna A
        Row TituloEmision = sheet.createRow(3); // Fila 3
        Cell CeldaEmision = TituloEmision.createCell(0); // Columna A
        CeldaEmision.setCellValue("Emisión: " + fechaEmision.format(new Date()) + " Hora: " + horaEmision.format(new Date()));

        // Agregar descripción del cliente
        String[] TituloDescripcion = {"", "CLIENTE:", "CÓDIGO:", "DIRECCIÓN:", ""};
        String[] DatosDescripcion = {cliente.getCODIGO(), cliente.getCODCOB(), cliente.getDIRECCION()};

        for (int i = 0; i < TituloDescripcion.length; i++) {
            Row ColumnaDescripcion = sheet.createRow(noColumna += 1);
            ColumnaDescripcion.setHeight((short) 300);

            Cell celda = ColumnaDescripcion.createCell(0);
            celda.setCellStyle(estilo2);

            // Componer la celda con el título y el dato correspondiente
            if (i > 0 && i - 1 < DatosDescripcion.length) {
                celda.setCellValue(TituloDescripcion[i] + " " + DatosDescripcion[i - 1]);
            } else {
                celda.setCellValue(TituloDescripcion[i]);
            }
        }
        noColumna++;

        // Crear encabezado de la tabla
        String[] titulos = {"CODIGO", "NOMBRE", "CONTACTO", "NIT", "EMAIL", "TELEFONO", "DIRECCION", "PUNTO", "LUGAR COBERTURA", "CAMPO1", "CAMPO2", "CAMPO3", "CAMPO4"};
        Row encabezado = sheet.createRow(noColumna++);
        for (int i = 0; i < titulos.length; i++) {
            Cell cell = encabezado.createCell(i);
            cell.setCellValue(titulos[i]);
            cell.setCellStyle(estilo2);
        }

        // Llenar datos de los clientes
        for (EReporteClientes client : clientes) {
            Row fila = sheet.createRow(noColumna++);
            fila.createCell(0).setCellValue(client.getCODIGO());
            fila.createCell(1).setCellValue(client.getNOMBRE());
            fila.createCell(2).setCellValue(client.getCONTACTO());
            fila.createCell(3).setCellValue(client.getNIT());
            fila.createCell(4).setCellValue(client.getEMAIL());
            fila.createCell(5).setCellValue(client.getTELEFONO());
            fila.createCell(6).setCellValue(client.getDIRECCION());
            fila.createCell(7).setCellValue(client.getPUNTO());
            fila.createCell(8).setCellValue(client.getRECOGEOFICINA());
            fila.createCell(9).setCellValue(client.getCAMPO1());
            fila.createCell(10).setCellValue(client.getCAMPO2());
            fila.createCell(11).setCellValue(client.getCAMPO3());
            fila.createCell(12).setCellValue(client.getCAMPO4());
        }

        // Ajustar tamaño de las columnas
        for (int i = 0; i < titulos.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Guardar archivo
        try (OutputStream out = outputStream) {
            workbook.write(out);
            out.flush();
            respuesta = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }

        return respuesta;
    }

    private CellStyle crearEstilo(XSSFWorkbook workbook, short fontSize, boolean isBold) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints(fontSize);
        font.setBold(isBold);
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

}
