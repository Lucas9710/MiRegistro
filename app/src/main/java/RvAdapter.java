import android.com.sirioibanes.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RegistroViewHolder> {

    List<Registro> listaregistro;

    public RvAdapter(List<Registro> listaregistro) {
        this.listaregistro = listaregistro;
    }

    @Override
    public RegistroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_registro,parent,false);
        RegistroViewHolder RegistroViewHolder = new RegistroViewHolder(view);
        return RegistroViewHolder;

    }
    @Override
    public void onBindViewHolder(RegistroViewHolder holder, int position) {

        Registro currentRegistro = listaregistro.get(position);

        String stateText = currentRegistro.state;
        String dateText = String.valueOf(currentRegistro.timestamp);

        holder.stateLabel.setText(stateText);
        holder.fechaLabel.setText(dateText);
    }

    @Override
    public int getItemCount() {
        return listaregistro.size() ;
    }

    public static class RegistroViewHolder extends RecyclerView.ViewHolder {

        TextView fechaLabel;
        TextView stateLabel;

        public RegistroViewHolder(View itemView){
            super(itemView);

            fechaLabel = (TextView) itemView.findViewById(R.id.date_label);
            stateLabel = (TextView) itemView.findViewById(R.id.state_label);
        }
    }
}

