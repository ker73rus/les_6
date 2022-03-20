package com.example.les_6

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val REQUEST_CODE = 7

class Fragment1 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(ActivityCompat.checkSelfPermission(view.context, Manifest.permission.READ_CONTACTS)
            == PackageManager.PERMISSION_GRANTED)
        {
            setContacts()
        }
        view.findViewById<View>(R.id.confirm_button).setOnClickListener() {
            view.findNavController().navigate(R.id.fragment_main)
        }
        view.findViewById<View>(R.id.confirm_button).setOnClickListener() {
            view.findNavController().navigate(R.id.fragment_main)
        }

        view.findViewById<View>(R.id.request_button).setOnClickListener() {
            if (ActivityCompat.checkSelfPermission(view.context, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED
            ) {
                setContacts()
            } else {
                requestContactPermission()
            }
        }

    }

    private fun requestContactPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Доступ к контактам:")
                .setMessage("Ну очень нужно!")
                .setCancelable(true)
                .setPositiveButton("ладно") { _, _ ->
                    requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE)
                }
                .setNegativeButton("Не дам") { _, _ ->
                    Toast.makeText(activity, "Ну нет, так нет \n ¯\\_(ツ)_/¯", Toast.LENGTH_LONG).show()
                }
            builder.show()
        } else {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE)
        }


    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setContacts()
            }
        }
    }




    private fun setContacts() {
        val contacts = mutableListOf<String>()
        val cursor = context?.contentResolver?.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        cursor?.let { cursor ->
            while (cursor.moveToNext()) {
                val index = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
                val contact: String
                if (index >= 0) {
                    contact = cursor.getString(index)
                    contacts.add(contact)
                }
            }
            cursor.close()
        }

        val contactsList: RecyclerView? = view?.findViewById(R.id.recyler)
        if (contactsList != null) {
            val adapter = ContactsAdapter(contacts)
            contactsList.adapter = adapter

            val layoutManager = LinearLayoutManager(context)
            contactsList.layoutManager = layoutManager
        }
    }

}