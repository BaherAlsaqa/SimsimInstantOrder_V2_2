package net.phpsm.simsim.simsiminstantorder.viewholders;

import android.view.View;
import android.widget.TextView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.models.Answer;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;


public class AnswerViewHolder extends ChildViewHolder {

    private TextView answerT;

    public AnswerViewHolder(View itemView) {
        super(itemView);

        answerT = (TextView) itemView.findViewById(R.id.answer);
    }

    public void onBind(Answer answer, ExpandableGroup group) {
        answerT.setText(answer.getAnswer());
    }
}
