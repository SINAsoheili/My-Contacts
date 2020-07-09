package ir.sinasoheili.mycontacts.VIEW;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import ir.sinasoheili.mycontacts.MODEL.UserContact;
import ir.sinasoheili.mycontacts.PRESENTER.MainActivityContract;
import ir.sinasoheili.mycontacts.PRESENTER.MainActivityPresenter;
import ir.sinasoheili.mycontacts.R;

public class MainActivity extends AppCompatActivity implements MainActivityContract.MainActivityContract_view, AdapterView.OnItemClickListener
{
    private ListView lv;

    private final int REQUEST_CODE = 100; //request code for read contact
    private final String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS};
    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitObj();

        if(checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(PERMISSIONS , REQUEST_CODE);
        }
        else
        {
            presenter.readAllContact();
        }
    }

    private void InitObj()
    {
        lv = findViewById(R.id.lv_contact_list);
        lv.setOnItemClickListener(this);

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

        ArrayAdapter<UserContact> adapter =  new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 , contacts);
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
        startActivity(intent);
    }
}
