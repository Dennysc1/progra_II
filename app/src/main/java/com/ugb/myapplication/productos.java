package com.ugb.myapplication;

public class productos {
    String _id;
    String _rev;
    String idProd;
    String presentacion;
    String descripcion;
    String codigo;
    String marca;
    String precio;
    String urlFotoProd;

    public productos(String _id, String _rev, String idProd, String presentacion, String descripcion, String codigo, String marca, String precio, String urlFoto) {
        this._id = _id;
        this._rev = _rev;
        this.idProd = idProd;
        this.presentacion = presentacion;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.marca = marca;
        this.precio = precio;
        this.urlFotoProd = urlFoto;
    }
    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public String get_rev() {
        return _rev;
    }
    public void set_rev(String _rev) {
        this._rev = _rev;
    }
    public String getUrlFotoProd() {
        return urlFotoProd;
    }

    public void setUrlFotoProd(String urlFotoProd) {
        this.urlFotoProd = urlFotoProd;
    }

    public String getIdProd() {
        return idProd;
    }

    public void setIdProd(String idProd) {
        this.idProd = idProd;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
