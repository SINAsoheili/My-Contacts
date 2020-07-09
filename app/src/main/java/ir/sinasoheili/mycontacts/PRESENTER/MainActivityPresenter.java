package ir.sinasoheili.mycontacts.PRESENTER;

import android.content.Context;

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
        ArrayList<UserContact> contacts = ContactManager.readAllContact(context);
        mainActivityView.showContacts(contacts);
    }
}
