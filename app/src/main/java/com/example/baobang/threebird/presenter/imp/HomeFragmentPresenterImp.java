package com.example.baobang.threebird.presenter.imp;

import android.view.View;

import com.example.baobang.threebird.model.Order;
import com.example.baobang.threebird.model.ProductOrder;
import com.example.baobang.threebird.model.Status;
import com.example.baobang.threebird.model.helper.OrderHelper;
import com.example.baobang.threebird.model.helper.StatusHelper;
import com.example.baobang.threebird.presenter.HomeFragmentPresenter;
import com.example.baobang.threebird.utils.Constants;
import com.example.baobang.threebird.utils.LineChartModel;
import com.example.baobang.threebird.utils.SlideModel;
import com.example.baobang.threebird.view.HomeFragmentView;
import com.example.baobang.threebird.view.fragments.ItemFragment;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.RealmList;


public class HomeFragmentPresenterImp implements HomeFragmentPresenter {
    private HomeFragmentView homeFragmentView;

    public HomeFragmentPresenterImp(HomeFragmentView homeFragmentView) {
        this.homeFragmentView = homeFragmentView;
    }

    @Override
    public void init(View view) {
        addSlide(view);
        addLineChart(view);
        addStaticalOrder(view);
    }

    @Override
    public void addLineChart(View view) {

        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -6);
        for(int i = 0; i <  7 ; i++){

            ArrayList<Order> orders = OrderHelper
                    .getOrderByStatusInDay(Constants.COMPLETED, calendar.getTime());
            long total = 0;
            for(Order order : orders)
                total += order.getToal();
            entries.add(new Entry(total, i));
            labels.add(i, calendar.getTime().toString());
            calendar.add(Calendar.DATE, 1);
        }
        homeFragmentView.showLineChart(view, entries, labels);

    }

    @Override
    public void addSlide(View view) {
        List<SlideModel> slideModels = new ArrayList<>();

        List<Status> statuses = StatusHelper.getAllStatus();
        Calendar calendar = Calendar.getInstance();
        for(Status status : statuses){
           if(status.getId() != 0){
               ArrayList<Order> orders = OrderHelper.getOrderByStatusInMonth(status.getId(), calendar.get(Calendar.MONTH));
               SlideModel slideModel = new SlideModel(status.getImage(), status.getDescription(), orders.size());
               slideModels.add(slideModel);
           }
        }
        homeFragmentView.showSlide(view, slideModels);
    }

    @Override
    public void addStaticalOrder(View view) {

        ArrayList<ItemFragment> itemFragments = new ArrayList<>();

        ArrayList<Status> statuses = StatusHelper.getAllStatus();
        Calendar calendar = Calendar.getInstance();
        for(Status status : statuses){
                ArrayList<Order> orders = OrderHelper.getOrderByStatusInMonth(status.getId(),calendar.get(Calendar.MONTH));
                LineChartModel itemFragmentModel =
                        new LineChartModel(status.getId(),
                                   status.getDescription(),orders.size(),
                                getAllPriceProductInventory(orders),
                                getAllPriceProduct(orders));
                ItemFragment itemFragment = ItemFragment.newInstance(itemFragmentModel);
                itemFragments.add(itemFragment);
        }
        homeFragmentView.showItemFragment(view, itemFragments);
    }


    private long getAllPriceProductInventory(ArrayList<Order> orders){
        long total = 0;

        for(Order order : orders){
           RealmList<ProductOrder> productOrders =  order.getProducts();
           for(ProductOrder productOrder : productOrders){
               total += productOrder.getToTalInventoryPrice();
           }
        }

        return total;
    }

    private long getAllPriceProduct(ArrayList<Order> orders){
        long total = 0;

        for(Order order : orders){
            RealmList<ProductOrder> productOrders =  order.getProducts();
            for(ProductOrder productOrder : productOrders){
                total += productOrder.getTotalPrice();
            }
        }
        return total;
    }
}
