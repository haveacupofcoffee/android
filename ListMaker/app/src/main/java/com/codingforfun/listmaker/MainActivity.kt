package com.codingforfun.listmaker

import android.os.Bundle
import android.renderscript.ScriptGroup
import android.text.InputType
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.AbsListView
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val listDataManager: ListDataManager = ListDataManager(this)
    private lateinit var listRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            showCreateListDialog()
        }

        //hook up listRecyclerView
        val lists = listDataManager.readLists()
        listRecyclerView = findViewById(R.id.lists_recyclerview)
        listRecyclerView.layoutManager = LinearLayoutManager(this)

        listRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showCreateListDialog() {
        //Retrieve the strings defined in strings.xml for use in the Dialog
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)

        /**
            Create an AlertDialog.Builder to help construct the Dialog. An EditText View is
            created as well to serve as the input field for the user to enter the name of the list.
            The inputType of the EditText is set to TYPE_CLASS_TEXT. Specifying the input type
            gives Android a hint as to what the most appropriate keyboard to show is. In this
            case, a text-based keyboard, since you want the list to have a name.
            The title of the Dialog is set by calling setTitle. You also pass in the content View
            of the Dialog. In this case the EditText View, by calling setView.
         */
        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)

        /*
            Inform the Dialog Builder that you want to add a positive button to the Dialog;
            this tells the Dialog a positive action has occurred and something should happen.
            You can also use negative buttons for doing things that you consider negative in
            your app, such as canceling an action.
            In this case, a positive button makes sense because the user is creating something.
            You pass in positiveButtonTitle as the label for the button and implement an
            onClickListener. For now, you dismiss the Dialog.
        */
        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            val list = TaskList(listTitleEditText.text.toString())
            listDataManager.saveList(list)

            val recyclerAdapter = listRecyclerView.adapter as ListSelectionRecyclerViewAdapter
            recyclerAdapter.addList(list)

            dialog.dismiss()
        }

        //4.you instruct the Dialog Builder to create the Dialog and display it on the
        //screen.
        builder.create().show()


    }
}
