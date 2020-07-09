package ir.sinasoheili.mycontacts.VIEW;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import ir.sinasoheili.mycontacts.MODEL.UserContact;
import ir.sinasoheili.mycontacts.R;

public class UserContractListAdapter extends ArrayAdapter<UserContact>
{
    private ArrayList<UserContact> contacts;

    public UserContractListAdapter(@NonNull Context context , @NonNull ArrayList<UserContact> contacts)
    {
        super(context, R.layout.user_contact_list_item, contacts);

        this.contacts = contacts;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        UserContractListAdapter_viewHolder holder;

        if(convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_contact_list_item , parent , false);
            holder = new UserContractListAdapter_viewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (UserContractListAdapter_viewHolder) convertView.getTag();
        }

        holder.fill(contacts.get(position));
        return convertView;
    }

    private class UserContractListAdapter_viewHolder
    {
        private ImageView iv;
        private TextView tv_name;

        public UserContractListAdapter_viewHolder(View view)
        {
            iv = view.findViewById(R.id.iv_user_contact_image_list_item);

            tv_name = view.findViewById(R.id.tv_user_contact_name_list_item);
        }

        public void fill(UserContact uc)
        {
            tv_name.setText(uc.getName());
        }
    }
}
