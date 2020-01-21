package net.phpsm.simsim.simsiminstantorder.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.adapter.MyProvCategoryAdapter;
import net.phpsm.simsim.simsiminstantorder.adapter.PaginationChatAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener19;
import net.phpsm.simsim.simsiminstantorder.models.responses.Chat;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.ChatItemsObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.ChatObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.PaginationScrollListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityChat extends AppCompatActivity {

    private View view;
    ///////////////////////////////////////////////////////
    //MyHomeAdapter adapter;
    AVLoadingIndicatorView progress;
    ArrayList<Chat> chatList = new ArrayList<>();
    //ArrayList<Delivery_Status> deliveryStatusArrayList = new ArrayList<>();
    RecyclerView mRecyclerView1;
    PaginationChatAdapter adapter1;
    AppSharedPreferences appSharedPreferences;
    String token;
    MyProvCategoryAdapter categoryAdapter;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 2;
    private int currentPage = PAGE_START;

    ApiService apiService;
    Call<ChatObject> call;
    int p_category_id = 1;
    LinearLayoutManager linearLayoutManager;
    Bundle bundle;
    String cuisine_status;
    ArrayList<Chat> ChatData ;
    private static final int REQUEST_LOCATION = 1;
    Intent intent;
    int p_m_id = 0;
    Button sendMessage;
    EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        appSharedPreferences = new AppSharedPreferences(ActivityChat.this);
        progress = findViewById(R.id.progress);
        mRecyclerView1 = findViewById(R.id.recyclerView);
        sendMessage = findViewById(R.id.btn_send_message);
        editTextMessage = findViewById(R.id.edittext_message);

        Toolbar toolbar = findViewById(R.id.toolbar);

        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        setSupportActionBar(toolbar);
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

        /////////////////////////////

        isLoading = false;
        isLastPage = false;
        currentPage = PAGE_START;

        ////////////////////////////

        ChatData = new ArrayList<>();

        adapter1 = new PaginationChatAdapter(ActivityChat.this);

        linearLayoutManager = new LinearLayoutManager(ActivityChat.this, LinearLayoutManager.VERTICAL, false);

        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        mRecyclerView1.setLayoutManager(linearLayoutManager);

        mRecyclerView1.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView1.setAdapter(adapter1);

        token = appSharedPreferences.readString("access_token");
        ////////////////////////////
        apiService = ApiClient.getClient().create(ApiService.class);
        //TODO /////////////////Intent : p_m_id////////////////////
        intent = getIntent();
        if (intent != null){
            p_m_id = intent.getIntExtra("p_m_id",0);
        }

        mRecyclerView1.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage(p_m_id);
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        //getHomeProviders();

        adapter1.setOnClickListener(new OnItemClickListener19() {
            @Override
            public void onItemClick(Chat item, View view) {
                //TODO ///////////////////OnItemClick////////////////////
            }
        });

        loadFirstPage(p_m_id);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString();
                sendMesage(p_m_id, message);
            }
        });

    }

    private void loadFirstPage(int p_m_id) {
        Log.e("loadFirstPage", "loadFirstPage: " + currentPage);
        progress.setVisibility(View.VISIBLE);
        callTopRatedMoviesApi(p_m_id).enqueue(new Callback<ChatObject>() {
            @Override
            public void onResponse(Call<ChatObject> call, Response<ChatObject> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("true")) {
                        chatList.clear();
                        adapter1.clear();
                        chatList = response.body().getChatItemsObject().getData();
                    /*for (int i=0; i<chatList.size(); i++) {
                        deliveryStatusArrayList = chatList.get(i).getDelivery_Status();
                    }*/
                        TOTAL_PAGES = response.body().getChatItemsObject().getLastPage();
                        //Toast.makeText(getContext(), "total page : "+TOTAL_PAGES, Toast.LENGTH_SHORT).show();
                        chatList = fetchResults(response);
                        adapter1.addAll(chatList);
                        fillData(chatList);
                        //TODO ///////////////////////ScrollToPosition////////////////////////////
                        mRecyclerView1.scrollToPosition(0);
                        if (currentPage < TOTAL_PAGES) adapter1.addLoadingFooter();
                        else isLastPage = true;

                        progress.setVisibility(View.GONE);
                    } else {
                        progress.setVisibility(View.GONE);
                        Log.d("firsterror", "error");
//                        Toast.makeText(ActivityChat.this, , Toast.LENGTH_SHORT).show();
                        CustomToast(getString(R.string.tryagain));
                    }
                }else{
//                    Toast.makeText(ActivityChat.this, , Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.tryagain));
                }
            }

            @Override
            public void onFailure(Call<ChatObject> call, Throwable t) {
                t.printStackTrace();
                Log.d("firsterror", "error1");
                try {
                    if (ActivityChat.this != null) {
//                        Toast.makeText(ActivityChat.this, , Toast.LENGTH_SHORT).show();
                        CustomToast(getString(R.string.tryagain));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progress.setVisibility(View.GONE);
            }
        });
    }

    private ArrayList<Chat> fetchResults(Response<ChatObject> response) {
        ChatObject chatObject = response.body();
        ChatItemsObject chatItemsObject = chatObject.getChatItemsObject();
        return  chatItemsObject.getData();
    }

    private void loadNextPage(int p_m_id) {

        //Toast.makeText(getContext(), "load next page", Toast.LENGTH_SHORT).show();
        Log.d("loadNextPage", "loadNextPage: " + currentPage);

        callTopRatedMoviesApi(p_m_id).enqueue(new Callback<ChatObject>() {
            @Override
            public void onResponse(Call<ChatObject> call, Response<ChatObject> response) {

                if (response.body().getStatus().equals("true")) {
                    adapter1.removeLoadingFooter();
                    isLoading = false;

                    chatList = fetchResults(response);
                    adapter1.addAll(chatList);
                    fillData(chatList);



                    if (currentPage != TOTAL_PAGES) adapter1.addLoadingFooter();
                    else isLastPage = true;
                }else{
                    Log.d("lasterror", "error");
//                    Toast.makeText(ActivityChat.this, , Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.tryagain));
                }
            }

            @Override
            public void onFailure(Call<ChatObject> call, Throwable t) {
                t.printStackTrace();
                Log.d("lasterror", "error1");
                if (ActivityChat.this != null) {
//                    Toast.makeText(ActivityChat.this, , Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.tryagain));
                }
            }
        });
    }

    private Call<ChatObject> callTopRatedMoviesApi(int p_id) {
        return call = apiService.getMessagesChat(p_id, "application/json","Bearer "+token, currentPage);
    }

    public void fillData(ArrayList<Chat> olist) {
//        arrivedData = new ArrayList<>();

        for (int i = 0; i < olist.size(); i++) {
            ChatData.add(olist.get(i));
        }
        Log.e("fillData", "SIZE olist : " + olist.size());
        Log.e("fillData", "SIZE ChatData : " + ChatData.size());
        sharedData();
    }

    public void SearchResult(ArrayList<Chat> fList) {
        Log.e("fListSize", "" + fList.size());
        adapter1.clear();
        adapter1.addAll(fList);
        adapter1.notifyDataSetChanged();
    }

    public ArrayList<Chat> sharedData() {
        Log.e("fillData", "SIZE ChatData FRAG : " + ChatData.size());
        return ChatData;
    }

    private void sendMesage(int provider_id, String message) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        String access_token = appSharedPreferences.readString("access_token");
        Call<SaveItem> call = apiService.sendMessage("application/json", "Bearer "+access_token, provider_id, message);
        call.enqueue(new Callback<SaveItem>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
                if (response.isSuccessful()) {

                    Log.d("validateCode", "validateCode");

                    if (response.body().getStatus() == true) {
                        Log.d("response.body().getStatus()", "true");
                        chatList.clear();
                        isLoading = false;
                        isLastPage = false;
                        currentPage = PAGE_START;

                        loadFirstPage(p_m_id);
                        editTextMessage.setText("");
                    }else {
                        Log.d("response.body().getStatus()", "false");
                    }
                }
            }

            @Override
            public void onFailure(Call<SaveItem> call, Throwable t) {
                Log.d("validateCode", "validateCodeFailure");
                if (ActivityChat.this != null) {
//                    Toast.makeText(ActivityChat.this,, Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.tryagain));
                }
            }
        });

    }

    private void CustomToast(String texttoast) {

        Toast toast = Toast.makeText(ActivityChat.this, texttoast, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.shaptextviewtoasterror);
        TextView text = view.findViewById(android.R.id.message);
        text.setShadowLayer(0,0,0,0);
        text.setTextColor(Color.WHITE);
        text.setPadding(15, 0, 15, 0);
        text.setGravity(Gravity.CENTER);
        toast.show();

    }

}
