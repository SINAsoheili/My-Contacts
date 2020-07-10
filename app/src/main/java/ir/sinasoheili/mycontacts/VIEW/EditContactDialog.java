package ir.sinasoheili.mycontacts.VIEW;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;

import ir.sinasoheili.mycontacts.MODEL.UserContact;
import ir.sinasoheili.mycontacts.PRESENTER.ContactPreferenceManager;
import ir.sinasoheili.mycontacts.R;

public class EditContactDialog extends DialogFragment implements View.OnClickListener
{
    private TextInputLayout tilName;
    private TextInputLayout tilPhone;
    private TextInputLayout tilDate;
    private EditText etName;
    private EditText etPhone;
    private EditText etDate;
    private Button btnSubmit;
    private Button btnDelete;

    private UserContact userContact;

    public EditContactDialog(UserContact userContact)
    {
        this.userContact = userContact;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.edit_contract_dialog_layout , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initObj(view);

        fillItems();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        //set dialog size
        getDialog().getWindow().setLayout(
                (getDialog().getWindow().getWindowManager().getDefaultDisplay().getWidth()/100)*90 ,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        //remove background
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    private void initObj(View view)
    {
        tilName = view.findViewById(R.id.til_dialog_name);
        tilPhone = view.findViewById(R.id.til_dialog_phone);
        tilDate = view.findViewById(R.id.til_dialog_date);

        etName = view.findViewById(R.id.et_dialog_name);
        etPhone = view.findViewById(R.id.et_dialog_phone);
        etDate = view.findViewById(R.id.et_dialog_date);

        btnSubmit = view.findViewById(R.id.btn_dialog_submit);
        btnSubmit.setOnClickListener(this);

        btnDelete = view.findViewById(R.id.btn_dialog_delete);
        btnDelete.setOnClickListener(this);
    }

    private void fillItems()
    {
        if(userContact == null)
        {
            return;
        }

        etName.setText(userContact.getName());
        etPhone.setText(userContact.getPhone());
        etDate.setText(userContact.getBirthDate());
    }

    @Override
    public void onClick(View v)
    {
        if(v.equals(btnSubmit))
        {
            if(isNameValid() && isPhoneValid() && isDateValid())
            {
                //do edit
                String name = etName.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String date = etDate.getText().toString();

                ContactPreferenceManager pref = ContactPreferenceManager.getInstance(getContext());
                if(date.isEmpty())
                {
                    userContact = pref.updateContact(getContext() , userContact , name , phone);
                }
                else
                {
                    userContact = pref.updateContact(getContext() , userContact , name , phone , date.trim());
                }

                //dismiss
                this.dismiss();

                //send new user contact to detail activity for updating
                EventBus.getDefault().post(userContact);
            }
        }
        else if(v.equals(btnDelete))
        {
            //delete item
            ContactPreferenceManager pref = ContactPreferenceManager.getInstance(getContext());
            pref.deleteContact(getContext() , userContact);

            //dismiss dialog
            this.dismiss();

            //goto activity and finish activity
            EventBus.getDefault().post(true);
        }
    }

    private boolean isNameValid()
    {
        String name = etName.getText().toString().trim();
        if(name.isEmpty())
        {
            tilName.setError(getString(R.string.dialog_error_empty , "name"));
            etName.requestFocus();
            return false;
        }
        tilName.setErrorEnabled(false);
        return true;
    }

    private boolean isPhoneValid()
    {
        String phone = etPhone.getText().toString().trim();
        if(phone.isEmpty())
        {
            tilPhone.setError(getString(R.string.dialog_error_empty , "phone"));
            etPhone.requestFocus();
            return false;
        }
        tilPhone.setErrorEnabled(false);
        return true;
    }

    private boolean isDateValid()
    {
        //because user can, don't set birth date so we don't check is empty or not

        /*
        String Date = etDate.getText().toString().trim();
        if(Date.isEmpty())
        {
            tilDate.setError(getString(R.string.dialog_error_empty , "birth date"));
            etDate.requestFocus();
            return false;
        }
        tilDate.setErrorEnabled(false);
         */
        return true;
    }
}
