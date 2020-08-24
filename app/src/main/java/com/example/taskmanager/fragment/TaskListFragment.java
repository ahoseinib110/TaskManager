package com.example.taskmanager.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.taskmanager.R;
import com.example.taskmanager.activity.TaskManagerActivity;
import com.example.taskmanager.model.State;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskDBRepository;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskListFragment extends Fragment {

    public static final String mtas = "ARG_TASK_LIST";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NAME = "name";
    private static final String ARG_TASKS_NUMBER = "tasksNumber";
    private static final String ARG_STATE = "state";
    public static final String ARG_TASK_LIST = "ARG_TASK_LIST";

    public static final int DETAIL_PICKER_REQUEST_CODE = 0;
    public static final String DIALOG_FRAGMENT_TAG = "tagDialog";

    // TODO: Rename and change types of parameters
    private String mName;
    private int mTasksNumber;
    private State mState;

    private RecyclerView mRecyclerViewTasks;

    private LinearLayout mLinearLayoutEmpty;

    private TaskDBRepository mTaskRepository;
    private TaskAdapter mTaskAdapter;

    List<Task> mTaskList;

    public TaskListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TaskListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskListFragment newInstance(String name, int tasksNumber, State state) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putInt(ARG_TASKS_NUMBER, tasksNumber);
        args.putSerializable(ARG_STATE, state);
        //Log.d("TLF_BASHIR", name + " " + tasksNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(ARG_NAME);
            mTasksNumber = getArguments().getInt(ARG_TASKS_NUMBER);
            mState = (State) getArguments().getSerializable(ARG_STATE);
            mTaskList = (List<Task>) getArguments().getSerializable(ARG_TASK_LIST);
        }
        mTaskRepository = TaskDBRepository.getInstance(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TaskManagerActivity taskManagerActivity = (TaskManagerActivity) getActivity();

        taskManagerActivity.mFABAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task("", State.TODO);
                //mTaskRepository.insert(task);
                TaskDetailFragment taskDetailFragment = TaskDetailFragment.newInstance(task);
                taskDetailFragment.setTargetFragment(TaskListFragment.this, DETAIL_PICKER_REQUEST_CODE);
                taskDetailFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
            }
        });

        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        findViews(view);
        mRecyclerViewTasks.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == DETAIL_PICKER_REQUEST_CODE && data != null) {
                setVisibility();
                updateUI();
                Log.d("bashir","come back");
            }
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Task task = null;
        outState.putSerializable(ARG_TASK_LIST, (Serializable) mTaskList);
    }

    private void findViews(View view) {
        mRecyclerViewTasks = view.findViewById(R.id.recycler_view_tasks);
        mLinearLayoutEmpty = view.findViewById(R.id.linearLayoutEmpty);
    }


    private void updateUI() {
        mTaskList = mTaskRepository.getList(mState);//getAppropriateListFromRepository();
        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter(mTaskList);
            mRecyclerViewTasks.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.setTaskList(mTaskList);
            mTaskAdapter.notifyDataSetChanged();
        }
    }


    //private List<Task> getAppropriateListFromRepository() {
    //    List<Task> taskList = new ArrayList<>();
    //    List<Task> tempList = mTaskRepository.getList();
    //    mTasksNumber = 0;
    //    for (int i = 0; i < tempList.size(); i++) {
    //        if (tempList.get(i).getTaskState().equals(mState)) {
    //            taskList.add(tempList.get(i));
    //            mTasksNumber++;
    //        }
    //    }
    //    return taskList;
    //}


    private class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
        List<Task> mTaskList;

        public TaskAdapter(List<Task> taskList) {
            mTaskList = taskList;
        }

        public List<Task> getTaskList() {
            return mTaskList;
        }

        public void setTaskList(List<Task> taskList) {
            mTaskList = taskList;
        }

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_row, parent, false);
            TaskViewHolder taskViewHolder = new TaskViewHolder(view);
            return taskViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            Task task = mTaskList.get(position);
            boolean isOdd = (position % 2 != 0);
            holder.onBind(task, isOdd);
            setVisibility();
        }

        @Override
        public int getItemCount() {
            return mTaskList.size();
        }
    }
    private void setVisibility() {
        if (mTaskList.size() > 0) {
            mRecyclerViewTasks.setVisibility(View.VISIBLE);
            mLinearLayoutEmpty.setVisibility(View.GONE);
        } else {
            mRecyclerViewTasks.setVisibility(View.GONE);
            mLinearLayoutEmpty.setVisibility(View.VISIBLE);
        }
    }
    private class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewName;
        private TextView mTextViewDate;
        private Button mButtonCircle;
        private LinearLayout mRowContainer;
        private Task mTask;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewName = itemView.findViewById(R.id.textViewName);
            mTextViewDate = itemView.findViewById(R.id.textViewDate);
            mRowContainer = itemView.findViewById(R.id.rowContainer);
            mButtonCircle = itemView.findViewById(R.id.buttonCircle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskDetailFragment taskDetailFragment = TaskDetailFragment.newInstance(mTask);
                    taskDetailFragment.setTargetFragment(TaskListFragment.this, DETAIL_PICKER_REQUEST_CODE);
                    taskDetailFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
                }
            });
        }

        public void onBind(Task task, boolean isOdd) {
            mTask = task;
            if (isOdd) {
                mRowContainer.setBackgroundResource(R.color.light_blue);
            } else {
                mRowContainer.setBackgroundResource(R.color.whight);
            }
            mTextViewName.setText(task.getTaskTitle());
            mTextViewDate.setText(String.valueOf(task.getDate()));
            mButtonCircle.setText(String.valueOf(task.getTaskTitle().charAt(0)));

        }
    }
}