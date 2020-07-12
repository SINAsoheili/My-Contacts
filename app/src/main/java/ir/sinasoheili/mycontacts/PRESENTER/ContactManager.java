package ir.sinasoheili.mycontacts.PRESENTER;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ir.sinasoheili.mycontacts.MODEL.UserContact;

public class ContactManager
{
    public static ArrayList<UserContact> readAllContact(Context context)
    {
        ArrayList<UserContact> contacts = new ArrayList<>();

        String[] columns = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME ,
                ContactsContract.CommonDataKinds.Phone.NUMBER ,
                ContactsContract.CommonDataKinds.Phone._ID
        };

        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                columns, null, null, null);

        cursor.moveToFirst();

        do
        {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            contacts.add(new UserContact(id , name , phone));
        }
        while (cursor.moveToNext());

//        unique(contacts);
        sort(contacts);

        return contacts;
    }

//    public static void unique(ArrayList<UserContact> contact)
//    {
//        for(int i=0 ; i<contact.size() ; i++)
//        {
//            for(int j=i ; j<contact.size() ; j++)
//            {
//                if(contact.get(i).getName().equals(contact.get(j).getName()))
//                {
//                    contact.remove(j);
//                }
//            }
//        }
//    }

    public static void sort(ArrayList<UserContact> contact)
    {
        Collections.sort(contact, new Comparator<UserContact>()
        {
            @Override
            public int compare(UserContact o1, UserContact o2)
            {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }
}
