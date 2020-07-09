package ir.sinasoheili.mycontacts.VIEW;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import ir.sinasoheili.mycontacts.MODEL.UserContact;
import ir.sinasoheili.mycontacts.PRESENTER.DetailUserContactContract;
import ir.sinasoheili.mycontacts.PRESENTER.DetailUserContactPresenter;
import ir.sinasoheili.mycontacts.R;

public class DetailUserContact extends AppCompatActivity implements DetailUserContactContract.DetailUserContactContract_view , View.OnClickListener
{
    private final String[] PERMISSION = {Manifest.permission.CALL_PHONE};
    private final int REQUEST_CODE = 200;

    private Button btnCall;

    private UserContact userContact;
    private DetailUserContactContract.DetailUserContactContract_presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user_contact);

        initObj();

        //get user contact
        Bundle bundle = getIntent().getExtras();
        if((bundle != null)&&(bundle.containsKey(UserContact.INTENT_KEY)))
        {
            userContact = (UserContact) bundle.get(UserContact.INTENT_KEY);
        }
    }

    private void initObj()
    {
        btnCall = findViewById(R.id.btn_call);
        btnCall.setOnClickListener(this);

        presenter = new DetailUserContactPresenter(DetailUserContact.this , this);
    }

    @Override
    public void onClick(View v)
    {
        if(v.equals(btnCall))
        {
            if(checkGetPermission())
            {
                presenter.call(userContact);
            }
        }
    }

    private boolean checkGetPermission()
    {
        boolean result = true;

        if(checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            result = false;

            requestPermissions(PERMISSION , REQUEST_CODE);
        }

        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(requestCode == REQUEST_CODE)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                presenter.call(userContact);
            }
            else
            {
                presenter.dial(userContact);
            }
        }
    }
}
