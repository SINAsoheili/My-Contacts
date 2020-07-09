package ir.sinasoheili.mycontacts.PRESENTER;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

import ir.sinasoheili.mycontacts.MODEL.UserContact;

public class ContactManager
{
    public static ArrayList<UserContact> readAllContact(Context context)
    {
        ArrayList<UserContact> contacts = new ArrayList<>();

        String[] columns = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME , ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                columns, null, null, null);

        cursor.moveToFirst();

        do
        {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            contacts.add(new UserContact(name , phone));
        }
        while (cursor.moveToNext());

        return contacts;
    }
}
