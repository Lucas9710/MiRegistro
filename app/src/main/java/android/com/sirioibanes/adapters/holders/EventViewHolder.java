package android.com.sirioibanes.adapters.holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Date;

import android.com.sirioibanes.R;
import android.com.sirioibanes.dtos.Event;

public class EventViewHolder extends RecyclerView.ViewHolder {
    private final TextView titleView;
    private final TextView descriptionView;
    private final TextView dateView;

    public EventViewHolder(@NonNull final View itemView) {
        super(itemView);
        titleView = itemView.findViewById(R.id.title);
        descriptionView = itemView.findViewById(R.id.description);
        dateView = itemView.findViewById(R.id.date);
    }

    public void onBind(@NonNull final Event event) {
        titleView.setText(event.titulo);
        descriptionView.setText(event.descripcion);
        dateView.setText(getDate(event.timestamp));
    }

    private String getDate(long timeStamp){

        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
            Date netDate = (new Date(timeStamp*1000));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }
}
