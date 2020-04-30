package com.codingforfun.listmaker

import android.content.Context
import androidx.preference.PreferenceManager

class TaskList(val name: String, val tasks: ArrayList<String> = ArrayList()) {
}

class ListDataManager(private val context: Context) {
    fun saveList(list: TaskList) {
        /*
            Get a reference to the app’s default SharedPreference store via
            PreferenceManager.getDefaultSharedPreferences(context). With the
            PreferenceManager object it returns, append .edit() to it to get a
            SharedPreferences.Editor instance. This allows you to write key-value pairs to
            SharedPreferences.
         */
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context).edit()

        /*
            Write TaskList to SharedPreferences as a set of Strings, passing in the name of the
            list as the key. Since the tasks in TaskList is an array of strings, you can’t store it
            directly in a string, so you convert the tasks in TaskList to a HashSet which
            SharedPreferences can use as a value to save. Since HashSet is a Set, it ensures
            unique values in the list.
         */
        sharedPreferences.putStringSet(list.name, list.tasks.toHashSet())

        /*
            Instruct the SharedPreferences Editor instance to apply the changes. This writes the
            changes to ListMaker’s SharedPreferences file.
         */
        sharedPreferences.apply()
    }

    fun readLists():ArrayList<TaskList> {
        //1
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        //2
        val sharedPreferenceContents = sharedPreferences.all

        //3
        val taskLists = ArrayList<TaskList>()

        //
        for(taskList in sharedPreferenceContents) {
            val itemsHashSet = ArrayList(taskList.value as HashSet<String>)
            val list = TaskList(taskList.key, itemsHashSet)
            //5
            taskLists.add(list)
        }

        return taskLists
    }
}