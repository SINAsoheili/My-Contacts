package ir.sinasoheili.mycontacts.PRESENTER;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import ir.sinasoheili.mycontacts.MODEL.UserContact;

public class DetailUserContactPresenter implements DetailUserContactContract.DetailUserContactContract_presenter
{
    private Context context;
    private DetailUserContactContract.DetailUserContactContract_view detailView;

    public DetailUserContactPresenter(Context context , DetailUserContactContract.DetailUserContactContract_view detailView)
    {
        this.context = context;
        this.detailView = detailView;
    }

    @Override
    public void call(UserContact userContact)
    {
        if(userContact == null)
        {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+userContact.getPhone()));
        context.startActivity(intent);
    }

    @Override
    public void dial(UserContact userContact)
    {
        if(userContact == null)
        {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+userContact.getPhone()));
        context.startActivity(intent);
    }

    @Override
    public void message(UserContact userContact)
    {
        if(userContact == null)
        {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("sms:"+userContact.getPhone()));
        context.startActivity(intent);
    }
}
