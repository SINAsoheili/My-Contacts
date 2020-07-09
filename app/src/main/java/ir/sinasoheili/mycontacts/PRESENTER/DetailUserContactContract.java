package ir.sinasoheili.mycontacts.PRESENTER;

import ir.sinasoheili.mycontacts.MODEL.UserContact;

public interface DetailUserContactContract
{
    public interface DetailUserContactContract_view
    {

    }

    public interface DetailUserContactContract_presenter
    {
        public void call(UserContact userContact);
        public void dial(UserContact userContact);
        public void message(UserContact userContact);
    }
}
