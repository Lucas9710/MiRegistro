package android.com.sirioibanes.adapters;

import android.com.sirioibanes.R;
import android.com.sirioibanes.adapters.holders.TableViewHolder;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableViewHolder> {

    private final List<HashMap<String, String>> mList = new ArrayList<>();

    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TableViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType,
                parent, false));
    }

    @Override
    public void onBindViewHolder(final TableViewHolder holder, final int position) {
        holder.onBind(mList.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setItems(@NonNull final List<HashMap<String, String>> mapList) {
        mList.clear();
        mList.addAll(mapList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.holder_table;
    }
}
