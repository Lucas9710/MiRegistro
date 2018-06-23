package android.com.sirioibanes.adapters.holders;

import android.com.sirioibanes.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class TableViewHolder extends RecyclerView.ViewHolder {
    public TableViewHolder(View itemView) {
        super(itemView);
    }

    public void onBind(HashMap<String, String> map) {
        ((TextView) itemView.findViewById(R.id.value)).setText(map.get("nombre"));
    }
}
