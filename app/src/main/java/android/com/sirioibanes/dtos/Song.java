package android.com.sirioibanes.dtos;

import android.com.sirioibanes.adapters.holders.SongViewHolder;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

@IgnoreExtraProperties
public class Song {
    public String artista;
    public String tema;
    public Long votos;
    public Long ranking;
    public String user;
    public Boolean votado;
    public Song() {
    }


    public Song(HashMap<String, Object> map) {
        //METODO DE INCIALIZACION
        this.artista = (String) map.get("artista");
        this.tema = (String) map.get("tema");
        this.votos = (Long) map.get("votos");
        this.user = (String) map.get("user");
        this.votado = false;
    }

    public String getArtista() {
        return artista;
    }

    public String getTema() {
        return tema;
    }

    public int getVotos() {
        return votos.intValue();
    }

    public void vote(final int type) {
        if (type == SongViewHolder.VOTE_UP) {
            this.votado = true;
            this.votos++;
        } else {
            this.votado = false;
            this.votos--;
        }
    }
}
