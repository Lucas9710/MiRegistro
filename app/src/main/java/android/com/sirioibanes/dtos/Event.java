package android.com.sirioibanes.dtos;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.HashMap;

public class Event implements Serializable {

    public final String titulo;
    public final String lugar;
    public final String descripcion;
    public final Boolean habilitada;
    public final String foto;
    public final String telefono;
    public final Long timestamp;
    public final HashMap<String, HashMap> redes;
    public final String key;
    public final HashMap<String, String> invitados;

    public Event(@NonNull final String eventKey, AbstractMap<String, Object> eventObject) {
        this.key = eventKey;
        this.titulo = (String) eventObject.get("titulo");
        this.lugar = (String) eventObject.get("lugar");
        this.descripcion = (String) eventObject.get("descripcion");
        this.habilitada = (Boolean) (eventObject.get("habilitada"));
        this.foto = (String) eventObject.get("foto");
        this.telefono = (String) eventObject.get("telefono");
        this.timestamp = (Long) eventObject.get("timestamp");
        this.redes = (HashMap<String, HashMap>) eventObject.get("redes");
        this.invitados = (HashMap<String, String>) eventObject.get("invitados");
    }
}
