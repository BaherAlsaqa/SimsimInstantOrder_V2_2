package net.phpsm.simsim.simsiminstantorder.viewholders;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import net.phpsm.simsim.simsiminstantorder.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;


public class QuestionViewHolder extends GroupViewHolder {

    private TextView questionT;

    public QuestionViewHolder(View itemView) {
        super(itemView);

        questionT = (TextView) itemView.findViewById(R.id.question);
    }

    @Override
    public void expand() {
        questionT.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_gray, 0);
        Log.i("Adapter", "expand");
    }

    @Override
    public void collapse() {
        Log.i("Adapter", "collapse");
        questionT.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_gray03, 0);
    }

    public void setGroupName(ExpandableGroup group) {
        questionT.setText(group.getTitle());
    }
}
