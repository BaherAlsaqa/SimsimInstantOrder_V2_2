package net.phpsm.simsim.simsiminstantorder.models;

import android.annotation.SuppressLint;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by baher on 20/12/2017.
 */
@SuppressLint("ParcelCreator")
public class Question extends ExpandableGroup<Answer> {

    public Question(String title, List<Answer> items) {
        super(title, items);
    }
}
