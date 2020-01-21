package net.phpsm.simsim.simsiminstantorder.activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.adapter.RecyclerAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.Answer;
import net.phpsm.simsim.simsiminstantorder.models.Question;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.QAItems;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.QAList;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static net.phpsm.simsim.simsiminstantorder.activitys.Signup1.MY_PREFS_NAME;

public class HelpCenter extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Question> questionArrayList;
    private ArrayList<QAItems> questionArrayListResponse;
    private ArrayList<Answer> questionArrayListResponse2;
    private RecyclerAdapter adapter;
    AppSharedPreferences appSharedPreferences;
    String token;
    String refreshToken;
    public static final String MY_PREFS_NAME1 = "MySharedP1";
    AVLoadingIndicatorView pb;
    ApiService apiInterface;
    EditText ask;
    SwipeRefreshLayout swiperefresh;
    View internet;
    View empityData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);

        Toolbar toolbar = findViewById(R.id.toolbar);
        swiperefresh=findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        internet=findViewById(R.id.no_internet);
        empityData=findViewById(R.id.no_data);
        setSupportActionBar(toolbar);
        /////////
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
        /////////

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        appSharedPreferences = new AppSharedPreferences(HelpCenter.this);
        token = appSharedPreferences.readString("access_token");
        refreshToken = appSharedPreferences.readString("refresh_token");

        pb = findViewById(R.id.progress);
        ask = findViewById(R.id.ask);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        questionArrayListResponse = new ArrayList<>();
        questionArrayList = new ArrayList<>();
        questionArrayListResponse2 = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        questionArrayList = new ArrayList<>();

        adapter = new RecyclerAdapter(HelpCenter.this, questionArrayList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        GET_ALL_QUESTIONS_AND_ANSWERS();
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                questionArrayList.clear();
                adapter.deleteAllDate();
                GET_ALL_QUESTIONS_AND_ANSWERS();


            }
        });
        ask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString() + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void filterList(String s) {
        ArrayList<Question> questionsFilter = new ArrayList<>();
        ArrayList<Question> ArrivedListData = new ArrayList<>();
        questionsFilter.clear();
        ArrivedListData = questionArrayList;
        for (int i = 0; i < ArrivedListData.size(); i++) {
            String question = ArrivedListData.get(i).getTitle() + "";
//            question.replace(" ", "");
//            if (question == null || question == "") {
//
//            }

            if (question.toLowerCase().contains(s.toLowerCase())) {
                questionsFilter.add(ArrivedListData.get(i));

                for (int j = 0; j < questionsFilter.size(); j++) {
                    Log.e("mFilterList", questionsFilter.get(j).getTitle().toString());
                }
                Log.e("--", "ـــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ");
                Log.e("mFilterList", questionsFilter.size() + " QFilter SIZE");
                Log.e("mFilterList", question);
            }
        }
        adapter.setFilter(questionsFilter);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }

    private void setData() {
        for (int i = 0; i < questionArrayListResponse.size(); i++) {
            questionArrayListResponse2 = new ArrayList<>();
            questionArrayListResponse2.add(new Answer(questionArrayListResponse.get(i).getAnswer_text()));
            questionArrayList.add(new Question(questionArrayListResponse.get(i).getQuestion_text(), questionArrayListResponse2));
        }

        Log.e("RESPONSE_ListData", questionArrayListResponse2.size() + "");
        Log.e("RESPONSE_ListData", questionArrayList.size() + "");


//        questionArrayList.add(new Question("About simsim app ?", questionArrayListResponse2));

        /*ArrayList<Answer> answer1 = new ArrayList<>();
        answer1.add(new Answer("simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app "));

        ArrayList<Answer> answer2 = new ArrayList<>();
        answer2.add(new Answer("order by simsim order by simsim order by simsim order by simsim order by simsim "));

        ArrayList<Answer> answer3 = new ArrayList<>();
        answer3.add(new Answer("purchase purchase purchase purchase purchase purchase purchase purchase purchase purchase purchase purchase "));

        ArrayList<Answer> answer4 = new ArrayList<>();
        answer4.add(new Answer("simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app "));

        ArrayList<Answer> answer5 = new ArrayList<>();
        answer5.add(new Answer("order by simsim order by simsim order by simsim order by simsim order by simsim "));

        ArrayList<Answer> answer6 = new ArrayList<>();
        answer6.add(new Answer("purchase purchase purchase purchase purchase purchase purchase purchase purchase purchase purchase purchase "));

        ArrayList<Answer> answer7 = new ArrayList<>();
        answer7.add(new Answer("simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app simsim app "));

        ArrayList<Answer> answer8 = new ArrayList<>();
        answer8.add(new Answer("order by simsim order by simsim order by simsim order by simsim order by simsim "));

        ArrayList<Answer> answer9 = new ArrayList<>();
        answer9.add(new Answer("purchase purchase purchase purchase purchase purchase purchase purchase purchase purchase purchase purchase "));


        questionArrayList.add(new Question("About simsim app ?", answer1));
        questionArrayList.add(new Question("How to make your order by simsim ?", answer2));
        questionArrayList.add(new Question("About purchase ?", answer3));
        questionArrayList.add(new Question("About simsim app ?", answer4));
        questionArrayList.add(new Question("How to make your order by simsim ?", answer5));
        questionArrayList.add(new Question("About purchase ?", answer6));
        questionArrayList.add(new Question("About simsim app ?", answer7));
        questionArrayList.add(new Question("How to make your order by simsim ?", answer8));
        questionArrayList.add(new Question("About purchase ?", answer9));*/
    }

    public void GET_ALL_QUESTIONS_AND_ANSWERS() {

        if (!isNetworkConnected()) {
            swiperefresh.setRefreshing(false);
            recyclerView.setVisibility(View.GONE);
            internet.setVisibility(View.VISIBLE);
            empityData.setVisibility(View.GONE);
            pb.setVisibility(View.GONE);

        } else {
        SharedPreferences sharedPreferencesGet = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String token = sharedPreferencesGet.getString("access_token", "");
        apiInterface = ApiClient.getClient().create(ApiService.class);
        Call<QAList> call3 = apiInterface.getQuestionsAndAnswers(appSharedPreferences.readString("lang"),"application/json", "Bearer "+token);
        call3.enqueue(new Callback<QAList>() {
            @Override
            public void onResponse(Call<QAList> call, Response<QAList> response) {
                try {
                    if (response.isSuccessful()) {
                        swiperefresh.setRefreshing(false);
                        empityData.setVisibility(View.GONE);
                        swiperefresh.setRefreshing(false);
                        recyclerView.setVisibility(View.VISIBLE);
                        internet.setVisibility(View.GONE);
                        pb.setVisibility(View.GONE);
                        if (response.body().isStatus() == true) {
//                            Toast.makeText(HelpCenter.this, "true", Toast.LENGTH_SHORT).show();
                            recyclerView.setVisibility(View.VISIBLE);
//                                Toast.makeText(getActivity(), "true", Toast.LENGTH_SHORT).show();
                            questionArrayListResponse = response.body().getItems();
                            if(questionArrayListResponse.size()==0){
                                empityData.setVisibility(View.VISIBLE);
                                swiperefresh.setRefreshing(false);
                                recyclerView.setVisibility(View.GONE);
                                internet.setVisibility(View.GONE);
                                pb.setVisibility(View.GONE);
                            }
                            setData();
                            adapter.addAll(questionArrayList);
                            Log.e("RESPONSE", "true");
                        } else if (response.body().isStatus() == false) {
                            pb.setVisibility(View.GONE);
                            Log.e("RESPONSE", "false");
                        }

                    } else {
                        swiperefresh.setRefreshing(false);

                        APIError apiError = ErrorUtils.parseError(response);
                        pb.setVisibility(View.GONE);
                        Log.e("RESPONSE", "error");

                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<QAList> call, Throwable t) {
                call.cancel();
                Log.e("RESPONSE", "onFailure");
                if (HelpCenter.this != null){
                    swiperefresh.setRefreshing(false);
                    recyclerView.setVisibility(View.GONE);
                    internet.setVisibility(View.VISIBLE);
                    empityData.setVisibility(View.GONE);
                    pb.setVisibility(View.GONE);
//                    Toast.makeText(HelpCenter.this, , Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.tryagain));
                }
            }
        });}
    }

    private void CustomToast(String texttoast) {

        Toast toast = Toast.makeText(HelpCenter.this, texttoast, Toast.LENGTH_LONG);
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
