package net.phpsm.simsim.simsiminstantorder.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.models.Answer;
import net.phpsm.simsim.simsiminstantorder.models.Question;
import net.phpsm.simsim.simsiminstantorder.viewholders.AnswerViewHolder;
import net.phpsm.simsim.simsiminstantorder.viewholders.QuestionViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;

public class RecyclerAdapter extends ExpandableRecyclerViewAdapter<QuestionViewHolder, AnswerViewHolder> {

    private Context activity;
    ArrayList<Question> group = new ArrayList<>();
    public RecyclerAdapter(Context activity, ArrayList<Question> groups) {
        super(groups);
        this.activity = activity;
        this.group=groups;
    }

    @Override
    public QuestionViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.group_view_holder, parent, false);

        return new QuestionViewHolder(view);
    }

    @Override
    public AnswerViewHolder onCreateChildViewHolder(ViewGroup parent, final int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.child_view_holder, parent, false);

        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(AnswerViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Answer answer = ((Question) group).getItems().get(childIndex);
        holder.onBind(answer,group);
    }

    @Override
    public void onBindGroupViewHolder(QuestionViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGroupName(group);
    }

    public void setFilter(ArrayList<Question> groups) {
        group = new ArrayList<>();
//        clear();
        group.addAll(groups);
        notifyDataSetChanged();
        Log.e("dataFilterd",groups.size()+" SIZE: groups");
        Log.e("dataFilterd",group.size()+" SIZE: group");
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public Question getItem(int position) {
        return group.get(position);
    }

    public void remove(Question r) {
        int position = group.indexOf(r);
        if (position > -1) {
            group.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void add(Question r) {
        group.add(r);
        notifyItemInserted(group.size() - 1);
    }

    public void addAll(ArrayList<Question> dataFilterd) {
        for (Question result : dataFilterd) {
            add(result);
        }
    }


    public void deleteAllDate() {
        group.clear();
        notifyDataSetChanged();
    }
}
