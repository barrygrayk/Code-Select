/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

/**
 *
 * @author Barry Gray
 */
import java.io.InputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
public class FileDownloadView {

    private StreamedContent file;

    public FileDownloadView() {
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/loading.jpg");
        file = new DefaultStreamedContent(stream, "image/gif", "download_loading.gif");
    }

    public StreamedContent getFile() {
        return file;
    }
}
