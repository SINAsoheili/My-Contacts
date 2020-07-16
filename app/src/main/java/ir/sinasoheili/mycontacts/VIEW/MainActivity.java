package ir.sinasoheili.mycontacts.VIEW;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;

import ir.sinasoheili.mycontacts.MODEL.UserContact;
import ir.sinasoheili.mycontacts.PRESENTER.MainActivityContract;
import ir.sinasoheili.mycontacts.PRESENTER.MainActivityPresenter;
import ir.sinasoheili.mycontacts.R;

public class MainActivity extends AppCompatActivity implements MainActivityContract.MainActivityContract_view, AdapterView.OnItemClickListener, View.OnClickListener
{
    private ListView lv;
    private Button btnAddContact;
    private TextView tv_empty_list;

    private final int REQUEST_CODE = 100; //request code for read contact
    private final String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS};
    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitObj();

        setFade();

        if(checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(PERMISSIONS , REQUEST_CODE);
        }
        else
        {
            presenter.readAllContact();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if(! EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }

        reloadItems();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    private void InitObj()
    {
        lv = findViewById(R.id.lv_contact_list);
        lv.setOnItemClickListener(this);

        tv_empty_list = findViewById(R.id.tv_empty_list);

        btnAddContact = findViewById(R.id.toolbar_btn_add_contact);
        btnAddContact.setOnClickListener(this);

        presenter = new MainActivityPresenter(MainActivity.this , this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(requestCode == REQUEST_CODE)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                presenter.readAllContact();
            }
            else
            {
                showMessage(getString(R.string.not_permission , "READ-CONTACT"));
            }
        }
    }

    @Override
    public void showContacts(ArrayList<UserContact> contacts)
    {
        if(contacts == null)
        {
            contacts = new ArrayList<>();
        }

        if(contacts.size() == 0)
        {
            tv_empty_list.setVisibility(View.VISIBLE);
        }
        else
        {
            tv_empty_list.setVisibility(View.GONE);
        }

        UserContractListAdapter adapter = new UserContractListAdapter(getApplicationContext() , contacts);
        lv.setAdapter(adapter);
    }

    private void showMessage(String text)
    {
        Snackbar.make(lv , text , Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = new Intent(MainActivity.this , DetailUserContact.class);

        UserContact uc = (UserContact) parent.getItemAtPosition(position);
        intent.putExtra(UserContact.INTENT_KEY , uc);

        CardView image_cv = view.findViewById(R.id.container_iv_user_contact_image_list_item);

        ActivityOptionsCompat aoc = ActivityOptionsCompat.makeSceneTransitionAnimation(
                MainActivity.this ,
               image_cv , image_cv.getTransitionName()
        );


        startActivity(intent , aoc.toBundle());
    }

    @Override
    public void onClick(View v)
    {
        if(v.equals(btnAddContact))
        {
            AddContactDialog dialog = new AddContactDialog();
            dialog.show(getSupportFragmentManager() , null);
        }
    }

    private void reloadItems()
    {
        if(checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
        {
            presenter.readAllContact();
        }
    }

    @Subscribe
    public void addNewContact(Boolean b)
    {
        if(b)
        {
            reloadItems();
        }
    }

    private void setFade() //set fade for animation transition
    {
        Fade fade = new Fade();

        fade.excludeTarget(R.id.toolbar , false);
        fade.excludeTarget(android.R.id.statusBarBackground , true);
        fade.excludeTarget(android.R.id.navigationBarBackground , true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
    }
}
