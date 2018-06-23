package android.com.sirioibanes.adapters.holders;

import android.com.sirioibanes.R;
import android.com.sirioibanes.dtos.Song;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//ARRIBA DE ESTAN LAS DEPENDENCIAS DE ESTA CLASE

public class SongViewHolder extends RecyclerView.ViewHolder {

    //VARIABLES GLOBALES
    public static final int VOTE_DOWN = 0;
    public static final int VOTE_UP = 1;
    private final TextView artistLabel;
    private final TextView cancionLabel;
    private final TextView userLabel;
    private final TextView votosLabel;
    private final TextView rankingLabel;
    private final ImageView trofeoImageView;
    private final ImageView corazonImageView;



    @IntDef({VOTE_DOWN, VOTE_UP})
    @Retention(RetentionPolicy.SOURCE)


    //METODOS
    public @interface VoteType {
    }

    public SongViewHolder(View itemView) {
        //este metodo se ejecuta al inicializar de la celda
        super(itemView);
        artistLabel = (TextView) itemView.findViewById(R.id.artistLabel);
        cancionLabel = (TextView) itemView.findViewById(R.id.songLabel);
        userLabel = (TextView) itemView.findViewById(R.id.userLabel);
        votosLabel = (TextView) itemView.findViewById(R.id.voteLabel);
        rankingLabel = (TextView) itemView.findViewById(R.id.rankingLabel);
        trofeoImageView = (ImageView) itemView. findViewById(R.id.imageView);
        corazonImageView = (ImageView) itemView. findViewById(R.id.imageButton);
    }

    public void onBind(@NonNull final Song song) {
        //este metodo se ejecuta cuando queremos impactar la informacion de una cancion


        //ARTISTA
        String artistText;
        if (song.artista == null) {
            artistText = "Artista desconocido";
        } else {
            artistText = song.artista;
        }
        artistLabel.setText(artistText);

        //CANCION
        String cancionText;
        if (song.tema == null) {
            cancionText = "Cancion Desconocido";
        } else {
            cancionText = song.tema;
        }
        cancionLabel.setText(cancionText);

        //USER
        String userText;
        if (song.user == null) {
            userText = "Usuario Desconocido";
        } else {
            userText = song.user;
        }
        userLabel.setText(userText);

        //VOTE
        String voteText;
        if (song.votos == null) {
            voteText = "0";
        } else {
            String strLong = Long.toString(song.votos);
            //song.votos = 1 -> NO FUNCIONA
            //strLong = "1" -> SI FUNCIONA
            voteText = strLong;
        }
        votosLabel.setText(voteText);

        //RANKING
        String rankingText;
        if (song.ranking == null) {
            rankingText = "0";
        } else  {
            String stringLong = Long.toString(song.ranking);
            rankingText = stringLong;
        }
        rankingLabel.setText(rankingText);

        //IMAGEN

        int position = song.ranking.intValue();

        switch (position) {
            case 1:
                trofeoImageView.setImageResource(R.drawable.ranking1);
                rankingLabel.setVisibility(View.INVISIBLE);
                trofeoImageView.setVisibility(View.VISIBLE);
                break;
            case 2:
                trofeoImageView.setImageResource(R.drawable.ranking2);
                rankingLabel.setVisibility(View.INVISIBLE);
                trofeoImageView.setVisibility(View.VISIBLE);
                break;
            case 3:
                trofeoImageView.setImageResource(R.drawable.ranking3);
                rankingLabel.setVisibility(View.INVISIBLE);
                trofeoImageView.setVisibility(View.VISIBLE);
                break;
            default:
                trofeoImageView.setVisibility(View.INVISIBLE);
                rankingLabel.setVisibility(View.VISIBLE);
                break;
        }

        if (song.votado) {
            corazonImageView.setImageResource(R.drawable.redheart);
            votosLabel.setTextColor(Color.WHITE);
        } else{
            corazonImageView.setImageResource(R.drawable.heartvoid);
            votosLabel.setTextColor(Color.RED);

        }

    }
}

