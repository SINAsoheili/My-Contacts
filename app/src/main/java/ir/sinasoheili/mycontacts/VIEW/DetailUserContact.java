package ir.sinasoheili.mycontacts.VIEW;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentProviderResult;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import ir.sinasoheili.mycontacts.MODEL.UserContact;
import ir.sinasoheili.mycontacts.PRESENTER.DetailUserContactContract;
import ir.sinasoheili.mycontacts.PRESENTER.DetailUserContactPresenter;
import ir.sinasoheili.mycontacts.R;

public class DetailUserContact extends AppCompatActivity implements DetailUserContactContract.DetailUserContactContract_view , View.OnClickListener
{
    private final String[] PERMISSION = {Manifest.permission.CALL_PHONE};
    private final int REQUEST_CODE = 200;

    private ImageView imageView;
    private Button btnCall;
    private Button btnMessage;
    private Button btnEdit;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvDate;

    private UserContact userContact;
    private DetailUserContactContract.DetailUserContactContract_presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user_contact);

        initObj();

        EventBus.getDefault().register(this);

        //get user contact
        Bundle bundle = getIntent().getExtras();
        if((bundle != null)&&(bundle.containsKey(UserContact.INTENT_KEY)))
        {
            userContact = (UserContact) bundle.get(UserContact.INTENT_KEY);
            fillContent();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    private void initObj()
    {
        imageView = findViewById(R.id.detail_activity_iv);

        btnCall = findViewById(R.id.detail_activity_btn_call);
        btnCall.setOnClickListener(this);

        btnMessage = findViewById(R.id.detail_activity_btn_message);
        btnMessage.setOnClickListener(this);

        btnEdit = findViewById(R.id.detail_activity_btn_edit);
        btnEdit.setOnClickListener(this);

        tvName = findViewById(R.id.detail_activity_tv_name);
        tvPhone = findViewById(R.id.detail_activity_tv_phone);
        tvDate = findViewById(R.id.detail_activity_tv_birth_date);

        presenter = new DetailUserContactPresenter(DetailUserContact.this , this);
    }

    private void fillContent()
    {
        tvName.setText(userContact.getName());
        tvPhone.setText(userContact.getPhone());
        tvDate.setText(userContact.getBirthDate());
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
        else if(v.equals(btnMessage))
        {
            presenter.message(userContact);
        }
        else if(v.equals(btnEdit))
        {
            showEditDialog();
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

    private void showEditDialog()
    {
        EditContactDialog dialog = new EditContactDialog(userContact);
        dialog.show(getSupportFragmentManager() , null);
    }

    @Subscribe
    public void getNewUserContact(UserContact newUserContact)
    {
        userContact = newUserContact;
        fillContent();
    }

    @Subscribe
    public void deleteContact(Boolean deleteContact)
    {
        showMessage(getString(R.string.delete_item));
        finish();
    }

    private void showMessage(String text)
    {
        Toast.makeText(getBaseContext() , text , Toast.LENGTH_SHORT).show();
    }
}
