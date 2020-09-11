package com.example.taskmanager.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskmanager.R;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserDBRepository;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserListFragment extends Fragment {
    private UserDBRepository mUserDBRepository;

    private RecyclerView mRecyclerViewUsers;

    private UserAdapter mUserAdapter;

    public UserListFragment() {
        // Required empty public constructor
    }


    public static UserListFragment newInstance() {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        mUserDBRepository = UserDBRepository.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        mRecyclerViewUsers = view.findViewById(R.id.recycler_view_users);
        mRecyclerViewUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUserAdapter = new UserAdapter(mUserDBRepository.getList());
        mRecyclerViewUsers.setAdapter(mUserAdapter);
        return view;
    }


    private class UserHolder extends RecyclerView.ViewHolder {
        private User mUser;
        private TextView mTextViewUserName;
        private TextView mTextViewRegDate;
        private ImageView mImageViewDelete;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewUserName = itemView.findViewById(R.id.text_view_user_name);
            mTextViewRegDate = itemView.findViewById(R.id.text_view_reg_date);
            mImageViewDelete = itemView.findViewById(R.id.image_view_delete);
            mImageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mUserDBRepository.delete(mUser);
                    mUserAdapter.setUsers(mUserDBRepository.getList());
                    mUserAdapter.notifyDataSetChanged();
                }
            });
        }

        public void onBind(User user){
            mUser = user;
            mTextViewUserName.setText(user.getUserName());
            mTextViewRegDate.setText("user.getRegDate()");
        }

    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder> {
        private List<User> mUsers;

        public List<User> getUsers() {
            return mUsers;
        }

        public void setUsers(List<User> users) {
            mUsers = users;
        }

        public UserAdapter(List<User> users) {
            mUsers = users;
        }

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_row_user, parent, false);
            UserListFragment.UserHolder userHolder = new UserListFragment.UserHolder(view);
            return userHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, int position) {
            holder.onBind(mUsers.get(position));
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }
    }
}