package vn.edu.fpt.projectprm392.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.fragments.FragmentLanguage;
import vn.edu.fpt.projectprm392.models.Item;

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
            holder.tv_itemProfileName.setText(mContext.getString(item.getName()));


            if (holder.tv_itemProfileName.getText() == mContext.getString(R.string.account_information)){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }

            if (holder.tv_itemProfileName.getText() == mContext.getString(R.string.language)){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        // Create a new instance of the FragmentLanguage
                        FragmentLanguage fragment = new FragmentLanguage();

                        // Get the FragmentManager and start a new FragmentTransaction
                        FragmentManager fragmentManager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        //--
                        FrameLayout fragmentContainer = ((AppCompatActivity)mContext).findViewById(R.id.fragment_lang);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);

                        fragmentContainer.setLayoutParams(layoutParams);


                        // Replace the contents of the container with the new fragment
                        fragmentTransaction.replace(R.id.fragment_lang, fragment);
                        fragmentTransaction.addToBackStack(null);


                        // Commit the transaction
                        fragmentTransaction.commit();
                    }
                });
            }
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
