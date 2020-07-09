package ir.sinasoheili.mycontacts.PRESENTER;

import java.util.ArrayList;

import ir.sinasoheili.mycontacts.MODEL.UserContact;

public interface MainActivityContract
{
    public interface MainActivityContract_view
    {
        public void showContacts(ArrayList<UserContact> contacts);
    }

    public interface MainActivityContract_presenter
    {
        public void readAllContact();
    }
}
