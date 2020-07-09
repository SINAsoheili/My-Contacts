package ir.sinasoheili.mycontacts.PRESENTER;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ir.sinasoheili.mycontacts.MODEL.UserContact;

public class ContactPreferenceManager
{
    private final String PREF_NAME = "CONTACT_LIST";
    private final String PREF_CONTACTS_KEY = "CONTACTS";
    private final String PREF_IS_CONTACTS_STORE_KEY = "IS_CONTACT_STORE";

    private Context context;
    private static ContactPreferenceManager ContactPreferenceManager;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private ContactPreferenceManager(Context context)
    {
        this.context = context;

        pref = context.getSharedPreferences(PREF_NAME , Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public static ContactPreferenceManager getInstance(Context context)
    {
        if(ContactPreferenceManager == null)
        {
            ContactPreferenceManager = new ContactPreferenceManager(context);
        }

        return ContactPreferenceManager;
    }

    public void StoreContact(ArrayList<UserContact> contacts)
    {
        Gson gson = new Gson();

        String js = gson.toJson(contacts);

        editor.putString(PREF_CONTACTS_KEY , js).putBoolean(PREF_IS_CONTACTS_STORE_KEY , true).apply();
    }

    public ArrayList<UserContact> getContact()
    {
        ArrayList<UserContact> contacts = new ArrayList<UserContact>();
        String js = pref.getString(PREF_CONTACTS_KEY , "");

        Gson gson = new Gson();
        contacts = gson.fromJson(js , new TypeToken<ArrayList<UserContact>>(){}.getType());

        return contacts;
    }

    public boolean isContactStore()
    {
        return pref.getBoolean(PREF_IS_CONTACTS_STORE_KEY , false);
    }
}
