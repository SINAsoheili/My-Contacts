package ir.sinasoheili.mycontacts.VIEW;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;

import ir.sinasoheili.mycontacts.MODEL.UserContact;
import ir.sinasoheili.mycontacts.PRESENTER.ContactPreferenceManager;
import ir.sinasoheili.mycontacts.R;

public class AddContactDialog extends DialogFragment implements View.OnClickListener, View.OnFocusChangeListener {
    private TextInputLayout tilName;
    private TextInputLayout tilPhone;
    private TextInputLayout tilDate;
    private EditText etName;
    private EditText etPhone;
    private EditText etDate;
    private Button btnAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.add_new_contract_dialog_layout , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initObj(view);
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
        etDate.setOnFocusChangeListener(this);
        etDate.setOnClickListener(this);

        btnAdd = view.findViewById(R.id.btn_dialog_add);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v.equals(btnAdd))
        {
            if(isNameValid() && isPhoneValid() && isDateValid())
            {
                ContactPreferenceManager pref = ContactPreferenceManager.getInstance(getContext());

                String name  = etName.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String date = etDate.getText().toString();

                if(date.isEmpty())
                {
                    pref.addContact(getContext() ,
                            new UserContact(
                                    String.valueOf(pref.getMaxId()+1) ,
                                    name ,
                                    phone
                            ));
                }
                else
                {
                    pref.addContact(getContext() ,
                            new UserContact(
                                    String.valueOf(pref.getMaxId()+1) ,
                                    name ,
                                    phone ,
                                    date.trim()
                            ));
                }


                this.dismiss();

                Toast.makeText(getContext() , getString(R.string.add_item) , Toast.LENGTH_SHORT).show();

                //say to main activity reload list of contact
                EventBus.getDefault().post(true);
            }
        }
        else if(v.equals(etDate))
        {
            showDateDialogPicker();
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

    @Override
    public void onFocusChange(View v, boolean hasFocus)
    {
        if(v.equals(etDate) && hasFocus)
        {
            showDateDialogPicker();
        }
    }

    private void showDateDialogPicker()
    {
        DatePickerDialog dialog = new DatePickerDialog(getContext());
        dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                etDate.setText(year+"/"+month+"/"+dayOfMonth);
            }
        });
        dialog.show();
    }
}
