package ir.sinasoheili.mycontacts.PRESENTER;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import ir.sinasoheili.mycontacts.MODEL.UserContact;

public class MainActivityPresenter implements MainActivityContract.MainActivityContract_presenter
{
    private Context context;
    private MainActivityContract.MainActivityContract_view mainActivityView;

    public MainActivityPresenter(Context context , MainActivityContract.MainActivityContract_view mainActivityView)
    {
        this.context = context;
        this.mainActivityView = mainActivityView;
    }

    @Override
    public void readAllContact()
    {
        ArrayList<UserContact> contacts;

        ContactPreferenceManager pref = ContactPreferenceManager.getInstance(context);
        if(pref.isContactStore()) // read contact from preferences
        {
            contacts = pref.getContact();
        }
        else //read contact from user device AND write to preference
        {
            contacts = ContactManager.readAllContact(context);

            pref.StoreContact(contacts);
        }

        mainActivityView.showContacts(contacts);
    }
}
