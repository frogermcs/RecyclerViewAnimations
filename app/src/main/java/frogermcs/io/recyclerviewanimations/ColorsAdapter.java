package frogermcs.io.recyclerviewanimations;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Miroslaw Stanek on 06.01.2016.
 */
public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ColorViewHolder> {

    private MainActivity mainActivity;
    private ArrayList<Integer> colors = new ArrayList<>();

    public ColorsAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        colors.addAll(ColorsHelper.COLORS);
    }

    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ColorViewHolder colorViewHolder = new ColorViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.onColorsListItemClicked(v);
            }
        });
        return colorViewHolder;
    }

    @Override
    public void onBindViewHolder(ColorViewHolder holder, int position) {
        int color = colors.get(position);
        holder.itemView.setBackgroundColor(color);
        holder.tvColor.setText("#" + Integer.toHexString(color));
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public void removeItemAtPosition(int position) {
        colors.remove(position);
        notifyItemRemoved(position);
    }

    public void addItemAtPosition(int position) {
        colors.add(position, ColorsHelper.COLORS.get(position));
        notifyItemInserted(position);
    }

    public void changeItemAtPosition(int position) {
        colors.set(position, ColorsHelper.getRandomColor());
        notifyItemChanged(position);
    }

    static class ColorViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvColor)
        TextView tvColor;

        public ColorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
