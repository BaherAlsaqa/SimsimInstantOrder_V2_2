package net.phpsm.simsim.simsiminstantorder.activitys;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.adapter.MyMessagesAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.MassegDatum;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.Messages;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static net.phpsm.simsim.simsiminstantorder.activitys.Signup1.MY_PREFS_NAME;


public class ActivityMessages extends AppCompatActivity {

    List<MassegDatum> messageList = new ArrayList<>();
    RecyclerView msgRecyclerView;
    MyMessagesAdapter msgAdapter;
    AppSharedPreferences appSharedPreferences;
    AVLoadingIndicatorView progressBar;
    String token;
    int pageId=1;
    String nextPage;
    SearchView searchView;
    String searchedText;
    SwipeRefreshLayout swiperefresh;
    int totalItemCount;
    View internet;
    View empityData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_messages);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Toolbar toolbar = findViewById(R.id.toolbar);
        searchView=findViewById(R.id.searchView);
        progressBar=findViewById(R.id.progress);
        swiperefresh=findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        msgRecyclerView = findViewById(R.id.recyclerView);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        setSupportActionBar(toolbar);
         internet= findViewById(R.id.no_internet);
        empityData=findViewById(R.id.no_data);

        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                }
        );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("tt",newText);
        searchedText=newText;
       // progressBar.setVisibility(View.VISIBLE);
      /*  while (nextPage!=null){
            pageId++;
            progressBar.setVisibility(View.VISIBLE);
            getMessagesData();
        }*/
       msgAdapter.filterList(newText);
        return true;
    }
});
        msgRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        msgAdapter = new MyMessagesAdapter(messageList, this);
        msgRecyclerView.setAdapter(msgAdapter);
        appSharedPreferences = new AppSharedPreferences(ActivityMessages.this);

        //sharedPreferencesGet = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        token = appSharedPreferences.readString("access_token");
        progressBar.setVisibility(View.VISIBLE);
        getMessagesData();

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                messageList.clear();
                msgAdapter.deleteAllData();
                getMessagesData();


            }
        });
        msgRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                // totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                boolean endHasBeenReached = lastVisible +1>= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached) {
                    if(nextPage!=null){
                        pageId++;
                       // progressBar.setVisibility(View.VISIBLE);
                        getMessagesData();
                    }
                    //you have reached to the bottom of your recycler view
                }
            }
        });
    }

    public  void NextPageOfMeessages() {
        if (nextPage!=null){
            pageId++;
        //    progressBar.setVisibility(View.VISIBLE);
            getMessagesData();
        }

    }
    private void getMessagesData() {
        if(!isNetworkConnected() && pageId==1){
            swiperefresh.setRefreshing(false);
            searchView.setVisibility(View.GONE);
            msgRecyclerView.setVisibility(View.GONE);
            internet.setVisibility(View.VISIBLE);
            empityData.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

        }else{
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Messages> call = apiService.getUserMessages(appSharedPreferences.readString("lang"),"api/v1/provider/messages?page="+pageId,"application/json","Bearer "+token);
        call.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                if (response.isSuccessful()) {
                    swiperefresh.setRefreshing(false);
                    messageList=response.body().getItems().getData();


                    if(messageList.size()==0 && pageId==1){
                       empityData.setVisibility(View.VISIBLE);
                        swiperefresh.setRefreshing(false);
                        msgRecyclerView.setVisibility(View.GONE);
                        internet.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }

                    totalItemCount=messageList.size();
                    nextPage= (String) response.body().getItems().getNextPageUrl();
                    if(nextPage==null){
                        msgAdapter.setIsFinish();
                    }
                    Log.d("txt",messageList.size()+"");

                    if(searchedText!=null){
                        msgAdapter.filterList(searchedText);
                    }
                        msgAdapter.addMessages(messageList);
                    // myPurchaseAdapter.notifyDataSetChanged();
                    // Log.d("xxxx",myPurchaseList.size()+"");
                   progressBar.setVisibility(View.GONE);

                }


                else {
                    swiperefresh.setRefreshing(false);

                    APIError apiError = ErrorUtils.parseError(response);
//                    Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                    CustomToast("Message is : " + apiError.getMessage());
//                    Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                    CustomToast("Error is : " + apiError.getError());
                    progressBar.setVisibility(View.GONE);

                }
            }
            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                swiperefresh.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                if (ActivityMessages.this != null){
//                    Toast.makeText(ActivityMessages.this, , Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.tryagain));
                }
            }
        });
    }
    }

    public void ShowPopUpDialog(String prviderImg,String providerName,String content,String date) {
        final Dialog dialog = new Dialog(ActivityMessages.this);
//        Toast.makeText(getApplicationContext(),,Toast.LENGTH_SHORT).show();
        CustomToast("popup");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.message_dialog_style);

        TextView text = (TextView) dialog.findViewById(R.id.content);
        text.setText(content);
        TextView provider= dialog.findViewById(R.id.name);
        provider.setText(providerName);
        TextView time=dialog.findViewById(R.id.time);
        time.setText(date);
        CircleImageView providerImage= dialog.findViewById(R.id.image);
        Picasso.with(getApplicationContext()).load(prviderImg).into(providerImage);

        /////

        /////
        dialog.show();


    }

    private void CustomToast(String texttoast) {

        Toast toast = Toast.makeText(ActivityMessages.this, texttoast, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.shaptextviewtoasterror);
        TextView text = view.findViewById(android.R.id.message);
        text.setShadowLayer(0,0,0,0);
        text.setTextColor(Color.WHITE);
        text.setPadding(15, 0, 15, 0);
        text.setGravity(Gravity.CENTER);
        toast.show();

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
