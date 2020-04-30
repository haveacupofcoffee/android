package com.codingforfun.listmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ListSelectionRecyclerViewAdapter(private val lists: ArrayList<TaskList>):
    RecyclerView.Adapter<ListSelectionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {
        /*
            First, it uses a LayoutInflater object to create a layout programmatically. It uses
            the parent context of the Adapter to create itself and attempts to inflate the Layout
            you want by passing in the layout name and the parent ViewGroup so the View has
            a parent it can refer to. The Boolean value is used to specify whether the View
            should be attached to the parent. Always use false for RecyclerView layouts as the
            RecyclerView attaches and detaches the Views for you.
            Note: LayoutInflater is a system utility used to instantiates a layout XML file into
            its corresponding View objects.
        */
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_selection_view_holder,
                                    parent, false)

        //The ViewHolder is created from the Layout and returned from the method
        return ListSelectionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    /*
        For each call of onBindViewHolder(), you take the TextViews you created in the
        ViewHolder and populate them with their position in the list and the name of the list
        from the listTitles array.
        This gets called repeatedly as you scroll through the RecyclerView.
    */
    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {
        holder.listPosition.text = (position + 1).toString()
        holder.listTitle.text = lists.get(position).name

    }

    fun addList(list: TaskList) {
        //1
        lists.add(list)

        //2
        notifyItemInserted(lists.size-1)

    }

}