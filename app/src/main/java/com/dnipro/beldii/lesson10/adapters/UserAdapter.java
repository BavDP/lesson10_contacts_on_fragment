package com.dnipro.beldii.lesson10.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dnipro.beldii.lesson10.R;
import com.dnipro.beldii.lesson10.helpers.AsyncPhotoLoader;
import com.dnipro.beldii.lesson10.model.User;

import java.util.ArrayList;

class UserDiffCallback extends DiffUtil.Callback {
    private ArrayList<User> oldUsers;
    private ArrayList<User> newUser;

    public UserDiffCallback(ArrayList<User> oldUsers, ArrayList<User> newUser) {
        this.oldUsers = oldUsers;
        this.newUser = newUser;
    }

    @Override
    public int getOldListSize() {
        return oldUsers.size();
    }

    @Override
    public int getNewListSize() {
        return newUser.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldUsers.get(oldItemPosition).getId() == newUser.get(newItemPosition).getId() ;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldUsers.get(oldItemPosition).equals(newUser.get(newItemPosition));
    }
}


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ContactHolder> {
    private ArrayList<User> users;
    private UserAdapterClickListener itemViewClickListener;

    public UserAdapterClickListener getItemViewClickListener() {
        return itemViewClickListener;
    }

    public void setItemViewClickListener(UserAdapterClickListener itemViewClickListener) {
        this.itemViewClickListener = itemViewClickListener;
    }

    public void setContacts(ArrayList<User> users) {
        final UserDiffCallback diffCallback = new UserDiffCallback(this.users, users);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.users = users;
        diffResult.dispatchUpdatesTo(this);
    }

    protected static class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View itemView;
        private TextView nameTextView;
        private TextView companyTextView;
        private ImageButton deleteBtn;

        private ImageButton editBtn;
        private ImageView photoView;
        private Drawable photoDrawable;
        private UserAdapterClickListener clickListener;

        public UserAdapterClickListener getClickListener() {
            return clickListener;
        }

        public void setClickListener(UserAdapterClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View view) {
            if (view == deleteBtn) {
                // нажатие кнопки удаления
                this.clickListener.deleteContactClick(itemView);
            } else if (view == nameTextView) {
                // нажатие по имени контакта
                this.clickListener.nameContactClick(itemView);
            } else if (view == editBtn) {
                this.clickListener.editContactClick(itemView);
            }
        }

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
        public void bind(User user) {
            nameTextView = itemView.findViewById(R.id.contactName);
            companyTextView = itemView.findViewById(R.id.company);
            photoView = itemView.findViewById(R.id.photo);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            editBtn = itemView.findViewById(R.id.editBtn);

            nameTextView.setText(user.getName());
            companyTextView.setText(user.getCompany());

            itemView.setTag(user);

            nameTextView.setOnClickListener(this);
            deleteBtn.setOnClickListener(this);
            editBtn.setOnClickListener(this);

            if (!user.getPhoto().isEmpty()) {
                AsyncPhotoLoader photoLoader = new AsyncPhotoLoader();
                photoLoader.load(user.getPhoto(), photoView);
            } else {
                Drawable defaultPhoto = AppCompatResources.getDrawable(itemView.getContext(), R.drawable.user);
                photoView.setImageDrawable(defaultPhoto);
            }
        }
    }

    public UserAdapter(ArrayList<User> users) {
        super();
        this.users = users;
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        holder.bind(users.get(position));
        holder.setClickListener(itemViewClickListener);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}