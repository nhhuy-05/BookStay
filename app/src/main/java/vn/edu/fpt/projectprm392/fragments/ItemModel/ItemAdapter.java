package vn.edu.fpt.projectprm392.fragments.ItemModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import vn.edu.fpt.projectprm392.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    private Context mContext;
    private List<Item> mList;

    public ItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Item> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile, parent, false);
        return new ItemViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = mList.get(position);
        if (item != null){
            holder.img_itemProfile.setImageResource(item.getResouceId());
            holder.tv_itemProfileName.setText(item.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_itemProfile;
        private TextView tv_itemProfileName;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            img_itemProfile = (ImageView) itemView.findViewById(R.id.img_itemProfile);
            tv_itemProfileName = (TextView) itemView.findViewById(R.id.tv_itemProfileName);
        }
    }
}
