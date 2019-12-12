package com.example.cnc_driver.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.example.cnc_driver.R;
import com.example.cnc_driver.common.Constacts;
import com.example.cnc_driver.common.eventBus.EventBusAction;
import com.example.cnc_driver.common.eventBus.ProductUpdateEvent;
import com.example.cnc_driver.net.FirebaseManager;
import com.example.cnc_driver.net.response.ProductResponse;
import com.example.cnc_driver.view.activity.AddProductActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Viewholder> {

    private Context context;
    private List<ProductResponse> item;
    private FirebaseManager firebaseManager = new FirebaseManager();

    public ProductAdapter(Context context, List<ProductResponse> item) {
        this.context = context;
        this.item = item;
    }

    public void update(List<ProductResponse> item) {
        this.item = item;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Picasso.get().load(item.get(position).getImage()).into(holder.img);
        holder.tvName.setText(item.get(position).getName());
        holder.tvPrice1.setText(item.get(position).getPrice());

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
                if(layout.getOpenStatus() == SwipeLayout.Status.Open) {
                    layout.close();
                }
            }
        });

        holder.Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new ProductUpdateEvent(EventBusAction.PRODUCT_UPDATE,item.get(position)));
                Intent i = new Intent(context, AddProductActivity.class);
                i.putExtra(Constacts.KEY_UPDATE, "1");
                context.startActivity(i);
            }
        });

        holder.Delete.setOnClickListener(view -> {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
            builder2.setMessage(R.string.key_you_sure_want_to_delete_this_contact)
                    .setCancelable(false)
                    .setNegativeButton(R.string.key_NO, (dialog, which) -> {
                        dialog.dismiss();
                        holder.swipeLayout.close();
                    })
                    .setPositiveButton(R.string.key_YES, (dialog, id) -> {
                        holder.swipeLayout.close();
                        firebaseManager.deleteProduct(item.get(position).getId());
                    });
            AlertDialog alert2 = builder2.create();
            alert2.show();
        });
    }

    @Override
    public int getItemCount() {
        if (item == null) {
            return 0;
        }
        return item.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        private SwipeLayout swipeLayout;
        private LinearLayout bottomWraper;
        private TextView Update;
        private TextView Delete;
        private CardView llItem;
        private ImageView img;
        private TextView tvName;
        private TextView tvPrice1;
        private TextView tvPrice2;


        Viewholder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.swipe);
            bottomWraper = itemView.findViewById(R.id.bottom_wraper);
            Update = itemView.findViewById(R.id.Update);
            Delete = itemView.findViewById(R.id.Delete);
            llItem = itemView.findViewById(R.id.ll_item);
            img = itemView.findViewById(R.id.img);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice1 = itemView.findViewById(R.id.tv_price1);
            tvPrice2 = itemView.findViewById(R.id.tv_price2);
        }
    }
}
