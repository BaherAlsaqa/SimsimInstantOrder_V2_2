package net.phpsm.simsim.simsiminstantorder.models.Objects;

import android.widget.Filter;

import net.phpsm.simsim.simsiminstantorder.adapter.MyMessagesAdapter;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.MassegDatum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 1/22/2018.
 */

public class MessagesFilter extends Filter {

    private List<MassegDatum> messagesList;
    private List<MassegDatum> filteredMsgList;
    private MyMessagesAdapter adapter;

    public MessagesFilter(List<MassegDatum> messagesList, MyMessagesAdapter adapter) {
        this.adapter = adapter;
        this.messagesList = messagesList;
        this.filteredMsgList = new ArrayList();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredMsgList.clear();
        final FilterResults results = new FilterResults();

        //here you need to add proper items do filteredContactList
        for (final MassegDatum item : messagesList) {
            if (item.getProvider().getName().toLowerCase().trim().contains(constraint.toString())) {
                filteredMsgList.add(item);
            }

        }

        results.values = filteredMsgList;
        results.count = filteredMsgList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.setList(filteredMsgList);
        adapter.notifyDataSetChanged();
    }
}
