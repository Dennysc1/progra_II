package com.ugb.myapplication;

public class fernando {
    String _id;
    String _rev;
    String idcos;
    String costo;
    String precio;
    String stock;
    String urlFotoAmigo;

    public fernando(String _id, String _rev, String idcos, String costo, String precio, String stock, String urlFoto) {
        this._id = _id;
        this._rev = _rev;
        this.idcos = idcos;
        this.costo = costo;
        this.precio = precio;
        this.stock = stock;
        this.urlFotoAmigo = urlFoto;
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
    public String getUrlFotoAmigo() {
        return urlFotoAmigo;
    }

    public void setUrlFotoAmigo(String urlFotoAmigo) {
        this.urlFotoAmigo = urlFotoAmigo;
    }

    public String getIdcos() {
        return idcos;
    }

    public void setIdcos(String idcos) {
        this.idcos = idcos;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
