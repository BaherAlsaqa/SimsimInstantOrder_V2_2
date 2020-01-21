package net.phpsm.simsim.simsiminstantorder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.adapter.MyPurchaseDetailsAdapter;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.MyPurchaseData;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.PurchaseorderItems;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPurchaseDetails extends Fragment {

    int x=0;
    RecyclerView purchaseRecycler;
    AVLoadingIndicatorView progress;
    CircleImageView productImage;
    MyPurchaseDetailsAdapter myPurchaseDetailsAdapter;
    public static MyPurchaseDetails newInstance() {
        MyPurchaseDetails fragment = new MyPurchaseDetails();
        return fragment;
    }

    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_purchase_details,container,false);
Bundle data=getArguments();
        MyPurchaseData purchaseData= data.getParcelable("purcahsesItems");
        List<PurchaseorderItems> adapterItems= (List<PurchaseorderItems>) purchaseData.getOrderItems();
        Log.d("sss",adapterItems.size()+"");
        purchaseRecycler=view.findViewById(R.id.purchaseRecycler);
        progress=view.findViewById(R.id.progress);
        productImage=view.findViewById(R.id.product_image);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        purchaseRecycler.setLayoutManager(layoutManager);
        myPurchaseDetailsAdapter = new MyPurchaseDetailsAdapter(adapterItems,getContext());
        purchaseRecycler.setAdapter(myPurchaseDetailsAdapter);



        return view;
    }


}
