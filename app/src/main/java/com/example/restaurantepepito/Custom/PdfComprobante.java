package com.example.restaurantepepito.Custom;

import android.Manifest;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.restaurantepepito.Model.DocumentoModel;
import com.example.restaurantepepito.Model.PedidoModel;
import com.example.restaurantepepito.Model.Pedido_x_PlatopModel;
import com.example.restaurantepepito.Model.RestauranteModel;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;

import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PdfComprobante {


    private String DIRECTORIO = "MisComprobantes";


    private String DOCUMENTO;
    private List<Pedido_x_PlatopModel> pedido_x_platopModelList;
    private DocumentoModel documentoModel;

    public PdfComprobante(String DOCUMENTO, List<Pedido_x_PlatopModel> pedido_x_platopModelList, DocumentoModel documentoModel) {
        this.DOCUMENTO = DOCUMENTO;
        this.pedido_x_platopModelList = pedido_x_platopModelList;
        this.documentoModel = documentoModel;
    }

    public PdfComprobante() {
    }

    public String getDOCUMENTO() {
        return DOCUMENTO;
    }

    public void setDOCUMENTO(String DOCUMENTO) {
        this.DOCUMENTO = DOCUMENTO;
    }

    public List<Pedido_x_PlatopModel> getPedido_x_platopModelList() {
        return pedido_x_platopModelList;
    }

    public void setPedido_x_platopModelList(List<Pedido_x_PlatopModel> pedido_x_platopModelList) {
        this.pedido_x_platopModelList = pedido_x_platopModelList;
    }

    public DocumentoModel getDocumentoModel() {
        return documentoModel;
    }

    public void setDocumentoModel(DocumentoModel documentoModel) {
        this.documentoModel = documentoModel;
    }

    public void crearPDF() {



        Font titulo = new Font(1, 20,Font.BOLD);
        Font subtitulo = new Font(1, 10,Font.BOLD);

        Document document = new Document();

        try{
            File file = crearFichero(DOCUMENTO);
            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
            PdfWriter writer = PdfWriter.getInstance(document, ficheroPDF);
            document.open();



            document.add(new Paragraph("RESTAURANTE PEPITO \n \n",titulo));
            document.add(new Paragraph("Direccion:  ",subtitulo));
            document.add(new Paragraph(documentoModel.getDistrito() +" " + documentoModel.getLocalidad() + " " +documentoModel.getDireccion()+  " \n"));
            document.add(new Paragraph("Correo: ",subtitulo));
            document.add(new Paragraph(documentoModel.getCorreo() +" \n"));


            document.add(new Paragraph("----------------------------------------------------------------------------------------------------------- \n \n"));

            document.add(new Paragraph("PEDIDO\n",titulo));
            document.add(new Paragraph("Tipo de Pedido: ",subtitulo));
            document.add(new Paragraph(documentoModel.getTipo()+" \n"));
            document.add(new Paragraph("Fecha de Entrega o Recogo: ",subtitulo));
            document.add(new Paragraph(documentoModel.getFecha()+" \n"));
            document.add(new Paragraph("Fecha del Pedido: ",subtitulo));
            document.add(new Paragraph(documentoModel.getFecha_pedido()+" \n"));
            document.add(new Paragraph("Hora: ",subtitulo));
            document.add(new Paragraph(documentoModel.getHora_inicio() +" a " + documentoModel.getHora_final() +" \n"));
            document.add(new Paragraph("Direccion de Envio: ",subtitulo));
            document.add(new Paragraph(documentoModel.getDistrito() + " " + documentoModel.getLocalidad() +" "+ documentoModel.getDireccion() + " \n"));


            document.add(new Paragraph("----------------------------------------------------------------------------------------------------------- \n"));


            document.add(new Paragraph("DETALLE DE COMPRA \n \n",titulo));



            PdfPTable tabla = new PdfPTable(3);



            tabla.addCell("Cantidad");
            tabla.addCell("Plato");
            tabla.addCell("SubTotal");

            for(int i = 0;i<pedido_x_platopModelList.size();i++){
                tabla.addCell(pedido_x_platopModelList.get(i).getCantidad().toString());
                tabla.addCell(pedido_x_platopModelList.get(i).getNombre());
                tabla.addCell("S/"+pedido_x_platopModelList.get(i).getSubtotal().toString());

            }


            tabla.setHorizontalAlignment(Element.ALIGN_LEFT);
            document.add(tabla);
            document.add(new Paragraph(" \n"));
            document.add(new Paragraph("----------------------------------------------------------------------------------------------------------- \n"));
            document.add(new Paragraph("TOTAL\n",titulo));
            document.add(new Paragraph("Costo de Envio: ",subtitulo));
            document.add(new Paragraph("S/"+documentoModel.getPrecio_entrega()+" \n"));
            document.add(new Paragraph("Total: \n",subtitulo));
            document.add(new Paragraph("S/"+documentoModel.getTotal()+" \n"));


        }

        catch (DocumentException e) {
            e.printStackTrace();
        }
        catch(IOException e){

        }
        finally {
            document.close();
        }

    }

    private File crearFichero(String nombreFichero) {
        File ruta = getRuta();
        File fichero = null;
        if(ruta != null){
            fichero = new File(ruta, nombreFichero);
        }

        return fichero;
    }

    public File getRuta(){
        File ruta = null;
        File descargas = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);





        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            ruta = new File(descargas, DIRECTORIO);

            if(ruta != null){
                if(!ruta.mkdir()){
                    if(!ruta.exists()){
                        return null;
                    }
                }
            }

        }
        return ruta;
    }

}
