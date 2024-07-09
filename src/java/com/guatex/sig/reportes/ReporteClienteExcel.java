/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.reportes;

import com.guatex.sig.datos.DReporteClientes;
import com.guatex.sig.entidades.EReporteClientes;
import com.guatex.sig.entidades.E_ReporteClienteExcel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PJUNIOR-3
 */
public class ReporteClienteExcel extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String codigo = request.getParameter("CODIGO");

        if (codigo == null || codigo.isEmpty()) {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                /* Muestra un mensaje de error si no se proporciona el código */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet ReporteClienteExcel</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>El parámetro CODIGO es requerido.</h1>");
                out.println("</body>");
                out.println("</html>");
            }
        } else {
            response.setHeader("Content-Disposition", "attachment;filename=ReporteClienteExcel.xlsx");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            DReporteClientes objClientes = new DReporteClientes();
            List<EReporteClientes> mostrarClientes = objClientes.reporteClientes(codigo);

            if (mostrarClientes != null && !mostrarClientes.isEmpty()) {
                try (ServletOutputStream outStream = response.getOutputStream()) {
                    EReporteClientes cliente = mostrarClientes.get(0); 
                    E_ReporteClienteExcel reporte = new E_ReporteClienteExcel();
                    reporte.generarXLSX(cliente, mostrarClientes, outStream, codigo);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
