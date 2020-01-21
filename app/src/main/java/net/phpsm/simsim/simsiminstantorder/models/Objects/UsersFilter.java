package net.phpsm.simsim.simsiminstantorder.models.Objects;

import android.widget.Filter;

import net.phpsm.simsim.simsiminstantorder.adapter.MyAddFriendsAdapter;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.UsersDatum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 1/22/2018.
 */

public class UsersFilter extends Filter {

    private List<UsersDatum> userList;
    private List<UsersDatum> filteredUserList;
    private MyAddFriendsAdapter adapter;

    public UsersFilter(List<UsersDatum> userList, MyAddFriendsAdapter adapter) {
        this.adapter = adapter;
        this.userList = userList;
        this.filteredUserList = new ArrayList();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredUserList.clear();
        final FilterResults results = new FilterResults();

        //here you need to add proper items do filteredContactList
        for (final UsersDatum item : userList) {
            if(item.getName()!=null){
            if (item.getName().toLowerCase().trim().contains(constraint.toString())) {
                filteredUserList.add(item);
            }}

        }

        results.values = filteredUserList;
        results.count = filteredUserList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.setList(filteredUserList);
        adapter.notifyDataSetChanged();
    }
}
