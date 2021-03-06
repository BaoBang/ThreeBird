package com.example.baobang.threebird.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.baobang.threebird.R;
import com.example.baobang.threebird.annimator.RecyclerViewAnimator;
import com.example.baobang.threebird.annimator.VegaLayoutManager;
import com.example.baobang.threebird.listener.OnItemRecyclerViewClickListener;
import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.utils.Constants;
import com.nightonke.boommenu.BoomMenuButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> implements Filterable {
    private List<Order> orders;
    private List<Order> tempOders;
    private RecyclerViewAnimator mAnimator;
    private RecyclerView recyclerView;
    private Activity context;

    private OnItemRecyclerViewClickListener onItemRecyclerViewClickListener;

    public OrderAdapter(Activity context, List<Order> orders, RecyclerView recyclerView, OnItemRecyclerViewClickListener onItemRecyclerViewClickListener) {
        this.orders = orders;
        this.tempOders = new ArrayList<>(this.orders);
        this.recyclerView = recyclerView;
        mAnimator = new RecyclerViewAnimator(recyclerView);
        this.context = context;
        this.onItemRecyclerViewClickListener = onItemRecyclerViewClickListener;
    }

    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_order, parent, false);
        OrderHolder orderHolder = new OrderHolder(view);
        mAnimator.onCreateViewHolder(view);
        return orderHolder;
    }

    @Override
    public void onBindViewHolder(final OrderHolder holder, int position) {
        holder.setPositionSelected(position);
        Order order = this.orders.get(position);
        holder.txtId.setText(String.valueOf(order.getId()));
        holder.txtClientName.setText(order.getClientName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi", "VN"));
        holder.txtCreatedAt.setText(simpleDateFormat.format(order.getCreatedAt()));
        holder.txtPayment.setText(String.valueOf(order.getToal()));
        if(order.getStatus() == Constants.COMPLETED){
            holder.txtStatus.setText(R.string.complete);
            holder.txtStatus.setTextColor(this.context.getResources().getColor(R.color.green));
        }else if(order.getStatus() == Constants.CANCEL){
            holder.txtStatus.setText(R.string.destroy);
            holder.txtStatus.setTextColor(this.context.getResources().getColor(R.color.red));
        }else{
            holder.txtStatus.setText(R.string.delivery);
            holder.txtStatus.setTextColor(this.context.getResources().getColor(R.color.blue));
        }
        holder.boomMenuButton.clearBuilders();

        for (int i = 0; i < holder.boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++){
            holder.boomMenuButton.addBuilder(
                    BuilderManager
                            .getSimpleCircleButtonBuilder()
                            .listener(
                                    index ->
                                    onItemRecyclerViewClickListener.onItemClick(index, orders.get(holder.getPositionSelected()))
                            )
            );
        }
//        holder.itemView.setOnClickListener(
//             view ->
//                onItemRecyclerViewClickListener
//                .onItemClick(
//                orders.get(holder.getPositionSelected())));

        mAnimator.onBindViewHolder(holder.layoutItem, position);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtOrderId)
        TextView txtId;

        @BindView(R.id.txtClientName)
        TextView txtClientName;

        @BindView(R.id.txtCreatedAt)
        TextView txtCreatedAt;

        @BindView(R.id.txtPayment)
        TextView txtPayment;

        @BindView(R.id.txtStatus)
        TextView txtStatus;

        @BindView(R.id.layoutItem)
        LinearLayout layoutItem;

        @BindView(R.id.bmb1)
        BoomMenuButton boomMenuButton;

        int positionSelected = -1;

        public int getPositionSelected() {
            return positionSelected;
        }

        public void setPositionSelected(int positionSelected) {
            this.positionSelected = positionSelected;
        }

        private OrderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new OrderFilter(this);
    }

    private class OrderFilter extends Filter{
        private OrderAdapter orderAdapter;

        private OrderFilter(OrderAdapter productAdapter) {
            this.orderAdapter = productAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String filterSeq = charSequence.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (filterSeq.length() > 0) {
                ArrayList<Order> filter = new ArrayList<>();
                for (Order order : tempOders) {
                    if (order.toString().toLowerCase().contains(filterSeq))
                        filter.add(order);
                }
                result.count = filter.size();
                result.values = filter;
                orders = filter;
            } else {
                // add all objects
                result.values = tempOders;
                result.count = tempOders.size();
                orders = tempOders;
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            orderAdapter = new OrderAdapter(context,orders, recyclerView, onItemRecyclerViewClickListener);
            VegaLayoutManager vegaLayoutManager = (VegaLayoutManager) recyclerView.getLayoutManager();
            vegaLayoutManager.setDeafaut();
            notifyDataSetChanged();
        }
    }
}
