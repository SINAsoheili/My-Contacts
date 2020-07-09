package ir.sinasoheili.mycontacts.VIEW;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import ir.sinasoheili.mycontacts.MODEL.UserContact;
import ir.sinasoheili.mycontacts.PRESENTER.DetailUserContactContract;
import ir.sinasoheili.mycontacts.R;

public class DetailUserContact extends AppCompatActivity implements DetailUserContactContract.DetailUserContactContract_view
{
    private UserContact userContact;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user_contact);

        Bundle bundle = getIntent().getExtras();
        if((bundle != null)&&(bundle.containsKey(UserContact.INTENT_KEY)))
        {
            userContact = (UserContact) bundle.get(UserContact.INTENT_KEY);

            Toast.makeText(this, userContact.toString() , Toast.LENGTH_SHORT).show();
        }
    }
}
