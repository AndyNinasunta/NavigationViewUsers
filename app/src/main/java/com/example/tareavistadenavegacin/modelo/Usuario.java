package com.example.tareavistadenavegacin.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Usuario implements Parcelable {

    String nomUsuario;
    String nombres;
    String contrasena;
    String img;
    JSONArray opciones;
    String rol;

    public Usuario() {
    }

    public Usuario(String nomUsuario, String nombres, String contrasena, String img, JSONArray opciones,String rol) {
        this.nomUsuario = nomUsuario;
        this.nombres = nombres;
        this.contrasena = contrasena;
        this.img = img;
        this.opciones = opciones;
        this.rol=rol;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public JSONArray getOpciones() {
        return opciones;
    }

    public void setOpciones(JSONArray opciones) {
        this.opciones = opciones;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    protected Usuario(Parcel in) {
        nomUsuario = in.readString();
        nombres = in.readString();
        contrasena = in.readString();
        img = in.readString();
        rol =in.readString();
        try {
            opciones = in.readByte() == 0x00 ? null : new JSONArray(in.readString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nomUsuario);
        dest.writeString(nombres);
        dest.writeString(contrasena);
        dest.writeString(img);
        dest.writeString(rol);
        if (opciones == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeString(opciones.toString());
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
}

